/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.db.tool.deid.annotator;

import edu.db.tool.deid.utils.Dictionary;
import edu.db.tool.deid.document.IAnnotation;
import edu.db.tool.deid.document.IDocument;
import edu.db.tool.deid.fx.FXConfig;
import edu.db.tool.deid.utils.FileUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Duy Bui
 */
public class Annotator_Dict_Manual_StandaloneOnly {

//    private final static Logger logger = Logger.getLogger(CustomDictAnnotator.class.getName());

    private static Dictionary csDictionary;
    private static Dictionary ciDictionary;

    private static boolean init = false;

    public static void reset() {
        init = false;
    }

    public static void init() {
        if (init) {
            return;
        }

        File csDicDir = new File(FXConfig.csDirPath);
        if (csDicDir.exists()) {
            List<String> csTerms = new ArrayList();
            for (File f : csDicDir.listFiles()) {
                csTerms.addAll(FileUtil.readFileByline(f.getAbsolutePath()));
            }
            csDictionary = new Dictionary(csTerms, true, true, true);
        }

        File ciDicDir = new File(FXConfig.ciDirPath);
        if (ciDicDir.exists()) {
            List<String> ciTerms = new ArrayList();
            for (File f : ciDicDir.listFiles()) {
                ciTerms.addAll(FileUtil.readFileByline(f.getAbsolutePath()));
            }
            ciDictionary = new Dictionary(ciTerms, true, true, false);
        }

        init = true;
    }

    public static List<IAnnotation> annotate(IDocument doc) {
        List<IAnnotation> annotations = new ArrayList<>();
        if (!FXConfig.isIncludeCustomDict()) {
            return annotations;
        }
        init();

        List<IAnnotation> anns = null;
        if (csDictionary != null) {
            anns = csDictionary.matchDoc(doc);

            for (IAnnotation ann : anns) {
                ann.label = "PHI";
            }
            annotations.addAll(anns);
        }
        if (ciDictionary != null) {
            anns = ciDictionary.matchDoc(doc);

            for (IAnnotation ann : anns) {
                ann.label = "PHI";
            }
            annotations.addAll(anns);
        }


        return annotations;
    }
}
