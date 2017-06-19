package net.eduard.api.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;

import net.eduard.api.API;
import net.eduard.api.util.Cs;

public abstract class CMD extends Manager implements TabCompleter, CommandExecutor {

	private transient PluginCommand command;
	
	protected transient boolean hasEvents; 
	
	public boolean hasEvents(){
		return hasEvents;
	}

	private transient Map<String, CMD> commands = new HashMap<>();

	private String permission = getCommandName()+".use";

	private List<String> aliases = new ArrayList<>();

	private String name = getCommandName();

	private String usage=API.USAGE+"§c /"+name+" help";

	private String permissionMessage = API.NO_PERMISSION;

	private String description;

	private transient boolean sub;

	public String getCommandName() {
		return getClass().getSimpleName().toLowerCase().replace("sub", "")
				.replace("subcommand", "").replace("comando", "")
				.replace("command", "").replace("cmd", "")
				.replace("eduard", "");
	}

	public List<String> onTabComplete(CommandSender sender, Command command,
			String label, String[] args) {
		return null;
	}

	public void broadcast(String message) {
		Cs.broadcast(message, permission);
	}

	public CMD() {
		this("");
	}
	public CMD(String name) {
		this.name = name;
		if (name.equals("")) {
			this.name = getCommandName();
		}
	}

	public CMD(String name, String... aliases) {
		this.name = name;
		this.aliases = Arrays.asList(aliases);
		this.sub = true;
	} 

	public boolean register(CMD sub) {
		if (commands.containsKey(sub.name)) {
			return false;
		}
		commands.put(sub.name, sub);
		sub.command = command;
		Cs.consoleMessage("§bCommandAPI §fO subcomando §e" + sub.name
				+ " §ffoi registrado no comando §a" + name
				+ " §fpara o Plugin §b" + getPlugin().getName());
		return true;
	}
	public CMD unregister(String sub) {
		if (hasSubCommand(sub)) {

		}
		return this;
	}
	/**
	 * Atualiza informações do Comando
	 */
	public void update() {
		command.setUsage(usage);
		command.setDescription(description);
		command.setAliases(aliases);
		command.setPermissionMessage(permissionMessage);
		command.setPermission(permission);
	}
	public boolean hasSubCommand(String sub) {
		return commands.containsKey(sub.toLowerCase());
	}
	public boolean register() {
		command = Bukkit.getPluginCommand(name);
		if (command == null) {
			Cs.consoleMessage("§bCommandAPI §fO comando §a" + name
					+ " §fnao foi registrado na plugin.yml de nenhum Plugin do Servidor");
			return false;
		}

		String pl = command.getPlugin().getName();
		setCommand(command);
		setPlugin(command.getPlugin());
		command.setExecutor(this);
		permission = getPlugin().getName() + ".command." + name;
		description = "§a" + command.getDescription();
		usage = API.SERVER_TAG + API.USAGE + command.getUsage();
		update();
		Cs.consoleMessage("§bCommandAPI §fO comando §a" + name
				+ " §ffoi registrado para o Plugin §b" + pl);
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
	public void sendUsage(CommandSender sender){
		sender.sendMessage(usage);
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		CMD current = this;
		for (int arg = 0; arg < args.length; arg++) {
			CMD sub = null;
			if (current.commands.isEmpty()) {
				break;
			}
			subFor : for (Entry<String, CMD> subCmdMap : current.commands
					.entrySet()) {
				sub = subCmdMap.getValue();
				String text = args[arg];
				if (text.equalsIgnoreCase(sub.name)) {
					break subFor;
				}
				for (String aliase : sub.aliases) {
					if (aliase.equalsIgnoreCase(text)) {
						break subFor;
					}
				}
				sub = null;
			}
			if (sub == null) {
				break;
			}
			current = sub;
		}
		if (current == this) {
			return false;
		}
		return current.onCommand(sender, command, label, args);
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

	public boolean isSubCommand() {
		return sub;
	}
	
	
}
