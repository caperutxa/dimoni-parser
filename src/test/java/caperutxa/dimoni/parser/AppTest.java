package caperutxa.dimoni.parser;

import org.junit.Assert;
import org.junit.Test;

import caperutxa.dimoni.parser.constants.Constants;

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
		};
		
		App.parseParameters(args);
		
		Assert.assertEquals(false, Constants.USE_DB);
		Assert.assertEquals("logs/testParameters.html", Constants.DESTINATION_FILE);
		
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
		};
		
		App.parseParameters(args);
		
		Assert.assertEquals(true, Constants.USE_DB);
		Assert.assertEquals("destination.html", Constants.DESTINATION_FILE);
		
		for(String s : App.fileNames) {
			Assert.assertEquals("file.testlog", s);
		}
	}
}
