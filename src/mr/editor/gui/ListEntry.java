package mr.editor.gui;

import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import mr.controller.KeyHandler;
import mr.model.misc.Coordinate;

public class ListEntry implements GUI {

	private static final int firstThreshold = 500;
	private static final int thresholds = 100;

	private final TrueTypeFont ttf;
	private final List<String> entries;
	private final Coordinate pos;
	private final Coordinate size;
	private final int interval;
	private final boolean centered;

	private int highlighted;
	private int current;
	private int offset;

	private boolean upPressed;
	private boolean upFirstPressed;
	private int upTime;
	private boolean downPressed;
	private boolean downFirstPressed;
	private int downTime;

	public ListEntry(TrueTypeFont ttf, List<String> entries, Coordinate pos, Coordinate size, boolean centered) {
		this.ttf = ttf;
		this.centered = centered;
		this.entries = entries;
		this.pos = pos;
		this.size = size;
		this.interval = (int) (size.y/ttf.getHeight());
		this.highlighted = -1;
		this.current = 0;
		this.offset = 0;
	}

	@Override
	public boolean MousePressed(int button, int x, int y) {
		boolean inside = isInside(x, y);
		if ( inside ) {
			int newCurrent = (int) ((y-pos.y)/ttf.getHeight());
			if ( newCurrent < entries.size() ) {
				current = newCurrent;
			}
		}
		return inside;
	}

	@Override
	public void MouseMoved(int oldX, int oldY, int newX, int newY) {
		if ( isInside(newX, newY) ) {
			highlighted = (int) ((newY-pos.y)/ttf.getHeight());
		} else {
			highlighted = -1;
		}
	}

	@Override
	public void keyPressed(int key, char c) {
		if ( KeyHandler.isCommand("Up", key) ) {
			upPressed = true;
			upFirstPressed = false;
			upTime = 0;
			current = Math.max(current-1, 0);
		} else if ( KeyHandler.isCommand("Down", key) ) {
			downPressed = true;
			downFirstPressed = false;
			downTime = 0;
			current = Math.min(current+1, entries.size()-1);
		}
	}

	@Override
	public void keyReleased(int key, char c) {
		if ( KeyHandler.isCommand("Up", key) ) {
			upPressed = false;
		} else if ( KeyHandler.isCommand("Down", key) ) {
			downPressed = false;
		}
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {

		float posY = pos.y;
		int index = 0;
		for ( String entry : entries ) {
			if ( index < offset ) {
				++index;
				continue;
			}
			float posX = pos.x;
			if ( centered ) {
				posX += size.x/2-ttf.getWidth(entry)/2;
			}
			if ( index == current ) {
				ttf.drawString(posX, posY, entry, Color.darkGray);
			} else if ( index == highlighted ) {
				ttf.drawString(posX, posY, entry, Color.gray);
			}else {
				ttf.drawString(posX, posY, entry, new Color(220, 220, 220));
			}
			posY += ttf.getHeight();
			++index;
			if ( index > interval+offset ) {
				break;
			}
		}
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		if ( upPressed ) {
			upTime += delta;
			if ( upTime > firstThreshold && !upFirstPressed ) {
				upTime -= firstThreshold;
				upFirstPressed = true;
				current = Math.max(current-1, 0);
			}
			while ( upFirstPressed && upTime > thresholds ) {
				upTime -= thresholds;
				current = Math.max(current-1, 0);
			}
		} else if ( downPressed ) {
			downTime += delta;
			if ( downTime > firstThreshold && !downFirstPressed ) {
				downTime -= firstThreshold;
				downFirstPressed = true;
				current = Math.min(current+1, entries.size()-1);
			}
			while ( downFirstPressed && downTime > thresholds ) {
				downTime -= thresholds;
				current = Math.min(current+1, entries.size()-1);
			}
		}
		if ( current >= offset+interval ) {
			offset = current-interval;
		}
		if ( current < offset ) {
			offset = current;
		}
	}

	public int getCurrent() {
		return current;
	}

	public String getCurrentSring() {
		return entries.get(current);
	}

	private boolean isInside(int x, int y) {
		boolean inside = x >= pos.x && x <= pos.x+size.x && y >= pos.y && y <= pos.y+size.y;
		return inside;
	}

}
