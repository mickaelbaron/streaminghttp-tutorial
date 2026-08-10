package fr.mickaelbaron.spellwhatroyal.tambouiclient.ui;

import static dev.tamboui.toolkit.Toolkit.panel;
import static dev.tamboui.toolkit.Toolkit.stack;
import static dev.tamboui.toolkit.Toolkit.text;
import static dev.tamboui.toolkit.Toolkit.row;

import dev.tamboui.layout.ContentAlignment;
import dev.tamboui.toolkit.element.Element;
import fr.mickaelbaron.spellwhatroyal.tambouiclient.controller.IWaitingServerController;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class WaitingServerUI {

	private String timer;

	public WaitingServerUI(final IWaitingServerController controller) {
	}

	protected Element render() {
		return panel(" SpellWhatRoyal - Waiting for Server ", stack(row(
				text("Waiting for server: ").bold(), text(this.timer).yellow())).alignment(ContentAlignment.CENTER)
				.fill());
	}

	public void setTimer(String value) {
		this.timer = value;
	}
}
