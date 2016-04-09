package mr.model.misc;

public class Interval {
	public float lowerBound,upperBound;

	public Interval(float x, float y) {
		this.lowerBound = x;
		this.upperBound = y;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new Interval(lowerBound,upperBound);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(lowerBound);
		result = prime * result + Float.floatToIntBits(upperBound);
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
		Interval other = (Interval) obj;
		if (Float.floatToIntBits(lowerBound) != Float.floatToIntBits(other.lowerBound)) {
			return false;
		}
		if (Float.floatToIntBits(upperBound) != Float.floatToIntBits(other.upperBound)) {
			return false;
		}
		return true;
	}

	public boolean intersect(Interval other) {
		float maxLower = Math.max(lowerBound, other.lowerBound);
		float minUpper = Math.min(upperBound, other.upperBound);

		return maxLower < minUpper;
	}

	public float getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(float lowerBound) {
		this.lowerBound = lowerBound;
	}

	public float getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(float upperBound) {
		this.upperBound = upperBound;
	}
}
