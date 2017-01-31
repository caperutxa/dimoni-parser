package caperutxa.dimoni.parser.report;

/**
 * Default step class
 * Override toHTML to customize templates
 * Implement a parsed content method to extract the information
 * 
 * @author caperutxa
 *
 */
public abstract class ReportStep {

	protected String cssClass = "";
	protected String iconClass = "";
	protected String content;
	
	public ReportStep(String content) {
		this.content = content;
	}
	
	/**
	 * This is the default html template
	 * Override this method to customize
	 * 
	 * @return
	 */
	public String toHTML(){
		StringBuilder html = new StringBuilder("<div class='")
				.append(cssClass)
				.append("'>");
		
		html.append("<span class='")
				.append(iconClass)
				.append("' > </span> ")
				.append(content)
				.append(" ");
		
		html.append("</div>");
		
		return html.toString();
	}
	
	public abstract void parseContent();

	/* * Getters and setters * */
	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getIconClass() {
		return iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
