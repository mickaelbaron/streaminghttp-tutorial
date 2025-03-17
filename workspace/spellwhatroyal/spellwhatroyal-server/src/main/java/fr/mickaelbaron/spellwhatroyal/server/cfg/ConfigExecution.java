package fr.mickaelbaron.spellwhatroyal.server.cfg;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
@ApplicationScoped
public class ConfigExecution implements IConfigExecution {

	@Inject
	@ConfigProperty(name = "spellwhatroyal.init.delay", defaultValue = "0")
	private int initDelay = 0;
	
	@Inject
	@ConfigProperty(name = "spellwhatroyal.pregame.delay", defaultValue = "3")
	private int preGameDelay = 3;
	
	@Inject
	@ConfigProperty(name = "spellwhatroyal.ingame.delay", defaultValue = "10")
	private int inGameDelay = 10;
	
	@Inject
	@ConfigProperty(name = "spellwhatroyal.postgame.delay", defaultValue = "4")
	private int postGameDelay = 4;
	
	@Override
	public int getInitDelay() {
		return this.initDelay;
	}

	@Override
	public int getPreGameDelay() {
		return this.preGameDelay;
	}

	@Override
	public int getInGameDelay() {
		return this.inGameDelay;
	}

	@Override
	public int getPostGameDelay() {
		return this.postGameDelay;
	}
	
	@PostConstruct
	public void init() {
		System.out.println("OULAs");
		System.out.println(displayAllConfigExecution());
	}

	@Override
	public String displayAllConfigExecution() {
		return "ConfigExecution [spellwhatroyal.init.delay=" + initDelay + ", spellwhatroyal.pregame.delay="
				+ preGameDelay + ", spellwhatroyal.ingame.delay=" + inGameDelay
				+ ", spellwhatroyal.postgame.delay=" + postGameDelay + "]";
	}
}
