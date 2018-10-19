package project;

public class Entity {
	private double mass;
	private double downForce;
	private double upForce;
	private double rightForce;
	private double leftForce;

	public Entity() {

	}

	public Entity(double mass, double downForce, double upForce, double leftForce, double rightForce) {
		this.mass = mass;
		this.downForce = downForce;
		this.upForce = upForce;
		this.leftForce = leftForce;
		this.rightForce = rightForce;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public double getMass() {
		return this.mass;
	}

	public void setDownForce(double downForce) {
		this.downForce = downForce;
	}

	public double getDownForce() {
		return this.downForce;
	}

	public void setUpForce(double upForce) {
		this.upForce = upForce;
	}

	public double getUpForce() {
		return this.upForce;
	}

	public void setRightForce(double rightForce) {
		this.rightForce = rightForce;
	}

	public double getRightForce() {
		return this.rightForce;
	}

	public void setLeftForce(double leftForce) {
		this.leftForce = leftForce;
	}

	public double getLeftForce() {
		return this.leftForce;
	}
	
}
