package scraper.config;

public class Property {
	private String name;
	private String regex;

	public Property(String name, String regex ) {
		this.name = name;
		this.regex = regex;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getRegex() {
		return regex;
	}

}
