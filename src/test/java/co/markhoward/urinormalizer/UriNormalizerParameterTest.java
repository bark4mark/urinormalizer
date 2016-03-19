package co.markhoward.urinormalizer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class UriNormalizerParameterTest {
	private String uri;
	private String expected;
	
	@Parameters
	public static Collection<String[]> decentUris(){
		String[] test1 = {"http://google.com", "http://google.com"};
		String[] test2 = {"http://stackoverflow.com/questions/497908/is-a-url-allowed-to-contain-a-space", "http://stackoverflow.com/questions/497908/is-a-url-allowed-to-contain-a-space"};
		String[] test3 = {"http://stackoverflow.com/questions/497908/is a url allowed to contain a space", "http://stackoverflow.com/questions/497908/is%20a%20url%20allowed%20to%20contain%20a%20space"};
		String[] test4 = {"https://www.rackspace.co.uk/sites/default/files/Human Cloud at Work.pdf", "https://www.rackspace.co.uk/sites/default/files/Human%20Cloud%20at%20Work.pdf"};
		String[] test5 = {"google.com", "http://google.com"};
		String[] test6 = {"HtTp://github.com", "http://github.com"};
		String[] test7 = {"http://www.github.com/", "http://www.github.com"};
		String[] test8 = {"//www.github.com/main.css", "http://www.github.com/main.css"};
		String[] test9 = {"//www.github.com/Main.css", "http://www.github.com/Main.css"};
		String[] test10 = {"http://www.github.com/index.html", "http://www.github.com"};
		String[] test11 = {"http://www.github.com/index.html#fragment", "http://www.github.com/index.html#fragment"};
		String[] test12 = {"http://www.github.com#fragment", "http://www.github.com#fragment"};
		
		List<String[]> params = new ArrayList<>();
		params.add(test1);
		params.add(test2);
		params.add(test3);
		params.add(test4);
		params.add(test5);
		params.add(test6);
		params.add(test7);
		params.add(test8);
		params.add(test9);
		params.add(test10);
		params.add(test11);
		params.add(test12);
		return params;
	}
	
	public UriNormalizerParameterTest(String uri, String expected){
		this.uri = uri;
		this.expected = expected;
	}
	
	@Test
	public void shouldNormalizeDecentUri(){
		Optional<String> uri = UriNormalizer.normalizeUri(this.uri);
		Assert.assertTrue(uri.isPresent());
		Assert.assertTrue(uri.get().equals(expected));
	}
}
