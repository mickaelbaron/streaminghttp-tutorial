package fr.mickaelbaron.spellwhatroyal.swingclient.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import fr.mickaelbaron.spellwhatroyal.api.NotYetImplementException;

/**
 * @author Mickael BARON (baron.mickael@gmail.com)
 */
public class ResizableImagePane extends JPanel {

	private static final long serialVersionUID = 1964345884854433034L;

	private Image img;

	public void setUrlImage(URL urlContent) {
		if (urlContent == null) {
			throw new NotYetImplementException();
		}

		try {
			this.setImage(ImageIO.read(urlContent));
		} catch (IOException e) {
			e.printStackTrace();

			throw new NotYetImplementException();
		}
	}

	private void setImage(Image value) {
		if (img != value) {
			Image old = img;
			this.img = value;
			firePropertyChange("image", old, img);
			revalidate();
			repaint();
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(300, 300);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (img != null) {
			Graphics2D g2d = (Graphics2D) g.create();

			int width = getWidth();
			int height = getHeight();

			double scaleFactor = getScaleFactorToFit(new Dimension(img.getWidth(this), img.getHeight(this)), getSize());

			int x = (int) ((width - (img.getWidth(this) * scaleFactor)) / 2);
			int y = (int) ((height - (img.getHeight(this) * scaleFactor)) / 2);

			AffineTransform at = new AffineTransform();
			at.translate(x, y);
			at.scale(scaleFactor, scaleFactor);
			g2d.setTransform(at);
			g2d.drawImage(img, 0, 0, this);
			g2d.dispose();
		}
	}

	private double getScaleFactor(int iMasterSize, int iTargetSize) {
		return (double) iTargetSize / (double) iMasterSize;
	}

	private double getScaleFactorToFit(Dimension original, Dimension toFit) {
		double dScale = 1d;

		if (original != null && toFit != null) {

			double dScaleWidth = getScaleFactor(original.width, toFit.width);
			double dScaleHeight = getScaleFactor(original.height, toFit.height);

			dScale = Math.min(dScaleHeight, dScaleWidth);

		}
		return dScale;
	}
}