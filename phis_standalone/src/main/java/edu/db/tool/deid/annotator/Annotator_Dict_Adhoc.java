/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.db.tool.deid.annotator;

import edu.db.tool.deid.document.IAnnotation;
import edu.db.tool.deid.utils.Dictionary;
import edu.db.tool.deid.document.IDocument;
import edu.db.tool.deid.utils.ResourceUtil;
import java.util.List;

/**
 *
 * @author Duy Bui
 */
public class Annotator_Dict_Adhoc {

    private static Dictionary caseSensitiveDict;

    public static Dictionary getCaseSensitiveDict() {
        if (caseSensitiveDict == null) {
            List<String> terms= ResourceUtil.getTextByLine("/edu/db/tool/deid/resources/dict/Countries.txt");
            caseSensitiveDict = new Dictionary(terms, true, true,true);
        }
        return caseSensitiveDict;
    }
    
    private static Dictionary caseInsensitiveDict;

    public static Dictionary getCaseInsensitiveDict() {
        if (caseInsensitiveDict == null) {
            List<String> terms= ResourceUtil.getTextByLine("/edu/db/tool/deid/resources/dict/Countries.txt");
            caseInsensitiveDict = new Dictionary(terms, true, true,true);
        }
        return caseInsensitiveDict;
    }

    public static List<IAnnotation> annotate(IDocument doc) {
        return getCaseSensitiveDict().matchDoc(doc);
    }

}
