package net.eduard.api.dev;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import net.eduard.api.config.Section;
import net.eduard.api.util.Save;

public class Jump implements Save {
	private boolean withHigh;
	private boolean highFirst;
	private double force;
	private double high;
	private Sounds sound;
	private boolean useVector;
	private Vector vector;

	public String toString() {
		return "Jump [withHigh=" + withHigh + ", highFirst=" + highFirst + ", force=" + force + ", high=" + high
				+ ", sound=" + sound + ", useVector=" + useVector + ", vector=" + vector + "]";
	}

	public Jump() {
	}

	public Jump(boolean withHigh, double high, double force, Sounds sound) {
		setWithHigh(withHigh);
		setHigh(high);
		setForce(force);
		setSound(sound);
	}

	public Jump(Vector vector, Sounds sound) {
		setVector(vector);
		setSound(sound);
		setUseVector(true);
	}

	public Jump create(final Entity entity) {

		Vector newVector = null;
		if (isUseVector() &&getVector() != null) {
			newVector = getVector();
		} else {
			newVector = entity.getLocation().getDirection();
			if (isHighFirst()) {
				if (isWithHigh()) {
					newVector.setY(getHigh());
				}
				newVector.multiply(getForce());
			}else {
				newVector.multiply(getForce());
				if (isWithHigh()) {
					newVector.setY(getHigh());
				}
			}
			
			
		}
		entity.setVelocity(newVector);
		if (getSound() != null) {
			if (entity instanceof Player) {
				Player player = (Player) entity;
				getSound().create(player);
			} else {
				getSound().create(entity.getLocation());
			}
		}
		return this;
	}

	public double getForce() {

		return force;
	}

	public double getHigh() {

		return high;
	}

	public Sounds getSound() {

		return sound;
	}

	public Vector getVector() {

		return vector;
	}

	public boolean isWithHigh() {

		return withHigh;
	}

	public void setForce(double force) {

		this.force = force;
	}

	public void setHigh(double high) {

		this.high = high;
	}

	public void setSound(Sounds sound) {

		this.sound = sound;
	}

	public void setVector(Vector vector) {

		this.vector = vector;
	}

	public void setWithHigh(boolean withHigh) {

		this.withHigh = withHigh;
	}

	public void save(Section section, Object value) {
	}

	public Object get(Section section) {
		return null;
	}

	public boolean isUseVector() {
		return useVector;
	}

	public void setUseVector(boolean useVector) {
		this.useVector = useVector;
	}

	public boolean isHighFirst() {
		return highFirst;
	}

	public void setHighFirst(boolean highFirst) {
		this.highFirst = highFirst;
	}

}
