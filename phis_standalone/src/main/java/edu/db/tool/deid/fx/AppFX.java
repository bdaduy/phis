/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.db.tool.deid.fx;

import edu.db.tool.deid.utils.TimeLog;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Duy Bui
 */
public class AppFX extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader=new FXMLLoader();
        
        Parent root = loader.load(getClass().getResource("/edu/db/tool/deid/resources/fx/FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Personal Health Information Scrubber (Standalone)");
        stage.setScene(scene);
        stage.show();
        
        System.out.println("Application Loaded ("+TimeLog.getCurrentTime()+")" );
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FXConfig.enableSTDLogging();
        launch(args);
    }
    
}
