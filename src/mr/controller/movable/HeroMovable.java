package mr.controller.movable;

import mr.model.Hero;

public class HeroMovable extends ItemMovable {

	public HeroMovable(Hero hero) {
		super(hero);
	}

	@Override
	public Hero getMovable() {
		return (Hero)super.getMovable();
	}
}
