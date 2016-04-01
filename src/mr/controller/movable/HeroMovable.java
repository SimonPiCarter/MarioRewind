package mr.controller.movable;

import mr.model.Hero;

public class HeroMovable extends AbstractMovable {

	private final Hero hero;

	public HeroMovable(Hero hero) {
		super(hero);
		this.hero = hero;
	}

	public Hero getHero() {
		return hero;
	}
}
