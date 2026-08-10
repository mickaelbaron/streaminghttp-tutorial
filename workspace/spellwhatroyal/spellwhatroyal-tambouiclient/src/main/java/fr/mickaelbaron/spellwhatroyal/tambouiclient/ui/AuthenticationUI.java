package fr.mickaelbaron.spellwhatroyal.tambouiclient.ui;

import static dev.tamboui.toolkit.Toolkit.dock;
import static dev.tamboui.toolkit.Toolkit.widget;
import static dev.tamboui.toolkit.Toolkit.textInput;
import static dev.tamboui.toolkit.Toolkit.panel;

import java.io.IOException;

import dev.tamboui.image.Image;
import dev.tamboui.image.ImageData;
import dev.tamboui.image.ImageScaling;
import dev.tamboui.image.protocol.HalfBlockProtocol;
import dev.tamboui.style.Style;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.toolkit.event.EventResult;
import dev.tamboui.widgets.block.Block;
import dev.tamboui.widgets.input.TextInputState;
import fr.mickaelbaron.spellwhatroyal.tambouiclient.controller.IAuthenticationController;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class AuthenticationUI {

	private final ImageData imageData;

	private final TextInputState nameInput = new TextInputState();

	private IAuthenticationController refController;

	public AuthenticationUI(final IAuthenticationController pRefController) throws IOException {
		this.refController = pRefController;

		this.imageData = ImageData.fromResource("/logo.jpg");
	}

	protected Element render() {
		var image = Image.builder()
				.data(imageData)
				.scaling(ImageScaling.FIT)
				.protocol(new HalfBlockProtocol())
				.block(Block.builder()
						.padding(1)
						.build())
				.build();

		return panel(" SpellWhatRoyal - Player Connection ", dock().center(widget(image)).bottom(
				textInput(nameInput)
						.id("playername")
						.title("Give a player name")
						.cursorStyle(Style.EMPTY.reversed())
						.length(3)
						.onKeyEvent(event -> {
							if (event.isConfirm()) { // Entrée
								refController.createPlayer(nameInput.text());
								return EventResult.HANDLED;
							}
							return EventResult.UNHANDLED;
						})

		).fill());
	}

	public void clean() {
		this.nameInput.clear();
	}
}
