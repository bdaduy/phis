package edu.db.tool.deid.training;

import edu.db.tool.deid.document.RecordCorpus;
import edu.db.tool.deid.document.IDocument;
import edu.db.tool.deid.document.IToken;
import edu.db.tool.deid.utils.TimeLog;
import edu.db.tool.deid.utils.UABCRFUtil;
import java.util.ArrayList;

import java.util.List;

/**
 *
 * @author Duy Bui
 */
public class TrainClassifiersBinaryI2b22014_1304_AgeDateZip {

    public static String MODELS_DIR = "Path to Model Directory";

    public static String TRAIN_DIR = "Path to i2b2 training corpus";

    public static String OUTPUT_DIR = MODELS_DIR;

    public static CRFConfig config = new CRFConfig(MODELS_DIR, "BINARY_I2B22014_1304_AGEDATEZIP");

    public static void main(String[] args) {

        train(config);

    }

    public static List<String> getTypes() {

        List<String> phiTypes = new ArrayList<>();
        phiTypes.add("AGE");
        phiTypes.add("ZIP");
        phiTypes.add("DATE");

        return phiTypes;
    }

    public static void train(CRFConfig config) {
        RecordCorpus recordSet = new RecordCorpus(TRAIN_DIR);
        recordSet.loadFolder(getTypes());
        recordSet.partitionSections();
        List<IDocument> docs = recordSet.documents;

        System.out.println("\nTrain:" + config.experimentName);
        UABCRFUtil.labelTokensGold(docs);

        config.prepareDirBinary(docs, "/edu/db/tool/deid/resources/crf/stanford.ner.prop", null);

        long base = TimeLog.getCurrent();

        try {
            System.out.println("\nStart Training:");
            edu.stanford.nlp.ie.crf.CRFClassifier.main(new String[]{"-prop", config.getTrainingPropFile()});
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("Elapsed Time: " + TimeLog.getSpanMin(base));
    }

}
