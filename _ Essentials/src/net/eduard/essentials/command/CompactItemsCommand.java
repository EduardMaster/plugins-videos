package net.eduard.essentials.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CompactItemsCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {

			Player p = (Player) sender;

			if (cmd.getName().equalsIgnoreCase("compactar")) {
				if (p.hasPermission("smash.compactar")) {

					try {
						int amountOfDiamonds = 0;
						int amountOfEmeralds = 0;
						int amountOfIron = 0;
						int amountOfGold = 0;
						int amountOfGlowstone = 0;
						int coal = 0;
						int redstone = 0;
						int lapis = 0;

						int itemsChanged = 0;

						for (ItemStack is : p.getInventory().getContents())
							if (is != null) {
								if (is.getType() == Material.DIAMOND) {
									p.getInventory().remove(is);
									amountOfDiamonds += is.getAmount();
								}
								if (is.getType() == Material.EMERALD) {
									amountOfEmeralds += is.getAmount();
									p.getInventory().remove(is);
								}
								if (is.getType() == Material.IRON_INGOT) {
									p.getInventory().remove(is);
									amountOfIron += is.getAmount();
								}
								if (is.getType() == Material.SLIME_BALL) {
									amountOfGlowstone += is.getAmount();
									p.getInventory().remove(is);
								}
								if (is.getType() == Material.GOLD_INGOT) {
									p.getInventory().remove(is);
									amountOfGold += is.getAmount();
								}
								if (is.getType() == Material.COAL) {
									p.getInventory().remove(is);
									coal += is.getAmount();
								}
								if (is.getType() == Material.REDSTONE) {
									redstone += is.getAmount();
									p.getInventory().remove(is);
								}
							}
						p.updateInventory();

						itemsChanged = amountOfDiamonds + amountOfEmeralds + amountOfGlowstone + amountOfGold
								+ amountOfIron + coal + redstone + lapis;

						int diamondsToTransform = amountOfDiamonds / 9;
						int diamondOverflow = amountOfDiamonds % 9;
						int emeraldsToTransform = amountOfEmeralds / 9;
						int emeraldsOverflow = amountOfEmeralds % 9;
						int ironToTransform = amountOfIron / 9;
						int ironOverflow = amountOfIron % 9;
						int goldToTransform = amountOfGold / 9;
						int goldOverflow = amountOfGold % 9;
						int glowstoneToTransform = amountOfGlowstone / 9;
						int glowstoneOverflow = amountOfGlowstone % 9;

						int rT = redstone / 9;
						int rO = redstone % 9;
						int lT = lapis / 9;
						int lO = lapis % 9;
						int cT = coal / 9;
						int cO = coal % 9;

						itemsChanged = itemsChanged - (diamondOverflow + emeraldsOverflow + ironOverflow + goldOverflow
								+ glowstoneOverflow + rO + cO + lO);

						p.getInventory().addItem(new ItemStack[] {
								new ItemStack(diamondsToTransform > 0 ? Material.DIAMOND_BLOCK : Material.AIR,
										diamondsToTransform),
								new ItemStack(emeraldsToTransform > 0 ? Material.EMERALD_BLOCK : Material.AIR,
										emeraldsToTransform),
								new ItemStack(diamondOverflow > 0 ? Material.DIAMOND : Material.AIR, diamondOverflow),
								new ItemStack(emeraldsOverflow > 0 ? Material.EMERALD : Material.AIR, emeraldsOverflow),
								new ItemStack(ironToTransform > 0 ? Material.IRON_BLOCK : Material.AIR,
										ironToTransform),
								new ItemStack(goldToTransform > 0 ? Material.GOLD_BLOCK : Material.AIR,
										goldToTransform),
								new ItemStack(glowstoneToTransform > 0 ? Material.SLIME_BLOCK : Material.AIR,
										glowstoneToTransform),
								new ItemStack(ironOverflow > 0 ? Material.IRON_INGOT : Material.AIR, ironOverflow),
								new ItemStack(goldOverflow > 0 ? Material.GOLD_INGOT : Material.AIR, goldOverflow),
								new ItemStack(glowstoneOverflow > 0 ? Material.SLIME_BALL : Material.AIR,
										glowstoneOverflow) });

						p.getInventory().addItem(
								new ItemStack[] { new ItemStack(rT > 0 ? Material.REDSTONE_BLOCK : Material.AIR, rT) });
						p.getInventory().addItem(
								new ItemStack[] { new ItemStack(lT > 0 ? Material.LAPIS_BLOCK : Material.AIR, lT) });
						p.getInventory().addItem(
								new ItemStack[] { new ItemStack(cT > 0 ? Material.COAL_BLOCK : Material.AIR, cT) });
						p.getInventory().addItem(
								new ItemStack[] { new ItemStack(rO > 0 ? Material.REDSTONE : Material.AIR, rO) });

						p.getInventory()
								.addItem(new ItemStack[] { new ItemStack(cO > 0 ? Material.COAL : Material.AIR, cO) });

						p.updateInventory();
						
					} catch (Exception e) {
						
						e.printStackTrace();
						return false;
					}
				} else {

//					MessageAPI.semPermissao(p, "Paladino");

				}
			}
		}
		return false;
	}

}
