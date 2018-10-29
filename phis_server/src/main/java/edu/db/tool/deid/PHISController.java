package edu.db.tool.deid;


import edu.db.tool.deid.model.RequestLookupPHI;
import edu.db.tool.deid.model.JSonAnnotation;
import edu.db.tool.deid.model.ResponseLookupPHI;
import edu.db.tool.deid.annotator.Annotator;
import edu.db.tool.deid.utils.ClassInit;
import edu.db.tool.deid.document.IAnnotation;
import edu.db.tool.deid.document.IDocument;
import edu.db.tool.deid.fx.FXConfig;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/phis_saas")
public class PHISController {

    private final static Logger logger = Logger.getLogger(PHISController.class.getName());
    
    public static String RootDir="C:\\bdaduy\\Codes\\UAB\\OpenDeid\\samples";

    @PostConstruct
    public void init() {
        FXConfig.dontIncludeCustomDict();
        ClassInit.Init();
    }

    @RequestMapping(value = "/lookupPHI", method = RequestMethod.POST)
    public ResponseLookupPHI lookupPHI(@RequestBody RequestLookupPHI request) {
        
        request.Decrypt("ChangeMe");
        
        if(request.method.equalsIgnoreCase("all"))
            FXConfig.switchToAllMode();
        else if (request.method.equalsIgnoreCase("lds"))
            FXConfig.switchToLDSMode();
        
        IDocument doc=IDocument.createDoc(request.patientId,request.text);
        
        Annotator.annotate(doc);
        
//        List<IAnnotation> anns = AllAnnotator.annotate(request.patientId, request.text);
        
        List<IAnnotation> anns=doc.getPHIAnns();
        
        ResponseLookupPHI res = new ResponseLookupPHI(request);
        for (IAnnotation ann : anns) {
            res.annotations.add(JSonAnnotation.convert(ann));
        }
        return res;
    }

}
