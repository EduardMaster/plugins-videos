package net.eduard.curso.aula_coins;

import java.util.HashMap;

import org.bukkit.entity.Player;

import net.eduard.api.lib.BukkitConfig;
import net.eduard.curso.CursoMain;

public class CoinsAPI {

	public static HashMap<String, Double> betas = new HashMap<>();

	public static BukkitConfig config = new BukkitConfig("betas.yml", CursoMain.getInstance());

	public static void save() {

		config.set("contas", betas);
		config.saveConfig();

	}

	@SuppressWarnings("unchecked")
	public static void reload() {

		config.reloadConfig();
		betas = (HashMap<String, Double>) config.get("contas");
		
		
		for (String key : config.getSection("contas").getKeys(false)) {
			betas.put(key, config.getDouble("contas."+key));
		}

	}

	public static void setCoins(Player p, double dindin) {
		betas.put(p.getName().toLowerCase(), dindin);
	}

	public static void addCoins(Player p, double dindin) {
		setCoins(p, getCoins(p) + dindin);
	}

	public static void removeCoins(Player p, double dindin) {
		setCoins(p, getCoins(p) - dindin);
	}

	public static double getCoins(Player p) {
		return betas.getOrDefault(p.getName().toLowerCase(), 0D);
	}

}
