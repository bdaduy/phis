/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.db.tool.deid.fx;

import edu.db.tool.deid.document.IAnnotation;
import edu.db.tool.deid.utils.FileUtil;
import edu.db.tool.deid.utils.ResourceUtil;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import static javafx.scene.input.KeyCode.I;

/**
 *
 * @author Duy Bui
 */
public class FXConfig {

    private final static Logger logger = Logger.getLogger(FXMLDocumentController.class.getName());

    public enum DEIDMODE {
        ALL, LDS
    };

    public enum REDACTMODE {
        GENERIC_TAG, DUP_CHARACTERS
    };
    public static DEIDMODE runmode = DEIDMODE.LDS;

    public static REDACTMODE redactmode = REDACTMODE.GENERIC_TAG;
    public static String genericTag = "[PHI]";
    public static String dupChars = "X";

    public static boolean isAllMode() {
        return runmode == DEIDMODE.ALL;
    }

    public static boolean isLDSMode() {
        return runmode == DEIDMODE.LDS;
    }

    public static void switchToLDSMode() {
        runmode = DEIDMODE.LDS;
    }

    public static void switchToAllMode() {
        runmode = DEIDMODE.ALL;
    }

    private static boolean includeCustomDict = true;

    public static boolean isIncludeCustomDict() {
        return includeCustomDict;
    }

    public static void dontIncludeCustomDict() {
        includeCustomDict = false;
    }

    public static void includeCustomDict() {
        includeCustomDict = true;
    }

    private static boolean logStDOut = false;
    public static void enableSTDLogging(){
        logStDOut=true;
    }
    public static void disableSTDLogging(){
        logStDOut=false;
    }

    public static String jarDirPath;
    public static String configDirPath;
    public static String customDictDirPath;
    public static String csDirPath;
    public static File csDir;
    public static String ciDirPath;
    public static File ciDir;
    public static String logDirPath;
    public static String logFilePath;
    public static String stdOutFilePath;
    public static String stdErrFilePath;

    public static void initFolder() {
        try {
            jarDirPath = new File(".").getCanonicalPath().toString();

            configDirPath = jarDirPath + "/config";
            logDirPath = configDirPath + "/log";
            customDictDirPath = configDirPath + "/custom_dict";
            csDirPath = customDictDirPath + "/case_sensitive";
            ciDirPath = customDictDirPath + "/case_insensitive";

            logFilePath = logDirPath + "/log.txt";
            stdOutFilePath = logDirPath + "/stdOut.txt";
            stdErrFilePath = logDirPath + "/stdErr.txt";

            FXConfig.includeCustomDict();
            File configDir = new File(configDirPath);
            configDir.mkdir();

            File customDictDir = new File(customDictDirPath);
            if (!customDictDir.exists()) {
                customDictDir.mkdir();
            }
            csDir = new File(csDirPath);
            if (!csDir.exists()) {
                csDir.mkdir();
                copyResFile("/edu/db/tool/deid/resources/custom_dict", "cs.terms.txt", csDirPath);
//                copyResFile("/edu/db/tool/deid/resources/custom_dict", "cs.uab.txt", csDirPath);
//                copyResFile("/edu/db/tool/deid/resources/custom_dict", "cs.professions.txt", csDirPath);
            }
            ciDir = new File(ciDirPath);
            if (!ciDir.exists()) {
                ciDir.mkdir();
                copyResFile("/edu/db/tool/deid/resources/custom_dict", "ci.terms.txt", ciDirPath);
//                copyResFile("/edu/db/tool/deid/resources/custom_dict", "ci.uab.txt", ciDirPath);
//                copyResFile("/edu/db/tool/deid/resources/custom_dict", "ci.professions.txt", ciDirPath);

            }

            File logDir = new File(logDirPath);
            logDir.mkdir();
            if (logStDOut) {
                System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(stdOutFilePath)), true));
                System.setErr(new PrintStream(new BufferedOutputStream(new FileOutputStream(stdOutFilePath)), true));
            }
            FileHandler fileTxt = new FileHandler(logFilePath);
            fileTxt.setFormatter(new SimpleFormatter());
            logger.addHandler(fileTxt);

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }

    }

    public static void copyResFile(String classpath, String name, String outputDir) {
        String text = ResourceUtil.getText(classpath + "/" + name);
        FileUtil.writeFile(text, outputDir + "/" + name, false);
    }

    public static String getTextRedactedText(String text, List<IAnnotation> phis) {
        StringBuffer buf = new StringBuffer(text);
        Collections.sort(phis, new Comparator<IAnnotation>() {
            @Override
            public int compare(IAnnotation o1, IAnnotation o2) {
                return o1.start - o2.start;
            }
        });

        String replaceString = "[REDACTED]";
        char replaceChar = 'X';

        if (redactmode == REDACTMODE.GENERIC_TAG) {
            if (genericTag.length() > 0 && genericTag.length() < 50) {
                replaceString = genericTag;
            }
        } else if (redactmode == REDACTMODE.DUP_CHARACTERS) {
            String c = dupChars;
            if (c.length() > 0) {
                replaceChar = c.charAt(0);
            }
        }

        int offset = 0;
        for (IAnnotation phi : phis) {

            if (redactmode == REDACTMODE.DUP_CHARACTERS) {
                char[] chars = new char[phi.getText().length()];
                Arrays.fill(chars, replaceChar);
                replaceString = new String(chars);
            }

            buf.replace(phi.start + offset, phi.end + offset, replaceString);
            offset = offset - (phi.end - phi.start) + replaceString.length();
        }

        return buf.toString();
    }
}
