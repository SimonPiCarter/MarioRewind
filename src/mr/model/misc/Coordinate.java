package mr.model.misc;

public class Coordinate {
	public float x,y;

	public Coordinate() {
		this(0,0);
	}

	public Coordinate(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Coordinate(Coordinate other) {
		this(other.x,other.y);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new Coordinate(x,y);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Coordinate other = (Coordinate) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x)) {
			return false;
		}
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y)) {
			return false;
		}
		return true;
	}

	public Coordinate add(Coordinate other) {
		x += other.x;
		y += other.y;
		return this;
	}

	public static Coordinate add(Coordinate A, Coordinate B) {
		return new Coordinate(A.x+B.x,A.y+B.y);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Coordinate :["+x+","+y+"]";
	}

}
