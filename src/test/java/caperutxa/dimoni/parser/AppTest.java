package caperutxa.dimoni.parser;

import org.junit.Assert;
import org.junit.Test;

import caperutxa.dimoni.parser.constants.Constants;
import caperutxa.dimoni.parser.constants.LogLevel;

/**
 * 
 * @author caperutxa
 *
 */
public class AppTest {
    
	@Test
	public void runProcessWithParameters() {
		App.main(testParameters());
	}
	
	String[] testParameters() {
		String[] args = new String[] {
				"-db:false"
				, "-d:logs/testParameters.html"
				, "-f:src/test/resources/logFiles/SuccessDeclaration.testlog"
		};
		
		return args;
	}
	
	@Test
	public void runProcessWithDBLogs() {
		String[] args = new String[] {
				"-db:true"
				, "-d:logs/runProcessWithDBLogs.html"
				, "-f:src/test/resources/logFiles/SuccessDeclaration.testlog"
		};
		
		App.main(args);
	}
	
	@Test
	public void runProcessWithDBLogsStrangeFile() {
		String[] args = new String[] {
				"-db:true"
				, "-d:logs/CalendartestinSearchform.txt.html"
				, "-f:src/test/resources/logFiles/CalendartestinSearchform.txt"
		};
		
		App.main(args);
	}
	
	@Test
	public void parseParameters1() {
		String[] args = new String[] {
				"-db:false"
				, "-d:logs/testParameters.html"
				, "-f:SuccessDeclaration.testlog"
				, "-tp:\"HH:mm:ss.SSS yyyy-MM-dd\""
		};
		
		App.parseParameters(args);
		
		Assert.assertEquals(false, Constants.USE_DB);
		Assert.assertEquals("logs/testParameters.html", Constants.DESTINATION_FILE);
		Assert.assertEquals("HH:mm:ss.SSS yyyy-MM-dd", Constants.LOG_DATETIME_FORMAT);
		
		for(String s : App.fileNames) {
			Assert.assertEquals("SuccessDeclaration.testlog", s);
		}
	}
	
	@Test
	public void parseParameters2() {
		String[] args = new String[] {
				"-db:true"
				, "-d:destination.html"
				, "-f:file.testlog"
				, "-f:file.testlog"
				, "-f:file.testlog"
				, "-tp:'HH:mm:ss.SSS yyyy-MM-dd'"
		};
		
		App.parseParameters(args);
		
		Assert.assertEquals(true, Constants.USE_DB);
		Assert.assertEquals("destination.html", Constants.DESTINATION_FILE);
		Assert.assertEquals("HH:mm:ss.SSS yyyy-MM-dd", Constants.LOG_DATETIME_FORMAT);
		
		for(String s : App.fileNames) {
			Assert.assertEquals("file.testlog", s);
		}
	}
	
	@Test
	public void applyDefaultConfigTest() {
		Constants.USE_DB = true;
		Constants.CONNECTION_STRING = "1";
		Constants.JDBC_DRIVER = "1";
		Constants.JDBC_USER = "1";
		Constants.JDBC_PASS = "1";
		Constants.LOG_DATETIME_FORMAT = "1";
		
		App.applyDefaultConfig();
		
		Assert.assertEquals("- BLOCKER :", LogLevel.BLOCKER);
		Assert.assertEquals("- OTHERS :", LogLevel.OTHERS);
		
		Assert.assertEquals(false, Constants.USE_DB);
		Assert.assertEquals("jdbc:mysql://localhost:3306/selenium", Constants.CONNECTION_STRING);
		Assert.assertEquals("com.mysql.cj.jdbc.Driver", Constants.JDBC_DRIVER);
		Assert.assertEquals("selenium", Constants.JDBC_USER);
		Assert.assertEquals("sTcSmdFBb5SPpNMb", Constants.JDBC_PASS);
		
		Assert.assertEquals("yyyy-MM-dd HH:mm:ss.SSS", Constants.LOG_DATETIME_FORMAT);
	}
	
	@Test
	public void handleDateTimeWrongFormat() {
		String format = "aloha";
		String defaultFormat = Constants.LOG_DATETIME_FORMAT;
		App.parseParameters(new String[]{ format });
		Assert.assertEquals(defaultFormat, Constants.LOG_DATETIME_FORMAT);
	}
}
