package framework;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ComponentListener;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;

import javax.swing.JPanel;

public class CustomButton extends JPanel implements MouseListener, ComponentListener {
	private static final long serialVersionUID = 1L;
	
	static Font defaultFont = new Font("SansSerif", Font.BOLD, 24);
	private static HashMap<String, Color> defaultColors = new HashMap<String, Color>(9);
	static {
		defaultColors.put("fill_normal", new Color(0x007700));
		defaultColors.put("border_normal", new Color(0x00FF00));
		defaultColors.put("text_normal", Color.WHITE);
		defaultColors.put("fill_hovered", new Color(0x60bb60));
		defaultColors.put("border_hovered",new Color(0x50FF50));
		defaultColors.put("text_hovered", Color.WHITE);
		defaultColors.put("fill_pressed", new Color(0x003000));
		defaultColors.put("border_pressed",new Color(0x00AA00));
		defaultColors.put("text_pressed", Color.LIGHT_GRAY);

//		defaultColors.put("fill_normal", new Color(0x777777));
//		defaultColors.put("border_normal", Color.WHITE);
//		defaultColors.put("text_normal", Color.WHITE);
//		defaultColors.put("fill_hovered", new Color(0xAAAAAA));
//		defaultColors.put("border_hovered",Color.WHITE);
//		defaultColors.put("text_hovered", Color.WHITE);
//		defaultColors.put("fill_pressed", new Color(0x444444));
//		defaultColors.put("border_pressed",Color.LIGHT_GRAY);
//		defaultColors.put("text_pressed", Color.LIGHT_GRAY);
	}
	
	private HashMap<String, Color> colors = defaultColors;

	private RoundRectangle2D.Double bounds = new RoundRectangle2D.Double(),
									fillBounds = new RoundRectangle2D.Double();
	private String text;
	private boolean hovered = false, pressed = false;
	private ActionListener action;
	
	public CustomButton(String text) {
		this(text,new Dimension(240,60));
	}
	
	public CustomButton(String text, Dimension size) {
		this(text, size, null);
	}
	
	public CustomButton(String text, ActionListener action) {
		this(text, new Dimension(240,60), action);
		
	}
	
	public CustomButton(String text, Dimension size, ActionListener action) {
		this.setPreferredSize(size);
		this.text = text;
		this.action = action;
		this.setFont(defaultFont);
		this.addMouseListener(this);
		this.addComponentListener(this);
	}
	
	public void addAction(ActionListener action) {
		this.action = action;
	}
	
	/**
	 * Sets a {@link Color} for a given property and state.
	 * @param property the property to assign. Options are: fill, border, text
	 * @param state the state to assign. Options are: normal, hovered, pressed
	 * @param color the color to assign
	 */
	public void setColor(String property, String state, Color color) {
		if(property.equals("fill")||property.equals("border")||property.equals("text")) {
			if(state.equals("auto")) {
				colors.put(property+"_normal", color);
				colors.put(property+"_hovered", color.brighter());
				colors.put(property+"_pressed", color.darker());
				return;
			}
		}
		if(colors.containsKey(property+"_"+state)) {
			colors.put(property+"_"+state, color);
		} else {
			if(!(property.equals("fill")||property.equals("border")||property.equals("text"))) {
				throw new IllegalArgumentException(property+" is not a valid property. Options are: fill, border, text");
			}
			if(!(state.equals("normal")||state.equals("hovered")||state.equals("pressed"))) {
				throw new IllegalArgumentException(state+" is not a valid state. Options are: normal, hovered, pressed");
			}
		}
	}
	
	public HashMap<String, Color> getColors(){
		return colors;
	}
	
	public void setColors(HashMap<String, Color> newColors) {
		if(defaultColors.keySet().equals(newColors.keySet())) {
			colors = newColors;
		} else {
			throw new IllegalArgumentException("Attempted to set improperly formatted color map");
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setColor(this.getParent().getBackground());
		g2d.fillRect(0, 0, getWidth(), getHeight());
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		String state = (pressed)?"pressed":((hovered)?"hovered":"normal");
		
		g2d.setColor(colors.get("border_"+state));
		g2d.fill(bounds);
		
		g2d.setColor(colors.get("fill_"+state));
		g2d.fill(fillBounds);
		
		g2d.setColor(colors.get("text_"+state));
		int strWidth = g2d.getFontMetrics().stringWidth(text);
		int strHeight = getFont().getSize()-g2d.getFontMetrics().getLeading()-g2d.getFontMetrics().getDescent();
		g2d.drawString(text, (getWidth()-strWidth)/2, (getHeight()+(strHeight))/2);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(action != null) {
			action.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED, null));
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		hovered = true;
		this.repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		hovered = false;
		pressed = false;
		this.repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pressed = true;
		this.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		pressed = false;
		this.repaint();
	}
	
	@Override
	public void componentResized(ComponentEvent e) {
		bounds = new RoundRectangle2D.Double(0,0,getWidth(),getHeight(),getHeight(), getHeight());
		fillBounds = new RoundRectangle2D.Double(5,5,getWidth()-10,getHeight()-10,getHeight()-10, getHeight()-10);
	}
	
	
	public void componentShown(ComponentEvent e) {}
	public void componentHidden(ComponentEvent e) {}
	public void componentMoved(ComponentEvent e) {}
}
