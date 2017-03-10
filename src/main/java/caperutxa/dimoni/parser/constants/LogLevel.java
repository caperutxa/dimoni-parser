package caperutxa.dimoni.parser.constants;

/**
 * Log level strings to parse
 * The idea is allow to modify those strings via properties file
 * and declare ELogLevel enumeration as deprecated
 * 
 * The log level value is lost during the migration
 * but never was fully implemented
 * 
 * @author caperutxa
 *
 */
public class LogLevel {
	
	public static String BLOCKER = "BLOCKER";
	public static String CONSOLE = "CONSOLE";
	public static String CONSOLE_WARNING = "CONSOLE_WARNING";
	public static String CONSOLE_ERROR = "CONSOLE_ERROR";
	public static String CRITICAL = "CRITICAL";
	public static String DEBUG = "DEBUG";
	public static String ENVIRONMENT = "ENVIRONMENT";
	public static String ERROR = "ERROR";
	public static String INFO = "INFO";
	public static String INTERNAL_ERROR = "INTERNAL_ERROR";
	public static String MAJOR = "MAJOR";
	public static String MINOR = "MINOR";
	public static String PICTURE = "PICTURE";
	public static String STEP = "STEP";
	public static String SUCCESS = "SUCCESS";
	public static String TEST = "TEST";
	public static String TIME = "TIME";
	public static String TRIVIAL = "TRIVIAL";
	public static String WARNING = "WARNING";
	public static String OTHERS = "OTHERS";

}
