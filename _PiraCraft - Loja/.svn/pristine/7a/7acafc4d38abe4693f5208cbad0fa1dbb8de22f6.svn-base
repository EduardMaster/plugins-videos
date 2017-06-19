package loja;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {

	public static ItemStack criar(String nome, Material item) {
		ItemStack a = new ItemStack(item);
		ItemMeta b = a.getItemMeta();
		b.setDisplayName(nome);
		a.setItemMeta(b);
		return a;
	}

	public static ItemStack criar(String nome, Material item, int numero) {
		ItemStack a = new ItemStack(item, 1, (short) numero);
		ItemMeta b = a.getItemMeta();
		b.setDisplayName(nome);
		a.setItemMeta(b);
		return a;
	}

	public static ItemStack criar(String nome, Material item, int numero, List<String> ln) {
		ItemStack a = new ItemStack(item, 1, (short) numero);
		ItemMeta b = a.getItemMeta();
		b.setDisplayName(nome);
		b.setLore(ln);
		a.setItemMeta(b);
		return a;
	}

	public static ItemStack criar(String nome, Material item, List<String> ln) {
		ItemStack a = new ItemStack(item);
		ItemMeta b = a.getItemMeta();
		b.setDisplayName(nome);
		b.setLore(ln);
		a.setItemMeta(b);
		return a;
	}

	public static Firework criar(Player p, Type tipo) {
		Firework a = p.getWorld().spawn(p.getLocation(), Firework.class);
		FireworkMeta b = a.getFireworkMeta();
		b.addEffect(FireworkEffect.builder().flicker(false).trail(true).with(tipo).withColor(Color.AQUA)
				.withColor(Color.AQUA).withColor(Color.GREEN).withColor(Color.LIME).withColor(Color.ORANGE)
				.withColor(Color.PURPLE).withColor(Color.YELLOW).withFade(Color.RED).build());
		b.setPower(0);
		a.setFireworkMeta(b);
		return a;
	}

}
