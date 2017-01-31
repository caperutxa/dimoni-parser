package caperutxa.dimoni.parser.report.steps;

import caperutxa.dimoni.parser.report.ReportStep;

public class StepCritical extends ReportStep {

	public StepCritical(String content) {
		super(content);
		iconClass = "glyphicon glyphicon-flash";
		cssClass = "col-xs-11 col-xs-offset-1 text-danger";
	}

	@Override
	public void parseContent() {
		content = content.substring(11);
		String[] partial = content.split("- CRITICAL :");
		content = partial[0] + "<strong> CRITICAL ERROR </strong>" + partial[1];
	}

}
