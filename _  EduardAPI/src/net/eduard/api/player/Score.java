package net.eduard.api.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

import net.eduard.api.chat.Chats;
import net.eduard.api.config.ConfigSection;
import net.eduard.api.util.Copy;
import net.eduard.api.util.Save;

public class Score extends DisplayBoard implements Save ,Copy{

	private String health=Chats.getRedHeart();;
	private String title;
	private List<String> lines = new ArrayList<>();

	public Score() {
		super();
	}

	public Score(String title, String... lines) {
		super(title, lines);
		this.title = title;
		this.lines.addAll(Arrays.asList(lines));
	}

	public void update(Player p) {
		getLines().clear();
		for (String line : this.lines) {
			getLines().add(ConfigSection.getReplacers(line, p));
		}
		update();
		setTitle(ConfigSection.getReplacers(title,p));
		getHealth().setDisplayName(ConfigSection.getReplacers(health,p));
	}

	public Score copy() {
	return	new Score(this.title, this.lines.toArray(new String[0]));
	}

	@Override
	public Object get(ConfigSection section) {
		lines = section.getStringList("lines");
		return null;
	}

	@Override
	public void save(ConfigSection section, Object value) {

	}

	public void setHealth(String health) {
		this.health =health;
	}

}
