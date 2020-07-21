package fr.mickaelbaron.spellwhatroyal.api.model;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class PlayerResultData {

	private String name;

	private String tokenId;

	private String value;

	private Integer score;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	@Override
	public String toString() {
		return "PlayerResultData [name=" + name + ", tokenId=" + tokenId + ", value=" + value + ", score=" + score
				+ "]";
	}
}
