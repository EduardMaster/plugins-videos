package net.eduard.curso.aula_caixas;

import java.util.ArrayList;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

import net.eduard.api.lib.storage.Storable;

public class Caixa implements Storable {
	
	private String nome;
	private ItemStack iconeLoja;
	private ItemStack caixa;
	private ArrayList<ItemStack> premios = new ArrayList<>();
	
	
	

	@Override
	public Object restore(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void store(Map<String, Object> map, Object object) {
		// TODO Auto-generated method stub
		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ItemStack getIconeLoja() {
		return iconeLoja;
	}

	public void setIconeLoja(ItemStack iconeLoja) {
		this.iconeLoja = iconeLoja;
	}

	public ItemStack getCaixa() {
		return caixa;
	}

	public void setCaixa(ItemStack caixa) {
		this.caixa = caixa;
	}

	public ArrayList<ItemStack> getPremios() {
		return premios;
	}

	public void setPremios(ArrayList<ItemStack> premios) {
		this.premios = premios;
	}

}
