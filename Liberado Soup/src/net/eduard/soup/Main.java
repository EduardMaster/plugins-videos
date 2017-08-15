
package net.eduard.soup;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.API;
import net.eduard.api.config.Config;
import net.eduard.api.game.Sounds;
import net.eduard.soup.command.SoupCommand;
import net.eduard.soup.event.SoupEvent;
import net.eduard.soup.event.SoupSignEvent;


/**
 * Preço: 15
 *
 */
public class Main extends JavaPlugin implements Listener {

	public static Config getConfig(World world) {

		return config.createConfig(world.getName() + "/config.yml");
	}

	public static Config config;

	public static ItemStack soup;

	@Override
	public void onEnable() {

		config = new Config(this);
		new SoupEvent().register(this);
		new SoupSignEvent().register(this);
		new SoupCommand().register();

		Sounds sound = Sounds.create(Sound.BURP);
		config.add("soup", "&6Voce ganhou varias sopas!");
		config.add("soup-name", "§6Soup");
		soup = API.newItem(Material.MUSHROOM_SOUP,config.message("SoupName"));
		config.add("empty-soup-name", "§6Soup Empty");
		config.add("create-sign", "&6Voce criou uma placa de sopas!");
		config.add("sign-name", "&c&lSopas gratis!");
		config.add("sign", Arrays.asList("&f=======","&aSopas!"),"&2Clique!","&f======");
		config.saveConfig();
		for (World world : Bukkit.getWorlds()) {
			Config config = getConfig(world);
			config.add("enable", true);
			config.add("no-change-food-level", true);
			config.add("soup-recover-value", 6);
			config.add("enable-sound", true);
			config.saveConfig();
			config.add("sound", sound);
		}
	
	}

}
