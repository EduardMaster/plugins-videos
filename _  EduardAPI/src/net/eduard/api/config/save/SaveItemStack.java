package net.eduard.api.config.save;

import java.util.List;
import java.util.Map.Entry;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.config.ConfigSection;
import net.eduard.api.manager.ItemAPI;
import net.eduard.api.util.Save;

public class SaveItemStack implements Save {

	public ItemStack get(ConfigSection section) {
		int id = section.getInt("id");
		int amount = section.getInt("amount");
		int data = section.getInt("data");
		@SuppressWarnings("deprecation")
		ItemStack item = new ItemStack(id, amount, (short) data);
		String name = section.message("name");
		if (!name.isEmpty()) {
			ItemAPI.setName(item, name);
		}
		List<String> lore = section.getMessages("lore");
		if (!lore.isEmpty()) {
			ItemAPI.setLore(item, lore);
		}
		String enchants = section.getString("enchants");
		if (!enchants.isEmpty()) {
			if (enchants.contains(", ")) {
				String[] split = enchants.split(", ");
				for (String enchs : split) {
					String[] sub = enchs.split("-");
					@SuppressWarnings("deprecation")
					Enchantment ench = Enchantment.getById(ConfigSection.toInt(sub[0]));
					Integer level = ConfigSection.toInt(sub[1]);
					item.addUnsafeEnchantment(ench, level);

				}
			} else {
				String[] split = enchants.split("-");
				@SuppressWarnings("deprecation")
				Enchantment ench = Enchantment.getById(ConfigSection.toInt(split[0]));
				Integer level = ConfigSection.toInt(split[1]);
				item.addUnsafeEnchantment(ench, level);

			}
		}
		return item;
	}

	@SuppressWarnings("deprecation")
	public void save(ConfigSection CS, Object value) {
		ItemStack item = (ItemStack) value;
		CS.set("id", item.getTypeId());
		CS.set("data", item.getDurability());
		CS.set("amount", item.getAmount());
		CS.set("name", ItemAPI.getName(item));
		CS.set("lore", ItemAPI.getLore(item));
		String enchants = "";
		if (item.getItemMeta().hasEnchants()) {
			StringBuilder b = new StringBuilder();
			int id = 0;
			for (Entry<Enchantment, Integer> map : item.getEnchantments().entrySet()) {
				if (id > 0)
					b.append(", ");
				Enchantment enchantment = map.getKey();
				b.append(enchantment.getId() + "-" + map.getValue());
				id++;
			}
			enchants = b.toString();
		}
		CS.set("enchants", enchants);
	};

}
