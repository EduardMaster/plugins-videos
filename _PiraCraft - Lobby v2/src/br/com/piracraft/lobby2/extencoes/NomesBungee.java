package br.com.piracraft.lobby2.extencoes;

import java.util.ArrayList;
import java.util.List;

public class NomesBungee {

	public static List<NomesBungee> nomes = new ArrayList<NomesBungee>();

	private String nome;
	private int idsala;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getIdsala() {
		return idsala;
	}

	public void setIdsala(int idsala) {
		this.idsala = idsala;
	}

}
