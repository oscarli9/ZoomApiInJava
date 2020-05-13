package zoomapi;

import org.junit.Assert;
import org.junit.Test;

public class ApiClientUnitTest extends Assert {
    private final Util.ApiClient client = new Util.ApiClient("www.google.com", 15, null);

    @Test
    public void testBaseUrl() {
        assertEquals("www.google.com", client.getBaseUrl());
        client.setBaseUrl("movie.douban.com");
        assertEquals("movie.douban.com", client.getBaseUrl());
    }

    @Test
    public void testTimeout() {
        assertEquals(15, client.getTimeout());
        client.setTimeout(20);
        assertEquals(20, client.getTimeout());
    }

    @Test
    public void startsWithSlash() {
        String endpoint = "/test_url";
        String finalUrl = client.urlFor(endpoint);
        assertEquals("www.google.com/test_url", finalUrl);
    }

    @Test
    public void endsWithSlash() {
        String endpoint = "test_url/";
        String finalUrl = client.urlFor(endpoint);
        assertEquals("www.google.com/test_url", finalUrl);
    }

    @Test
    public void startsWithoutSlashAndEndWithoutSlash() {
        String endpoint = "test_url/";
        String finalUrl = client.urlFor(endpoint);
        assertEquals("www.google.com/test_url", finalUrl);
    }

    @Test
    public void startsWithSlashAndEndWithSlash() {
        String endpoint = "/test_url/";
        String finalUrl = client.urlFor(endpoint);
        assertEquals("www.google.com/test_url", finalUrl);
    }

    @Test
    public void getResponseTest() {
        Util.ApiClient client = new Util.ApiClient("http://webcode.me", 15, null);
        System.out.println(client.urlFor(""));
        System.out.println(client.getRequest(""));
    }
}
