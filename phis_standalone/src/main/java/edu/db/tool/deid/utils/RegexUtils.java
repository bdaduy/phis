package edu.db.tool.deid.utils;

import edu.db.tool.deid.document.IAnnotation;
import edu.db.tool.deid.document.IDocument;
import edu.db.tool.deid.document.ISection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Duy Bui
 */
public class RegexUtils {

    public static String buildRegex(List<String> terms) {
        Collections.sort(terms, new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {

                return o2.length() - o1.length();
            }
        });

        StringBuffer buf = new StringBuffer();
        for (String string : terms) {
            string = getRegex(string, false);
            if (buf.length() > 0) {
                buf.append("|");
            }

            buf.append(string);
        }
        return buf.toString();

    }

    public static String getRegex(String term, boolean abstractNumber) {
        String s = term.trim().replaceAll("([\\*+\\[\\]\\^\\$\\|)(\\.?\\\\])", "\\\\$1").replaceAll("\\s+", "\\\\s+");

        if (abstractNumber) {
            s = s.replaceAll("\\d+", "\\\\d+");
        }

        return s;
    }

    public static List<IAnnotation> matchDoc(IDocument doc, List<Pattern> patterns) {
        List<IAnnotation> anns = new ArrayList<>();
        for (Pattern p : patterns) {
            Matcher m = p.matcher(doc.getText());
            while (m.find()) {
                for (int i = 1; i <= m.groupCount(); i++) {
                    int start = m.start(i);
                    int end = m.end(i);
                    IAnnotation ann = new IAnnotation(start, end, m.group(i), null);
//                    System.out.println(ann.toString4());
                    anns.add(ann);

                }
            }
        }
        return anns;
    }

    public static List<IAnnotation> matchDoc(IDocument doc, Pattern pattern) {
        List<Pattern> patterns = new ArrayList<Pattern>();
        patterns.add(pattern);

        return matchDoc(doc, patterns);
    }

    public static List<IAnnotation> matchContent(ISection section, List<Pattern> patterns) {
        List<IAnnotation> anns = new ArrayList<>();
        for (Pattern p : patterns) {
            Matcher m = p.matcher(section.getContentText());
            while (m.find()) {
                for (int i = 1; i <= m.groupCount(); i++) {
                    int start = m.start(i);
                    int end = m.end(i);
                    if (section.titleSentence != null) {
                        start += section.titleSentence.end;
                        end += section.titleSentence.end;
                    }

                    if (end > start) {
                        IAnnotation ann = new IAnnotation(start, end, m.group(i), null);
                        anns.add(ann);
                    }
                }

            }
        }
        return anns;
    }

    public static List<IAnnotation> matchContent(IDocument doc, Pattern pattern) {
        List<Pattern> patterns = new ArrayList<Pattern>();
        patterns.add(pattern);
        return matchContent(doc, patterns);
    }

    public static List<IAnnotation> matchContent(IDocument doc, List<Pattern> patterns) {
        List<IAnnotation> anns = new ArrayList<>();
        for (ISection section : doc.sections) {
            anns.addAll(matchContent(section, patterns));
        }
        return anns;
    }

}
