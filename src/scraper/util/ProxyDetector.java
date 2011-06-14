/* --------------------------------------------------------------------------
 * ProxyDetector.java
 * --------------------------------------------------------------------------
 * 
 * Copyright (c) 2007, Ramon Antonio Parada (http://ramonantonio.net)
 * All rights reserved.
 * 
 * This software is licensed under the BSD license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * --------------------------------------------------------------------------
 */

package scraper.util;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;




/**
 * 
 * @author rap
 *
 */
public class ProxyDetector {
	
	/**
	 * 
	 *
	 */
	public ProxyDetector() {
		
	}
	
	/**
	 * 
	 *
	 */
	public static void test() {
		URL url;
		try {
			url = new URL("http://ramonantonio.net/semlist/listindex");
		HttpURLConnection http = (HttpURLConnection)url.openConnection();
		http.setRequestProperty("NAME","VALUE");
		http.setDoInput(true);
		http.setDoOutput(true);
		http.setAllowUserInteraction(true);
		http.addRequestProperty("User-Agent", "SemList");
		int response = http.getResponseCode();
		Debug.println("Response: " + response, Debug.INFO);
		InputStream is = http.getInputStream();
		Debug.println("Length: " + http.getContentLength(), Debug.INFO);
	      //int c;
	      //while ((c = is.read()) != -1) System.out.write(c);
	      //System.out.println();
		
	//	XMLReader r = new XMLReader(is);
			//r.parse(true);
		} catch (Exception e) {
			//setProxy();
		}
	}
	
	/**
	 * 
	 * @param verbose
	 */
	public static void setFICProxy(boolean verbose) {
		//String url = "http://www.marchal.com/",
	    String proxy = "poza.cc.fic.udc.es";
	    String port = "3128";
	
		//URL server = new URL(url);
		Properties systemProperties = System.getProperties();
		systemProperties.setProperty("http.proxyHost",proxy);
		systemProperties.setProperty("http.proxyPort",port);
		//HttpURLConnection connection = (
		//HttpURLConnection)server.openConnection();
		//connection.connect();
		//InputStream in = connection.getInputStream();
		//readResponse(in);
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		ProxyDetector.setProxy();
		ProxyDetector.test();
	}
    
	/**
	 * 
	 *
	 */
	public static void setProxy() {
		setFICProxy(false);
	}
	
	/**
	 * 
	 * @param verbose
	 */
    public static void setProxy(boolean verbose) {
        try {
            //TODO Utilizar debug
            System.setProperty("java.net.useSystemProxies","true");
            List l = ProxySelector.getDefault().select(
                        new URI("http://www.yahoo.com/"));
            
            for (Iterator iter = l.iterator(); iter.hasNext(); ) {
                
                Proxy proxy = (Proxy) iter.next();


                Debug.println("proxy hostname : " + proxy.type(), Debug.INFO);
                
                InetSocketAddress addr = (InetSocketAddress)
                    proxy.address();
                
                if (addr == null) {
                    Debug.println("No Proxy", Debug.INFO);
                    
                } else {

                    Debug.println("proxy hostname : " + 
                            addr.getHostName(), Debug.INFO);

                    Debug.println("proxy port : " + 
                            addr.getPort(), Debug.INFO);
                    
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
