package caperutxa.dimoni.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import caperutxa.dimoni.parser.constants.Constants;
import caperutxa.dimoni.parser.constants.ELogLevel;
import caperutxa.dimoni.parser.constants.ETestDeclaration;
import caperutxa.dimoni.parser.constants.ETimes;
import caperutxa.dimoni.parser.manager.IterationTestManager;
import caperutxa.dimoni.parser.model.LogModel;
import caperutxa.dimoni.parser.report.ReportsHTML;
import caperutxa.dimoni.parser.report.steps.DebugStep;
import caperutxa.dimoni.parser.report.steps.StepInternalParseError;

public class Parser {
	
	public static LogModel logModel;
	public static SimpleDateFormat logTimeFormat = new SimpleDateFormat(Constants.LOG_DATETIME_FORMAT);
	public static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(Constants.LOG_DATETIME_FORMAT);
	public static ReportsHTML reports;
	public static StringBuilder html;
	
	/**
	 * The process consist in:
	 * Parse file and return a list of string
	 * Extract information of each file
	 * At the end create the reporting
	 * @param args
	 */
	public static void startProcess(List<String> args) {
		if(args.isEmpty()) {
			System.out.println("No file passed to be parsed!");
			return;
		}
		
		int i = 0;
		html = new StringBuilder();
		
		for(String s : args) {
			System.out.println("Parse file : " + s);
			i++;
			logModel = new LogModel();
			logModel.setLogFileAbsolutePath(s);
			logModel.setLogReportFileAbsolutePath(Constants.DESTINATION_FILE);
			reports = new ReportsHTML(logModel);
			List<String> fileContent = new LinkedList<String>();
			
			fileContent = readFileAndConvertToList(s);
			setDeclarationLevel(fileContent);
			parseTestTimes(fileContent);
			if(Constants.USE_DB) {
				new IterationTestManager().registerTestIteration();
			}
			html.append(reports.prepareContent(i, s, fileContent));
		}
		
		writeHTMLReports(reports.createHTMLReports(html.toString()));
		
	}
	/**
	 * Any parser error is placed in a list. This list will be placed at the
	 * end of the reporting.
	 * 
	 * Any row with error will be placed in a report with the raw format
	 * 
	 * @param level
	 * @param message
	 */
	public static void addInternalParseError(ELogLevel level, String message) {
		StringBuilder content = new StringBuilder()
				.append(logTimeFormat.format(new Date()))
				.append(" - ")
				.append(level.name())
				.append(" : ")
				.append(message);
		
		reports.getHtmlConsole().add(new StepInternalParseError(content.toString()));
	}
	
	/**
	 * Add messages to console list. This list will be added to the report at the end of test steps
	 * 
	 * @param level
	 * @param message
	 */
	public static void addHtmlConsoleStep(ELogLevel level, String message) {
		StringBuilder content = new StringBuilder()
				.append(logTimeFormat.format(new Date()))
				.append(" - ")
				.append(level.name())
				.append(" : ")
				.append(message);
		
		reports.getHtmlConsole().add(new DebugStep(content.toString()));
	}
	
	/**
	 * When an error arise we need to get the message and the stack trace
	 * 
	 * @param e
	 */
	public static void logInternaleError(Exception e) {
		addInternalParseError(ELogLevel.INTERNAL_ERROR, e.toString());
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		addInternalParseError(ELogLevel.INTERNAL_ERROR, sw.toString());
		addInternalParseError(ELogLevel.DEBUG, "End internal error");
	}
	
	/**
	 * Read a file and return a list of string
	 * 
	 * @param filePath
	 * @return LinkedList<String>
	 */
	public static List<String> readFileAndConvertToList(String filePath) {
		List<String> list = new LinkedList<String>();
		
		BufferedReader br = null;
		
		try {
			String line;
			br = new BufferedReader(new FileReader(filePath));

			while ((line = br.readLine()) != null) {
				list.add(line);
			}
		} catch (IOException e) {
			logInternaleError(e);
		} finally {
			try {
				if (null != br) br.close();
			} catch (IOException ex) {
				logInternaleError(ex);
			}
		}
		
		return list;
	}
	
	/**
	 * Iterate over the list looking for errors or warnings
	 * and then declare test result
	 * 
	 * Also look for test title (TEST step)
	 */
	public static void setDeclarationLevel(List<String> list) {
		for(String s : list) {
			if(s.contains("- " + ELogLevel.BLOCKER + " :")) {
				addLogLevelDeclarationToTestModel(ELogLevel.BLOCKER);
			} else if (s.contains("- " + ELogLevel.CRITICAL + " :")) {
				addLogLevelDeclarationToTestModel(ELogLevel.CRITICAL);
			} else if(s.contains("- " + ELogLevel.ERROR + " :")) {
				addLogLevelDeclarationToTestModel(ELogLevel.ERROR);
			} else if(s.contains("- " + ELogLevel.INTERNAL_ERROR + " :")) {
				addLogLevelDeclarationToTestModel(ELogLevel.INTERNAL_ERROR);
			} else if(s.contains("- " + ELogLevel.WARNING + " :")) {
				addLogLevelDeclarationToTestModel(ELogLevel.WARNING);
			} else if(s.contains("- " + ELogLevel.TEST + " :")) {
				addLogLevelDeclarationToTestModel(ELogLevel.OTHERS);
				logModel.getTestName().add(s.split("- " + ELogLevel.TEST + " :")[1]);
			} else {
				addLogLevelDeclarationToTestModel(ELogLevel.OTHERS);
			}
		}
		
		if(logModel.getLogLevelDeclaration().containsKey(ELogLevel.BLOCKER)
				|| logModel.getLogLevelDeclaration().containsKey(ELogLevel.CRITICAL)
				|| logModel.getLogLevelDeclaration().containsKey(ELogLevel.ERROR)
		){
			logModel.setTestSuccess(false);
			logModel.setTestResultDeclaration(ETestDeclaration.ERROR);
		} else if(logModel.getLogLevelDeclaration().containsKey(ELogLevel.WARNING)) {
			logModel.setTestSuccess(true);
			logModel.setTestResultDeclaration(ETestDeclaration.WARNING);
		} else if(logModel.getLogLevelDeclaration().containsKey(ELogLevel.INTERNAL_ERROR)) {
			logModel.setTestSuccess(true);
			logModel.setTestResultDeclaration(ETestDeclaration.MINOR);
		} else {
			logModel.setTestSuccess(true);
			logModel.setTestResultDeclaration(ETestDeclaration.SUCCESS);
		}
	}
	
	/**
	 * Add one to log level declaration
	 * 
	 * @param l
	 */
	static void addLogLevelDeclarationToTestModel(ELogLevel l) {
		int n = 1;
		
		if(logModel.getLogLevelDeclaration().containsKey(l)) {
			n = logModel.getLogLevelDeclaration().get(l) + 1;
		}

		logModel.getLogLevelDeclaration().put(l, n);
	}
	
	/**
	 * Parse times from logs
	 * Is important to maintain the format and use the method TestConstaines.logTime
	 * to leave the times with the correct format
	 * 
	 * The total test time differs from the one used in the logs
	 * This total time get the first time to the end
	 * 
	 * The last step should to be placed by hand
	 */
	public static void parseTestTimes(List<String> list) {
		for(ETimes t : ETimes.values()) {
			logModel.getTestTimeList().put(t, new LinkedHashMap<String, Long>());
		}
		
		// Default Total test times
		logModel.setStartTestTime(new DateTime());
		logModel.setEndTestTime(logModel.getStartTestTime());
		
		try {
			logModel.setStartTestTime(dateTimeFormatter.parseDateTime(list.get(0).substring(0,Constants.LOG_DATETIME_FORMAT.length())));
			// Avoid testing times
			logModel.setEndTestTime(logModel.getStartTestTime());
			logModel.setEndTestTime(dateTimeFormatter.parseDateTime(list.get(list.size() - 1).substring(0,Constants.LOG_DATETIME_FORMAT.length())));
		} catch(IndexOutOfBoundsException oob) {
			Parser.logInternaleError(oob);
		} catch(IllegalArgumentException iae) {
			Parser.logInternaleError(iae);
		}
		
		logModel.setTestingTime(new Interval(logModel.getStartTestTime(), logModel.getEndTestTime()));
		long t = (logModel.getTestingTime().getEndMillis() - logModel.getTestingTime().getStartMillis());
		logModel.getTestTimeList().get(ETimes.TEST_TIME).put("Total", t);
		
		// partial times
		DateTime stepTime = logModel.getStartTestTime();
		String stepName = "Preparation";
		int i = 0;
		
		for(String s : list) {
			i++;
			try {
				if(s.contains("- " + ELogLevel.STEP + " :")) {
					DateTime partial = dateTimeFormatter.parseDateTime(s.substring(0,23));
					Interval inter = new Interval(stepTime, partial);
					long ts = (inter.getEndMillis() - inter.getStartMillis());
					logModel.getTestTimeList().get(ETimes.STEP_TIME).put(stepName + " (" + i + ")", ts);
					stepTime = partial;
					stepName = s.split("- STEP : ")[1];
				} else if(s.contains("- " + ELogLevel.TIME + " :")) {
					String[] partialTimeLine = s.split(" - TIME : ")[1].split(" #@# ");
					String name = partialTimeLine[2] + " (" + i + ")";
					long ts = Long.valueOf(partialTimeLine[0]);
					if(ETimes.LOAD_TIME.toString().equals(partialTimeLine[1])) {
						logModel.getTestTimeList().get(ETimes.LOAD_TIME).put(name, ts);
					} else if(ETimes.RESPONSE_TIME.toString().equals(partialTimeLine[1])) {
						logModel.getTestTimeList().get(ETimes.RESPONSE_TIME).put(name, ts);
					} else if(ETimes.WAIT.toString().equals(partialTimeLine[1])) {
						logModel.getTestTimeList().get(ETimes.WAIT).put(name, ts);
					} else {
						logModel.getTestTimeList().get(ETimes.OTHER).put(name, ts);
					}
				}
			} catch(Exception e) {
				logInternaleError(e);
			}
		}
		
		// set the last step by hand
		i++;
		Interval inter = new Interval(new DateTime(), new DateTime());
		try {
			inter = new Interval(stepTime, logModel.getEndTestTime());
		} catch(IllegalArgumentException iae) {
			logInternaleError(iae);
		}
		long ts2 = (inter.getEndMillis() - inter.getStartMillis());
		logModel.getTestTimeList().get(ETimes.STEP_TIME).put(stepName + " (" + i + ")", ts2);
	}
	
	/**
	 * Create HTML reports from log file and console outputs
	 */
	public static void writeHTMLReports(String report) {
		//String report = new ReportsHTML().getContent(testModel.logFileList, testModel.logConsoleFileList);
		
		try {
			FileWriter f = new FileWriter(Constants.DESTINATION_FILE,true);
			BufferedWriter bw = new BufferedWriter(f);
	    	bw.write(report);
	    	bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
