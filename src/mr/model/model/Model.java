package mr.model.model;

import mr.model.misc.Coordinate;

public class Model {
	private String id;
	private String sprite;
	private Coordinate size;
	private Coordinate hitBoxOffset;
	private Coordinate hitBoxSize;

	public Model() {
		this.size = new Coordinate();
		this.hitBoxOffset = new Coordinate();
		this.hitBoxSize = new Coordinate();
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSprite() {
		return sprite;
	}
	public void setSprite(String sprite) {
		this.sprite = sprite;
	}
	public Coordinate getSize() {
		return size;
	}
	public void setSize(Coordinate size) {
		this.size = size;
	}
	public Coordinate getHitBoxOffset() {
		return hitBoxOffset;
	}
	public void setHitBoxOffset(Coordinate hitBoxOffset) {
		this.hitBoxOffset = hitBoxOffset;
	}
	public Coordinate getHitBoxSize() {
		return hitBoxSize;
	}
	public void setHitBoxSize(Coordinate hitBoxSize) {
		this.hitBoxSize = hitBoxSize;
	}
}
