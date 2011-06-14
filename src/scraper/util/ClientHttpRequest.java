package scraper.util;

import java.net.URLConnection;
import java.net.URL;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Random;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.util.Iterator;

/**
 * <p>Title: Client HTTP Request class</p>
 * <p>Description: this class helps to send POST HTTP requests with various form data,
 * including files. Cookies can be added to be included in the request.</p>
 *
 * @author Vlad Patryshev
 * @version 1.0
 */
public class ClientHttpRequest {
  URLConnection connection;
  OutputStream os = null;
  Map<String, String> cookies = new HashMap<String, String>();
  String useragent;
  
/*
	protected static String IE7 = "Mozilla/4.0 (compatible; MSIE 7.0b; Windows NT 6.0)";
	protected static String ICEWEASEL = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.4) Gecko/20070508 Iceweasel/2.0.0.4 (Debian-2.0.0.4-1)";
	protected static String SEMLIST = "SemList";

  protected void connect() throws IOException {

		connection.addRequestProperty("User-Agent", useragent);
    if (os == null) os = connection.getOutputStream();
  }

  protected void write(char c) throws IOException {
    connect();
    os.write(c);
  }

  protected void write(String s) throws IOException {
    connect();
    os.write(s.getBytes());
  }

  protected void newline() throws IOException {
    connect();
    write("\r\n");
  }

  protected void writeln(String s) throws IOException {
    connect();
    write(s);
    newline();
  }

  private static Random random = new Random();

  protected static String randomString() {
    return Long.toString(random.nextLong(), 36);
  }

  String boundary = "---------------------------" + randomString() + randomString() + randomString();

  private void boundary() throws IOException {
    write("--");
    write(boundary);
  }

  /**
   * Creates a new multipart POST HTTP request on a freshly opened URLConnection
   *
   * @param connection an already open URL connection
   * @throws IOException
   */
  public ClientHttpRequest(URLConnection connection) throws IOException {
    this.connection = connection;
    connection.setDoOutput(true);
    connection.setRequestProperty("Content-Type",
                                  "multipart/form-data; boundary=" + boundary);
  }

  /**
   * Creates a new multipart POST HTTP request for a specified URL
   *
   * @param url the URL to send request to
   * @throws IOException
   */
  public ClientHttpRequest(URL url) throws IOException {
    this(url.openConnection());
  }

  /**
   * Creates a new multipart POST HTTP request for a specified URL string
   *
   * @param urlString the string representation of the URL to send request to
   * @throws IOException
   */
  public ClientHttpRequest(String urlString) throws IOException {
    this(new URL(urlString));
  }

  /**
   * 
   *
   */
  public void postCookies() {
    StringBuffer cookieList = new StringBuffer();

    for (Iterator i = cookies.entrySet().iterator(); i.hasNext();) {
      Map.Entry entry = (Map.Entry)(i.next());
      cookieList.append(entry.getKey().toString() + "=" + entry.getValue());

      if (i.hasNext()) {
        cookieList.append("; ");
      }
    }
    if (cookieList.length() > 0) {
      connection.setRequestProperty("Cookie", cookieList.toString());
    }
  }

  /**
   * adds a cookie to the requst
   * @param name cookie name
   * @param value cookie value
   * @throws IOException
   */
  public void setCookie(String name, String value) throws IOException {
    cookies.put(name, value);
  }

  /**
   * adds cookies to the request
   * @param cookies the cookie "name-to-value" map
   * @throws IOException
   */
  public void setCookies(Map<String, String> cookies) throws IOException {
    if (cookies == null) return;
    this.cookies.putAll(cookies);
  }

  /**
   * adds cookies to the request
   * @param cookies array of cookie names and values (cookies[2*i] is a name, cookies[2*i + 1] is a value)
   * @throws IOException
   */
  public void setCookies(String[] cookies) throws IOException {
    if (cookies == null) return;
    for (int i = 0; i < cookies.length - 1; i+=2) {
      setCookie(cookies[i], cookies[i+1]);
    }
  }

  private void writeName(String name) throws IOException {
    newline();
    write("Content-Disposition: form-data; name=\"");
    write(name);
    write('"');
  }

  /**
   * adds a string parameter to the request
   * @param name parameter name
   * @param value parameter value
   * @throws IOException
   */
  public void setParameter(String name, String value) throws IOException {
    boundary();
    writeName(name);
    newline(); newline();
    writeln(value);
  }

  /**
   * 
   * @param in
   * @param out
   * @throws IOException
   */
  private static void pipe(InputStream in, OutputStream out) throws IOException {
    byte[] buf = new byte[500000];
    int nread;
    int total = 0;
    synchronized (in) {
      while((nread = in.read(buf, 0, buf.length)) >= 0) {
        out.write(buf, 0, nread);
        total += nread;
      }
    }
    out.flush();
    buf = null;
  }

  /**
   * adds a file parameter to the request
   * @param name parameter name
   * @param filename the name of the file
   * @param is input stream to read the contents of the file from
   * @throws IOException
   */
  public void setParameter(String name, String filename, InputStream is) throws IOException {
    boundary();
    writeName(name);
    write("; filename=\"");
    write(filename);
    write('"');
    newline();
    write("Content-Type: ");
    String type = URLConnection.guessContentTypeFromName(filename);
    if (type == null) type = "application/octet-stream";
    writeln(type);
    newline();
    pipe(is, os);
    newline();
  }

  /**
   * adds a file parameter to the request
   * @param name parameter name
   * @param file the file to upload
   * @throws IOException
   */
  public void setParameter(String name, File file) throws IOException {
    setParameter(name, file.getPath(), new FileInputStream(file));
  }

 
  /**
   * adds parameters to the request
   * @param parameters "name-to-value" map of parameters; if a value is a file, the file is uploaded, otherwise it is stringified and sent in the request
   * @throws IOException
   */
  public void setParameters(Map<String, String> parameters) throws IOException {
	  
	  //TODO Optimize for Map<String, String>
    if (parameters == null) return;
    for (Iterator i = parameters.entrySet().iterator(); i.hasNext();) {
      Map.Entry entry = (Map.Entry)i.next();
      setParameter(entry.getKey().toString(), entry.getValue().toString());
    }
  }

  /**
   * adds parameters to the request
   * @param parameters array of parameter names and values (parameters[2*i] is a name, parameters[2*i + 1] is a value); if a value is a file, the file is uploaded, otherwise it is stringified and sent in the request
   * @throws IOException
   */
  public void setParameters(Object[] parameters) throws IOException {
    if (parameters == null) return;
    for (int i = 0; i < parameters.length - 1; i+=2) {
      setParameter(parameters[i].toString(), parameters[i+1].toString());
    }
  }

  /**
   * posts the requests to the server, with all the cookies and parameters that were added
   * @return input stream with the server response
   * @throws IOException
   */
  public InputStream post() throws IOException {

	
    boundary();
    writeln("--");
    os.close();
    return connection.getInputStream();
  }

  /**
   * posts the requests to the server, with all the cookies and parameters that were added before (if any), and with parameters that are passed in the argument
   * @param parameters request parameters
   * @return input stream with the server response
   * @throws IOException
   */
  public InputStream post(Map<String, String> parameters) throws IOException {
    setParameters(parameters);
    return post();
  }

  /**
   * posts the requests to the server, with all the cookies and parameters that were added before (if any), and with parameters that are passed in the argument
   * @param parameters request parameters
   * @return input stream with the server response
   * @throws IOException
   */
  public InputStream post(Object[] parameters) throws IOException {
    setParameters(parameters);
    return post();
  }

  /**
   * posts the requests to the server, with all the cookies and parameters that were added before (if any), and with cookies and parameters that are passed in the arguments
   * @param cookies request cookies
   * @param parameters request parameters
   * @return input stream with the server response
   * @throws IOException
   */
  public InputStream post(Map<String, String> cookies, Map<String, String> parameters) throws IOException {
    setCookies(cookies);
    setParameters(parameters);
    return post();
  }

  /**
   * posts the requests to the server, with all the cookies and parameters that were added before (if any), and with cookies and parameters that are passed in the arguments
   * @param cookies request cookies
   * @param parameters request parameters
   * @return input stream with the server response
   * @throws IOException
   */
  public InputStream post(String[] cookies, Object[] parameters) throws IOException {
    setCookies(cookies);
    setParameters(parameters);
    return post();
  }
  
  /**
   * @param url
   * @return the StringBuffer
   * @throws Exception
   */   
   public static StringBuffer readDataToStringBuffer(URL url) throws Exception {
	
	   //todo THIS HTTP THING
	   /*
		StringBuffer data = null;
		BufferedInputStream stream;
		
		//long time = System.currentTimeMillis();
		
		SimpleHttpConnectionManager connectionManager = new SimpleHttpConnectionManager();
		
		HttpConnectionManagerParams params = connectionManager.getParams();
		
		//params.setSoTimeout(2000);
		params.setConnectionTimeout(8000);
		
		connectionManager.setParams(params);
		
		HttpClient client = new HttpClient(connectionManager);
	
		
		GetMethod method = new GetMethod(url.toString());
		//method.setDoAuthentication(true);
		
		int statusCode = client.executeMethod(method);
		
		if (statusCode == HttpStatus.SC_OK) {
		    
		    stream = new BufferedInputStream(method.getResponseBodyAsStream());
		    int buffer;
		    
		    /* Saves the page data in a string buffer... */
		    data = new StringBuffer();
		    
		    while ((buffer = stream.read()) != -1) {
			data.append((char) buffer);
		    }
		    
		    stream.close();
		}
		return data;
   }

   
   
   /**
    * @param url
    * @return the result
    * @throws Exception
    */
   public static byte [] readDataToByteArray(URL url) throws Exception {
	
	byte[] data = {-1};
	
	//long time = System.currentTimeMillis();
	
	SimpleHttpConnectionManager connectionManager = new SimpleHttpConnectionManager();
	
	HttpConnectionManagerParams params = connectionManager.getParams();
	params.setConnectionTimeout(2000);
	connectionManager.setParams(params);
	
	HttpClient client = new HttpClient(connectionManager);
	//HostConfiguration config = new HostConfiguration();


	GetMethod method = new GetMethod(url.toString());
	//method.setDoAuthentication(true);
	
	try {
	    
	    int statusCode = client.executeMethod(method);
	    
	    if (statusCode == HttpStatus.SC_OK) {
		
		BufferedInputStream  inputStream = new BufferedInputStream(method.getResponseBodyAsStream());
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream(inputStream.available());
		
		int buffer;
		while ((buffer = inputStream.read()) != -1)
		    byteStream.write(buffer);
		
		inputStream.close();
		data = byteStream.toByteArray();
	    }
	    
	} catch (Exception e) {
	    throw new Exception(e.getMessage());
	} finally {
	    method.releaseConnection();
	}
	
	return data;
   }
   
   
  // static void setProxySetting(HttpClient client) {
	
	//MovieManagerConfig mmc = MovieManager.getConfig();
	
	
	

   
   /**
    * @param fileName
    * @param data
    */
   public static void writeToFile(String fileName, StringBuffer data) {
	
		try {
		    
		    FileOutputStream fileStream = new FileOutputStream(new File(fileName));
		    for (int u = 0; u < data.length(); u++)
			fileStream.write(data.charAt(u));
		    fileStream.close();
		
		} catch (Exception e) {
		  //  log.error("Exception:"+ e.getMessage());
	}
   }
   
   

   /**
    * Decodes a html string and returns its unicode string.
 * @param toDecode 
 * @return decoded String
    */
   public static String decodeHTML(String toDecode) {
	String decoded = "";
		
	try {
	    int end = 0;
	    for (int i=0; i < toDecode.length(); i++) {
		if (toDecode.charAt(i)=='&' && toDecode.charAt(i+1)=='#' && (end=toDecode.indexOf(";", i))!=-1) {
		    decoded += (char)Integer.parseInt(toDecode.substring(i+2,end));
		    i = end;
		} else if (toDecode.charAt(i)=='<' && toDecode.indexOf('>', i) != -1) {
		    i = toDecode.indexOf('>', i);
		} else {
		    decoded += toDecode.charAt(i);
		}
	    }
	} catch (Exception e) {
	    //log.error("", e);
	} 
	/* Returns the decoded string... */
	return decoded.trim();
   }

   
   /**
    * Decodes a html string and returns its unicode string.
 * @param toDecode 
 * @return array with decoded HTML
    */
   public static Object [] decodeHTMLtoArray(String toDecode) {
   	ArrayList<String> decoded = new ArrayList<String>();
   	String tmp = "";
   	
   	try {
   		int end = 0;
   		for (int i=0; i < toDecode.length(); i++) {
   			if (toDecode.charAt(i)=='&' && toDecode.charAt(i+1)=='#' && (end=toDecode.indexOf(";", i)) != -1) {
   				tmp += (char) Integer.parseInt(toDecode.substring(i+2,end));
   				i = end;
   			} else if (toDecode.charAt(i)=='<' && toDecode.indexOf('>', i) != -1) {
   				i = toDecode.indexOf('>', i);
   				
   				if (!tmp.trim().equals(""))
   					decoded.add(tmp.trim());
   				
   				tmp = "";
   			} else {
   				tmp += toDecode.charAt(i);
   			}
   		}
   	} catch (Exception e) {
   		//log.error("", e);
   	} 
   	/* Returns the decoded string... */
   	return decoded.toArray();
   }
   */
}
