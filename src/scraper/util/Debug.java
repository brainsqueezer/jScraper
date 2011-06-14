package scraper.util;

import java.io.PrintStream;
import java.util.Vector;

/**
 * @author rap
 *
 */
public abstract class Debug {
	
private static Vector<PrintStream> outputList = new Vector<PrintStream>();
	
	/**
	 * 
	 */
	public static int INFO = 0;
	/**
	 * 
	 */
	public static int WARNING = 1;
	/**
	 * 
	 */
	public static int ERROR = 2;
	private static int debugLevel = ERROR;
	
	/**
	 * @param msg
	 * @param type
	 */
	public static void println(String msg, int type) {
		for (PrintStream output:outputList)
			output.println(msg);
	}
	
	/**
	 * @param msg
	 * @param type
	 */
	public static void println(Object msg, int type) {
		println(msg.toString(), type);
	}
	
	/**
	 * @param output
	 */
	public void addOutput(PrintStream output) {
		outputList.add(output);
	}

	/**
	 * @param b
	 */
	public static void assertion(boolean b) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @return current debug level
	 */
	public static int getDebugLevel() {
		return debugLevel;
	}
	
	/**
	 * @param level
	 */
	public static void setDebugLevel(int level) {
		debugLevel = level;
	}
}
