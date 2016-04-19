package mr.model;

import mr.model.misc.Coordinate;
import mr.model.state.IState;

public class Item extends Sprite {
	private String id;
	private HitBox hitBox;
	//private int state;
	private IState state;

	public Item(Coordinate position, Coordinate size, String type, String id, IState state) {
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
		return state.getState();
	}

	public HitBox getHitBox() {
		return hitBox;
	}

	public void setHitBox(HitBox hitBox) {
		this.hitBox = hitBox;
	}
}
