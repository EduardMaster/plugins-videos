
package net.eduard.witherspawn.command;

import net.eduard.api.manager.CMD;

public class WitherCMD extends CMD {
	public CMD setspawn = new SetSpawnSUB();
	public CMD spawn = new SpawnSUB();
	public CMD help = new HelpSUB();

}
