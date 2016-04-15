package mr.view;

import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import mr.controller.Rewinder;
import mr.model.misc.Coordinate;

public class Renderer {
	private RenderingContext context;
	private Rewinder rewinder;


	public void updateContext(RenderingContext context) {
		this.context = context;
	}

	public void render(Graphics g) throws SlickException {
		if ( context != null ) {
			for ( List<RenderingImage> layer : context.getLayersImages() ) {
				for ( RenderingImage image : layer ) {
					draw(g,image);
				}
			}
		}
		if ( rewinder != null ) {
			g.setColor(Color.cyan);
			Coordinate lastPos = null;
			if ( rewinder.getPoints() != null ) {
				for ( Coordinate nextPos : rewinder.getPoints() ) {
					if ( lastPos != null ) {
						g.drawLine(lastPos.x, lastPos.y, nextPos.x, nextPos.y);
					}
					lastPos = nextPos;
				}
			}
		}
	}

	private void draw(Graphics g, RenderingImage image) {
		if ( image.getStartSrc() != null && image.getEndSrc() != null ) {
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
		else {
			g.drawImage(
					ResourceHandler.getImage(image.getImage()),
					image.getPosition().getX(),
					image.getPosition().getY()
					);
		}
	}

	public void setRewinder(Rewinder rewinder) {
		this.rewinder = rewinder;
	}
}
