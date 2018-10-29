/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.db.tool.deid.fx;

import edu.db.tool.deid.annotator.Annotator;
import edu.db.tool.deid.utils.ClassInit;
import edu.db.tool.deid.document.IAnnotation;
import edu.db.tool.deid.annotator.Annotator_Dict_Manual_StandaloneOnly;
import edu.db.tool.deid.utils.Dictionary;
import edu.db.tool.deid.utils.RegexUtils;
import edu.db.tool.deid.document.IDocument;
import edu.db.tool.deid.fx.FXConfig.DEIDMODE;
import edu.db.tool.deid.fx.FXConfig.REDACTMODE;
import edu.db.tool.deid.utils.FileUtil;
import edu.db.tool.deid.utils.ResourceUtil;
import edu.db.tool.deid.intervaltree.IntervalTree;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.IndexRange;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Duy Bui
 */
public class FXMLDocumentController implements Initializable {

    public String exportFileName = "Redacted.Document.txt";
    @FXML
    private TextArea textArea;
    private StringBuffer textBuf;

    @FXML
    private TextField textFieldCharacters;

    @FXML
    private TextField textFieldTag;

    @FXML
    private RadioButton radioTag;

    @FXML
    private RadioButton radioCharacters;

    @FXML
    private RadioButton radioModeLDS;

    @FXML
    private RadioButton radioModeAll;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label messageLabel;

    private String text = null;

    private final static Logger logger = Logger.getLogger(FXMLDocumentController.class.getName());

    private ClassInit resourceInit;
    public static Timeline timeline;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);

        messageLabel.setText("Loading resources....");
        resourceInit = ClassInit.Init();
        timeline = new Timeline(new KeyFrame(
                Duration.millis(3000),
                ae -> checkClock()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        FXConfig.initFolder();
        textArea.setContextMenu(contextMenuInit());
        progressBar.setProgress(0);

    }

    private void checkClock() {
        if (resourceInit.stop) {
            progressBar.setProgress(0);
            timeline.stop();
            messageLabel.setText("Ready");

        } else {
            progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
            messageLabel.setText("Loading resources....");
        }
    }

    private ContextMenu contextMenuInit() {

        final ContextMenu contextMenu = new ContextMenu();
        MenuItem redactItem = new MenuItem("Redact");
        redactItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                IndexRange range = textArea.getSelection();
                if (range.getLength() > 0) {

                    String replaceString = "[REDACTED]";

                    if (radioTag.isSelected()) {
                        String tag = textFieldTag.getText();
                        if (tag.length() > 0 && tag.length() < 50) {
                            replaceString = tag;
                        }
                    } else if (radioCharacters.isSelected()) {
                        String c = textFieldCharacters.getText();
                        char replaceChar = 'X';
                        if (c.length() > 0) {
                            replaceChar = c.charAt(0);
                        }
                        char[] chars = new char[range.getLength()];
                        Arrays.fill(chars, replaceChar);
                        replaceString = new String(chars);
                    }
                    textArea.replaceText(range.getStart(), range.getEnd(), replaceString);
                }

            }
        });

        Menu addLexiconMenu = new Menu("Add Lexicon");
        Menu csLexiconMenu = new Menu("Case Sensitive");
        Menu ciLexiconMenu = new Menu("Case Insensitive");
        addLexiconMenu.getItems().addAll(csLexiconMenu, ciLexiconMenu);

        if (FXConfig.csDir != null) {
            MenuItem folderItem = new MenuItem("Open Folder");
            csLexiconMenu.getItems().add(folderItem);
            folderItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        Runtime.getRuntime().exec("explorer.exe /open," + FXConfig.csDir.getAbsolutePath());
                    } catch (IOException ex) {
                        logger.log(Level.SEVERE, null, ex);
                    }
                }
            });
            
            csLexiconMenu.getItems().add(new SeparatorMenuItem());

            for (File f : FXConfig.csDir.listFiles()) {
                MenuItem i = new MenuItem(f.getName());
                csLexiconMenu.getItems().add(i);
                i.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String text = getValidToBeAddLexicon();
                        if (text != null) {
                            FileUtil.writeFile(FileUtil.NL + text, f.getAbsolutePath(), true);
                            Annotator_Dict_Manual_StandaloneOnly.reset();
                        }
                    }
                });
            }
        }
        if (FXConfig.ciDir != null) {
            
            MenuItem folderItem = new MenuItem("Open Folder");
            ciLexiconMenu.getItems().add(folderItem);
            folderItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        Runtime.getRuntime().exec("explorer.exe /open," + FXConfig.ciDir.getAbsolutePath());
                    } catch (IOException ex) {
                        logger.log(Level.SEVERE, null, ex);
                    }
                }
            });
            
            ciLexiconMenu.getItems().add(new SeparatorMenuItem());
            
            
            
            for (File f : FXConfig.ciDir.listFiles()) {
                MenuItem i = new MenuItem(f.getName());
                ciLexiconMenu.getItems().add(i);
                i.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String text = getValidToBeAddLexicon();
                        if (text != null) {
                            FileUtil.writeFile(FileUtil.NL + text.toLowerCase(), f.getAbsolutePath(), true);
                            Annotator_Dict_Manual_StandaloneOnly.reset();
                        }
                    }
                });
            }
        }

        contextMenu.getItems().addAll(redactItem, new SeparatorMenuItem(), addLexiconMenu);

        return contextMenu;
    }

    public String getValidToBeAddLexicon() {
        String s = getSelectedText();
        if (s == null || (s.trim()).equals("")) {
            alert("Text length must not be empty");
            return null;
        }
        s = s.trim().replaceAll("[\n\r\t ]+", " ");
        if (s.length() > 50) {
            alert("Text length must be lower than 50 characters");
            return null;
        }

        return s;
    }

    public String getSelectedText() {
        IndexRange range = textArea.getSelection();
        if (range.getLength() > 0) {
            return textArea.getText(range.getStart(), range.getEnd());
        }
        return null;
    }

    @FXML
    protected void importFileMouseClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showOpenDialog(textArea.getScene().getWindow());
        if (file != null) {
            exportFileName = "Redacted." + file.getName();
            this.text = FileUtil.readFile(file.getAbsolutePath());
            textArea.setText(this.text);
        }

    }

    @FXML
    protected void loadSampleMouseClicked(ActionEvent event) {
        this.text = ResourceUtil.getText("/edu/db/tool/deid/resources/fx/SampleText.txt");
        textArea.setText(text);
//        textArea.setEditable(true);
    }

    @FXML
    protected void redactPHIMouseClicked(ActionEvent event) {

        if (timeline.getStatus() != Timeline.Status.STOPPED) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please wait for resource initialization", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        try {

            progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);

            if (radioModeLDS.isSelected()) {
                FXConfig.switchToLDSMode();
            } else if (radioModeAll.isSelected()) {
                FXConfig.switchToAllMode();
            }
            Annotator_Dict_Manual_StandaloneOnly.reset();

            String text = textArea.getText();
            IDocument doc = IDocument.createDoc(text);
            Annotator.annotate(doc);
            List<IAnnotation> anns = doc.getPHIAnns();
            anns = filterRedacatedAnns(doc, anns);

            textBuf = new StringBuffer(text);

            textArea.setText(getTextRedactedText(anns));
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
            alert("Trace errors in config/log directory");
        } finally {
            progressBar.setProgress(0);

        }

//        textArea.setEditable(false);
    }

    private void alert(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR, s, ButtonType.OK);
        alert.showAndWait();
    }

    public List<IAnnotation> filterRedacatedAnns(IDocument doc, List<IAnnotation> anns) {

        Dictionary dict = new Dictionary(Arrays.asList(new String[]{textFieldTag.getText()}), true, true, true);
        List<IAnnotation> redactedAnns = dict.matchDoc(doc);

        IntervalTree<IAnnotation> it = new IntervalTree<IAnnotation>();
        for (IAnnotation ann : redactedAnns) {
            it.addInterval(ann.start, ann.end, ann);
        }

        Pattern pattern = Pattern.compile("\\b(" + textFieldCharacters.getText() + "{2,})\\b");
        redactedAnns = RegexUtils.matchContent(doc, pattern);
        for (IAnnotation ann : redactedAnns) {
            it.addInterval(ann.start, ann.end, ann);
        }

        List<IAnnotation> selectedAnns = new ArrayList<>();
        for (IAnnotation ann : anns) {
            List<IAnnotation> overlaps = it.get(ann.start, ann.end);
            if (overlaps.isEmpty()) {
                selectedAnns.add(ann);
            }
        }

        return selectedAnns;
    }

    @FXML
    protected void batchRunMouseClicked(ActionEvent event) {

        updateFXConfigWithGui();

        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(textArea.getScene().getWindow());
        dialog.setTitle("Batch Runner");

        Parent root;
        try {
//            FXMLLoader loader = FXMLLoader.load(FXMLDocumentController.class.getResource("/edu/db/tool/deid/resources/fx/BatchProcessFXMLDocument.fxml"));

            root = FXMLLoader.load(FXMLDocumentController.class.getResource("/edu/db/tool/deid/resources/fx/BatchProcessFXMLDocument.fxml"));
            Scene scene = new Scene(root);

//            BatchProcessFXMLDocumentController controller = loader.getController();
//            controller.mainController = this;
            dialog.setScene(scene);

            dialog.show();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }

    }

    public void updateFXConfigWithGui() {
        if (radioTag.isSelected()) {
            FXConfig.redactmode = REDACTMODE.GENERIC_TAG;
            FXConfig.genericTag = textFieldTag.getText();
        } else if (radioCharacters.isSelected()) {
            FXConfig.redactmode = REDACTMODE.DUP_CHARACTERS;
            FXConfig.dupChars = textFieldCharacters.getText();
        }
        if (radioModeAll.isSelected()) {
            FXConfig.runmode = DEIDMODE.ALL;
        } else if (radioModeLDS.isSelected()) {
            FXConfig.runmode = DEIDMODE.LDS;

        }
    }

    @FXML
    protected void exportMouseClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName(exportFileName);

        File file = fileChooser.showSaveDialog(textArea.getScene().getWindow());
        if (file != null) {
            FileUtil.writeFile(textArea.getText(), file.getAbsolutePath(), false);
        }
    }

    public String getTextRedactedText(List<IAnnotation> phis) {
        Collections.sort(phis, new Comparator<IAnnotation>() {
            @Override
            public int compare(IAnnotation o1, IAnnotation o2) {
                return o1.start - o2.start;
            }
        });

        String replaceString = "[REDACTED]";
        char replaceChar = 'X';
        

        if (radioTag.isSelected()) {
            String tag = textFieldTag.getText();
            if (tag.length() > 0 && tag.length() < 50) {
                replaceString = tag;
            }
        } else if (radioCharacters.isSelected()) {
            String c = textFieldCharacters.getText();
            if (c.length() > 0) {
                replaceChar = c.charAt(0);
            }
        }

        int offset = 0;
        for (IAnnotation phi : phis) {

            if (radioCharacters.isSelected()) {
                char[] chars = new char[phi.getText().length()];
                Arrays.fill(chars, replaceChar);
                replaceString = new String(chars);
            }

            textBuf.replace(phi.start + offset, phi.end + offset, replaceString);
            offset = offset - (phi.end - phi.start) + replaceString.length();
        }

        return textBuf.toString();
    }

}
