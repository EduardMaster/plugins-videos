
package net.eduard.soup;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.config.Config;
import net.eduard.api.game.Sounds;
import net.eduard.api.setup.Mine;
import net.eduard.soup.command.SoupCommand;


public class Main extends JavaPlugin implements Listener {

	public static Config getConfig(World world) {

		return config.createConfig(world.getName() + "/config.yml");
	}

	public static Config config;

	public static ItemStack soup;

	@Override
	public void onEnable() {

		config = new Config(this);
		new SoupCommand().register();
		Mine.event(this, this);
		Sounds sound = Sounds.create(Sound.BURP);
		config.add("soup", "&6Voce ganhou varias sopas!");
		config.add("soup-name", "§6Soup");
		soup = Mine.newItem(Material.MUSHROOM_SOUP, config.message("SoupName"));
		config.add("empty-soup-name", "§6Soup Empty");
		config.add("create-sign", "&6Voce criou uma placa de sopas!");
		config.add("sign-name", "&c&lSopas gratis!");
		config.add("sign", Arrays.asList("&f=======", "&aSopas!"), "&2Clique!", "&f======");
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

	@EventHandler
	public void event(SignChangeEvent e) {

		Player p = e.getPlayer();
		if (e.getLine(0).toLowerCase().contains("soup")) {
			int id = 0;
			for (String text : Main.config.getMessages("sign")) {
				e.setLine(id, Mine.removeBrackets(text));
				id++;
			}
			p.sendMessage(Main.config.message("create-sign"));
		}
	}

	@EventHandler
	public void effect(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Config config = Main.getConfig(p.getWorld());
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getState() instanceof Sign) {
				Sign sign = (Sign) e.getClickedBlock().getState();
				if (sign.getLine(0).equalsIgnoreCase(Mine.removeBrackets(Main.config.getMessages("sign").get(0)))) {
					Inventory inv = Mine.newInventory(Main.config.message("sign-name"), 6 * 9);

					for (ItemStack item : inv) {
						if (item == null) {
							inv.addItem(Main.soup);
						}
					}
					p.openInventory(inv);
				}
			}
		}
		if (e.getItem() == null)
			return;
		if (e.getItem().getType() == Material.MUSHROOM_SOUP) {
			
			if (config.getBoolean("enable")) {
				boolean remove = false;
				e.setCancelled(true);
				if (e.getAction().name().contains("LEFT")) {
					e.setCancelled(false);
				}
				int value = config.getInt("soup-recover-value");
				if (p.getHealth() < p.getMaxHealth()) {

					double calc = p.getHealth() + value;
					p.setHealth(calc >= p.getMaxHealth() ? p.getMaxHealth() : calc);
					remove = true;
				}
				if (!config.getBoolean("no-change-food-level")) {
					if (p.getFoodLevel() < 20) {
						int calc = value + p.getFoodLevel();
						p.setFoodLevel(calc >= 20 ? 20 : calc);
						p.setSaturation(p.getSaturation() + 5);
						remove = true;
					}
				}
				if (remove) {
					e.setUseItemInHand(Result.DENY);
					p.getItemInHand().setType(Material.BOWL);
					if (config.getBoolean("enable-sound")) {
						config.getSound("sound").create(p);
					}
				}
			}
		}
	}

	@EventHandler
	public void event(FoodLevelChangeEvent e) {

		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (Main.getConfig(p.getWorld()).getBoolean("no-change-food-level")) {
				if (e.getFoodLevel() <= 20) {
					e.setFoodLevel(20);
					p.setExhaustion(0);
					p.setSaturation(20);
				}
			}
		}

	}
}
