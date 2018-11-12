package ex_01;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WikiPhilosophy {

	final static List<String> visited = new ArrayList<String>();
	final static WikiFetcher wf = new WikiFetcher();

	/*
	 * Tests a conjecture about Wikipedia and Philosophy.
	 *
	 * https://en.wikipedia.org/wiki/Wikipedia:Getting_to_Philosophy
	 *
	 * 1. Clicking on the first non-parenthesized, non-italicized link 2. Ignoring
	 * external links, links to the current page, or red links 3. Stopping when
	 * reaching "Philosophy", a page with no links or a page that does not exist, or
	 * when a loop occurs
	 *
	 * @param args
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String destination = "https://en.wikipedia.org/wiki/Philosophy";
		String source = "https://en.wikipedia.org/wiki/Java_(programming_language)";

		testConjecture(destination, source, 10);
	}

	/**
	 * Starts from given URL and follows first link until it finds the destination
	 * or exceeds the limit.
	 *
	 * @param destination
	 * @param source
	 * @throws IOException
	 */
	public static void testConjecture(String destination, String source, int limit) throws IOException {
		// TODO: FILL THIS IN!

		String url = source;
		for (int i = 0; i < limit; i++) {
			if (visited.contains(url)) {
				System.err.println("We're in a loop, exiting.");
				return;
			} else {
				visited.add(url);
			}
			Element elt = getFirstElement(url);

			//abs:href get absolute addr 
			url = elt.attr("abs:href");
			System.out.println(url);
			if (url.equals(destination)) {
				System.out.println("ureka!");
				break;
			}
		}

		for (int i = 0; i < visited.size(); i++)
			System.out.println(visited.get(i));
	}

	private static Element getFirstElement(String url) throws IOException {
		// TODO Auto-generated method stub
		WikiFetcher wk = new WikiFetcher();
		Elements ele = wk.fetchWikipedia(url);
		WikiPaser wiki = new WikiPaser(ele);
		Element elt = wiki.findFirstLink();

		return elt;
	}

}