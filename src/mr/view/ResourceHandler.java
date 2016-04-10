package mr.view;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import mr.controller.RenderingImageLoader;
import mr.model.GameConstant;
import mr.model.Sprite;

public class ResourceHandler {
	private static Map<String, Image> resourcesImages;
	private static Map<String, RenderingImage> resourcesRenderingImages;
	private static Image defaultImage;

	public static void init() throws SlickException {
		defaultImage = new Image(GameConstant.defaultPathToImage);
		resourcesImages = new HashMap<String, Image>();
		resourcesRenderingImages = new HashMap<String, RenderingImage>();
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
				resourcesRenderingImages.put(sprite.getType(), RenderingImageLoader.LoadRenderingImage(sprite.getType()));
			} catch (Exception e) {
				System.err.println("Cannot load file "+sprite.getType()+" use default image instead");
				resourcesRenderingImages.put(sprite.getType(), new RenderingImage(sprite.getPosition(),sprite.getSize(),GameConstant.defaultPathToImage));
			}
		}

		return new RenderingImage(resourcesRenderingImages.get(sprite.getType()));
	}


}
