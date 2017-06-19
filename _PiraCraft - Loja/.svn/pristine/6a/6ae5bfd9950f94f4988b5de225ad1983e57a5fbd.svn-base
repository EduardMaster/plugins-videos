package loja;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import br.com.piracraft.api.PiraCraftAPI;

public class Utils {

	public static List<Material> material = new ArrayList<Material>();
	public static List<String> nomess = new ArrayList<String>();
	public static List<Integer> coins = new ArrayList<Integer>();
	public static List<Integer> produto = new ArrayList<Integer>();

	public static HashMap<UUID, String> info = new HashMap<UUID, String>();

	public static void putThingsOnList(Player p) {
		if (Main.server.toLowerCase().contains("kitpvp")) {
			try {
				ResultSet rs = br.com.piracraft.api.Main.getMysql().conectar().createStatement()
						.executeQuery("select * from V_MINIGAME_PRODUTOS_KITPVP "
								+ "where ID_PRODUTO not in (select ID_PRODUTOS from MINIGAMES_PRODUTOS_COMPRA_ITENS MPCI where uuid = '"
								+ new PiraCraftAPI(p).getUUID() + "' and status = '1' ORDER BY `ID_PRODUTO`)");

				while (rs.next()) {
					nomess.add(rs.getString("NOME_PRODUTO"));
					material.add(Material.getMaterial(rs.getString("ITEM_ICONE")));
					coins.add(rs.getInt("COINS"));
					produto.add(rs.getInt("ID_PRODUTO"));
				}
				rs.getStatement().getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (Main.server.toLowerCase().contains("skywars")) {
			try {
				ResultSet rs = br.com.piracraft.api.Main.getMysql().conectar().createStatement()
						.executeQuery("select * from V_MINIGAME_PRODUTOS_SKYWARS "
								+ "where ID_PRODUTO not in (select ID_PRODUTOS from MINIGAMES_PRODUTOS_COMPRA_ITENS MPCI where uuid = '"
								+ new PiraCraftAPI(p).getUUID() + "' and status = '1' ORDER BY `ID_PRODUTO`)");

				while (rs.next()) {
					nomess.add(rs.getString("NOME_PRODUTO"));
					material.add(Material.getMaterial(rs.getString("ITEM_ICONE")));
					coins.add(rs.getInt("COINS"));
					produto.add(rs.getInt("ID_PRODUTO"));
				}
				rs.getStatement().getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (Main.server.toLowerCase().contains("lobby")) {

		}
	}

	public static int getProduct(String kit) {
		for (int x = 0; x < produto.size(); x++) {
			if (nomess.get(x).equalsIgnoreCase(kit)) {
				return produto.get(x);
			}
		}
		return 0;
	}

}