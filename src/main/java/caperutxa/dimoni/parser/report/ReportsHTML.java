package caperutxa.dimoni.parser.report;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import caperutxa.dimoni.parser.constants.ELogLevel;
import caperutxa.dimoni.parser.constants.ETimes;
import caperutxa.dimoni.parser.model.LogModel;
import caperutxa.dimoni.parser.report.steps.ConsoleStep;
import caperutxa.dimoni.parser.report.steps.DebugStep;
import caperutxa.dimoni.parser.report.steps.InfoStep;
import caperutxa.dimoni.parser.report.steps.PictureStep;
import caperutxa.dimoni.parser.report.steps.StepBlocker;
import caperutxa.dimoni.parser.report.steps.StepCritical;
import caperutxa.dimoni.parser.report.steps.StepError;
import caperutxa.dimoni.parser.report.steps.StepInternalError;
import caperutxa.dimoni.parser.report.steps.StepTest;
import caperutxa.dimoni.parser.report.steps.StepWarning;
import caperutxa.dimoni.parser.report.steps.TestTitle;
import caperutxa.dimoni.parser.report.steps.TimeStep;
import caperutxa.dimoni.parser.report.steps.TrivialStep;


/**
 * Create an html report
 * 
 * The report header is in src/main/resources/html/htmlReportHeader.html
 * and is called by getHTMLReportHead() method
 * 
 * @author caperutxa
 *
 */
public class ReportsHTML {

	LogModel logModel;
	
	StringBuilder html = new StringBuilder();
	
	List<ReportStep> htmlSteps = new LinkedList<ReportStep>();
	List<ReportStep> htmlConsole = new LinkedList<ReportStep>();
	
	public ReportsHTML(LogModel model) {
		logModel = model;
	}
	
	/**
	 * Prepare the content. Note that each file is a new report section
	 * All is joined into on String builder through this method
	 * 
	 * @param n
	 * @param fileName
	 * @param list
	 */
	public String prepareContent(int n, String fileName, List<String> list) {
		extractContent(list);
		
		html.append("<div class='container-fluid'>")
				.append(System.lineSeparator())
				.append(getHTMLReportHead(n, fileName))
				.append(System.lineSeparator())
				.append("<div id='testContainer").append(n).append("'></div>")
				.append(System.lineSeparator())
				.append(getHTMLTitle())
				.append(System.lineSeparator())
				.append("<div id='stepListContainer").append(n).append("' class='col-md-9 col-xs-12'>")	
				.append(System.lineSeparator())
				.append(getHTMLStepList(htmlSteps))
				.append(System.lineSeparator())
				.append("</div>")
				.append(System.lineSeparator())
				.append("<div id='columnListContainer").append(n).append("' class='col-md-3 col-xs-12'>")
				.append(System.lineSeparator())	
				.append(getHTMLSummary())
				.append(System.lineSeparator())
				.append(getHTMLTimeColumn())
				.append(System.lineSeparator())
				.append("</div>")
				.append("</div><!-- caperutxa -->")
				.append("<div class='container-fluid'>")
				.append(getHTMLConsole(n, htmlConsole))
				.append("</div><!-- caperutxa -->")
				.append(System.lineSeparator());
		
		return html.toString();
	}
	
	/**
	 * Return the html report as string
	 * @return
	 */
	public String createHTMLReports(String content) {
		StringBuilder s = new StringBuilder();
		
		s.append("<!DOCTYPE html><html>")
			.append(System.lineSeparator())
			.append(getHTMLHeaders())
			.append(System.lineSeparator())
			.append("<body>")
			.append(content)
			.append("</body>")
			.append(System.lineSeparator())
			.append("</html><!-- caperutxa -->");
		
		return s.toString();
	}
	
	/**
	 * Extract content from the string list
	 * and place it in the correct lists
	 * @param list
	 */
	void extractContent(List<String> list) {
		for(String s : list) {
			if(s.contains("- " + ELogLevel.TEST + " :")
					|| s.contains("- " + ELogLevel.DEBUG + " :")
			) {
				htmlSteps.add(new DebugStep(s));
			} else if(s.contains("- " + ELogLevel.BLOCKER + " :")) {
				StepBlocker sb = new StepBlocker(s);
				sb.parseContent();
				htmlSteps.add(sb);
			} else if(s.contains("- " + ELogLevel.CRITICAL + " :")) {
				StepCritical sc = new StepCritical(s);
				sc.parseContent();
				htmlSteps.add(sc);
			} else if(s.contains("- " + ELogLevel.ERROR + " :")) {
				StepError se = new StepError(s);
				se.parseContent();
				htmlSteps.add(se);
			} else if(s.contains("- " + ELogLevel.INFO + " :")) {
				InfoStep i = new InfoStep(s);
				i.parseContent();
				htmlSteps.add(i);
			} else if(s.contains("- " + ELogLevel.INTERNAL_ERROR + " :")) {
				StepInternalError sie = new StepInternalError(s);
				sie.parseContent();
				htmlSteps.add(sie);
			} else if(s.contains("- " + ELogLevel.PICTURE + " :")) {
				PictureStep p = new PictureStep(s);
				p.parseContent();
				htmlSteps.add(p);
			} else if(s.contains("- " + ELogLevel.TIME + " :")) {
				TimeStep t = new TimeStep(s);
				t.parseContent();
				htmlSteps.add(t);
			} else if(s.contains("- " + ELogLevel.TRIVIAL + " :")) {
				TrivialStep t = new TrivialStep(s);
				t.parseContent();
				htmlSteps.add(t);
			} else if(s.contains("- " + ELogLevel.STEP + " :")) {
				StepTest t = new StepTest(s);
				t.parseContent();
				htmlSteps.add(t);
			} else if(s.contains("- " + ELogLevel.WARNING + " :")) {
				StepWarning w = new StepWarning(s);
				w.parseContent();
				htmlSteps.add(w);
			} else {
				DebugStep d = new DebugStep(s);
				d.setCssClass("col-xs-11 col-xs-offset-1");
				htmlSteps.add(d);
			}
		}
	}
	
	/**
	 * Extract information from console
	 * Marionete and gecko driver throw this kind of outputs.
	 * 
	 * @param list
	 */
	public void extractConsoleContent(List<String> list) {
		for(String s : list) {
			htmlConsole.add(new ConsoleStep(s));
		}
	}
	
	/**
	 * Get html headers to place in the report
	 * @return
	 */
	String getHTMLHeaders() {
		StringBuilder s = new StringBuilder("<head>")
				.append("<meta charset='utf-8' />")
				.append("<meta name='viewport' content='width=device-width, initial-scale=1.0' />")
				.append("<meta name=\"author\" content=\"Caperutxa\" />")
				.append("<link rel=\"icon\" href=\"http://icon-icons.com/icons2/586/PNG/128/robot-with-plug_icon-icons.com_55259.png\">")
				.append("<title>")
				.append(logModel.getTestNameAsString())
				.append("</title>")
				.append("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">")
				//.append("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\" integrity=\"sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa\" crossorigin=\"anonymous\"></script>")
				.append("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js\" ></script>")
				.append("</head>");
		
		return s.toString();
	}
	
	/**
	 * Return the headers with links
	 * 
	 * @return
	 */
	String getHTMLReportHead(int n, String name) {
		StringBuilder s = new StringBuilder(System.lineSeparator())
				.append("<div class='row' >")
					.append("<nav class='navbar navbar-inverse'>")
						.append(System.lineSeparator())
						.append("<div id='containerNavBar").append(n).append("'>")
							.append("<div class='navbar-header'>")
								.append("<button type='button' class='navbar-toggle collapsed' data-toggle='collapse' data-target='#navbar").append(n).append("' aria-expanded='false'>")
									.append("<span class='sr-only'>Toggle navigation</span>")
									.append("<span class='icon-bar'></span>")
									.append("<span class='icon-bar'></span>")
									.append("<span class='icon-bar'></span>")
								.append("</button>")
								.append("<a class='navbar-brand' href='").append(name).append("'>").append(name).append("</a>")
							.append("</div><!--caperutxa -->")
							.append(System.lineSeparator())
							.append("<div id='navbar").append(n).append("' class='navbar-collapse collapse' style='padding-left:15%;'>")
								.append("<ul id='linkCollection").append(n).append("' class='nav navbar-nav'>")
									.append("<li title='Test steps'><a href='#stepListContainer").append(n).append("'><span class='glyphicon glyphicon-th-list'></span></a></li>")
									.append("<li title='Statistics section'><a href='#columnListContainer").append(n).append("'><span class='glyphicon glyphicon-signal'></span></a></li>")
									.append("<li title='Parse troubles'><a href='#consoleSection").append(n).append("'><span class='glyphicon glyphicon-console'></span></a></li>")
								.append("</ul>")
							.append("</div><!--caperutxa -->")
							.append(System.lineSeparator())
						.append("</div>")
						.append(System.lineSeparator())
					.append("</nav>")
				.append("</div>")
				.append(System.lineSeparator());
		
		return s.toString();
	}
	
	/**
	 * html title with the content
	 * 
	 * @return
	 */
	String getHTMLTitle() {
		TestTitle t = new TestTitle();
		t.declareTestResults(logModel.isTestSuccess(), logModel.getTestResultDeclaration());
		t.setTestName(logModel.getTestNameAsString());
		t.setStartDate(logModel.getTestStartTimeAsString());
		t.setTotalTime(logModel.getTotalTestTime());
		
		StringBuilder s = new StringBuilder("<div>");
		
			s.append("<div class='row'>")
				.append("<div class='col-xs-12'>")
				.append("<div class='well'>");
			
			s.append(t.toHTML());
			
			s.append("</div>")
				.append("</div>")
				.append("</div>");
		
		s.append("</div>");
		
		return s.toString();
	}
	
	/**
	 * 
	 * @return
	 */
	String getHTMLStepList(List<ReportStep> list) {
		StringBuilder s = new StringBuilder("<div>");
		
		for(ReportStep r : list) {
			s.append("<div class='row'>");
			
			s.append(r.toHTML());
			
			s.append("</div>");
		}
		
		s.append("</div>");
		
		return s.toString();
	}
	
	/**
	 * 
	 * @return
	 */
	String getHTMLConsole(int n, List<ReportStep> list) {
		StringBuilder s = new StringBuilder("<div id='consoleSection").append(n).append("'>")
				.append("<div class='row'>")
				.append("<p class='bg-info h3' style='padding:10px;' > Console outputs </p>")
				.append("</div>");
		
		for(ReportStep r :  list) {
			s.append("<div class='row'>");
			
			s.append(r.toHTML());
			
			s.append("</div>");
		}
		
		s.append("</div>");
		
		return s.toString();
	}
	
	/**
	 * 
	 * @return
	 */
	String getHTMLSummary() {
		StringBuilder s = new StringBuilder("<div>")
				.append("<p class='bg-info h3' style='padding:10px;'> Summary </p>");
		
		for(Map.Entry<ELogLevel, Integer> entry :  logModel.getLogLevelDeclaration().entrySet()) {
			s.append("<div class='row'><div class='col-xs-11 col-xs-offset-1'>");
			
			s.append(entry.getKey())
				.append(" : ")
				.append(entry.getValue());
			
			s.append("</div></div>");
		}
		
		s.append("</div>");
		
		return s.toString();
	}
	
	/**
	 * 
	 * @return
	 */
	String getHTMLTimeColumn() {
		StringBuilder s = new StringBuilder()
				.append("<p class='bg-info h3' style='padding:10px;'> Times </p>")
				.append(drawChart(ETimes.STEP_TIME.name(), logModel.getTestTimeList().get(ETimes.STEP_TIME)))
				.append(drawTable(ETimes.STEP_TIME.name(), logModel.getTestTimeList().get(ETimes.STEP_TIME)))
				.append(drawChart(ETimes.LOAD_TIME.name(), logModel.getTestTimeList().get(ETimes.LOAD_TIME)))
				.append(drawTable(ETimes.LOAD_TIME.name(), logModel.getTestTimeList().get(ETimes.LOAD_TIME)))
				.append(drawChart(ETimes.RESPONSE_TIME.name(), logModel.getTestTimeList().get(ETimes.RESPONSE_TIME)))
				.append(drawTable(ETimes.RESPONSE_TIME.name(), logModel.getTestTimeList().get(ETimes.RESPONSE_TIME)));
		
		return s.toString();
	}
	
	/**
	 * Draw a chart for a desired time
	 * If there are no entries for that time, the chart is not drawn
	 * @param title
	 * @param map
	 * @return
	 */
	String drawChart(String title, Map<String, Long> map) {
		StringBuilder labels = new StringBuilder("  ");
		StringBuilder data = new StringBuilder("  ");
		StringBuilder chart = new StringBuilder(" ");
		boolean chartExist = false;
		
		for(Map.Entry<String, Long> entry : map.entrySet()) {
			chartExist = true;
			labels.append("'")
				.append(entry.getKey())
				.append("',");
			data.append(entry.getValue().toString()).append(",");
		}
		
		if(chartExist) {
			chart.append("<div class='row' style='margin-bottom: 25px;' >");
			
			chart.append("<canvas id='")
					.append(title)
					.append("'></canvas>")
					.append("<script>")
					.append("var ctx").append(title)
					.append(" = document.getElementById('")
					.append(title)
					.append("');")
					.append("var line").append(title)
					.append(" = new Chart(ctx").append(title).append(", {");
					
			chart.append("type: 'line',")
					.append("data: {")
						.append("labels: [")
						.append(labels.substring(0, labels.length() - 1))
						.append("],")
						.append("datasets: [ {")
						.append("fill: false,")
						.append("lineTension: 0.1,")
						.append("data: [")
						.append(data.substring(0, data.length() - 1))
						.append("] } ]")
					.append("},")
					.append("options: {")
						.append("responsive: true,")
						.append("title: {")
							.append("display: true,")
							.append("text: '").append(title).append("',")
							.append("position: 'top'")
						.append("},")
						.append("legend: { display: false }")
					.append("}");
			
			chart.append("});")
					.append("</script>");
			
			chart.append("</div>");
		}
		
		return chart.toString();
	}
	
	/**
	 * Draw a table with the time information
	 * If there are no entries the table is not drawn
	 * @param title
	 * @param map
	 * @return
	 */
	String drawTable(String title, Map<String, Long> map) {
		boolean chartExist = false;
		
		StringBuilder table = new StringBuilder("<div class='row' style='margin-bottom: 25px;'>");
		
		table.append("<table class='table table-striped table-bordered table-hover table-condensed text-center' >")
				.append("<tr><th class='text-center'>")
				.append(title)
				.append("</th><th  class='text-center'>Time (ms)</th></tr>");
		
		for(Map.Entry<String, Long> entry : map.entrySet()) {
			chartExist = true;
			table.append("<tr>")
				.append("<td>").append(entry.getKey()).append("</td>")
				.append("<td>").append(entry.getValue().toString()).append("</td>")
				.append("</tr>");
		}
		
		table.append("</table>");
		
		table.append("</div>");
		
		return chartExist ? table.toString() : " ";
	}
	
	
	
	/* * getters and setters * */
	public LogModel getLogModel() {
		return logModel;
	}

	public void setLogModel(LogModel logModel) {
		this.logModel = logModel;
	}

	public StringBuilder getHtml() {
		return html;
	}

	public void setHtml(StringBuilder html) {
		this.html = html;
	}

	public List<ReportStep> getHtmlSteps() {
		return htmlSteps;
	}

	public void setHtmlSteps(List<ReportStep> htmlSteps) {
		this.htmlSteps = htmlSteps;
	}

	public List<ReportStep> getHtmlConsole() {
		return htmlConsole;
	}

	public void setHtmlConsole(List<ReportStep> htmlConsole) {
		this.htmlConsole = htmlConsole;
	}
	
}
