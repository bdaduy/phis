using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using DeIdClient.CS.Model;
/**
 *
 * @author Duy Bui
 */
namespace DeIdClient
{
    class EventHandler
    {

        public static void dataGridView1_CellContentClick(MainGui gui,object sender, DataGridViewCellEventArgs e)
        {
            if (e.RowIndex >= 0)
            {
                DataGridViewRow row = gui.dataGridView1.Rows[e.RowIndex];
                if (row.IsNewRow)
                    return;

                int start = Int32.Parse(row.Cells[0].Value.ToString());
                int end = Int32.Parse(row.Cells[1].Value.ToString());

                gui.textArea.SelectionStart = start;
                gui.textArea.SelectionLength = end - start;
                gui.textArea.Focus();

            }
        }
    }
}
