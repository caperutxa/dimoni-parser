package caperutxa.dimoni.parser.report.steps;

import caperutxa.dimoni.parser.report.ReportStep;

public class StepMinor extends ReportStep {

	public StepMinor(String content) {
		super(content);
		iconClass = "glyphicon glyphicon-eye-close";
		cssClass = "col-xs-11 col-xs-offset-1 text-primary";
	}

	@Override
	public void parseContent() {
		// Nothing to do
	}
}
