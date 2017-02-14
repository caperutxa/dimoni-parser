package caperutxa.dimoni.parser.model;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import caperutxa.dimoni.parser.Parser;
import caperutxa.dimoni.parser.constants.Constants;
import caperutxa.dimoni.parser.constants.ELogLevel;
import caperutxa.dimoni.parser.constants.ETestDeclaration;
import caperutxa.dimoni.parser.constants.ETimes;

/**
 * Each log file is parsed looking for some expectatives
 * This class contains the model
 * 
 * @author caperutxa
 *
 */
public class LogModel {
	
	List<String> testName = new LinkedList<String>();
	String environment = "Unknown environment";
	
	DateTime startTestTime; 
	DateTime endTestTime;
	Interval testingTime;
	DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(Constants.LOG_DATETIME_FORMAT);
	
	boolean testSuccess = true;
	ETestDeclaration testResultDeclaration = ETestDeclaration.SUCCESS;
	
	String logFileAbsolutePath;
	List<String> logFileList;
	String logReportFileAbsolutePath;
	
	Map<ELogLevel, Integer> logLevelDeclaration = new LinkedHashMap<ELogLevel, Integer>();
	Map<ETimes, Map<String, Long>> testTimeList = new LinkedHashMap<ETimes, Map<String, Long>>();
	
	/**
	 * Return the test name as string
	 * @return
	 */
	public String getTestNameAsString() {
		if(testName.isEmpty()) {
			return "Test name not found";
		}
		
		StringBuilder name = new StringBuilder();
		for(String s : testName) {
			name.append(s).append("<br />");
		}
		
		return name.toString();
	}
	
	/**
	 * Return the last test name in the list (detected)
	 * or a default message if the list is empty
	 * @return
	 */
	public String getLastTestName() {
		if(!testName.isEmpty()) {
			return testName.get(testName.size() - 1);
		}
		
		return "Test name not found";
	}
	
	public String getTestStartTimeAsString() {
		return dateTimeFormatter.print(startTestTime);
	}
	
	/**
	 * Return the duration time with format days, h, min, s and ms
	 * 
	 * @return
	 */
	public String getTotalTestTime() {
		Period p = (new Duration(testingTime.getEndMillis() - testingTime.getStartMillis())).toPeriod();
		PeriodFormatter format = new PeriodFormatterBuilder()
				//.printZeroAlways()
				.appendDays()
				.appendSuffix(" days")
				.appendSeparator(", ")
				.appendHours()
				.appendSuffix("h ")
				.appendSeparator("")
				.appendMinutes()
				.appendSuffix("m ")
				.appendSeparator("")
				.appendSeconds()
				.appendSuffix("s ")
				.appendSeparator("")
				.appendMillis3Digit()
				.appendSuffix("ms ")
				.toFormatter();
		
		return format.print(p);
	}
	
	/**
	 * Return the message to be inserted in DB
	 * If there are no troubles returns null
	 * 
	 * @return
	 */
	public String getLogLevelDeclarationCountAsString() {
		if(logLevelDeclaration.isEmpty()) {
			return null;
		}
		
		StringBuilder s = new StringBuilder();
		
		for(Map.Entry<ELogLevel, Integer> entry : logLevelDeclaration.entrySet()) {
			s.append(" , ")
			.append(entry.getKey())
			.append(" : ")
			.append(entry.getValue());
		}
		
		return s.toString().substring(2);
	}
	
	/**
	 * Set test environment to the environment attribute.
	 * This will be used to leave logs in DB
	 * 
	 * @param s the raw log string
	 */
	public void setEnvironmentFromLog(String s) {
		try {
			environment = s.split("- " + ELogLevel.ENVIRONMENT + " :")[1];
		} catch(Exception e) {
			Parser.addHtmlConsoleStep(ELogLevel.INTERNAL_ERROR, "Unable to parse environment from logs");
			Parser.addHtmlConsoleStep(ELogLevel.INTERNAL_ERROR, e.toString());
		}
	}
	
	
	/* * getters and setters * */
	public List<String> getTestName() {
		return testName;
	}
	public void setTestName(List<String> testName) {
		this.testName = testName;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public DateTime getStartTestTime() {
		return startTestTime;
	}
	public void setStartTestTime(DateTime startTestTime) {
		this.startTestTime = startTestTime;
	}
	public DateTime getEndTestTime() {
		return endTestTime;
	}
	public void setEndTestTime(DateTime endTestTime) {
		this.endTestTime = endTestTime;
	}
	public Interval getTestingTime() {
		return testingTime;
	}
	public void setTestingTime(Interval testingTime) {
		this.testingTime = testingTime;
	}
	public boolean isTestSuccess() {
		return testSuccess;
	}
	public void setTestSuccess(boolean testSuccess) {
		this.testSuccess = testSuccess;
	}
	public ETestDeclaration getTestResultDeclaration() {
		return testResultDeclaration;
	}
	public void setTestResultDeclaration(ETestDeclaration testResultDeclaration) {
		this.testResultDeclaration = testResultDeclaration;
	}
	public String getLogFileAbsolutePath() {
		return logFileAbsolutePath;
	}
	public void setLogFileAbsolutePath(String logFileAbsolutePath) {
		this.logFileAbsolutePath = logFileAbsolutePath;
	}
	public List<String> getLogFileList() {
		return logFileList;
	}
	public void setLogFileList(List<String> logFileList) {
		this.logFileList = logFileList;
	}
	public String getLogReportFileAbsolutePath() {
		return logReportFileAbsolutePath;
	}
	public void setLogReportFileAbsolutePath(String logReportFileAbsolutePath) {
		this.logReportFileAbsolutePath = logReportFileAbsolutePath;
	}
	public Map<ELogLevel, Integer> getLogLevelDeclaration() {
		return logLevelDeclaration;
	}
	public void setLogLevelDeclaration(Map<ELogLevel, Integer> logLevelDeclaration) {
		this.logLevelDeclaration = logLevelDeclaration;
	}
	public Map<ETimes, Map<String, Long>> getTestTimeList() {
		return testTimeList;
	}
	public void setTestTimeList(Map<ETimes, Map<String, Long>> testTimeList) {
		this.testTimeList = testTimeList;
	}
	
}
