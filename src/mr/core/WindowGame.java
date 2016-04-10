package mr.core;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import mr.controller.LevelLoader;
import mr.controller.movable.SpriteMovable;
import mr.core.exception.InputFileNotFoundException;
import mr.model.GameConstant;
import mr.model.GameConstant.Layers;
import mr.model.Level;
import mr.model.Sprite;
import mr.model.misc.Coordinate;
import mr.view.Renderer;
import mr.view.RenderingContext;
import mr.view.RenderingImage;
import mr.view.ResourceHandler;
import mr.view.screen.ScreenRenderer;

public class WindowGame extends BasicGame {
	private Renderer renderer;

	private RenderingContext context;

	private SpriteMovable sprite;
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
		this.sprite = new SpriteMovable(new Sprite(new Coordinate(0, 0), new Coordinate(GameConstant.TILE_SIZE, GameConstant.TILE_SIZE), "test.png"));
		this.context.addToLayer(Layers.FOREGROUND, new RenderingImage(sprite.getSprite().getPosition(),sprite.getSprite().getSize(),"test.png"));
		String level = "level.lvl.txt";
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
		sprite.move(lvl.getStartingScreen());
		sprite.updateSpeed(delta/1000.f);
		//System.out.println(sprite.isOnGround());
	}

	public static void main(String[] args) throws SlickException {
		new AppGameContainer(new WindowGame(), 640, 640, false).start();
	}

	private float speed = 0.2f;
	private float speedX = 0;

	@Override
	public void keyPressed(int key, char c) {
		if ( c == 'z' && sprite.isOnGround() ) {
			sprite.getForce().setY(-450);
		}
		if ( c == 'q' ) {
			speedX -= speed;
		}
		if ( c == 'd' ) {
			speedX += speed;
		}
		sprite.getSpeed().setX(speedX);
	}

	@Override
	public void keyReleased(int key, char c) {
		if ( c == 'q' ) {
			speedX += speed;
		}
		if ( c == 'd' ) {
			speedX -= speed;
		}
		sprite.getSpeed().setX(speedX);
	}
}

