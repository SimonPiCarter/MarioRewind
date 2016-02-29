package mr.view;

import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ResourceHandler {
	private static Map<String, Image> resources;
	private static Image defaultImage;
	
	public static void init() throws SlickException {
		defaultImage = new Image("");
	}
	
	public static Image getImage(String path) {
		// Load the file if necessary
		if ( !resources.containsKey(path) ) {
			try {
				resources.put(path, new Image(path));
			} catch (SlickException e) {
				System.err.println("Cannot load file "+path+" use default image instead");
				resources.put(path, defaultImage);
			}
		}
		
		return resources.get(path);
	}
}
