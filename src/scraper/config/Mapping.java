package scraper.config;

import java.util.Vector;

public class Mapping {
	private String url;
	private Vector<Property> properties;
	
	public Mapping() {
		this.properties = new Vector<Property>();
	}
	
	public Mapping(String url) {
		this.url = url;
		this.properties = new Vector<Property>();
	}
	
	public String getUrl( ) {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void addProperty(Property prop) {
		
	}
	
	public Vector<Property> getProperties( ) {
		return properties;
	}

}
