package imageEditor;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	BufferedImage activeImage;
	
	static BufferedImage texBase = new BufferedImage(10,10,BufferedImage.TYPE_INT_ARGB);
	static {
		Graphics g = texBase.getGraphics();
		g.setColor(new Color(0xAAAAAA));
		g.fillRect(0, 0, 10, 10);
		g.setColor(new Color(0xCCCCCC));
		g.fillRect(0, 0, 5, 5);
		g.fillRect(5, 5, 10, 10);
		g.dispose();
	}
	
	public ImagePanel() {
		super();
		this.setPreferredSize(new Dimension(0,0));
		this.setMinimumSize(new Dimension(0,0));
	}
	
	public void setActiveImage(BufferedImage newImage) {
		activeImage = newImage;
		if(newImage != null) {
			this.setPreferredSize(new Dimension(newImage.getWidth(),newImage.getHeight()));
		} else {
			this.setPreferredSize(new Dimension(0,0));
		}
		this.setMinimumSize(this.getPreferredSize());
		this.repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		TexturePaint tex = new TexturePaint(texBase, new Rectangle2D.Double(0,0,10,10));
		g2.setPaint(tex);
		g2.fillRect(0, 0, getWidth(), getHeight());
		
		AffineTransform oldAT = g2.getTransform();
		
		g2.translate(this.getWidth()/2, this.getHeight()/2);
		
		if(activeImage != null) {
			g2.drawImage(
					activeImage,
					-activeImage.getWidth()/2,
					-activeImage.getHeight()/2,
					null);
		}
		
		g2.setTransform(oldAT);
	}
	
}
