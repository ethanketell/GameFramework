package framework;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import game.*;
import imageEditor.ImagePanel;

public class Framework {
	
	static final boolean 
	tcsp = false,
	debug = false;
	
	static JFrame controlFrame = null;
	static final Color 
			backgroundColor 	= new Color(0x003000),
			bodyColor 		= new Color(0x007700),
			borderColor 		= new Color(0x00FF00);
	
	public static void main(String[] args) {
	    try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch(Exception e) {}
	    
	    
		openControlWindow();
	}
	
	private static void openControlWindow() {
		if(controlFrame != null) {
			controlFrame.setLocationRelativeTo(null);
			controlFrame.setVisible(true);
		} else {
			final JFrame controlFrame = new JFrame();
			controlFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			controlFrame.setUndecorated(true);
			controlFrame.setResizable(true);
			
			final JPanel controlPanel = new JPanel();
			controlPanel.setLayout(new BoxLayout(controlPanel,BoxLayout.Y_AXIS));
			controlPanel.setBackground(Color.GREEN);
			controlPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));	
			
			JPanel titlePanel = new JPanel();								//Creating the title bar
			titlePanel.setPreferredSize(new Dimension(0,60));
			titlePanel.setLayout(new BoxLayout(titlePanel,BoxLayout.X_AXIS));
			titlePanel.add(Box.createHorizontalStrut(5));
			titlePanel.setBackground(bodyColor);
			MouseAdapter dragHandler = new MouseAdapter() {					//Lets the window be dragged by
				int offsetX, offsetY;										//the title bar
				public void mousePressed(MouseEvent e) {
					Point loc = controlFrame.getLocationOnScreen();
					offsetX = loc.x-e.getXOnScreen();
					offsetY = loc.y-e.getYOnScreen();
				}
				public void mouseDragged(MouseEvent e) {
					Point loc = e.getLocationOnScreen();
					controlFrame.setLocation(loc.x+offsetX, loc.y+offsetY);
				}
			};
			titlePanel.addMouseMotionListener(dragHandler);
			titlePanel.addMouseListener(dragHandler);
																			//Add icon to title bar
			if(tcsp) {
				JLabel icon = new JLabel();
				icon.setIcon(new ImageIcon(Images.coderSchoolImage));
				icon.setAlignmentX(Component.LEFT_ALIGNMENT);
				titlePanel.add(icon);
			}
																			//Add text to title bar
			titlePanel.add(Box.createHorizontalGlue());
			Box titleBox = Box.createVerticalBox();
			JLabel title = new JLabel("Ethan's Java Game");
			title.setFont(new Font("SansSerif", Font.BOLD, 20));
			title.setForeground(Color.WHITE);
			title.setVerticalTextPosition(SwingConstants.CENTER);
			title.setAlignmentX(Component.CENTER_ALIGNMENT);
			titleBox.add(title);
			
			title = new JLabel("Framework");
			title.setFont(new Font("SansSerif", Font.BOLD, 20));
			title.setForeground(Color.WHITE);
			title.setVerticalTextPosition(SwingConstants.CENTER);
			title.setAlignmentX(Component.CENTER_ALIGNMENT);
			titleBox.add(title);
			
			titleBox.setAlignmentX(Component.CENTER_ALIGNMENT);
			titlePanel.add(titleBox);
			titlePanel.add(Box.createHorizontalGlue());
			
			controlPanel.add(titlePanel);
			controlPanel.add(Box.createVerticalStrut(5));
					
			JPanel buttonPanel = new JPanel();								//Create panel to hold buttons
			buttonPanel.setBackground(backgroundColor);
			GridLayout layout = new GridLayout(0,1);
			layout.setHgap(10);
			layout.setVgap(10);
			buttonPanel.setLayout(layout);
			
			CustomButton button;
																			//Begin adding buttons
			button = new CustomButton("Launch Game", new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Game g = new Game();
					g.setup();
					g.start();
					controlFrame.dispose();
				}
			});
			buttonPanel.add(button);
							
			button = new CustomButton("Manage Sprites", new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					openSpriteManager();
					controlFrame.setVisible(false);
				}
			});
			buttonPanel.add(button);
			
			button = new CustomButton("Manage Sounds", new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("TODO: Sound Manager");
				}
			});
			buttonPanel.add(button);
			
			button = new CustomButton("Exit", new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controlFrame.dispose();
					System.exit(0);
				}
			});
			
			buttonPanel.add(button);
			
			buttonPanel.setBorder(BorderFactory.createLineBorder(buttonPanel.getBackground(),10));
			controlPanel.add(buttonPanel);
			controlFrame.add(controlPanel);
			controlFrame.pack();						//Resizes the frame to fit components
			controlFrame.setLocationRelativeTo(null);	//Centers the frame on the screen
			controlFrame.setVisible(true);
		}
	}

	private static void openSpriteManager() {
		
		JFrame frame = new JFrame("Sprite Manager");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				Images.save();
				openControlWindow();
			}
		});
		
		JPanel leftPanelHost = new JPanel();
		leftPanelHost.setLayout(new CardLayout());
		
		JPanel leftPanelList = new JPanel();
		leftPanelList.setLayout(new BoxLayout(leftPanelList, BoxLayout.Y_AXIS));
		
		DefaultListModel<String> imageNames = new DefaultListModel<String>();
		for(String name : Images.getImageNames()) {
			imageNames.addElement(name);
		}
		
		JList<String> listPane = new JList<String>(imageNames);
		
		JScrollPane listScroll = new JScrollPane();
		listScroll.getViewport().add(listPane);
		listScroll.setPreferredSize(new Dimension(60,240));
		leftPanelList.add(listScroll);
		Box buttonBox = Box.createHorizontalBox();
		JButton addButton = new JButton("+");
		JButton removeButton = new JButton("-");
		
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openAddImageWindow(listPane);
			}
		});
		
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<String> names = listPane.getSelectedValuesList();
				int response;
				if(names.size() == 0) {
					return;
				} else {
					if(names.size() == 1) {
						response = JOptionPane.showConfirmDialog(frame,"Are you sure you want to remove \'"+names.get(0)+"\'?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
					} else {
						String selection = "";
						for(String name : names) {
							selection = selection + name + ((names.indexOf(name)<names.size()-1)?", ":"");
						}
						response = JOptionPane.showConfirmDialog(frame,"Are you sure you want to remove all of the following?\n\n"+selection, "Confirm Deletion", JOptionPane.YES_NO_OPTION);
					}
					if(response == JOptionPane.YES_OPTION) {
						for(String name : names) {
							Images.removeImage(name);
							imageNames.removeElementAt(imageNames.indexOf(name));
						}
						listPane.setSelectedIndex(-1);
					}
				}
			}
		});
		
		buttonBox.add(removeButton);
		buttonBox.add(Box.createHorizontalGlue());
		buttonBox.add(addButton);
		leftPanelList.add(buttonBox);
		
		listScroll.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(5,5,5,5),
				BorderFactory.createLoweredBevelBorder()));
		
		leftPanelHost.add(leftPanelList);
		
		JPanel leftPanelEditor = new JPanel();
		leftPanelEditor.setLayout(new BoxLayout(leftPanelEditor, BoxLayout.Y_AXIS));
		Box temp = Box.createHorizontalBox();
		JLabel tempLabel = new JLabel("Rotation:");
		tempLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
		temp.add(tempLabel);
		JTextField angle = new JTextField();
		angle.setText("0");
		Dimension textBoxSize = new Dimension(60,20);
		angle.setPreferredSize(textBoxSize);
		angle.setMaximumSize(textBoxSize);
		temp.add(Box.createHorizontalGlue());
		angle.setAlignmentY(Component.CENTER_ALIGNMENT);
		temp.add(angle);
		leftPanelEditor.add(temp);
		JSlider rotationSlider = new JSlider(SwingConstants.HORIZONTAL,0,359,0);
		rotationSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				angle.setText(""+rotationSlider.getValue());
			}
		});
		
		rotationSlider.setMinorTickSpacing(15);
		rotationSlider.setMajorTickSpacing(45);
		rotationSlider.setPaintTicks(true);
		leftPanelEditor.add(rotationSlider);
		
		leftPanelEditor.add(Box.createVerticalGlue());
		
		leftPanelHost.add(leftPanelEditor);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		ImagePanel imageDisplay = new ImagePanel();
		JScrollPane imageScroll = new JScrollPane();
		imageScroll.setPreferredSize(new Dimension(240,240));
		imageScroll.getViewport().add(imageDisplay);
		imageScroll.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(5,5,5,5),
				BorderFactory.createLoweredBevelBorder()));
		rightPanel.add(imageScroll);
		
		buttonBox = Box.createHorizontalBox();
		JButton edit = new JButton("Modify");
		buttonBox.add(Box.createHorizontalGlue());
		buttonBox.add(edit);
		rightPanel.add(buttonBox);
		
		edit.addActionListener(new ActionListener() {
			boolean editorMode = false;
			public void actionPerformed(ActionEvent e) {
				editorMode = !editorMode;
				leftPanelList.setVisible(!editorMode);
				leftPanelEditor.setVisible(editorMode);
			}
		});
		
		listPane.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				String name = listPane.getSelectedValue();
				if(name != null) {
					imageDisplay.setActiveImage(Images.getImage(name));
				} else {
					imageDisplay.setActiveImage(null);
				}
				imageScroll.getViewport().revalidate();
			}
		});
		
		listScroll.setBackground(frame.getBackground());
		imageScroll.setBackground(frame.getBackground());
		
		JSplitPane mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,leftPanelHost,rightPanel);
		mainPanel.setContinuousLayout(true);
		mainPanel.setDividerSize(3);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		frame.add(mainPanel);
		
		frame.pack();
		frame.setMinimumSize(frame.getSize());
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private static void openAddImageWindow(JList<String> nameList) {
		JFrame frame = new JFrame("Import new image");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel host = new JPanel();
		host.setLayout(new BoxLayout(host,BoxLayout.Y_AXIS));
		host.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		frame.add(host);
		
		JPanel nameEntry = new JPanel();
		nameEntry.setLayout(new BoxLayout(nameEntry,BoxLayout.X_AXIS));
		JTextField nameEntryField = new JTextField();
		JLabel nameLabel = new JLabel("Name: ");
		nameEntry.add(nameLabel);
		nameEntry.add(Box.createHorizontalGlue());
		nameEntry.add(nameEntryField);
		
		JPanel pathEntry = new JPanel();
		pathEntry.setLayout(new BoxLayout(pathEntry,BoxLayout.X_AXIS));
		JTextField pathEntryField = new JTextField();
		pathEntryField.setEditable(false);
		JLabel pathLabel = new JLabel("Path: ");
		pathEntry.add(pathLabel);
		pathEntry.add(Box.createHorizontalGlue());
		pathEntry.add(pathEntryField);
		JButton pathSelect = new JButton("...");
		pathSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String file = selectFile("Select an image");
				pathEntryField.setText(file);
				if(file != null && nameEntryField.getText().equals("")) {
					int
					lastSeparator = file.lastIndexOf(File.separator),
					dot = file.lastIndexOf('.');
					if(lastSeparator >= 0
							&& dot >= 0
							&& lastSeparator < file.length()-1
							&& dot < file.length()) {
						nameEntryField.setText(file.substring(lastSeparator+1, dot));
					}
				}
			}
		});
		pathEntry.add(pathSelect);
		
		nameLabel.validate();
		pathLabel.validate();
		Dimension labelSize = new Dimension(Math.max(nameLabel.getPreferredSize().width, pathLabel.getPreferredSize().width),nameLabel.getPreferredSize().height);
		nameLabel.setPreferredSize(labelSize);
		pathLabel.setPreferredSize(labelSize);
		
		JPanel controlButtons = new JPanel();
		controlButtons.setLayout(new BoxLayout(controlButtons,BoxLayout.X_AXIS));
		JButton button = new JButton("Cancel");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		controlButtons.add(button);
//		controlButtons.add(Box.createHorizontalStrut(100));
		controlButtons.add(Box.createHorizontalGlue());
		button = new JButton("Confirm");
		button.setSelected(true);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String 	name = nameEntryField.getText(),
						path = pathEntryField.getText();
				boolean hasName = !name.equals(""),
						hasPath = !path.equals("");
				if(hasName && hasPath) {
					Images.retrieveImage(name, path);
					((DefaultListModel<String>)nameList.getModel()).addElement(name);
					nameList.setSelectedValue(name, true);
					frame.dispose();
				} else {
					if(hasName) {
						JOptionPane.showMessageDialog(frame, "Make sure to select a file!", "Missing path",JOptionPane.ERROR_MESSAGE);
					} else if(hasPath) {
						JOptionPane.showMessageDialog(frame, "Make sure to name your image!", "Missing name",JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(frame, "Select an image using the \'...\' button,\nthen name it in the box labelled name!", "Missing name and path",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		controlButtons.add(button);
		
		host.add(nameEntry);
		host.add(pathEntry);
		host.add(Box.createVerticalStrut(5));
		host.add(controlButtons);
		
		frame.setPreferredSize(new Dimension(290,120));
		frame.setMinimumSize(new Dimension(190,120));
		frame.setMaximumSize(new Dimension(Integer.MAX_VALUE,120));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
	
	private static String selectFile(String title) {
		FileDialog fd = new FileDialog((Frame)null, title, FileDialog.LOAD);
		fd.setFilenameFilter(new FilenameFilter() {
			List<String> extensions = Arrays.asList(".png",".tif",".tiff",".bmp",".jpg",".jpeg",".gif");
			
			public boolean accept(File dir, String name) {
				int dotIndex = name.lastIndexOf('.');
				if(dotIndex > 0 && dotIndex < name.length()-1) {
					String ext = name.substring(dotIndex).toLowerCase();
					return extensions.contains(ext);
				}
				return false;
			}
		});
		fd.setVisible(true);
		String 	path = fd.getDirectory(),
				file = fd.getFile();
		if(file != null) {
			if(path != null) {
				return path+file;
			} else {
				return file;
			}
		} else {
			return null;
		}
	}
	
	public static void printDebug(String s) {
		if(debug) {
			System.out.println(s);
		}
	}
	
}
