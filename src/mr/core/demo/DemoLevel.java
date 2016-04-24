package mr.core.demo;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import mr.controller.EntityHandler;
import mr.controller.LevelLoader;
import mr.controller.ModelHandler;
import mr.controller.ProjectileHandler;
import mr.controller.Rewinder;
import mr.controller.ai.action.IAction;
import mr.controller.ai.action.Shoot;
import mr.controller.ai.action.Waypoint;
import mr.controller.entity.Hero;
import mr.core.ICore;
import mr.core.exception.FormatModelException;
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

public class DemoLevel implements ICore {

	private RenderingContext context;
	private Rewinder rewinder;

	private Renderer renderer;

	private Hero hero;

	private Level lvl;

	private float baseForce = 15;
	private float forceX = 0;
	private boolean rewind;
	private boolean rewindAllowed;

	private int timeStep = 5;
	private int elapsedTime = 0;

	private boolean win;
	private boolean lost;

	private EntityHandler handler;

	private TrueTypeFont ttf;
	private TrueTypeFont ttfWin;

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

		ttf = new TrueTypeFont(new Font("Verdana", Font.BOLD, 20), true);
		ttfWin = new TrueTypeFont(new Font("Verdana", Font.BOLD, 40), true);

		String level = "resources/level.lvl.txt";
		try {
			lvl = LevelLoader.loadLevel(level);

			ScreenRenderer.addScreenToContext(context, lvl.getStartingScreen(), lvl);
		} catch (InputFileNotFoundException e) {
			System.err.println("Cannot load level : "+level);
			e.printStackTrace();
		}
		this.handler = lvl.getStartingScreen().getHandler();
		this.hero = lvl.getHero();

		List<IAction> list = new ArrayList<IAction>();
		list.add(new Waypoint(null, null, null, new Coordinate(100, 0)));
		list.add(new Shoot(null, null, null, new Coordinate(0, -1),750));
		list.add(new Waypoint(null, null, null, new Coordinate(450, 0)));
		list.add(new Shoot(null, null, null, new Coordinate(0, -1),750));
		this.handler.getEnemies().iterator().next().setActions(list);


		this.context.addToLayer(Layers.FOREGROUND, this.lvl.getHero());
		this.context.addToLayer(Layers.FOREGROUND, this.handler.getEnemies().toArray(new Item[0]));
		this.context.addToLayer(Layers.FOREGROUND, this.handler.getColliders().toArray(new Item[0]));
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		this.renderer.render(g);
		ttf.drawString(GameConstant.WIDTH*GameConstant.TILE_SIZE-100, 15, "Life : "+hero.getLife(), Color.white);
		if ( win ) {
			ttfWin.drawString(GameConstant.WIDTH*GameConstant.TILE_SIZE/2-100, 200, "You won!", Color.red);
		} else if ( lost ) {
			ttfWin.drawString(GameConstant.WIDTH*GameConstant.TILE_SIZE/2-100, 200, "You lost!", Color.red);
		}
	}

	@Override
	public ICore update(GameContainer container, int delta) throws SlickException {
		elapsedTime += delta;
		if ( hero.getLife() == 0 ) {
			lost = true;
		}
		while ( elapsedTime > timeStep && !win && !lost) {
			elapsedTime -= timeStep;

			hero.getForce().x = forceX;

			handler.update(timeStep, lvl.getStartingScreen(), context);

			ProjectileHandler.get().update(hero);

			context.update(timeStep);

			if ( rewind ) {
				rewinder.rewind(timeStep, hero);
				rewindAllowed = false;
			} else {
				rewinder.record(timeStep, hero);
			}
			if ( hero.isOnGround() ) {
				rewindAllowed = true;
			}
		}
		if ( hero.isTouchedRightScreen() ) {
			win = true;
		}

		return this;
	}

	@Override
	public void keyPressed(int key, char c) {
		if ( ( c == 'z' || key == Input.KEY_UP ) && hero.isOnGround() ) {
			hero.getForce().setY(-250);
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
			hero.setDying(false);
		}
	}

}
