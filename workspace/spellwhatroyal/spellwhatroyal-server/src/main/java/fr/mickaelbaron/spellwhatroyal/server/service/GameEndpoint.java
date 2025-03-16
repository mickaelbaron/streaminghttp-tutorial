package fr.mickaelbaron.spellwhatroyal.server.service;

import java.io.IOException;

import fr.mickaelbaron.spellwhatroyal.api.AllPlayerDataResultEncoder;
import fr.mickaelbaron.spellwhatroyal.api.NotYetImplementException;
import fr.mickaelbaron.spellwhatroyal.api.PlayerDataEncoderDecoder;
import fr.mickaelbaron.spellwhatroyal.api.PlayerDataResultEncoder;
import fr.mickaelbaron.spellwhatroyal.api.model.AllPlayerDataResult;
import fr.mickaelbaron.spellwhatroyal.api.model.PlayerData;
import fr.mickaelbaron.spellwhatroyal.api.model.PlayerDataResult;
import fr.mickaelbaron.spellwhatroyal.server.business.GameEngine;
import fr.mickaelbaron.spellwhatroyal.server.business.PlayerEngine;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.websocket.EncodeException;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
@ApplicationScoped
@ServerEndpoint(value = "/game/{tokenid}", encoders = { PlayerDataResultEncoder.class,
		AllPlayerDataResultEncoder.class }, decoders = PlayerDataEncoderDecoder.class)
public class GameEndpoint {

	@Inject
	private GameEngine refGameEngine;

	@Inject
	private PlayerEngine refPlayerEngine;

	@OnOpen
	public void onOpen(Session session, @PathParam("tokenid") String tokenid) {
		this.refPlayerEngine.updateSessionId(tokenid, session.getId());
		session.setMaxIdleTimeout(0);
	}

	@OnMessage
	public void onMessage(PlayerData name, Session session) throws IOException {
		String currentValue = refGameEngine.getCurrentSpellWhatElement().getText();

		// Send to the current session.
		if (name != null && name.getValue() != null && !name.getValue().isEmpty()) {
			PlayerDataResult newPlayerData = new PlayerDataResult();
			boolean result = currentValue.equalsIgnoreCase(name.getValue());
			newPlayerData.setValid(result);

			refPlayerEngine.updatePlayerGameData(name.getToken(), name.getValue());
			try {
				session.getBasicRemote().sendObject(newPlayerData);
			} catch (EncodeException e) {
				e.printStackTrace();

				throw new NotYetImplementException();
			}
		}

		// Send all clients.
		long currentRightAnswer = this.refPlayerEngine.getCurrentRightAnswer(currentValue);
		long playerCount = this.refPlayerEngine.getPlayerCount();
		AllPlayerDataResult newAllPlayerData = new AllPlayerDataResult();
		newAllPlayerData.setPlayers(playerCount);
		newAllPlayerData.setRightAnswers(currentRightAnswer);

		for (Session current : session.getOpenSessions()) {
			try {
				current.getBasicRemote().sendObject(newAllPlayerData);
			} catch (EncodeException e) {
				e.printStackTrace();

				throw new NotYetImplementException();
			}
		}
	}

	@OnClose
	public void onClose(Session session) {
		refPlayerEngine.removePlayer(session.getId());
	}
}
