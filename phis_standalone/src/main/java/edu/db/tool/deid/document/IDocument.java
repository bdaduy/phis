package edu.db.tool.deid.document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import edu.db.tool.deid.document.IAnnotation;
import edu.db.tool.deid.document.ISection;
import edu.db.tool.deid.document.IToken;
import edu.db.tool.deid.intervaltree.IntervalTree;
import edu.db.tool.deid.utils.CallablePreprocessRecord;
import edu.db.tool.deid.utils.CallableTokenizationNoHeading;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author Duy Bui
 */
public class IDocument implements Serializable {

    public String patientId;
    public String docId;
    public String docType;
    public String fileName;
    public String filePath;

    public StringBuffer unTaggedText = new StringBuffer();
    public String text;

    /* Annotation Training */
    public IntervalTree<IAnnotation> goldPHIRegistry = new IntervalTree<>();
    public List<IAnnotation> goldPHIs = new ArrayList<>();

    /* Annotation Testing */
    public IntervalTree<IAnnotation> testTokenRegistry = new IntervalTree<IAnnotation>();
    public IntervalTree<IAnnotation> testAnnotationRegistry = new IntervalTree<IAnnotation>();

    /* Document Structure */
    public List<ISection> sections;
    public IntervalTree<IAnnotation> tokenRegistry = new IntervalTree<IAnnotation>();

    // evaluation
//    public EvalRecord<IToken> tokenEvalRecord = new EvalRecord<IToken>();

    public void addPHI(List<IAnnotation> anns){
        for (IAnnotation ann:anns){
            List<IAnnotation> tokens=tokenRegistry.get(ann.start, ann.end);
            for(IAnnotation token:tokens){
                token.label="PHI";
            }
        }
    }
    
    public String getText() {
        if (text == null) {
            text = unTaggedText.toString();
        }
        return text;
    }

    public String getTextPHITagged(List<IAnnotation> phis) {
        StringBuffer buf = new StringBuffer(unTaggedText);

        int offset = 0;
        for (IAnnotation phi : phis) {
            String replaceString = "[" + phi.label + ":" + phi.getText() + "]";
            buf.replace(phi.start + offset, phi.end + offset, replaceString);
            offset = offset - (phi.end - phi.start) + replaceString.length();
        }

        return buf.toString();
    }

    public String getGoldPHITagged() {
        return getTextPHITagged(goldPHIs);
    }

    public String getDebugContext(int start, int end, int windows) {
        String left = unTaggedText.substring(Math.max(0, start - windows), start).replaceAll("\n","\\\\n");
        String right = unTaggedText.substring(end, Math.min(unTaggedText.length(), end + windows)).replaceAll("\n","\\\\n");
        return  left + "[" + unTaggedText.substring(start, end).replaceAll("\n","\\\\n") + "]" + right;
    }


    /* Tokens */
    public List<IToken> getTokens() {
        List<IToken> tokens = new ArrayList<>();

        for (ISection sec : sections) {
            tokens.addAll(sec.getAllTokens());
       }
        return tokens;
    }
    public List<IAnnotation> getPHIAnns(){
        List<IAnnotation> annotations = new ArrayList<>();

        for (ISection sec : sections) {
            List<IToken> tokens=sec.getAllTokens();
            IAnnotation prevAnnotation=null;
            for(IToken token:tokens){
                if("PHI".equals(token.label)){
                    if(prevAnnotation!=null && token.start-prevAnnotation.end<2){
                        prevAnnotation.end=token.end;
                        prevAnnotation.text=text.substring(prevAnnotation.start, prevAnnotation.end);
                    } else{
                        prevAnnotation=new IAnnotation(token.start,token.end,token.text,null);
                        annotations.add(prevAnnotation);
                    }
                } else{
                    prevAnnotation=null;
                }
            }
            
       }
        return annotations;
    }

    public void partitionSection() {

        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Callable<CallableTokenizationNoHeading>> threads = new ArrayList<>();
        threads.add(new CallableTokenizationNoHeading(this));

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
    public static IDocument createDoc(String content) {
        return createDoc(null, content);
    }
    

    public static IDocument createDoc(String patientId,String content) {
        IDocument doc = new IDocument();
        doc.patientId=patientId;
        doc.text = content;
        doc.unTaggedText = new StringBuffer(content);
        return doc;
    }

    public String toString(){
        StringBuffer buf=new StringBuffer();
        buf.append("Doc ID:"+docId+"\n");
        buf.append("Text Size:"+text.length()+"\n");
         buf.append(text+"\n");
        buf.append("Gold Ann Size:"+goldPHIs.size()+"\n");
        for(IAnnotation ann:goldPHIs){
            buf.append(ann+"\n");
        }
        return buf.toString();
    }
    
    public String getTokenLabeling(){
        StringBuffer buf=new StringBuffer();
        for(IToken token:getTokens()){
            buf.append(token.getText()+"\t"+token.goldLabel+"\t"+token.testLabel+"\n");
        }
        return buf.toString();
    }
    // /*Section Test*/
    // public void printSection(){
    // for(ISection sec:sections){
    // Debug.print(sec.titleSentence);
    // }
    // }
    // public void printSectionTokens(){
    // for(ISection sec:sections){
    // for(IToken token:sec.titleSentence.tokens){
    // Debug.print(token.getText());
    // }
    //
    // }
    // }
}
