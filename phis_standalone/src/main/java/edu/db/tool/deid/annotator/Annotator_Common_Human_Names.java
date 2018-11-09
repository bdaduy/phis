/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.db.tool.deid.annotator;

import edu.db.tool.deid.utils.HumanNames;
import edu.db.tool.deid.utils.RegexUtils;
import edu.db.tool.deid.document.IAnnotation;
import edu.db.tool.deid.document.IDocument;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author Duy Bui
 */
public class Annotator_Common_Human_Names {

    public static List<IAnnotation> annotate(IDocument doc) {
        List<IAnnotation> anns = new ArrayList();

        HumanNames.init();

        Pattern pattern = Pattern.compile("\\b(([A-Z][A-Za-z]+) *, *([A-Z][A-Za-z]+) +([A-Z]))\\b"); //John , Smith H
        List<IAnnotation> matchedAnns = RegexUtils.matchDoc(doc, pattern);
        int numOfGroup = 4;
        int groupCount = matchedAnns.size() / 4;
        for (int i = 0; i < groupCount; i++) {
            IAnnotation ann = matchedAnns.get(4 * i);

            IAnnotation lastNameAnn = matchedAnns.get(4 * i + 1);
            IAnnotation firstNameAnn = matchedAnns.get(4 * i + 2);
            IAnnotation middleInitialAnn = matchedAnns.get(4 * i + 3);

            if (HumanNames.firstNames.contains(firstNameAnn.getTextLC()) || HumanNames.lastNames.contains(lastNameAnn.getTextLC())) {
                if (middleInitialAnn.getText().equals("M") || middleInitialAnn.getText().equals("F")) {
                    anns.add(lastNameAnn);
                    anns.add(firstNameAnn);
                } else {
                    anns.add(ann);
                }
            }
        }

        pattern = Pattern.compile("\\b(([A-Z][A-Za-z]+) +([A-Z])\\. +([A-Z][A-Za-z]+))\\b");// John M. Smith
        matchedAnns = RegexUtils.matchDoc(doc, pattern);
        numOfGroup = 4;
        groupCount = matchedAnns.size() / 4;
        for (int i = 0; i < groupCount; i++) {
            IAnnotation ann = matchedAnns.get(4 * i);
            IAnnotation firstNameAnn = matchedAnns.get(4 * i + 1);
            IAnnotation middleInitialAnn = matchedAnns.get(4 * i + 2);
            IAnnotation lastNameAnn = matchedAnns.get(4 * i + 3);
            
            if (HumanNames.firstNames.contains(firstNameAnn.getTextLC()) || HumanNames.lastNames.contains(lastNameAnn.getTextLC())) {
                anns.add(ann);
            }
        }

        pattern = Pattern.compile("Drs?\\.? +([A-Z][A-Za-z]+)(?: +and +([A-Z][A-Za-z]+))?\\b");
        matchedAnns = RegexUtils.matchDoc(doc, pattern);
        for (int i = 0; i < matchedAnns.size(); i++) {
            IAnnotation lastNameAnn = matchedAnns.get(i);
            if (lastNameAnn.text != null) {
                if (HumanNames.lastNames.contains(lastNameAnn.getTextLC())) {
                    anns.add(lastNameAnn);
                }
            }

        }

        pattern = Pattern.compile("\\b(([A-Z][A-Za-z]+) *, *([A-Z][A-Za-z]+))\\b"); // John, Smith
        matchedAnns = RegexUtils.matchDoc(doc, pattern);
        numOfGroup = 3;
        groupCount = matchedAnns.size() / numOfGroup;
        for (int i = 0; i < groupCount; i++) {
            IAnnotation ann = matchedAnns.get(numOfGroup * i);

            IAnnotation lastNameAnn = matchedAnns.get(numOfGroup * i + 1);
            IAnnotation firstNameAnn = matchedAnns.get(numOfGroup * i + 2);

            if (HumanNames.firstNames.contains(firstNameAnn.getTextLC()) && HumanNames.lastNames.contains(lastNameAnn.getTextLC())) {
                anns.add(ann);
            }
        }

//        for (IAnnotation ann : anns) {
//            ann.label = "PHI";
//        }

        return anns;
    }

}
