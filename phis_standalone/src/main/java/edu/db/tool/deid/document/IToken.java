package edu.db.tool.deid.document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 *
 * @author Duy Bui
 */
public class IToken extends IAnnotation {

	public ISentence sentence;

	public String normText;
	public String pos;
	public String lemma;
	public String lemmaLC;

	public IToken prev;
	public IToken next;

	public String goldLabel;
	public String testLabel;
	public String testDebugLabel;

	public String toString() {
		return getText();
	}

	public IToken(ISentence sentence, int start, int end, String text, String source) {
		super(start, end, text, source);
		this.sentence = sentence;
	}
	


	public String getText() {
		if (text == null) {
			text = sentence.section.document.unTaggedText.substring(start, end);
		}
		return text;
	}

	public boolean isNewline() {
		return normText.equals("*NL*");
	}

	public List<IToken> getRight(int number) {
		IToken nextToken = next;
		List<IToken> tokens = new ArrayList<>();
		while (nextToken != null) {
			tokens.add(nextToken);
			if (tokens.size() == number)
				break;
			nextToken = nextToken.next;
		}
		return tokens;
	}

	public String getRightCharacter(int number) {
		return sentence.section.document.unTaggedText.substring(end,
				Math.min(end + number, sentence.section.document.unTaggedText.length()));
	}

	public List<IToken> getLeft(int number) {
		IToken prevToken = prev;
		List<IToken> tokens = new ArrayList<>();
		while (prevToken != null) {
			tokens.add(prevToken);
			if (tokens.size() == number)
				break;
			prevToken = prevToken.prev;
		}

		return tokens;
	}

	public String left5;

	public String getLeft5() {
		if (left5 == null) {
			List<IToken> tokens = getLeft(5);

			StringBuffer buf = new StringBuffer();

			for (IToken token : tokens) {
				if (buf.length() > 0)
					buf.insert(0, " ");
				buf.insert(0, token.lemma);
			}

			left5 = buf.toString();

		}

		return left5;
	}

	public String right5;

	public String getRight5() {
		if (right5 == null) {
			List<IToken> tokens = getRight(5);

			StringBuffer buf = new StringBuffer();

			for (IToken token : tokens) {
				if (buf.length() > 0)
					buf.append(" ");
				buf.append(token.lemma);
			}

			right5 = buf.toString();

		}

		return right5;
	}

	public String left5LC;

	public String getLeft5LC() {
		if (left5LC == null) {
			left5LC = getLeft5().toLowerCase();
		}
		return left5LC;
	}

	public String right5LC;

	public String getRight5LC() {
		if (right5LC == null) {
			right5LC = getRight5().toLowerCase();
		}
		return right5LC;
	}

}
