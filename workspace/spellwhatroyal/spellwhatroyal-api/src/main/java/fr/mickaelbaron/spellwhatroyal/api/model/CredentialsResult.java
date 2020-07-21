package fr.mickaelbaron.spellwhatroyal.api.model;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class CredentialsResult {

	protected String username;

	protected String token;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
