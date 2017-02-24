
package net.eduard.api.dev;

import org.bukkit.entity.Player;

import net.eduard.api.config.Section;
import net.eduard.api.manager.TitleAPI;
import net.eduard.api.util.Save;
import net.eduard.api.util.TitleType;

public class Title implements Save {

	private int fadeIn;

	private int stay;

	private int fadeOut;

	private String message;

	private TitleType type = TitleType.TITLE;

	public Title() {
	}

	public Title(String message, int fadeIn, int stay, int fadeOut) {
		this.fadeIn = fadeIn;
		this.stay = stay;
		this.fadeOut = fadeOut;
		this.message = message;
	}

	public Title create(Player player) {

		TitleAPI.send(player, type, message, fadeIn, stay, fadeOut);
		return this;
	}

	public int getFadeIn() {

		return fadeIn;
	}

	public int getFadeOut() {

		return fadeOut;
	}

	public String getMessage() {

		return message;
	}

	public int getStay() {

		return stay;
	}

	public Title setFadeIn(int fadeIn) {

		this.fadeIn = fadeIn;
		return this;
	}

	public Title setFadeOut(int fadeOut) {

		this.fadeOut = fadeOut;
		return this;
	}

	public Title setMessage(String message) {

		this.message = message;
		return this;
	}

	public Title setStay(int stay) {

		this.stay = stay;
		return this;
	}

	public void save(Section section, Object value) {

	}

	public Object get(Section section) {
		return null;
	}

}
