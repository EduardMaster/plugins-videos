
package net.eduard.witherspawn.command;

import net.eduard.api.manager.Commands;

public class WitherCommand extends Commands {
	
	public WitherCommand() {
		super("wither");
		addSub(new Help());
		addSub(new SetSpawn());
		addSub(new Spawn());
	}

}
