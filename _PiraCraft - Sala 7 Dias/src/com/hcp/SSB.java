package com.hcp;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class SSB {

	private Scoreboard scoreboard;

	private String titulo;
	private Map<String, Integer> scores;
	private List<Team> teams;

	public SSB(String titulo) {
		this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		this.titulo = titulo;
		this.scores = Maps.newLinkedHashMap();
		this.teams = Lists.newArrayList();
	}

	public void LinhaEmBranco() {
		add(" ");
	}

	public void add(String texto) {
		add(texto, null);
	}

	public void add(String texto, Integer score) {
		Preconditions.checkArgument(texto.length() < 48, "Somente 48 letras!");
		texto = fixDuplicates(texto);
		scores.put(texto, score);
	}

	private String fixDuplicates(String texto) {
		while (scores.containsKey(texto))
			texto += "§r";
		if (texto.length() > 48)
			texto = texto.substring(0, 47);
		return texto;
	}

	private Map.Entry<Team, String> createTeam(String texto) {
		String result = "";
		if (texto.length() <= 16)
			return new AbstractMap.SimpleEntry<>(null, texto);
		Team team = scoreboard.registerNewTeam("text-" + scoreboard.getTeams().size());
		Iterator<String> iterator = Splitter.fixedLength(16).split(texto).iterator();
		team.setPrefix(iterator.next());
		result = iterator.next();
		if (texto.length() > 32)
			team.setSuffix(iterator.next());
		teams.add(team);
		return new AbstractMap.SimpleEntry<>(team, result);
	}

	@SuppressWarnings("deprecation")
	public void build() {
		Objective obj = scoreboard.registerNewObjective((titulo.length() > 16 ? titulo.substring(0, 15) : titulo),
				"dummy");
		obj.setDisplayName(titulo);
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);

		int index = scores.size();

		for (Map.Entry<String, Integer> text : scores.entrySet()) {
			Map.Entry<Team, String> team = createTeam(text.getKey());
			Integer score = text.getValue() != null ? text.getValue() : index;
			OfflinePlayer player = Bukkit.getOfflinePlayer(team.getValue());
			if (team.getKey() != null)
				team.getKey().addPlayer(player);
			obj.getScore(player).setScore(score);
			index -= 1;
		}
	}

	public void reset() {
		titulo = null;
		scores.clear();
		for (Team t : teams)
			t.unregister();
		teams.clear();
	}

	public Scoreboard getScoreboard() {
		return scoreboard;
	}

	public void send(Player... players) {
		for (Player p : players)
			p.setScoreboard(scoreboard);
	}

}
