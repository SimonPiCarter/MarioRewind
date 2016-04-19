package mr.core;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import mr.controller.LevelLoader;
import mr.controller.Rewinder;
import mr.controller.colliders.EnemyCollider;
import mr.controller.movable.HeroMovable;
import mr.core.exception.InputFileNotFoundException;
import mr.model.GameConstant;
import mr.model.GameConstant.Layers;
import mr.model.Hero;
import mr.model.HitBox;
import mr.model.Item;
import mr.model.Level;
import mr.model.misc.Coordinate;
import mr.view.Renderer;
import mr.view.RenderingContext;
import mr.view.ResourceHandler;
import mr.view.screen.ScreenRenderer;

public class WindowGame extends BasicGame {

	private RenderingContext context;
	private Rewinder rewinder;

	private Renderer renderer;

	private HeroMovable item;
	private EnemyCollider monster;
	private Level lvl;


	private float speed = 1.0f;
	private float speedX = 0;
	private boolean rewind;

	public WindowGame() {
		super("Core :: WindowGame");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		// Load default image
		ResourceHandler.init();

		this.renderer = new Renderer();

		this.context = new RenderingContext();
		this.rewinder = new Rewinder();
		this.renderer.updateContext(context);
		this.renderer.setRewinder(rewinder);

		this.item = new HeroMovable(new Hero(
				new Coordinate(0, 0),
				new Coordinate(GameConstant.TILE_SIZE, GameConstant.TILE_SIZE),
				"resources/sprite.spt.txt",
				"id",
				0,0,0));
		this.item.getMovable().setHitBox(new HitBox(new Coordinate(),this.item.getMovable().getSize()));
		this.monster = new EnemyCollider(new Item(
				new Coordinate(200, 400),
				new Coordinate(GameConstant.TILE_SIZE, GameConstant.TILE_SIZE),
				"resources/sprite.spt.txt",
				"id",
				0));
		this.monster.getItem().setHitBox(new HitBox(new Coordinate(),new Coordinate(GameConstant.TILE_SIZE, GameConstant.TILE_SIZE)));
		this.context.addToLayer(Layers.FOREGROUND, item.getMovable());
		this.context.addToLayer(Layers.FOREGROUND, this.monster.getItem());
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

	private int timeStep = 5;
	private int elapsedTime = 0;

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		elapsedTime += delta;
		while ( elapsedTime > timeStep ) {
			elapsedTime -= timeStep;

			this.monster.collide(item, item.getSpeed(), new Coordinate());
			item.move(lvl.getStartingScreen());
			item.updateSpeed();

			context.update(timeStep);

			if ( rewind ) {
				item.getMovable().setState(1);
				rewinder.rewind(timeStep, item);
			} else {
				item.getMovable().setState(0);
				rewinder.record(timeStep, item);
			}
		}
	}

	public static void main(String[] args) throws SlickException {
		new AppGameContainer(new WindowGame(), 640, 640, false).start();
	}

	@Override
	public void keyPressed(int key, char c) {
		if ( ( c == 'z' || key == Input.KEY_UP ) && item.isOnGround() ) {
			item.getForce().setY(-250);
		}
		if ( c == 'q' || key == Input.KEY_LEFT ) {
			speedX -= speed;
			item.getSpeed().setX(speedX);
		}
		if ( c == 'd' || key == Input.KEY_RIGHT ) {
			speedX += speed;
			item.getSpeed().setX(speedX);
		}
		if ( c == 'r' || key == Input.KEY_SPACE ) {
			rewind = true;
		}
	}

	@Override
	public void keyReleased(int key, char c) {
		if ( c == 'q' || key == Input.KEY_LEFT ) {
			speedX += speed;
			item.getSpeed().setX(speedX);
		}
		if ( c == 'd' || key == Input.KEY_RIGHT ) {
			speedX -= speed;
			item.getSpeed().setX(speedX);
		}
		if ( c == 'r' || key == Input.KEY_SPACE ) {
			rewind = false;
		}
	}
}

