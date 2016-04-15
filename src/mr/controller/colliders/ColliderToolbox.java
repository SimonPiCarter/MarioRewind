package mr.controller.colliders;

import mr.model.misc.Coordinate;

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
