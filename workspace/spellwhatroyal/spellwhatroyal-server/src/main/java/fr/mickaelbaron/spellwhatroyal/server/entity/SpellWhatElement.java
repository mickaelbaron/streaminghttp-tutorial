package fr.mickaelbaron.spellwhatroyal.server.entity;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class SpellWhatElement {

	private String text;
	
	private String url;
	
	private String help;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getHelp() {
		return help;
	}

	public void setHelp(String help) {
		this.help = help;
	}
}
