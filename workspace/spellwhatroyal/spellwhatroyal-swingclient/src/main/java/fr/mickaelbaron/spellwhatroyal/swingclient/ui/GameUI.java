package fr.mickaelbaron.spellwhatroyal.swingclient.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import fr.mickaelbaron.spellwhatroyal.swingclient.controller.IGameController;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class GameUI extends JFrame {

	private static final long serialVersionUID = 8509127876363555637L;

	private JTextField name = new JTextField();

	private JTextField check = new JTextField();

	private JTextField score = new JTextField();

	private JTextField timer = new JTextField();

	private JTextField help = new JTextField();

	private JTextField others = new JTextField();

	private JTextField proposition = new JTextField();

	private ResizableImagePane panelCenter;

	public GameUI(final IGameController refController) {
		super("Spell What Royal: Game");
		this.setLayout(new BorderLayout());

		JPanel panelNorth = new JPanel();
		panelNorth.setLayout(new GridLayout(3, 2));
		panelNorth.add(createPanelInformation("Name", name));
		panelNorth.add(createPanelInformation("Found", check));
		panelNorth.add(createPanelInformation("Score", score));
		panelNorth.add(createPanelInformation("Timer", timer));
		panelNorth.add(createPanelInformation("Others", others));
		this.add(BorderLayout.NORTH, panelNorth);

		panelCenter = new ResizableImagePane();
		this.add(BorderLayout.CENTER, panelCenter);

		JPanel panelSouth = new JPanel();
		panelSouth.setLayout(new GridLayout(2, 1));
		JPanel helpPanel = new JPanel(new BorderLayout());
		helpPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Help"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		help.setEnabled(false);
		helpPanel.add(help);
		panelSouth.add(helpPanel);
		JPanel propositionPanel = new JPanel(new BorderLayout());
		propositionPanel.add(proposition);
		propositionPanel
				.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Give your proposition"),
						BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		panelSouth.add(propositionPanel);
		this.add(BorderLayout.SOUTH, panelSouth);

		this.setPreferredSize(new Dimension(500, 500));

		this.proposition.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				refController.newValue(proposition.getText());
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				refController.newValue(proposition.getText());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});

		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				refController.close();
			}
		});
	}

	public void setOthers(String v) {
		this.others.setText(v);
	}

	public void setName(String v) {
		this.name.setText(v);
	}

	public void setCheck(String v) {
		this.check.setText(v);
	}

	public void setScore(String v) {
		this.score.setText(v);
	}

	public void setTimer(String v) {
		this.timer.setText(v);
	}

	public void setHelp(String v) {
		this.help.setText(v);
	}

	public void setProposition(String v) {
		this.proposition.setText(v);
	}

	public void setImage(URL value) {
		this.panelCenter.setUrlImage(value);
	}

	public void clean() {
		this.proposition.setText("");
		this.check.setText("-");
		this.others.setText("-");
	}

	private JPanel createPanelInformation(String title, JTextField ref) {
		JPanel info = new JPanel(new BorderLayout());
		info.add(BorderLayout.WEST, new JLabel(title));
		info.add(BorderLayout.CENTER, ref);
		ref.setEnabled(false);

		return info;
	}
}
