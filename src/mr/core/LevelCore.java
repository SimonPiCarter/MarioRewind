package mr.core;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import mr.controller.EntityHandler;
import mr.controller.Rewinder;
import mr.model.Level;
import mr.view.Renderer;
import mr.view.RenderingContext;

public class LevelCore implements ICore {

	private RenderingContext context;
	private Rewinder rewinder;

	private Renderer renderer;

	private Level lvl;

	private float baseForce = 15;
	private float forceX = 0;
	private boolean rewind;
	private boolean rewindAllowed;

	private EntityHandler handler;

	private TrueTypeFont ttf;

	private int timeStep = 5;
	private int elapsedTime = 0;

	@Override
	public void init(GameContainer container) throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public ICore update(GameContainer container, int delta) throws SlickException {
		return null;
	}

	@Override
	public void keyPressed(int key, char c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(int key, char c) {
		// TODO Auto-generated method stub

	}

}
