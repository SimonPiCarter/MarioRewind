package mr.editor.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public interface GUI {

	boolean MousePressed(int button, int x, int y);

	void MouseMoved(int oldX, int oldY, int newX, int newY);

	public void keyPressed(int key, char c);

	public void keyReleased(int key, char c);

	public void render(GameContainer container, Graphics g) throws SlickException;

	public void update(GameContainer container, int delta) throws SlickException;
}
