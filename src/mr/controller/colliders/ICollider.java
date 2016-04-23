package mr.controller.colliders;

import mr.controller.entity.Hero;

public interface ICollider {
	void collide(Hero hero);

	boolean isActive();
}
