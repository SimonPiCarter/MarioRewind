package mr.model;

import mr.model.misc.Coordinate;

public class Sprite {
	private Coordinate position;
	private Coordinate size;
	private String type;
	
	public Sprite(Coordinate position, Coordinate size, String type) {
		this.position = position;
		this.size = size;
		this.type = type;
	}
	public Coordinate getPosition() {
		return position;
	}
	public void setPosition(Coordinate position) {
		this.position = position;
	}
	
	public Coordinate getSize() {
		return size;
	}
	public void setSize(Coordinate size) {
		this.size = size;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
