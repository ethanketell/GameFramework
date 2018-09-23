package framework;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * This is the default type of World, it will be described by a width and height in pixels,
 * and will display those coordinates exactly.
 * 
 * @see ScrollableWorld
 * @see GridWorld
 * @author Ethan Ketell
 *
 */
public class World {
	
	/**
	 * An {@link ArrayList} containing all the entities in this World
	 */
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	
	private int width, height;
	
	/**
	 * Constant providing easy access to cardinal and ordinal direction values.
	 */
	public static final double
		EAST = 0,
		SOUTHEAST = 45,
		SOUTH = 90,
		SOUTHWEST = 135,
		WEST = 180,
		NORTHWEST = 225,
		NORTH = 270,
		NORTHEAST = 315;
	
	/**
	 * Creates a World with the given {@code width} and {@code height} in pixels.
	 * @param width The width of the world
	 * @param height The height of the world
	 */
	public World(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Gives the viewport of the world, meaning what in-world coordinates are visible within
	 * the bounds of the parent {@link GamePanel}.
	 * @return The visible {@link Rectangle2D.Double} of the world
	 */
	public Rectangle2D.Double getViewPort() {
		return new Rectangle2D.Double(0,0,width,height);
	}
	
	/**
	 * Adds an {@link Entity} to the World at the origin (0,0)
	 * @param entity The Entity to add
	 */
	public void add(Entity entity) {
		entities.add(entity);
		entity.setParentWorld(this);
		entity.x = 0;
		entity.y = 0;
	}
	
	/**
	 * Adds an {@link Entity} to the world at the given x, y coordinate.
	 * @param entity The Entity to add
	 * @param x The x coordinate for the Entity
	 * @param y The y coordinate for the Entity
	 */
	public void add(Entity entity, double x, double y) {
		entities.add(entity);
		entity.setParentWorld(this);
		entity.x = x;
		entity.y = y;
	}
	
	/**
	 * This method is called to update the World. If Overridden, be sure to call 
	 * {@link World#tick() super.tick()} 
	 * @param elapsedMillis The time elapsed since the last update call, in milliseconds.
	 */
	public void tick(long elapsedMillis) {
		for(Entity e : entities) {
			e.tick(elapsedMillis);
		}
	}
	
	/**
	 * This method is called to draw the World.
	 * @param g The {@link Graphics2D} of the {@link GamePanel} hosting the world
	 */
	public void paint(Graphics2D g) {
		g.setColor(new Color(40,80,10));
		g.fillRect(0, 0, width, height);
	}
	 /**
	  * Calls the {@link Entity#paint(Graphics2D) paint} method of each {@link Entity} in
	  * this World.
	  * @param g The {@link Graphics2D} of the {@link GamePanel} hosting the world
	  */
	public final void paintEntities(Graphics2D g) {
		for(Entity e : entities) {
			e.paint(g);
		}
	}
}
