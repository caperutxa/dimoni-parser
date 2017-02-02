package caperutxa.dimoni.parser.report.steps;

import caperutxa.dimoni.parser.report.ReportStep;

public class StepTest extends ReportStep {

	String stepName = "";
	String stepTime = "";
	
	public StepTest(String content) {
		super(content);
		iconClass = "glyphicon glyphicon-scale";
		cssClass = "h4";
	}

	@Override
	public void parseContent() {
		stepName = content.split("- STEP :")[1];
		stepTime = content.split("- STEP :")[0];
	}
	
	@Override
	public String toHTML(){
		StringBuilder html = new StringBuilder("<div class='")
				.append(cssClass)
				.append("'>");
		
		html.append("<span class='")
				.append(iconClass)
				.append("' > </span> ")
				.append(stepName)
				.append(" - <small>")
				.append(stepTime)
				.append(" </small>");
		
		html.append("</div>");
		
		return html.toString();
	}

}
