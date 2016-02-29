package mr.model;

import mr.model.misc.Coordinate;

public class Projectile extends Item {
	private Coordinate direction;
	private double weight;
	
	
	public Projectile(Coordinate position, Coordinate size, String type, String id, String state, Coordinate direction,
			double weight) {
		super(position, size, type, id, state);
		this.setDirection(direction);
		this.setWeight(weight);
	}

	public Coordinate getDirection() {
		return direction;
	}
	public void setDirection(Coordinate direction) {
		this.direction = direction;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
		
}
