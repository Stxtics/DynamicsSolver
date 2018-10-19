package project;

public class Rope {
	private double tension;
	private Entity object1;
	private Entity object2;

	public Rope() {

	}

	public Rope(double tension, Entity object1, Entity object2) {
		this.tension = tension;
		this.object1 = object1;
		this.object2 = object2;
	}

	public void setTension(double tension) {
		this.tension = tension;
	}
	
	public double getTension() {
		return this.tension;
	}

	public void setObject1(Entity object1) {
		this.object1 = object1;
	}
	
	public Object getObject1() {
		return this.object1;
	}

	public void setObject2(Entity object2) {
		this.object2 = object2;
	}
	
	public Object getObject2() {
		return this.object2;
	}
}
