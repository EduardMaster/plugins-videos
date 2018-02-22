package net.eduard.api.setup;

import org.bukkit.ChatColor;

public final class ChatAPI {

	public static ChatColor SUCCESS = ChatColor.GREEN;
	public static ChatColor SUCCESS_ARGUMENT = ChatColor.DARK_GREEN;
	public static ChatColor ERROR = ChatColor.RED;
	public static ChatColor ERROR_ARGUMENT = ChatColor.DARK_RED;
	public static ChatColor MESSAGE = ChatColor.GOLD;
	public static ChatColor MESSAGE_ARGUMENT = ChatColor.YELLOW;
	public static ChatColor CHAT_CLEAR = ChatColor.WHITE;
	public static ChatColor CHAT_NORMAL = ChatColor.GRAY;
	public static ChatColor GUI_TITLE = ChatColor.BLACK;
	public static ChatColor GUI_TEXT = ChatColor.DARK_GRAY;
	public static ChatColor CONFIG = ChatColor.AQUA;
	public static ChatColor CONFIG_ARGUMENT = ChatColor.DARK_AQUA;
	public static ChatColor ITEM_TITLE = ChatColor.LIGHT_PURPLE;
	public static ChatColor ITEM_TEXT = ChatColor.DARK_PURPLE;
	public static ChatColor TITLE = ChatColor.DARK_BLUE;
	public static ChatColor TEXT = ChatColor.BLUE;

	public static enum Chat {
		NORMAL("§A", "§2"), ERROR("§C", "§4"), SIMPLE("§7", "§8"), GAME("§E", "§6"), SETUP("§B", "§3"), BONUS("§9",
				"§1"), EFFECT("§D", "§5"), EXTRA("§F", "§0");

		private String dark;
		private String light;

		private Chat(String light, String dark) {
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

		public String getLightBold() {
			return this.light + "§l";
		}

		public String getDarkBold() {
			return this.dark + "§l";
		}

		public String getLight() {
			return this.light;
		}

		public void setLight(String light) {
			this.light = light;
		}

		public String getDark() {
			return this.dark;
		}

		public void setDark(String dark) {
			this.dark = dark;
		}

		public String toString() {
			return getDarkBold();
		}
	}
}
