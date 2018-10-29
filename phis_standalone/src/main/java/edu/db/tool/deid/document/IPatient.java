package edu.db.tool.deid.document;

import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author Duy Bui
 */
public class IPatient {
    
    public String patientId;
    public String MRN;
    public String firstName;
    public String middleName;
    public String lastName;

    public String address1;
    public String address2;
    public String address3;

    public String city;
    public String state;
    public String zip;
    public String phone;
    public String email;
    public String birthDate;
    public String sex;
    public int age;

    public String race;
    public String ethnicGroup;


}
