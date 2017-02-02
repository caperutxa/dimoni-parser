package caperutxa.dimoni.parser.report.steps;

import caperutxa.dimoni.parser.report.ReportStep;

public class TrivialStep extends ReportStep {

	public TrivialStep(String content) {
		super(content);
		iconClass = "glyphicon glyphicon-leaf";
		cssClass = "col-xs-11 col-xs-offset-1 text-muted small";
	}

	@Override
	public void parseContent() {
		// Nothing to do
	}

}
