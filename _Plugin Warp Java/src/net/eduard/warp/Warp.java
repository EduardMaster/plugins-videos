package net.eduard.warp;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.API;
import net.eduard.api.config.Config;
import net.eduard.api.config.Section;
import net.eduard.api.dev.Game;
import net.eduard.api.dev.Sounds;
import net.eduard.api.gui.Events;
import net.eduard.api.gui.Gui;
import net.eduard.api.util.Save;
import net.eduard.api.util.SimpleEffect;

public class Warp implements Save {
	private static Gui gui;
	private static HashMap<String, Warp> warps = new HashMap<>();
	private String name;
	private Location spawn = Bukkit.getWorlds().get(0).getSpawnLocation();
	private int delayInSeconds = 1;
	private boolean canMoveOnDelay = true;
	private String message = "§6Voce foi teleportado para o Warp §e$warp";
	private Sounds sound = Sounds.create(Sound.ENDERMAN_TELEPORT);
	private boolean enableGui = true;
	private ItemStack guiIcon;

	private Warp(String name) {
		warps.put(name, this);
		this.name = name;
		guiIcon = API.newItem(Material.ENDER_PEARL, "§bTeleporte para " + name);

	}

	public Warp() {
	}

	public String chat(String key) {
		return Main.config.message(key).replace("$warp", name);
	}

	public static void reloadWarps() {
		warps.clear();
		for (Config config : Main.getWarp("").getConfigs()) {
			config.reloadConfig();
			Warp warp = (Warp) config.get("Warp");
			warps.put(warp.getName().toLowerCase(), warp);
		}
		if (gui != null) {
			gui.clear();

		} else {
			gui = new Gui(6, Main.config.message("GuiName"));
		}
		gui.setItem(Main.config.getItem("GuiItem"));

		int id = 0;
		for (Warp warp : warps.values()) {
			gui.set(id, warp.getGuiIcon(), new Events().setCloseInventory(true).setCommand("warp " + warp.getName()));
			id++;
		}
		if (!gui.isRegistred())
			gui.register();
	}

	public static void saveWarps() {
		for (Warp warp : warps.values()) {
			Config config = Main.getWarp(warp.getName());
			config.set("Warp", warp);
			config.saveConfig();
		}

	}

	public void teleport(Player p) {
		new Game(delayInSeconds).delay(new SimpleEffect() {

			public void effect() {
				p.teleport(spawn);
				if (message != null)
					p.sendMessage(message.replace("$warp", name));
				if (sound != null)
					sound.create(p);
			}
		});

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}

	public int getDelayInSeconds() {
		return delayInSeconds;
	}

	public void setDelayInSeconds(int delayInSeconds) {
		this.delayInSeconds = delayInSeconds;
	}

	public boolean isCanMoveOnDelay() {
		return canMoveOnDelay;
	}

	public void setCanMoveOnDelay(boolean canMoveOnDelay) {
		this.canMoveOnDelay = canMoveOnDelay;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Sounds getSound() {
		return sound;
	}

	public void setSound(Sounds sound) {
		this.sound = sound;
	}

	public boolean isEnableGui() {
		return enableGui;
	}

	public void setEnableGui(boolean enableGui) {
		this.enableGui = enableGui;
	}

	public ItemStack getGuiIcon() {
		return guiIcon;
	}

	public void setGuiIcon(ItemStack guiIcon) {
		this.guiIcon = guiIcon;
	}

	public static Gui getGui() {
		return gui;
	}

	public static boolean exists(String name) {
		return warps.containsKey(name.toLowerCase());
	}

	public static Warp getWarp(String name) {
		if (exists(name.toLowerCase())) {
			return warps.get(name.toLowerCase());
		}
		return new Warp(name);
	}

	public static String getWarps() {
		StringBuilder text = new StringBuilder();
		int id = 0;
		for (Warp warp : warps.values()) {
			if (id > 0)
				text.append(", ");
			text.append(warp.name);
			id++;
		}
		return text.toString();
	}

	public static boolean hasWarps() {
		return !warps.isEmpty();
	}

	public void save(Section section, Object value) {

	}

	public static void delete(String name) {
		warps.remove(name.toLowerCase());
		Main.getWarp(name.toLowerCase()).deleteConfig();
	}

	public Object get(Section section) {
		return null;
	}

	public static void open(Player p) {
		gui.open(p);
	}
}
