package net.eduard.api.command.lag;

import net.eduard.api.manager.CommandManager;

public class LagCommand extends CommandManager{

	public LagCommand() {
		super("lag");
		register(new LagMobsCommand());
	}
}
