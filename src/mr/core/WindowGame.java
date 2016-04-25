package mr.core;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import mr.controller.ModelHandler;
import mr.core.exception.FormatModelException;
import mr.core.exception.InputFileNotFoundException;
import mr.core.menu.MainMenu;
import mr.view.ResourceHandler;

public class WindowGame extends BasicGame {

	ICore core;

	public WindowGame() {
		super("Mario Rewind");
		//core = new DemoLevel("resources/level.lvl.txt");
		core = new MainMenu();
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		// Load default image
		ResourceHandler.init();
		try {
			ModelHandler.get().load("resources/models.data.txt");
		} catch (InputFileNotFoundException | FormatModelException e1) {
			e1.printStackTrace();
		}
		core.init(container);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		core.render(container, g);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		core = core.update(container, delta);
	}

	public static void main(String[] args) throws SlickException {
		new AppGameContainer(new WindowGame(), 640, 640, false).start();
	}

	@Override
	public void keyPressed(int key, char c) {
		core.keyPressed(key, c);
	}

	@Override
	public void keyReleased(int key, char c) {
		core.keyReleased(key, c);
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		core.mousePressed(button, x, y);
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		core.mouseMoved(oldx, oldy, newx, newy);
	}
}

