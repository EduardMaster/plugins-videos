package loja;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import br.com.piracraft.api.PiraCraftAPI;

public class CreateGui {

	private Player owner;
	private String title;
	private int size;
	private String kit;

	public CreateGui(Player p, int size, String title, String kit) {
		owner = p;
		this.title = title;
		this.size = size;
		this.kit = kit;
	}

	public String getKit() {
		return kit.toUpperCase();
	}

	public String getTitle() {
		return title;
	}

	public Player getOwner() {
		return owner;
	}

	public int getSize() {
		return size;
	}

	public int getPrice() {
		for (int x = 0; x < Utils.coins.size(); x++) {
			if (Utils.nomess.get(x).equalsIgnoreCase(kit)) {
				return Utils.coins.get(x);
			}
		}
		return 0;
	}

	public Inventory buildCoins() {
		Inventory inv = Bukkit.createInventory(getOwner(), getSize(), getTitle());

		for (int i = 0; i < getSize(); i++) {
			inv.setItem(i, ItemAPI.Criar(Material.STAINED_GLASS_PANE, 1, 5, " ", false));
		}

		inv.setItem(0, ItemBuilder.criar("§cVoltar", Material.CARPET, 14));
		inv.setItem(4,
				ItemBuilder.criar("§eCoins §c§l §6" + new PiraCraftAPI(getOwner()).getCoins(), Material.GOLD_INGOT));

		inv.setItem(11, ItemBuilder.criar("§a1 dia", Material.IRON_INGOT,
				Arrays.asList(" ", "§ePreco §c§l§ §2" + getPrice(), "§eAproveite este kit por §f§l24h§e!")));
		inv.setItem(13, ItemBuilder.criar("§a7 dias", Material.DIAMOND,
				Arrays.asList(" ", "§ePreco §c§l§ §2" + getPrice() * 3, "§eAproveite este kit por §f§l7 dias§e!")));
		inv.setItem(15, ItemBuilder.criar("§a30 dias", Material.EMERALD,
				Arrays.asList(" ", "§ePreco §c§l§ §2" + getPrice() * 5, "§eAproveite este kit por §f§l30 dias§e!")));

		return inv;
	}

	public Inventory buildCash() {
		Inventory inv = Bukkit.createInventory(getOwner(), getSize(), getTitle());

		for (int i = 0; i < getSize(); i++) {
			inv.setItem(i, ItemAPI.Criar(Material.STAINED_GLASS_PANE, 1, 14, " ", false));
		}

		inv.setItem(10, ItemBuilder.criar("§a1 dia", Material.PAPER, Arrays.asList(" ", "§ePre§o §c§l§ §2")));
		inv.setItem(10, ItemBuilder.criar("§a7 dias", Material.PAPER));
		inv.setItem(10, ItemBuilder.criar("§a30 dias", Material.PAPER));

		return inv;
	}

}
