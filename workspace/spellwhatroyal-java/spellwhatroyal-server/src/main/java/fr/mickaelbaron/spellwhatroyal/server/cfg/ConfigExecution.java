package fr.mickaelbaron.spellwhatroyal.server.cfg;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
@ApplicationScoped
public class ConfigExecution implements IConfigExecution {

	@Inject
	@ConfigProperty(name = "spellwhatroyal.init.delay", defaultValue = "0")
	private Integer initDelay;
	
	@Inject
	@ConfigProperty(name = "spellwhatroyal.pregame.delay", defaultValue = "3")
	private Integer preGameDelay;
	
	@Inject
	@ConfigProperty(name = "spellwhatroyal.ingame.delay", defaultValue = "10")
	private Integer inGameDelay;
	
	@Inject
	@ConfigProperty(name = "spellwhatroyal.postgame.delay", defaultValue = "4")
	private Integer postGameDelay;
	
	@Override
	public Integer getInitDelay() {
		return this.initDelay;
	}

	@Override
	public Integer getPreGameDelay() {
		return this.preGameDelay;
	}

	@Override
	public Integer getInGameDelay() {
		return this.inGameDelay;
	}

	@Override
	public Integer getPostGameDelay() {
		return this.postGameDelay;
	}
	
	@PostConstruct
	public void init() {
		System.out.println(displayAllConfigExecution());
	}

	@Override
	public String displayAllConfigExecution() {
		return "ConfigExecution [spellwhatroyal.init.delay=" + initDelay + ", spellwhatroyal.pregame.delay="
				+ preGameDelay + ", spellwhatroyal.ingame.delay=" + inGameDelay
				+ ", spellwhatroyal.postgame.delay=" + postGameDelay + "]";
	}
}
