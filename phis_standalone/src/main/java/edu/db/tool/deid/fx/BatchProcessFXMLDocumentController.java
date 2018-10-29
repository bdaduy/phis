/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.db.tool.deid.fx;

import edu.db.tool.deid.annotator.Annotator;
import edu.db.tool.deid.document.IAnnotation;
import edu.db.tool.deid.document.IDocument;
import edu.db.tool.deid.utils.FileUtil;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.util.Duration;

/**
 *
 * @author Duy Bui
 */
public class BatchProcessFXMLDocumentController implements Initializable {

    private final static Logger logger = Logger.getLogger(BatchProcessFXMLDocumentController.class.getName());

    @FXML
    private TextField textFieldInputFolder;
    @FXML
    private TextField textFieldOutputFolder;
    @FXML
    private TextField textFieldInputFileExtension;
    @FXML
    private TextField textFieldOutputFilePrefix;
    @FXML
    private Button buttonOutputFolderBrowse;

    @FXML
    private CheckBox checkBoxSameInOutFolder;

    @FXML
    private Label labelMsg;

    @FXML
    protected void checkBoxSameInOutFolderOnAction(ActionEvent event) {
        boolean isSameIO = checkBoxSameInOutFolder.isSelected();
        textFieldOutputFolder.setDisable(isSameIO);
        buttonOutputFolderBrowse.setDisable(isSameIO);
        if (isSameIO) {
            textFieldOutputFolder.setText(textFieldInputFolder.getText());
        }
    }

    @FXML
    protected void browseInputFolderMouseClicked(ActionEvent event) {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory
                = directoryChooser.showDialog(textFieldInputFolder.getScene().getWindow());

        if (selectedDirectory == null) {
            logger.log(Level.WARNING, "No Directory selected");
        } else {
            textFieldInputFolder.setText(selectedDirectory.getAbsolutePath());
            if (checkBoxSameInOutFolder.isSelected()) {
                textFieldOutputFolder.setText(textFieldInputFolder.getText());
            }
        }
    }

    @FXML
    protected void browseOutputFolderMouseClicked(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory
                = directoryChooser.showDialog(textFieldOutputFolder.getScene().getWindow());

        if (selectedDirectory == null) {
            logger.log(Level.WARNING, "No Directory selected");
        } else {
            textFieldOutputFolder.setText(selectedDirectory.getAbsolutePath());
        }

    }

    private void alert(String s) {
        Alert alert = new Alert(AlertType.ERROR, s, ButtonType.OK);
        alert.showAndWait();
    }

    public static boolean stop = false;
    public static boolean error = false;
    public static Queue<File> qFiles = new LinkedList<File>();
    public static int maxSize = 0;
    private static File inputDir;
    private static File outputDir;
    private Pattern fileExtPattern;
    private String prefix;

    private boolean validate() {
        if (inputDir == null || !inputDir.isDirectory()) {
            alert("Input Directory Not Valid");
            return false;
        }
        if (outputDir == null || !outputDir.isDirectory()) {
            alert("Output Directory Not Valid");
            return false;
        }

        if (textFieldInputFileExtension.getText().trim().equals("")) {
            alert("Empty Input File Extension");
            return false;
        }
        try {
            String fileExt = textFieldInputFileExtension.getText();
            fileExt = fileExt.replaceAll("[.]", "\\\\$0");
            fileExt = fileExt.replaceAll("[*]", ".$0");
            fileExt = fileExt + "$";
//            logger.log(Level.INFO, fileExt);

            fileExtPattern = Pattern.compile(fileExt, Pattern.CASE_INSENSITIVE);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            alert("File Extension Invalid");
            return false;
        }

        if (textFieldOutputFilePrefix.getText().trim().equals("")) {

            alert("Empty Out File Prefix");
            return false;
        }
        prefix = textFieldOutputFilePrefix.getText().trim();

        return true;
    }

    @FXML
    protected void resumeMouseClicked(ActionEvent event) {
        processFileQueue();
    }

    @FXML
    protected void executeMouseClicked(ActionEvent event) {
        inputDir = new File(textFieldInputFolder.getText());
        outputDir = new File(textFieldOutputFolder.getText());

        if (!validate()) {
            return;
        }

        qFiles.clear();

        List<File> txtFiles = new ArrayList<File>();
        for (File f : inputDir.listFiles()) {

            Matcher m = fileExtPattern.matcher(f.getName());

            if (f.isFile() && m.matches()) {
                txtFiles.add(f);
                qFiles.add(f);
            }
        }
        maxSize = qFiles.size();
        labelMsg.setText("[Running] - Found " + maxSize + " files");

        processFileQueue();

        timeline.play();

    }

    private void processFileQueue() {
        stop = false;
        error = false;

        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    while (!qFiles.isEmpty() && !stop) {
                        File f = qFiles.poll();
                        String text = FileUtil.readFile(f.getAbsolutePath());
                        IDocument doc = IDocument.createDoc(text);
                        Annotator.annotate(doc);
                        List<IAnnotation> anns = doc.getPHIAnns();
                        String redactedText = FXConfig.getTextRedactedText(text, anns);
                        FileUtil.writeFile(redactedText, outputDir.getAbsolutePath() + "\\" + prefix + f.getName(), false);
                    }
                } catch (Exception e) {
                    logger.log(Level.SEVERE, e.getMessage());
                    error = true;
                } finally {
                    stop = true;

                    return null;
                }

            }

        };

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    public void checkLabel() {
        if (error) {
            labelMsg.setText("[ERROR] - Trace errors in config/log directory");
            return;
        }

        String status = "Running";
        if (stop) {
            status = "Stopped";
        }

        labelMsg.setText("[" + status + "] - Remaining Files: " + qFiles.size());

    }

    @FXML
    protected void stopMouseClicked(ActionEvent event) {
        stop = true;
    }
    public static Timeline timeline;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        timeline = new Timeline(new KeyFrame(
                Duration.millis(3000),
                ae -> checkLabel()));
        timeline.setCycleCount(Animation.INDEFINITE);

    }

}
