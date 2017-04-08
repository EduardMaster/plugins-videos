package net.eduard.api.util;

public enum ChatType {

	NORMAL("§A", "§2"),ERROR("§C", "§4"), SIMPLE("§7", "§8"), GAME("§E", "§6"), SETUP("§B",
			"§3"),  BONUS("§9", "§1"),EFFECT("§D",
					"§5"), EXTRA("§F", "§0");

	private ChatType(String light, String dark) {
		setLight(light);
		setDark(dark);
	}
	private String dark;

	private String light;
	
	public String getLightBold(){
		return light +"§l";
	}
	public String getDarkBold(){
		return dark +"§l";
	}
	
	public String getLight() {
		return light;
	}

	public void setLight(String light) {
		this.light = light;
	}

	public String getDark() {
		return dark;
	}

	public void setDark(String dark) {
		this.dark = dark;
	}
	public String toString() {
		return getDarkBold();
	}
}
