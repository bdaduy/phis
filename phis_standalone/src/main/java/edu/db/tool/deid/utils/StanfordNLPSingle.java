/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.db.tool.deid.utils;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.logging.RedwoodConfiguration;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Duy Bui
 */
public class StanfordNLPSingle {

    private static StanfordCoreNLP corenlp;

    public static StanfordCoreNLP getCoreNLP() {
        if (corenlp == null) {
            RedwoodConfiguration.empty().capture(System.out).apply();
            Properties props = new Properties();
            props.setProperty("annotators", "tokenize, ssplit");
            corenlp = new StanfordCoreNLP(props);
            RedwoodConfiguration.current().restore(System.out).apply();
        }

        return corenlp;

    }

}
