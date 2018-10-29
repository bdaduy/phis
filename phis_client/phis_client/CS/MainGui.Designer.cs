using System;
using System.Windows.Forms;

namespace DeIdClient
{
    partial class MainGui
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.textArea = new System.Windows.Forms.RichTextBox();
            this.tableLayoutPanel1 = new System.Windows.Forms.TableLayoutPanel();
            this.panel4 = new System.Windows.Forms.Panel();
            this.buttonSaveConfig = new System.Windows.Forms.Button();
            this.textBoxRepeatedCharacter = new System.Windows.Forms.TextBox();
            this.textBoxGenericTag = new System.Windows.Forms.TextBox();
            this.radioButtonRepeatedCharacter = new System.Windows.Forms.RadioButton();
            this.radioButtonGenericTag = new System.Windows.Forms.RadioButton();
            this.label1 = new System.Windows.Forms.Label();
            this.textBoxKeywordCaseInsensitive = new System.Windows.Forms.TextBox();
            this.panel1 = new System.Windows.Forms.Panel();
            this.buttonChangeOutputFolder = new System.Windows.Forms.Button();
            this.buttonChangeInputFolder = new System.Windows.Forms.Button();
            this.radioButtonLDS = new System.Windows.Forms.RadioButton();
            this.radioButtonALL = new System.Windows.Forms.RadioButton();
            this.textBoxOutputFolder = new System.Windows.Forms.TextBox();
            this.buttonReset = new System.Windows.Forms.Button();
            this.label7 = new System.Windows.Forms.Label();
            this.textBoxInputFolder = new System.Windows.Forms.TextBox();
            this.label6 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.buttonScrub = new System.Windows.Forms.Button();
            this.buttonRun = new System.Windows.Forms.Button();
            this.buttonNext = new System.Windows.Forms.Button();
            this.buttonBack = new System.Windows.Forms.Button();
            this.buttonSave = new System.Windows.Forms.Button();
            this.buttonLoad = new System.Windows.Forms.Button();
            this.comboBoxNotes = new System.Windows.Forms.ComboBox();
            this.textBoxPatientId = new System.Windows.Forms.TextBox();
            this.dataGridView1 = new System.Windows.Forms.DataGridView();
            this.startCol = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.endCol = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.textCol = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.textBoxKeywordCaseSensitive = new System.Windows.Forms.TextBox();
            this.panel3 = new System.Windows.Forms.Panel();
            this.label3 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.tableLayoutPanel1.SuspendLayout();
            this.panel4.SuspendLayout();
            this.panel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
            this.panel3.SuspendLayout();
            this.SuspendLayout();
            // 
            // textArea
            // 
            this.textArea.BackColor = System.Drawing.SystemColors.Window;
            this.textArea.Dock = System.Windows.Forms.DockStyle.Fill;
            this.textArea.Font = new System.Drawing.Font("Arial Narrow", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.textArea.Location = new System.Drawing.Point(3, 95);
            this.textArea.Name = "textArea";
            this.tableLayoutPanel1.SetRowSpan(this.textArea, 3);
            this.textArea.Size = new System.Drawing.Size(1340, 561);
            this.textArea.TabIndex = 0;
            this.textArea.Text = "";
            // 
            // tableLayoutPanel1
            // 
            this.tableLayoutPanel1.ColumnCount = 3;
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 70F));
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 15F));
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 15F));
            this.tableLayoutPanel1.Controls.Add(this.panel4, 0, 4);
            this.tableLayoutPanel1.Controls.Add(this.textBoxKeywordCaseInsensitive, 2, 3);
            this.tableLayoutPanel1.Controls.Add(this.textArea, 0, 1);
            this.tableLayoutPanel1.Controls.Add(this.panel1, 0, 0);
            this.tableLayoutPanel1.Controls.Add(this.dataGridView1, 1, 1);
            this.tableLayoutPanel1.Controls.Add(this.textBoxKeywordCaseSensitive, 1, 3);
            this.tableLayoutPanel1.Controls.Add(this.panel3, 1, 2);
            this.tableLayoutPanel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel1.Location = new System.Drawing.Point(0, 0);
            this.tableLayoutPanel1.Name = "tableLayoutPanel1";
            this.tableLayoutPanel1.RowCount = 5;
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 92F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 55.58026F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 30F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 44.41974F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 43F));
            this.tableLayoutPanel1.Size = new System.Drawing.Size(1924, 703);
            this.tableLayoutPanel1.TabIndex = 3;
            // 
            // panel4
            // 
            this.tableLayoutPanel1.SetColumnSpan(this.panel4, 3);
            this.panel4.Controls.Add(this.buttonSaveConfig);
            this.panel4.Controls.Add(this.textBoxRepeatedCharacter);
            this.panel4.Controls.Add(this.textBoxGenericTag);
            this.panel4.Controls.Add(this.radioButtonRepeatedCharacter);
            this.panel4.Controls.Add(this.radioButtonGenericTag);
            this.panel4.Controls.Add(this.label1);
            this.panel4.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel4.Location = new System.Drawing.Point(4, 664);
            this.panel4.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.panel4.Name = "panel4";
            this.panel4.Size = new System.Drawing.Size(1916, 34);
            this.panel4.TabIndex = 8;
            // 
            // buttonSaveConfig
            // 
            this.buttonSaveConfig.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.buttonSaveConfig.Location = new System.Drawing.Point(1749, 0);
            this.buttonSaveConfig.Name = "buttonSaveConfig";
            this.buttonSaveConfig.Size = new System.Drawing.Size(164, 35);
            this.buttonSaveConfig.TabIndex = 13;
            this.buttonSaveConfig.Text = "Save Configuration";
            this.buttonSaveConfig.UseVisualStyleBackColor = true;
            this.buttonSaveConfig.Click += new System.EventHandler(this.buttonSaveConfig_Click);
            // 
            // textBoxRepeatedCharacter
            // 
            this.textBoxRepeatedCharacter.Location = new System.Drawing.Point(607, 5);
            this.textBoxRepeatedCharacter.MaxLength = 1;
            this.textBoxRepeatedCharacter.Name = "textBoxRepeatedCharacter";
            this.textBoxRepeatedCharacter.Size = new System.Drawing.Size(20, 26);
            this.textBoxRepeatedCharacter.TabIndex = 23;
            this.textBoxRepeatedCharacter.Text = "X";
            // 
            // textBoxGenericTag
            // 
            this.textBoxGenericTag.Location = new System.Drawing.Point(302, 5);
            this.textBoxGenericTag.Name = "textBoxGenericTag";
            this.textBoxGenericTag.Size = new System.Drawing.Size(116, 26);
            this.textBoxGenericTag.TabIndex = 22;
            this.textBoxGenericTag.Text = "[Redacted]";
            // 
            // radioButtonRepeatedCharacter
            // 
            this.radioButtonRepeatedCharacter.AutoSize = true;
            this.radioButtonRepeatedCharacter.Checked = true;
            this.radioButtonRepeatedCharacter.Location = new System.Drawing.Point(424, 5);
            this.radioButtonRepeatedCharacter.Name = "radioButtonRepeatedCharacter";
            this.radioButtonRepeatedCharacter.Size = new System.Drawing.Size(187, 24);
            this.radioButtonRepeatedCharacter.TabIndex = 21;
            this.radioButtonRepeatedCharacter.TabStop = true;
            this.radioButtonRepeatedCharacter.Text = "Repeated Characters";
            this.radioButtonRepeatedCharacter.UseVisualStyleBackColor = true;
            // 
            // radioButtonGenericTag
            // 
            this.radioButtonGenericTag.AutoSize = true;
            this.radioButtonGenericTag.Location = new System.Drawing.Point(181, 6);
            this.radioButtonGenericTag.Name = "radioButtonGenericTag";
            this.radioButtonGenericTag.Size = new System.Drawing.Size(121, 24);
            this.radioButtonGenericTag.TabIndex = 20;
            this.radioButtonGenericTag.Text = "Generic Tag";
            this.radioButtonGenericTag.UseVisualStyleBackColor = true;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(8, 6);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(160, 25);
            this.label1.TabIndex = 37;
            this.label1.Text = "Redaction Mode:";
            // 
            // textBoxKeywordCaseInsensitive
            // 
            this.textBoxKeywordCaseInsensitive.Dock = System.Windows.Forms.DockStyle.Fill;
            this.textBoxKeywordCaseInsensitive.Location = new System.Drawing.Point(1638, 426);
            this.textBoxKeywordCaseInsensitive.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.textBoxKeywordCaseInsensitive.Multiline = true;
            this.textBoxKeywordCaseInsensitive.Name = "textBoxKeywordCaseInsensitive";
            this.textBoxKeywordCaseInsensitive.ScrollBars = System.Windows.Forms.ScrollBars.Both;
            this.textBoxKeywordCaseInsensitive.Size = new System.Drawing.Size(282, 228);
            this.textBoxKeywordCaseInsensitive.TabIndex = 4;
            this.textBoxKeywordCaseInsensitive.Text = "[Case Insensitive Phrases]";
            // 
            // panel1
            // 
            this.tableLayoutPanel1.SetColumnSpan(this.panel1, 3);
            this.panel1.Controls.Add(this.buttonChangeOutputFolder);
            this.panel1.Controls.Add(this.buttonChangeInputFolder);
            this.panel1.Controls.Add(this.radioButtonLDS);
            this.panel1.Controls.Add(this.radioButtonALL);
            this.panel1.Controls.Add(this.textBoxOutputFolder);
            this.panel1.Controls.Add(this.buttonReset);
            this.panel1.Controls.Add(this.label7);
            this.panel1.Controls.Add(this.textBoxInputFolder);
            this.panel1.Controls.Add(this.label6);
            this.panel1.Controls.Add(this.label5);
            this.panel1.Controls.Add(this.buttonScrub);
            this.panel1.Controls.Add(this.buttonRun);
            this.panel1.Controls.Add(this.buttonNext);
            this.panel1.Controls.Add(this.buttonBack);
            this.panel1.Controls.Add(this.buttonSave);
            this.panel1.Controls.Add(this.buttonLoad);
            this.panel1.Controls.Add(this.comboBoxNotes);
            this.panel1.Controls.Add(this.textBoxPatientId);
            this.panel1.Location = new System.Drawing.Point(3, 3);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(1857, 86);
            this.panel1.TabIndex = 1;
            // 
            // buttonChangeOutputFolder
            // 
            this.buttonChangeOutputFolder.Location = new System.Drawing.Point(386, 46);
            this.buttonChangeOutputFolder.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.buttonChangeOutputFolder.Name = "buttonChangeOutputFolder";
            this.buttonChangeOutputFolder.Size = new System.Drawing.Size(80, 35);
            this.buttonChangeOutputFolder.TabIndex = 36;
            this.buttonChangeOutputFolder.Text = "Change";
            this.buttonChangeOutputFolder.UseVisualStyleBackColor = true;
            this.buttonChangeOutputFolder.Click += new System.EventHandler(this.buttonChangeOutputFolder_Click);
            // 
            // buttonChangeInputFolder
            // 
            this.buttonChangeInputFolder.Location = new System.Drawing.Point(386, 5);
            this.buttonChangeInputFolder.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.buttonChangeInputFolder.Name = "buttonChangeInputFolder";
            this.buttonChangeInputFolder.Size = new System.Drawing.Size(80, 37);
            this.buttonChangeInputFolder.TabIndex = 35;
            this.buttonChangeInputFolder.Text = "Change";
            this.buttonChangeInputFolder.UseVisualStyleBackColor = true;
            this.buttonChangeInputFolder.Click += new System.EventHandler(this.buttonChangeInputFolder_Click);
            // 
            // radioButtonLDS
            // 
            this.radioButtonLDS.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.radioButtonLDS.AutoSize = true;
            this.radioButtonLDS.Checked = true;
            this.radioButtonLDS.Location = new System.Drawing.Point(816, 50);
            this.radioButtonLDS.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.radioButtonLDS.Name = "radioButtonLDS";
            this.radioButtonLDS.Size = new System.Drawing.Size(66, 24);
            this.radioButtonLDS.TabIndex = 34;
            this.radioButtonLDS.TabStop = true;
            this.radioButtonLDS.Text = "LDS";
            this.radioButtonLDS.UseVisualStyleBackColor = true;
            // 
            // radioButtonALL
            // 
            this.radioButtonALL.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.radioButtonALL.AutoSize = true;
            this.radioButtonALL.Location = new System.Drawing.Point(816, 14);
            this.radioButtonALL.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.radioButtonALL.Name = "radioButtonALL";
            this.radioButtonALL.Size = new System.Drawing.Size(121, 24);
            this.radioButtonALL.TabIndex = 33;
            this.radioButtonALL.Text = "Safe Harbor";
            this.radioButtonALL.UseVisualStyleBackColor = true;
            // 
            // textBoxOutputFolder
            // 
            this.textBoxOutputFolder.Location = new System.Drawing.Point(136, 48);
            this.textBoxOutputFolder.Name = "textBoxOutputFolder";
            this.textBoxOutputFolder.Size = new System.Drawing.Size(243, 26);
            this.textBoxOutputFolder.TabIndex = 32;
            this.textBoxOutputFolder.Text = "C:\\DeidentifiedNotes";
            // 
            // buttonReset
            // 
            this.buttonReset.Location = new System.Drawing.Point(1107, 5);
            this.buttonReset.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.buttonReset.Name = "buttonReset";
            this.buttonReset.Size = new System.Drawing.Size(74, 76);
            this.buttonReset.TabIndex = 19;
            this.buttonReset.Text = "Reset";
            this.buttonReset.UseVisualStyleBackColor = true;
            this.buttonReset.Click += new System.EventHandler(this.buttonReset_Click);
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label7.Location = new System.Drawing.Point(0, 46);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(137, 25);
            this.label7.TabIndex = 31;
            this.label7.Text = "Output Folder:";
            // 
            // textBoxInputFolder
            // 
            this.textBoxInputFolder.Location = new System.Drawing.Point(136, 14);
            this.textBoxInputFolder.Name = "textBoxInputFolder";
            this.textBoxInputFolder.Size = new System.Drawing.Size(243, 26);
            this.textBoxInputFolder.TabIndex = 30;
            this.textBoxInputFolder.Text = "C:\\PatientNotes";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label6.Location = new System.Drawing.Point(16, 14);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(121, 25);
            this.label6.TabIndex = 29;
            this.label6.Text = "Input Folder:";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label5.Location = new System.Drawing.Point(546, 13);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(62, 25);
            this.label5.TabIndex = 28;
            this.label5.Text = "MRN:";
            // 
            // buttonScrub
            // 
            this.buttonScrub.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.buttonScrub.Location = new System.Drawing.Point(1021, 5);
            this.buttonScrub.Name = "buttonScrub";
            this.buttonScrub.Size = new System.Drawing.Size(81, 76);
            this.buttonScrub.TabIndex = 27;
            this.buttonScrub.Text = "Redact";
            this.buttonScrub.UseVisualStyleBackColor = true;
            this.buttonScrub.Click += new System.EventHandler(this.buttonScrub_Click);
            // 
            // buttonRun
            // 
            this.buttonRun.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.buttonRun.Location = new System.Drawing.Point(941, 5);
            this.buttonRun.Name = "buttonRun";
            this.buttonRun.Size = new System.Drawing.Size(76, 76);
            this.buttonRun.TabIndex = 16;
            this.buttonRun.Text = "Submit";
            this.buttonRun.UseVisualStyleBackColor = true;
            this.buttonRun.Click += new System.EventHandler(this.buttonRun_Click);
            // 
            // buttonNext
            // 
            this.buttonNext.Location = new System.Drawing.Point(623, 48);
            this.buttonNext.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.buttonNext.Name = "buttonNext";
            this.buttonNext.Size = new System.Drawing.Size(62, 33);
            this.buttonNext.TabIndex = 15;
            this.buttonNext.Text = "Next";
            this.buttonNext.UseVisualStyleBackColor = true;
            this.buttonNext.Click += new System.EventHandler(this.buttonNext_Click);
            // 
            // buttonBack
            // 
            this.buttonBack.Location = new System.Drawing.Point(551, 48);
            this.buttonBack.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.buttonBack.Name = "buttonBack";
            this.buttonBack.Size = new System.Drawing.Size(64, 33);
            this.buttonBack.TabIndex = 14;
            this.buttonBack.Text = "Back";
            this.buttonBack.UseVisualStyleBackColor = true;
            this.buttonBack.Click += new System.EventHandler(this.buttonBack_Click);
            // 
            // buttonSave
            // 
            this.buttonSave.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.buttonSave.Location = new System.Drawing.Point(1187, 5);
            this.buttonSave.Name = "buttonSave";
            this.buttonSave.Size = new System.Drawing.Size(81, 76);
            this.buttonSave.TabIndex = 11;
            this.buttonSave.Text = "Save";
            this.buttonSave.UseVisualStyleBackColor = true;
            this.buttonSave.Click += new System.EventHandler(this.buttonSave_Click);
            // 
            // buttonLoad
            // 
            this.buttonLoad.Location = new System.Drawing.Point(474, 5);
            this.buttonLoad.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.buttonLoad.Name = "buttonLoad";
            this.buttonLoad.Size = new System.Drawing.Size(69, 76);
            this.buttonLoad.TabIndex = 13;
            this.buttonLoad.Text = "Load";
            this.buttonLoad.UseVisualStyleBackColor = true;
            this.buttonLoad.Click += new System.EventHandler(this.buttonLoad_Click);
            // 
            // comboBoxNotes
            // 
            this.comboBoxNotes.FormattingEnabled = true;
            this.comboBoxNotes.Location = new System.Drawing.Point(693, 48);
            this.comboBoxNotes.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.comboBoxNotes.Name = "comboBoxNotes";
            this.comboBoxNotes.Size = new System.Drawing.Size(106, 28);
            this.comboBoxNotes.TabIndex = 9;
            this.comboBoxNotes.SelectedIndexChanged += new System.EventHandler(this.ComboBoxNotes_SelectedIndexChanged);
            // 
            // textBoxPatientId
            // 
            this.textBoxPatientId.Location = new System.Drawing.Point(615, 12);
            this.textBoxPatientId.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.textBoxPatientId.Name = "textBoxPatientId";
            this.textBoxPatientId.Size = new System.Drawing.Size(184, 26);
            this.textBoxPatientId.TabIndex = 8;
            // 
            // dataGridView1
            // 
            this.dataGridView1.AllowUserToResizeRows = false;
            this.dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView1.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.startCol,
            this.endCol,
            this.textCol});
            this.tableLayoutPanel1.SetColumnSpan(this.dataGridView1, 2);
            this.dataGridView1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.dataGridView1.EditMode = System.Windows.Forms.DataGridViewEditMode.EditProgrammatically;
            this.dataGridView1.Location = new System.Drawing.Point(1349, 95);
            this.dataGridView1.Name = "dataGridView1";
            this.dataGridView1.RowHeadersVisible = false;
            this.dataGridView1.RowTemplate.Height = 28;
            this.dataGridView1.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dataGridView1.Size = new System.Drawing.Size(572, 293);
            this.dataGridView1.TabIndex = 2;
            this.dataGridView1.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridView1_CellContentClick);
            // 
            // startCol
            // 
            this.startCol.HeaderText = "Start";
            this.startCol.Name = "startCol";
            this.startCol.Width = 50;
            // 
            // endCol
            // 
            this.endCol.HeaderText = "End";
            this.endCol.Name = "endCol";
            this.endCol.Width = 50;
            // 
            // textCol
            // 
            this.textCol.AutoSizeMode = System.Windows.Forms.DataGridViewAutoSizeColumnMode.Fill;
            this.textCol.HeaderText = "Text";
            this.textCol.Name = "textCol";
            // 
            // textBoxKeywordCaseSensitive
            // 
            this.textBoxKeywordCaseSensitive.Dock = System.Windows.Forms.DockStyle.Fill;
            this.textBoxKeywordCaseSensitive.Location = new System.Drawing.Point(1350, 426);
            this.textBoxKeywordCaseSensitive.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.textBoxKeywordCaseSensitive.Multiline = true;
            this.textBoxKeywordCaseSensitive.Name = "textBoxKeywordCaseSensitive";
            this.textBoxKeywordCaseSensitive.ScrollBars = System.Windows.Forms.ScrollBars.Both;
            this.textBoxKeywordCaseSensitive.Size = new System.Drawing.Size(280, 228);
            this.textBoxKeywordCaseSensitive.TabIndex = 3;
            this.textBoxKeywordCaseSensitive.Text = "[Case Sensitive Phrases]";
            // 
            // panel3
            // 
            this.tableLayoutPanel1.SetColumnSpan(this.panel3, 2);
            this.panel3.Controls.Add(this.label3);
            this.panel3.Controls.Add(this.label2);
            this.panel3.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel3.Location = new System.Drawing.Point(1349, 394);
            this.panel3.Name = "panel3";
            this.panel3.Size = new System.Drawing.Size(572, 24);
            this.panel3.TabIndex = 9;
            // 
            // label3
            // 
            this.label3.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(446, 3);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(125, 20);
            this.label3.TabIndex = 11;
            this.label3.Text = "Case Insensitive";
            // 
            // label2
            // 
            this.label2.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(0, 3);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(114, 20);
            this.label2.TabIndex = 10;
            this.label2.Text = "Case Sensitive";
            // 
            // MainGui
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1924, 703);
            this.Controls.Add(this.tableLayoutPanel1);
            this.Name = "MainGui";
            this.Text = "Personal Health Information Scrubber";
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.MainGuiClosing);
            this.tableLayoutPanel1.ResumeLayout(false);
            this.tableLayoutPanel1.PerformLayout();
            this.panel4.ResumeLayout(false);
            this.panel4.PerformLayout();
            this.panel1.ResumeLayout(false);
            this.panel1.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
            this.panel3.ResumeLayout(false);
            this.panel3.PerformLayout();
            this.ResumeLayout(false);

        }

        





        #endregion

        public System.Windows.Forms.RichTextBox textArea;
        public System.Windows.Forms.TableLayoutPanel tableLayoutPanel1;
        public System.Windows.Forms.Panel panel1;
        public System.Windows.Forms.DataGridView dataGridView1;
        public TextBox textBoxPatientId;
        public ComboBox comboBoxNotes;
        private Button buttonSave;
        public TextBox textBoxKeywordCaseInsensitive;
        public TextBox textBoxKeywordCaseSensitive;
        private Button buttonLoad;
        private Button buttonNext;
        private Button buttonBack;
        public Button buttonRun;
        private Button buttonScrub;
        private Label label5;
        private Label label6;
        private TextBox textBoxInputFolder;
        private TextBox textBoxOutputFolder;
        private Label label7;
        private RadioButton radioButtonLDS;
        private RadioButton radioButtonALL;
        private Button buttonChangeOutputFolder;
        private Button buttonChangeInputFolder;
        private Button buttonReset;
        private DataGridViewTextBoxColumn startCol;
        private DataGridViewTextBoxColumn endCol;
        private DataGridViewTextBoxColumn textCol;
        private Panel panel4;
        public Button buttonSaveConfig;
        private TextBox textBoxRepeatedCharacter;
        private TextBox textBoxGenericTag;
        private RadioButton radioButtonRepeatedCharacter;
        private RadioButton radioButtonGenericTag;
        private Label label1;
        private Panel panel3;
        private Label label3;
        private Label label2;
    }
}

