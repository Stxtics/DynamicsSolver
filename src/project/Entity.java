package project;

/**
 * An entity that is created when a rectangle2d is made. It stores data that the user
 * can set and then the program uses to solve the problem.
 */
public class Entity {
    /**
     * Variables of values that the entity can have.
     */
	private Double mass;
	private Double downForce;
	private Double upForce;
	private Double rightForce;
	private Double leftForce;

    /**
     * Default constructor for the entity.
     */
	public Entity() {

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
	public Double getMass() {
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
	public Double getDownForce() {
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
	public Double getUpForce() {
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
	public Double getRightForce() {
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
	public Double getLeftForce() {
		return this.leftForce;
	}
	
}