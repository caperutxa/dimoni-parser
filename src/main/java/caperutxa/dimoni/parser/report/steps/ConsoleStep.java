package caperutxa.dimoni.parser.report.steps;

import caperutxa.dimoni.parser.report.ReportStep;

public class ConsoleStep extends ReportStep {

	public ConsoleStep(String content) {
		super(content);
		iconClass = "glyphicon glyphicon-console";
		cssClass = "col-xs-11 col-xs-offset-1";
	}

	@Override
	public void parseContent() {
		// Nothing to do
	}
	

}
