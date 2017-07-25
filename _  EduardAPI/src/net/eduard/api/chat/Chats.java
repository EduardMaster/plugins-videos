package net.eduard.api.chat;

import org.bukkit.ChatColor;

public enum Chats {
	NORMAL("§A", "§2"), ERROR("§C", "§4"), SIMPLE("§7", "§8"), GAME("§E",
			"§6"), SETUP("§B", "§3"), BONUS("§9",
					"§1"), EFFECT("§D", "§5"), EXTRA("§F", "§0");

	private Chats(String light, String dark) {
		setLight(light);
		setDark(dark);
	}
	public static String getHeart() {
		return "♥";
	}

	public static String getArrow() {
		return "➵";
	}

	public static String getArrowRight() {
		return "››";
	}

	public static String getArrowLeft() {
		return "‹‹";
	}

	public static String getSquared() {
		return "❑";
	}

	public static String getInterrogation() {
		return "➁";
	}

	public static String getRedHeart() {
		return ChatColor.RED + getHeart();
	}

	public static String getALlSimbols() {
		return "❤❥✔✖✗✘❂⋆✢✣✤✥✦✩✪✫✬✭✵✴✳✲✱★✰✯✮✶✷✸✹✺✻✼❄❅✽✡☆❋❊❉❈❇❆✾✿❀❁❃✌♼♽✂➣➢⬇➟⬆⬅➡✈✄➤➥➦➧➨➚➘➙➛➶➵➴➳➲➸➞➝➜➷➹➹➺➻➼➽Ⓜ⬛⬜ℹ☕▌▄▆▜▀▛█";
	}

	public static String getAllSimbols2() {
		return "™⚑⚐☃⚠⚔⚖⚒⚙⚜⚀⚁⚂⚃⚄⚅⚊⚋⚌⚍⚏⚎☰☱☲☳☴☵☶☷⚆⚇⚈⚉♿♩♪♫♬♭♮♯♠♡♢♗♖♕♔♧♛♦♥♤♣♘♙♚♛♜♝♞♟⚪➃➂➁➀➌➋➊➉➈➇➆➅➄☣☮☯⚫➌➋➊➉➈➇➆➅➄➍➎➏➐➑➒➓ⓐⓑⓚ";
	}

	public static String getAllSimbols3() {
		return "웃유♋♀♂❣¿⌚☑▲☠☢☿Ⓐ✍☤✉☒▼⌘⌛®©✎♒☁☼ツღ¡Σ☭✞℃℉ϟ☂¢£⌨⚛⌇☹☻☺☪½∞✆☎⌥⇧↩←→↑↓⚣⚢⌲♺☟☝☞☜➫❑❒◈◐◑«»‹›×±※⁂‽¶—⁄—–≈÷≠π†‡‡¥€‰●•·";
	}
	private String dark;

	private String light;

	public String getLightBold() {
		return light + "§l";
	}
	public String getDarkBold() {
		return dark + "§l";
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
