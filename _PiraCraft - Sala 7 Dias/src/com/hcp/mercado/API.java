package com.hcp.mercado;

public class API {

	private String itemName;
	private int id;
	private int quant;
	private String p;
	private int ativo;
	private int network;
	private int variacao;

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getQuantidade() {
		return quant;
	}

	public void setQuantidade(int quant) {
		this.quant = quant;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUUID(String p) {
		this.p = p;
	}

	public String getUUID() {
		return this.p;
	}

	public void setAtivo(int i) {
		this.ativo = i;
	}

	public int getAtivo() {
		return this.ativo;
	}

	public int getNetwork() {
		return network;
	}

	public void setNetwork(int network) {
		this.network = network;
	}

	public int getVariacao() {
		return variacao;
	}

	public void setVariacao(int variacao) {
		this.variacao = variacao;
	}

}
