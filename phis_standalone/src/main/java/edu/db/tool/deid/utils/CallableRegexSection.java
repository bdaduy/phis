package edu.db.tool.deid.utils;

import edu.db.tool.deid.document.AnnotationGroup;
import edu.db.tool.deid.document.IAnnotation;
import edu.db.tool.deid.utils.MatchMethod;
import edu.db.tool.deid.document.IDocument;
import edu.db.tool.deid.document.ISection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author Duy Bui
 */
public class CallableRegexSection implements Callable {

    List<Pattern> patterns;
    public IDocument inputDoc;
    public List<AnnotationGroup> annotationGroups;
    public MatchMethod matchMethod;

    public CallableRegexSection(List<Pattern> patterns, IDocument inputDoc, MatchMethod matchMethod) {
        super();
        this.patterns = patterns;
        this.inputDoc = inputDoc;
        this.matchMethod = matchMethod;

    }

    @Override
    public Object call() throws Exception {
        // annotations = new ArrayList<>();
        annotationGroups = new ArrayList<>();

        for (ISection section : inputDoc.sections) {

            for (Pattern pattern : patterns) {
                Matcher m;
                if (matchMethod.matchContentOnly) {

                    m = pattern.matcher(section.getContentText());

                } else {

                    m = pattern.matcher(section.getText());

                }

                while (m.find()) {
                    AnnotationGroup annGroup = new AnnotationGroup();
                    annGroup.pattern = pattern;
                    annotationGroups.add(annGroup);

                    for (int group = 1; group <= m.groupCount(); group++) {

                        int start, end;
                        String text = m.group(group);

                        if (text == null) {
                            continue;
                        }
                        if (matchMethod.matchContentOnly) {
                            start = section.titleSentence.end + m.start(group);
                            end = section.titleSentence.end + m.end(group);
                        } else {
                            start = section.start + m.start(group);
                            end = section.start + m.end(group);
                        }

                        IAnnotation ann = new IAnnotation(start, end, m.group(group), null);
                        ann.pattern = pattern;
                        // annotations.add(ann);
                        annGroup.annotations.add(ann);
                    }

                    if (annGroup.annotations.size() > 0) {
                        annGroup.start = annGroup.getStart();
                        annGroup.end = annGroup.getEnd();
                        annGroup.setText(inputDoc.unTaggedText.substring(annGroup.start, annGroup.end));
                    }

                }
            }

        }

        return this;
    }
}
