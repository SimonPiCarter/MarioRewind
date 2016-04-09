package mr.controller.movable;

import mr.model.Sprite;

public class SpriteMovable extends AbstractMovable {

	private final Sprite sprite;

	public SpriteMovable(Sprite sprite) {
		super();
		this.sprite = sprite;
	}

	@Override
	public Sprite getSprite() {
		return sprite;
	}
}
