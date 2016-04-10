package mr.model;

import mr.model.misc.Coordinate;

public class Item extends Sprite {
	private String id;
	private int state;

	public Item(Coordinate position, Coordinate size, String type, String id, int state) {
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

	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
}
