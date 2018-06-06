package net.eduard.curso.eventos;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

import net.eduard.api.lib.EnchantGlow;
import net.eduard.api.lib.VaultAPI;
import net.eduard.api.lib.core.Mine;

public class Eventos implements Listener {

	// item que faz o efeito
	public static ItemStack itemPremio1() {
		@SuppressWarnings("deprecation")
		ItemStack item = new ItemStack(19);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("titulo");
		meta.setLore(Arrays.asList("linha1"));
		item.setItemMeta(meta);
		EnchantGlow.addGlow(item);
		return item;
	}

	// ganhar um item ao entrar
	@EventHandler
	public void event(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		p.getInventory().addItem(itemPremio1());
		// criar uma cabeça de um jogador
		p.getInventory().addItem(Mine.newHead("Display", "EduardKIllerPro", 1, Arrays.asList("linha1")));
	}

	// fazer efeitos por um item
	@EventHandler
	public void event(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_AIR | e.getAction() == Action.RIGHT_CLICK_BLOCK) {

			if (e.getMaterial() == Material.AIR)
				return;
			if (e.getItem().isSimilar(itemPremio1())) {

				// Se o item tiver efeito proprio fazer este comando abaixo
				e.setCancelled(true);
				Mine.remove(p.getInventory(), itemPremio1(), 1);

				// tocar um som
				p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 2, 1);

				{
					// criar o fogo de artificio
					Firework fire = (Firework) p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);

					// pegar meta
					FireworkMeta meta = fire.getFireworkMeta();

					// setar distancia de voo
					meta.setPower(5);

					// adicionar um efeito
					meta.addEffect(FireworkEffect.builder().trail(true).flicker(true).with(Type.BALL)
							.withColor(Color.GREEN).withFade(Color.OLIVE).build());

					// setar meta
					fire.setFireworkMeta(meta);

				}

				// aqui vai o efeito
				// fazer comando
				p.chat("/comando");

				// mandar mensagem
				p.sendMessage("");

				// dar um item
				p.getInventory().addItem(new ItemStack(Material.DIAMOND, 64));

				// setar um item em uma posição
				p.getInventory().setItem(Mine.getPosition(1, 5), new ItemStack(Material.GOLD_INGOT, 64));

				// dar dinheiro
				double quantidade = 300;
				VaultAPI.getEconomy().bankDeposit(p.getName(), quantidade);

				// tirar dinheiro
				VaultAPI.getEconomy().bankWithdraw(p.getName(), quantidade);

				// dar teleporte em um local
				p.teleport(new Location(p.getWorld(), 1500, 300, 1500));

				// dar teleporte em um mundo diferente
				p.teleport(new Location(Bukkit.getWorld("nomedomundo"), 1500, 300, 1500));

				// efeito por pocertagem
				int porcentagem = 25;
				if (Math.random() <= porcentagem / 100) {

					// efeito

				}

				// quantidades aleatorias de qualquer coisa
				int random = 5 + new Random().nextInt(10);
				ItemStack item = new ItemStack(Material.EMERALD, random);
				p.getInventory().addItem(item);

				// encher o inventario de algum item
				Mine.fill(p.getInventory(), new ItemStack(Material.MUSHROOM_SOUP));

			}
		}

	}

}
