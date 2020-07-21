package fr.mickaelbaron.spellwhatroyal.server.entity;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class SpellWhatElement {

	private String text;
	
	private String uri;
	
	private String help;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
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
