package mr.controller.movable;

import mr.controller.colliders.ColliderToolbox;
import mr.model.GameConstant;
import mr.model.Item;
import mr.model.Screen;
import mr.model.misc.Coordinate;
import mr.model.model.Model;
import mr.model.state.AbstractState;
import mr.model.state.AbstractState.StateEvent;

public class Movable extends Item {

	private Coordinate speed;
	private float weight;
	private Coordinate force;
	private boolean onGround;
	private boolean touchedLeftScreen;
	private boolean touchedRightScreen;
	private boolean touchedUpScreen;
	private boolean touchedBottomScreen;

	public Movable(Coordinate position, Model model, String id, AbstractState state) {
		super(position, model, id, state);
		speed = new Coordinate(0, 0);
		weight = 1;
		force = new Coordinate(0, 0);
	}

	/**
	 * Move accordingly to the screen tiles
	 * @param screen
	 */
	public void move(Screen screen) {

		Coordinate pos = getPosition();
		Coordinate size = getSize();

		for ( int i = 0 ; i < GameConstant.WIDTH ; ++ i ) {
			for ( int j = 0 ; j < GameConstant.HEIGHT ; ++ j ) {
				if ( screen.getTiles()[i+j*GameConstant.WIDTH] > 0 && screen.getTiles()[i+j*GameConstant.WIDTH] < screen.getNbTiles()+1 ) {
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

		getPosition().add(speed);
	}

	private void adjustPositionToTile(int x, int y, Coordinate speed) {
		float leftX = x*GameConstant.TILE_SIZE;
		float rightX = (x+1)*GameConstant.TILE_SIZE;
		float upY = y*GameConstant.TILE_SIZE;
		float downY = (y+1)*GameConstant.TILE_SIZE;

		ColliderToolbox.adjustPositionToTile(this, leftX, rightX, upY, downY);
	}

	/**
	 * Compute acceleration and new speed
	 * @param time time elapsed since last frame
	 */
	public void updateSpeed() {
		if ( !onGround ) {
			force.x /= 5;
		}
		// Compute new speed
		speed.x = speed.x + force.x/weight*0.0075f;
		float slow = 0;
		// Compute floor resistance
		if ( onGround && speed.x > 0  ) {
			slow = speed.x/10;
			speed.x = Math.max(0, speed.x-slow);
		}
		if ( onGround && speed.x < 0  ) {
			slow = speed.x/10;
			speed.x = Math.min(0, speed.x-slow);
		}
		if ( speed.x > 0 &&speed.x > GameConstant.HORIZONTAL_LIMIT ) {
			speed.x = Math.max(GameConstant.HORIZONTAL_LIMIT , speed.x*0.9f);
		}
		else if ( speed.x < 0 && speed.x < -GameConstant.HORIZONTAL_LIMIT ) {
			speed.x = Math.min(-GameConstant.HORIZONTAL_LIMIT , speed.x*0.9f);
		}
		speed.y = speed.y + force.y/weight*0.0075f;
		if ( speed.y > GameConstant.VERTICAL_LIMIT ) {
			speed.y = GameConstant.VERTICAL_LIMIT;
		}
		// Reset force to weight only
		force.x = 0;
		force.y = weight*2;
		updateState();
	}

	public void updateState() {
		if ( getSpeed().x > GameConstant.epsilon ) {
			updateState(StateEvent.MoveRight);
		} else if ( getSpeed().x < -GameConstant.epsilon ) {
			updateState(StateEvent.MoveLeft);
		} else {
			updateState(StateEvent.Stop);
		}
		if ( getSpeed().y > GameConstant.epsilon ) {
			updateState(StateEvent.Fall);
		} else if ( getSpeed().y < -GameConstant.epsilon ) {
			updateState(StateEvent.Jump);
		}
		if ( isDying() ) {
			updateState(StateEvent.Die);
		}
	}

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
