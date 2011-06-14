package scraper.util;
/*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

	Copyright (c) Non, Inc. 1997 -- All Rights Reserved

PROJECT:	JavaWorld
MODULE:		Web Stuff
FILE:		Happy.java

AUTHOR:		John D. Mitchell, Jul  8, 1997

REVISION HISTORY:
	Name	Date		Description
	----	----		-----------
	JDM	97.07.08   	Initial version.

DESCRIPTION:

	This file shows how to POST to a web-server and get back the raw
	response data.

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*/


import java.awt.*;
import java.applet.*;
import java.io.*;
import java.util.*;
import java.net.*;


public class Happy extends Applet
    {
    private TextArea textArea = new TextArea (25, 70);

    public void init ()
	{
	try
	    {
	    URL			url;
	    URLConnection	urlConn;
	    DataOutputStream	printout;
	    DataInputStream	input;

	    // URL of CGI-Bin script.
	    url = new URL (getCodeBase().toString() + "env.cgi");

	    // URL connection channel.
	    urlConn = url.openConnection();

	    // Let the run-time system (RTS) know that we want input.
	    urlConn.setDoInput (true);

	    // Let the RTS know that we want to do output.
	    urlConn.setDoOutput (true);

	    // No caching, we want the real thing.
	    urlConn.setUseCaches (false);

	    // Specify the content type.
	    urlConn.setRequestProperty
		("Content-Type", "application/x-www-form-urlencoded");

	    // Send POST output.
	    printout = new DataOutputStream (urlConn.getOutputStream ());

	    String content =
		"name=" + URLEncoder.encode ("Buford Early") +
		"&email=" + URLEncoder.encode ("buford@known-space.com");
	    
	    printout.writeBytes (content);
	    printout.flush ();
	    printout.close ();

	    // Get response data.
	    input = new DataInputStream (urlConn.getInputStream ());

	    String str;
	    while (null != ((str = input.readLine())))
		{
		System.out.println (str);
		textArea.appendText (str + "\n");
		}

	    input.close ();

	    // Display response.
	    add ("Center", textArea);
	    }
	catch (MalformedURLException me)
	    {
	    System.err.println("MalformedURLException: " + me);
	    }
	catch (IOException ioe)
	    {
	    System.err.println("IOException: " + ioe.getMessage());
	    }
	}	// End of method init().
    }		// End of class Happy.
