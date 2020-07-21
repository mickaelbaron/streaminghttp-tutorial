package fr.mickaelbaron.spellwhatroyal.api;

import java.io.IOException;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.mickaelbaron.spellwhatroyal.api.model.PlayerData;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class PlayerDataEncoderDecoder implements Decoder.Text<PlayerData>, Encoder.Text<PlayerData> {

	@Override
	public void init(EndpointConfig config) {
	}

	@Override
	public void destroy() {
	}

	@Override
	public PlayerData decode(String s) throws DecodeException {
		ObjectMapper mapper = new ObjectMapper();

		try {
			PlayerData readValue = mapper.readValue(s, PlayerData.class);
			return readValue;
		} catch (IOException e) {
			e.printStackTrace();

			throw new NotYetImplementException();
		}
	}

	@Override
	public boolean willDecode(String s) {
		return (s != null);
	}

	@Override
	public String encode(PlayerData object) throws EncodeException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();

			throw new NotYetImplementException();
		}
	}
}
