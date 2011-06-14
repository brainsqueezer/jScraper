/**
 * 
 */
package scraper.test;

import java.util.Vector;
import java.util.regex.MatchResult;

import scraper.RegexParser;



/**
 * @author rap
 *
 * <!-- PARTE FINAL DE LA CABECERA -->
 *
 * <tr valign="top"><!-- row 12 -->
 * <td colspan="33" height="20" valign="top" bgcolor="#996600"><div align="center"><b><font face="Arial, Helvetica, sans-serif" size="3" color="#FFFFFF">aba                                                                                                 </font></b></div></td>
 * </tr>
 * <tr valign="top"><!-- row 13 -->
 *
 * <td colspan="33" height="240" valign="top"><font face="Arial, Helvetica, sans-serif"><font size="2">
 * <b>aba</b><i>s.f.</i> <b>1</b>. Parte inferior de cert
 * </b> <b>2</b>. Parte do sombre....o sol.</i> 
 * <b>3</b>. Parte inferior dun mon
 * 
 * </font></font></td> 
 *
 * </tr>
 *
 *	
 * <!-- PARTE DEL PIE -->
 */
public class RAGParser {
	
    String rag1_url = "http://www.edu.xunta.es/diccionarios/ListaDefinicion.jsp?IDXT=";
    //String rag1_pattern = "<td colspan=\"33\" height=\"20\" valign=\"top\" bgcolor=\"#996600\"><font face=\"Arial, Helvetica, sans-serif\" size=\"3\" color=\"#FFFFFF\">([^<]*)</tr>";
    String rag1_pattern = "<font face=\"Arial, Helvetica, sans-serif\" size=\"3\" color=\"#FFFFFF\">([^<]*)";
    String rag2_url = "http://www.elpais.com/cartelera/pelicula.html?provincia=0000000038&localidad=&pel_pelicula=0000079824";
    String rag2_pattern = "<font size=\"2\">([[^<][^/][^f]]*)</font>";
    
    
	
	/**
	 * @param num 
	 * 
	 */
	public RAGParser(int num) {
		RegexParser p = new RegexParser(rag1_url+num);
	    Vector<MatchResult> results = p.parse(rag2_pattern);
	      
	    for (MatchResult result :results) {
	            System.out.print("id: "+result.group(1));
	            //System.out.println(" title: "+result.group(2));
	            
	     }
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int min = 1;
		int max = 20550;
		for (int i = min; i <= max; i++ ) {
			new RAGParser(i);
		}
	}

}
