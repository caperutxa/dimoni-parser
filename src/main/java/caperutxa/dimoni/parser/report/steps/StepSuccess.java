package caperutxa.dimoni.parser.report.steps;

import caperutxa.dimoni.parser.report.ReportStep;

public class StepSuccess extends ReportStep {

	public StepSuccess(String content) {
		super(content);
		iconClass = "glyphicon glyphicon-ok-sign";
		cssClass = "col-xs-11 col-xs-offset-1 text-success";
	}

	@Override
	public void parseContent() {
		// Nothing to do
		
	}

}
