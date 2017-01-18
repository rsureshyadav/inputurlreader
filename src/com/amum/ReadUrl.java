package com.amum;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class ReadUrl {

	public static String executeUrl(String urlString) throws Exception {
	    BufferedReader reader = null;
	    try {
	        URL url = new URL(urlString);
	        reader = new BufferedReader(new InputStreamReader(url.openStream()));
	        StringBuffer buffer = new StringBuffer();
	        int read;
	        char[] chars = new char[1024];
	        while ((read = reader.read(chars)) != -1)
	            buffer.append(chars, 0, read); 

	        System.out.println("ReadUrl::Executing==>>Completed");
	        return buffer.toString();
	    } finally {
	        if (reader != null)
	            reader.close();
	    }
	}
	
	//not used anymore-- replaced with property
	private static String urlBuildUsingConstant() {
		 String url_string = "https://www.google.com/finance/getprices?q="+UrlConstant.STOCK_NAME+"&x="+UrlConstant.EXCHANGE_NAME+"&p="+UrlConstant.PERIOD+"d";
		 System.out.println("Executing==>>"+ url_string);
		return url_string;
	}
}
