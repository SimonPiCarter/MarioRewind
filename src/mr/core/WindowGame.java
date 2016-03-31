package mr.core;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import mr.controller.LevelLoader;
import mr.core.exception.InputFileNotFoundException;
import mr.model.Level;
import mr.view.Renderer;
import mr.view.RenderingContext;
import mr.view.ResourceHandler;
import mr.view.screen.ScreenRenderer;

public class WindowGame extends BasicGame {
	private Renderer renderer;
	
	private RenderingContext context;
	
    public WindowGame() {
        super("Core :: WindowGame");
    }

    @Override
    public void init(GameContainer container) throws SlickException {
    	// Load default image
    	ResourceHandler.init();
    	
    	this.renderer = new Renderer();
    	this.context = new RenderingContext(null);
    	String level = "level.lvl.txt";
        try {
			Level lvl = LevelLoader.loadLevel(level);
			
			ScreenRenderer.addScreenToContext(context, lvl.getStartingScreen(), lvl);
		} catch (InputFileNotFoundException e) {
			System.err.println("Cannot load level : "+level);
			e.printStackTrace();
		}
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
    	this.renderer.update(context);
    	this.renderer.render(g);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
    	
    }
    
    public static void main(String[] args) throws SlickException {
        new AppGameContainer(new WindowGame(), 640, 640, false).start();
    }
}

