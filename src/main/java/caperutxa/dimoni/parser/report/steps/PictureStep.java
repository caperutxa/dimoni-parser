package caperutxa.dimoni.parser.report.steps;

import caperutxa.dimoni.parser.report.ReportStep;

public class PictureStep extends ReportStep {

	String imgPath = "";
	String parseContent = "";
	
	public PictureStep(String content) {
		super(content);
		iconClass = "glyphicon glyphicon-picture";
		cssClass = "col-xs-11 col-xs-offset-1";
	}

	@Override
	public void parseContent() {
		parseContent = content;
		imgPath = content.split("- PICTURE : ")[1];
	}
	
	@Override
	public String toHTML(){
		StringBuilder html = new StringBuilder("<div class='")
				.append(cssClass)
				.append("'>");
		html.append("<span class='")
				.append(iconClass)
				.append("' > </span> ")
				.append(parseContent);
		html.append("</div>");
		
		html.append("<div class='")
				.append(cssClass)
				.append("'>");
		
		html.append("<img class='img-responsive img-thumbnail' src='")
				.append(imgPath)
				.append("' alt='Image Not found in ")
				.append(imgPath)
				.append("' /> ");
		
		html.append("</div>");
		
		return html.toString();
	}
	
}
