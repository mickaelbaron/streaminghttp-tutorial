package fr.mickaelbaron.spellwhatroyal.swingclient.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.mickaelbaron.spellwhatroyal.swingclient.controller.IAuthenticationController;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class AuthenticationUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 5108809230568156417L;

	private IAuthenticationController refController;

	private JTextField playerName;

	public AuthenticationUI(final IAuthenticationController pRefController) {
		super("Spell What Royal: Player Connection");
		this.refController = pRefController;

		this.setLayout(new BorderLayout(0, 20));

		ResizableImagePane resizableImagePane = new ResizableImagePane();
		URL resource = getClass().getResource("/logo.jpg");
		resizableImagePane.setUrlImage(resource);
		this.add(BorderLayout.CENTER, resizableImagePane);

		JPanel panelSouth = new JPanel(new BorderLayout());
		panelSouth.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Give a player name"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		playerName = new JTextField(10);
		panelSouth.add(BorderLayout.CENTER, playerName);
		playerName.addActionListener(this);

		JButton playerCreate = new JButton("Go");
		playerCreate.addActionListener(this);

		panelSouth.add(BorderLayout.EAST, playerCreate);
		this.add(BorderLayout.SOUTH, panelSouth);

		this.setPreferredSize(new Dimension(400, 400));

		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void clean() {
		this.playerName.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		refController.createPlayer(playerName.getText());
	}
}
