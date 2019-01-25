package net.eduard.economy.command;

import net.eduard.api.lib.manager.CommandManager;

public class EconomyCommand extends CommandManager{

	public EconomyCommand() {
		super("money");
		register(new EconomyAddCommand());
		register(new EconomyHelpCommand());
	}
}
