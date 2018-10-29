using DeIdClient.CS.Controller;
using DeIdClient.CS.Model;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Data;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Xml;
/**
 *
 * @author Duy Bui
 */
namespace DeIdClient.CS.Model
{
    public class Document
    {
        public string patientId;
        public string documentId;
        public string text;
        public ArrayList annotations = new ArrayList();

     
        public static void log(string s)
        {
            System.Diagnostics.Debug.WriteLine(s);
        }

        public static Document getAnnotatedDocumentWS(string inputPath,string outputPath,string docId)
        {
            string inputFilePath = Path.Combine(inputPath, docId);
            string outputFilePath= Path.Combine(outputPath, "phis."+docId);

            if (!File.Exists(inputFilePath) && !File.Exists(outputFilePath))
                return null;

            FileInfo inputFile = new FileInfo(inputFilePath);
            FileInfo outputFile = new FileInfo(outputFilePath);


            Document note = new Document();
            note.documentId = docId;
            if (outputFile.Exists)
                note.text = System.IO.File.ReadAllText(outputFilePath);
            else
                note.text = System.IO.File.ReadAllText(inputFilePath);

            return note;

        }
    }
}
