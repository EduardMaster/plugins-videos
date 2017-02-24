
package net.eduard.fake;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.API;
import net.eduard.api.config.Config;
import net.eduard.api.manager.PlayerAPI;
import net.eduard.fake.command.FakeCommand;
import net.eduard.fake.event.FakeEvent;

public class Main extends JavaPlugin implements Listener {

	public static HashMap<String, String> fakes = new HashMap<>();

	public void onDisable() {

		for (Entry<String, String> fake : fakes.entrySet()) {
			Player p = API.getPlayer(fake.getValue());
			p.setDisplayName(fake.getKey());
			p.setPlayerListName(fake.getKey());
			PlayerAPI.changeName(p, fake.getKey());
		}

	}

	public static Config config;

	public void onEnable() {

		config = new Config(this);
		config.add("name_reset_to_original", "&aSeu nome voltou para o estado original!");
		config.add("name_exist_exeption", "&cJa existe um player com esse nome! &e$name");
		config.add("name_change_sussefully", "&aVoce agora esta com esse nome! &e$name");
		config.add("kick_by_player_exist", "&cExiste um jogador com esse nome! &e$name");
		config.add("name_reset_by_other", "&aSeu nome foi resetado porque entrou um jogador com esse nome! &e$name");
		config.saveConfig();
		new FakeEvent();
		new FakeCommand();
		for (Player p : API.getPlayers()) {
			fakes.put(p.getName(), p.getName());
		}
	}
}
