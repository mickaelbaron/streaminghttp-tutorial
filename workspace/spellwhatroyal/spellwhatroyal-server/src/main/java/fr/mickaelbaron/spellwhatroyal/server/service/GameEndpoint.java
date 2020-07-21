package fr.mickaelbaron.spellwhatroyal.server.service;

import java.io.IOException;

import javax.inject.Inject;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import fr.mickaelbaron.spellwhatroyal.api.AllPlayerDataResultEncoder;
import fr.mickaelbaron.spellwhatroyal.api.NotYetImplementException;
import fr.mickaelbaron.spellwhatroyal.api.PlayerDataEncoderDecoder;
import fr.mickaelbaron.spellwhatroyal.api.PlayerDataResultEncoder;
import fr.mickaelbaron.spellwhatroyal.api.model.AllPlayerDataResult;
import fr.mickaelbaron.spellwhatroyal.api.model.PlayerData;
import fr.mickaelbaron.spellwhatroyal.api.model.PlayerDataResult;
import fr.mickaelbaron.spellwhatroyal.server.business.GameEngine;
import fr.mickaelbaron.spellwhatroyal.server.business.PlayerEngine;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
@ServerEndpoint(value = "/game/{tokenid}", encoders = { PlayerDataResultEncoder.class, AllPlayerDataResultEncoder.class}, decoders = PlayerDataEncoderDecoder.class)
public class GameEndpoint {

	@Inject
	GameEngine refGameEngine;

	@Inject
	PlayerEngine refPlayerEngine;
	
	@OnOpen
	public void onOpen(Session session, @PathParam("tokenid") String tokenid) {
		boolean updateSessionId = this.refPlayerEngine.updateSessionId(tokenid, session.getId());
		if (updateSessionId) {
			session.setMaxIdleTimeout(0);			
		} else {
			if (!"debug".equalsIgnoreCase(tokenid)) {
				try {
					session.close();
				} catch (IOException e) {
					throw new NotYetImplementException();
				}				
			}
		}
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

