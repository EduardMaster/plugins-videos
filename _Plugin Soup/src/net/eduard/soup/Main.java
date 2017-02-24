
package net.eduard.soup;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.API;
import net.eduard.api.config.Config;
import net.eduard.api.dev.Sounds;
import net.eduard.api.gui.Click;
import net.eduard.api.util.ClickEffect;
import net.eduard.soup.command.SoupCMD;
import net.eduard.soup.event.SoupEvent;

public class Main extends JavaPlugin implements Listener {

	public static Config getConfig(World world) {

		return config.createConfig(world.getName() + "/config.yml");
	}

	public static Config config;

	public static ItemStack soup;

	public void onEnable() {

		config = new Config(this);
		new SoupEvent();
		new SoupCMD();
		

		Sounds sound = Sounds.create(Sound.BURP);
		config.add("Soup", "&6Voce ganhou varias sopas!");
		config.add("SoupName", "§6Soup");
		soup = API.newItem(Material.MUSHROOM_SOUP,config.message("SoupName"));
		config.add("EmptySoupName", "§6Soup Empty");
		config.add("CreateSign", "&6Voce criou uma placa de sopas!");
		config.add("SignName", "&c&lSopas gratis!");
		config.add("Sign", Arrays.asList("&f=======","&aSopas!"),"&2Clique!","&f======");
		config.saveConfig();
		for (World world : Bukkit.getWorlds()) {
			Config config = getConfig(world);
			config.add("enable", true);
			config.add("noChangeFood", true);
			config.add("minLifeToUseSoup", 17);
			config.add("soupRecover", 6);
			config.add("enableSound", true);
			config.saveConfig();
			config.add("Sound", sound);
		}
		new Click(Material.MUSHROOM_SOUP, new ClickEffect() {

			public void effect(PlayerInteractEvent e) {

				Player p = e.getPlayer();
				Config config = Main.getConfig(p.getWorld());
				if (config.getBoolean("enable")) {
					boolean remove = false;
					e.setCancelled(true);
					if (p.getHealth() < config.getDouble("MinLifeToUseSoup")) {
						int life = config.getInt("SoupRecover");
						double calc = p.getHealth() + life;
						p.setHealth(
							calc >= p.getMaxHealth() ? p.getMaxHealth() : calc);
						remove = true;
					}
					if (!config.getBoolean("NoChangeFood")) {
						if (p.getFoodLevel() < 20) {
							int food = config.getInt("SoupRecover");
							int calc = food + p.getFoodLevel();
							p.setFoodLevel(calc >= 20 ? 20 : calc);
							p.setSaturation(p.getSaturation() + 5);
							remove = true;
						}
					}
					if (remove) {
						e.setUseItemInHand(Result.DENY);
						p.getItemInHand().setType(Material.BOWL);
						if (config.getBoolean("EnableSound")) {
							config.getSound("Sound").create(p);

						}
					}
				}

			}

			public void effect(PlayerInteractEntityEvent e) {
				// TODO Auto-generated method stub
				
			}

			
		});
	}

}
