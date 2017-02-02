package caperutxa.dimoni.parser.report.steps;

import caperutxa.dimoni.parser.constants.ETestDeclaration;
import caperutxa.dimoni.parser.report.ReportStep;

public class TestTitle extends ReportStep {

	String testName = "";
	String startDate = "";
	String resultMessage = "";
	String totalTime = "";
	String alertClass = "alert alert-success";
	
	public TestTitle() {
		super("Nothing to pass");
	}
	
	public TestTitle(String content) {
		super(content);
		iconClass = "glyphicon glyphicon-thumbs-up";
	}
	
	/**
	 * Test result declaration is used to set the header, alert style
	 * and result message
	 * 
	 * @param success
	 * @param declare
	 */
	public void declareTestResults(boolean success, ETestDeclaration declare) {
		if(!success) {
			iconClass = "glyphicon glyphicon-fire";
			alertClass = "alert alert-danger";
			resultMessage = "<strong>" + declare + "</strong>. Errors found during the test";
		} else {
			if(ETestDeclaration.WARNING == declare) {
				iconClass = "glyphicon glyphicon-exclamation-sign";
				alertClass = "alert alert-warning";
				resultMessage = "<strong>" + declare + "</strong>. The test pass with warnings. Take a look!";
			} else if(ETestDeclaration.MINOR == declare) {
				iconClass = "glyphicon glyphicon-eye-open";
				alertClass = "alert alert-info";
				resultMessage = "<strong>" + declare + "</strong>. The test pass but some internal errors arise. Please, fix your test to remove the errors";
			} else {
				iconClass = "glyphicon glyphicon-thumbs-up";
				alertClass = "alert alert-success";
				resultMessage = "<strong>" + declare + "</strong>. Well done! ";
			}
		}
	}
	
	@Override
	public void parseContent() {
		startDate = content.split("- TEST :")[0];
		testName = content.split("- TEST :")[1];
	}
	
	@Override
	public String toHTML() {
		StringBuilder html = new StringBuilder("<p class='h2'> ")
				.append(testName)
				.append(" <span class='text-muted small'> ")
				.append(startDate)
				.append(" </span></p>");
		
		html.append("<div class='")
				.append(alertClass)
				.append("' role='alert'>")
					.append("<p class='lead'><span class='")
					.append(iconClass)
					.append("' aria-hidden='true'></span> ")
					.append(resultMessage)
					.append("</p>")
					.append("<p>Total time ")
					.append(totalTime)
					.append("</p>")
				.append(" </div>");
		
		return html.toString();
	}

	/* * Getters and setters * */
	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}
	
	

}
