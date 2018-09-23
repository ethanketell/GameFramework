package framework;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * This is the base class for all objects which are in a game. If you want something to exist
 * in the game world, it should extend this class.
 * @author Ethan Ketell
 *
 */
public abstract class Entity {
	/**
	 * The {@code x} coordinate, or horizontal position, of this Entity within it's parent {@link World}
	 * <br> Lower values are closer to the left side of the World, higher are toward the right.
	 *@see {@link Entity#y}
	 */
	public double x;
	/**
	 * The {@code y} coordinate, or vertical position, of this Entity within it's parent {@link World}
	 * <br> Lower values are closer to the top of the World, higher are toward the bottom.
	 * @see {@link Entity#x}
	 */
	public double y;
	/**
	 * This Entity's orientation in degrees, with zero being East, increasing counter-clockwise
	 * @see {@link World#EAST}
	 * <br> {@link World#NORTHEAST}
	 * <br> {@link World#NORTH}
	 * <br> {@link World#NORTHWEST}
	 * <br> {@link World#WEST}
	 * <br> {@link World#SOUTHWEST}
	 * <br> {@link World#SOUTH}
	 * <br> {@link World#SOUTHEAST}
	 */
	public double direction = World.NORTH;
	
	/**
	 * This Entity's image, used to draw to the {@link World}
	 */
	protected BufferedImage sprite;
	
	/**
	 * The parent world. This value is automatically assigned upon being added to the world,
	 * and allows the Entity to interact with other members of the same world. Use 
	 * {@link Entity#getParentWorld} to access this variable.
	 */
	private World world;
	
	public Entity() {
		this.x = 0;
		this.y = 0;
		this.direction = World.NORTH;
		sprite = Images.missingImage;
	}
	
	/**
	 * Assign a new {@link BufferedImage image} to the {@link Entity}
	 * @param sprite the new image
	 */
	protected void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}
	
	/**
	 * Returns the sprite, rotated to point in the direction given by this Entity's
	 * {@link Entity#direction rotation} value.
	 */
	protected BufferedImage getSprite() {
		if(Framework.debug) {
			Graphics g = sprite.getGraphics();
			g.setColor(Color.RED);
			g.drawRect(0, 0, sprite.getWidth()-1, sprite.getHeight()-1);
		}
		BufferedImage image = Images.rotateImage(sprite, direction);
		if(Framework.debug) {
			Graphics g = image.getGraphics();
			g.setColor(Color.GREEN);
			g.drawRect(0, 0, image.getWidth()-1, image.getHeight()-1);
			g.dispose();
		}
		return image;
	}
	
	/**
	 * Paints the {@link Entity} to the {@link Graphics2D} context provided. The graphics will have
	 * a transform applied to align drawn Entities to the world, so using the world-relative
	 * coordinates and size should work perfectly well.
	 * <br><br> This method can be Overridden to change how the Entity draws, but generally it
	 * should be easier to assign an image. If Overridden, no permanent changes should be made to
	 * the Graphics2D, such as by the {@link Graphics#setClip(java.awt.Shape) setClip} method.
	 * @param g The transformed Graphics2D belonging to the {@link World}
	 */
	public void paint(Graphics2D g) {
		BufferedImage rotImage = getSprite();
		g.drawImage(rotImage, (int)(x-rotImage.getWidth()/2), (int)(y-rotImage.getHeight()/2),null);
		if(Framework.debug) {
			g.setColor(Color.BLUE);
			g.setStroke(new BasicStroke(2f));
			double radians = Math.toRadians(direction);
			g.drawLine((int)this.x, (int)this.y, (int)(this.x+50*Math.cos(radians)), (int)(this.y+50*Math.sin(radians)));
			g.setColor(Color.RED);
			g.fillOval((int)x-2, (int)y-2, 4, 4);
		}
	}
	
	/**
	 * Returns whether or not the sprite is visible, calculated using the Entity's parent
	 * {@link World World's} {@link World#getViewPort() ViewPort}
	 * @return whether or not ({@code true} or {@code false}) this Entity is within the
	 * visible rectangle of it's parent world.
	 */
	public final boolean isVisible() {
		Rectangle2D.Double bounds = new Rectangle2D.Double(x-(sprite.getWidth()/2.0), y-(sprite.getHeight()/2.0), sprite.getWidth(), sprite.getHeight());
		Rectangle2D.Double viewPort = world.getViewPort();
		return bounds.intersects(viewPort);
	}
	
	/**
	 * Gives access to the {@link World} this Entity exist within.
	 * @return the World this Entity exist within
	 */
	public final World getParentWorld() {
		return world;
	}
	
	/**
	 * Assigns the parent {@link World} to the Entity. Should generally not be called
	 * directly, but left to be called when {@link World#add(Entity)} is called.
	 * @param world The new parent world
	 */
	public final void setParentWorld(World world) {
		this.world = world;
	}
	
	public double getDirectionToward(Entity other) {
		return this.getDirectionToward(other.x, other.y);
	}
	
	public double getDirectionToward(Point p) {
		return this.getDirectionToward(p.x, p.y);
	}
	
	public double getDirectionToward(double x, double y) {
		return Math.toDegrees(Math.atan2(y-this.y, x-this.x));
	}
	
	/**
	 * This method is called once per frame, and should handle all the necessary logic to
	 * allow this Entity to function in the game. Implementation is completely left up to
	 * the subclass. For static objects, such as terrain decoration, this function can be
	 * left blank. For the sake of optimization, games with many active Entities should
	 * consider checking if the Entity is on screen using the {@link Entity#isVisible() isVisible}
	 * method to determine whether or not to perform tick functions.
	 * @param elapsedMillis The time elapsed since the last update call, in milliseconds. This can,
	 * in most cases, be ignored.
	 */
	public abstract void tick(long elapsedMillis);
	
}
