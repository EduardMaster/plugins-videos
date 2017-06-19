package net.eduard.api.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

import net.eduard.api.config.Save;
import net.eduard.api.config.Section;
import net.eduard.api.util.Cs;

public class Score extends DisplayBoard implements Save {

	private String health=Cs.getRedHeart();;
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
			getLines().add(Cs.getText(line, p));
		}
		update();
		getHealth().setDisplayName(Cs.getText(health,p));
	}

	public Score copy() {
	return	new Score(this.title, this.lines.toArray(new String[0]));
	}

	@Override
	public Object get(Section section) {
		lines = section.getStringList("lines");
		return null;
	}

	@Override
	public void save(Section section, Object value) {

	}

	public void setHealth(String health) {
		this.health =health;
	}

}
