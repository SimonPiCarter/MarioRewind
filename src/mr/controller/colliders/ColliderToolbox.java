package mr.controller.colliders;

import mr.model.GameConstant;
import mr.model.HitBox;
import mr.model.Item;
import mr.model.misc.Coordinate;
import mr.model.misc.Interval;

public class ColliderToolbox {

	public static boolean collide(Coordinate posA, HitBox hitBoxA, Coordinate posB, HitBox hitBoxB) {
		return isInside(
				posA.x+hitBoxA.offset.x,
				posA.x+hitBoxA.offset.x+hitBoxA.size.x,
				posA.y+hitBoxA.offset.y,
				posA.y+hitBoxA.offset.y+hitBoxA.size.y,
				posB.x+hitBoxB.offset.x,
				posB.x+hitBoxB.offset.x+hitBoxB.size.x,
				posB.y+hitBoxB.offset.y,
				posB.y+hitBoxB.offset.y+hitBoxB.size.y
				);
	}

	public enum Collision {
		NO_COLLISION,
		FIRST_ITEM_KILLED,
		SECOND_ITEM_KILLED
	}

	public static Collision detectKillCollision(Item itemA, Item itemB, Coordinate heroSpeed, Coordinate itemSpeed) {
		Coordinate pos = Coordinate.add(itemA.getPosition(),itemA.getHitBox().offset);
		Coordinate size = itemA.getHitBox().size;
		float leftX = itemB.getPosition().x+itemB.getHitBox().offset.x;
		float rightX = itemB.getPosition().x+itemB.getHitBox().offset.x+itemB.getHitBox().size.x;
		float upY = itemB.getPosition().y+itemB.getHitBox().offset.y;
		float downY = itemB.getPosition().y+itemB.getHitBox().offset.y+itemB.getHitBox().size.y;

		boolean killed = false;
		boolean collision = false;

		if ( new Interval(pos.y,pos.y+size.y).intersect(new Interval(upY,downY)) ) {
			if ( heroSpeed.x-itemSpeed.x > 0 ) {
				if ( pos.x+size.x < leftX && pos.x+size.x+heroSpeed.x > leftX+itemSpeed.x ) {
					collision = true;
				}
			} else if ( heroSpeed.x-itemSpeed.x < 0 ) {
				if ( pos.x > rightX && pos.x+heroSpeed.x < rightX+itemSpeed.x ) {
					collision = true;
				}
			}
		}
		if ( new Interval(pos.x,pos.x+size.x).intersect(new Interval(leftX,rightX)) ) {
			if ( heroSpeed.y-itemSpeed.y > 0 ) {
				if ( pos.y+size.y < upY && pos.y+size.y+heroSpeed.y > upY+itemSpeed.y ) {
					heroSpeed.y = Math.max(0,upY-pos.y-size.y-GameConstant.epsilon);
					killed = true;
					collision = true;
				}
			} else if ( heroSpeed.y-itemSpeed.y < 0 ) {
				if ( pos.y > downY && pos.y+heroSpeed.y < downY+itemSpeed.y ) {
					heroSpeed.y = Math.min(0,downY-pos.y+GameConstant.epsilon);
					collision = true;
				}
			}
		}
		if ( collision ) {
			if ( killed ) {
				return Collision.SECOND_ITEM_KILLED;
			} else {
				return Collision.FIRST_ITEM_KILLED;
			}
		}
		return Collision.NO_COLLISION;
	}

	public static boolean isInside(float leftX,float rightX,float upY,float downY, Coordinate pos, Coordinate size) {
		return isInside(leftX, rightX, upY, downY, pos.x, pos.x+size.x,pos.y,pos.y+size.y);
	}

	public static boolean isInside(float leftX,float rightX,float upY,float downY,
			float leftXB,float rightXB,float upYB,float downYB) {

		if ( isInside(leftX,rightX,upY,downY,leftXB,upYB)
				|| isInside(leftX,rightX,upY,downY,rightXB,upYB)
				|| isInside(leftX,rightX,upY,downY,rightXB,downYB)
				|| isInside(leftX,rightX,upY,downY,leftXB,downYB) ) {
			return true;
		}
		return false;
	}

	public static boolean isInside(float leftX,float rightX,float upY,float downY,float x, float y) {
		return (leftX < x && rightX > x && upY < y && downY > y);
	}
}
