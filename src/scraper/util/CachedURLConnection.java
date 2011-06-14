package scraper.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @author rap
 *
 */
public class CachedURLConnection {
	protected static String IE7 = "Mozilla/4.0 (compatible; MSIE 7.0b; Windows NT 6.0)";
	protected static String ICEWEASEL = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.4) Gecko/20070508 Iceweasel/2.0.0.4 (Debian-2.0.0.4-1)";
	protected static String SEMLIST = "SemList";
	protected static String FIREFOX = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.4) Gecko/20061201 Firefox/2.0.0.4 (Ubuntu-feisty)";
	private URL url;
	private String filename;
	private String useragent;
	private boolean cached = true;
	private static int maxthreads = 1;
	private final static int bufferSize = 4096;
	private final static String dirname = "/tmp/scraper-cache";
	
	
	public InputStream getInputStream() throws IOException {
	    	//StringBuffer out = new StringBuffer();
		InputStream is = null;
	    	try {
				File file = new File(filename);
				if (!file.exists()) {
					this.getFromInet();
				}
				 is   = new FileInputStream(file);
		   
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				throw new IOException();
			}
			return is;
	    }
	
	public String getString() throws IOException {
		StringBuffer out = new StringBuffer();
			InputStream is = this.getInputStream();
			byte[] b = new byte[bufferSize];
		    for (int n; (n = is.read(b)) != -1;) {
		        out.append(new String(b, 0, n));
		    }
		    return out.toString();
	}
	
	public void useCache(boolean value) {
		this.cached = value;
	}
	/**
	 * @param url
	 * @return
	 */
	public CachedURLConnection(URL url) {
//		return getFromInet(url);
		this.url = url;
		this.useragent = ICEWEASEL;

		try {
			filename = dirname+"/"+URLEncoder.encode(this.url.toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    /**
     * @param url
     * @return
     */
    private String getFromInet() {
//    	Semaphore sem = new Semaphore(4);
//    	 
//    	   // code that needs constraining to four active threads:
//    	   try {
//			sem.acquire();
//		
//    	   } catch (InterruptedException e1) {
//   			// TODO Auto-generated catch block
//   			e1.printStackTrace();
//   		
//    	   } finally {
//    	    sem.release();
//    	  }
    	StringBuffer out = new StringBuffer();
    	HttpURLConnection http;
		try {
			http = (HttpURLConnection)this.url.openConnection();
		http.addRequestProperty("User-Agent", this.useragent);
		http.setDoInput(true);
		http.setDoOutput(true);
		String encoding = http.getContentEncoding();
		//http.setAllowUserInteraction(true);
		//int response = http.getResponseCode();
		//System.out.println("Response: " + response);
		InputStream is = http.getInputStream();
		
	
		//System.out.println("Encoding: "+encoding);
		File dir = new File(dirname);
		if (!dir.exists()) {
			dir.mkdir();
		}
		
		
		File file = new File(filename);
		if (!file.exists()) {
			file.createNewFile();
		} else if (!this.cached) {
			file.delete();
			file.createNewFile();
			
		}
		FileOutputStream fout = new FileOutputStream(file);
	   
	    byte[] buffer = new byte[bufferSize];
	    int readCount = 0;
	    while ((readCount = is.read(buffer)) != -1) { 
	      if (readCount < bufferSize) {
	        fout.write(buffer, 0, readCount);
	      } else {
	        fout.write(buffer);
	      }
	    }

		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out.toString();
    }
  
//    System.out.print(out.toString());
	
	
}
