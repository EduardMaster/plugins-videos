package net.eduard.live;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Kit {

	private String nome;
	private ItemStack icone;
	private int cooldown;
	private ArrayList<ItemStack> itens = new ArrayList<>();
	public String getNome() {
		return nome;
	}
	public void darKit(Player p) {
		for (ItemStack item : itens) {
			p.getInventory().addItem(item);
		}
		p.sendMessage("§aVoce ganhou o kit "+ nome);
		
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public ArrayList<ItemStack> getItens() {
		return itens;
	}
	public void setItens(ArrayList<ItemStack> itens) {
		this.itens = itens;
	}
	public ItemStack getIcone() {
		return icone;
	}
	public void setIcone(ItemStack icone) {
		this.icone = icone;
	}
	public int getCooldown() {
		return cooldown;
	}
	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}
	
}
