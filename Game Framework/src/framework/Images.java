package framework;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

@SuppressWarnings("unchecked")
public class Images {
	private static Map<String, String> filePaths;
	private static Map<String, BufferedImage> images;
	private static String nullImage =
			"iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAAgklEQVR42u2WUQrAIAxDe1j"
			+ "vf4XK/mQ0a3SyDsxHRQrWZ1KK5uZeGXYtzVpJCEAAAvg3wJjP9lEN5mEpQHSBAD4FuIOUA"
			+ "ETFIqgxUE5zgAKIpGStQOeRFVCBzN+nXphpxlcAjELbFJgdycjCZQVYZdgZsaUJGbBlC/Qf"
			+ "EIAAzgOojA7CqTytwSc+uQAAAABJRU5ErkJggg==";
	private static String coderSchool = 
			"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";
	private static String imageFolder = "res"+File.separator+"images"+File.separator;
	
	public static BufferedImage missingImage;
	public static BufferedImage coderSchoolImage;
	static {		//Initialize image map and serial images
		images = new HashMap<String, BufferedImage>();
		BufferedImage missing = null;
		byte decoded[] = DatatypeConverter.parseBase64Binary(nullImage);
		try {
			missing = ImageIO.read(new ByteArrayInputStream(decoded));
		} catch (IOException e) {/* This should never happen*/}
		missingImage = missing;
		BufferedImage coder = null;
		decoded = DatatypeConverter.parseBase64Binary(coderSchool);
		try {
			coder = ImageIO.read(new ByteArrayInputStream(decoded));
		} catch (IOException e) {}
		coderSchoolImage = coder;
	}
	static {		//Retrieve saved image map
		File f = new File("res"+File.separator+"images"+File.separator+"images.dat");
		try {
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			filePaths = (HashMap<String,String>)ois.readObject();
			ois.close();
			fis.close();
		} catch (FileNotFoundException e) {
			Framework.printDebug("Could not find \'"+f.getPath()+"\'");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println("\'"+f.getPath()+"\' seems to be corrupt. Fix or delete it.");
			e.printStackTrace();
		}
		if(filePaths == null) {
			Framework.printDebug("Saved imagename Map is null, creating a new one");
			filePaths = new HashMap<String,String>();
		}
		for(String name : filePaths.keySet()) {
			retrieveImage(name, localizePath(filePaths.get(name)));
		}
	}
	/**
	 * Retrieves the image at the given file path and assigns it the given name.
	 * Also handles formatting for future use, copying the image to the res/images folder,
	 * and renaming it if it's name does not follow the convention {@code name}.png
	 * <br><br>For use by the framework; do not use this function to get sprite images,
	 * instead use {@link Images#getImage(String) getImage}
	 * @param name The name of the image to be used in the {@link Images#getImage(String) getImage} method
	 * @param path The file path of the image
	 * @return Whether the image was retrieved successfully
	 * @see {@link java.io.File}
	 * @see {@link java.awt.BufferedImage}
	 */
	public static boolean retrieveImage(String name, String path) {
		File f = new File(path);
		BufferedImage image = null;
		try {
			image = ImageIO.read(f);
			images.put(name, image);
		} catch(IOException e) {
			System.err.println("Could not retrieve image "+path);
			return false;
		}
		File newFile = new File(imageFolder+name+".png");
		if(!f.equals(newFile)) {
			try {
				ImageIO.write(image, "PNG", new File(imageFolder+name+".png"));
				if(f.getParent() != null && f.getParent().equals(imageFolder)) {
					f.delete();
				}
			} catch (IOException e) {
				System.err.println("Could not write "+imageFolder+name+".png");
				e.printStackTrace();
			}
		}
		filePaths.put(name, newFile.getPath());
		return true;
	}
	
	/**
	 * Removes the named image from the handler, and deletes it from the images folder.
	 * <br><br>For use by the framework; do not use this function to remove images, instead
	 * use the sprite manager.
	 * @param name the name of the image to be removed
	 */
	public static void removeImage(String name) {
		String path = filePaths.get(name);
		File f = new File(path);
		f.delete();
		filePaths.remove(name);
		images.remove(name);
	}
	
	/**
	 * Returns the image with the given name
	 * @param imageName The name of the image
	 * @return The image with the given name, or a "null" image if the name is not found
	 */
	public static BufferedImage getImage(String imageName) {
		if(images.containsKey(imageName)) {
			return images.get(imageName);
		} else {
			return missingImage;
		}
	}
	
	/**
	 * Returns a {@link java.util.Set Set} containing the names of the images
	 * available to the {@link Images#getImage(String) getImage} method
	 * @return A Set containing Strings
	 */
	public static Set<String> getImageNames(){
		((HashMap<String,BufferedImage>)images).keySet();
		return images.keySet();
	}
	
	/**
	 * Write the names and paths to a file to be loaded at a later date
	 */
	public static void save() {
		File f = new File(imageFolder+"images.dat");
		
		if(!f.getParentFile().exists()) {
			Framework.printDebug("Creating folder \'"+f.getParent()+"\'");
			f.getParentFile().mkdirs();
		};
		try {
			if(f.exists()) {
				Framework.printDebug("Deleting old version of \'"+f.getPath()+"\'");
				f.delete();
			}
			Framework.printDebug("Creating \'"+f.getPath()+"\'");
			f.createNewFile();
		} catch(IOException e) {
			System.err.println("Failed to create file \'"+f.getPath()+"\'");
			e.printStackTrace();
		}
		try {
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			Framework.printDebug("Writing \'"+f.getPath()+"\'");
			oos.writeObject(filePaths);
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			System.err.println("Could not find \'images.dat,\' this should not happen");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Failed to create outputstream to \'images.dat\'");
			e.printStackTrace();
		}
	}
	
	/**
	 * Rotates an image counter-clockwise
	 * @param image The {@link BufferedImage} to rotate
	 * @param radians The angle to rotate by
	 * @return A rotated image
	 */
	public static BufferedImage rotateImage(BufferedImage image, double degrees) {
		double radians = Math.toRadians(degrees);
		int newWidth = (int)(image.getHeight()*Math.abs(Math.sin(radians))+image.getWidth()*Math.abs(Math.cos(radians)));
		int newHeight = (int)(image.getHeight()*Math.abs(Math.cos(radians))+image.getWidth()*Math.abs(Math.sin(radians)));
		BufferedImage out = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D)out.getGraphics();
		AffineTransform transform = new AffineTransform();
		transform.translate((newWidth-image.getWidth())/2, (newHeight-image.getHeight())/2);
		transform.rotate(radians, image.getWidth()/2, image.getHeight()/2);
		
		g.setTransform(transform);
		g.drawImage(image,0,0, null);
		g.dispose();
		return out;
	}
	
	/**
	 * Scales an image
	 * @param image The {@link BufferedImage} to scale
	 * @param scale The multiple by which to scale the image
	 * @return A scaled instance of the image
	 */
	public static BufferedImage scaleImage(BufferedImage image, double scale) {
		if(scale == 1) {
			return image;
		}
		int newWidth = (int) Math.ceil(image.getWidth()*scale);
		int newHeight = (int)(Math.ceil(image.getHeight()*scale));
		BufferedImage out = new BufferedImage(newWidth,newHeight,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D)out.getGraphics();
		g.scale(scale, scale);
		g.drawImage(image,0,0,null);
		g.dispose();
		return out;
	}
	
	/**
	 * Returns an instance of the given {@link BufferedImage} transformed
	 * by the given {@code degrees}, {@code sx}, and {@code sy}
	 * @param image The image to transform
	 * @param degrees The angle to rotate the image by
	 * @param sx The factor to scale the width of the image by
	 * @Param sy The factor to scale the height of the image by
	 * @return A copy of {@link image} with the transformations applied
	 */
	public static BufferedImage transformImage(BufferedImage image, double degrees, double sx, double sy) {
		double radians = Math.toRadians(degrees);
		int scaleWidth = (int)(image.getWidth() * sx);
		int scaleHeight = (int)(image.getHeight() * sy);
		int newWidth = (int)(scaleHeight*Math.abs(Math.sin(radians))+scaleWidth*Math.abs(Math.cos(radians)));
		int newHeight = (int)(scaleHeight*Math.abs(Math.cos(radians))+scaleWidth*Math.abs(Math.sin(radians)));
		
		BufferedImage out = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		
		AffineTransform transform = new AffineTransform();
		transform.translate((newWidth-scaleWidth)/2, (newHeight-scaleHeight)/2);
		transform.rotate(radians, scaleWidth/2, scaleHeight/2);
		transform.scale(sx, sy);
		
		Graphics2D g2 = (Graphics2D)out.getGraphics();
		g2.setTransform(transform);
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
		
		return out;
	}
	
	/**
	 * Localizes the String path, replacing any forward/back slashes with the appropriate
	 * version provided by {@link File#separatorChar}
	 * @param path The path to be localized
	 * @return The localized version of the path
	 */
	private static String localizePath(String path) {
		if(File.separatorChar == '/') {
			path = path.replace('\\', File.separatorChar);
		} else {
			path = path.replace('/', File.separatorChar);
		}
		return path;
	}
}
