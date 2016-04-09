package mr.controller.movable;

import mr.model.Hero;

public class HeroMovable extends AbstractMovable {

	private final Hero hero;

	public HeroMovable(Hero hero) {
		super();
		this.hero = hero;
	}

	@Override
	public Hero getSprite() {
		return hero;
	}
}
