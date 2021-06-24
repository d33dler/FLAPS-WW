package nl.andrewlalis.physics;

/**
 * A 2-dimensional vector.
 */
public class Vec2 {
	public double x;
	public double y;

	public Vec2() {
		this(0, 0);
	}

	public Vec2(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double mag() {
		return Math.sqrt(x * x + y * y);
	}

	public double dot(Vec2 other) {
		return x * other.x + y * other.y;
	}

	public void acc(Vec2 other) {
		this.x += other.x;
		this.y += other.y;
	}

	public Vec2 add(Vec2 other) {
		return new Vec2(x + other.x, y + other.y);
	}

	public Vec2 sub(Vec2 other) {
		return new Vec2(x - other.x, y - other.y);
	}

	public Vec2 mul(double factor) {
		return new Vec2(x * factor, y * factor);
	}

	public Vec2 div(double factor) {
		return new Vec2(x / factor, y / factor);
	}

	public Vec2 norm() {
		return this.div(this.mag());
	}

	public double hyp(Vec2 other) {
		return Math.sqrt(Math.pow(other.x - x, 2) + Math.pow(other.y - y, 2));
	}

	public double angleTo(Vec2 other) {
		return Math.atan2(other.y - y, other.x - x);
	}

	@Override
	public String toString() {
		return "Vec2{x=" + x + ", y=" + y + "}";
	}
}
