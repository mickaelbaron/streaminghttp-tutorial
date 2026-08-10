package fr.mickaelbaron.spellwhatroyal.tambouiclient.ui;

import static dev.tamboui.toolkit.Toolkit.column;
import static dev.tamboui.toolkit.Toolkit.dock;
import static dev.tamboui.toolkit.Toolkit.panel;
import static dev.tamboui.toolkit.Toolkit.text;
import static dev.tamboui.toolkit.Toolkit.widget;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dev.tamboui.image.Image;
import dev.tamboui.image.ImageData;
import dev.tamboui.image.ImageScaling;
import dev.tamboui.image.protocol.HalfBlockProtocol;
import dev.tamboui.layout.Alignment;
import dev.tamboui.layout.Constraint;
import dev.tamboui.text.Line;
import dev.tamboui.text.Span;
import dev.tamboui.text.Text;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.widgets.block.Block;
import dev.tamboui.widgets.paragraph.Paragraph;
import fr.mickaelbaron.spellwhatroyal.tambouiclient.controller.IResultController;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class ResultUI {

	private ImageData imageData;

	private String yourValue = "";

	private String found = "";

	private String score = "";

	private String solution = "";

	private class PlayerResult {
		String name;
		String value;
		String score;
	}

	private List<PlayerResult> players = new ArrayList<>();

	public ResultUI(final IResultController controller) throws IOException {
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

		Text youText = Text.from(
				Line.from(Span.raw("Value: ").bold(), Span.raw(yourValue).yellow()),
				Line.from(Span.raw("Found: ").bold(), Span.raw(found).yellow()),
				Line.from(Span.raw("Score: ").bold(), Span.raw(score).yellow()));

		List<Element> playersText = new ArrayList<>();
		for (PlayerResult currentPlayerResult : players) {
			Text currentPlayerText = Text.from(
					Line.from(Span.raw("Name: ").bold(), Span.raw(currentPlayerResult.name).yellow(), Span.raw(" "),
							Span.raw("Value: ").bold(), Span.raw(currentPlayerResult.value).yellow(), Span.raw(" "),
							Span.raw("Score: ").bold(), Span.raw(currentPlayerResult.score).yellow()));
			var widget = widget(Paragraph.builder().text(currentPlayerText).build());
			playersText.add(widget);
		}

		return panel(" SpellWhatRoyal - Game ",
				dock().center(panel("Image spelled", widget(image)).titleAlignment(Alignment.CENTER))
						.rightWidth(Constraint.percentage(50))
						.right(column(panel("Solution", text(solution)), panel("You",
								widget(Paragraph.builder().text(youText).build()))
								.constraint(Constraint.length(5)),
								panel("Other", column(playersText.toArray(new Element[0]))).fill()))
						.fill());
	}

	public void setImage(ImageData value) {
		this.imageData = value;
	}

	public void setSolution(String value) {
		this.solution = value;
	}

	public void setYourValue(String value) {
		if (value == null) {
			this.yourValue = "";
		} else {
			this.yourValue = value;
		}
	}

	public void setFound(String value) {
		this.found = value;
	}

	public void setScore(String value) {
		this.score = value;
	}

	public void clean() {
		players.clear();
	}

	public void addPlayer(String pName, String pValue, Integer pScore) {
		PlayerResult newPlayerResult = new PlayerResult();
		newPlayerResult.name = pName != null ? pName : "";
		newPlayerResult.value = pValue != null ? pValue : "";
		newPlayerResult.score = Integer.toString(pScore);

		players.add(newPlayerResult);
	}
}
