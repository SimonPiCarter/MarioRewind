package mr.core;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public interface ICore {
	void init(GameContainer container) throws SlickException;

	public void render(GameContainer container, Graphics g) throws SlickException;

	public ICore update(GameContainer container, int delta) throws SlickException;

	public void keyPressed(int key, char c);

	public void keyReleased(int key, char c);
}
