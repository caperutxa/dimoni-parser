package caperutxa.dimoni.parser.report.steps;

import caperutxa.dimoni.parser.report.ReportStep;

public class StepTrivial extends ReportStep {

	public StepTrivial(String content) {
		super(content);
		iconClass = "glyphicon glyphicon-leaf";
		cssClass = "col-xs-11 col-xs-offset-1 text-muted small";
	}

	@Override
	public void parseContent() {
		// Nothing to do
	}

}
