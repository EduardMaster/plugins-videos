package mega.plugins.kitpvp.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import mega.plugins.kitpvp.manager.Kit;
import mega.plugins.kitpvp.manager.KitPvP;

public class KitPvp extends Kit {

	public KitPvp() {
		super("PvP");
		register();
		setLore("", "", "");
		ItemStack item = KitPvP.newItem(Material.DIAMOND_SWORD,
				"§aESPADINHA MOSTRUOSA");
		item.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		
		getItems().add(item);
	}
}