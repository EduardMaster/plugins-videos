
package net.eduard.warp;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.API;
import net.eduard.api.config.Config;
import net.eduard.api.config.Section;
import net.eduard.warp.command.DeleteWarpCommand;
import net.eduard.warp.command.SetWarpCommand;
import net.eduard.warp.command.WarpCommand;
import net.eduard.warp.command.WarpMenuCommand;
import net.eduard.warp.command.WarpReloadCommand;
import net.eduard.warp.command.WarpsCommand;
import net.eduard.warp.event.WarpEvent;

public class Main extends JavaPlugin implements Listener{

	public static Config getWarp(String warpName) {

		if (warpName.isEmpty()) {
			return config.createConfig("Warps/");
		}
			
		return config.createConfig("Warps/" + warpName.toLowerCase() + ".yml");
	}

	public static Config config;

	
	public void onEnable() {
		config = new Config(this);
		ItemStack item = API.newItem(Material.COMPASS, "&6&lWarps");
		config.add("Warp", "&6Voce sera teleportado para esse warp: &e$warp");
		config.add("NoWarps", "&cNao existem warps!");
		config.add("Warps", "&aWarps disponiveis: &2$warps");
		config.add("SetWarp", "&bVoce criou esse warp: &3$warp");
		config.add("DeleteWarp", "&bVoce deletou esse warp: &3$warp");
		config.add("NoWarp", "&cNao existe esse warp: &f$warp");
		config.add("ReloadWarps", "&bAs warps foram recarregadas!");
		config.add("GuiName", "&8- Teleportes -");
		config.add("GuiItem",item);
		config.add("giveGuiItemOnJoin", true);
		config.saveConfig();
		new WarpCommand();
		new SetWarpCommand();
		new DeleteWarpCommand();
		new WarpsCommand();
		new WarpEvent();
		new WarpReloadCommand();
		new WarpMenuCommand();
		Section.register(Warp.class);
		Warp.reloadWarps();
	}
	public void onDisable() {
		Warp.saveWarps();
	}
}
