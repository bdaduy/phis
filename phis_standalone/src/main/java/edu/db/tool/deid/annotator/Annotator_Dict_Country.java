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
public class Annotator_Dict_Country {

    private static Dictionary countryDict;

    public static Dictionary getCountryDict() {
        if (countryDict == null) {
            List<String> terms= ResourceUtil.getTextByLine("/edu/db/tool/deid/resources/dict/Countries.txt");
            countryDict = new Dictionary(terms, true, true, false);
        }
        return countryDict;
    }

    public static List<IAnnotation> annotate(IDocument doc) {
        return getCountryDict().matchDoc(doc);
    }

}
