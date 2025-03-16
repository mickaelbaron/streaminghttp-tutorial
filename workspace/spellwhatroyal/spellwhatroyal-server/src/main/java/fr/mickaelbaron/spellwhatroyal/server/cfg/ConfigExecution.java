package fr.mickaelbaron.spellwhatroyal.server.cfg;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
@ApplicationScoped
public class ConfigExecution implements IConfigExecution {

	//@Inject
	//@ConfigProperty(name = "spellwhatroyal.init.delay", defaultValue = "0")
	private Integer initDelay = 0;
	
	//@Inject
	//@ConfigProperty(name = "spellwhatroyal.pregame.delay", defaultValue = "3")
	private Integer preGameDelay = 3;
	
	//@Inject
	//@ConfigProperty(name = "spellwhatroyal.ingame.delay", defaultValue = "10")
	private Integer inGameDelay = 10;
	
	//@Inject
	//@ConfigProperty(name = "spellwhatroyal.postgame.delay", defaultValue = "4")
	private Integer postGameDelay = 4;
	
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
