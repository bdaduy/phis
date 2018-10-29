/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.db.tool.deid.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author dbui
 */
public class JSonDocument {

    public String patientId;
    public String recordId;
    public String lastUser;
    public String lastModified;
    public List<JSonAnnotation> annotations = new ArrayList<JSonAnnotation>();
    public Set<String> labels = new HashSet<String>();

    public String text;

}
