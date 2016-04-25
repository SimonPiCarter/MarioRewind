package mr.editor;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import mr.controller.KeyHandler;
import mr.core.ICore;
import mr.editor.gui.ListEntry;
import mr.model.GameConstant.Layers;
import mr.model.Sprite;
import mr.model.misc.Coordinate;
import mr.view.Renderer;
import mr.view.RenderingContext;
import mr.view.ResourceHandler;

public class SpriteEditorMenu implements ICore {

	private Renderer renderer;
	private RenderingContext context;

	private ListEntry entries;

	private boolean validated;
	private boolean back;
	private final ICore previous;

	private TrueTypeFont ttf;
	private String string;
	private Image image;
	private Sprite sprite;

	private Coordinate fullImageSize;
	private Coordinate spriteSize;

	public SpriteEditorMenu(ICore previous, String sprite) {
		this.previous = previous;
		this.string = sprite;
		this.fullImageSize = new Coordinate(300,400);
		this.spriteSize = new Coordinate(300,100);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		renderer = new Renderer();
		context = new RenderingContext();
		renderer.updateContext(context);

		sprite = new Sprite(new Coordinate(300,100+fullImageSize.y), new Coordinate(0,0), string);
		context.addToLayer(Layers.FOREGROUND, sprite);

		ttf = new TrueTypeFont(new Font("Verdana", Font.BOLD, 20), true);
		List<String> list = new ArrayList<String>();
		for ( int i = 0 ; i < ResourceHandler.getRenderingImage(sprite).getNbStates() ; ++ i ) {
			list.add(Integer.toString(i));
		}

		image = new Image(ResourceHandler.getRenderingImage(sprite).getImage());

		float factorX = spriteSize.x/ResourceHandler.getRenderingImage(sprite).getSize().x;
		float factorY = spriteSize.y/ResourceHandler.getRenderingImage(sprite).getSize().y;
		sprite.getSize().x = ResourceHandler.getRenderingImage(sprite).getSize().x*Math.min(factorX, factorY);
		sprite.getSize().y = ResourceHandler.getRenderingImage(sprite).getSize().y*Math.min(factorX, factorY);

		entries = new ListEntry(
				ttf,
				list,
				new Coordinate(50,25+fullImageSize.y),
				new Coordinate(300,150),
				false);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		entries.render(container, g);

		renderer.render(g);

		// Draw full image
		float factorX = fullImageSize.x/image.getWidth();
		float factorY = fullImageSize.y/image.getHeight();
		image.draw(300,25,Math.min(factorX, factorY));


		ttf.drawString(5, container.getHeight()-ttf.getHeight(), "A : Back",new Color(220, 220, 220));
		String newString = "E : Edit";
		ttf.drawString(container.getWidth()-ttf.getWidth(newString)-5, container.getHeight()-ttf.getHeight(), newString,new Color(220, 220, 220));
	}

	@Override
	public ICore update(GameContainer container, int delta) throws SlickException {
		context.update(delta);
		entries.update(container, delta);
		if ( validated ) {
			validated = false;
			back = false;
			sprite.setState(entries.getCurrent());
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
