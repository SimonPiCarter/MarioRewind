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

	private static float epsilon = 1e-2f;

	public AbstractMovable() {
		speed = new Coordinate(0, 0);
		weight = 1;
		force = new Coordinate(0, 0);
	}

	public abstract Sprite getSprite();

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

	/**
	 * Move accordingly to the screen tiles
	 * @param screen
	 */
	public void move(Screen screen) {

		for ( int i = 0 ; i < GameConstant.WIDTH ; ++ i ) {
			for ( int j = 0 ; j < GameConstant.HEIGHT ; ++ j ) {
				if ( screen.getTiles()[i+j*GameConstant.WIDTH] != 0 ) {
					adjustPositionToTile(i,j,speed);
				}
			}
		}

		getSprite().getPosition().add(speed);
	}

	private void adjustPositionToTile(int x, int y, Coordinate speed) {
		Coordinate pos = getSprite().getPosition();
		Coordinate size = getSprite().getSize();
		float leftX = x*GameConstant.TILE_SIZE;
		float rightX = (x+1)*GameConstant.TILE_SIZE;
		float upY = y*GameConstant.TILE_SIZE;
		float downY = (y+1)*GameConstant.TILE_SIZE;

		if ( new Interval(pos.y,pos.y+size.y).intersect(new Interval(upY,downY)) ) {
			if ( speed.x > 0 ) {
				if ( pos.x+size.x < leftX && pos.x+size.x+speed.x > leftX ) {
					speed.x = Math.max(0,leftX-pos.x-size.x-epsilon);
					System.out.println("1speed.x="+speed.x);
				}
			} else if ( speed.x < 0 ) {
				if ( pos.x > rightX && pos.x+speed.x < rightX ) {
					speed.x = Math.min(0,rightX-pos.x+epsilon);
					System.out.println("2speed.x="+speed.x);
				}
			}
		}
		if ( new Interval(pos.x,pos.x+size.x).intersect(new Interval(leftX,rightX)) ) {
			if ( speed.y > 0 ) {
				if ( pos.y+size.y < upY && pos.y+size.y+speed.y > upY ) {
					speed.y = Math.max(0,upY-pos.y-size.y-epsilon);
					System.out.println("1speed.y="+speed.y);
				}
			} else if ( speed.y < 0 ) {
				if ( pos.y > downY && pos.y+speed.y < downY ) {
					speed.y = Math.min(0,downY-pos.y+epsilon);
					System.out.println("2speed.y="+speed.y);
				}
			}
		}
	}

	/**
	 * Compute acceleration and new speed
	 * @param time time elapsed since last frame
	 */
	public void updateSpeed(float time) {
		// Compute new speed
		speed.x = speed.x + force.x/weight*time;
		speed.y = speed.y + force.y/weight*time;
		// Reset force to weight only
		force.x = 0;
		force.y = -weight;
	}


}
