package com.hcp.daays;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import com.hcp.Main;

public class BoxContent {

	public static void fillChest() {
		for (Block b : Schemas.caixa) {
			if (b.getState().getType() == Material.CHEST) {
				Chest c = (Chest) b.getState();
				c.getBlockInventory().setItem(new Random().nextInt(26),
						new ItemStack(Main.boxItems.get(new Random().nextInt(Main.boxItems.size()))));
			}
		}
	}

}
