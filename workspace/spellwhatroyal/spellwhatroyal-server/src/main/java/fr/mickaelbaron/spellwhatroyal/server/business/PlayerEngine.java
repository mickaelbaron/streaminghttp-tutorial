package fr.mickaelbaron.spellwhatroyal.server.business;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.ApplicationScoped;

import fr.mickaelbaron.spellwhatroyal.server.entity.PlayerGameData;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
@ApplicationScoped
public class PlayerEngine {

	private AtomicInteger refAtomicInteger = new AtomicInteger();

	// TokenID => Name
	private Map<String, PlayerGameData> allPlayersNameByTokenId = new HashMap<>();

	public String createPlayer(String name) {
		String incrementAndGet = Integer.toString(refAtomicInteger.incrementAndGet());
		PlayerGameData newPlayer = new PlayerGameData();
		newPlayer.setName(name);
		newPlayer.setTokenId(incrementAndGet);
		allPlayersNameByTokenId.put(incrementAndGet, newPlayer);
		return incrementAndGet;
	}

	public boolean updateSessionId(String token, String sessionId) {
		if (allPlayersNameByTokenId.containsKey(token)) {
			allPlayersNameByTokenId.get(token).setSessionId(sessionId);	
			return true;
		} else {
			return false;
		}
	}

	public void removePlayer(String sessionId) {
		allPlayersNameByTokenId.entrySet().removeIf(e -> e.getValue().getSessionId().equalsIgnoreCase(sessionId));
	}

	public void updatePlayerGameData(String token, String currentValue) {
		PlayerGameData playerGameData = allPlayersNameByTokenId.get(token);

		if (playerGameData != null) {
			playerGameData.setCurrentValue(currentValue);
		}
	}

	public void computeScore(String currentValue) {
		allPlayersNameByTokenId.forEach((k, v) -> {
			v.incrementScore(currentValue);
		});
	}

	public Collection<PlayerGameData> getAllPlayerGameData() {
		return allPlayersNameByTokenId.values();
	}

	public int getPlayerCount() {
		return allPlayersNameByTokenId.size();
	}

	public long getCurrentRightAnswer(String currentValue) {
		if (currentValue != null) {
			return allPlayersNameByTokenId.values().stream().filter(e -> e.getCurrentValue() != null)
					.filter(e -> e.getCurrentValue().equalsIgnoreCase(currentValue)).count();
		} else {
			return 0;
		}
	}
}
