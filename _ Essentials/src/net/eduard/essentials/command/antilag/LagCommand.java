package net.eduard.essentials.command.antilag;

import net.eduard.api.lib.manager.CommandManager;

public class LagCommand extends CommandManager{

	public LagCommand() {
		super("lag");
		register(new LagMobsCommand());
	}
}
