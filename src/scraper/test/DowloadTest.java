package scraper.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import scraper.util.CachedURLConnection;


public class DowloadTest {
	
	
	public static void main(String[] args) {
		String url = "http://www.google.com/";
		
		try {
			
			CachedURLConnection cache = new CachedURLConnection(new URL(url));	
	        String content = cache.getString();
	        
	        System.out.println(content);
	      
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
