package ex_04;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import helper.JedisMaker;
import helper.WikiFetcher;
import redis.clients.jedis.Jedis;

/**
 * 
 * @author sk173 crawler queue에 삽입하여서 index 되어있으면 add 아니면 poll 한다.
 * 
 */
public class WikiCrawler {
	// keeps track of where we started
	@SuppressWarnings("unused")
	private final String source;

	// the index where the results go
	private JedisIndex index;

	// queue of URLs to be indexed
	private Queue<String> queue = new LinkedList<String>();

	// fetcher used to get pages from Wikipedia
	final static WikiFetcher wf = new WikiFetcher();

	/**
	 * Constructor.
	 *
	 * @param source
	 * @param index
	 */
	public WikiCrawler(String source, JedisIndex index) {
		this.source = source;
		this.index = index;
		queue.offer(source);
	}

	/**
	 * Returns the number of URLs in the queue.
	 *
	 * @return
	 */
	public int queueSize() {
		return queue.size();
	}

	/**
	 * Gets a URL from the queue and indexes it.
	 * 
	 * @param testing
	 *
	 * @return URL of page indexed.
	 * @throws IOException
	 *             queue 가 empty인지 검사한다 queue에 첫번째 위치에서 url을 꺼내온다 testing이 true이면서
	 *             인덱싱되어있으면 null반환
	 * 
	 */
	public String crawl(boolean testing) throws IOException {
		// TODO: FILL THIS IN!
		if (queue.isEmpty())
			return null;
		String url = queue.poll();
		System.out.println("crwaling : " + url);

		Elements ele;
		if (testing) {
			ele = wf.readWikipedia(url);
		} else {
			if (index.isIndexed(url))
				return null;
			ele = wf.fetchWikipedia(url);
		}

		index.indexPage(url, ele);
		queueInternalLinks(ele);

		return url;
	}

	/**
	 * Parses paragraphs and adds internal links to the queue.
	 * 
	 * @param paragraphs
	 */
	// NOTE: absence of access level modifier means package-level
	// queue에 링크들을 추가한다 element 속성 href 있는 것들을 가져온다.
	public void queueInternalLinks(Elements paragraphs) {
		// TODO: FILL THIS IN!
		for (Element ele : paragraphs) {
			queueInternalLink(ele);
		}
	}
	//Element.attr = href 안에있는 속성들을 가져온다.
	private void queueInternalLink(Element paragraph) {
		// TODO Auto-generated method stub
		Elements elts = paragraph.select("a[href]");
		//css selector 연사자 a[href] 연산자를 가져온다. return elts;
		//System.out.println(paragraph);
		for(Element ele : elts) {
			String realUrl = paragraph.attr("href");
			if(realUrl.startsWith("/wiki")) {
				String absUrl = paragraph.attr("abs:href");
				//String relHref = link.attr("href"); // == "/"
				//String absHref = link.attr("abs:href"); // "http://jsoup.org/"
				//절대경로를 가지고온다
				System.out.println(absUrl);
				queue.offer(absUrl);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		// make a WikiCrawler
		Jedis jedis = JedisMaker.make();
		JedisIndex index = new JedisIndex(jedis);
		String source = "https://en.wikipedia.org/wiki/Java_(programming_language)";
		WikiCrawler wc = new WikiCrawler(source, index);

		// for testing purposes, load up the queue
		Elements paragraphs = wf.fetchWikipedia(source);
		wc.queueInternalLinks(paragraphs);

		// loop until we index a new page
		String res;
		do {
			res = wc.crawl(false);

			// REMOVE THIS BREAK STATEMENT WHEN crawl() IS WORKING
			break;
		} while (res == null);

		Map<String, Integer> map = index.getCounts("the");
		for (Entry<String, Integer> entry : map.entrySet()) {
			System.out.println(entry);
		}
	}
}