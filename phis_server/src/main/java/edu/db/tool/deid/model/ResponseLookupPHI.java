/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.db.tool.deid.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Duy Bui
 */
public class ResponseLookupPHI {

    public List<JSonAnnotation> annotations;
        
    public ResponseLookupPHI(RequestLookupPHI req){
        this.annotations=new ArrayList<>();
    }
    
}
