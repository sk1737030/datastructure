package ex_01;

/*
 *	ũ�Ѹ� �Ŀ� �˻��� index �� counter��� ���Ŀ� 
 * 	�ð����⵵ ���.  
 */

import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

public class TermCounter {
	private Map<String, Integer> map;
	private String label;

	public TermCounter(String label) {
		this.label = label;
		this.map = new HashMap<String, Integer>();
	}

	public void put(String term, int count) {
		map.put(term, count);
	}

	public Integer get(String term) {
		Integer count = map.get(term);
		return count == null ? 0 : count;
	}

	public void incrementTermCount(String term) {
		put(term, get(term) + 1);
	}

	public void processElements(Elements paragraphs) {
		for (Node node : paragraphs)
			processTree(node);
	}

	private void processTree(Node root) {
		// TODO Auto-generated method stub
		for (Node node : new WikiNodeIterable(root)) {
			if (node instanceof TextNode)
				processText(((TextNode) node).text());
		}
	}

	private void processText(String text) {
		// TODO Auto-generated method stub
		String[] array = text.replaceAll("\\pP", " ").toLowerCase().split("\\s+");

		for (int i = 0; i < array.length; i++) {
			String term = array[i];
			incrementTermCount(term);
		}

	}
}
