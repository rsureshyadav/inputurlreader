package com.amum;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class UrlEngine {

	public static void main(String[] args) {
		Properties prop = new Properties();
		InputStream input = null;
		
		try {
			//read the config file
			input = new FileInputStream("conf/config.properties");
			prop.load(input);
			//read the html url query
			String html_output = ReadUrl.executeUrl(urlBuildUsingProperty(prop));
			//write the htmlquery to file
			WriteUrl.execute(html_output,prop);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

	private static String urlBuildUsingProperty(Properties prop) {
		 String url_string = "https://www.google.com/finance/getprices?q="+prop.getProperty("stock.name")+"&x="+prop.getProperty("exchange.name")+"&p="+prop.getProperty("period")+"d";
		 System.out.println("Executing==>>"+ url_string);
		return url_string;
	}

	

	
}
