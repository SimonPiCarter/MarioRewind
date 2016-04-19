package mr.controller.movable;

import mr.model.GameConstant;
import mr.model.Screen;
import mr.model.Sprite;
import mr.model.misc.Coordinate;
import mr.model.misc.Interval;

public abstract class AbstractMovable {
	private Coordinate speed;
	private float weight;
	private Coordinate force;
	private boolean onGround;
	private boolean touchedLeftScreen;
	private boolean touchedRightScreen;
	private boolean touchedUpScreen;
	private boolean touchedBottomScreen;

	public AbstractMovable() {
		speed = new Coordinate(0, 0);
		weight = 1;
		force = new Coordinate(0, 0);
	}

	public abstract Sprite getMovable();

	public Coordinate getSpeed() {
		return speed;
	}
	public void setSpeed(Coordinate speed) {
		this.speed = speed;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public Coordinate getForce() {
		return force;
	}
	public void setForce(Coordinate force) {
		this.force = force;
	}

	public boolean isOnGround() {
		return onGround;
	}

	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}

	/**
	 * Move accordingly to the screen tiles
	 * @param screen
	 */
	public void move(Screen screen) {

		Coordinate pos = getMovable().getPosition();
		Coordinate size = getMovable().getSize();

		for ( int i = 0 ; i < GameConstant.WIDTH ; ++ i ) {
			for ( int j = 0 ; j < GameConstant.HEIGHT ; ++ j ) {
				if ( screen.getTiles()[i+j*GameConstant.WIDTH] > 0 ) {
					adjustPositionToTile(i,j,speed);
				}
			}
		}

		// Check on side of the board
		if ( speed.x > 0 ) {
			if ( pos.x+size.x+speed.x > GameConstant.WIDTH*GameConstant.TILE_SIZE ) {
				speed.x = Math.max(0,GameConstant.WIDTH*GameConstant.TILE_SIZE-pos.x-size.x-GameConstant.epsilon);
				touchedRightScreen = true;
			}
		} else if ( speed.x < 0 ) {
			if ( pos.x+speed.x < 0 ) {
				speed.x = Math.min(0,-pos.x+GameConstant.epsilon);
				touchedLeftScreen = true;
			}
		}
		// Check
		if ( speed.y > 0 ) {
			if ( pos.y+size.y+speed.y > GameConstant.HEIGHT*GameConstant.TILE_SIZE ) {
				speed.y = Math.max(0,GameConstant.HEIGHT*GameConstant.TILE_SIZE-pos.x-size.x-GameConstant.epsilon);
				touchedBottomScreen = true;
			}
		} else if ( speed.y < 0 ) {
			if ( pos.y+speed.y < 0 ) {
				speed.y = Math.min(0,-pos.y+GameConstant.epsilon);
				touchedUpScreen = true;
			}
		}

		if ( Math.abs(speed.y) > GameConstant.epsilon ) {
			this.onGround = false;
		}

		getMovable().getPosition().add(speed);
	}

	private void adjustPositionToTile(int x, int y, Coordinate speed) {
		Coordinate pos = getMovable().getPosition();
		Coordinate size = getMovable().getSize();
		float leftX = x*GameConstant.TILE_SIZE;
		float rightX = (x+1)*GameConstant.TILE_SIZE;
		float upY = y*GameConstant.TILE_SIZE;
		float downY = (y+1)*GameConstant.TILE_SIZE;

		if ( new Interval(pos.y,pos.y+size.y).intersect(new Interval(upY,downY)) ) {
			if ( speed.x > 0 ) {
				if ( pos.x+size.x < leftX && pos.x+size.x+speed.x > leftX ) {
					speed.x = Math.max(0,leftX-pos.x-size.x-GameConstant.epsilon);

				}
			} else if ( speed.x < 0 ) {
				if ( pos.x > rightX && pos.x+speed.x < rightX ) {
					speed.x = Math.min(0,rightX-pos.x+GameConstant.epsilon);
				}
			}
		}
		if ( new Interval(pos.x,pos.x+size.x).intersect(new Interval(leftX,rightX)) ) {
			if ( speed.y > 0 ) {
				if ( pos.y+size.y < upY && pos.y+size.y+speed.y > upY ) {
					speed.y = Math.max(0,upY-pos.y-size.y-GameConstant.epsilon);
					this.onGround = true;
				}
			} else if ( speed.y < 0 ) {
				if ( pos.y > downY && pos.y+speed.y < downY ) {
					speed.y = Math.min(0,downY-pos.y+GameConstant.epsilon);
				}
			}
		}
	}

	/**
	 * Compute acceleration and new speed
	 * @param time time elapsed since last frame
	 */
	public void updateSpeed() {
		// Compute new speed
		speed.x = speed.x + force.x/weight*0.0075f;
		if ( speed.x > GameConstant.HORIZONTAL_LIMIT ) {
			speed.x *= 0.9f;
		}
		speed.y = speed.y + force.y/weight*0.0075f;
		if ( speed.y > GameConstant.VERTICAL_LIMIT ) {
			speed.y = GameConstant.VERTICAL_LIMIT;
		}
		// Reset force to weight only
		force.x = 0;
		force.y = weight*2;
	}

	public boolean isTouchedLeftScreen() {
		return touchedLeftScreen;
	}
	public void setTouchedLeftScreen(boolean touchedLeftScreen) {
		this.touchedLeftScreen = touchedLeftScreen;
	}
	public boolean isTouchedRightScreen() {
		return touchedRightScreen;
	}
	public void setTouchedRightScreen(boolean touchedRightScreen) {
		this.touchedRightScreen = touchedRightScreen;
	}
	public boolean isTouchedUpScreen() {
		return touchedUpScreen;
	}
	public void setTouchedUpScreen(boolean touchedUpScreen) {
		this.touchedUpScreen = touchedUpScreen;
	}
	public boolean isTouchedBottomScreen() {
		return touchedBottomScreen;
	}
	public void setTouchedBottomScreen(boolean touchedBottomScreen) {
		this.touchedBottomScreen = touchedBottomScreen;
	}


}
