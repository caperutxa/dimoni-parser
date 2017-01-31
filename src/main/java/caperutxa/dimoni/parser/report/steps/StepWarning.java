package caperutxa.dimoni.parser.report.steps;

import caperutxa.dimoni.parser.report.ReportStep;

public class StepWarning extends ReportStep {

	public StepWarning(String content) {
		super(content);
		iconClass = "glyphicon glyphicon-exclamation-sign";
		cssClass = "col-xs-11 col-xs-offset-1 text-warning";
	}

	@Override
	public void parseContent() {
		content = content.substring(11);
	}

}
