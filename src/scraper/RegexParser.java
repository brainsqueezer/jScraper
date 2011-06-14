package scraper;

import java.util.Vector;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import scraper.util.CachedURLConnection;



/**
 * @author rap
 *
 */
public class RegexParser {

	
	private String location;
//	private Vector<String> patterns;
	private Vector<String> results;
	/**
	 * @param location
	 */
	public RegexParser(String location) {
		this.location = location;
		//patterns = new Vector<String>();
		results = new Vector<String>();
	}

	
	
	/**
	 * @param pattern 
	 * @return Vector of results
	 */
	public Vector<String> parse(String pattern) {
		String str = CachedURLConnection.get(location);
		//ProxyDetector.setProxy();
		//Pattern p = Pattern.compile("<a href=\"pelicula.html?provincia=0000000038&localidad=&pel_pelicula=0000079788\" class=a11r003>Delirious</a></td>");
	    Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
	    while (m.find()) {
	    	MatchResult result=m.toMatchResult();
	    	//System.out.print("id: "+result.group(1));
	    	//System.out.println(" title: "+result.group(2));
	    	results.add(result.group(1));
	    	//String algo = "http://www.elpais.com/cartelera/pelicula.html?provincia=0000000038&localidad=&pel_pelicula=0000079824";
	        //System.out.println(" title: "+result.group(2)        
	    }
		return results;
	}
	
	/**
	 * @return
	 */
	public Vector<String> getResults() {
		return results;
		
	}
}
