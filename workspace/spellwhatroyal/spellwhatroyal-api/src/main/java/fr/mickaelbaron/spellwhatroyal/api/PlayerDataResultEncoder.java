package fr.mickaelbaron.spellwhatroyal.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.mickaelbaron.spellwhatroyal.api.model.PlayerDataResult;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class PlayerDataResultEncoder implements Encoder.Text<PlayerDataResult>  {

	@Override
	public void init(EndpointConfig config) {
	}

	@Override
	public void destroy() {
	}

	@Override
	public String encode(PlayerDataResult object) throws EncodeException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			
			throw new NotYetImplementException();
		}
	}
}
