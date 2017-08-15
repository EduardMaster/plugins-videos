package net.eduard.api.game;

import org.bukkit.entity.Player;

import net.eduard.api.config.ConfigSection;
import net.eduard.api.setup.RexAPI;
import net.eduard.api.util.Save;

public class Title implements Save, Cloneable {

	private int fadeIn;

	private int stay;

	private int fadeOut;

	private String title;
	private String subTitle;

	public String getTitle() {
		return title;
	}

	public Title setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public Title setSubTitle(String subTitle) {
		this.subTitle = subTitle;
		return this;
	}

	public Title() {
	}

	public Title(int fadeIn, int stay, int fadeOut, String title,
			String subTitle) {
		super();
		this.fadeIn = fadeIn;
		this.stay = stay;
		this.fadeOut = fadeOut;
		this.title = title;
		this.subTitle = subTitle;
	}

	public Title create(Player player) {
		RexAPI.sendTitle(player, title, subTitle, fadeIn, stay, fadeOut);
		return this;
	}

	public int getFadeIn() {

		return fadeIn;
	}

	public int getFadeOut() {

		return fadeOut;
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

	public Title setStay(int stay) {

		this.stay = stay;
		return this;
	}

	@Override
	public void save(ConfigSection section, Object value) {

	}

	@Override
	public Object get(ConfigSection section) {
		return null;
	}
	@Override
	public Title clone() {
		try {
			return (Title) super.clone();
		} catch (Exception e) {
		}
		return null;
	}

}
