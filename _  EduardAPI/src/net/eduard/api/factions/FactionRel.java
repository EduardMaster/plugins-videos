package net.eduard.api.factions;

public enum FactionRel {

	RIVAL("§c"), ALLY("§9"), NEUTRAL("§7"), MEMBER("§a"), FREE_ZONE(
			"§f"), PROTECTED_ZONE("§6"), WAR_ZONE("§4"), WAR(
					"§d"), SUBORDINATE("§3"), LEADER("§6"), SUPERIOR("§B");

	private FactionRel(String color) {
		setColor(color);
	}

	private String color;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
