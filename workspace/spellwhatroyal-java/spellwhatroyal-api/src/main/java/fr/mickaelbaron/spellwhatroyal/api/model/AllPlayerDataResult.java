package fr.mickaelbaron.spellwhatroyal.api.model;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class AllPlayerDataResult implements DataResult {

	private Long players;
	
	private Long rightAnswers;

	public Long getPlayers() {
		return players;
	}

	public void setPlayers(Long players) {
		this.players = players;
	}

	public Long getRightAnswers() {
		return rightAnswers;
	}

	public void setRightAnswers(Long rightAnswers) {
		this.rightAnswers = rightAnswers;
	}
	
}
