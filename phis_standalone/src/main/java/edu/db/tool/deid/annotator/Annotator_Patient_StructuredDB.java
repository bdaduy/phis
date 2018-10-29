/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.db.tool.deid.annotator;

import static edu.db.tool.deid.annotator.Annotator_CRF_UAB_305_LDS.getFileClassifier;
import edu.db.tool.deid.utils.RegexUtils;
import edu.db.tool.deid.document.IAnnotation;
import edu.db.tool.deid.document.IDocument;
import edu.db.tool.deid.document.IPatient;
import edu.db.tool.deid.utils.UABCRFUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author DuyMac
 */
public class Annotator_Patient_StructuredDB {
    
    public static List<IPatient> query(String patientId){
       List<IPatient> records=new ArrayList<>();
       
       /*Implement logic for querying patient data by MRN/patientId*/
       
       return records;
    }

    
    public static List<IAnnotation> annotate(IDocument doc) {
        if(doc.patientId==null)
            return new ArrayList();

        List<IPatient> patientRecords = query(doc.patientId);

        
        if (patientRecords == null || patientRecords.isEmpty()) {
            return new ArrayList();
        }

        List<Pattern> patterns = new ArrayList<Pattern>();
        for (IPatient p : patientRecords) {

            patterns.add(Pattern.compile("(?i)(" + p.firstName + " +" + p.lastName + ")"));
            patterns.add(Pattern.compile("(?i)(" + p.lastName + " *, *" + p.firstName + ")"));

            if (p.middleName != null) {

                patterns.add(Pattern.compile("(?i)(" + p.lastName + " *, *" + p.firstName + " +" + p.middleName + ")"));
                patterns.add(Pattern.compile("(?i)(" + p.firstName + " +" + p.middleName + " +" + p.lastName + ")"));
                patterns.add(Pattern.compile("(?i)(" + p.firstName + " + +" + p.lastName + ")"));
                patterns.add(Pattern.compile("(?i)(" + p.firstName + " +" + p.middleName + ")"));

            }

            patterns.add(Pattern.compile("(?i)\\b(?:mrs|ms|mr)\\.? *(" + p.lastName + ")"));
            patterns.add(Pattern.compile("(0*" + p.patientId + ")"));
            patterns.add(Pattern.compile("(" + p.MRN + ")"));

            if (p.address1 != null) {
                patterns.add(Pattern.compile("(?i)\\b(" + p.address1 + ")\\b"));
            }

            if (p.address2 != null) {
                patterns.add(Pattern.compile("(?i)\\b(" + p.address2 + ")\\b"));
            }

            if (p.city != null) {
                patterns.add(Pattern.compile("(?i)\\b(" + p.city + ")\\b"));
            }
            if (p.state != null) {
                if (p.state.length() > 2) {
                    patterns.add(Pattern.compile("\\b(" + p.state + ")\\b"));
                } else {
                    patterns.add(Pattern.compile("\\b(" + p.state + ") +\\d+"));
                }

            }

        }

        return RegexUtils.matchDoc(doc, patterns);
        
    }
}
