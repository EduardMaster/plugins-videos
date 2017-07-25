package net.eduard.plugin_1v1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Main extends JavaPlugin {

	public static Main getPlugin() {
		return JavaPlugin.getPlugin(Main.class);
	}
	private static Main plugin;
	public static Main getInstance() {
		return plugin;
	}
	public void onDisable() {
		for (Entry<Player, Player> mapa : emPvP.entrySet()) {
			Player jogador = mapa.getKey();
			pegarItensGuardados(jogador);
		}
	}
	public void onEnable() {
		plugin = this;
		getCommand("1v1").setExecutor(new Comando());
		Bukkit.getPluginManager().registerEvents(new Evento(), plugin);
	}
	public Map<Player, Player> convites = new HashMap<>();
	public Map<Player, Player> emPvP = new HashMap<>();
	public Map<Player, ItemStack[]> itens = new HashMap<>();
	public Map<Player, ItemStack[]> armaduras = new HashMap<>();
	public void convidarJogador(Player jogador, Player convidado) {
		if (convites.containsKey(jogador)) {
			jogador.sendMessage("§cVoce acabou de convidar o jogador espere o convite espirar!");
		}else
			{
			new BukkitRunnable() {
				
				@Override
				public void run() {
					if (!emPvP.containsKey(jogador)) {
						
						convidado.sendMessage("§cVoce não aceitou o convite do jogador "+jogador.getName());
						jogador.sendMessage("§cO jogador não aceitou o contive seu!");
					}
				}
			}.runTaskLater(this, 20*5);
		}

	}
	public void aceitarPvP(Player player,Player convidado) {
		if (convites.containsKey(player)) {
			if (convites.get(player).equals(convidado)) {
				iniciarPvP(player, convidado);
			}
		}
	}
	public void iniciarPvP(Player jogador,Player convidado) {
		emPvP.put(jogador, convidado);
		jogador.sendMessage("§aA batalha começa em 5 segundos!");
		convidado.sendMessage("§aA batalha começa em 5 segundos!");
		guardarItensAtuais(jogador);
		guardarItensAtuais(convidado);
		equiparItens(jogador);
		equiparItens(convidado);
		adicionarEfeitos(jogador);
		adicionarEfeitos(convidado);
		
	}
	public void vencerPvP(Player jogador, Player perdedor) {
		
	}
	private void equiparItens(Player jogador) {
		jogador.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
		jogador.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
		jogador.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
		jogador.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
		jogador.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
	}
	private void adicionarEfeitos(Player jogador) {
		jogador.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20*5, 1));
		jogador.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*5, 1));
	}
	public void guardarItensAtuais(Player jogador) { 
		armaduras.put(jogador, jogador.getInventory().getArmorContents());
		itens.put(jogador, jogador.getInventory().getContents());
		jogador.getInventory().clear();
		jogador.getInventory().setArmorContents(null);
	}
	public void pegarItensGuardados(Player jogador) {
		if (armaduras.containsKey(jogador)) {
			jogador.getInventory().setArmorContents(armaduras.get(jogador)
					);
		}
		if (itens.containsKey(jogador)) {
			jogador.getInventory().setContents(itens.get(jogador));
		}
	}

}
