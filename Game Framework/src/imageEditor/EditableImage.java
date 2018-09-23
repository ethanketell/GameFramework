package imageEditor;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import framework.Images;

public class EditableImage {
	
	BufferedImage image;
	double rotation, sx, sy, x, y;
	
	public EditableImage(BufferedImage image) {
		this.image = image;
	}
	
	public void setRotation(double rotation) {
		this.rotation = rotation;
	}
	
	public void setScale(double sx, double sy) {
		this.sx = sx;
		this.sy = sy;
	}
	
	public void setPos(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void paint(Graphics g) {
		BufferedImage transformed = Images.transformImage(image, rotation, sx, sy);
		g.drawImage(transformed, (int)(x-transformed.getWidth()/2.0), (int)(y-transformed.getHeight()/2.0), null);
	}
}
