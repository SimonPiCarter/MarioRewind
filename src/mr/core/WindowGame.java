package mr.core;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import mr.controller.LevelLoader;
import mr.controller.Rewinder;
import mr.controller.ai.AI;
import mr.controller.ai.action.IAction;
import mr.controller.ai.action.Waypoint;
import mr.controller.colliders.EnemyCollider;
import mr.controller.movable.HeroMovable;
import mr.controller.movable.ItemMovable;
import mr.core.exception.InputFileNotFoundException;
import mr.model.GameConstant;
import mr.model.GameConstant.Layers;
import mr.model.Hero;
import mr.model.HitBox;
import mr.model.Item;
import mr.model.Level;
import mr.model.misc.Coordinate;
import mr.model.model.AIModel;
import mr.model.state.Idle;
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
	private AI ai;
	private Level lvl;

	private float baseForce = 15;
	private float forceX = 0;
	private boolean rewind;
	private boolean rewindAllowed;

	private int timeStep = 5;
	private int elapsedTime = 0;

	private int deadThreshold = 1000;
	private int elapsedDyingTime = 0;

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
				"resources/spriteFull.spt.txt",
				"id",
				new Idle(true),
				0,
				0));
		this.item.getMovable().setHitBox(new HitBox(new Coordinate(),this.item.getMovable().getSize()));
		this.monster = new EnemyCollider(new Item(
				new Coordinate(200, 416),
				new Coordinate(GameConstant.TILE_SIZE, GameConstant.TILE_SIZE),
				"resources/spriteFull.spt.txt",
				"id",
				new Idle(true)));
		this.ai = new AI(new ItemMovable(this.monster.getItem()), new AIModel());

		List<IAction> list = new ArrayList<IAction>();
		list.add(new Waypoint(null, null, null, new Coordinate(100, 0)));
		list.add(new Waypoint(null, null, null, new Coordinate(450, 0)));
		this.ai.setActions(list);

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

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		elapsedTime += delta;
		while ( elapsedTime > timeStep ) {
			elapsedTime -= timeStep;

			item.getForce().x = forceX;

			item.updateSpeed();
			item.updateState();
			ai.update(delta);
			ai.getMovable().updateState();

			if ( !monster.getItem().isDead() && !monster.getItem().isDying() ) {
				monster.collide(item, item.getSpeed(), ai.getMovable().getSpeed());
			}
			if ( monster.getItem().isDying() ) {
				elapsedDyingTime += timeStep;
				if ( elapsedDyingTime > deadThreshold ) {
					monster.getItem().setDead(true);
					monster.getItem().setDying(false);
				}
			}
			if ( monster.getItem().isDead() ) {
				this.context.removeFromLayer(Layers.FOREGROUND, this.monster.getItem());
			}
			item.move(lvl.getStartingScreen());
			ai.getMovable().move(lvl.getStartingScreen());

			context.update(timeStep);

			if ( rewind ) {
				rewinder.rewind(timeStep, item);
				rewindAllowed = false;
			} else {
				rewinder.record(timeStep, item);
			}
			if ( item.isOnGround() ) {
				rewindAllowed = true;
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
			forceX = -baseForce;
		}
		if ( c == 'd' || key == Input.KEY_RIGHT ) {
			forceX = baseForce;
		}
		if ( c == 'r' || key == Input.KEY_SPACE ) {
			rewind = rewindAllowed;
		}
	}

	@Override
	public void keyReleased(int key, char c) {
		if ( c == 'q' || key == Input.KEY_LEFT ) {
			if ( forceX < 0 ) {
				forceX = 0;
			}
		}
		if ( c == 'd' || key == Input.KEY_RIGHT ) {
			if ( forceX > 0 ) {
				forceX = 0;
			}
		}
		if ( c == 'r' || key == Input.KEY_SPACE ) {
			rewind = false;
		}
		if ( c == 'x' ) {
			item.getMovable().setDying(false);
		}
	}
}

