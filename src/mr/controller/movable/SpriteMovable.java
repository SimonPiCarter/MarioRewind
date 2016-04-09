package mr.controller.movable;

import mr.model.Hero;
import mr.model.Sprite;

public class SpriteMovable extends AbstractMovable {

	private final Sprite sprite;

	public SpriteMovable(Hero sprite) {
		super();
		this.sprite = sprite;
	}

	@Override
	public Sprite getSprite() {
		return sprite;
	}
}
