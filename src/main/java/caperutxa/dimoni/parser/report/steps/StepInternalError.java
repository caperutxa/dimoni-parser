package caperutxa.dimoni.parser.report.steps;

import caperutxa.dimoni.parser.report.ReportStep;

public class StepInternalError extends ReportStep {

	public StepInternalError(String content) {
		super(content);
		iconClass = "glyphicon glyphicon-wrench";
		cssClass = "col-xs-11 col-xs-offset-1 text-danger small";
	}

	@Override
	public void parseContent() {
		// Nothing to do
	}

}
