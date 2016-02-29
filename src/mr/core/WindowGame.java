package mr.core;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import mr.view.Renderer;
import mr.view.ResourceHandler;

public class WindowGame extends BasicGame {
	private Renderer renderer;
	
    public WindowGame() {
        super("Core :: WindowGame");
    }

    @Override
    public void init(GameContainer container) throws SlickException {
    	// Load default image
    	ResourceHandler.init();
    	
    	this.renderer = new Renderer();
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
    	this.renderer.render(g);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
    	
    }
    
    public static void main(String[] args) throws SlickException {
        new AppGameContainer(new WindowGame(), 640, 480, false).start();
    }
}

