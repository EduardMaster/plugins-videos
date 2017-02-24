
package net.eduard.parkour.command;

import net.eduard.api.manager.CMD;

public class ParkourCMD extends CMD {

	public CMD help = new HelpSUB();
	public CMD create = new CreateSUB();
	public CMD delete = new DeleteSUB();
	public CMD lobby = new LobbySUB();
	public CMD play = new PlaySUB();
	public CMD setend = new SetEndSUB();
	public CMD setlobby = new SetLobbySUB();
	public CMD setspawn = new SetSpawnSUB();
	
}
