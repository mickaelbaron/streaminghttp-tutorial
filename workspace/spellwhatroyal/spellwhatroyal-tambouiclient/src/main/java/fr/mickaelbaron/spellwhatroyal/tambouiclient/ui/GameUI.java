package fr.mickaelbaron.spellwhatroyal.tambouiclient.ui;

import static dev.tamboui.toolkit.Toolkit.dock;
import static dev.tamboui.toolkit.Toolkit.panel;
import static dev.tamboui.toolkit.Toolkit.spacer;
import static dev.tamboui.toolkit.Toolkit.text;
import static dev.tamboui.toolkit.Toolkit.textInput;
import static dev.tamboui.toolkit.Toolkit.widget;

import java.io.IOException;

import dev.tamboui.image.Image;
import dev.tamboui.image.ImageData;
import dev.tamboui.image.ImageScaling;
import dev.tamboui.image.protocol.HalfBlockProtocol;
import dev.tamboui.layout.Alignment;
import dev.tamboui.layout.Constraint;
import dev.tamboui.style.Style;
import dev.tamboui.text.Line;
import dev.tamboui.text.Span;
import dev.tamboui.text.Text;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.widgets.block.Block;
import dev.tamboui.widgets.block.BorderType;
import dev.tamboui.widgets.input.TextInputState;
import dev.tamboui.widgets.paragraph.Paragraph;
import fr.mickaelbaron.spellwhatroyal.tambouiclient.controller.IGameController;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class GameUI {

	private String help = "";

	private String name = "";

	private String check = "";

	private String score = "";

	private String timer = "";

	private String others = "";

	private final TextInputState answerInput = new TextInputState();

	private ImageData imageData;

	private IGameController refController;

	private String lastSentAnswer = "";

	public GameUI(final IGameController pRefController) throws IOException {
		this.refController = pRefController;
	}

	public Element render() {
		var image = Image.builder()
				.data(imageData)
				.scaling(ImageScaling.FIT)
				.protocol(new HalfBlockProtocol())
				.block(Block.builder()
						.padding(1)
						.build())
				.build();

		Text infoText = Text.from(
				Line.from(Span.raw("Name: ").bold(), Span.raw(name).yellow()),
				Line.empty(),
				Line.from(Span.raw("Found: ").bold(), Span.raw(check).yellow()),
				Line.empty(),
				Line.from(Span.raw("Score: ").bold(), Span.raw(score).yellow()),
				Line.empty(),
				Line.from(Span.raw("Timer: ").bold(), Span.raw(timer).yellow()),
				Line.empty(),
				Line.from(Span.raw("Others: ").bold(), Span.raw(others).yellow()));

		String currentAnswer = answerInput.text();
		if (!currentAnswer.equals(lastSentAnswer)) {
			refController.newValue(currentAnswer);
			lastSentAnswer = currentAnswer;
		}

		return panel(" SpellWhatRoyal - Game ",
				dock().center(panel("Image to Spell", widget(image)).titleAlignment(Alignment.CENTER))
						.right(panel("Information",
								widget(Paragraph.builder().text(infoText).build())))
						.rightWidth(Constraint.percentage(20))
						.bottom(panel(panel("Help", text(this.help)), spacer(),
								textInput(answerInput)
										.id("answerinput")
										.title("Give your proposition")
										.cursorStyle(Style.EMPTY.reversed())
										.length(3))
								.borderType(BorderType.NONE),
								null)
						.fill());
	}

	public void setOthers(String value) {
		this.others = value;
	}

	public void setName(String value) {
		this.name = value;
	}

	public void setCheck(String value) {
		this.check = value;
	}

	public void setScore(String value) {
		this.score = value;
	}

	public void setTimer(String value) {
		this.timer = value;
	}

	public void setHelp(String value) {
		this.help = value;
	}

	public void setProposition(String v) {
	}

	public void setImage(ImageData value) {
		this.imageData = value;
	}

	public void clean() {
		this.check = "";
		this.others = "";
		this.answerInput.clear();
	}
}
