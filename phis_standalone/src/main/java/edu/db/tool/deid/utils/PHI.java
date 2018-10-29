package edu.db.tool.deid.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Duy Bui
 */
public class PHI {

    private static List<String> hipaaTypes;

    public static List<String> getHIPAAs() {
        if (hipaaTypes == null) {
            hipaaTypes = new ArrayList<>();

          hipaaTypes.add("AGE");
          hipaaTypes.add("ZIP");
          hipaaTypes.add("DATE");                

            hipaaTypes.add("PATIENT");
            hipaaTypes.add("CITY");
            hipaaTypes.add("STREET");
            hipaaTypes.add("ORGANIZATION");
            hipaaTypes.add("PHONE");
            hipaaTypes.add("IPADDRESS");
            hipaaTypes.add("FAX");
            hipaaTypes.add("EMAIL");
            hipaaTypes.add("MEDICALRECORD");
            hipaaTypes.add("HEALTHPLAN");
            hipaaTypes.add("LICENSE");
            hipaaTypes.add("DEVICE");
            hipaaTypes.add("VEHICLE");
            hipaaTypes.add("BIOID");
            hipaaTypes.add("SSN");
            hipaaTypes.add("ACCOUNT");
            hipaaTypes.add("IDNUM");

            hipaaTypes.add("USERNAME");
            hipaaTypes.add("DOCTOR");
            hipaaTypes.add("HOSPITAL");
            hipaaTypes.add("COUNTRY");
            hipaaTypes.add("STATE");
            hipaaTypes.add("LOCATION-OTHER");
            hipaaTypes.add("PROFESSION");
            hipaaTypes.add("URL");
            hipaaTypes.add("PHI");
        }

        return hipaaTypes;
    }

    public static Map<String, String> hmSubCat2Cat = getMapping();

    private static Map<String, String> getMapping() {
        Map<String, String> map = new HashMap<String, String>();

        map.put("DATE", "DATE");
        map.put("DOCTOR", "NAME");
        map.put("PATIENT", "NAME");
        map.put("USERNAME", "NAME");
        map.put("AGE", "AGE");
        map.put("PHONE", "CONTACT");
        map.put("FAX", "CONTACT");
        map.put("EMAIL", "CONTACT");
        map.put("URL", "CONTACT");
        map.put("LICENSE", "ID");
        map.put("MEDICALRECORD", "ID");
        map.put("IDNUM", "ID");
        map.put("DEVICE", "ID");
        map.put("BIOID", "ID");
        map.put("HEALTHPLAN", "ID");
        map.put("HOSPITAL", "LOCATION");
        map.put("CITY", "LOCATION");
        map.put("STATE", "LOCATION");
        map.put("STREET", "LOCATION");
        map.put("ZIP", "LOCATION");
        map.put("ORGANIZATION", "LOCATION");
        map.put("COUNTRY", "LOCATION");
        map.put("LOCATION-OTHER", "LOCATION");
        map.put("PROFESSION", "PROFESSION");

        return map;
    }

}
