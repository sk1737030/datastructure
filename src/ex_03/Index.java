package ex_03;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.jsoup.select.Elements;

/*
 * set -> hashSet, treeSet
 * map�̶�� ��ü�� �⺻ capacity = 16 , load_facotr = 0.75
 * bucket ũ�� �þ� �� ��, load_factor == ����� ������ �� / capacity
 * �ʱ⿡ map�� ũ�⸦ ������� �˶����� capacity�� sizeup �ؾߵȴ�.
 * rehasing�� �Ͼ�� ������.
 */
public class Index {

	private Map<String, Set<TermCounter>> index = new HashMap<String, Set<TermCounter>>();

	/**
	 * Adds a TermCounter to the set associated with `term`.
	 *
	 * @param term
	 * @param tc
	 */
	public void add(String term, TermCounter tc) {
		Set<TermCounter> set = get(term); //return index.get(term);

		// if we're seeing a term for the first time, make a new Set
		if (set == null) {
			set = new HashSet<TermCounter>();
			index.put(term, set); //�ʿ� term�� url�� �ִ´� ���� url�� �ȵ��ִ�.
		}// otherwise we can modify an existing Set
		set.add(tc); //set�� url�� ���� ������ set�� �����Ѵ�.
	}

	/**
	 * Looks up a search term and returns a set of TermCounters.
	 *
	 * @param term
	 * @return
	 */
	public Set<TermCounter> get(String term) {
		return index.get(term);
	}

	/**
	 * Prints the contents of the index.
	 */
	public void printIndex() {
		// loop through the search terms
		for (String term : keySet()) {
			System.out.println(term);

			// for each term, print the pages where it appears
			Set<TermCounter> tcs = get(term);
			for (TermCounter tc : tcs) {
				Integer count = tc.get(term);
				System.out.println("    " + tc.getLabel() + " " + count);
			}
		}
	}

	/**
	 * Returns the set of terms that have been indexed.
	 *
	 * @return
	 */
	public Set<String> keySet() {
		return index.keySet();
	}

	/**
	 * Add a page to the index.
	 *
	 * @param url
	 *            URL of the page.
	 * @param paragraphs
	 *            Collection of elements that should be indexed.
	 */
	public void indexPage(String url, Elements paragraphs) {
		// TODO: Your code here
		// make a TermCounter and count the terms in the paragraphs
		TermCounter tc = new TermCounter(url);
		tc.processElements(paragraphs);

		// for each term in the TermCounter, add the TermCounter to the index
		for (String s : tc.keySet()) {
			add(s, tc);
		}

	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		WikiFetcher wf = new WikiFetcher();
		Index indexer = new Index();

		String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";
		Elements paragraphs = wf.fetchWikipedia(url);
		indexer.indexPage(url, paragraphs);

		url = "https://en.wikipedia.org/wiki/Programming_language";
		paragraphs = wf.fetchWikipedia(url);
		indexer.indexPage(url, paragraphs);

		indexer.printIndex();
		
		System.out.println(indexer.keySet());
		Set<TermCounter> set = indexer.get("the");
		Iterator<TermCounter> iter = set.iterator();
		while(iter.hasNext()) {
			TermCounter term = iter.next();
			System.out.println(term.get("the"));
}
	
	}
}