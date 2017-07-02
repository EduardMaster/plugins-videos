package net.eduard.hg;

import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.hg.event.Principal;
import net.eduard.hg.setup.HgManager;
import net.eduard.hg.setup.HgRoom;
import net.eduard.hg.setup.HgTimer;

public class HgPlugin extends JavaPlugin {
	public static HgManager setup;
	public static HgRoom hg;
	public static HgTimer timer;
	public void onEnable() {
		hg = new HgRoom();;
		new Principal();
		setup = new HgManager();
		setup.getRooms().put(1, hg);
		timer = new HgTimer();
		timer.runTaskTimer(this, 20, 20);
		setup.reloadMapas();
		
	}@Override
	public void onDisable() {
		setup.salvarMapas();
	}
}
