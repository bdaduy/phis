package edu.db.tool.deid.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.db.tool.deid.document.IAnnotation;
import edu.db.tool.deid.document.IDocument;
import edu.db.tool.deid.utils.FileUtil;

import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;
import org.ahocorasick.trie.Trie.TrieBuilder;
/**
 *
 * @author Duy Bui
 */
public class Dictionary {
	public String inputFile;
	public List<String> terms;

	public Trie trie;

	public Dictionary(String[] filePaths, boolean removeOverlap, boolean onlyWholeWords, boolean caseSensitive) {
		Set<String> termSet = new HashSet<>();
		for (String path : filePaths) {
			termSet.addAll(FileUtil.readFileByline(path));
		}
		this.terms = new ArrayList<>(termSet);
		Collections.sort(this.terms);
		init(removeOverlap, onlyWholeWords, caseSensitive);
	}

	public Dictionary(List<String> terms, boolean removeOverlap, boolean onlyWholeWords, boolean caseSensitive) {
		this.terms = terms;
		init(removeOverlap, onlyWholeWords, caseSensitive);
	}
        public Dictionary(Set<String> terms, boolean removeOverlap, boolean onlyWholeWords, boolean caseSensitive) {
		this.terms = new ArrayList<String>(terms);
		init(removeOverlap, onlyWholeWords, caseSensitive);
	}

	public void init(boolean removeOverlap, boolean onlyWholeWords, boolean caseSensitive) {
		TrieBuilder builder = Trie.builder();
		if (removeOverlap)
			builder = builder.removeOverlaps();
		if (onlyWholeWords)
			builder = builder.onlyWholeWords();
		if (!caseSensitive)
			builder = builder.caseInsensitive();
                
		for (String term : terms) {
			builder.addKeyword(term.trim().replaceAll("\\s+", " "));
		}
		trie = builder.build();
	}

	public List<IAnnotation> matchDoc(IDocument doc) {
		List<IAnnotation> annotations = new ArrayList<>();

		Collection<Emit> emits = trie.parseText(doc.getText());

		for (Emit emit : emits) {
                        //Debug.print("E:"+emit);
			int start = emit.getStart();
			int end = emit.getEnd()+1;
			IAnnotation ann = new IAnnotation(start, end, doc.unTaggedText.substring(start, end), null);
			annotations.add(ann);

		}

		return annotations;

	}

	public void match(IDocument doc) {
		Collection<Emit> emits = trie.parseText(doc.getText());

	}

	
}
