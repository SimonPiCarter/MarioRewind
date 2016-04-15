package mr.model;

import mr.model.misc.Coordinate;

public class HitBox {
	public Coordinate offset;
	public Coordinate size;

	public HitBox() {
		offset = new Coordinate();
		size = new Coordinate();
	}

	public HitBox(Coordinate offset, Coordinate size) {
		this.offset = offset;
		this.size = size;
	}
}
