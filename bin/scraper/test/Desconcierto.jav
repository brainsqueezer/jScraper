package scraper.test;

import java.util.Vector;
import java.util.regex.MatchResult;

import scraper.RegexParser;



public class Desconcierto {
private static String des1_url = "http://www.desconcierto.com/musica/agenda_de_conciertos/agenda_de_conciertos_julio_2007.html"; 
protected static String des1_pattern = "<span class=\"agenda-dia\">([^<]*)";

protected static String des2_pattern = "<span class=\"agenda-lugar\">([^<]*)</span>";
protected static String des3_pattern = "<span class=\"agenda-grupo\">([^<]*)</span>";
protected static String des4_pattern = "</span>, ([^<]*)<br />";


/**
 * @param num 
 * 
 */
public Desconcierto() {
	RegexParser p = new RegexParser(des1_url);
    Vector<MatchResult> results = p.parse(des4_pattern);
      
    for (MatchResult result :results) {
            System.out.print("id: "+result.group(1));
            //System.out.println(" title: "+result.group(2));
            
     }
}

public static void main(String[] args) {
	new Desconcierto();
}

}
