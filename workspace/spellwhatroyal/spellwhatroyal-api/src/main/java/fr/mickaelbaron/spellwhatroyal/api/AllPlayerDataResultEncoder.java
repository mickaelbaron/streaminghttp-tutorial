package fr.mickaelbaron.spellwhatroyal.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.mickaelbaron.spellwhatroyal.api.model.AllPlayerDataResult;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class AllPlayerDataResultEncoder
		implements Encoder.Text<AllPlayerDataResult> {

	@Override
	public void init(EndpointConfig config) {
	}

	@Override
	public void destroy() {
	}

	@Override
	public String encode(AllPlayerDataResult object) throws EncodeException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();

			throw new NotYetImplementException();
		}
	}
}
