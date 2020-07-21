package fr.mickaelbaron.spellwhatroyal.swingclient.model;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class GameModel {

	private String playerName;

	private String token;

	private int score = 0;

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
