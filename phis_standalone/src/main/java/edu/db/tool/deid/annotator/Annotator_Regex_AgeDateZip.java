/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.db.tool.deid.annotator;

import edu.db.tool.deid.utils.RegexUtils;
import edu.db.tool.deid.document.IAnnotation;
import edu.db.tool.deid.document.IDocument;
import edu.db.tool.deid.fx.FXConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author dbui
 */
public class Annotator_Regex_AgeDateZip{

    public static List<Pattern> regexPatterns;

    public static List<Pattern> getRegexPatterns() {
        if (regexPatterns == null) {
            regexPatterns = new ArrayList<Pattern>();
            regexPatterns.add(Pattern.compile("[MF] +((?:10|11|12|[1-9])?[0-9]) +Years")); // age
            regexPatterns.add(Pattern.compile("\\b[aA]ge *[: ] *((?:10|11|12|[1-9])?[0-9])\\b")); // age
            
            regexPatterns.add(Pattern.compile("\\b((?:[0-9]{2})?[0-9]{2}[-/][0-3]?[0-9][-/][0-3]?[0-9])\\b")); // date
            regexPatterns.add(Pattern.compile("\\b([0-3]?[0-9][-/][0-3]?[0-9][-/](?:[0-9]{2})?[0-9]{2})\\b")); // date
            

        }
        return regexPatterns;
    }

    public static List<IAnnotation> annotate(IDocument doc) {
        if(FXConfig.isLDSMode())
            return new ArrayList();
        return RegexUtils.matchDoc(doc, getRegexPatterns());
    }

}
