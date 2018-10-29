package edu.db.tool.deid.document;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Duy Bui
 */
public class ISection extends IAnnotation {

	public IDocument document;

	public ISentence titleSentence = null;

	public ISection(IDocument document, int start, int end, String text, String source) {
		super(start, end, text, source);
		this.document = document;

	}

	public List<ISentence> contentSentences = new ArrayList<>();

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("H: " + titleSentence + "\n");
		for (ISentence s : contentSentences) {
			buf.append(" " + s + "\n");
		}

		return buf.toString();
	}

	public String toString2() {
		StringBuffer buf = new StringBuffer();
		buf.append("H: " + titleSentence.toString2() + "\n");
		for (ISentence s : contentSentences) {
			buf.append(" " + s.toLinkedString() + "\n");
		}

		return buf.toString();
	}

	public String getText() {
		if (text == null) {
			text = document.unTaggedText.substring(start, end);
		}
		return text;
	}
	


	public String contentText;

	public String getContentText() {

		if (contentText == null) {
			if (!contentSentences.isEmpty()) {
				int start = contentSentences.get(0).start;
                                if(titleSentence!=null)
                                    start = titleSentence.end;
				int end = contentSentences.get(contentSentences.size() - 1).end;
				contentText = document.unTaggedText.substring(start, end);
			} else {
				contentText = "";
			}

		}
		return contentText;
	}

	

	public List<IToken> getAllTokens() {
		List<IToken> tokens = new ArrayList<>();
                if(titleSentence!=null)
                    tokens.addAll(titleSentence.tokens);
		for (ISentence sentence : contentSentences) {
			tokens.addAll(sentence.tokens);
		}

		return tokens;
	}
}
