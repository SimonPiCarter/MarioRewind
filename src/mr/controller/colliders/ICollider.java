package mr.controller.colliders;

import mr.controller.movable.HeroMovable;
import mr.model.misc.Coordinate;

public interface ICollider {
	void collide(HeroMovable heroMovable, Coordinate heroSpeed, Coordinate ownSpeed);
}
