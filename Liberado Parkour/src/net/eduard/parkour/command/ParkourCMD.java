
package net.eduard.parkour.command;

import net.eduard.api.manager.CMD;

public class ParkourCMD extends CMD {
	
	public ParkourCMD() {
		register(new CreateSUB());
		register(new DeleteSUB());
		register(new HelpSUB());
		register(new LobbySUB());
		register(new PlaySUB());
		register(new ReloadSUB());
		register(new SetEndSUB());
		register( new SetLobbySUB());
		register(new SetSpawnSUB());
		register();
		
	}
}
