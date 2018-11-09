/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.db.tool.deid.annotator;

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
public class Annotator_Regex{

    public static List<Pattern> regexPatterns;

    public static List<Pattern> getRegexPatterns() {
        if (regexPatterns == null) {
            regexPatterns = new ArrayList<Pattern>();
            regexPatterns.add(Pattern.compile("\\b(\\d{3}-\\d{3}-\\d{4})\\b")); // phone
            regexPatterns.add(Pattern.compile("(\\(\\d{3}\\)[ -]\\d{3}-\\d{4})\\b")); // (205) 996-1958 
            
            regexPatterns.add(Pattern.compile("(?:ACCESSION NUMBER|ACCESSION|MRN|FIN|MR#|ID) *: *([A-Z0-9-]+)\\b")); // 
            
            regexPatterns.add(Pattern.compile("(?i)([a-z0-9!#$'*+-/=?^_`{|}~]+@[a-z0-9-]+\\.[a-z]{2,3})\\b")); //email
            regexPatterns.add(Pattern.compile("(?i)(?:https?:)?[a-z0-9-./\\\\]+\\\\.(?:com|org|net|mil|edu|gov|us)[a-z0-9-./\\\\]*")); //url


        }
        return regexPatterns;
    }

    public static List<IAnnotation> annotate(IDocument doc) {
        return RegexUtils.matchDoc(doc, getRegexPatterns());
    }

}
