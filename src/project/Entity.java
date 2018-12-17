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
	private Double weight;
	private Double resultantForce;
	private Double rightForce;
	private Double leftForce;
	private int shapeIndex;

    /**
     * Default constructor for the entity.
     */
	public Entity(int shapeIndex) {
        this.shapeIndex = shapeIndex;
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
	public void setWeight(double weight) {
		this.weight = weight;
	}

    /**
     * Gets the down force of the Entity.
     */
	public Double getWeight() {
		if (this.weight == null) {
			return 0.0;
		}
		return this.weight;
	}

    /**
     * Sets the up force of the Entity.
     */
	public void setResultantForce(double resultantForce) {
		this.resultantForce = resultantForce;
	}

    /**
     * Gets the up force of the Entity.
     */
	public Double getResultantForce() {
        if (this.resultantForce == null) {
            return 0.0;
        }
		return this.resultantForce;
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
        if (this.rightForce == null) {
            return 0.0;
        }
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
        if (this.leftForce == null) {
            return 0.0;
        }
		return this.leftForce;
	}

    public int getShapeIndex() { return this.shapeIndex; }
}