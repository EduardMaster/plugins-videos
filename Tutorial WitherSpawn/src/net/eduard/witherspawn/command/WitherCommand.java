
package net.eduard.witherspawn.command;

import net.eduard.api.manager.CMD;

public class WitherCommand extends CMD {
	
	public WitherCommand() {
		super("wither");
		register(new Help());
		register(new SetSpawn());
		register(new Spawn());
	}

}
