/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.db.tool.deid.utils;

import edu.db.tool.deid.fx.FXMLDocumentController;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Duy Bui
 */
public class ResourceUtil {

    private final static Logger logger = Logger.getLogger(ResourceUtil.class.getName());

    public static String NL = System.getProperty("line.separator");

    public static String getAbsolutePath(String path) {
        StringBuilder sb = new StringBuilder();
        File file = new File(ResourceUtil.class.getResource(path).getFile());
        return file.getAbsolutePath();
    }

    public static String getText(String path) {
        StringBuilder sb = new StringBuilder();

        InputStream in = ResourceUtil.class.getResourceAsStream(path);
        if(in==null){
            logger.log(Level.SEVERE, "Couldn't find resource: "+path);
            return "";
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(NL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(ResourceUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return sb.toString();
    }

    public static List<String> getTextByLine(String path) {
        List<String> lines = new ArrayList<>();
        InputStream in = ResourceUtil.class.getResourceAsStream(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(ResourceUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lines;
    }

}
