
package net.eduard.drops;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.config.Config;
import net.eduard.api.gui.Drop;
import net.eduard.api.gui.DropItem;
import net.eduard.drops.command.DropsReloadCMD;

public class Main extends JavaPlugin {

	public static Main plugin;
	public static Config config;


	public void onEnable() {

		plugin = this;
		config = new Config(this);
		new DropsReloadCMD();
		reload();
	}
	@SuppressWarnings("deprecation")
	public static void reload() {
		config.reloadConfig();
		for (World world:Bukkit.getWorlds()) {
			config.add("enable."+world.getName(), true);
			Drop.CAN_DROP.put(world, config.getBoolean("enable."+world.getName()));
			for (EntityType type:EntityType.values()) {
				if (type.isAlive()&&type.isSpawnable()) {
					Drop drop = new Drop(true, true, 2, 5, Arrays.asList(new DropItem(new ItemStack(Material.GOLDEN_APPLE), 1, 3, 0.75)));
					Config newConfig = config.newConfig(world.getName().toLowerCase()+"/"+type.getName().toLowerCase()+".yml");
					newConfig.add("CustomDrop", drop);		
					drop = (Drop) newConfig.get("CustomDrop");
					Drop.setDrop(world, type, drop);
					newConfig.saveConfig();
				}
			}
		}
		config.saveConfig();
	}
	
}
