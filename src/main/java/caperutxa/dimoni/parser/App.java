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
    public static List<String> fileNames;
	
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
        		System.out.println("File to parse " + s.substring(3));
        	} else if(s.startsWith("-db:")) {
        		Constants.USE_DB = Boolean.valueOf(s.substring(4));
        		System.out.println("Use DB modified to " + Constants.USE_DB);
        	} else if(s.startsWith("-d:")) {
        		Constants.DESTINATION_FILE = s.substring(3);
        		System.out.println("New destination file " + Constants.DESTINATION_FILE);
        		try {
					Files.deleteIfExists(new File(Constants.DESTINATION_FILE).toPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
        	} else if(s.startsWith("-tp:")) {
        		Constants.LOG_DATETIME_FORMAT = s.substring(4);
        		Constants.LOG_DATETIME_FORMAT = Constants.LOG_DATETIME_FORMAT.replaceAll("\"", "").replaceAll("'","");
        		System.out.println("New date time format " + Constants.LOG_DATETIME_FORMAT);
        	} else {
        		System.out.println("Unknown parameter " + s);
        	}
		}
	}
}
