package mr.core;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import mr.controller.LevelLoader;
import mr.controller.ModelHandler;
import mr.controller.ProjectileHandler;
import mr.controller.Rewinder;
import mr.controller.ai.AI;
import mr.controller.ai.action.IAction;
import mr.controller.ai.action.Shoot;
import mr.controller.ai.action.Waypoint;
import mr.controller.colliders.EnemyCollider;
import mr.controller.movable.HeroMovable;
import mr.controller.movable.ItemMovable;
import mr.core.exception.FormatModelException;
import mr.core.exception.InputFileNotFoundException;
import mr.model.GameConstant;
import mr.model.GameConstant.Layers;
import mr.model.Hero;
import mr.model.Item;
import mr.model.Level;
import mr.model.misc.Coordinate;
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

	private int recoverThreshold = 1500;
	private int elapsedHeroDyingTime = 0;
	private int life = 3;

	private Font font;
	private TrueTypeFont ttf;

	public WindowGame() {
		super("Core :: WindowGame");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		// Load default image
		ResourceHandler.init();

		this.renderer = new Renderer();
		try {
			ModelHandler.get().load("resources/models.data.txt");
		} catch (InputFileNotFoundException | FormatModelException e1) {
			e1.printStackTrace();
		}

		this.context = new RenderingContext();
		this.rewinder = new Rewinder();
		this.renderer.updateContext(context);
		this.renderer.setRewinder(rewinder);
		ProjectileHandler.get().setContext(context);

		this.item = new HeroMovable(new Hero(
				new Coordinate(0, 0),
				ModelHandler.get().getModel("default"),
				"id",
				new Idle(true),
				0,
				0));
		this.monster = new EnemyCollider(new Item(
				new Coordinate(200, 416),
				ModelHandler.get().getModel("default"),
				"id",
				new Idle(true)));
		this.ai = new AI(new ItemMovable(this.monster.getItem()), ModelHandler.get().getAIModel("monster"));

		List<IAction> list = new ArrayList<IAction>();
		list.add(new Waypoint(null, null, null, new Coordinate(100, 0)));
		list.add(new Shoot(null, null, null, new Coordinate(0, -1),750));
		list.add(new Waypoint(null, null, null, new Coordinate(450, 0)));
		list.add(new Shoot(null, null, null, new Coordinate(0, -1),750));
		this.ai.setActions(list);

		font = new Font("Verdana", Font.BOLD, 20);
		ttf = new TrueTypeFont(font, true);

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
		ttf.drawString(GameConstant.WIDTH*GameConstant.TILE_SIZE-100, 15, "Life : "+life, Color.white);
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
			ai.update(timeStep);
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


			if ( item.getMovable().isDying() ) {
				if ( elapsedHeroDyingTime == 0 ) {
					--life;
				}
				elapsedHeroDyingTime += timeStep;
				if ( elapsedHeroDyingTime > recoverThreshold ) {
					elapsedHeroDyingTime = 0;
					item.getMovable().setDying(false);

				}
			}

			item.move(lvl.getStartingScreen());
			ai.getMovable().move(lvl.getStartingScreen());

			ProjectileHandler.get().update();

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

