package edu.db.tool.deid.annotator;

import edu.db.tool.deid.document.IAnnotation;
import edu.db.tool.deid.document.IDocument;
import edu.db.tool.deid.utils.ResourceUtil;
import edu.db.tool.deid.utils.UABCRFUtil;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.logging.RedwoodConfiguration;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 *
 * @author Duy Bui
 */
public class Annotator_CRF_I2b22014_1304_LDS{

    public static AbstractSequenceClassifier<CoreLabel> fileClassifier;

    public static AbstractSequenceClassifier<CoreLabel> getFileClassifier() {
        if (fileClassifier == null) {
            try {
                RedwoodConfiguration.empty().capture(System.out).apply();

                InputStream in = ResourceUtil.class.getResourceAsStream("/edu/db/tool/deid/resources/crf/crf.binary.i2b22014.1304.lds.model.gz");

                fileClassifier = UABCRFUtil.importModelStream(new GZIPInputStream(in));

                RedwoodConfiguration.current().restore(System.out).apply();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return fileClassifier;
    }

    public static List<IAnnotation> annotate(IDocument doc) {
        return UABCRFUtil.getAnnotations(doc.getText(), getFileClassifier());
    }



    

}
