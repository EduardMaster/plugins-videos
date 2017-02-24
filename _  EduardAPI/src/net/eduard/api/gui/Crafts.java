package net.eduard.api.gui;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

import net.eduard.api.API;
import net.eduard.api.config.Section;
import net.eduard.api.util.Save;

public class Crafts implements Save {
	private ShapedRecipe recipe;

	public ItemStack getResult() {
		return recipe.getResult();
	}

	public Crafts(ItemStack craftResult) {

		this.recipe = new ShapedRecipe(craftResult);
		recipe.shape("789", "456", "123");
	}

	char get1(int slot) {
		char x = 'A';
		slot--;
		for (int id = 1; id <= slot; id++) {
			x++;
		}
		return x;
	}

	private char getSlot(int slot) {

		return Character.forDigit(slot, 10);
	}

	public Crafts set(int slot, Material material) {

		return set(slot, material, 0);

	}

	public Crafts set(int slot, Material material, int data) {
		@SuppressWarnings("deprecation")
		MaterialData type = new MaterialData(material, (byte) data);
		return set(slot, type);
	}

	public Crafts set(int slot, MaterialData materialData) {
		recipe.setIngredient(getSlot(slot), materialData);
		return this;
	}

	public Crafts addRecipe() {
		Bukkit.addRecipe(recipe);
		return this;
	}

	public ShapedRecipe getRecipe() {
		return recipe;
	}

	public void save(Section section, Object value) {
		Crafts craft = (Crafts) value;
		section.set("result", craft.getResult());
		Map<Character, ItemStack> map = craft.recipe.getIngredientMap();
		for (int i = 1; i < 10; i++) {
			ItemStack item = map.get(getSlot(i));
			if (item == null)
				continue;
			@SuppressWarnings("deprecation")
			int id = item.getTypeId();
			section.set("slot"+i,  id+ "-" + item.getDurability());
		}
	}

	public Object get(Section section) {
		ItemStack result = section.getItem("result");
		Crafts craft = new Crafts(result);
		for (int i = 1; i < 10; i++) {
			try {
				if (section.contains("slot" + i)) {
					String text = section.getString("slot" + i);
					String[] split = text.split("-");
					@SuppressWarnings("deprecation")
					Material id = Material.getMaterial(API.toInt(split[0]));
					craft.set(i, id, API.toInt(split[1]));
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}	

		}

		return craft;
	}
}
