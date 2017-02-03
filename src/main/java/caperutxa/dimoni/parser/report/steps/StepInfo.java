package caperutxa.dimoni.parser.report.steps;

import caperutxa.dimoni.parser.report.ReportStep;

public class StepInfo extends ReportStep {

	public StepInfo(String content) {
		super(content);
		iconClass = "glyphicon glyphicon-info-sign";
		cssClass = "col-xs-11 col-xs-offset-1 text-info";
	}

	@Override
	public void parseContent() {
		// Nothing to do
	}

}
