package net.eduard.api.manager;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

import net.eduard.api.API;

public class Commands extends SubCommands {

	private transient PluginCommand command;

	public Commands() {
		this("");
	}
	public String getPermission() {
		return command.getPermission();
	}
	public void setPermission(String permission) {
		if (command != null)
			command.setPermission(permission);
	}

	public Commands(String name) {
		super(name);
		if (name.isEmpty()) {
			name = getClass().getSimpleName().toLowerCase()
					.replace("comando", "").replace("command", "")
					.replace("cmd", "");
		}
		PluginCommand cmd = Bukkit.getPluginCommand(name);
		if (cmd == null) {
			API.consoleMessage("§bCommandAPI §fO comando §a" + name
					+ " §fnao foi registrado na plugin.yml de nenhum Plugin do Servidor");
			return;
		}
		setCommand(cmd);
		cmd.setExecutor(this);
		cmd.setPermissionMessage(API.NO_PERMISSION);
		cmd.setPermission("eduard.command." + name);
		cmd.setDescription("§a" + cmd.getDescription());
		cmd.setUsage(API.SERVER_TAG + API.USAGE + cmd.getUsage());
		cmd.setExecutor(this);
		setPlugin(cmd.getPlugin());
		API.consoleMessage("§bCommandAPI §fO comando §a" + name
				+ " §ffoi registrado para o Plugin §b"
				+ getCommand().getPlugin().getName());

	}
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		SubCommands current = this;
		for (int arg = 0; arg < args.length; arg++) {
			SubCommands sub = null;
			if (current.getSubCommands().isEmpty()) {
				break;
			}
			subFor : for (Entry<String, SubCommands> subCmdMap : current
					.getSubCommands().entrySet()) {
				sub = subCmdMap.getValue();
				String text = args[arg];
				if (text.equalsIgnoreCase(sub.getArgument())) {
					break subFor;
				}
				for (String aliase : sub.getAliases()) {
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
		if (sender.hasPermission(current.getPermission())) {
			current.command(sender, command, label, args);
			current.command(sender, args);
		}
		return current.onCommand(sender, command, label, args);
	}
	public void addSub(SubCommands subCommands) {
		getSubCommands().put(subCommands.getArgument(), subCommands);
		API.consoleMessage("§bCommandAPI §fO subcomando §e"
				+ subCommands.getArgument() + " §ffoi registrado no comando §a"
				+ getCommand().getName() + " §fpara o Plugin §b"
				+ getCommand().getPlugin().getName());
	}
	public PluginCommand getCommand() {
		return command;
	}
	public void setCommand(PluginCommand command) {
		this.command = command;
	}

}
