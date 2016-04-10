package mr.core;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import mr.controller.LevelLoader;
import mr.controller.movable.ItemMovable;
import mr.core.exception.InputFileNotFoundException;
import mr.model.GameConstant;
import mr.model.GameConstant.Layers;
import mr.model.Item;
import mr.model.Level;
import mr.model.misc.Coordinate;
import mr.view.Renderer;
import mr.view.RenderingContext;
import mr.view.ResourceHandler;
import mr.view.screen.ScreenRenderer;

public class WindowGame extends BasicGame {
	private Renderer renderer;

	private RenderingContext context;

	private ItemMovable item;
	private Level lvl;

	public WindowGame() {
		super("Core :: WindowGame");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		// Load default image
		ResourceHandler.init();

		this.context = new RenderingContext();
		this.renderer = new Renderer();
		this.renderer.updateContext(context);
		this.item = new ItemMovable(new Item(
				new Coordinate(0, 0),
				new Coordinate(GameConstant.TILE_SIZE, GameConstant.TILE_SIZE),
				"resources/sprite.spt.txt",
				"id",
				0));
		this.context.addToLayer(Layers.FOREGROUND, item.getMovable());
		String level = "resources/level.lvl.txt";
		try {
			lvl = LevelLoader.loadLevel(level);

			ScreenRenderer.addScreenToContext(context, lvl.getStartingScreen(), lvl);
		} catch (InputFileNotFoundException e) {
			System.err.println("Cannot load level : "+level);
			e.printStackTrace();
		}
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		this.renderer.render(g);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		item.move(lvl.getStartingScreen());
		item.updateSpeed(delta/1000.f);
		context.update(delta);
		//System.out.println(sprite.isOnGround());
	}

	public static void main(String[] args) throws SlickException {
		new AppGameContainer(new WindowGame(), 640, 640, false).start();
	}

	private float speed = 0.2f;
	private float speedX = 0;

	@Override
	public void keyPressed(int key, char c) {
		if ( c == 'z' && item.isOnGround() ) {
			item.getForce().setY(-450);
		}
		if ( c == 'q' ) {
			speedX -= speed;
		}
		if ( c == 'd' ) {
			speedX += speed;
		}
		if ( key == Input.KEY_SPACE ) {
			item.getMovable().setState((item.getMovable().getState()+1)%2);
		}
		item.getSpeed().setX(speedX);
	}

	@Override
	public void keyReleased(int key, char c) {
		if ( c == 'q' ) {
			speedX += speed;
		}
		if ( c == 'd' ) {
			speedX -= speed;
		}
		item.getSpeed().setX(speedX);
	}
}

