package net.eduard.live07;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ComandoCompactar implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;

			PlayerInventory inv = p.getInventory();

			int ferrenosnaoCompactados = 0;
			int ferrosQuantidadeFinal = 0;
			for (ItemStack item : inv.getContents()) {
				if (item == null)
					continue;
				if (item.getType() == Material.IRON_INGOT) {

					ferrosQuantidadeFinal = ferrosQuantidadeFinal + item.getAmount();

				}
			}
			inv.remove(Material.IRON_INGOT);

			if (ferrosQuantidadeFinal == 0) {
				p.sendMessage("§cNao tinhas ferros");
			} else {

				ferrenosnaoCompactados = ferrosQuantidadeFinal % 9;

				int ferroCompactado = (int) (ferrosQuantidadeFinal / 9D);
				p.getInventory().addItem(new ItemStack(Material.IRON_BLOCK, ferroCompactado));
				if (ferrenosnaoCompactados > 0) {
					p.getInventory().addItem(new ItemStack(Material.IRON_INGOT, ferrenosnaoCompactados));
				}

			}

		}
		return false;
	}

}
