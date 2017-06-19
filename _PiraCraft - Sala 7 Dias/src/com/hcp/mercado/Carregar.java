package com.hcp.mercado;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import br.com.piracraft.api.Main;
import br.com.piracraft.api.util.MySQL;

public class Carregar {

	public static List<API> itens = new ArrayList<API>();

	public static String getItem(int id, String variacao) {
		String a = null;
		if(variacao.equalsIgnoreCase("0")){
			try {
				ResultSet rs = MySQL.getQueryResult(
						"SELECT * FROM LOJAMINECRAFT WHERE ID_ITEM = '" + id + "'");
				if (rs.next()) {
					a = rs.getString("ID");
				}
				rs.getStatement().getConnection().close();
				;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			try {
				ResultSet rs = MySQL.getQueryResult(
						"SELECT * FROM LOJAMINECRAFT WHERE ID_ITEM = '" + id + "' AND ID_VARIACAO = '" + variacao + "'");
				if (rs.next()) {
					a = rs.getString("ID");
				}
				rs.getStatement().getConnection().close();
				;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return a;
	}

	@SuppressWarnings("deprecation")
	public static boolean containsItem(Player p, ItemStack a) {
		boolean ab = false;
		for (int x = 0; x < Carregar.itens.size(); x++) {
			if (Carregar.itens.get(x).getUUID().equalsIgnoreCase(Main.uuid.get(p))) {
				if(Carregar.itens.get(x).getNetwork() == Main.network.get(p)){
					if (Carregar.itens.get(x).getId() == a.getType().getId()
							&& Carregar.itens.get(x).getVariacao() == (int) a.getData().getData()) {
						ab = true;
					}
				}
			}
		}
		return ab;
	}

	public static boolean containsPlayer(String uuid) {
		boolean ab = false;
		List<String> uuids = new ArrayList<String>();
		for (int x = 0; x < Carregar.itens.size(); x++) {
			uuids.add(Carregar.itens.get(x).getUUID());
		}
		if (uuids.contains(uuid)) {
			ab = true;
		}
		return ab;
	}

}
