package caperutxa.dimoni.parser.report.steps;

import caperutxa.dimoni.parser.report.ReportStep;

public class StepMajor extends ReportStep {

	public StepMajor(String content) {
		super(content);
		iconClass = "glyphicon glyphicon-eye-open";
		cssClass = "col-xs-11 col-xs-offset-1 text-warning";
	}

	@Override
	public void parseContent() {
		// Nothing to do
	}

}
