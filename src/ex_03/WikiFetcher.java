package ex_03;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*
 * @author sk173
 *	1. after download wikipedia html, encaptulate code
 *	2. for getting enough time, sleep action 
 */
public class WikiFetcher {
	private long lastRequestTime = -1;
	private long minInterval = 1000;

	/*
	 * 1.url을 파싱하고 본문을 가져온다 2.단락 요소의 리스트를 반환
	 */
	public Elements fetchWikipedia(String url) throws IOException {
		sleepIfNeeded();
		
		Connection conn = Jsoup.connect(url);
		Document doc = conn.get();
		Element content = doc.getElementById("mw-content-text");

		// TODO: avoid selecting paragraphs from sidebars and boxouts
		Elements paras = content.select("p");
	
		return paras;
	}

	private void sleepIfNeeded() {
		if (lastRequestTime != -1) {
			long currentTime = System.currentTimeMillis();
			long nextRequestTime = lastRequestTime + minInterval;
			if (currentTime < nextRequestTime) {
				try {
					Thread.sleep(nextRequestTime - currentTime);
				} catch (InterruptedException e) {
					System.err.println("Warning: sleep interrupted in fetchWikipedia.");
				}
			}
		}
		lastRequestTime = System.currentTimeMillis();
	}

	public Elements readWikipedia(String url) throws IOException {
		URL realURL = new URL(url);

		// assemble the file name
		String slash = File.separator;
	 	String filename = "resources" + slash + realURL.getHost()+"wiki" + realURL.getPath()+".txt";
	 	
		// read the file
		InputStream stream = WikiFetcher.class.getClassLoader().getResourceAsStream(filename);
		
		Document doc = Jsoup.parse(stream, "UTF-8", filename);

		// parse the contents of the file
		Element content = doc.getElementById("mw-content-text");
		Elements paras = content.select("a");
		return paras;
	}

	public static void main(String[] args) throws IOException {
		WikiFetcher wf = new WikiFetcher();
		String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";
		Elements paragraphs = wf.readWikipedia(url);

		for (Element paragraph : paragraphs) {
			System.out.println(paragraph);
		}
	}
}
