package edu.db.tool.deid.document;


import edu.db.tool.deid.intervaltree.IntervalTree;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;


/**
 *
 * @author Duy Bui
 */
public class IAnnotation extends Span {
	public String id;

	public  String text;
	public  String textLC;
	public String source;
	public transient Pattern pattern;
	public boolean canOverride=false;
	
	public String label;

//	public SectionHeadingAnn heading;
        
        public IAnnotation clone(){
            IAnnotation ann=new IAnnotation();
            ann.start=this.start;
            ann.end=this.end;
            ann.id=this.id;
            ann.text=this.text;
            ann.source=this.source;
            ann.canOverride=this.canOverride;
            ann.label=this.label;
            return ann;
        }
        
        public List<IAnnotation> diff(int start, int end){
            List<IAnnotation> anns=new ArrayList<>();
            
            if(this.start<start && this.end<=end){
                IAnnotation ann= this.clone();
                ann.text=ann.text.substring(0, start-this.start);
                ann.end=start;
                
                anns.add(ann);
                
            } else if (this.end>end && this.start>=start){
                IAnnotation ann= this.clone();
                ann.text=ann.text.substring(end-this.start);
                ann.start=end;
                
                anns.add(ann);
            } else if(this.start<start && this.end>end){
                IAnnotation ann= this.clone();
                ann.text=ann.text.substring(0, start-this.start);
                ann.end=start;
                
                anns.add(ann);
                
                ann= this.clone();
                ann.text=ann.text.substring(end-this.start);
                ann.start=end;
               
                anns.add(ann);
            }
            return anns;
        }

	public IAnnotation() {
		super(0, 0);
	}

	public String getText() {
		return text;
	}

	public String getTextLC() {
		if (textLC == null) {
			textLC = getText().toLowerCase();
		}

		return textLC;
	}

	public void setText(String text) {
		this.text = text;
	}

	public IAnnotation(int start, int end, String text, String source) {
		super(start, end);
		this.text = text;
		this.source = source;
	}

	public static List<IAnnotation> combine(List<IAnnotation> phis) {
		List<IAnnotation> allPhis = new ArrayList<>(phis);
		Collections.sort(allPhis, new Comparator<IAnnotation>() {

			@Override
			public int compare(IAnnotation phi1, IAnnotation phi2) {

				return phi2.getSize() - phi1.getSize();
			}

		});

		IntervalTree<IAnnotation> it = new IntervalTree<IAnnotation>();

		List<IAnnotation> selectedPhis = new ArrayList<>();

		for (IAnnotation phi : allPhis) {
			List<IAnnotation> searchPhis = it.get(phi.start, phi.end);
			if (searchPhis.isEmpty()) {
				it.addInterval(phi.start, phi.end, phi);
				selectedPhis.add(phi);
			}

		}
		Collections.sort(selectedPhis, new Comparator<IAnnotation>() {

			@Override
			public int compare(IAnnotation phi1, IAnnotation phi2) {

				return phi1.start - phi2.start;
			}

		});

		return selectedPhis;
	}

	// public IAnnotation(int start, int end, String text) {
	// super(start, end);
	// this.text = text;
	// }

	public String toString4() {
		return "[" + this.getClass().getSimpleName() + " " + start + " " + end + " " + text + " "+label+" " + source + "]";
	}
	
	public String toString2() {
		return source;
	}

	public String toString() {
		return "[" + text + "]";
	}

	public int getSize() {
		return end - start;
	}
        public static String toString(List<IAnnotation> anns){ 
            StringBuffer buf=new StringBuffer();
            for(IAnnotation ann:anns){
                buf.append(ann.toString4()+"\n");
            }
            return buf.toString();

        }

}
