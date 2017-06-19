package net.eduard.api.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;


public class FullBoard extends DisplayBoard {

	private BukkitTask task;
	private FullType type;

	private List<String> titles = new ArrayList<>();
	private int id;
 
	public FullBoard(FullType type, String title, String... colors) {
		this.type = type;
		String color = "";
		if (colors.length > 0) {
			color = colors[0];
		}
		if (type == FullType.GO_AND_BACK) {
			for (int i = 0; i <= title.length(); i++) {
				String text = color + title.substring(0, i);
				titles.add(text);
			}
			for (int i = title.length()-1; i >= 0; i--) {
				String text = color + title.substring(0, i);
				titles.add(text);
			}
		}

	}

	public void init(Plugin plugin, long ticks) {
		task = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {

			@Override
			public void run() {
				if (type == FullType.GO_AND_BACK) {
					if (id >= titles.size()) {
						id = 0;
					}
					setDisplay(titles.get(id));
					setTitle(getDisplay());
					id++;
				}
			}
		}, ticks, ticks);
	}
	public void stop() {
		if (task != null) {
			task.cancel();
			task = null;
		}
	}

	public static class FullLine {
//		String newTitle = ChatColor.stripColor(name + getEmpty(100));
//		// vamo supor que size = 30
//		int size = 32 - scrollPrefix.length() - scrollSuffix.length();
//		if (newTitle.length() <= size) {
//			currentChar = 0;
//			setTitle(name);
//		} else {
//			if (currentChar >= newTitle.length()) {
//				currentChar = 0;
//			}
//
//			String scrolled = "";
//			String moved = newTitle.substring(currentChar);
//			if (moved.length() > size) {
//				scrolled = moved.substring(0, size);
//			} else {
//				int rest = size - moved.length();
//				scrolled = moved.substring(0, moved.length());
//				scrolled += newTitle.substring(0, rest);
//			}
//			setTitle(scrollPrefix + scrolled + scrollSuffix);
//			currentChar++;
//		}
	}
	public static enum FullType {
		FORWARD, BACKWARD, GO_AND_BACK,
	}
	public FullType getType() {
		return type;
	}
	public void setType(FullType type) {
		this.type = type;
	}
	public List<String> getTitles() {
		return titles;
	}
	public void setTitles(List<String> titles) {
		this.titles = titles;
	}

}
