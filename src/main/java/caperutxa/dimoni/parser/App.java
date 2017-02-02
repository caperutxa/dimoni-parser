package caperutxa.dimoni.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.joda.time.format.DateTimeFormat;

import caperutxa.dimoni.parser.constants.Constants;

/**
 * Entry point form console
 * Flow
 * * Apply config/parser.properties file
 * * Modify with parameters
 * * Parser
 * 
 * @author caperutxa
 *
 */
public class App 
{
    public static List<String> fileNames;
	
	public static void main( String[] args )
    {
		System.out.println("Parse test log file");
		applyDefaultConfig();
		parseParameters(args);
		Parser.startProcess(fileNames);
		System.out.println("End. By caperutxa");
    }
	
	/**
	 * Apply properties file
	 */
	public static void applyDefaultConfig() {
		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream("config/parser.properties");
			prop.load(input);

			Constants.USE_DB = Boolean.valueOf(prop.getProperty("DB.USE"));
			Constants.CONNECTION_STRING = prop.getProperty("DB.CONNECTION_STRING");
			Constants.JDBC_DRIVER = prop.getProperty("DB.JDBC_DRIVER");
			Constants.JDBC_USER = prop.getProperty("DB.JDBC_USER");
			Constants.JDBC_PASS = prop.getProperty("DB.JDBC_PASS");
			
			parseParameters(new String[]{ "-tp:" + prop.getProperty("date-time-format") });
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Manage parameters
	 * 
	 * @param parameter
	 */
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
        		try {
        			String format = s.substring(4).replaceAll("\"", "").replaceAll("'","");
        			DateTimeFormat.forPattern(format);
        			Constants.LOG_DATETIME_FORMAT = format;
        			System.out.println("New date time format " + Constants.LOG_DATETIME_FORMAT);
        		} catch (Exception e) {
        			System.out.println(e.getMessage());
        			System.out.println("Default date time format applied");
        		}
        	} else {
        		System.out.println("Unknown parameter " + s);
        	}
		}
	}
}
