package caperutxa.dimoni.parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

import caperutxa.dimoni.parser.constants.Constants;

/**
 * Hello world!
 *
 */
public class App 
{
    public static List<String> fileNames = new LinkedList<String>();
	
	public static void main( String[] args )
    {
		System.out.println("Parse test log file");
		parseParameters(args);
		Parser.startProcess(fileNames);
		System.out.println("End. By caperutxa");
    }
	
	public static void parseParameters(String[] parameter) {
		fileNames = new LinkedList<String>();
		
		for(String s : parameter) {
			if(s.startsWith("-f:")) {
        		fileNames.add(s.substring(3));
        	} else if(s.startsWith("-db:")) {
        		Constants.USE_DB = Boolean.valueOf(s.substring(4));
        	} else if(s.startsWith("-d:")) {
        		Constants.DESTINATION_FILE = s.substring(3);
        		try {
					Files.deleteIfExists(new File(Constants.DESTINATION_FILE).toPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
        	} else {
        		System.out.println("Unknown parameter " + s);
        	}
		}
	}
}
