package edu.db.tool.deid.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import edu.db.tool.deid.document.IAnnotation;
import edu.db.tool.deid.document.IDocument;
import java.io.StringReader;
import org.xml.sax.InputSource;

/**
 *
 * @author Duy Bui
 */
public class I2B2Parser extends DefaultHandler {

    public List<String> hipaaPHIs = PHI.getHIPAAs();

    public I2B2Parser(List<String> types) {
        this.hipaaPHIs=types;
    }

    boolean isBeginTags = false;
    boolean isBeginText = false;

    IDocument currentRecord;

    String lastTag = null;

    IAnnotation currentPHI;

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        lastTag = qName;

        if (qName.equals("deIdi2b2") || qName.equals("NGRID_deId") || qName.equals("DOCUMENT")) {
            currentRecord = new IDocument();
        }

        if (qName.equals("TAGS")) {
            isBeginTags = true;
            return;
        }
        if (qName.equals("TEXT")) {
            isBeginText = true;
            return;
        }

        if (isBeginTags) {
            IAnnotation phi = new IAnnotation();
//			phi.type = qName;
            phi.label = attributes.getValue("TYPE");

            phi.id = attributes.getValue("id");
            phi.source = attributes.getValue("source");

            phi.start = Integer.parseInt(attributes.getValue("start"));
            phi.end = Integer.parseInt(attributes.getValue("end"));

            phi.setText(currentRecord.unTaggedText.substring(phi.start, phi.end));

            if (hipaaPHIs!=null && !hipaaPHIs.contains(phi.label)) {
                return;
            }
            
            if(phi.label.equals("Non-PHI")){
//                System.out.println("Non-PHI:"+phi);
                return;
            }
//            if(phi.source.contains("PersonKeys")||phi.source.contains("ManualKeys")){
//                return;
//            }
            

            currentRecord.goldPHIs.add(phi);
            currentRecord.goldPHIRegistry.addInterval(phi.start, phi.end, phi);

        }

    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("TAGS")) {
            isBeginTags = false;
        }
        if (qName.equals("TEXT")) {
            isBeginText = false;
            currentRecord.text=currentRecord.unTaggedText.toString();
        }

    }

    public void characters(char ch[], int start, int length) throws SAXException {
        String s = new String(ch, start, length);

        if (isBeginText) {
            currentRecord.unTaggedText.append(s);
        }

    }

    /**
     * @param args
     */
    public static IDocument parse(String filePath, List<String> types) {
        I2B2Parser handler = new I2B2Parser(types);
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(filePath, handler);

        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        File file = new File(filePath);

        handler.currentRecord.fileName = file.getName();
        handler.currentRecord.filePath = file.getAbsolutePath();

        return handler.currentRecord;
    }
    
    public static IDocument parseString(String content, List<String> types) throws Exception{
        I2B2Parser handler = new I2B2Parser(types);
        
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            InputSource is=new InputSource( new StringReader( content ));
//            is.setEncoding("UTF-8");
            saxParser.parse(is, handler);
                    

        


        return handler.currentRecord;
    }

    public static List<IDocument> importRecords(String folderPath, List<String> types) {
        List<IDocument> records = new ArrayList<IDocument>();
        File folder = new File(folderPath);
        for (File file : folder.listFiles()) {
            // System.out.print(file.getName());
            IDocument rec = parse(file.getAbsolutePath(), types);
            rec.fileName = file.getName();
            rec.filePath = file.getAbsolutePath();
            records.add(rec);
        }
        return records;
    }

}
