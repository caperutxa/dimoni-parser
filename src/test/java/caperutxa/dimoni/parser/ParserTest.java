package caperutxa.dimoni.parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import caperutxa.dimoni.parser.constants.Constants;
import caperutxa.dimoni.parser.constants.ETestDeclaration;
import caperutxa.dimoni.parser.constants.LogLevel;

public class ParserTest {

	List<String> args = new LinkedList<String>();

	@Before
	public void before() {
		App.applyDefaultConfig();
		Constants.USE_DB = false;
	}
	
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
		Constants.DESTINATION_FILE = "logs/CalendartestingSearchform.html";
		Files.deleteIfExists(new File(Constants.DESTINATION_FILE).toPath());
		
		Parser.startProcess(args);
	}
	
	@Test
	public void successLogFile() throws IOException {
		args.add("src/test/resources/logFiles/SuccessDeclaration.testlog");
		Constants.DESTINATION_FILE = "logs/SuccessDeclaration.testlog.html";
		Files.deleteIfExists(new File(Constants.DESTINATION_FILE).toPath());
		
		Parser.startProcess(args);
		Assert.assertEquals(true, Parser.logModel.isTestSuccess());
		Assert.assertEquals(ETestDeclaration.SUCCESS, Parser.logModel.getTestResultDeclaration());
	}
	
	@Test
	public void warningLogFile() throws IOException {
		args.add("src/test/resources/logFiles/WarningDeclarationWarning.testlog");
		Constants.DESTINATION_FILE = "logs/WarningDeclaration.testlog.html";
		Files.deleteIfExists(new File(Constants.DESTINATION_FILE).toPath());
		
		Parser.startProcess(args);
		Assert.assertEquals(true, Parser.logModel.isTestSuccess());
		Assert.assertEquals(ETestDeclaration.WARNING, Parser.logModel.getTestResultDeclaration());
	}
	
	@Test
	public void MinorLogFile() throws IOException {
		args.add("src/test/resources/logFiles/MinorDeclarationInternalError.testlog");
		Constants.DESTINATION_FILE = "logs/MinorDeclaration.testlog.html";
		Files.deleteIfExists(new File(Constants.DESTINATION_FILE).toPath());
		
		Parser.startProcess(args);
		Assert.assertEquals(true, Parser.logModel.isTestSuccess());
		Assert.assertEquals(ETestDeclaration.MINOR, Parser.logModel.getTestResultDeclaration());
	}
	
	@Test
	public void allTypesOfLogs() throws IOException {
		args.add("src/test/resources/logFiles/allTypesOfLogs.testlog");
		Constants.DESTINATION_FILE = "logs/allTypesOfLogs.testlog.html";
		Files.deleteIfExists(new File(Constants.DESTINATION_FILE).toPath());
		
		Parser.startProcess(args);
		Assert.assertEquals(false, Parser.logModel.isTestSuccess());
		Assert.assertEquals(ETestDeclaration.ERROR, Parser.logModel.getTestResultDeclaration());
		
		Assert.assertEquals(Integer.valueOf(1), Parser.logModel.getLogLevelDeclaration().get(LogLevel.BLOCKER));
		Assert.assertEquals(Integer.valueOf(1), Parser.logModel.getLogLevelDeclaration().get(LogLevel.CONSOLE_ERROR));
		Assert.assertEquals(Integer.valueOf(1), Parser.logModel.getLogLevelDeclaration().get(LogLevel.CONSOLE_WARNING));
		Assert.assertEquals(Integer.valueOf(1), Parser.logModel.getLogLevelDeclaration().get(LogLevel.CRITICAL));
		Assert.assertEquals(Integer.valueOf(1), Parser.logModel.getLogLevelDeclaration().get(LogLevel.ERROR));
		Assert.assertEquals(Integer.valueOf(1), Parser.logModel.getLogLevelDeclaration().get(LogLevel.INTERNAL_ERROR));
		Assert.assertEquals(Integer.valueOf(1), Parser.logModel.getLogLevelDeclaration().get(LogLevel.MAJOR));
		Assert.assertEquals(Integer.valueOf(1), Parser.logModel.getLogLevelDeclaration().get(LogLevel.MINOR));
		Assert.assertEquals(Integer.valueOf(1), Parser.logModel.getLogLevelDeclaration().get(LogLevel.WARNING));
		Assert.assertEquals(Integer.valueOf(13), Parser.logModel.getLogLevelDeclaration().get(LogLevel.OTHERS));
	}
	
}
