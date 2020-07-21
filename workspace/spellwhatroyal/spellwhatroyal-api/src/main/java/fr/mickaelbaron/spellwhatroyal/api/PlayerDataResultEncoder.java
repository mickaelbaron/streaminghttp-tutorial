package fr.mickaelbaron.spellwhatroyal.api;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.mickaelbaron.spellwhatroyal.api.model.PlayerDataResult;

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
