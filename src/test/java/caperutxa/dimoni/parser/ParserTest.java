package caperutxa.dimoni.parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import caperutxa.dimoni.parser.constants.Constants;

public class ParserTest {

	List<String> args = new LinkedList<String>();
	
	@Test
	public void testProcess() throws IOException {
		args.add("src/test/resources/logFiles/ErrorDeclarationBlocker.testlog");
		Constants.DESTINATION_FILE = "logs/testProcess.test";
		Files.deleteIfExists(new File(Constants.DESTINATION_FILE).toPath());
		
		Parser.startProcess(args);
	}
	
	@Test
	public void testProcessMoreThanOneFile() throws IOException {
		args.add("src/test/resources/logFiles/ErrorDeclarationBlocker.testlog");
		args.add("src/test/resources/logFiles/ConsoleLog.testconsole");
		
		Constants.DESTINATION_FILE = "logs/testProcessMoreThanOneFile.test";
		Files.deleteIfExists(new File(Constants.DESTINATION_FILE).toPath());
		
		Parser.startProcess(args);
	}
	
	@Test
	public void nonExistingFile() throws IOException {
		args.add("NonExistingFile.test");
		Constants.DESTINATION_FILE = "logs/nonExistingFile.test";
		Files.deleteIfExists(new File(Constants.DESTINATION_FILE).toPath());
		
		Parser.startProcess(args);
	}
	
	@Test
	public void externalLogFile() throws IOException {
		args.add("src/test/resources/logFiles/CalendartestinSearchform.txt");
		Constants.DESTINATION_FILE = "logs/CalendartestinSearchform.html";
		Files.deleteIfExists(new File(Constants.DESTINATION_FILE).toPath());
		
		Parser.startProcess(args);
	}
}
