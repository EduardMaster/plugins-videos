package net.eduard.api.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;

import net.eduard.api.API;
import net.eduard.api.config.ConfigSection;
import net.eduard.api.setup.ExtraAPI;

public abstract class CMD extends Manager
		implements
			TabCompleter,
			CommandExecutor {

	private transient PluginCommand command;

	private transient Map<String, CMD> commands = new HashMap<>();

	private String permission = getCommandName() + ".use";

	private String name = getCommandName();

	private String usage;

	private List<String> aliases = new ArrayList<>();

	private String permissionMessage = API.NO_PERMISSION;

	private String description = "Exemplo de descricao";

	public String getCommandName() {

		return getClass().getSimpleName().toLowerCase().replace("sub", "")
				.replace("subcommand", "").replace("comando", "")
				.replace("command", "").replace("cmd", "")
				.replace("eduard", "");
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command,
			String label, String[] args) {
		return null;
	}

	public void broadcast(String message) {
		API.broadcast(message, permission);

	}

	public CMD() {
		this("");
	}
	public CMD(String name) {
		this.name = name;
		if (name.equals("")) {
			this.name = getCommandName();
		}
		preRegister();
	}
	public void preRegister() {
		command = Bukkit.getPluginCommand(name);
		if (command == null) {
			return;
		}
		setCommand(command);
		setPlugin(command.getPlugin());
		if (command.getPermission() == null) {
			permission = getPlugin().getName() + "." + name;
		} else if (command.getPermission().isEmpty()) {
			permission = getPlugin().getName() + "." + name;
		} else
			permission = command.getPermission();
		description = "§a" + command.getDescription();

		if (!command.getUsage().isEmpty()) {
			usage = API.USAGE + command.getUsage().replace("<command>", name);
		} else {
			usage = API.USAGE + "/" + name + " help";

		}
		aliases = command.getAliases();
		update();
	}

	public CMD(String name, String... aliases) {
		this.name = name;
		this.aliases = Arrays.asList(aliases);
	}
	public void sendUsage(CommandSender sender) {
		API.chat(sender, getUsage());
	}
	public boolean register(CMD sub) {
		if (commands.containsKey(sub.name)) {
			return false;
		}
		if (command == null) {
			return false;
		}
		commands.put(sub.name, sub);
		sub.command = command;
		sub.permission = permission + "." + sub.name;
		if (sub.usage == null)
			sub.usage = API.USAGE + "/" + name + " " + sub.name;
		// sub.a
		ExtraAPI.consoleMessage("§bCommandAPI §fO subcomando §e" + sub.name
				+ " §ffoi registrado no comando §a" + name
				+ " §fpara o Plugin §b" + getPlugin().getName());
		return true;
	}
	public void update() {
		command.setUsage(usage);
		command.setDescription(description);
		command.setPermission(permission);
	}

	public boolean register() {
		if (command == null) {
			ExtraAPI.consoleMessage("§bCommandAPI §fO comando §a" + name
					+ " §fnao foi registrado na plugin.yml de nenhum Plugin do Servidor");
			return false;
		}
		command.setExecutor(this);
		register(getPlugin());
		ExtraAPI.consoleMessage("§bCommandAPI §fO comando §a" + name
				+ " §ffoi registrado para o Plugin §b"
				+ command.getPlugin().getName());
		return true;

	}
	public boolean unregisterCommand() {
		if (hasCommand()) {
			API.removeCommand(name);

			return true;
		}
		return false;
	}
	public boolean hasCommand() {
		return command != null;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 0) {
			return false;
		}
		CMD cmd = this;
		int id = 0;
		while (true) {
			if (args.length == id) {
				return cmd.onCommand(sender, command, label, args);
			}
			String arg = args[id];
			CMD newCmd = null;
			for (CMD sub : cmd.getCommands().values()) {
				if (sub.getName().equalsIgnoreCase(arg)) {
					newCmd = sub;
					break;
				}
				if (sub.getAliases().contains(arg.toLowerCase())) {
					newCmd = sub;
					break;
				}
			}
			if (newCmd == null) {
				if (cmd == this) {
					return false;
				}
				return cmd.onCommand(sender, command, label, args);
			} else {
				cmd = newCmd;
			}
			id++;

		}

	}
	public PluginCommand getCommand() {
		return command;
	}
	protected void setCommand(PluginCommand command) {
		this.command = command;
	}

	public Map<String, CMD> getCommands() {
		return commands;
	}

	protected void setCommands(Map<String, CMD> commands) {
		this.commands = commands;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public List<String> getAliases() {
		return aliases;
	}

	public void setAliases(List<String> aliases) {
		this.aliases = aliases;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public String getPermissionMessage() {
		return permissionMessage;
	}

	public void setPermissionMessage(String permissionMessage) {
		this.permissionMessage = permissionMessage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Object get(ConfigSection section) {
		this.aliases = section.getStringList("aliases");

		return null;
	}

}
