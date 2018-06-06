package net.eduard.curso.caixa;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.lib.core.ConfigAPI;
import net.eduard.api.lib.core.Mine;
import net.eduard.api.lib.storage.StorageAPI;

public class CaixaSistema  {
	public static CaixaManager gerenciadorDeCaixas;// ou caixaManager
	public static ConfigAPI config;

	public static void ligar(JavaPlugin plugin) {
		config = new ConfigAPI("caixas.yml", plugin);

		StorageAPI.register(Caixa.class);
		StorageAPI.register(CaixaManager.class);
		
		
		if (config.contains("manager")) {
			gerenciadorDeCaixas = (CaixaManager) config.get("manager");
		} else {
			gerenciadorDeCaixas = new CaixaManager();
			Caixa caixaExemplo = new Caixa();
			caixaExemplo.setNome("Basica");
			caixaExemplo.setIconeLoja(Mine.newItem(Material.ENDER_CHEST, "§aCaixa Básica por 30 pila"));
			caixaExemplo.setCaixa(Mine.newItem(Material.CHEST, "§6Caixa Básica"));
			
			caixaExemplo.getPremios().add(new ItemStack(Material.DIAMOND,64));
			caixaExemplo.getPremios().add(new ItemStack(Material.DIAMOND,64));
			caixaExemplo.getPremios().add(new ItemStack(Material.DIAMOND,64));
			caixaExemplo.getPremios().add(new ItemStack(Material.DIAMOND,64));
			caixaExemplo.getPremios().add(new ItemStack(Material.DIAMOND,64));
			caixaExemplo.getPremios().add(new ItemStack(Material.DIAMOND,64));
			caixaExemplo.getPremios().add(new ItemStack(Material.IRON_INGOT,64));
			caixaExemplo.getPremios().add(new ItemStack(Material.IRON_INGOT,64));
			caixaExemplo.getPremios().add(new ItemStack(Material.IRON_INGOT,64));
			
			caixaExemplo.getPremios().add(new ItemStack(Material.IRON_INGOT,64));
			caixaExemplo.getPremios().add(new ItemStack(Material.IRON_INGOT,64));
			caixaExemplo.getPremios().add(new ItemStack(Material.IRON_INGOT,64));
			caixaExemplo.getPremios().add(new ItemStack(Material.STONE,1));
			caixaExemplo.getPremios().add(new ItemStack(Material.STONE,1));
			caixaExemplo.getPremios().add(new ItemStack(Material.STONE,1));
			
			caixaExemplo.getPremios().add(new ItemStack(Material.STONE,1));
			caixaExemplo.getPremios().add(new ItemStack(Material.STONE,1));
			caixaExemplo.getPremios().add(new ItemStack(Material.STONE,1));
			
			caixaExemplo.getPremios().add(new ItemStack(Material.STONE,1));
			caixaExemplo.getPremios().add(new ItemStack(Material.STONE,1));
			
			caixaExemplo.getPremios().add(new ItemStack(Material.STONE,1));
			caixaExemplo.getPremios().add(new ItemStack(Material.STONE,1));
			caixaExemplo.getPremios().add(new ItemStack(Material.STONE,1));
			
			caixaExemplo.getPremios().add(new ItemStack(Material.STONE,1));
			caixaExemplo.getPremios().add(new ItemStack(Material.STONE,1));
			caixaExemplo.getPremios().add(new ItemStack(Material.STONE,1));
			
			caixaExemplo.getPremios().add(new ItemStack(Material.STONE,1));
			caixaExemplo.getPremios().add(new ItemStack(Material.STONE,1));
			caixaExemplo.getPremios().add(new ItemStack(Material.STONE,1));
			caixaExemplo.getPremios().add(new ItemStack(Material.STONE,1));
			caixaExemplo.getPremios().add(new ItemStack(Material.STONE,1));
			
			caixaExemplo.getPremios().add(new ItemStack(Material.DIAMOND_CHESTPLATE));
			caixaExemplo.getPremios().add(new ItemStack(Material.DIAMOND_CHESTPLATE));
			caixaExemplo.getPremios().add(new ItemStack(Material.DIAMOND_LEGGINGS));
			caixaExemplo.getPremios().add(new ItemStack(Material.DIAMOND_BOOTS));
			caixaExemplo.getPremios().add(new ItemStack(Material.DIAMOND_HELMET));
			
			gerenciadorDeCaixas.getCaixas().add(caixaExemplo);
			
			
			
			
			config.set("manager", gerenciadorDeCaixas);
			config.saveConfig();
		}

	}

}
