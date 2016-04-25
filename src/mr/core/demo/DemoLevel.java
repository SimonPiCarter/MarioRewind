package mr.core.demo;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import mr.controller.KeyHandler;
import mr.controller.LevelLoader;
import mr.controller.ModelHandler;
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

	private boolean won;
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
		try {
			ModelHandler.get().load("resources/models.data.txt");
		} catch (InputFileNotFoundException | FormatModelException e1) {
			e1.printStackTrace();
		}


		ttf = new TrueTypeFont(new Font("Verdana", Font.BOLD, 20), true);
		ttfWin = new TrueTypeFont(new Font("Verdana", Font.BOLD, 40), true);

		loadLevel(renderer);
	}

	private void loadLevel(Renderer renderer) {
		try {
			this.lvl = LevelLoader.loadLevel(levelPath);

			this.screenHanlder = new ScreenHandler(renderer, lvl.getStartingScreen(), lvl);

			this.screenHanlder.init();
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
		screenHanlder.render(g);
		ttf.drawString(GameConstant.WIDTH*GameConstant.TILE_SIZE-100, 15, "Life : "+lvl.getHero().getLife(), Color.white);
		if ( won ) {
			ttfWin.drawString(GameConstant.WIDTH*GameConstant.TILE_SIZE/2-100, 200, "You won!", Color.red);
			ttf.drawString(GameConstant.WIDTH*GameConstant.TILE_SIZE/2-50, 250, "Y : replay", Color.white);
		} else if ( lost ) {
			ttfWin.drawString(GameConstant.WIDTH*GameConstant.TILE_SIZE/2-100, 200, "You lost!", Color.red);
			ttf.drawString(GameConstant.WIDTH*GameConstant.TILE_SIZE/2-50, 250, "Y : retry", Color.white);
		}
	}

	@Override
	public ICore update(GameContainer container, int delta) throws SlickException {
		Hero hero = lvl.getHero();
		elapsedTime += delta;
		if ( hero.getLife() == 0 ) {
			lost = true;
		}
		while ( elapsedTime > timeStep && !won && !lost) {
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
		// Update screen if necessary
		if ( hero.isTouchedRightScreen() ) {
			if ( screenHanlder.getScreen().getRight() == null) {
				won = true;
			} else {
				screenHanlder = screenHanlder.getToRightScreen();
			}
		}
		if ( hero.isTouchedLeftScreen() ) {
			if ( screenHanlder.getScreen().getLeft() != null) {
				screenHanlder = screenHanlder.getToLeftScreen();
			}
		}
		if ( hero.isTouchedBottomScreen() && screenHanlder.getScreen().getBottom() == null) {
			lost = true;
		}

		return this;
	}

	@Override
	public void keyPressed(int key, char c) {
		if ( ( KeyHandler.isCommand("Up", key) ) && lvl.getHero().isOnGround() ) {
			lvl.getHero().getForce().setY(-250);
		}
		if ( KeyHandler.isCommand("Left", key) ) {
			forceX = -baseForce;
		}
		if ( KeyHandler.isCommand("Right", key) ) {
			forceX = baseForce;
		}
		if ( KeyHandler.isCommand("Rewind", key) ) {
			rewind = rewindAllowed;
		}
	}

	@Override
	public void keyReleased(int key, char c) {
		if ( KeyHandler.isCommand("Left", key) ) {
			if ( forceX < 0 ) {
				forceX = 0;
			}
		}
		if ( KeyHandler.isCommand("Right", key) ) {
			if ( forceX > 0 ) {
				forceX = 0;
			}
		}
		if ( KeyHandler.isCommand("Rewind", key) ) {
			rewind = false;
		}
		if (KeyHandler.isCommand("Retry", key) && ( lost || won ) ) {
			loadLevel(renderer);
			lost = false;
			won = false;
			elapsedTime = 0;
		}
	}

	@Override
	public void mousePressed(int button, int x, int y) {

	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {

	}

}
