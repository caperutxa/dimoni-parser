package caperutxa.dimoni.parser.report.steps;

import caperutxa.dimoni.parser.report.ReportStep;

public class StepDebug extends ReportStep {

	public StepDebug(String content) {
		super(content);
		iconClass = "glyphicon glyphicon-pencil";
		cssClass = "col-xs-11 col-xs-offset-1 text-muted";
	}

	@Override
	public void parseContent() {
		// Nothing to do
	}

}
