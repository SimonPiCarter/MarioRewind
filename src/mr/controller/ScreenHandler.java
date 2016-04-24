package mr.controller;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import mr.model.GameConstant;
import mr.model.GameConstant.Layers;
import mr.model.Item;
import mr.model.Level;
import mr.model.Screen;
import mr.model.misc.Coordinate;
import mr.view.Renderer;
import mr.view.RenderingContext;
import mr.view.RenderingImage;

public class ScreenHandler {

	private final Renderer renderer;

	private RenderingContext context;
	private Rewinder rewinder;

	private Level lvl;

	public ScreenHandler(Renderer renderer) {
		this.renderer = renderer;
		this.context = new RenderingContext();
		this.rewinder = new Rewinder();
	}

	public void init(Level lvl, Screen screen) {
		this.renderer.updateContext(context);
		this.renderer.setRewinder(rewinder);
		ProjectileHandler.get().setContext(context);

		this.lvl = lvl;
		addScreenToContext(context, screen, lvl);

		this.context.addToLayer(Layers.FOREGROUND, lvl.getHero());
		this.context.addToLayer(Layers.FOREGROUND, screen.getHandler().getEnemies().toArray(new Item[0]));
		this.context.addToLayer(Layers.FOREGROUND, screen.getHandler().getColliders().toArray(new Item[0]));
	}

	public void update(int timeStep) {
		lvl.getStartingScreen().getHandler().update(timeStep, lvl.getStartingScreen(), context);

		ProjectileHandler.get().update(lvl.getHero());

		context.update(timeStep);
	}

	public void render(Graphics g) throws SlickException {
		this.renderer.render(g);
	}

	public Rewinder getRewinder() {
		return rewinder;
	}

	public RenderingContext getContext() {
		return context;
	}

	/**
	 * Add tiles of {@link Screen} to {@link RenderingContext}.
	 * @param context
	 * @param screen
	 * @param level
	 */
	public static void addScreenToContext(RenderingContext context, Screen screen, Level level) {
		Coordinate size = new Coordinate(GameConstant.TILE_SIZE, GameConstant.TILE_SIZE);
		int[] tiles = screen.getTiles();
		RenderingImage[] images = new RenderingImage[tiles.length];
		for ( int i = 0 ; i < tiles.length ; ++ i ) {
			if ( tiles[i] > 0 ) {
				int x = i%GameConstant.WIDTH;
				int y = (i-x)/GameConstant.WIDTH;

				Coordinate coord = new Coordinate(x*GameConstant.TILE_SIZE, y*GameConstant.TILE_SIZE);
				// -1 to handle 0 as empty tile (therefore index 1 in the level = 0 in the array)
				if ( tiles[i]-1 < level.getTiles().size() ) {
					images[i] = new RenderingImage(coord, size, level.getTiles().get(tiles[i]-1));
				}
			}
		}

		context.addToLayer(GameConstant.Layers.TILES, images);
	}
}
