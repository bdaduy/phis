package edu.db.tool.deid.document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Duy Bui
 */
public class ISentence extends IAnnotation {

	public ISection section;

	public ISentence(ISection section, int start, int end, String text, String source) {
		super(start, end, text, source);
		this.section = section;

	}

	public List<IToken> tokens = new ArrayList<IToken>();
	
	public String getText() {
		if (text == null) {
			text = section.document.unTaggedText.substring(start, end);
		}
		return text;
	}

	public void linkToken() {
		IToken prevToken = null;
		for (IToken token : tokens) {
			if (prevToken != null) {
				prevToken.next = token;
				token.prev = prevToken;
			}
			prevToken = token;
		}
	}

	private Set<String> tokenLemmaLCs;

	public Set<String> getLemmaLCs() {
		if (tokenLemmaLCs == null) {
			tokenLemmaLCs = new HashSet<>();
			for (IToken token : tokens) {
				tokenLemmaLCs.add(token.lemmaLC);
			}
		}

		return tokenLemmaLCs;
	}

	public String toLinkedString() {
		IToken token = tokens.get(0);
		StringBuffer buf = new StringBuffer();
		while (token != null) {
			if(buf.length()>0)
				buf.append(" -- ");
			buf.append(token.normText);
			token = token.next;
		}
		return buf.toString();
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();

		for (IToken token : tokens) {
			if (buf.length() > 0)
				buf.append(" ");
			buf.append(token.getText());
		}

		return buf.toString();
	}

	public String toString2() {
		return section.document.unTaggedText.substring(start, end);
	}
	
	int nonWhitespaceCount=-1;
	public  int countNonWhiteSpaceToken(){
		if(nonWhitespaceCount<0){
			nonWhitespaceCount=0;
			for(IToken token:tokens){
				if(!token.isNewline())
					nonWhitespaceCount++;
			}
		}
		return nonWhitespaceCount;
	}

}
