package br.com.piracraft.lobby2.menu;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Menu implements Listener{
	
	private static Map<String, Menu> menus = new HashMap<>();
	public static Menu getMenu(String name){
		return menus.get(name);
	}
	public Menu(String title) {
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
		setMenu(Bukkit.createInventory(null, 6*9, title));
		menus.put(getClass().getSimpleName().replace("Menu", ""), this);
		
	}
	private Inventory menu;
	
	public abstract void update();
	
	public void show(Player player){
		player.openInventory(menu);
	}
	
	public static int getPosition(int x,int z){
		int value = (x-1)*9;
		return value+z-1;
	}
	public static Plugin getPlugin(){
		return JavaPlugin.getProvidingPlugin(Menu.class);
	}
	public Inventory getMenu() {
		return menu;
	}
	public void setMenu(Inventory menu) {
		this.menu = menu;
	}
}
