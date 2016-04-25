package mr.editor;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import mr.controller.KeyHandler;
import mr.core.ICore;
import mr.editor.gui.ListEntry;
import mr.model.misc.Coordinate;
import mr.view.ResourceHandler;

public class SpriteMenu implements ICore {

	private ListEntry entries;

	private boolean validated;
	private boolean back;
	private final ICore previous;

	private TrueTypeFont ttf;

	public SpriteMenu(ICore previous) {
		this.previous = previous;
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		ttf = new TrueTypeFont(new Font("Verdana", Font.BOLD, 40), true);
		List<String> list = new ArrayList<String>();
		list.addAll(ResourceHandler.getRenderingImages().keySet());

		entries = new ListEntry(
				ttf,
				list,
				new Coordinate(100,50),
				new Coordinate(container.getWidth(),container.getHeight()-100),
				false);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		entries.render(container, g);
		ttf.drawString(5, container.getHeight()-ttf.getHeight(), "A : Back",new Color(220, 220, 220));
		String newString = "N : New";
		ttf.drawString(container.getWidth()-ttf.getWidth(newString)-5, container.getHeight()-ttf.getHeight(), newString,new Color(220, 220, 220));
	}

	@Override
	public ICore update(GameContainer container, int delta) throws SlickException {
		entries.update(container, delta);
		if ( validated ) {
			validated = false;
			back = false;
		}
		if ( back ) {
			validated = false;
			back = false;
			return previous;
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
		if ( KeyHandler.isCommand("Cancel", key) ) {
			back = true;
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
