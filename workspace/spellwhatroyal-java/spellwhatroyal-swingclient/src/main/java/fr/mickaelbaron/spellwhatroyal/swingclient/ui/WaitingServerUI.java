package fr.mickaelbaron.spellwhatroyal.swingclient.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import fr.mickaelbaron.spellwhatroyal.swingclient.controller.IWaitingServerController;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class WaitingServerUI extends JFrame {

	private static final long serialVersionUID = 9204289470069118133L;

	private JLabel logo;

	public WaitingServerUI(final IWaitingServerController controller) {
		super("Spell What Royal: Waiting for server");
		this.setLayout(new BorderLayout());

		logo = new JLabel();
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(logo);

		this.setPreferredSize(new Dimension(300, 300));
		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				controller.close();
			}
		});
	}

	public void setTimer(String value) {
		this.logo.setText("Waiting for server: " + value + " s");
	}
}
