package fr.mickaelbaron.spellwhatroyal.server.service;

import fr.mickaelbaron.spellwhatroyal.api.model.Credentials;
import fr.mickaelbaron.spellwhatroyal.api.model.CredentialsResult;
import fr.mickaelbaron.spellwhatroyal.server.business.PlayerEngine;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
@Path("/authentication")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthenticationResource {

	@Inject
	PlayerEngine refPlayerEngine;
	
	@POST
	public CredentialsResult login(Credentials credentials) {
		if (credentials == null || credentials.getUsername() == null || credentials.getUsername().isEmpty()) {
			throw new WebApplicationException("Parameter is missing.", Status.BAD_REQUEST);
		}
		
		CredentialsResult newCredentialsResult = new CredentialsResult();
		newCredentialsResult.setToken(refPlayerEngine.createPlayer(credentials.getUsername()));
		return newCredentialsResult;
	}
}
