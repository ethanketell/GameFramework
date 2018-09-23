package framework;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private World world = null;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(640,480));
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		Rectangle2D.Double myBounds = new Rectangle2D.Double(0, 0, this.getWidth(), this.getHeight());
		Rectangle2D.Double worldBounds = world.getViewPort();
		
		AffineTransform transform = new AffineTransform();
		transform.scale(myBounds.getWidth()/worldBounds.getWidth(), myBounds.getHeight()/worldBounds.getHeight());    
		transform.translate(-worldBounds.getMinX(), -worldBounds.getMinY());
		
		g2.setTransform(transform);
		
		world.paint(g2);
		world.paintEntities(g2);
		
		if(Framework.debug) {
			Point mouse = Input.getMousePosition();
			g2.setColor(Color.RED);
			g2.fillOval(mouse.x-1, mouse.y-1, 2, 2);
		}
	}

}
