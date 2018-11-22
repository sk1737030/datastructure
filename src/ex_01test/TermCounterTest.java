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
 * map�̶�� ��ü�� �⺻ capacity = 16 , load_facotr = 0.75
 * bucket ũ�� �þ� �� ��, load_factor == ����� ������ �� / capacity
 * �ʱ⿡ map�� ũ�⸦ ������� �˶����� capacity�� sizeup �ؾߵȴ�.
 * rehasing�� �Ͼ�� ������.
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