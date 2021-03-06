package mr.core.menu;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import mr.controller.KeyHandler;
import mr.core.ICore;
import mr.editor.EditorMenu;
import mr.editor.gui.ListEntry;
import mr.model.misc.Coordinate;

public class MainMenu implements ICore {

	private ListEntry entries;

	private boolean validated;

	@Override
	public void init(GameContainer container) throws SlickException {
		TrueTypeFont ttf = new TrueTypeFont(new Font("Verdana", Font.BOLD, 40), true);
		List<String> list = new ArrayList<String>();
		list.add("Play");
		list.add("Editor");
		list.add("Exit");
		entries = new ListEntry(
				ttf,
				list,
				new Coordinate(0,50),
				new Coordinate(container.getWidth(),container.getHeight()-100),
				true);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		entries.render(container, g);
	}

	@Override
	public ICore update(GameContainer container, int delta) throws SlickException {
		entries.update(container, delta);
		if ( validated ) {
			validated = false;
			if ( entries.getCurrent() == 1 ) {
				ICore next = new EditorMenu(this);
				next.init(container);
				return next;
			} else if ( entries.getCurrent() == 2 ) {
				container.exit();
			}
		}
		return this;
	}

	@Override
	public void keyPressed(int key, char c) {
		entries.keyPressed(key, c);
	}

	@Override
	public void keyReleased(int key, char c) {
		entries.keyReleased(key, c);
		if ( KeyHandler.isCommand("Ok", key) ) {
			validated = true;
		}
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		entries.MousePressed(button, x, y);
		validated = true;
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		entries.MouseMoved(oldx, oldy, newx, newy);
	}

}
