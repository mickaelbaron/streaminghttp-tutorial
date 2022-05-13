package fr.mickaelbaron.spellwhatroyal.swingclient.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.function.Consumer;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.sse.InboundSseEvent;
import javax.ws.rs.sse.SseEventSource;

import fr.mickaelbaron.spellwhatroyal.api.NotYetImplementException;
import fr.mickaelbaron.spellwhatroyal.api.model.GameData;
import fr.mickaelbaron.spellwhatroyal.api.model.PlayerData;
import fr.mickaelbaron.spellwhatroyal.api.model.PlayerResultData;
import fr.mickaelbaron.spellwhatroyal.swingclient.model.GameModel;
import fr.mickaelbaron.spellwhatroyal.swingclient.ui.AuthenticationUI;
import fr.mickaelbaron.spellwhatroyal.swingclient.ui.GameUI;
import fr.mickaelbaron.spellwhatroyal.swingclient.ui.ResultUI;
import fr.mickaelbaron.spellwhatroyal.swingclient.ui.WaitingServerUI;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class SpellWhatRoyalController
		implements IAuthenticationController, IWaitingServerController, IGameController, IResultController {

	private AuthenticationUI refAuthenticationUI;

	private WaitingServerUI refWaitingServerUI;

	private GameUI refGameUI;

	private ResultUI refResultUI;

	private GameModel refModel;

	private Client clientRest;

//	private ClientManager clientWS;

	private Session currentWSSession;

	private SseEventSource build;

	private static final String HOST = "localhost";

	private static final int PORT = 8080;

	private static URI getRestURI() {
		return UriBuilder.fromUri("http://" + HOST).port(PORT).build();
	}

	public SpellWhatRoyalController() {
		refAuthenticationUI = new AuthenticationUI(this);
		refWaitingServerUI = new WaitingServerUI(this);
		refGameUI = new GameUI(this);
		refResultUI = new ResultUI(this);

		refModel = new GameModel();
		this.clientRest = ClientBuilder.newClient();
//		this.clientWS = ClientManager.createClient();

		this.showAuthenticationUI();
	}

	public static void main(String[] args) {
		new SpellWhatRoyalController();
	}

	@Override
	public void createPlayer(String value) {
		// Exercice 2: to complete 
	}

	@Override
	public void newValue(String value) {
		// Exercice 4: to complete
	}

	private void createSseEventSource() {	
		// Exercice 3: to complete
	}
	
	private Consumer<InboundSseEvent> onEvent = (InboundSseEvent inboundSseEvent) -> {
		// Exercice 3: to complete
	};

	private Consumer<Throwable> onError = (throwable) -> {
		// Exercice 3: to complete
	};

	private Runnable onComplete = () -> {
		// Exercice 3: to complete
	};

	private void createWebsocket() {
		// Exercice 4: to complete
	}
	
	private void start() {
		// Create SSE connection.
		this.createSseEventSource();

		// Create WS connection.
		this.createWebsocket();
		
		this.refGameUI.setName(this.refModel.getPlayerName());
	}

	private URL createURLFromString(String value) {
		URL newURL;
		try {
			newURL = new URL(value);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();

			throw new NotYetImplementException();
		}
		return newURL;
	}

	private void inGame(GameData readData) {
		// Exercice 4: to complete

		// Clean the next step.
		this.refResultUI.clean();
	}

	private void preGame(GameData readData) {
		// Exercice 3: to complete

		// Clean the next step.
		this.refGameUI.clean();
	}

	private void postGame(GameData readData) {
		URL gameDataURL = this.createURLFromString(readData.getUrl());

		this.refResultUI.setImage(gameDataURL);
		this.refResultUI.setSolution(readData.getValue());
		this.refResultUI.setScore(Integer.toString(refModel.getScore()));
		this.initPostGameForCurrentPlayer(readData.getValue(), readData.getResult());

		showResultUI();
	}

	private void initPostGameForCurrentPlayer(String solution, List<PlayerResultData> allPlayers) {
		String content = "";
		for (PlayerResultData playerResultData : allPlayers) {
			if (refModel.getToken().equals(playerResultData.getTokenId())) {
				this.refResultUI.setYourValue(playerResultData.getValue());
				this.refResultUI.setFound(solution.equalsIgnoreCase(playerResultData.getValue()) ? "Yes" : "No");
				this.refResultUI.setScore(Integer.toString(playerResultData.getScore()));
				this.refModel.setScore(playerResultData.getScore());
			}
			content += "Name: '" + playerResultData.getName() + "' Value: '"
					+ (playerResultData.getValue() != null ? playerResultData.getValue() : "No value") + "' Score: '"
					+ playerResultData.getScore() + "'\n";
		}
		this.refResultUI.setPlayers(content);
	}

	private void goToAuthentication() {
		if (currentWSSession != null) {
			try {
				currentWSSession.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		showAuthenticationUI();
	}

	private void showAuthenticationUI() {
		this.refAuthenticationUI.clean();
		refAuthenticationUI.setVisible(true);
		refWaitingServerUI.setVisible(false);
		refGameUI.setVisible(false);
		refResultUI.setVisible(false);
	}

	private void showWaitingServerUI() {
		refAuthenticationUI.setVisible(false);
		refWaitingServerUI.setVisible(true);
		refGameUI.setVisible(false);
		refResultUI.setVisible(false);
	}

	private void showGameUI() {
		refAuthenticationUI.setVisible(false);
		refWaitingServerUI.setVisible(false);
		refGameUI.setVisible(true);
		refResultUI.setVisible(false);
	}

	private void showResultUI() {
		refAuthenticationUI.setVisible(false);
		refWaitingServerUI.setVisible(false);
		refGameUI.setVisible(false);
		refResultUI.setVisible(true);
	}

	@Override
	public void close() {
		if (this.currentWSSession != null) {
			try {
				this.currentWSSession.close();
			} catch (IOException e) {
				e.printStackTrace();

				throw new NotYetImplementException();
			}
		}

		if (this.build != null) {
			this.build.close();
		}

		System.exit(0);
	}
}
