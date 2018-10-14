package net.eduard.curso.caixa;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.config.Config;
import net.eduard.api.lib.ConfigAPI;
import net.eduard.api.lib.Mine;
import net.eduard.api.lib.storage.StorageAPI;

public class CaixaAPI {
	private static CaixaManager manager;
	private static ConfigAPI config;

	public static ConfigAPI getConfig() {
		return config;
	}

	public static void setConfig(ConfigAPI config) {
		CaixaAPI.config = config;
	}

	public static void reload() {
		if (config.contains("manager")) {
			manager = (CaixaManager) config.get("manager");

		} else {
			manager = new CaixaManager();
			Caixa caixaExemplo = new Caixa();
			caixaExemplo.setNome("Basica");
			caixaExemplo.setIconeLoja(Mine.newItem(Material.ENDER_CHEST, "§aCaixa Básica por 30 pila"));
			caixaExemplo.setCaixa(Mine.newItem(Material.CHEST, "§6Caixa Básica"));

			caixaExemplo.getPremios().add(new ItemStack(Material.DIAMOND, 64));
			caixaExemplo.getPremios().add(new ItemStack(Material.DIAMOND, 64));
			caixaExemplo.getPremios().add(new ItemStack(Material.DIAMOND, 64));
			caixaExemplo.getPremios().add(new ItemStack(Material.DIAMOND, 64));
			caixaExemplo.getPremios().add(new ItemStack(Material.DIAMOND, 64));
			caixaExemplo.getPremios().add(new ItemStack(Material.DIAMOND, 64));
			caixaExemplo.getPremios().add(new ItemStack(Material.IRON_INGOT, 64));
			caixaExemplo.getPremios().add(new ItemStack(Material.IRON_INGOT, 64));
			caixaExemplo.getPremios().add(new ItemStack(Material.IRON_INGOT, 64));

			caixaExemplo.getPremios().add(new ItemStack(Material.IRON_INGOT, 64));
			caixaExemplo.getPremios().add(new ItemStack(Material.IRON_INGOT, 64));
			caixaExemplo.getPremios().add(new ItemStack(Material.IRON_INGOT, 64));
			caixaExemplo.getPremios().add(new ItemStack(Material.STONE, 1));
			caixaExemplo.getPremios().add(new ItemStack(Material.STONE, 1));
			caixaExemplo.getPremios().add(new ItemStack(Material.STONE, 1));

			caixaExemplo.getPremios().add(new ItemStack(Material.STONE, 1));
			caixaExemplo.getPremios().add(new ItemStack(Material.STONE, 1));
			caixaExemplo.getPremios().add(new ItemStack(Material.STONE, 1));

			caixaExemplo.getPremios().add(new ItemStack(Material.STONE, 1));
			caixaExemplo.getPremios().add(new ItemStack(Material.STONE, 1));

			caixaExemplo.getPremios().add(new ItemStack(Material.STONE, 1));
			caixaExemplo.getPremios().add(new ItemStack(Material.STONE, 1));
			caixaExemplo.getPremios().add(new ItemStack(Material.STONE, 1));

			caixaExemplo.getPremios().add(new ItemStack(Material.STONE, 1));
			caixaExemplo.getPremios().add(new ItemStack(Material.STONE, 1));
			caixaExemplo.getPremios().add(new ItemStack(Material.STONE, 1));

			caixaExemplo.getPremios().add(new ItemStack(Material.STONE, 1));
			caixaExemplo.getPremios().add(new ItemStack(Material.STONE, 1));
			caixaExemplo.getPremios().add(new ItemStack(Material.STONE, 1));
			caixaExemplo.getPremios().add(new ItemStack(Material.STONE, 1));
			caixaExemplo.getPremios().add(new ItemStack(Material.STONE, 1));

			caixaExemplo.getPremios().add(new ItemStack(Material.DIAMOND_CHESTPLATE));
			caixaExemplo.getPremios().add(new ItemStack(Material.DIAMOND_CHESTPLATE));
			caixaExemplo.getPremios().add(new ItemStack(Material.DIAMOND_LEGGINGS));
			caixaExemplo.getPremios().add(new ItemStack(Material.DIAMOND_BOOTS));
			caixaExemplo.getPremios().add(new ItemStack(Material.DIAMOND_HELMET));

			manager.getCaixas().add(caixaExemplo);

			save();

		}
	}

	public static void save() {

		config.set("manager", manager);
		config.saveConfig();
	}

	public static void ligar(JavaPlugin plugin) {
		config = new ConfigAPI("caixas.yml", plugin);
		StorageAPI.register(Caixa.class);
		StorageAPI.register(CaixaManager.class);
		reload();

	}

	public static CaixaManager getManager() {
		return manager;
	}

	public static void setManager(CaixaManager manager) {
		CaixaAPI.manager = manager;
	}

}
