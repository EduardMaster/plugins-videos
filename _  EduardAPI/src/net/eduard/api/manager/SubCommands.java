package net.eduard.api.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import net.eduard.api.API;

public class SubCommands extends EventAPI
		implements
			TabCompleter,
			CommandExecutor {

	private String permission = "";

	private List<String> aliases = new ArrayList<>();

	private String argument;

	private transient Map<String, SubCommands> subCommands = new HashMap<>();

	public SubCommands(String name, String... aliases) {
		if (name.isEmpty()) {
			name = getClass().getSimpleName().toLowerCase().replace("sub", "")
					.replace("subcommand", "").replace("comando", "")
					.replace("command", "").replace("cmd", "").replace("eduard", "");
		}
		setArgument(name);
		if (aliases.length > 0)
			this.aliases = Arrays.asList(aliases);
		setPermission("eduardapi.command."+name);
	}
	public SubCommands() {
		this("");
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		return true;
	}
	public void command(CommandSender sender, Command command, String label,
			String[] args) {

	}
	public List<String> onTabComplete(CommandSender sender, Command command,
			String label, String[] args) {
		return null;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getArgument() {
		return argument;
	}

	public void setArgument(String argument) {
		this.argument = argument;
	}

	public Map<String, SubCommands> getSubCommands() {
		return subCommands;
	}

	public void setSubCommands(Map<String, SubCommands> subCommands) {
		this.subCommands = subCommands;
	}
	public List<String> getAliases() {
		return aliases;
	}
	public void setAliases(List<String> aliases) {
		this.aliases = aliases;
	}

	public void command(CommandSender sender, String[] args) {

	}
	public void broadcast(String message) {
		API.broadcast(message, getPermission());
	}

}
