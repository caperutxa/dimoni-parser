package caperutxa.dimoni.parser.report.steps;

import caperutxa.dimoni.parser.report.ReportStep;

public class StepBlocker extends ReportStep {

	public StepBlocker(String content) {
		super(content);
		iconClass = "glyphicon glyphicon-fire";
		cssClass = "col-xs-11 col-xs-offset-1 text-danger lead";
	}

	@Override
	public void parseContent() {
		String[] partial = content.split("- BLOCKER :");
		content = partial[0] + "<strong> BLOCKER ERROR </strong>" + partial[1];
		
	}

}
