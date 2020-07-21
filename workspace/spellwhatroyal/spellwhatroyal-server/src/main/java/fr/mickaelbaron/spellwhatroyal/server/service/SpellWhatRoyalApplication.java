package fr.mickaelbaron.spellwhatroyal.server.service;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
@ApplicationPath("/")
public class SpellWhatRoyalApplication extends javax.ws.rs.core.Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> s = new HashSet<Class<?>>();
		s.add(GameResource.class);
		s.add(AuthenticationResource.class);
		return s;
	}
}
