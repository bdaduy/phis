using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using DeIdClient;
using DeIdClient.CS.Controller;
using DeIdClient.CS.Model;
/**
 *
 * @author Duy Bui
 */
namespace DeIdClient.CS.Controller
{
    public class MainContextMenu
    {
        public MainGui gui;
        public MainContextMenu(MainGui gui)
        {
            this.gui = gui;

            ContextMenu contextMenu = new ContextMenu();
            MenuItem phiItemNew = new MenuItem();
            phiItemNew.Text = "&Add";
            phiItemNew.Click += new System.EventHandler(menuItemAdd_Click);
            contextMenu.MenuItems.Add(phiItemNew);

            phiItemNew = new MenuItem();
            phiItemNew.Text = "&Delete";
            phiItemNew.Click += new System.EventHandler(menuItemDelete_Click);
            contextMenu.MenuItems.Add(phiItemNew);

           

            phiItemNew = new MenuItem();
            phiItemNew.Text = "&Add Keyword (Case Matter)";
            phiItemNew.Click += new System.EventHandler(menuItemAddKeyWordCaseSensitive_Click);
            contextMenu.MenuItems.Add(phiItemNew);

            phiItemNew = new MenuItem();
            phiItemNew.Text = "&Add Keyword (Case Not Matter)";
            phiItemNew.Click += new System.EventHandler(menuItemAddKeyWordCaseInsensitive_Click);
            contextMenu.MenuItems.Add(phiItemNew);


            gui.textArea.ContextMenu = contextMenu;
        }
        private void menuItemAdd_Click(object sender, EventArgs e)
        {
            int start = gui.textArea.SelectionStart;
            int end = start + gui.textArea.SelectionLength;

            if (gui.annotationList.isOverlap(start, end))
                return;

            String text = gui.textArea.SelectedText;
            String trimStartText = text.TrimStart();
            start += (text.Length - trimStartText.Length);
            String trimEndText = text.TrimEnd();

            end -= (text.Length - trimEndText.Length);

            if (end > start)
            {
                Annotation ann = new Annotation(start, end, text.Trim());

                gui.annotationList.InsertBegin(ann);
                gui.markPHI(ann);
                gui.refreshTable();
            }
        }

        private void menuItemAddKeyWordCaseInsensitive_Click(object sender, EventArgs e)
        {
            string text = gui.textArea.SelectedText.Trim();
            if (text.Length > 0)
                gui.textBoxKeywordCaseInsensitive.AppendText("\r\n" + text.ToLower());
        }

        private void menuItemAddKeyWordCaseSensitive_Click(object sender, EventArgs e)
        {
            string text = gui.textArea.SelectedText.Trim();
            if(text.Length>0)
                gui.textBoxKeywordCaseSensitive.AppendText("\r\n" + text );
        }
        

        private void menuItemDelete_Click(object sender, EventArgs e)
        {
            int start = gui.textArea.SelectionStart;
            int end = start + gui.textArea.SelectionLength;
            ArrayList removedAnns = gui.annotationList.Remove(start, end);
            gui.removePHIFromEditor(removedAnns);
            gui.refreshTable();
        }


    }
}
