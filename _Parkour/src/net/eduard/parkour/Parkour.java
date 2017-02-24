package net.eduard.parkour;

import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.config.Config;

public class Parkour extends Config {
	public Parkour(JavaPlugin plugin) {
		super(plugin);
		add("Create", "&bO Parkour $arena foi criado!");
		add("Delete", "&bO Parkour $arena foi deletado!");
		add("SetSpawn", "&bO Spawn do Parkour $arena foi setado!");
		add("SetEnd", "&bO Final do Parkour $arena foi setado!");
		add("AlreadyExists", "&bEste Parkour ja foi criado!");
		add("Invalid", "&cEste Parkour nao foi criado!");
		add("SetLobby", "&bLobby do Parkour foi setado!");
		add("Reload", "&aOs Parkours foram recarregados!");
		add("SetCheck", "&bCheckPoint adicionado!");
		add("Creating", "&6Voce esta criando o Parkour &e$arena");
		add("CheckPoint", "&6Checkpoint encontrado!");
		add("NoLobby", "&cO Lobby do Parkour nao foi setado!");
		add("Lobby", "&6Voce foi para o Lobby do Parkour!");
		add("Join", "&6Voce entrou no Parkour &e$arena");
		add("Quit", "&6Voce desistiu do Parkour $arena");
		add("OnlyAdmin", "&cPrecisa da permissao parkour.admin");
		add("Win", "&6Voce terminou o Parkour &e$player &2(&a$money&2)");
		add("WinBroadcast", "&6O jogador &e$player &6venceu o Parkour &e$arena &2(&a$money&2)");
		saveConfig();
	}
}
