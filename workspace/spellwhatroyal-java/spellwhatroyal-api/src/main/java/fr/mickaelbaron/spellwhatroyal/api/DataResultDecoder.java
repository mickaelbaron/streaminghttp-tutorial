package fr.mickaelbaron.spellwhatroyal.api;

import java.io.IOException;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.mickaelbaron.spellwhatroyal.api.model.AllPlayerDataResult;
import fr.mickaelbaron.spellwhatroyal.api.model.DataResult;
import fr.mickaelbaron.spellwhatroyal.api.model.PlayerDataResult;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class DataResultDecoder implements Decoder.Text<DataResult> {

	@Override
	public void init(EndpointConfig config) {
	}

	@Override
	public void destroy() {
	}

	@Override
	public DataResult decode(String s) throws DecodeException {
		ObjectMapper mapper = new ObjectMapper();

		try {
			if (s.contains("valid")) {
				PlayerDataResult readValue = mapper.readValue(s, PlayerDataResult.class);
				return readValue;
			} else {
				AllPlayerDataResult readValue = mapper.readValue(s, AllPlayerDataResult.class);
				return readValue;
			}
		} catch (IOException e) {
			e.printStackTrace();

			throw new NotYetImplementException();
		}
	}

	@Override
	public boolean willDecode(String s) {
		return (s != null);
	}
}
