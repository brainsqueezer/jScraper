package scraper.test;



/**
 * @author rap
 *
 */
public class IMDBTest {
	
	/**
	 * 
	 */
	public IMDBTest() {
		try {
			IMDB imdb = new IMDB("0435670");
			System.out.println(imdb.getTitle());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new IMDBTest();
	}
}
