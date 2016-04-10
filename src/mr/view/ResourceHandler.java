package mr.view;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import mr.model.Sprite;

public class ResourceHandler {
	private static Map<String, Image> resourcesImages;
	private static Map<String, RenderingImage> resourcesRenderingImages;
	private static Image defaultImage;

	private static String defaultPathToImage = "resources/default.png";

	public static void init() throws SlickException {
		defaultImage = new Image(defaultPathToImage);
		resourcesImages = new HashMap<String, Image>();
	}

	public static Image getImage(String path) {
		// Load the file if necessary
		if ( !resourcesImages.containsKey(path) ) {
			try {
				resourcesImages.put(path, new Image(path));
			} catch (Exception e) {
				System.err.println("Cannot load file "+path+" use default image instead");
				resourcesImages.put(path, defaultImage);
			}
		}

		return resourcesImages.get(path);
	}

	public static RenderingImage getRenderingImage(Sprite sprite) {
		// Load the file if necessary
		if ( !resourcesRenderingImages.containsKey(sprite.getType()) ) {
			try {
				resourcesRenderingImages.put(sprite.getType(), new RenderingImage(sprite.getPosition(),sprite.getSize(),defaultPathToImage));
			} catch (Exception e) {
				System.err.println("Cannot load file "+sprite.getType()+" use default image instead");
				resourcesRenderingImages.put(sprite.getType(), new RenderingImage(sprite.getPosition(),sprite.getSize(),defaultPathToImage));
			}
		}

		return resourcesRenderingImages.get(sprite.getType());
	}


}
