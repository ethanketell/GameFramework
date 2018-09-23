package framework;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * This is the base class for the game itself. This extensions of this class handle the
 * global functionality of the game, such as handling {@link World Worlds}, adding
 * {@link Entity Entities}, saving data, and closing the game when appropriate. The Game
 * class also controls drawing, possessing the {@link GamePanel} that displays game contents,
 * and {@link AbstractGame#setup()} is used when the "Launch Game" button is pressed. Only one
 * subclass of AbstractGame should exist per project, behavior is not guaranteed if more than
 * one exists.
 * 
 * @author Ethan Ketell
 */
public abstract class AbstractGame {
	
	private int fps = 30;
	
	private GamePanel panel = null;
	private World activeWorld = null;
	
	private Timer gameTimer = null;
	
	/**
	 * This method is called when the game first starts, via the "Launch Game" button.
	 * It should initialize all {@link World Worlds}, and set one active using the 
	 * {@link World#setActiveWorld(World)} method to activate one when the game begins.
	 */
	public abstract void setup();
	
	/**
	 * This method creates the {@link JFrame} and {@link GamePanel} for the game and starts
	 * the {@link Timer} which tells the game to {@link Game#tick() tick}
	 */
	public final void start() {
		JFrame frame = new JFrame();			//creates the window for the game
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		//"Exit the program when the window closes"
		
		Input listener = new Input();
		panel = new GamePanel();				//creates the panel for the game
		panel.setWorld(activeWorld);			//sets the active world for the panel
		listener.setPanel(panel);
		
		frame.add(panel);					//adds the panel to the window
		frame.pack();						//resizes the window to fit the panel
		frame.setLocationRelativeTo(null);	//centers the window on screen
		frame.setVisible(true);				//shows the window
		
		AbstractGame ag = this;
		gameTimer = new Timer(1000/fps, new ActionListener() {
			
			long lastUpdate = System.currentTimeMillis();
			public void actionPerformed(ActionEvent e) {
				long now = System.currentTimeMillis();
				ag.tick(now-lastUpdate);
				Input.refresh();
				lastUpdate = now;
			}
		});
		gameTimer.start();
	}
	
	/**
	 * This method tells the active {@link World} to {@link World#tick() tick}, the
	 * {@link GamePanel} to {@link GamePanel#repaint() repaint}, and updates mouse and
	 * key inputs.
	 * @param elapsedMillis The time elapsed since the last update call, in milliseconds.
	 */
	public void tick(long elapsedMillis) {
		if(activeWorld != null) {
			activeWorld.tick(elapsedMillis);
		}
		if(panel != null) {
			panel.repaint();
		}
	}
	
	/**
	 * This method will be called prior to the game closing down. There is no guarantee
	 * this method will be called in cases where the game is closed by the system, or in
	 * other abnormal conditions, such as crashing or power loss. If any data should be
	 * saved before game close, this method should call {@link Game#save(String)}.
	 */
	public abstract void stop();
	 
	/**
	 * Sets the active {@link World} to {@code world}. The active world is the world which
	 * is drawn to the screen, and the only world which will receive calls to the
	 * {@link World#tick() tick()} method.
	 * @param world The World to be set as active for the {@link Game}
	 */
	public final void setActiveWorld(World world) {
		activeWorld = world;
		if(panel != null) {
			panel.setWorld(world);
		}
	}
	
	public static boolean keyIsDown(String keyName) {
		return Input.keyIsDown(keyName);
	}
	
	public static boolean keyWasPressed(String keyName) {
		return Input.keyWasPressed(keyName);
	}

}
