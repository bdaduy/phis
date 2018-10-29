package edu.db.tool.deid.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.db.tool.deid.document.IAnnotation;
import edu.db.tool.deid.document.IDocument;
import edu.db.tool.deid.document.IToken;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.Triple;
import java.io.InputStream;

/**
 *
 * @author Duy Bui
 */
public class UABCRFUtil {

    public static void labelTokensGold(List<IDocument> docs) {
        labelTokensGold(docs, "BINARY");
    }

    public static void labelTokensGold(List<IDocument> docs, String abstractType) {
        labelTokensGold(docs, abstractType, false);
    }

    public static String abstractPHI(String label, String abstracType) {
        if (label.equals("O")) {
            return label;
        }

        if (abstracType.equals("BINARY")) {
            return "PHI";
        }

        if (abstracType.equals("CATEGORY")) {
            return PHI.hmSubCat2Cat.get(label);
        }

        return label;
    }

    public static void labelTokensGold(List<IDocument> docs, String abstractType, boolean printDebug) {
        for (IDocument doc : docs) {

            for (IToken token : doc.getTokens()) {
                List<IAnnotation> phiAnns = doc.goldPHIRegistry.get(token.start, token.end);

                String label = "O";
                String source = null;

                for (IAnnotation ann : phiAnns) {

                    label = abstractPHI(ann.label, abstractType);
                    source = ann.label;
                }

                token.goldLabel = label;
                token.source = label;

            }
        }
    }

    public static void labelTokensTest(List<IDocument> docs) {
        for (IDocument doc : docs) {
            for (IToken token : doc.getTokens()) {
                List<IAnnotation> anns = doc.testTokenRegistry.get(token.start, token.end);
                String label = "O";
                if (!anns.isEmpty()) {
                    label = anns.get(0).label;
                }
                token.testLabel = label;
            }
        }
    }
    


    /* CRF model */
    public static AbstractSequenceClassifier<CoreLabel> importModelFile(String modelFilePath) {

        AbstractSequenceClassifier<CoreLabel> classifier = null;
        try {

            Properties props = new Properties();
            // props.setProperty("tokenizerFactory",
            // "edu.stanford.nlp.process.WhitespaceTokenizer");
            classifier = CRFClassifier.getClassifier(modelFilePath, props);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classifier;
    }

    public static AbstractSequenceClassifier<CoreLabel> importModelStream(InputStream stream) {

        AbstractSequenceClassifier<CoreLabel> classifier = null;
        try {
            Properties props = new Properties();
            // props.setProperty("tokenizerFactory",
            // "edu.stanford.nlp.process.WhitespaceTokenizer");
            classifier = CRFClassifier.getClassifier(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classifier;
    }

    public static List<IAnnotation> getAnnotations(String text, AbstractSequenceClassifier<CoreLabel> classifier) {
        List<IAnnotation> anns = new ArrayList<>();
        List<Triple<String, Integer, Integer>> triples = classifier.classifyToCharacterOffsets(text);
        for (Triple<String, Integer, Integer> trip : triples) {
            int start = trip.second();
            int end = trip.third();
            String label = trip.first();
            String annText = text.substring(start, end);

            IAnnotation ann = new IAnnotation(start, end, annText, "CRF");
            ann.label = label;

            anns.add(ann);

        }
        return anns;
    }

}
