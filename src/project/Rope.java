package project;

/**
 * A rope that is created when a rope or pulley is made. It holds the
 * tension of the rope and each object on either side of it.
 */
public class Rope {
    /**
     * Variables that a rope has.
     */
	private Double tension;
	private Entity object1;
	private Entity object2;

    /**
     * Default constructor for a rope.
     */
	public Rope() {

	}

    /**
     * Sets the tension of the Rope.
     */
	public void setTension(double tension) {
		this.tension = tension;
	}

    /**
     * Gets the tension of the Rope.
     */
	public Double getTension() {
		return this.tension;
	}

    /**
     * Sets the left object of the Rope.
     */
	public void setObject1(Entity object1) {
		this.object1 = object1;
	}

    /**
     * Gets the left entity of the Rope.
     */
	public Entity getObject1() {
		return this.object1;
	}

    /**
     * Sets the right entity of the Rope.
     */
	public void setObject2(Entity object2) {
		this.object2 = object2;
	}

    /**
     * Gets the right entity of the Rope.
     */
	public Entity getObject2() {
		return this.object2;
	}
}
