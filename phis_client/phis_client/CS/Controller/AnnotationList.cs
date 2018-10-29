using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DeIdClient.CS.Model;
/**
 *
 * @author Duy Bui
 */
namespace DeIdClient.CS.Controller
{
    public class AnnotationList
    {
        MainGui gui;
        public ArrayList annotations;


        public AnnotationList(MainGui gui)
        {
            this.gui = gui;
            annotations = new ArrayList();
        }

        public AnnotationList(ArrayList annotations, MainGui gui)
        {
            this.annotations = annotations;
            this.gui = gui;
        }


        

        public void clear()
        {
            annotations.Clear();
        }

        public void InsertBegin(Annotation ann)
        {
            if (!isOverlap(ann.start, ann.end)) {
                annotations.Insert(0, ann);
            }

        }

        public void Add(Annotation ann)
        {
            if (!isOverlap(ann.start, ann.end)) {
                annotations.Add(ann);
            }
            
        }
        public void AddRange(ArrayList anns)
        {
            foreach (Annotation ann in anns)
            {
                Add(ann);
            }
        }
        public bool isOverlap(int start,int end)
        {
            foreach (Annotation ann in annotations)
            {
                if (!((ann.start < start && ann.end <= start) || (ann.end > end && ann.start >= end)))
                {
                    return true;
                }
            }

            return false;
        }

        public ArrayList search(int start, int end)
        {
            ArrayList results = new ArrayList();
            foreach (Annotation ann in annotations)
            {
                if (!((ann.start < start && ann.end <= start) || (ann.end > end && ann.start >= end)))
                {
                    results.Add(ann);
                }
            }
            return results;
        }
        public ArrayList Remove(int start, int end)
        {
            ArrayList anns = search(start,end);
            foreach (Annotation ann in anns)
            {
                annotations.Remove(ann);
            }
            return anns;
        }

        public void SortPosition()
        {
            annotations.Sort(new PositionComparer());
        }

       


    }


    public class RecordIndexComparer : Comparer<string>
    {
        ArrayList items;
        public RecordIndexComparer(ArrayList items)
        {
            this.items = items;
        }


        public override int Compare(string a1, string a2)
        {
            return items.IndexOf(a1) - items.IndexOf(a2);
        }


    }

    public class PositionComparer : Comparer<Annotation>
    {


        public override int Compare(Annotation a1, Annotation a2)
        {
            return a1.start - a2.start;
        }


    }

}
