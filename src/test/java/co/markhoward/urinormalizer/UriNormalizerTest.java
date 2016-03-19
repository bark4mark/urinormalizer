package co.markhoward.urinormalizer;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

public class UriNormalizerTest {
	@Test
	public void testNullValue(){
		Optional<String> uri = UriNormalizer.normalizeUri(null);
		Assert.assertTrue(!uri.isPresent());
	}
	
	@Test
	public void testBadUri(){
		Optional<String> badUri = UriNormalizer.normalizeUri("htt123p://b a d");
		Assert.assertTrue(!badUri.isPresent());
	}
	
	@Test
	public void testCanBeInitialized(){
		UriNormalizer uriNormalizer = new UriNormalizer();
		Assert.assertTrue(uriNormalizer != null);
	}
	
	@Test
	public void testFragmentIsRemoved(){
		Optional<String> uri = UriNormalizer.normalizeUri("http://www.github.com#fragment", true);
		Assert.assertTrue(uri.get().equals("http://www.github.com"));
	}
	
	@Test
	public void testFragmentIsRemovedWithAFile(){
		Optional<String> uri = UriNormalizer.normalizeUri("http://www.github.com/page1.html#fragment", true);
		Assert.assertTrue(uri.get().equals("http://www.github.com/page1.html"));
	}
}
