package edu.db.tool.deid.document;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import edu.db.tool.deid.utils.I2B2Parser;
import edu.db.tool.deid.utils.CallablePreprocessRecord;
import edu.db.tool.deid.utils.CallableTokenizationNoHeading;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Duy Bui
 */
public class RecordCorpus {

    public List<IDocument> documents = new ArrayList<IDocument>();

    public Map<String, IDocument> hmDocId2Record = new HashMap<String, IDocument>();

    public String folderPath;

//    public EvalRecord<IToken> tokenEvalRecord = new EvalRecord<IToken>();

    public RecordCorpus(String folderPath) {
        this.folderPath = folderPath;
    }

    public void loadFolder(String[] Ids, List<String> types) {
        loadFolder(new ArrayList<String>(Arrays.asList(Ids)), -1, types);
    }

    public void loadFolder(int limit, List<String> types) {
        loadFolder(null, limit, types);
    }

    public void loadFolder(List<String> types) {
        loadFolder(null, -1, types);
    }

    public List<IDocument> getTrainings(int trainSize) {
        return documents.subList(0, trainSize);
    }

    public List<IDocument> getTestings(int trainSize) {
        return documents.subList(trainSize, documents.size());
    }
    public List<IDocument> getAll() {
        return documents;
    }

    public List<IDocument> getDocuments(String[] docIds) {
        List<IDocument> docs = new ArrayList();
        for (String id : docIds) {
            if (hmDocId2Record.containsKey(id)) {
                docs.add(hmDocId2Record.get(id));
            }
        }
        return docs;
    }

    public List<IDocument> getDocuments(List<String> docIds) {
        List<IDocument> docs = new ArrayList();
        for (String id : docIds) {
            docs.add(hmDocId2Record.get(id));
        }
        return docs;
    }

    public void addDoc(IDocument doc, String name) {
        documents.add(doc);
        hmDocId2Record.put(name, doc);
    }

    public void loadFolder(Collection<String> Ids, int limit, List<String> types) {
        if (folderPath == null) {
            return;
        }

        documents = new ArrayList<IDocument>();
        hmDocId2Record = new HashMap<String, IDocument>();

        File folder = new File(folderPath);
        System.out.println("loadFolder:" + folderPath);
        int count = 0;

        for (File file : folder.listFiles()) {
            if (file.getName().endsWith(".xml")) {
                if (Ids != null && !Ids.contains(file.getName())) {
                    continue;
                }

                IDocument rec = I2B2Parser.parse(file.getAbsolutePath(), types);

                rec.fileName = file.getName();

                addDoc(rec, rec.fileName);

                count++;
                if (limit > 0 && count >= limit) {
                    break;
                }
            }

        }

    }

//    //import from mongodb
//    public void addDocument(Document document) {
//        if (hmDocId2Record == null) {
//            hmDocId2Record = new HashMap<String, IDocument>();
//        }
//
//    }

    /* get documents */
    public IDocument getDocument(String id, List<String> types) {
        loadFolder(new String[]{id}, types);
        return hmDocId2Record.get(id);
    }

    public List<IDocument> getDocuments(String[] docIds, List<String> types) {
        loadFolder(docIds, types);
        Set<String> idSet = new HashSet<String>(Arrays.asList(docIds));
        List<IDocument> returnme = new ArrayList<IDocument>();
        for (IDocument doc : this.documents) {
            if (idSet.contains(doc.docId)) {
                returnme.add(doc);
            }
        }
        return returnme;
    }

    /* Partition */
    public void partitionSections() {

        ExecutorService executor = Executors.newFixedThreadPool(10);

        List<Callable<CallableTokenizationNoHeading>> threads = new ArrayList<>();

        for (int i = 0; i < documents.size(); i++) {
            threads.add(new CallableTokenizationNoHeading(documents.get(i)));
        }

        try {
            List<Future<CallableTokenizationNoHeading>> futures = executor.invokeAll(threads);

            for (Future<CallableTokenizationNoHeading> future : futures) {
                CallableTokenizationNoHeading thread = future.get();

            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        executor.shutdown();

    }

}
