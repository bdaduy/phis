using DeIdClient.CS.Controller;
using DeIdClient.CS.Model;
using System;
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Xml;
using System.Web.Script.Serialization;
using System.Text.RegularExpressions;
using System.Diagnostics;
using System.Web.Helpers;
/**
*
* @author Duy Bui
*/
namespace DeIdClient
{
    public partial class MainGui : Form
    {

        public AnnotationList annotationList;
        public Document currentDoc;
        AppConfig appConfig=null;


        public MainGui()
        {

            appConfig = AppConfig.Get();

            InitializeComponent();

            annotationList = new AnnotationList(this);

            //heading

            InititalzeVariable();
            new MainContextMenu(this);

        }
        public string getSubString(int start, int end)
        {
            return textArea.Text.Substring(start, end - start);
        }
        public void createRow(ArrayList annotations)
        {
            if (annotations == null)
                return;
            foreach (Annotation ann in annotations)
            {
                createRow(ann);
            }
        }
        public void createRow(Annotation ann)
        {
            DataGridViewRow row = (DataGridViewRow)dataGridView1.Rows[0].Clone();
            row.Cells[0].Value = ann.start;
            row.Cells[1].Value = ann.end;
            row.Cells[2].Value = ann.text;
            dataGridView1.Rows.Add(row);
        }
        public void removePHIFromEditor(Annotation ann)
        {
            textArea.SelectionStart = ann.start;
            textArea.SelectionLength = ann.end - ann.start;
            textArea.SelectionBackColor = Color.White;
            textArea.SelectionColor = Color.Black;
        }
        public void removePHIFromEditor(ArrayList annotations)
        {
            foreach (Annotation ann in annotations)
            {
                removePHIFromEditor(ann);
            }
        }
        public void updatePHIFromEditor(ArrayList annotations)
        {
            markPHI(annotations);
        }
        private void dataGridView1_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
            EventHandler.dataGridView1_CellContentClick(this, sender, e);
        }




        public void refreshAll()
        {
            refreshEditor();
            refreshTable();
        }

        public void refreshEditor()
        {
            this.panel1.Focus();

            textArea.SelectionStart = 0;
            textArea.SelectionLength = textArea.Text.Length;
            textArea.SelectionBackColor = Color.White;
            textArea.SelectionColor = Color.Black;
            markPHI(annotationList.annotations);
        }


        public void refreshTable()
        {
            dataGridView1.Rows.Clear();
            dataGridView1.Refresh();

            
            annotationList.SortPosition();
            

            createRow(annotationList.annotations);
        }

        public void markPHI(ArrayList annotations)
        {
            if (annotations == null)
                return;
            foreach (Annotation ann in annotations)
            {
                markPHI(ann);
            }
        }

        public void markPHI(Annotation ann)
        {
            textArea.SelectionStart = ann.start;
            textArea.SelectionLength = ann.end - ann.start;
            textArea.SelectionBackColor = Color.Yellow;

        }


        public string InputPath
        {
            get
            {
                return textBoxInputFolder.Text;
            }
        }
        public string OutputPath
        {
            get
            {
                return textBoxOutputFolder.Text;
            }
        }

        //private ArrayList corpusRecordIds;
        public ArrayList getCorpusRecordIds()
        {
            ArrayList corpusRecordIds = new ArrayList();


            DirectoryInfo inputDirInfo = new DirectoryInfo(InputPath);
            if (!inputDirInfo.Exists)
            {
                MessageBox.Show("Input Folder Not Exist!", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return corpusRecordIds;
            }

            FileInfo[] files = inputDirInfo.GetFiles();

            foreach (FileInfo file in files)
            {
                corpusRecordIds.Add(file.Name);
            }

            return corpusRecordIds;
        }
        public int getCorpusIndex(string id)
        {
            int index = getCorpusRecordIds().IndexOf(id);
            return index + 1;
        }

        private void buttonLoad_Click(object sender, EventArgs e)
        {
            comboBoxNotes.Items.Clear();
            ArrayList recordIds = getCorpusRecordIds();

            int minIndex = 1;
            int maxIndex = recordIds.Count;

            minIndex = Math.Max(1, minIndex);
            maxIndex = Math.Min(maxIndex, recordIds.Count);


            for (int i = minIndex - 1; i < maxIndex; i++)
                comboBoxNotes.Items.Add(getCorpusIndex(recordIds[i].ToString()) + "," + recordIds[i]);

            if (comboBoxNotes.Items.Count > 0)
                comboBoxNotes.SelectedIndex = 0;
            else
            {
                comboBoxNotes.Text = "";
            }
        }
        public String getCurrentDocId()
        {
            if (comboBoxNotes.SelectedIndex >= 0)
            {
                string selectedText = comboBoxNotes.Items[comboBoxNotes.SelectedIndex].ToString().Split(',')[1];

                return selectedText;
            }
            else
                return null;
        }
        public void reloadDocument()
        {
            String selectedDocID = getCurrentDocId();
            if (selectedDocID != null)
            {

                FileInfo fileInfo = new FileInfo(Path.Combine(InputPath, selectedDocID));
                if (!fileInfo.Exists)
                {
                    return;
                }

                currentDoc = Document.getAnnotatedDocumentWS(InputPath, OutputPath, selectedDocID);

                if (currentDoc != null)
                {
                    textBoxPatientId.Text = currentDoc.patientId;
                    textArea.Text = currentDoc.text;
                    annotationList = new AnnotationList(currentDoc.annotations, this);

                    refreshAll();

                    textArea.Select(0, 0);
                    textArea.Focus();


                }
            }
            else
            {
                currentDoc = null;
            }

        }

        

        private void ComboBoxNotes_SelectedIndexChanged(object sender, EventArgs e)
        {
            reloadDocument();
        }

        private void buttonNext_Click(object sender, EventArgs e)
        {
            if (comboBoxNotes.Items.Count > 0)
            {
                comboBoxNotes.SelectedIndex = (comboBoxNotes.SelectedIndex + 1) % comboBoxNotes.Items.Count;
            }

        }

        private void buttonBack_Click(object sender, EventArgs e)
        {
            if (comboBoxNotes.Items.Count > 0)
            {
                comboBoxNotes.SelectedIndex = (comboBoxNotes.Items.Count + comboBoxNotes.SelectedIndex - 1) % comboBoxNotes.Items.Count;
            }
        }

        public static void log(string s)
        {
            System.Diagnostics.Debug.WriteLine(s);
        }


        private void AnnotateService()
        {
            try
            {
                if (currentDoc == null)
                {
                    if (textArea.Text == null)
                        return;

                    currentDoc = new Document();
                    currentDoc.documentId = "Temp ID";
                    
                }

                currentDoc.text = textArea.Text;
                annotationList.clear();

                using (WebClient wc = new WebClient())
                {
                    wc.Headers[HttpRequestHeader.ContentType] = "application/json";
                    RequestLookupPHI request = new RequestLookupPHI();
                    request.documentId = currentDoc.documentId;
                    request.patientId = currentDoc.patientId;
                    if (radioButtonALL.Checked)
                        request.method = "all";
                    else if (radioButtonLDS.Checked)
                        request.method = "lds";
                    request.text = currentDoc.text;

                    request.Encrypt(appConfig.Password);

                    var message = new JavaScriptSerializer().Serialize(request);

                    string Url = "http://"+appConfig.IPAddress+":"+appConfig.Port+"/phis_saas/lookupPHI";

                    string response = wc.UploadString(Url, message);

                    dynamic obj = Json.Decode(response);

                    if (obj.annotations != null)
                    {
                        foreach (var ann in obj.annotations)
                        {
                            Annotation newann = new Annotation(ann.start, ann.end, currentDoc.text.Substring(ann.start,ann.end-ann.start));
                            annotationList.Add(newann);
                        }
                    }
                }
                refreshAll();

            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex.Message);
                MessageBox.Show(string.Format("Unable to connect to remote service: {0}({1})",appConfig.IPAddress,appConfig.Port), "Connection Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
            }
        }


        private void buttonSave_Click(object sender, EventArgs e)
        {
            string currentDocId = getCurrentDocId();
            string text = textArea.Text;

            if (currentDocId == null)
            {
                if(text!=null && text.Trim() != "")
                {
                    currentDocId = DateTime.Now.ToString("yyyyMMdd-HHMMss") + ".txt";
                    comboBoxNotes.Items.Clear();
                    comboBoxNotes.Text = currentDocId;
                }
                    
            }
            if (currentDocId != null)
            {

                DirectoryInfo outputDirInfo = new DirectoryInfo(OutputPath);
                if (!outputDirInfo.Exists)
                {
                    MessageBox.Show("Output Folder Not Exist!", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    return;
                }


                string outputFilePath = Path.Combine(OutputPath, "phis." + currentDocId);
                System.IO.File.WriteAllText(outputFilePath, text);
            }
        }


        

        private void InititalzeVariable()
        {
            textBoxKeywordCaseSensitive.Text = string.Join("\r\n", appConfig.CaseSensitiveTerms);
            textBoxKeywordCaseInsensitive.Text = string.Join("\r\n", appConfig.CaseInSensitiveTerms);
            textBoxInputFolder.Text = appConfig.InputFolder;
            textBoxOutputFolder.Text = appConfig.OutputFolder;

        }



        private void buttonRun_Click(object sender, EventArgs e)
        {
            AnnotateService();
            AnnotateKeywords();
        }

        private void AnnotateKeywords()
        {
            List<string> caseInsensitiveKeys = new List<string>();
            List<string> caseSensitiveKeys = new List<string>();

            for (int i = 0; i < textBoxKeywordCaseInsensitive.Lines.Length; i++)
            {
                string text = textBoxKeywordCaseInsensitive.Lines[i].Trim();
                if (text.Length > 0)
                {
                    caseInsensitiveKeys.Add("\\b"+text+"\\b");
                }
            }
            DirectoryInfo dirInfo = new DirectoryInfo("config/custom_dict/case_insensitive");
            if (dirInfo.Exists)
            {
                foreach (FileInfo fileInfo in dirInfo.GetFiles())
                {
                    foreach(string line in System.IO.File.ReadAllLines(fileInfo.FullName))
                    {
                        if(line.Length>0)
                            caseInsensitiveKeys.Add(line);
                    }
                }
            }

            for (int i = 0; i < textBoxKeywordCaseSensitive.Lines.Length; i++)
            {
                string text = textBoxKeywordCaseSensitive.Lines[i].Trim();
                if (text.Length > 0)
                {
                    caseSensitiveKeys.Add("\\b" + text + "\\b");
                }
            }
            dirInfo = new DirectoryInfo("config/custom_dict/case_sensitive");
            if (dirInfo.Exists)
            {
                foreach (FileInfo fileInfo in dirInfo.GetFiles())
                {
                    foreach (string line in System.IO.File.ReadAllLines(fileInfo.FullName))
                    {
                        if (line.Length > 0) caseSensitiveKeys.Add(line);
                    }
                }
            }


            string regex = string.Join("|", caseInsensitiveKeys);
            MatchCollection mc = Regex.Matches(textArea.Text, regex,RegexOptions.IgnoreCase);
            foreach (Match m in mc)
            {
                Annotation ann = new Annotation(m.Index, m.Index + m.Length, m.Value);
                annotationList.Add(ann);
            }
            regex = string.Join("|", caseSensitiveKeys);
            mc = Regex.Matches(textArea.Text, regex);
            foreach (Match m in mc)
            {
                Annotation ann = new Annotation(m.Index, m.Index + m.Length, m.Value);
                annotationList.Add(ann);
            }

            refreshAll();
        }



        private void buttonScrub_Click(object sender, EventArgs e)
        {
            ArrayList annotations = new ArrayList(annotationList.annotations);
            annotations.Sort(new PositionComparer());

            StringBuilder sb = new StringBuilder(textArea.Text);

            int offset = 0;
            foreach (Annotation phi in annotations)
            {

                string replaceString = "[PHI]";
                if(radioButtonGenericTag.Checked)
                {
                    replaceString= textBoxGenericTag.Text.Trim();
                } else if (radioButtonRepeatedCharacter.Checked)
                {
                    replaceString = new String(textBoxRepeatedCharacter.Text[0], phi.text.Length);
                    replaceString = "[" + replaceString + "]";
                }

                sb.Replace(phi.text, replaceString, phi.start + offset,phi.text.Length);
                offset = offset - (phi.end - phi.start) + replaceString.Length;
            }
            //textArea.ResetText();
            textArea.Text = sb.ToString();
            annotationList.clear();
            refreshAll();

        }

        private void buttonChangeInputFolder_Click(object sender, EventArgs e)
        {
            using (var fbd = new FolderBrowserDialog())
            {
                DialogResult result = fbd.ShowDialog();
                if (result == DialogResult.OK && !string.IsNullOrWhiteSpace(fbd.SelectedPath))
                {
                    textBoxInputFolder.Text = fbd.SelectedPath;
                }
            }
        }

        private void buttonChangeOutputFolder_Click(object sender, EventArgs e)
        {
            using (var fbd = new FolderBrowserDialog())
            {
                DialogResult result = fbd.ShowDialog();
                if (result == DialogResult.OK && !string.IsNullOrWhiteSpace(fbd.SelectedPath))
                {
                    textBoxOutputFolder.Text = fbd.SelectedPath;
                }
            }
        }

        private void SaveConfiguration()
        {
            appConfig.CaseSensitiveTerms = Regex.Split(textBoxKeywordCaseSensitive.Text, @"\r?\n|\r");
            appConfig.CaseInSensitiveTerms = Regex.Split(textBoxKeywordCaseInsensitive.Text, @"\r?\n|\r");
            appConfig.InputFolder = textBoxInputFolder.Text;
            appConfig.OutputFolder = textBoxOutputFolder.Text;

            appConfig.Save();
        }
        private void MainGuiClosing(object sender, FormClosingEventArgs e)
        {
            SaveConfiguration();
        }
        private void buttonSaveConfig_Click(object sender, EventArgs e)
        {
            SaveConfiguration();
        }

        private void buttonReset_Click(object sender, EventArgs e)
        {
            reloadDocument();
        }
    }

}
