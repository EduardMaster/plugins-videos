package net.eduard.api.manager;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.API;
import net.eduard.api.dev.Game;
import net.eduard.api.util.SimpleEffect;

@SuppressWarnings("unchecked")
public class CMD implements CommandExecutor, TabCompleter {

	private PluginCommand command;

	private String permission;

	public CMD(JavaPlugin plugin) {
		String name = getClass().getSimpleName().toLowerCase();
		setPermission(name + ".use");
		if (name.contains("cmd") | name.contains("command")) {
			name = name.replace("cmd", "").replace("command", "");
			PluginCommand cmd;
			if (plugin == null) {
				cmd = Bukkit.getPluginCommand(name);
			} else {
				cmd = plugin.getCommand(name);
			}

			if (cmd != null) {
				plugin = (JavaPlugin) cmd.getPlugin();
				setCommand(cmd);
				cmd.setExecutor(this);
				cmd.setPermissionMessage("§cVocê não tem permissão para usar este comando!");
				cmd.setPermission(name + ".use");
				cmd.setDescription("§a" + cmd.getDescription());
				cmd.setUsage("§c" + cmd.getUsage());
				API.console(
						"§bCommandAPI §fO comando §a" + name + " §ffoi registrado para o Plugin §b" + plugin.getName());

				times++;
			} else {
				API.console("§bCommandAPI §fO comando §a" + name
						+ " §fnao foi registrado na plugin.yml de nenhum Plugin do Servidor");
			}
		}

	}

	public CMD() {
		this(null);

	}

	public static void removeAliaseFromCommand(PluginCommand cmd, String aliase) {
		String cmdName = cmd.getName().toLowerCase();
		if (getCommands().containsKey(aliase)) {
			getCommands().remove(aliase);
			API.console("§bCommandAPI §fremovendo aliase §a" + aliase + "§f do comando §b" + cmdName);
		} else {
			API.console("§bCommandAPI §fnao foi encontrado a aliase §a" + aliase + "§f do comando §b" + cmdName);
		}

	}

	public static void removeCommand(String name) {
		if (getCommands().containsKey(name)) {
			PluginCommand cmd = Bukkit.getPluginCommand(name);
			String pluginName = cmd.getPlugin().getName();
			String cmdName = cmd.getName();
			for (String aliase : cmd.getAliases()) {
				removeAliaseFromCommand(cmd, aliase);
				removeAliaseFromCommand(cmd, pluginName.toLowerCase() + ":" + aliase);
			}
			try {
				getCommands().remove(cmd.getName());
			} catch (Exception e) {
			}
			API.console("§bCommandAPI §fremovendo o comando §a" + cmdName + "§f do Plugin §b" + pluginName);
		} else {
			API.console("§bCommandAPI §fnao foi encontrado a commando §a" + name);
		}

	}

	public PluginCommand getCommand() {
		return command;
	}

	public void setCommand(PluginCommand command) {
		this.command = command;
	}

	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return null;
	}

	public void command(CommandSender sender, String[] args) {

	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length > 0) {
			CMD cmd = this;
			for (String arg : args) {
				try {
					Field field = cmd.getClass().getField(arg);
					if (field.getType().equals(CMD.class)) {
						cmd = (CMD) field.get(cmd);
					}
				} catch (Exception ex) {
				}
				// if (cmd.getSubCommands().containsKey(arg.toLowerCase())) {
				// cmd = cmd.getSubCommands().get(arg.toLowerCase());
				// }
			}
			if (cmd != this) {
				if (!sender.hasPermission(cmd.getPermission())) {
					sender.sendMessage(command.getPermissionMessage());
					return true;
				}
				cmd.onCommand(sender, command, label, args);
				cmd.command(sender, args);
			}
			return true;
		}
		return false;

	}

	public static Map<String, Command> getCommands() {
		return cmds;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	private static int times;
	private static Map<String, Command> cmds = new HashMap<>();
	static {
		try {
			Object map = RexAPI.getValue(Bukkit.getServer().getPluginManager(), "commandMap");
			cmds = (Map<String, Command>) RexAPI.getValue(map, "knownCommands");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		new Game(2).delay(new SimpleEffect() {

			public void effect() {
				API.console("§bCommandAPI §fforam registrados mais de §a" + times
						+ "§f comandos §8(CommandExecutors) §fno Servidor!");
			}
		});
	}
}
