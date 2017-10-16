
package net.eduard.fake;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.API;
import net.eduard.api.config.Config;
import net.eduard.api.setup.Mine;
import net.eduard.fake.comandos.FakeCommand;
import net.eduard.fake.event.FakeEvent;
/**
 * Preço: 15
 * @author Eduard-PC
 *
 */

public class Main extends JavaPlugin {

	@Override
	public void onDisable() {
		for (Player p : API.getPlayers()) {
			FakeAPI.reset(p);
		}
	}

	public static Config config;

	@Override
	public void onEnable() {

		config = new Config(this);
		config.add("name_reset_to_original",
				"&aSeu nome voltou para o estado original!");
		config.add("name_exist_exeption",
				"&cJa existe um player com esse nome! &e$name");
		config.add("name_change_sussefully",
				"&aVoce agora esta com esse nome! &e$name");
		config.add("kick_by_player_exist",
				"&cExiste um jogador com esse nome! &e$name");
		config.add("name_reset_by_other",
				"&aSeu nome foi resetado porque entrou um jogador com esse nome! &e$name");
		config.saveConfig();
		Bukkit.getPluginManager().registerEvents(new FakeEvent(), this);
		new FakeCommand().register();
		for (Player p : API.getPlayers()) {
			Mine.callEvent(new PlayerJoinEvent(p, null));
		}
	}
}
