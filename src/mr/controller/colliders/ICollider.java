package mr.controller.colliders;

import mr.controller.entity.Hero;
import mr.model.misc.Coordinate;

public interface ICollider {
	void collide(Hero hero, Coordinate heroSpeed, Coordinate ownSpeed);
}
