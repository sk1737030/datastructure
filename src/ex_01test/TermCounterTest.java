package ex_01test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import ex_01.TermCounter;
import ex_01.WikiFetcher;

/*
 * set -> hashSet, treeSet
 * map이라는 객체는 기본 capacity = 16 , load_facotr = 0.75
 * bucket 크기 늘어 날 때, load_factor == 저장된 데이터 수 / capacity
 * 초기에 map의 크기를 어느정도 알때에는 capacity를 sizeup 해야된다.
 * rehasing이 일어나기 때문에.
 */

public class TermCounterTest {

	private TermCounter counter;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";
		
		WikiFetcher wf = new WikiFetcher();
		Elements paragraphs = wf.readWikipedia(url);
		
		counter = new TermCounter(url.toString());
		counter.processElements(paragraphs);
	}

	@Test
	public void testSize() {
		assertThat(counter.size(), is(4798));
	}
}