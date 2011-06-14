/**
 * @(#)IMDB.java 1.0 29.01.06 (dd.mm.yy)
 *
 * Copyright (2003) Mediterranean
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2, or any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with 
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Boston, MA 02111.
 * 
 * Contact: mediterranean@users.sourceforge.net
 **/

package scraper.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;
import org.aific.util.FileUtil;
import org.aific.web.ClientHttpRequest;

/**
 * 
 * @author rap
 *
 */
public class IMDB {
    
    /** The imdb key for the movie */
    private String _key = "";
    /** The date of the movie */
    private String _date = "";
    /** The title the movie */
    private String _title = "";
    /** Directed by */
    private String _directedBy = "";
    /** Written by */
    private String _writtenBy = "";
    /** Genre */
    private String _genre = "";
    /** The rating. */
    private String _rating = "";
    /** The colour of the movie */
    private String _colour = "";
    /** Also known as of the movie */
    private String _aka = "";
    /** The country of the movie */
    private String _country = "";
    /** The language of the movie */
    private String _language = "";
    /** The Sound Mix of the movie */
    private String _mpaa = "";
    /** The Sound Mix of the movie */
    private String _soundMix = "";
    /** The Sound Mix of the movie */
    private String _runtime = "";   
    /** The Sound Mix of the movie */
    private String _certification = "";
    /** The Sound Mix of the movie */
    private String _awards = "";
    /** The plot. */
    private String _plot = "";
    /** The cast. */
    private String _cast = "";
    /** The cover url. */
    private String _coverURL = "";
    /** The cover url. */
    private String _coverName = ""; 
    /** Reding ok... */
    private boolean _coverOK = false;
    
    
    /**
     * The constructor. Initializes all vars (read from the net) for
     * the movie with key.
     * @param key 
     * @throws Exception 
     **/
    public IMDB(String key) throws Exception {
	
		_key = key;
	
		/* Gets the url... */
		URL url = new URL("http://akas.imdb.com/title/tt"+key+"/");
	
		/* Saves the page data in a string buffer... */
		StringBuffer data = ClientHttpRequest.readDataToStringBuffer(url);
	
		if (data == null) {
			throw new Exception("Invalid HTTP link");
		}
    
		parseData(data);
    }
    
    
    /**
     * @param key
     * @param data
     * @throws Exception
     */
    public IMDB(String key, StringBuffer data) throws Exception {
	
		_key = key;
	
		parseData(data);
    }
    
    
    /**
     * 
     * @param data
     * @throws Exception
     */
    private void parseData(StringBuffer data) throws Exception {
	
		int start = 0;
		int end = 0;
	
		Object [] tmpArray;
	
		//FileUtil.writeToFile("imdb.html", data);
		
		try {
			/* Processes the data... */

			/* Gets the title... */
			if ((start = data.indexOf("<div id=\"tn15title\">", start)) != -1 &&
				(end = data.indexOf("</div>", start)) != -1) {
	
				tmpArray = ClientHttpRequest.decodeHTMLtoArray(data.substring(start, end));

				_title = (String) tmpArray[0];

//				if (MovieManager.getConfig().getAutoMoveThe() && _title.startsWith("The ")) {
//					_title = _title.substring(_title.indexOf(" ")+1, _title.length())+ ", The";
//				}
//				else if (MovieManager.getConfig().getAutoMoveAnAndA() && (_title.startsWith("A ") || _title.startsWith("An "))) {
//					_title = _title.substring(_title.indexOf(" ")+1, _title.length())+ ", "+ _title.substring(0, _title.indexOf(" "));
//				}
		
				_date = (String) tmpArray[2];
			}
	    
			/* Gets the cover url... */
			if ((start = data.indexOf("<div class=\"photo\">")) != -1 && 
				(end = data.indexOf("</div>", start)) != -1) {
	    	
				if (data.substring(start, end).indexOf("Poster Not Submitted") == -1) {
	    	
					if ((start = data.indexOf("src=\"",start) +5) !=4 &&
						(end = data.indexOf("\"", start)) != -1) {
						_coverURL = ClientHttpRequest.decodeHTML(data.substring(start, end));
					}
	    	
					start = _coverURL.lastIndexOf(".");

					if (start != 0 && start != -1)
						_coverName = _key + _coverURL.substring(start, _coverURL.length());
				}
			}
	    
			start = 0;
			end = 0;
			/* Gets the rating... */
			if ((start = data.indexOf("User Rating:", start)+ 12) != 11 &&
				(end = data.indexOf("/10</b>",start)) != -1 &&
				(start = data.indexOf("<b>",end-9) +3) != 2) {
		
				_rating = ClientHttpRequest.decodeHTML(data.substring(start, end));
			}
	     
			start = 0;
			end = 0;
			
			
			// Gets the directed by... 
			String tmp = getClassInfo(data, "Director");
			tmp = tmp.substring(tmp.indexOf(":")+1, tmp.length());
				
			ArrayList list = getLinkContentName(tmp);
	    	 
			while (!list.isEmpty()) {
				if (!_directedBy.equals(""))
					_directedBy += ", ";
	    			
				_directedBy += list.remove(0);
			}
				 
			
			// Gets the written by... 
			tmp = getClassInfo(data, "Writer");
			tmp = tmp.substring(tmp.indexOf(":")+1, tmp.length());
			
			list = getLinkContentName(tmp);
	    		
			while (!list.isEmpty()) {
				if (!_writtenBy.equals(""))
					_writtenBy += ", ";
	    			
				_writtenBy += list.remove(0);
			}
	    				
			_genre = getDecodedClassInfo(data, "Genre:");
	    
			_genre = _genre.replaceAll("(more)$", "");
			
			_plot = getDecodedClassInfo(data, "Plot Outline:");
	     
			_cast = getDecodedClassInfo(data, "class=\"cast\">");
	    
			_cast = _cast.replaceAll(" \\.\\.\\.", ",");
							
			_aka = getDecodedClassInfo(data, "Also Known As:");
	    
			_mpaa = getDecodedClassInfo(data, "<a href=\"/mpaa\">MPAA</a>:");
	    
			_runtime = getDecodedClassInfo(data, "Runtime:");
	    
			_country = getDecodedClassInfo(data, "Country:");
	    
			_language = getDecodedClassInfo(data, "Language:");
	    
			_colour = getDecodedClassInfo(data, "Color:");
	    
			_soundMix = getDecodedClassInfo(data, "Sound Mix:");
	    
			_certification = getDecodedClassInfo(data, "Certification:");
	    
			_awards = getDecodedClassInfo(data, "Awards:");
			_awards = _awards.replaceAll("(more)$", "");
	  
			/* Gets a bigger plot (if it exists...)
			   /* Creates the url... */
			URL url = new URL("http://akas.imdb.com/title/tt"+ _key +"/plotsummary");
	    
			data = ClientHttpRequest.readDataToStringBuffer(url);   
	    
			/* Processes the data... */
			start = 0;
			end = 0;
	    
			if ((start = data.indexOf("class=\"plotpar\">",start)+16) != 15 &&
				(end=data.indexOf("</p>",start)) != -1) {
				_plot = ClientHttpRequest.decodeHTML(data.substring(start, end));
				
				if (_plot.indexOf("Written by") != -1)
					_plot = _plot.substring(0, _plot.indexOf("Written by"));
			}
	   	   
			_plot = _plot.trim();
			_plot = _plot.replaceAll("(more)$", "");
			
		} catch (Exception e) {
			//log.error("", e);
			System.out.println(e);
		}
    }

    /**
     * Gets the key.
     * @return the key
     **/
    public String getKey() {
		return _key;
    }

    /**
     * Gets the date.
     * @return the date
     **/
    public String getDate() {
		return _date;
    }
  
    /**
     * Gets the title.
     * @return the title
     **/
    public String getTitle() {
		return _title;
    }
  
    /**
     * Gets the durected by.
     * @return the directors name
     **/
    public String getDirectedBy() {
		return _directedBy;
    }
  
    /**
     * Gets the written by.
     * @return the author who wrote it
     **/
    public String getWrittenBy() {
		return _writtenBy;
    }
  
    /**
     * Gets the genre.
     * @return the genre
     **/
    public String getGenre() {
		return _genre;
    }
  
    /**
     * Gets the rating.
     * @return the rating
     **/
    public String getRating() {
		return _rating;
    }
  
    /**
     * Gets the colour.
     * @return the colour
     **/
    public String getColour() {
		return _colour;
    }

    /**
     * Gets the country.
     * @return the country
     **/
    public String getCountry() {
		return _country;
    }

    /**
     * Gets the language.
     * @return the language
     **/
    public String getLanguage() {
		return _language;
    }
    
    /**
     * Gets the plot.
     * @return the plot
     **/
    public String getPlot() {
		return _plot; 
    }
  
    /**
     * Gets the cast.
     * @return the cast
     **/
    public String getCast() {
		return _cast;
    }
    
    /**
     * Gets the aka.
     * @return the aka
     **/
    public String getAka() {
		return _aka;
    }
    
    /**
     * Gets the mpaa.
     * @return the mpaa
     **/
    public String getMpaa() {
		return _mpaa;
    }
    
    /**
     * Gets the Sound Mix.
     * @return the sound mix
     **/
    public String getSoundMix() {
		return _soundMix;
    }

    /**
     * Gets the Runtime.
     * @return the runtime
     **/
    public String getRuntime() {
		return _runtime;
    }
    
    /**
     * Gets the Certification.
     * @return the certification
     **/
    public String getCertification() {
		return _certification;
    }

    /**
     * Gets the Awards.
     * @return the awards
     **/
    public String getAwards() {
		return _awards;
    }
    
    
    /**
     * Gets the cover name.
     * @return the cover name 
     **/
    public String getCoverName() {
		return _coverName;
    }
    
    /**
     * Gets the cover url.
     * @return the cover url
     **/
    public String getCoverURL() {
		return _coverURL;
    }
  
    /**
     * Gets the cover.
     * @return the cover
     **/
    public byte[] getCover() {
      
		byte[] coverData = {-1};
		try {
	    
			if (!_coverURL.equals("")) {
		
				URL url = new URL(_coverURL);
				coverData = ClientHttpRequest.readDataToByteArray(url);
		
				_coverOK = true;
			}
		} catch (Exception e) {
			//log.error("", e);
			_coverOK = false;
		} 
	
		/* Returns the data... */
		return coverData;
    }
    
    
    /**
     * Returns true if the last cover reading went ok..
     * @return  truew is the cover is ok
     **/
    public boolean getCoverOK() {
		return _coverOK;
    }

    /**
     * Returns simple matches list...
     * @param title 
     * @return simple matches list
     * @throws Exception 
     **/
    public static Vector<IMDB> getSimpleMatches(String title) throws Exception {
	
		return getMatches("http://akas.imdb.com/find?s=tt&q="+ title);
		// http://akas.imdb.com/find?s=tt&q=Predator 
		// http://akas.imdb.com/find?s=all&q=predator
		//	return getMatches("http://www.imdb.com/find?tt=on;q=" + title);
		//return getMatches("http://www.imdb.com/find?tt=on;q=",title);
    }

    /**
     * Returns extended matches list...
     * @param title 
     * @return extend matches list
     * @throws Exception 
     **/
    public static Vector<IMDB> getExtendedMatches(String title) throws Exception {
		return getMatches("http://akas.imdb.com/find?more=tt;q=" + title);
    }
    
    /**
     * Returns a DefaultListModel with ModelMovie's of the movies that IMDB
     * returned for the searched title.
     *
     * urlType = http://www.imdb.com/find?tt=on;q= or
     *           http://www.imdb.com/find?more=tt;q=
     */
    private static Vector<IMDB> getMatches(String urlType) throws Exception {
//		DefaultListModel listModel = new DefaultListModel();
    	Vector<IMDB> list = new Vector<IMDB>();
    
		try {
	
			URL url = new URL(urlType.replaceAll("[\\p{Blank}]+","%20"));
	    
			StringBuffer data = ClientHttpRequest.readDataToStringBuffer(url);
	    
			//FileUtil.writeToFile("direct-simple", data);
        
			int start = 0, end = 0, stop = 0;
			String key = "";
			String movieTitle = "", aka = "";
			int titleSTart, titleEnd;
			int otherResults = data.indexOf("Other Results");
			int partialMatches = data.indexOf("Titles (Partial Matches)");
   
			/* If there's only one movie for that title it goes directly to that site...  */
			if (!data.substring(data.indexOf("<title>")+7, data.indexOf("<title>")+11).equals("IMDb")) {
				/* Gets the title... */
	    
				titleSTart = data.indexOf("<title>", start)+7;
				titleEnd = data.indexOf("</title>", titleSTart);
				movieTitle = ClientHttpRequest.decodeHTML(data.substring(titleSTart, titleEnd));
	    
				if ((start=data.indexOf("title/tt",start) + 8) != 7) {
					key = ClientHttpRequest.decodeHTML(data.substring(start, start + 7));
				}
		
				/* Getting aka titles */
				if ((start = data.indexOf("Also Known As:")) != -1) {
					start += 18;
	  
					stop = data.indexOf("<b class=", start)-5;
	  	  
					while (true) {
						if (start >= stop) break;
						start += 4;
						end = data.indexOf("<br>", start);
						if (!aka.equals(""))
							aka += FileUtil.getLineSeparator(); // windows == "\r\n";, linuz == \n
						aka += ClientHttpRequest.decodeHTML(data.substring(start, end));
						start = data.indexOf("<br>", end);
					}
//					aka = MovieManagerCommandAddMultipleMoviesByFile.performExcludeParantheses(aka, false);
				}
		
				//listModel.addElement(new ModelIMDB(key, movieTitle, aka));
				list.addElement(new IMDB(key));
				return list;
			}
			/* Processes the data... */
			start = 0;
			end = 0;
			while (true) {
 
				aka = "";

				if ((start = data.indexOf("/title/tt", start)+9) == 8) break;
		
				if ((end = data.indexOf("/\">", start)) == -1) break;
	    
				/* the string "Other Results only occurs with the simplematches url,
				   therefore the variable otherResults will contain -1 when 
				   using the extended matches url*/
	    
				if ((otherResults != -1) && (partialMatches != -1) && (start > partialMatches)) 
					break;
		
				key = ClientHttpRequest.decodeHTML(data.substring(start, start+7));
	    
				start += key.length() + 3;
				if ((end = data.indexOf("</", start)) == -1) 
					break;
		
				titleSTart = data.indexOf(">", start)+1;
				titleEnd = data.indexOf("</a>", titleSTart);
				movieTitle = ClientHttpRequest.decodeHTML(data.substring(titleSTart, titleEnd));
	    
				/* Skipping the </a> and adds the year to the movieTitle*/
				titleSTart = titleEnd+4;
				titleEnd += 11;
	    
				movieTitle = movieTitle.concat(data.substring(titleSTart, titleEnd));
		
				start += movieTitle.length() + 2;
		
				end = data.indexOf("</li>", start);
		
				/* Parses the aka-titles... */
				while (data.indexOf(";aka", start) < end) {
					titleSTart = data.indexOf(";aka", start)+1; 
					titleEnd = data.indexOf("<br>", titleSTart);
		    
					if (titleEnd < titleSTart || titleEnd > end)
						titleEnd = data.indexOf("</li>", titleSTart);
		    
					if (titleEnd > end)
						break;
		    
					if (titleSTart > 0 && titleEnd > 0) {
						start = titleEnd;
			
						if (!aka.equals("")) {
							aka += FileUtil.getLineSeparator();
						}
						aka += ClientHttpRequest.decodeHTML(data.substring(titleSTart, titleEnd));
					}
					else
						break;
				}
		
				/* Adds to the list model... */
				boolean insert = true;
				for (int i = 0; i < list.size(); i++) {
					if ((list.elementAt(i)).getKey().equals(key)) {
						insert = false;
						break;
					}
				}
				if (insert) {
					//listModel.addElement(new ModelIMDB(key, movieTitle, aka));
					list.addElement(new IMDB(key));
				}
			}
		
		
		} catch (IndexOutOfBoundsException i) {
			//log.warn(i.getMessage());
		
		} catch (MalformedURLException m) {
			//log.warn(m.getMessage());
		}
	
		/* Returns the model... */
		return list;
    }
    

    /**
     * Grabs the content of a class info containing the classname
     */
    protected static String getClassInfo(StringBuffer data, String className) {
    	String tmp = "";

    	
    		int start = 0;
    		int end = 0;
    		while ((start = data.indexOf("<div class=\"info\">", end)) != -1 && 
				   (end = data.indexOf("</div>", start)) != -1) {

    			tmp = data.substring(start, end);	

    			if (tmp.indexOf(className) != -1) {
    				
    				start = tmp.indexOf(className) + className.length();
    				tmp = tmp.substring(start, tmp.length());	
    				tmp = tmp.trim();
    				break;
    			}
    			tmp = "";
    		}
    	return tmp;
    }
    
    
    /**
     * Grabs the content of a class info containing the classname
     * and cleans it up by removing html and paranthesis.
     **/
    protected static String getDecodedClassInfo(StringBuffer data, String className) {
    	String decoded = null;
    	String tmp = "";

    	try {
    		int end = 0;
    		tmp = getClassInfo(data, className);
    		
    		end = tmp.indexOf("<a class=\"tn15more\"");
    		
    		if (end != -1) {
				tmp = tmp.substring(0, end);
    		}
		
			if (className.equals("Also Known As:")) {
				decoded = decodeAka(tmp);
			} else if (className.equals("class=\"cast\">")) {
				decoded = decodeCast(tmp);
			} else
				decoded = ClientHttpRequest.decodeHTML(tmp);
		
		
    	} catch (Exception e) {
    		//log.error("", e);
    	} 
    	/* Returns the decoded string... */
    	return decoded.trim();
    }
    
    
    
    
    /**
     * Decodes a html string and returns its unicode string.
     **/
    protected static ArrayList getLinkContentName(String toDecode) {
    	ArrayList<String> decoded = new ArrayList<String>();
    	String tmp = "";
		
		try {
			int start = 0;
			int end = 0;
	    
			while ((start = toDecode.indexOf("<a href=", start)) != -1) {
	    	
				start = toDecode.indexOf(">", start) +1;
				end = toDecode.indexOf("</a>", start);
	    	
				tmp = toDecode.substring(start, end);
				decoded.add(ClientHttpRequest.decodeHTML(tmp.trim()));
			}
		} catch (Exception e) {
			//log.error("", e);
		} 
		/* Returns the decoded string... */
		return decoded;
    }
    
    /**
     * Decodes a html string and returns its unicode string.
     **/
    protected static String decodeAka(String toDecode) {
		String decoded = "";
		
		try {
			String [] akaTitles = toDecode.split("<br>");
		
			for (int i = 0; i < akaTitles.length; i++) {
				decoded += ClientHttpRequest.decodeHTML(akaTitles[i]) + "\r\n";
			}
			
		} catch (Exception e) {
			//log.error("", e);
		} 
		/* Returns the decoded string... */
		return decoded.trim();
    }
    
    
    /**
     * Decodes a html string and returns its unicode string.
     */
    protected static String decodeCast(String toDecode) {
		StringBuffer decoded = new StringBuffer();
		
		try {
			String [] castSplit = toDecode.split("<td class=\"hs\">");
			String [] tmp;
		
			for (int i = 0; i < castSplit.length; i++) {
			
				tmp = ClientHttpRequest.decodeHTML(castSplit[i]).split(" \\.\\.\\.");
			
				if (tmp.length == 2) {
					decoded.append(tmp[0].trim());
					decoded.append(" (" + tmp[1].trim() + "), ");
				}
			}
			
		} catch (Exception e) {
			//log.error("", e);
		} 
		/* Returns the decoded string... */
		return decoded.toString();
    }
    
    
   
    /**
     * @param toBeCleaned
     * @return a
     */
    public static String extractTime(String toBeCleaned) {
	
		boolean start = false;
		String result = "";
	
		for (int i = 0; i < toBeCleaned.length(); i++) {
	    
			if (Character.isDigit(toBeCleaned.charAt(i)))
				start = true;
	    
			if (start) {
		
				if (Character.isDigit(toBeCleaned.charAt(i))) {
					result += toBeCleaned.charAt(i);
				} else {
					break;
				}
			}
		}
		return result;
    }
}
