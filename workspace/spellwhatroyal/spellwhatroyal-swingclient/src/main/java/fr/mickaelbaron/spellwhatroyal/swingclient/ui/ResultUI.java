package fr.mickaelbaron.spellwhatroyal.swingclient.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import fr.mickaelbaron.spellwhatroyal.api.NotYetImplementException;
import fr.mickaelbaron.spellwhatroyal.swingclient.controller.IResultController;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class ResultUI extends JFrame {

	private static final long serialVersionUID = 1387065855569180290L;

	private JTextArea result;

	private ResizableImagePane image;

	private JTextField solution = new JTextField();

	private JTextField yourValue = new JTextField();

	private JTextField found = new JTextField();

	private JTextField score = new JTextField();

	public ResultUI(final IResultController controller) {
		super("Spell What Royal: Result");
		this.setLayout(new BorderLayout());

		JPanel panelNorth = new JPanel(new BorderLayout());
		panelNorth.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Solution"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		image = new ResizableImagePane();
		image.setMaximumSize(new Dimension(200, 200));
		solution = new JTextField();
		solution.setEditable(false);
		panelNorth.add(BorderLayout.CENTER, image);
		panelNorth.add(BorderLayout.SOUTH, createPanelInformation("Solution", solution));
		this.add(BorderLayout.NORTH, panelNorth);

		JPanel panelCenter = new JPanel(new BorderLayout());

		JPanel panelCenterNorth = new JPanel(new GridLayout(3, 1));
		panelCenterNorth.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("You"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		panelCenterNorth.add(createPanelInformation("Value", yourValue));
		panelCenterNorth.add(createPanelInformation("Found", found));
		panelCenterNorth.add(createPanelInformation("Score", score));
		panelCenter.add(BorderLayout.NORTH, panelCenterNorth);

		result = new JTextArea();
		result.setEditable(false);
		result.setRows(10);
		JScrollPane panelResult = new JScrollPane(result);
		panelResult.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Other Players"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		panelCenter.add(BorderLayout.CENTER, panelResult);

		this.add(BorderLayout.CENTER, panelCenter);

		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				controller.close();
			}
		});
	}

	public void setImage(URL value) {
		this.image.setUrlImage(value);
	}

	public void setSolution(String value) {
		this.solution.setText(value);
	}

	public void setYourValue(String value) {
		this.yourValue.setText(value);
	}

	public void setFound(String value) {
		this.found.setText(value);
	}

	public void setScore(String value) {
		this.score.setText(value);
	}

	public void setPlayers(String value) {
		this.result.setText(value);
	}

	private JPanel createPanelInformation(String title, JTextField ref) {
		JPanel info = new JPanel(new BorderLayout());
		info.add(BorderLayout.WEST, new JLabel(title));
		info.add(BorderLayout.CENTER, ref);
		ref.setEnabled(false);

		return info;
	}

	private static URL createURLFromString(String value) {
		URL newURL;
		try {
			newURL = new URL(value);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();

			throw new NotYetImplementException();
		}
		return newURL;
	}

	public void clean() {	
	}
}
