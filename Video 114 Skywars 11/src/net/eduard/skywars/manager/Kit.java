package net.eduard.skywars.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import net.eduard.skywars.Main;

public class Kit implements Listener{
	
	public static final Map<String, Kit> KITS_REGISTRED = new HashMap<>();
	
	public Kit() {
		Bukkit.getPluginManager().registerEvents(this, Main.plugin);
	}
	
	
	
	private String name;
	
	private String title;
	
	private int iconId;
	
	private int iconData;
	
	private List<String> description = new ArrayList<>();
	
	private List<ItemStack> items = new ArrayList<>();
	
	private List<String> kits = new ArrayList<>();

	private boolean glow;
	
	private boolean hasAbility;
	
	private int timesCanUse=1;
	
	public void register(){
		KITS_REGISTRED.put(name, this);
	}
	public void unregister(){
		KITS_REGISTRED.remove(name);
	}
	private List<Player> players = new ArrayList<>();
	
	
	public boolean hasKit(Player player){
		return players.contains(player);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getIconId() {
		return iconId;
	}

	public void setIconId(int iconId) {
		this.iconId = iconId;
	}
	@SuppressWarnings("deprecation")
	public void setIcon(Material type){
		setIconId(type.getId());
	}

	public int getIconData() {
		return iconData;
	}

	public void setIconData(int iconData) {
		this.iconData = iconData;
	}

	public List<String> getDescription() {
		return description;
	}

	public void setDescription(List<String> description) {
		this.description = description;
	}

	public boolean isGlow() {
		return glow;
	}

	public void setGlow(boolean glow) {
		this.glow = glow;
	}

	public boolean isHasAbility() {
		return hasAbility;
	}

	public void setHasAbility(boolean hasAbility) {
		this.hasAbility = hasAbility;
	}

	public int getTimesCanUse() {
		return timesCanUse;
	}

	public void setTimesCanUse(int timesCanUse) {
		this.timesCanUse = timesCanUse;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}	
	
	
	
	
	
	
	
	
	
	

}
