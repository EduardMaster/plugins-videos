package net.eduard.api.particle;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.eduard.api.config.Save;
import net.eduard.api.config.Section;
import net.eduard.api.manager.RexAPI;

public class Particle implements Save {

	private Location location;

	private int amount;

	private ParticleType particle;

	private float speed;

	private float xRandom;

	private float yRandom;

	private float zRandom;

	public Particle(ParticleType type, Location location, int amount) {
		setLocation(location);
		setAmount(amount);
		setParticle(type);
	}

	public Particle(ParticleType type, Location location, int amount, float random, float speed) {

		this(type, location, amount);
		setxRandom(random);
		setyRandom(random);
		setzRandom(random);
		setSpeed(speed);
	}

	public Particle(ParticleType type, Location location, int amount, float xRandom, float yRandom, int zRandom) {

		this(type, location, amount);
		setxRandom(xRandom);
		setyRandom(yRandom);
		setzRandom(zRandom);
	}

	public Particle(ParticleType type, Location location, int amount, float xRandom, float yRandom, int zRandom,
			float speed) {

		this(type, location, amount, xRandom, yRandom, zRandom);
		setSpeed(speed);
	}

	public Particle create() {
		try {
			RexAPI.sendPackets(getPacket());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return this;
	}

	private Object getPacket() throws Exception {

		return RexAPI.getNew(RexAPI.pPlayOutWorldParticles, particle.getParticleName(), (float) location.getX(),
				(float) location.getY(), (float) location.getZ(), xRandom, yRandom, zRandom, speed, amount);
	}

	public Particle create(Player p) {

		try {
			RexAPI.sendPacket(getPacket(), p);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return this;
	}

	public int getAmount() {

		return amount;
	}

	public Location getLocation() {

		return location;
	}

	public ParticleType getParticle() {

		return particle;
	}

	public float getSpeed() {

		return speed;
	}

	public float getxRandom() {

		return xRandom;
	}

	public float getyRandom() {

		return yRandom;
	}

	public float getzRandom() {

		return zRandom;
	}

	public void setAmount(int amount) {

		this.amount = amount;
	}

	public void setLocation(Location location) {

		this.location = location;
	}

	public void setParticle(ParticleType particle) {

		this.particle = particle;
	}

	public void setSpeed(float speed) {

		this.speed = speed;
	}

	public void setxRandom(float xRandom) {

		this.xRandom = xRandom;
	}

	public void setyRandom(float yRandom) {

		this.yRandom = yRandom;
	}

	public void setzRandom(float zRandom) {

		this.zRandom = zRandom;
	}

	public void save(Section section, Object value) {

	}

	public Object get(Section section) {
		return null;
	}
	public enum ParticleType {
		HUGE_EXPLOSION("hugeexplosion", 0), LARGE_EXPLODE("largeexplode", 1),
		FIREWORKS_SPARK("fireworksSpark", 2), BUBBLE("bubble", 3),
		SUSPEND("suspend", 4), DEPTH_SUSPEND("depthSuspend", 5),
		TOWN_AURA("townaura", 6), CRIT("crit", 7), MAGIC_CRIT("magicCrit", 8),
		MOB_SPELL("mobSpell", 9), MOB_SPELL_AMBIENT("mobSpellAmbient", 10),
		SPELL("spell", 11), INSTANT_SPELL("instantSpell", 12),
		WITCH_MAGIC("witchMagic", 13), NOTE("note", 14), PORTAL("portal", 15),
		ENCHANTMENT_TABLE("enchantmenttable", 16), EXPLODE("explode", 17),
		FLAME("flame", 18), LAVA("lava", 19), FOOTSTEP("footstep", 20),
		SPLASH("splash", 21), LARGE_SMOKE("largesmoke", 22), CLOUD("cloud", 23),
		RED_DUST("reddust", 24), SNOWBALL_POOF("snowballpoof", 25),
		DRIP_WATER("dripWater", 26), DRIP_LAVA("dripLava", 27),
		SNOW_SHOVEL("snowshovel", 28), SLIME("slime", 29), HEART("heart", 30),
		ANGRY_VILLAGER("angryVillager", 31), HAPPY_VILLAGER("happyVillager", 32);

	private String particleName;

	private ParticleType(String particleName, int id) {
		setParticleName(particleName);
	}

	public String getParticleName() {

		return particleName;
	}

	public void setParticleName(String particleName) {
		this.particleName = particleName;
	}

	}
}
