package net.eduard.live;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class Eventos implements Listener {

	@EventHandler
	public void clicar(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.getItemInHand() == null)
			return;

		ItemStack caixa = new ItemStack(Material.CHEST);
		ItemMeta meta = caixa.getItemMeta();
		meta.setDisplayName("§aCaixa basica");
		caixa.setItemMeta(meta);

		if (p.getItemInHand().isSimilar(caixa)) {

			abrirCaixaBasica(p);

		}

	}

	@EventHandler
	public void evento(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		ItemStack caixa = new ItemStack(Material.CHEST);
		ItemMeta meta = caixa.getItemMeta();
		meta.setDisplayName("§aCaixa basica");
		caixa.setItemMeta(meta);

		p.getInventory().addItem(caixa);
	}
	public static void abrirCaixaBasica(Player player) {
		List<ItemStack> lista = new ArrayList<>();
		lista.add(new ItemStack(Material.DIAMOND_SWORD));
		lista.add(new ItemStack(Material.DIAMOND_CHESTPLATE));
		lista.add(new ItemStack(Material.DIAMOND_LEGGINGS));
		lista.add(new ItemStack(Material.DIAMOND_BOOTS));
		lista.add(new ItemStack(Material.STONE,64));
		lista.add(new ItemStack(Material.STONE,64));
		lista.add(new ItemStack(Material.STONE,64));
		lista.add(new ItemStack(Material.STONE,64));
		lista.add(new ItemStack(Material.STONE,64));
		lista.add(new ItemStack(Material.STONE,64));
		lista.add(new ItemStack(Material.STONE,64));
		Inventory inv = Bukkit.createInventory(null, 3 * 9, "Caixa basica...");

		new BukkitRunnable() {
			int duracao = 30;
			@Override
			public void run() {
				duracao--;
				for (int i = 0; i < inv.getSize(); i++) {
					inv.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1, (short) (1 + new Random().nextInt(15))));
				}
				int index = (1+new Random().nextInt(lista.size()))-1;
				ItemStack  itemGerado = lista.get(index);
				inv.setItem(14, itemGerado);
				if (duracao==0) {
					cancel();
				}
			}
		}.runTaskTimerAsynchronously(Main.getPlugin(Main.class), 5, 4);

		player.openInventory(inv);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void evento(AsyncPlayerPreLoginEvent e) {
		String nome = e.getName();
		if (Main.config.contains("Banimentos." + nome.toLowerCase())) {
			int dias = Main.config.getInt("Banimentos." + nome.toLowerCase() + ".duracao");
			long banidoDesde = Main.config.getLong("Banimentos." + nome.toLowerCase() + ".dia");
			String autor = Main.config.getString("Banimentos." + nome.toLowerCase() + ".autor");

			int diaEmMilis = 24 * 60 * 60 * 1000;

			long agora = System.currentTimeMillis();

			int tempoDoBan = diaEmMilis * dias;

			if (agora > banidoDesde + tempoDoBan) {
				OfflinePlayer jogador = Bukkit.getOfflinePlayer(nome);
				jogador.setBanned(false);
			} else {
				SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				String banidoDesdeFormatado = formatador.format(banidoDesde);
				String banidoAte = formatador.format(banidoDesde + tempoDoBan);
				e.setKickMessage(
						"§6 MinhaNetwork \n" + "§aBanido temporarimente\n" + "§aAutor: " + autor + "\n §aDias: " + dias
								+ "\n §aBanido desde: " + banidoDesdeFormatado + "\n §aAté: " + banidoAte);

				e.setLoginResult(Result.KICK_BANNED);
			}

		}
	}

}
