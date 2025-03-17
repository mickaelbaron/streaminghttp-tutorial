package fr.mickaelbaron.spellwhatroyal.server.cfg;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public interface IConfigExecution {

	int getInitDelay();
	
	int getPreGameDelay();
	
	int getInGameDelay();
	
	int getPostGameDelay();
	
	String displayAllConfigExecution();
}
