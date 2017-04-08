
package net.eduard.parkour.command;

import net.eduard.api.manager.Commands;

public class ParkourCMD extends Commands {
	
	public ParkourCMD() {
		addSub(new CreateSUB());
		addSub(new DeleteSUB());
		addSub(new HelpSUB());
		addSub(new LobbySUB());
		addSub(new PlaySUB());
		addSub(new ReloadSUB());
		addSub(new SetEndSUB());
		addSub( new SetLobbySUB());
		addSub(new SetSpawnSUB());
		
	}
}
