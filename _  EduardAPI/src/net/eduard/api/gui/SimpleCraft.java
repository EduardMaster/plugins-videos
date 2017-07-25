package net.eduard.api.gui;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.material.MaterialData;

import net.eduard.api.config.ConfigSection;
import net.eduard.api.util.Save;

public final class SimpleCraft implements Save {

	private ShapelessRecipe recipe;

	public SimpleCraft(ItemStack result) {
		this.recipe = new ShapelessRecipe(result);
	}

	public SimpleCraft add(Material ingredient, int data) {
		@SuppressWarnings("deprecation")
		MaterialData value = new MaterialData(ingredient, (byte) data);
		recipe.addIngredient(value);
		return this;
	}

	public SimpleCraft add(Material ingredient) {
		return add(ingredient, 0);
	}

	public SimpleCraft addRecipe() {

		Bukkit.addRecipe(getRecipe());
		return this;
	}

	public ItemStack getResult() {
		return recipe.getResult();
	}

	public ShapelessRecipe getRecipe() {

		return recipe;
	}

	public void save(ConfigSection section, Object value) {
		SimpleCraft craft = (SimpleCraft) value;
		section.set("result", craft.getResult());
		ArrayList<String> list = new ArrayList<>();
		for (ItemStack item : craft.recipe.getIngredientList()) {
			@SuppressWarnings("deprecation")
			int id = item.getTypeId();
			list.add("" + id + "-" + item.getDurability());
		}
		section.set("items", list);
	}

	public Object get(ConfigSection section) {
		ItemStack result = section.getItem("result");
		SimpleCraft craft = new SimpleCraft(result);
		for (String line : section.getStringList("items")) {
			try {
				String[] split = line.split("-");
				@SuppressWarnings("deprecation")
				Material id = Material.getMaterial(ConfigSection.toInt(split[0]));
				Integer data = ConfigSection.toInt(split[1]);
				craft.add(id, data);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return craft;
	}
}