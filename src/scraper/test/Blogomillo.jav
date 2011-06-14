package scraper.test;

import java.util.Vector;
import java.util.regex.MatchResult;

import scraper.RegexParser;



/**
 * @author rap
 *
 */
public class Blogomillo {
	private static String mill1_url = "http://blogaliza.org/index.php?val=lblogomillo";
    String mill1_pattern = "<font face=\"Arial, Helvetica, sans-serif\" size=\"3\" color=\"#FFFFFF\">([^<]*)";
    String rag2_url = "http://www.elpais.com/cartelera/pelicula.html?provincia=0000000038&localidad=&pel_pelicula=0000079824";
    String rag2_pattern = "<font size=\"2\">([[^<][^/][^f]]*)</font>";
    
    
	
	/**
	 * @param num 
	 * 
	 */
	public Blogomillo(int num) {
		RegexParser p = new RegexParser(mill1_url);
	    Vector<MatchResult> results = p.parse(mill1_pattern);
	      
	    for (MatchResult result :results) {
	            System.out.print("id: "+result.group(1));
	            //System.out.println(" title: "+result.group(2));
	            
	     }
	}

}
