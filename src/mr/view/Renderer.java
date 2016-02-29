package mr.view;

import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Renderer {
	private RenderingContext context;
	
	public void update(RenderingContext context) {
		this.context = context;
	}

	public void render(Graphics g) throws SlickException {
		for ( List<RenderingImage> layer : context.getLayers() ) {
			for ( RenderingImage image : layer ) {
				draw(g,image);
			}
		}
	}
	
	private void draw(Graphics g, RenderingImage image) {
		g.drawImage(
				ResourceHandler.getImage(image.getImage()), 
				image.getPosition().getX(), 
				image.getPosition().getY(),
				image.getPosition().getX()+image.getSize().getX(), 
				image.getPosition().getY()+image.getSize().getY(),
				image.getStartSrc().getX(),
				image.getStartSrc().getY(),
				image.getEndSrc().getX(),
				image.getEndSrc().getY()
				);
	}
}
