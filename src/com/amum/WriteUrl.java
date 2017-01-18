package com.amum;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class WriteUrl {

	public static void  execute(String html_output,Properties prop) throws Exception {
		Files.write(Paths.get(prop.getProperty("file.input.one")), html_output.getBytes());
        System.out.println("WriteUrl:: Step - 1==>>Completed");
        filterUrlInput(prop);
	}
/*
 * This method is used to return the text file in structure format for spark analysis
 */
	private static void filterUrlInput(Properties prop) throws IOException {
		String fileName=prop.getProperty("file.input.one");
		int period = Integer.parseInt(prop.getProperty("period"));
		List<String> list = new ArrayList<>();
		List<String> listWithNodate = new ArrayList<>();
		List<String> listWithDate = new ArrayList<>();
		List<String> outputList = new ArrayList<>();
		List<String> finalOutputList = new ArrayList<>();

		try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {

			//br returns as stream and convert it into a List
			list = br.lines().collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}

		//list.forEach(System.out::println);
		//System.out.println(list.size());
		int count=0;
		for(String listString:list){
			if(count>=list.size()-period){
				//System.out.println(">> "+listString);
				listWithNodate.add(listString);
			}
			count++;
		}
		
		for(String listString:listWithNodate){
			listWithDate.add(listString);
		}

	    Collections.reverse(listWithDate);
	    int dateRange=0;
		LocalDate today = LocalDate.now();
	    for(String listString:listWithDate){
			//System.out.println(listString);
			String lineArray[]=listString.split(",");
			if(dateRange==0){		
				dateRange=Integer.parseInt(lineArray[0]);
				outputList.add("DATE,CLOSE,HIGH,LOW,OPEN,VOLUME,SYMBOL");
			}
			if(lineArray[0].length()<5 ){
				if(Integer.parseInt(lineArray[0])==dateRange){
					lineArray[0] =today.toString();
					outputList.add(Arrays.toString(lineArray));
				}else{
					 LocalDate date = today.minusDays(dateRange - Integer.parseInt(lineArray[0]));
					 lineArray[0] =date.toString();
					 outputList.add(Arrays.toString(lineArray));
				}
			}else{
				 LocalDate date = today.minusDays(dateRange);
				 lineArray[0] =date.toString();
				 outputList.add(Arrays.toString(lineArray));
			}
		}
	   for(String line : outputList){
		  line = line.replace("[", "");
		  line = line.replace("]", ", "+prop.getProperty("stock.name"));
		  finalOutputList.add(line.trim());
	   }
		 writeFilterOutput(finalOutputList,prop); 
	}
	private static void writeFilterOutput(List<String> outputList, Properties prop) throws IOException {
		
		String fileName= prop.getProperty("file.output");
		fileName=fileName.replace(".txt", "_"+prop.getProperty("stock.name")+".txt");
		
		Path out = Paths.get(fileName);
		System.out.println("<<Final Execution Completed Sucesfully>>"+out.getFileName());
		Files.write(out,outputList,Charset.defaultCharset());
	}
	
	}

		

