package edu.db.tool.deid.document;

import java.io.Serializable;
/**
 *
 * @author Duy Bui
 */
public class Span implements Serializable{
	public int start;
	public int end;
	
	public Span(int start, int end) {
		super();
		this.start = start;
		this.end = end;
	}
	
	public Span remove(IAnnotation ann){
		if(start<ann.start)
			return new Span(start, ann.start);
		if(end>ann.end){
			return new Span(ann.end, end);
		}
		
		return null;
	}
	
	public boolean isContainIn(Span ann){
		return start>=ann.start && end<=ann.end;
	}
	
	public boolean isContain(Span ann){
		return start<=ann.start && end>=ann.end;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof Span){
			Span s=(Span) obj;
			if(s.start==start&&s.end==end)
				return true;
		}
		
		return false;
	}
	
	
}
