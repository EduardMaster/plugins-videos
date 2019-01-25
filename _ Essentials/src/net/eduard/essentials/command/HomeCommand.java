package net.eduard.essentials.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.PluginDisableEvent;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.config.Config;
import net.eduard.api.lib.game.SoundEffect;
import net.eduard.api.lib.game.Title;
import net.eduard.api.lib.manager.CommandManager;

public class HomeCommand extends CommandManager {
	public HomeCommand() {
		super("home");
	}

	public Config config = new Config("homes.yml");
	public SoundEffect sound = SoundEffect.create("ENDERMAN_TELEPORT");
	public String message = "§6Voce teleportado para sua Home!";
	public String messageError = "§cSua home não foi setada!";
	public Title title = new Title(20, 20 * 2, 20, "§6Casa §e$home", "§bTeleportado para sua casa §3$home!");

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (Mine.onlyPlayer(sender)) {
			Player p = (Player) sender;
			String home = "home";
			if (args.length >= 1) {
				home = args[0];
			}
			String path = p.getUniqueId().toString() + "." + home;
			if (config.contains(path)) {
				final String homex = home;
				Mine.TIME.asyncDelay(new Runnable() {
					public void run() {
						p.teleport(config.getLocation(path));
						sound.create(p);
						sender.sendMessage(message.replace("$home", homex));
						Mine.sendTitle(p, title.getTitle().replace("$home", homex),
								title.getSubTitle().replace("$home", homex), title.getFadeIn(), title.getStay(),
								title.getFadeOut());

					}
				}

						, 20);

			} else {
				sender.sendMessage(messageError.replace("$home", home));
				config.remove(path);
			}

		}

		return true;
	}

	@EventHandler
	public void event(PluginDisableEvent e) {
		if (e.getPlugin().equals(getPluginInstance())) {
			config.saveConfig();
		}
	}

}
