package ex_05_search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import ex_04.JedisIndex;
import helper.JedisMaker;
import redis.clients.jedis.Jedis;

/**
 * Represents the results of a search query.
 *
 */
public class WikiSearch {

	// map from URLs that contain the term(s) to relevance score
	private Map<String, Integer> map;

	/**
	 * Constructor.
	 *
	 * @param map
	 */
	public WikiSearch(Map<String, Integer> map) {
		this.map = map;
	}

	/**
	 * Looks up the relevance of a given URL.
	 *
	 * @param url
	 * @return
	 */
	public Integer getRelevance(String url) {
		Integer relevance = map.get(url);
		return relevance == null ? 0 : relevance;
	}

	/**
	 * Prints the contents in order of term frequency.
	 *
	 * @param
	 */
	private void print() {
		List<Entry<String, Integer>> entries = sort();
		for (Entry<String, Integer> entry : entries) {
			System.out.println(entry);
		}
	}

	/**
	 * Computes the union of two search results.
	 *
	 * @param that
	 * @return New WikiSearch object.
	 */
	public WikiSearch or(WikiSearch that) {
		// TODO: FILL THIS IN!
		Map<String, Integer> searchMap = new HashMap<String, Integer>();

		for (String str : map.keySet()) {
			searchMap.put(str, map.get(str));
		}

		for (String str : that.map.keySet()) {
			searchMap.put(str, totalRelevance(searchMap.get(str), that.map.get(str)));
		}

		WikiSearch search = new WikiSearch(searchMap);
		return search;
	}

	/**
	 * Computes the intersection of two search results.
	 *
	 * @param that
	 * @return New WikiSearch object.
	 */
	public WikiSearch and(WikiSearch that) {
		// TODO: FILL THIS IN!
		Map<String, Integer> searchMap = new HashMap<String, Integer>();
		Map<String, Integer> thatMap = that.map;

		for (String str : map.keySet()) {
			if (thatMap.containsKey(str)) {
				searchMap.put(str, totalRelevance(map.get(str), thatMap.get(str)));
			}
		}

		WikiSearch search = new WikiSearch(searchMap);
		return search;
	}

	/**
	 * Computes the intersection of two search results.
	 *
	 * @param that
	 * @return New WikiSearch object.
	 */
	public WikiSearch minus(WikiSearch that) {
		// TODO: FILL THIS IN!
		Map<String, Integer> searchMap = new HashMap<String, Integer>();
		Map<String, Integer> thatMap = that.map;

		for (String str : map.keySet()) {
			if (!thatMap.containsKey(str)) {
				searchMap.put(str, map.get(str));
			}
		}

		WikiSearch search = new WikiSearch(searchMap);
		return search;
	}

	/**
	 * Computes the relevance of a search with multiple terms.
	 *
	 * @param rel1:
	 *            relevance score for the first search
	 * @param rel2:
	 *            relevance score for the second search
	 * @return
	 */
	protected int totalRelevance(Integer rel1, Integer rel2) {
		// simple starting place: relevance is the sum of the term frequencies.
		if (rel1 == null)
			rel1 = 0;
		return rel1 + rel2;
	}

	/**
	 * Sort the results by relevance.
	 *
	 * @return List of entries with URL and relevance.
	 */
	public List<Entry<String, Integer>> sort() {
		// TODO: FILL THIS IN!
		List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>();

		for (Entry<String, Integer> entry : map.entrySet()) {
			list.add(entry);
		}
		//entry���� �߰��Ҷ� map.entrySet Ű���带 ���;
		Comparator<Entry<String, Integer>> comparator = new Comparator<Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> arg0, Entry<String, Integer> arg1) {
				// TODO Auto-generated method stub
	
				if (arg0.getKey().compareTo(arg1.getKey()) > 0)
					return 1;
				else if (arg0.getKey().compareTo(arg1.getKey()) < 0)
					return -1;
				/* return ���� 0 ���� ���������� arg0 �� �����ο´�.
				 * return ���� 0 ���� Ŭ������ arg0 ������ arg1�� �´�.
				 */
				return 0;
			}
		};

		Collections.sort(list, comparator);
		ListIterator<Entry<String, Integer>> iter = list.listIterator();
		while (iter.hasNext()) {
			System.out.println(iter.next());
		}
		return list;
	}

	/**
	 * Performs a search and makes a WikiSearch object.
	 *
	 * @param term
	 * @param index
	 * @return
	 */
	public static WikiSearch search(String term, JedisIndex index) {
		Map<String, Integer> map = index.getCounts(term);
		return new WikiSearch(map);
	}

	public static void main(String[] args) throws IOException {

		// make a JedisIndex
		Jedis jedis = JedisMaker.make();
		JedisIndex index = new JedisIndex(jedis);

		// search for the first term
		String term1 = "java";
		System.out.println("Query: " + term1);
		WikiSearch search1 = search(term1, index);
		search1.print();

		// search for the second term
		String term2 = "programming";
		System.out.println("Query: " + term2);
		WikiSearch search2 = search(term2, index);
		search2.print();

		// compute the intersection of the searches
		System.out.println("Query: " + term1 + " AND " + term2);
		WikiSearch intersection = search1.and(search2);
		intersection.print();
	}
}