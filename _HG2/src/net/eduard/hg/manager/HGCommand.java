package net.eduard.hg.manager;

import net.eduard.api.manager.CMD;
import net.eduard.hg.Main;

public abstract class HGCommand extends CMD {
	public HGCommand() {
		super(Main.plugin);
	}
}
