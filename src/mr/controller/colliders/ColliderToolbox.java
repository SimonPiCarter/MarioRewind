package mr.controller.colliders;

import mr.model.misc.Coordinate;

public class ColliderToolbox {


	public static boolean isInside(float leftX,float rightX,float upY,float downY, Coordinate pos, Coordinate size) {
		if ( isInside(leftX,rightX,upY,downY,pos.x,pos.y)
				|| isInside(leftX,rightX,upY,downY,pos.x+size.x,pos.y)
				|| isInside(leftX,rightX,upY,downY,pos.x+size.x,pos.y+pos.y)
				|| isInside(leftX,rightX,upY,downY,pos.x,pos.y) ) {
			return true;
		}
		return false;
	}

	public static boolean isInside(float leftX,float rightX,float upY,float downY,float x, float y) {
		return (leftX < x && rightX > x && upY < y && downY > y);
	}
}
