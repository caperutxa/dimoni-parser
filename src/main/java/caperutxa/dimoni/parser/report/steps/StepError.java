package caperutxa.dimoni.parser.report.steps;

import caperutxa.dimoni.parser.report.ReportStep;

public class StepError extends ReportStep {

	public StepError(String content) {
		super(content);
		iconClass = "glyphicon glyphicon-remove-sign";
		cssClass = "col-xs-11 col-xs-offset-1 text-danger";
	}

	@Override
	public void parseContent() {
		content = content.substring(11);
	}

}
