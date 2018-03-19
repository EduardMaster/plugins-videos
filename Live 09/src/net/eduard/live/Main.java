package net.eduard.live;

import org.apache.logging.log4j.core.net.MulticastDNSAdvertiser;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	private KitManager kitManager;

	public void onEnable() {
		getCommand("baucair").setExecutor(new ComandoBauCair());
		getCommand("luz").setExecutor(new ComandoLuz());
		getCommand("teste")	.setExecutor(new ComandoTeste());
		getCommand("mudarplaca").setExecutor(new ComandoMudarPlaca());
		Bukkit.getPluginManager().registerEvents(new Eventos(), this);
		kitManager = new KitManager();
		Kit kitnovo = new Kit();
		kitnovo.setNome("Novo");
		kitnovo.setCooldown(15);
		kitnovo.setIcone(new ItemStack(Material.GOLDEN_APPLE,1,(short) 1));
		kitnovo.getItens().add(new ItemStack(Material.DIAMOND_SWORD));
		kitnovo.getItens().add(new ItemStack(Material.DIAMOND_CHESTPLATE));
		kitManager.getKits().add(kitnovo);
		Bukkit.getPluginManager().registerEvents(kitManager, this);
		
	}
	
	
	
}
