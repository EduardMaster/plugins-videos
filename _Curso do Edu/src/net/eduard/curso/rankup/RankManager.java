package net.eduard.curso.rankup;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import net.eduard.api.lib.VaultAPI;

public class RankManager {

	private Map<String, Rank> ranks = new HashMap<>();

	private Map<UUID, String> players = new HashMap<>();

	private String rankInicial = "novatao";

	public Rank getPrimeiroRank() {

		return ranks.get(rankInicial);

	}

	public Rank getRank(Player p) {

		return ranks.get(players.get(p));
	}
	public boolean temRank(Player p) {
		return players.containsKey(p.getUniqueId());
	}

	public boolean podeSubirDeRank(Player player) {
		if (temRank(player)) {

			Rank rank = getRank(player);

			return VaultAPI.getEconomy().has(player, rank.getPrice());

		}
		return false;
	}

	public void criarRank(String nome, String tag, double price,
			String proximoRank, String rankAnterior) {
		
		
		Rank rank = new Rank();
		rank.setName(nome);
		rank.setTag(tag);
		rank.setAnterior(rankAnterior);
		rank.setProximo(proximoRank);
		rank.setPrice(price);
		
		ranks.put(rank.getName(), rank);
		
	}
	public boolean existeRank(String nome) {
		return ranks.containsKey(nome);
	}
	public void deletarRank(String nome)
	{
		ranks.remove(nome);
	}

	public void subirDeRank(Player player) {
		Rank rank = getRank(player);

		players.put(player.getUniqueId(), rank.getProximo());

		VaultAPI.getEconomy().withdrawPlayer(player, rank.getPrice());

	}

	public Map<String, Rank> getRanks() {
		return ranks;
	}

	public void setRanks(Map<String, Rank> ranks) {
		this.ranks = ranks;
	}

	public String getRankInicial() {
		return rankInicial;
	}

	public void setRankInicial(String rankInicial) {
		this.rankInicial = rankInicial;
	}

	public Map<UUID, String> getPlayers() {
		return players;
	}

	public void setPlayers(Map<UUID, String> players) {
		this.players = players;
	}

}
