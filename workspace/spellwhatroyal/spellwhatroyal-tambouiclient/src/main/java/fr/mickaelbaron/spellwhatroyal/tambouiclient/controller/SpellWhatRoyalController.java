package fr.mickaelbaron.spellwhatroyal.tambouiclient.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.glassfish.tyrus.client.ClientManager;

import fr.mickaelbaron.spellwhatroyal.api.DataResultDecoder;
import fr.mickaelbaron.spellwhatroyal.api.NotYetImplementException;
import fr.mickaelbaron.spellwhatroyal.api.PlayerDataEncoderDecoder;
import fr.mickaelbaron.spellwhatroyal.api.model.AllPlayerDataResult;
import fr.mickaelbaron.spellwhatroyal.api.model.Credentials;
import fr.mickaelbaron.spellwhatroyal.api.model.CredentialsResult;
import fr.mickaelbaron.spellwhatroyal.api.model.DataResult;
import fr.mickaelbaron.spellwhatroyal.api.model.GameData;
import fr.mickaelbaron.spellwhatroyal.api.model.PlayerData;
import fr.mickaelbaron.spellwhatroyal.api.model.PlayerDataResult;
import fr.mickaelbaron.spellwhatroyal.api.model.PlayerResultData;
import fr.mickaelbaron.spellwhatroyal.tambouiclient.model.GameModel;
import fr.mickaelbaron.spellwhatroyal.tambouiclient.ui.SpellWhatRoyalUI;
import jakarta.websocket.ClientEndpointConfig;
import jakarta.websocket.Decoder;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import jakarta.websocket.Endpoint;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.sse.InboundSseEvent;
import jakarta.ws.rs.sse.SseEventSource;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class SpellWhatRoyalController
		implements IAuthenticationController, IWaitingServerController, IGameController, IResultController {

	private GameModel refModel;

	private Client clientRest;

	private ClientManager clientWS;

	private Session currentWSSession;

	private SseEventSource build;

	private static final String HOST = "localhost";

	private static final int PORT = 9080;

	private SpellWhatRoyalUI refSpellWhatRoyalUI;

	private static URI getRestURI() {
		return UriBuilder.fromUri("http://" + HOST).port(PORT).build();
	}

	public SpellWhatRoyalController() throws Exception {
		refModel = new GameModel();

		this.clientRest = ClientBuilder.newClient();
		this.clientWS = ClientManager.createClient();

		refSpellWhatRoyalUI = new SpellWhatRoyalUI(this);
	}

	public static void main(String[] args) throws Exception {
		new SpellWhatRoyalController();
	}

	@Override
	public void createPlayer(String value) {
		// Call REST web service.

		Credentials newPlayer = new Credentials();
		newPlayer.setUsername(value);

		try {
			Response post = clientRest.target(getRestURI()).path("authentication").request()
					.post(Entity.entity(newPlayer, MediaType.APPLICATION_JSON_TYPE));
			if (post.getStatus() == 200) {
				CredentialsResult readEntity = post.readEntity(CredentialsResult.class);

				refModel.setPlayerName(value);
				refModel.setToken(readEntity.getToken());

				this.start();
			} else {
				System.err.println("Problem to create player to the server.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Problem to connect to the server.");
		}
	}

	@Override
	public void newValue(String value) {
		// In
		try {
			// Post
			PlayerData newPlayerData = new PlayerData();
			newPlayerData.setToken(refModel.getToken());
			newPlayerData.setValue(value);
			currentWSSession.getBasicRemote().sendObject(newPlayerData);
		} catch (IOException e) {
			e.printStackTrace();

			throw new NotYetImplementException();
		} catch (EncodeException e) {
			e.printStackTrace();

			throw new NotYetImplementException();
		}
	}

	private void createSseEventSource() {
		WebTarget webTarget = clientRest.target(getRestURI()).path("game").path("timer");
		build = SseEventSource.target(webTarget).build();

		build.register(onEvent, onError, onComplete);
		build.open();
	}

	private Consumer<InboundSseEvent> onEvent = (InboundSseEvent inboundSseEvent) -> {
		GameData readData = inboundSseEvent.readData(GameData.class);

		switch (readData.getState()) {
			case PRE_GAME: {
				preGame(readData);

				break;
			}
			case IN_GAME: {
				inGame(readData);

				break;
			}
			case POST_GAME: {
				postGame(readData);

				break;
			}
		}
	};

	private Consumer<Throwable> onError = (throwable) -> {
		throwable.printStackTrace();
	};

	private Runnable onComplete = () -> {
		goToAuthentication();
	};

	private void createWebsocket() {
		// Create WS connection.
		final ClientEndpointConfig cec = ClientEndpointConfig.Builder.create()
				.encoders(Arrays.<Class<? extends Encoder>>asList(PlayerDataEncoderDecoder.class))
				.decoders(Arrays.<Class<? extends Decoder>>asList(DataResultDecoder.class)).build();
		try {
			URI uriBuild = UriBuilder.fromUri("ws://" + HOST + "/game/" + this.refModel.getToken()).port(PORT).build();

			currentWSSession = clientWS.connectToServer(new Endpoint() {
				@Override
				public void onOpen(Session session, EndpointConfig config) {
					session.addMessageHandler(new MessageHandler.Whole<DataResult>() {
						@Override
						public void onMessage(DataResult message) {
							if (message instanceof AllPlayerDataResult) {
								AllPlayerDataResult refDataResult = (AllPlayerDataResult) message;
								refSpellWhatRoyalUI.setGameUIOthers(
										refDataResult.getRightAnswers() + " / " + refDataResult.getPlayers());
							} else if (message instanceof PlayerDataResult) {
								PlayerDataResult refDataResult = (PlayerDataResult) message;
								refSpellWhatRoyalUI.setGameUICheck(refDataResult.getValid() ? "Yes" : "No");
							} else {
								throw new NotYetImplementException();
							}

						}
					});
				}

			}, cec, uriBuild);
		} catch (DeploymentException | IOException e) {
			e.printStackTrace();

			throw new NotYetImplementException();
		}

	}

	private void start() {
		// Create SSE connection.
		this.createSseEventSource();

		// Create WS connection.
		this.createWebsocket();

		this.refSpellWhatRoyalUI.setGameUIName(this.refModel.getPlayerName());
	}

	private URL createURLFromString(String value) {
		return this.getClass().getResource("/" + value);
	}

	private void inGame(GameData readData) {
		URL gameDataURL = this.createURLFromString(readData.getUrl());

		this.refSpellWhatRoyalUI.setGameUIImage(gameDataURL);
		this.refSpellWhatRoyalUI.setGameUITimer(Integer.toString(readData.getCounter()));
		this.refSpellWhatRoyalUI.setGameUIHelp(readData.getHelp());
		this.refSpellWhatRoyalUI.setGameUIScore(Integer.toString(refModel.getScore()));

		showGameUI();
	}

	private void preGame(GameData readData) {
		this.refSpellWhatRoyalUI.setWaitingUITimer(Integer.toString(readData.getCounter()));

		showWaitingServerUI();
	}

	private void postGame(GameData readData) {
		URL gameDataURL = this.createURLFromString(readData.getUrl());

		this.refSpellWhatRoyalUI.setResultUIImage(gameDataURL);
		this.refSpellWhatRoyalUI.setResultUISolution(readData.getValue());
		this.refSpellWhatRoyalUI.setResultUIScore(Integer.toString(refModel.getScore()));
		this.initPostGameForCurrentPlayer(readData.getValue(), readData.getResult());

		showResultUI();
	}

	private void initPostGameForCurrentPlayer(String solution, List<PlayerResultData> allPlayers) {
		this.refSpellWhatRoyalUI.setResultUIClean();

		for (PlayerResultData playerResultData : allPlayers) {
			if (refModel.getToken().equals(playerResultData.getTokenId())) {
				this.refSpellWhatRoyalUI.setResultUIYourValue(playerResultData.getValue());
				this.refSpellWhatRoyalUI
						.setResultUIFound(solution.equalsIgnoreCase(playerResultData.getValue()) ? "Yes" : "No");
				this.refSpellWhatRoyalUI.setResultUIScore(Integer.toString(playerResultData.getScore()));
				this.refModel.setScore(playerResultData.getScore());
			}

			this.refSpellWhatRoyalUI.addResultUIPlayers(playerResultData.getName(), playerResultData.getValue(),
					playerResultData.getScore());
		}
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
		this.refSpellWhatRoyalUI.showAuthenticationUI();
	}

	private void showWaitingServerUI() {
		this.refSpellWhatRoyalUI.showWaitingServerUI();
	
		// Clean the next step.
		this.refSpellWhatRoyalUI.setGameUIclean();
	}

	private void showGameUI() {
		this.refSpellWhatRoyalUI.showGameUI();

		// Clean the next step.
		this.refSpellWhatRoyalUI.setResultUIClean();
	}

	private void showResultUI() {
		this.refSpellWhatRoyalUI.showResultUI();
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
