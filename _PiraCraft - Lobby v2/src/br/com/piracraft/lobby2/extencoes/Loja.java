package br.com.piracraft.lobby2.extencoes;

import java.util.ArrayList;
import java.util.List;

public class Loja {

	public static List<Loja> shop = new ArrayList<Loja>();
	public static List<LojaMinecraft> lojaMinecraft = new ArrayList<LojaMinecraft>();

	private int item;
	private int valorCash;
	private int valorCoins;
	private int variacao;
	private int quantidade;
	private boolean ativo;
	private boolean isCash;
	private int idTabela;

	public int getItem() {
		return item;
	}

	public void setItem(int item) {
		this.item = item;
	}

	public int getValorCash() {
		return valorCash;
	}

	public void setValorCash(int valorCash) {
		this.valorCash = valorCash;
	}

	public int getValorCoins() {
		return valorCoins;
	}

	public void setValorCoins(int valorCoins) {
		this.valorCoins = valorCoins;
	}

	public int getVariacao() {
		return variacao;
	}

	public void setVariacao(int variacao) {
		this.variacao = variacao;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public boolean isCash() {
		return isCash;
	}

	public void setCash(boolean isCash) {
		this.isCash = isCash;
	}

	public int getIdTabela() {
		return idTabela;
	}

	public void setIdTabela(int idTabela) {
		this.idTabela = idTabela;
	}

}
