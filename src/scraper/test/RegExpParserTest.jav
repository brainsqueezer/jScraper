package scraper.test;

import java.util.Vector;
import java.util.regex.MatchResult;

import scraper.RegexParser;



/**
 * @author rap
 *
 */
public class RegExpParserTest {
	
	
	static String cgai = "http://www.cgai.org/index.php?seccion=programacion_ficha.php&id_programa=950&cambio_a_fecha2=0";
	static String entradas = "http://www.entradas.com/entradas/a001000.do?idprov=15&identidad=1";
	static String elpais = "http://www.elpais.com/cartelera/peliculasPoblacion.html?provincia=0000000038&localidad=0000000038&opcion=1";
	static String terra = "http://www.terra.es/cine/cartelera/busqueda.cfm?pr=15";
	static String todocine_mov = "http://www.todocine.com/mov/00514931.htm";
	static String minutos = "http://20minutos.lanetro.com/cartelera.cfm?top=15";
	
	static String terra_id = "http://www.terra.es/cine/cartelera/pelicula.cfm?ID=7724&pr=15";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	    String elpais1_url = "http://www.elpais.com/cartelera/peliculasPoblacion.html?provincia=0000000038&localidad=0000000038&opcion=1";
	    String elpais1_pattern = "<a href=\"pelicula.html[^0-9]provincia=0000000038&localidad=&pel_pelicula=([0-9]*)\" class=a11r003>([^<]*)<";
	    String alpais2_url = "http://www.elpais.com/cartelera/pelicula.html?provincia=0000000038&localidad=&pel_pelicula=0000079824";
	    String eloais2_pattern = "";
	    
	    RegexParser p = new RegexParser(elpais1_url);
	    Vector<MatchResult> results = p.parse(elpais1_pattern);
	      
	    for (MatchResult result :results) {
	            System.out.print("id: "+result.group(1));
	            System.out.println(" title: "+result.group(2));
	            
	     }
		
	}
}
