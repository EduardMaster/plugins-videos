
package net.eduard.parkour;

import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.config.Config;
/**
 * Preço: 35
 * 
 * @author Eduard-PC
 *
 */
public class Main extends JavaPlugin {
	private static Main plugin;
	private Config config;
	private Parkour parkour;

	public Config config() {
		return config;
	}
	public static Main getInstance() {
		return plugin;
	}
	@Override
	public void onEnable() {
		plugin = this;

		config = new Config(this);
		setParkour(new Parkour(this));
		config.add("Create", "&bO Parkour $arena foi criado!");
		config.add("Delete", "&bO Parkour $arena foi deletado!");
		config.add("SetSpawn", "&bO Spawn do Parkour $arena foi setado!");
		config.add("SetEnd", "&bO Final do Parkour $arena foi setado!");
		config.add("AlreadyExists", "&bEste Parkour ja foi criado!");
		config.add("Invalid", "&cEste Parkour nao foi criado!");
		config.add("SetLobby", "&bLobby do Parkour foi setado!");
		config.add("Reload", "&aOs Parkours foram recarregados!");
		config.add("SetCheck", "&bCheckPoint adicionado!");
		config.add("Creating", "&6Voce esta criando o Parkour &e$arena");
		config.add("CheckPoint", "&6Checkpoint encontrado!");
		config.add("NoLobby", "&cO Lobby do Parkour nao foi setado!");
		config.add("Lobby", "&6Voce foi para o Lobby do Parkour!");
		config.add("Join", "&6Voce entrou no Parkour &e$arena");
		config.add("Quit", "&6Voce desistiu do Parkour $arena");
		config.add("OnlyAdmin", "&cPrecisa da permissao parkour.admin");
		config.add("Win", "&6Voce terminou o Parkour &e$player &2(&a$money&2)");
		config.add("WinBroadcast",
				"&6O jogador &e$player &6venceu o Parkour &e$arena &2(&a$money&2)");
		config.saveConfig();
	}

	@Override
	public void onDisable() {
	}
	public Parkour getParkour() {
		return parkour;
	}
	public void setParkour(Parkour parkour) {
		this.parkour = parkour;
	}

}
