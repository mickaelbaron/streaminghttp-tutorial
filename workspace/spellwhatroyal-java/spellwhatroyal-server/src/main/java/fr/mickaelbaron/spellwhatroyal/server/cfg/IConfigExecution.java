package fr.mickaelbaron.spellwhatroyal.server.cfg;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public interface IConfigExecution {

	Integer getInitDelay();
	
	Integer getPreGameDelay();
	
	Integer getInGameDelay();
	
	Integer getPostGameDelay();
	
	String displayAllConfigExecution();
}
