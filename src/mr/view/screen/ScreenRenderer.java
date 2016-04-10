package mr.view.screen;

import mr.model.GameConstant;
import mr.model.Level;
import mr.model.Screen;
import mr.model.misc.Coordinate;
import mr.view.RenderingContext;
import mr.view.RenderingImage;

public class ScreenRenderer {

	/**
	 * Add tiles of {@link Screen} to {@link RenderingContext}.
	 * @param context
	 * @param screen
	 * @param level
	 */
	public static void addScreenToContext(RenderingContext context, Screen screen, Level level) {
		int[] tiles = screen.getTiles();
		RenderingImage[] images = new RenderingImage[tiles.length];
		for ( int i = 0 ; i < tiles.length ; ++ i ) {
			if ( tiles[i] > 0 ) {
				int x = i%GameConstant.WIDTH;
				int y = (i-x)/GameConstant.WIDTH;

				Coordinate coord = new Coordinate(x*GameConstant.TILE_SIZE, y*GameConstant.TILE_SIZE);
				// -1 to handle 0 as empty tile (therefore index 1 in the level = 0 in the array)
				images[i] = new RenderingImage(coord, level.getTiles().get(tiles[i]-1));
			}
		}

		context.addToLayer(GameConstant.Layers.TILES, images);
	}
}
