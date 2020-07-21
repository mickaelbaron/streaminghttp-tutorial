package fr.mickaelbaron.spellwhatroyal.api.model;

import java.util.List;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class GameData {

	private Integer counter;

	private GameState state;

	private String uri;

	private String help;

	private String value;

	private List<PlayerResultData> result;

	public Integer getCounter() {
		return counter;
	}

	public void setCounter(Integer counter) {
		this.counter = counter;
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getHelp() {
		return help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<PlayerResultData> getResult() {
		return result;
	}

	public void setResult(List<PlayerResultData> result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "GameData [counter=" + counter + ", state=" + state + ", uri=" + uri + ", help=" + help + ", value="
				+ value + ", result=" + result + "]";
	}
}
