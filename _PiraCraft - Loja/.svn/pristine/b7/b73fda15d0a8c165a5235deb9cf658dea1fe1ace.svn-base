package loja;

import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Gui {

	public static HashMap<Player, String> comprou = new HashMap<Player, String>();

	public static void lojaMAIN(Player p) {
		Inventory inv = Bukkit.createInventory(p, 27, "§e§oPira§f§oCraft §b- §aMain");

		inv.setItem(4, ItemBuilder.criar("§eEscolha algum servidor para comprar algo!", Material.EMERALD));

		inv.setItem(12, ItemBuilder.criar("§cSkyWars", Material.GRASS, Arrays.asList("§e- §4Ainda sendo feito!")));
		inv.setItem(13, ItemBuilder.criar("§aKitPvP", Material.IRON_SWORD));
		inv.setItem(12, ItemBuilder.criar("§6HG", Material.MUSHROOM_SOUP,
				Arrays.asList("§e- §4Desativado.", "§e- §4Servidor em desenvolvimento.")));

		p.openInventory(inv);
	}

	public static void lojaKitsPvP(Player p) {
		Inventory inv = Bukkit.createInventory(p, 45, "§e§oPira§f§oCraft §b- §aKitPvP");

		inv.setItem(0, ItemBuilder.criar("§cVoltar", Material.CARPET, 14));
		inv.setItem(8, ItemBuilder.criar("§aAvancar", Material.CARPET, 5));

		ItemStack vidro = ItemAPI.Criar(Material.IRON_FENCE, 1, 0, " ", false);
		for (int i = 0; i < 8; i++) {
			inv.setItem(i, vidro);
		}

		for (int x = 0; x < Utils.nomess.size(); x++) {
			if (!inv.contains(Utils.material.get(x))) {
				if (comprou.containsKey(p)) {
					if (!comprou.get(p).equalsIgnoreCase(Utils.nomess.get(x))) {
						inv.addItem(ItemBuilder.criar("§a" + Utils.nomess.get(x), Utils.material.get(x)));
					}
				} else {
					inv.addItem(ItemBuilder.criar("§a" + Utils.nomess.get(x), Utils.material.get(x)));
				}
			}
		}

		ItemStack[] arrayOfItemStack;
		int i = (arrayOfItemStack = inv.getContents()).length;
		for (int i2 = 0; i2 < i; i2++) {
			ItemStack item = arrayOfItemStack[i2];
			if (item == null) {
				inv.setItem(inv.firstEmpty(), ItemAPI.Criar(Material.STAINED_GLASS_PANE, 1, 14, " ", false));
			}
		}

		p.openInventory(inv);
	}

	public static void lojaSW(Player p) {
		Inventory inv = Bukkit.createInventory(p, 54, "§e§oPira§f§oCraft §b- §aSkyWars");

		p.openInventory(inv);
	}

}
