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

import mr.controller.LevelLoader;
import mr.controller.ModelHandler;
import mr.controller.ProjectileHandler;
import mr.controller.ScreenHandler;
import mr.controller.ai.action.IAction;
import mr.controller.ai.action.Shoot;
import mr.controller.ai.action.Waypoint;
import mr.controller.entity.Hero;
import mr.core.ICore;
import mr.core.exception.FormatModelException;
import mr.core.exception.InputFileNotFoundException;
import mr.model.GameConstant;
import mr.model.Level;
import mr.model.misc.Coordinate;
import mr.view.Renderer;
import mr.view.ResourceHandler;

public class DemoLevel implements ICore {

	private Renderer renderer;
	private ScreenHandler screenHanlder;

	private Level lvl;

	private float baseForce = 15;
	private float forceX = 0;
	private boolean rewind;
	private boolean rewindAllowed;

	private int timeStep = 5;
	private int elapsedTime = 0;

	private boolean win;
	private boolean lost;

	private TrueTypeFont ttf;
	private TrueTypeFont ttfWin;

	private final String levelPath;

	public DemoLevel(String levelPath) {
		this.levelPath = levelPath;
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		// Load default image
		ResourceHandler.init();

		this.renderer = new Renderer();
		this.screenHanlder = new ScreenHandler(renderer);
		try {
			ModelHandler.get().load("resources/models.data.txt");
		} catch (InputFileNotFoundException | FormatModelException e1) {
			e1.printStackTrace();
		}


		this.renderer.updateContext(screenHanlder.getContext());
		this.renderer.setRewinder(screenHanlder.getRewinder());
		ProjectileHandler.get().setContext(screenHanlder.getContext());

		ttf = new TrueTypeFont(new Font("Verdana", Font.BOLD, 20), true);
		ttfWin = new TrueTypeFont(new Font("Verdana", Font.BOLD, 40), true);

		try {
			this.lvl = LevelLoader.loadLevel(levelPath);

			this.screenHanlder.init(lvl, lvl.getStartingScreen());
		} catch (InputFileNotFoundException e) {
			System.err.println("Cannot load level : "+levelPath);
			e.printStackTrace();
		}

		List<IAction> list = new ArrayList<IAction>();
		list.add(new Waypoint(null, null, null, new Coordinate(100, 0)));
		list.add(new Shoot(null, null, null, new Coordinate(0, -1),750));
		list.add(new Waypoint(null, null, null, new Coordinate(450, 0)));
		list.add(new Shoot(null, null, null, new Coordinate(0, -1),750));
		this.lvl.getStartingScreen().getHandler().getEnemies().iterator().next().setActions(list);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		this.renderer.render(g);
		ttf.drawString(GameConstant.WIDTH*GameConstant.TILE_SIZE-100, 15, "Life : "+lvl.getHero().getLife(), Color.white);
		if ( win ) {
			ttfWin.drawString(GameConstant.WIDTH*GameConstant.TILE_SIZE/2-100, 200, "You won!", Color.red);
		} else if ( lost ) {
			ttfWin.drawString(GameConstant.WIDTH*GameConstant.TILE_SIZE/2-100, 200, "You lost!", Color.red);
		}
	}

	@Override
	public ICore update(GameContainer container, int delta) throws SlickException {
		Hero hero = lvl.getHero();
		elapsedTime += delta;
		if ( hero.getLife() == 0 ) {
			lost = true;
		}
		while ( elapsedTime > timeStep && !win && !lost) {
			elapsedTime -= timeStep;

			hero.getForce().x = forceX;

			screenHanlder.update(timeStep);

			if ( rewind ) {
				screenHanlder.getRewinder().rewind(timeStep, hero);
				rewindAllowed = false;
			} else {
				screenHanlder.getRewinder().record(timeStep, hero);
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
		if ( ( c == 'z' || key == Input.KEY_UP ) && lvl.getHero().isOnGround() ) {
			lvl.getHero().getForce().setY(-250);
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
			lvl.getHero().setDying(false);
		}
	}

}
