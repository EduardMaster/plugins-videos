package net.eduard.eduardapi;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.API;
import net.eduard.api.command.AdminCommand;
import net.eduard.api.command.EduardCommand;
import net.eduard.api.command.GamemodeCommand;
import net.eduard.api.command.GotoCommand;
import net.eduard.api.config.Section;
import net.eduard.api.game.Tag;
import net.eduard.api.manager.CMD;
import net.eduard.api.manager.EduardPlugin;
import net.eduard.api.manager.RexAPI;
import net.eduard.api.util.Cs;

public class EduardAPI extends EduardPlugin implements Listener {
	private static JavaPlugin plugin;

	public static JavaPlugin getInstance() {
		return plugin;
	}

	public void init() {
		plugin = this;
		API.resetScoreboards();
		Cs.consoleMessage("§bEduardAPI §fBarAPI §aativado!");
		Cs.consoleMessage("§bEduardAPI §fDataBase §agerada!");
		RexAPI.saveObjects();
		API.loadMaps();
		Cs.consoleMessage("§bEduardAPI §fMapas §acarregados!");
		
		API.AUTO_RESPAWN = getConfig().getBoolean("auto-respawn");
		API.NO_JOIN_MESSAGE = getConfig().getBoolean("no-join-message");
		API.NO_QUIT_MESSAGE = getConfig().getBoolean("no-quit-message");
		API.NO_DEATH_MESSAGE = getConfig().getBoolean("no-death-message");
		API.ON_JOIN = config.message("on-join-message");
		API.ON_QUIT = config.message("on-quit-message");
		API.SERVER_TAG = config.message("server-tag");
		Cs.consoleMessage("§bEduardAPI §aativado!");
		API.event(this);
		for (Player p : API.getPlayers()) {
			API.callEvent(new PlayerJoinEvent(p, null));
		}
		Section.register(CMD.class);
		Section.register(AdminCommand.class);
		RexAPI.commands(config.getSection("commands"),new GamemodeCommand(),new GotoCommand());
		new EduardCommand().register();
		config.saveConfig();
	}

	public void onDisable() {
		API.saveMaps();
		Cs.consoleMessage("§bEduardAPI §aMapas salvados!");
		Cs.consoleMessage("§bEduardAPI §cdesativado!");
	}

	@EventHandler
	public void entrar(PlayerJoinEvent e) {

		Player p = e.getPlayer();
		if (!Tag.getTags().containsKey(p)) {
			API.resetTag(p);
		}
		if (getConfig().getBoolean("save-players")) {
			RexAPI.saveObject("Players/" + p.getName() + " " + p.getUniqueId(),
					p);
		}
		if (getConfig().getBoolean("custom-join-message")) {
			e.setJoinMessage(API.ON_JOIN.replace("$player", p.getName()));
		}
		if (API.NO_JOIN_MESSAGE) {
			e.setJoinMessage(null);
			return;
		}

	}
	@EventHandler
	public void morrer(PlayerDeathEvent e) {
		Player p = e.getEntity();
		if (API.AUTO_RESPAWN) {
			if (p.hasPermission("eduardapi.autorespawn")) {
				API.TIME.delay(1L, new Runnable() {

					public void run() {
						if (p.isDead()) {
							p.setFireTicks(0);
							try {
								RexAPI.makeRespawn(p);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}
				});
			}

		}
		if (API.NO_DEATH_MESSAGE) {
			e.setDeathMessage("");
		}
	}
	@EventHandler
	public void sair(PlayerQuitEvent e) {

		Player p = e.getPlayer();
		if (getConfig().getBoolean("custom-quit-message"))
			e.setQuitMessage(API.ON_QUIT.replace("$player", p.getName()));
		if (API.NO_QUIT_MESSAGE) {
			e.setQuitMessage("");
		}

	}
}
