package fr.mickaelbaron.spellwhatroyal.server.entity;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class PlayerGameData {

	private String name;
	
	private String tokenId;
	
	private String currentValue;
	
	private String previousValue;
	
	private Integer score = 0;

	private String sessionId;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
	public void incrementScore(String trueValue) {
		if (currentValue != null && trueValue != null) {
			if (currentValue.equalsIgnoreCase(trueValue)) {
				score++;
			}
		}

		previousValue = currentValue;
		currentValue = null;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getPreviousValue() {
		return previousValue;
	}

	public void setPreviousValue(String previousValue) {
		this.previousValue = previousValue;
	}
}
