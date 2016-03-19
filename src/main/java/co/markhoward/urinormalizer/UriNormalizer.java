package co.markhoward.urinormalizer;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Sets;

/**
 * A class to normalize a URI, fixing common mistakes
 */
public class UriNormalizer {
	/**
	 * Normalize a URI using some defaults
	 * 
	 * @param uri
	 *            The URI to normalize
	 * @return The normalized URI
	 */
	public static Optional<String> normalizeUri(String uri) {
		return normalizeUri(uri, false);
	}

	/**
	 * Normalize a URI using some defaults but remove the fragment
	 * 
	 * @param uri
	 *            The URI to normalize
	 * @param removeFragment
	 *            True if the fragment is to be removed
	 * @return The normalized URI
	 */
	public static Optional<String> normalizeUri(String uri, boolean removeFragment) {
		Set<String> defaultFilenames = Sets.newHashSet(DEFAULTS);
		return normalizeUri(uri, defaultFilenames, removeFragment);
	}

	/**
	 * Normalize a URI and remove the default filename
	 * 
	 * @param uri
	 *            The URI to normalize
	 * @param defaultFilenames
	 *            A Set of default filenames
	 * @return The normalized URI
	 */
	public static Optional<String> normalizeUri(String uri, Set<String> defaultFilenames) {
		return normalizeUri(uri, defaultFilenames, false);
	}

	/**
	 * Normalize a URI and remove the default filename and fragment
	 * 
	 * @param uri
	 *            The URI to normalize
	 * @param defaultFilenames
	 *            A Set of default filenames
	 * @param removeFragment
	 *            True if the fragment is to be removed
	 * @return The normalized URI
	 */
	public static Optional<String> normalizeUri(String uri, Set<String> defaultFilenames, boolean removeFragment) {
		if (uri == null) {
			return Optional.empty();
		}

		if (uri.endsWith(SLASH))
			uri = uri.substring(0, uri.length() - 1);

		uri = uri.trim();

		uri = uri.replaceAll("/\\.{1,2}/", "/");
		uri = uri.replaceFirst("(^[^/]+//[^/:]+):80([$/])", "$1$2");
		uri = uri.replaceAll(" ", "%20");

		try {
			URI dirtyUri = new URI(uri);
			URI cleanUri = new URI(
					StringUtils.isBlank(dirtyUri.getScheme()) ? null : dirtyUri.getScheme().toLowerCase(),
					dirtyUri.getUserInfo(),
					StringUtils.isBlank(dirtyUri.getHost()) ? null : dirtyUri.getHost().toLowerCase(),
					dirtyUri.getPort(), dirtyUri.getPath(), dirtyUri.getQuery(),
					removeFragment ? null : dirtyUri.getFragment());
			uri = cleanUri.toASCIIString();
		} catch (URISyntaxException exception) {
			return Optional.empty();
		}

		if (!uri.startsWith(HTTP) && !uri.startsWith(HTTPS)) {
			if (uri.startsWith(DOUBLE_SLASH)) {
				uri = uri.substring(2);
			}
			uri = String.format("%s//%s", HTTP, uri);
		}

		for (String defaultFilename : defaultFilenames) {
			if (uri.endsWith(defaultFilename)) {
				uri = uri.substring(0, uri.length() - defaultFilename.length());
				break;
			}
		}

		return Optional.of(uri);
	}

	private static final String HTTP = "http:";
	private static final String HTTPS = "https:";
	private static final String SLASH = "/";
	private static final String DOUBLE_SLASH = "//";
	private static final String[] DEFAULTS = { "/index.html", "/default.html" };
}
