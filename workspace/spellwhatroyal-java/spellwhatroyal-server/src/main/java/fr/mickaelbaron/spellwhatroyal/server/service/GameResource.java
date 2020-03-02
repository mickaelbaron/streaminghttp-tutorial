package fr.mickaelbaron.spellwhatroyal.server.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;

import fr.mickaelbaron.spellwhatroyal.api.model.GameData;
import fr.mickaelbaron.spellwhatroyal.api.model.GameState;
import fr.mickaelbaron.spellwhatroyal.api.model.PlayerResultData;
import fr.mickaelbaron.spellwhatroyal.server.business.GameEngine;
import fr.mickaelbaron.spellwhatroyal.server.business.PlayerEngine;
import fr.mickaelbaron.spellwhatroyal.server.entity.PlayerGameData;
import fr.mickaelbaron.spellwhatroyal.server.entity.SpellWhatElement;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
@Path("/game")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GameResource {

	@Inject
	GameEngine refGameEngine;

	@Inject
	PlayerEngine refPlayerEngine;
	
	@Path("/timer")
	@GET
	@Produces(MediaType.SERVER_SENT_EVENTS)
	public void getTimer(@Context SseEventSink eventSink, @Context Sse sse) {
		new Thread(() -> {
			while (true) {
				// Global data.
				GameData newGameData = new GameData();
				newGameData.setCounter(refGameEngine.getCounter());
				newGameData.setState(refGameEngine.getState());
				SpellWhatElement currentSpellWhatElement = refGameEngine.getCurrentSpellWhatElement();
				
				if (refGameEngine.getState() != GameState.PRE_GAME) {
					newGameData.setUri(currentSpellWhatElement.getUri());					
				}
				
				// Ingame data.
				if (refGameEngine.getState() == GameState.IN_GAME) {
					newGameData.setHelp(currentSpellWhatElement.getHelp());
				}				
				
				// Postgame data.
				if (refGameEngine.getState() == GameState.POST_GAME) {
					newGameData.setValue(refGameEngine.getCurrentSpellWhatElement().getText());
					List<PlayerResultData> players = new ArrayList<>();
					Collection<PlayerGameData> allPlayerGameData = this.refPlayerEngine.getAllPlayerGameData();
					for (PlayerGameData playerGameData : allPlayerGameData) {
						PlayerResultData newPlayerResultData = new PlayerResultData();
						newPlayerResultData.setName(playerGameData.getName());
						newPlayerResultData.setScore(playerGameData.getScore());
						newPlayerResultData.setValue(playerGameData.getPreviousValue());
						newPlayerResultData.setTokenId(playerGameData.getTokenId());
						players.add(newPlayerResultData);
					}
					newGameData.setResult(players);
				}
				
				OutboundSseEvent event = sse.newEventBuilder().name("update-timer")
						.mediaType(MediaType.APPLICATION_JSON_TYPE).id("1").data(GameData.class, newGameData)
						.reconnectDelay(1000).build();
				if (!eventSink.isClosed()) {
					eventSink.send(event);
				} else {
					return;
				}

				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
