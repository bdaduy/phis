package edu.db.tool.deid.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import edu.db.tool.deid.document.IDocument;
import edu.db.tool.deid.document.ISection;
import edu.db.tool.deid.document.ISentence;
import edu.db.tool.deid.document.IToken;

import edu.stanford.nlp.ling.CoreAnnotations.CharacterOffsetBeginAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.CharacterOffsetEndAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import sun.security.ssl.Debug;

/**
 *
 * @author Duy Bui
 */
public class CallableTokenizationNoHeading implements Callable {

    public IDocument inputRecord;

    public CallableTokenizationNoHeading(IDocument inputRecord) {
        this.inputRecord = inputRecord;
    }

    @Override
    public Object call() throws Exception {
        splitSection(inputRecord);

        return this;
    }

    public List<ISection> splitSection(IDocument document) {

        StanfordCoreNLP corenlp = StanfordNLPSingle.getCoreNLP();

        document.sections = new ArrayList<>();
        ISection rootSection = new ISection(document, 0, 0, null, null);
        rootSection.titleSentence = null;
        document.sections.add(rootSection); // root heading;

        Annotation annotation = new Annotation(document.getText());

        corenlp.annotate(annotation);

        List<CoreMap> sentences = annotation.get(SentencesAnnotation.class);

        for (CoreMap sentence : sentences) {
            ISentence isentence = new ISentence(rootSection, -1, -1, null, null);
            rootSection.contentSentences.add(isentence);
            
            for (CoreLabel token : sentence.get(TokensAnnotation.class)) {

                int tstart = token.beginPosition();
                int tend = token.endPosition();

                IToken itoken = new IToken(isentence, tstart, tend, document.unTaggedText.substring(tstart, tend), null);
                itoken.normText = token.get(TextAnnotation.class);

                isentence.tokens.add(itoken);
                document.tokenRegistry.addInterval(itoken.start, itoken.end, itoken);

            }
            isentence.start=sentence.get(CharacterOffsetBeginAnnotation.class);
            isentence.end=sentence.get(CharacterOffsetEndAnnotation.class);
            isentence.linkToken();

        }
        

        return document.sections;
    }

}
