using DeIdClient.CS.Controller;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
/**
 *
 * @author Duy Bui
 */
namespace DeIdClient.CS.Model
{
    public class Annotation : IComparable
    {
        public int start;
        public int end;
        public string text;

        public Annotation()
        {
        }
        public Annotation(int start, int end, string text)
        {
            this.start = start;
            this.end = end;
            this.text = text;

        }

        public int CompareTo(object obj)
        {
           return this.CompareTo(obj);
        }
    }
}
