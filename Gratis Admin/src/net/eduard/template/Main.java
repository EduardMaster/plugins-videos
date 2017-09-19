
package net.eduard.template;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.template.command.TemplateCommand;
import net.eduard.template.event.NoFall;
import net.eduard.template.event.TemplateEvent;
import net.eduard.template.event.TrocaRapida;
import net.eduard.template.event.VerInfo;
import net.eduard.template.event.VerInv;

public class Main extends JavaPlugin implements Listener {
	public static Main plugin;
	public static FileConfiguration config;
	public static ArrayList<Player> admins = new ArrayList<>();
	@Override
	public void onEnable() {
		plugin = this;
		config = plugin.getConfig();
		saveDefaultConfig();
		{
			config.addDefault("valor3", "valor4");
			config.options().copyDefaults(true);
			saveConfig();
		}
		getCommand("comando").setExecutor(new TemplateCommand());
		Bukkit.getPluginManager().registerEvents(new TemplateEvent(), this);
		Bukkit.getPluginManager().registerEvents(new NoFall(), this);
		Bukkit.getPluginManager().registerEvents(new VerInfo(), this);
		Bukkit.getPluginManager().registerEvents(new VerInv(), this);
		Bukkit.getPluginManager().registerEvents(new TrocaRapida(), this);
		 
	}
	public static ItemStack newItem(String name,Material material,String... lore) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		return item;
	}
	public static void giveItemsAdmin(Player p) {
		PlayerInventory inv = p.getInventory();
		inv.clear();
		inv.setArmorContents(null);
		ItemStack testNockback = newItem("§aTestar Knockback", Material.STICK);
		testNockback.addUnsafeEnchantment(Enchantment.KNOCKBACK, 10);
		ItemStack testNoFall = newItem("§aTestar No-Fall", Material.FEATHER);
		ItemStack seeInfo = newItem("§aVer Informações", Material.BLAZE_ROD);
		ItemStack testAutoSoup = newItem("§aTestar Auto-Soup", Material.BOWL);
		ItemStack changeFast = newItem("§aTroca-Rapida", Material.MAGMA_CREAM);
		inv.setItem(0, testNockback);
		inv.setItem(2, seeInfo);
		inv.setItem(3, testNoFall);
		inv.setItem(5, testAutoSoup);
		inv.setItem(7, changeFast);
	}

	@Override
	public void onDisable() {

	}

}
