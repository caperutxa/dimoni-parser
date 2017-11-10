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
import caperutxa.dimoni.parser.constants.LogLevel;

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
    public static String defaultLogLevelString = "default";
	
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
			input = new FileInputStream(getPathToResourcesFile("/config/parser.properties"));
			prop.load(input);
			
			LogLevel.BLOCKER = logLevelFromConfig(prop, "BLOCKER");
			LogLevel.CONSOLE = logLevelFromConfig(prop, "CONSOLE");
			LogLevel.CONSOLE_WARNING = logLevelFromConfig(prop, "CONSOLE_WARNING");
			LogLevel.CONSOLE_ERROR = logLevelFromConfig(prop, "CONSOLE_ERROR");
			LogLevel.CRITICAL = logLevelFromConfig(prop, "CRITICAL");
			LogLevel.DEBUG = logLevelFromConfig(prop, "DEBUG");
			LogLevel.ENVIRONMENT = logLevelFromConfig(prop, "ENVIRONMENT");
			LogLevel.ERROR = logLevelFromConfig(prop, "ERROR");
			LogLevel.INFO = logLevelFromConfig(prop, "INFO");
			LogLevel.INTERNAL_ERROR = logLevelFromConfig(prop, "INTERNAL_ERROR");
			LogLevel.MAJOR = logLevelFromConfig(prop, "MAJOR");
			LogLevel.MINOR = logLevelFromConfig(prop, "MINOR");
			LogLevel.PICTURE = logLevelFromConfig(prop, "PICTURE");
			LogLevel.STEP = logLevelFromConfig(prop, "STEP");
			LogLevel.SUCCESS = logLevelFromConfig(prop, "SUCCESS");
			LogLevel.TEST = logLevelFromConfig(prop, "TEST");
			LogLevel.TIME = logLevelFromConfig(prop, "TIME");
			LogLevel.TRIVIAL = logLevelFromConfig(prop, "TRIVIAL");
			LogLevel.WARNING = logLevelFromConfig(prop, "WARNING");
			LogLevel.OTHERS = logLevelFromConfig(prop, "OTHERS");
			
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
	
	/**
	 * The levels should be in the configuration file
	 * but if they are missing the process must go on
	 * 
	 * @param level
	 * @return
	 */
	public static String logLevelFromConfig(Properties prop, String level) {
		String l = level;
		
		try {
			l =  prop.getProperty("logLevel." + level);
			if(null == l) {
				l = level;
				System.out.println("The " + level + " is null. Please ckeck!");
			}
		} catch(Exception e) {
			System.out.println("Troubles with " + level + " level. Use default vale (" + level + ")");
			System.out.println(e.toString());
		}
		
		return l;
	}

	public static String getPathToResourcesFile(String fileName) {
		String path = App.class.getResource(fileName).getPath();
		if(path.startsWith("/C:/")) {
			path = path.substring(1);
		} else if(path.startsWith("file:/C:/")) {
			path = path.substring(6);
		}

		path = path.replace("parser.jar!", "resources");

		return path;
	}
	
}
