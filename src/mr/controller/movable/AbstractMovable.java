package mr.controller.movable;

import mr.model.Screen;
import mr.model.Sprite;
import mr.model.misc.Coordinate;

public abstract class AbstractMovable {
	private Coordinate speed;
	private float weight;
	private Coordinate force;

	private final Sprite item;

	public AbstractMovable(Sprite item) {
		this.item = item;
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

	/**
	 * Move accordingly to the screen tiles
	 * @param screen
	 */
	public void move(Screen screen) {
		item.getPosition().add(speed);
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
