package fr.mickaelbaron.spellwhatroyal.server.service;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import fr.mickaelbaron.spellwhatroyal.api.model.Credentials;
import fr.mickaelbaron.spellwhatroyal.api.model.CredentialsResult;
import fr.mickaelbaron.spellwhatroyal.server.business.PlayerEngine;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
@Path("/authentication")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthenticationResource {

	@Inject
	private PlayerEngine refPlayerEngine;
	
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
