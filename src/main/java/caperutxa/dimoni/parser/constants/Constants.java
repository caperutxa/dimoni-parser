package caperutxa.dimoni.parser.constants;

public class Constants {

	//public static String DESTINATION_FOLDER = "";
	//public static String HTML_REPORT_FOLDER = "reports/";
	//public static String SCREENSHOTS_FOLDER = "img/";
	//public static String LOG_FOLDER = "logs/";
	public static String DESTINATION_FILE = "logs/parser.log.html";
	
	public static String LOG_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	
	// TODO Implement this threshold
	public static boolean LOG_REPORT_ALL = true;
	public static int LOG_REPORT_LEVEL = ELogLevel.DEBUG.priority();
	
	public static boolean USE_HTML_REPORTS = true;
	//public static boolean USE_SCREENSHOT = true;
	
	public static boolean USE_DB = true;
	public static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	public static String CONNECTION_STRING = "jdbc:mysql://localhost:3306/selenium";
	public static String JDBC_USER = "selenium";
	public static String JDBC_PASS = "sTcSmdFBb5SPpNMb";
	
}
