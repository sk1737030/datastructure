package ex_01;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

/**
 * 
 * @author sk173 seperate firstlink
 */

public class WikiPaser {
	private Elements paragraphs;
	private Deque<String> parenthesisStack;

	public WikiPaser(Elements paras) {
		this.paragraphs = paras;
		parenthesisStack = new ArrayDeque<String>();
	}

	public Element findFirstLink() {
		for (Element paragraph : paragraphs) {
			Element firstLink = findFirstLinkPara(paragraph);
			if (firstLink != null) {
				return firstLink;
			}
			if (!parenthesisStack.isEmpty()) {
				System.err.println("Warning: unbalanced parentheses.");
			}
		}
		return null;
	}

	//
	private Element findFirstLinkPara(Node root) {
		// TODO Auto-generated method stub.
		// create an Iterable that traverses the tree
		Iterable<Node> iter = new WikiNodeIterable(root);

		for (Node node : iter) {
			if (node instanceof TextNode)
				//for removing link in there like "(,)" 
				processTextNode((TextNode) node);
			if (node instanceof Element) {
				Element firstLink = processElement((Element) node);
				if (firstLink != null) {
					return firstLink;
				}
			}
		}

		return null;
	}

	/*
	 * Returns the element if it is a valid link, null otherwise.
	 * 
	 */
	private Element processElement(Element elt) {
		// TODO Auto-generated method stub
		if (vaildLink(elt))
			return elt;
		return null;
	}

	private boolean vaildLink(Element node) {
		// TODO Auto-generated method stub
		if (!node.tagName().equals("a"))
			return false;
		if (isItalic(node))
			return false;
		return true;
	}

	private boolean isItalic(Element start) {
		// TODO Auto-generated method stub
		for (Element elt = start; elt != null; elt = elt.parent())
			if (elt.tagName().equals("i") || elt.tagName().equals("em"))
				return true;
		return false;
	}

	/*
	 * checks whet her a link is value
	 * 
	 * @param node StringTokenizer put String
	 */
	private void processTextNode(TextNode node) {
		// TODO Auto-generated method stub
		StringTokenizer st = new StringTokenizer(node.text(), "()", true);
		while (st.hasMoreTokens()) {
			String data = st.nextToken();
			if (st.equals("("))
				System.out.println(data);
				parenthesisStack.push(data);
			if (st.equals(")")) {
				if (parenthesisStack.isEmpty())
					System.err.println("Warning: unbalanced parentheses.");
				parenthesisStack.pop();
			}
		}

	}
}
