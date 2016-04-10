package mr.controller.movable;

import mr.model.Item;

public class ItemMovable extends AbstractMovable {

	private final Item item;

	public ItemMovable(Item sprite) {
		super();
		this.item = sprite;
	}

	@Override
	public Item getMovable() {
		return item;
	}
}
