package mr.model;

import mr.model.misc.Coordinate;

public class Item extends Sprite {
	private String id;
	private String state;
	
	public Item(Coordinate position, Coordinate size, String type, String id, String state) {
		super(position, size, type);
		this.id = id;
		this.state = state;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
