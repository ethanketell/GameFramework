package framework;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class Input implements KeyListener, MouseListener, MouseMotionListener {
	
	static boolean debugPrint = false;
	
	
	private static HashMap<Integer,String> keyNames = new HashMap<Integer,String>();
	static {
		//Keys without useful corresponding chars
		keyNames.put(8, 		"BACKSPACE");
		keyNames.put(10, 	"ENTER");
		keyNames.put(16, 	"SHIFT");
		keyNames.put(17, 	"CONTROL");
		keyNames.put(18, 	"ALT");
		keyNames.put(20, 	"CAPSLOCK");
		keyNames.put(27,		"ESCAPE");
		keyNames.put(37, 	"LEFT");
		keyNames.put(38, 	"UP");
		keyNames.put(39, 	"RIGHT");
		keyNames.put(40, 	"DOWN");
		keyNames.put(127, 	"DELETE");
		keyNames.put(157, 	"COMMAND");
		
		//Keys that work better with names than chars
		keyNames.put(192, "GRAVE");
		keyNames.put(45, "MINUS");
		keyNames.put(61, "EQUALS");
		keyNames.put(91, "LEFT_BRACKET");
		keyNames.put(93, "RIGHT_BRACKET");
		keyNames.put(92, "BACKSLASH");
		keyNames.put(59, "SEMICOLON");
		keyNames.put(222, "APOSTROPHE");
		keyNames.put(44, "COMMA");
		keyNames.put(46, "PERIOD");
		keyNames.put(47, "SLASH");
		keyNames.put(32, "SPACE");
	}
	
	private static ArrayList<String> keysDown = new ArrayList<String>();
	private static ArrayList<String> keysPressed = new ArrayList<String>();
	
	/**
	 * Resets the keysPressed list for use with the
	 * {@link Input#keyWasPressed(String) keyWasPressed} method.
	 */
	public static void refresh() {
		keysPressed.clear();
	}
	
	/**
	 * Returns whether the key was pressed since the last {@link Input#refresh() refresh}
	 * call, will only return {@code true} on the first tick the key is down, compared to
	 * {@link keyIsDown(String)}, which returns {@link true} as long as the key is held.
	 * @param key The name of the key to check
	 * @return Whether the key was pressed since the last refresh
	 * @see Input#keyIsDown(String)
	 * @see Input#setPrintKeysPressed(boolean)
	 */
	public static boolean keyWasPressed(String key) {
		return keysPressed.contains(translate(key.toUpperCase()));
	}
	
	/**
	 * Returns whether the key is being held, will return {@link true} as long as the key is held,
	 * compared to {@link keyWasPressed(String)}, which only returns {@code true} on the first tick
	 * the key is down
	 * @param key The name of the key to check
	 * @return Whether the key is down
	 * @see Input#keyWasPressed(String)
	 * @see Input#setPrintKeysPressed(boolean)
	 */
	public static boolean keyIsDown(String key) {
		return keysDown.contains(translate(key.toUpperCase()));
	}
	
	/**
	 * Gives the name of a key, prefixed by it's location on the keyboard.
	 * @param code The keycode to be named, as given by the {@link KeyEvent#getKeyCode()} method
	 * @param location The location of the key, as given by the {@link KeyEvent#getKeyLocation()} method
	 * @return A String representing the key and location of the key, with the format
	 * {@code LOCATION_KEYNAME} (i.e. "LEFT_SHIFT")
	 */
	public static String getKeyName(int code, int location) {
		String prefix;
		switch(location) {
		case 2:
			prefix = "LEFT_";
			break;
		case 3:
			prefix = "RIGHT_";
			break;
		default:
			prefix = "";
		}
		String key;
		if(keyNames.containsKey(code)) {
			key = keyNames.get(code);
		} else if(!KeyEvent.getKeyText(code).contains("Unknown")) {
			key = KeyEvent.getKeyText(code);
		} else {
			return "Unknown KeyCode: "+code;
		}
		return prefix+key;
	}
	
	/**
	 * Converts common names for buttons to their proper label.
	 * @param buttonName The name to convert
	 * @return The converted name if a conversion is given, or {@code buttonName} if
	 * no translation is found.
	 */
	public static String translate(String buttonName) {
		switch(buttonName) {
		case "ESC":
			return "ESCAPE";
		case "SLASH":
		case "DIVIDE":
		case "QUESTION":
		case "F_SLASH":
		case "FORWARD_SLASH":
			return "/";
		case "BACKSLASH":
		case "BACK_SLASH":
		case "BACKWARD_SLASH":
		case "BAR":
			return "\\";
		case "TILDE":
		case "SQUIGGLY":
		case "`":
		case "~":
			return "GRAVE";
		case "[":
		case "LEFT_BRACE":
		case "OPEN_BRACKET":
		case "OPEN_BRACE":
			return "LEFT_BRACKET";
		case "]":
		case "RIGHT_BRACE":
		case "CLOSE_BRACKET":
		case "CLOSE_BRACE":
			return "RIGHT_BRACKET";
		case "COLON":
		case ";":
			return "SEMICOLON";
		case "QUOTE":
		case "QUOTATION":
		case "'":
		case "\"":
			return "APOSTROPHE";
		case "LESS_THAN":
		case "LESSER":
		case "OPEN_ANGLE":
		case "LEFT_ANGLE":
		case ",":
		case "<":
			return "COMMA";
		case "GREATER_THAN":
		case "GREATER":
		case "CLOSE_ANGLE":
		case "RIGHT_ANGLE":
		case ".":
		case ">":
			return "PERIOD";
		case "PLUS":
		case "=":
		case "+":
			return "EQUALS";
		case "UNDERSCORE":
		case "_":
		case "-":
			return "MINUS";
		case "META":
		case "APPLE":
			return "COMMAND";
		case "LSHIFT":
			return "LEFT_SHIFT";
		case "RSHIFT":
			return "RIGHT_SHIFT";
		case "LEFT_MOUSE_CLICK":
		case "LEFT_MOUSE_BUTTON":
		case "MOUSE_LEFT_CLICK":
		case "MOUSE_LEFT_BUTTON":
		case "LEFT_CLICK":
		case "LEFT_BUTTON":
		case "MOUSE_LEFT":
		case "LMB":
			return "MOUSE_1";
		case "MIDDLE_MOUSE_CLICK":
		case "MIDDLE_MOUSE_BUTTON":
		case "MOUSE_MIDDLE_CLICK":
		case "MOUSE_MIDDLE_BUTTON":
		case "MIDDLE_CLICK":
		case "MIDDLE_BUTTON":
		case "SCROLL_BUTTON":
			return "MOUSE_2";
		case "RIGHT_MOUSE_CLICK":
		case "RIGHT_MOUSE_BUTTON":
		case "MOUSE_RIGHT_CLICK":
		case "MOUSE_RIGHT_BUTTON":
		case "RIGHT_CLICK":
		case "RIGHT_BUTTON":
		case "MOUSE_RIGHT":
		case "RMB":
			return "MOUSE_3";
		default:
			return buttonName;
		}
	}

	/**
	 * Sets whether or not to print the name of keys and buttons which are pressed to
	 * the console. Useful to find the name of keys you can't identify.
	 * @param show Whether to print key names or not.
	 */
	public static void setPrintKeysPressed(boolean show) {
		debugPrint = show;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		String key = getKeyName(e.getKeyCode(), e.getKeyLocation());
		if(!keysDown.contains(key) && !keysPressed.contains(key)) {
			keysPressed.add(key);
		}
		if(!keysDown.contains(key)) {
			keysDown.add(key);
		}
		if(debugPrint || Framework.debug) {
			System.out.println(key);
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		String key = getKeyName(e.getKeyCode(), e.getKeyLocation());
		keysDown.remove(key);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	// ############################################
	// ##### BEGINNING OF MOUSE INPUT SECTION #####
	// ############################################
	
	private static Point mousePos = new Point(0,0);
	
	private static ArrayList<String> mouseButtonsDown = new ArrayList<String>();
	private static ArrayList<String> mouseButtonsPressed = new ArrayList<String>();
	
	public static String getMouseButtonName(int code) {
		return "MOUSE_"+code;
	}
	
	/**
	 * Returns the current position of the mouse
	 * @return A {@link Point} representing the position of the mouse.
	 */
	public static Point getMousePosition() {
		return mousePos;
	}
	
	/**
	 * Returns whether the mouse button was pressed since the last
	 * {@link Input#refresh() refresh} call, will only return {@code true} on the first tick
	 * the button is down, compared to {@link mouseButtonIsDown(String)}, which returns
	 * {@link true} as long as the button is held.
	 * @param button The name of the button to check
	 * @return Whether the button was pressed since the last refresh
	 * @see Input#mouseButtonIsDown(String)
	 * @see Input#setPrintKeysPressed(boolean)
	 */
	public static boolean mouseButtonWasPressed(String button) {
		return mouseButtonsPressed.contains(translate(button));
	}
	
	/**
	 * Returns whether the mouse button is being held, will return {@link true} as long as the
	 * button is held, compared to {@link mouseButtonWasPressed(String)}, which only returns
	 * {@code true} on the first tick the button is down.
	 * @param button The name of the mouse button to check
	 * @return Whether the button is down
	 * @see Input#mouseButtonWasPressed(String)
	 * @see Input#setPrintKeysPressed(boolean)
	 */
	public static boolean mouseButtonIsDown(String button) {
		return mouseButtonsDown.contains(translate(button));
	}
	
	
	@Override
	public void mouseDragged(MouseEvent e) {
		mousePos = e.getPoint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mousePos = e.getPoint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		String button = getMouseButtonName(e.getButton());
		if(!mouseButtonsDown.contains(button) && !mouseButtonsPressed.contains(button)) {
			mouseButtonsPressed.add(button);
		}
		if(!mouseButtonsDown.contains(button)) {
			mouseButtonsDown.add(button);
		}
		if(debugPrint || Framework.debug) {
			System.out.println(button);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		String button = getMouseButtonName(e.getButton());
		mouseButtonsDown.remove(button);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mousePos = e.getPoint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	/**
	 * Sets up the Input object to handle input given to the {@link Component}
	 * @param comp The Component to add listeners to
	 */
	public void setPanel(Component comp) {
		comp.setFocusable(true);
		comp.addKeyListener(this);
		comp.addMouseListener(this);
		comp.addMouseMotionListener(this);
	}
}
