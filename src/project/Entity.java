package project;

/**
 * An entity that is created when a rectangle2d is made. It stores data that the user
 * can set and then the program uses to solve the problem.
 */
public class Entity {
    /**
     * Variables of values that the entity can have.
     */
	private double mass;
	private double downForce;
	private double upForce;
	private double rightForce;
	private double leftForce;

    /**
     * Default constructor for the entity.
     */
	public Entity() {

	}

    /**
     * Constructor for an entity that sets data for it when it is made.
     */
	public Entity(double mass, double downForce, double upForce, double leftForce, double rightForce) {
		this.mass = mass;
		this.downForce = downForce;
		this.upForce = upForce;
		this.leftForce = leftForce;
		this.rightForce = rightForce;
	}

    /**
     * Sets the mass of the Entity.
     */
	public void setMass(double mass) {
		this.mass = mass;
	}

    /**
     * Gets the mass of the Entity.
     */
	public double getMass() {
		return this.mass;
	}

    /**
     * Sets the down force of the Entity.
     */
	public void setDownForce(double downForce) {
		this.downForce = downForce;
	}

    /**
     * Gets the down force of the Entity.
     */
	public double getDownForce() {
		return this.downForce;
	}

    /**
     * Sets the up force of the Entity.
     */
	public void setUpForce(double upForce) {
		this.upForce = upForce;
	}

    /**
     * Gets the up force of the Entity.
     */
	public double getUpForce() {
		return this.upForce;
	}

    /**
     * Sets the right force of the Entity.
     */
	public void setRightForce(double rightForce) {
		this.rightForce = rightForce;
	}

    /**
     * Gets the right force of the Entity.
     */
	public double getRightForce() {
		return this.rightForce;
	}

    /**
     * Sets the left force of the Entity.
     */
	public void setLeftForce(double leftForce) {
		this.leftForce = leftForce;
	}

    /**
     * Gets the left force of the Entity.
     */
	public double getLeftForce() {
		return this.leftForce;
	}
	
}