package edu.db.tool.deid.utils;


import java.util.HashSet;
import java.util.List;
import java.util.Set;


import edu.db.tool.deid.utils.ResourceUtil;
/**
 *
 * @author Duy Bui
 */
public class HumanNames {
    
    public static Set<String> lastNames=new HashSet<String>();
    public static Set<String> firstNamesMale=new HashSet<String>();
    public static Set<String> firstNamesFemale=new HashSet<String>();
    public static Set<String> firstNames=new HashSet<String>();
    
    
    private static boolean IsInit = false;

    public static void init() {
        if (IsInit) {
            return;
        }
        List<String> lines = ResourceUtil.getTextByLine("/edu/db/tool/deid/resources/dict/dist.all.last");
        for (String line : lines) {
            String[] parts = line.split("\\s+");
            String name = parts[0].toLowerCase();
            lastNames.add(name);
        }
        
        lines = ResourceUtil.getTextByLine("/edu/db/tool/deid/resources/dict/dist.female.first");
        for (String line : lines) {
            String[] parts = line.split("\\s+");
            String name = parts[0].toLowerCase();
            firstNames.add(name);
            firstNamesFemale.add(name);
        }
        lines = ResourceUtil.getTextByLine("/edu/db/tool/deid/resources/dict/dist.male.first");
                
        for (String line : lines) {
            String[] parts = line.split("\\s+");
            String name = parts[0].toLowerCase();
            firstNames.add(name);
            firstNamesMale.add(name);
        }
        IsInit = true;

    }

}
