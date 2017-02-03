package caperutxa.dimoni.parser.report.steps;

import caperutxa.dimoni.parser.report.ReportStep;

public class StepTime extends ReportStep {

	String time = "";
	String timeType = "";
	String page = "";
	
	public StepTime(String content) {
		super(content);
		iconClass = "glyphicon glyphicon-time";
		cssClass = "col-xs-11 col-xs-offset-1";
	}

	@Override
	public void parseContent() {
		String[] parse = content.split("- TIME :")[1].split("#@#");
		time = parse[0];
		timeType = parse[1];
		page = parse[2];
	}
	
	@Override
	public String toHTML(){
		StringBuilder html = new StringBuilder("<div class='")
				.append(cssClass)
				.append("'>");
		
		html.append("<span class='")
				.append(iconClass)
				.append("' > </span> ")
				.append(page)
				.append(" ")
				.append(timeType)
				.append(" ")
				.append(time)
				.append(" ms");
		
		html.append("</div>");
		
		return html.toString();
	}

}
