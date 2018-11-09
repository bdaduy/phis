/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.db.tool.deid.model;

import edu.db.tool.deid.document.IAnnotation;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Duy Bui
 */
public final class JSonAnnotation {

    public int start;
    public int end;

    
    public JSonAnnotation(){
        
    }
    public static JSonAnnotation convert(IAnnotation iann){
        JSonAnnotation jann=new JSonAnnotation();
        jann.start=iann.start;
        jann.end=iann.end;
        return jann;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

}
