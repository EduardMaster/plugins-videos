
package net.eduard.witherspawn.command;

import net.eduard.api.lib.manager.CommandManager;

public class WitherCommand extends CommandManager {
	
	public WitherCommand() {
		super("wither");
		register(new WitherHelpCommand());
		register(new WitherSetSpawnCommand());
		register(new WitherSpawnCommand());
	}

}
