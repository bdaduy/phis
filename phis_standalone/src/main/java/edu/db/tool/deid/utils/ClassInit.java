/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.db.tool.deid.utils;

import edu.db.tool.deid.annotator.Annotator_CRF_I2b22014_1304_AgeDateZip;
import edu.db.tool.deid.annotator.Annotator_CRF_I2b22014_1304_LDS;
import edu.db.tool.deid.annotator.Annotator_CRF_UAB_305_LDS;
import edu.db.tool.deid.annotator.Annotator_Dict_Country;
import edu.db.tool.deid.fx.FXConfig;
import java.util.logging.Logger;
import javafx.scene.control.ProgressBar;

/**
 *
 * @author Duy Bui
 */
public class ClassInit extends Thread {

    private final static Logger logger = Logger.getLogger(ClassInit.class.getName());
    public boolean stop = false;

    public ClassInit() {
    }

    @Override
    public void run() {
        stop = false;
        try {

            Annotator_CRF_I2b22014_1304_LDS.getFileClassifier();
            Annotator_CRF_UAB_305_LDS.getFileClassifier();
            Annotator_CRF_I2b22014_1304_AgeDateZip.getFileClassifier();
            HumanNames.init();
            Annotator_Dict_Country.getCountryDict();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stop = true;

        }

    }

    public static ClassInit Init() {
        ClassInit init = new ClassInit();
        init.start();
        return init;
    }
}
