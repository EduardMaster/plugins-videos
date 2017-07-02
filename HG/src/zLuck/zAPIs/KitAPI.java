package zLuck.zAPIs;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import zLuck.zMain.zLuck;

public class KitAPI {
	
	public static HashMap<Player, String> kit = new HashMap<Player, String>();
	public static HashMap<Player, Long> cooldown = new HashMap<Player, Long>();

	public static String getKit(Player p) {
		return kit.get(p);
	}
		
	public static void setarKit(Player p, String Kit) {
		kit.put(p, Kit);
	}
	public static void setarCooldown(final Player p, String kit, long tempo) {
		cooldown.put(p, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(tempo));
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
			public void run() {
				if (cooldown.containsKey(p)) {
					cooldown.remove(p);
				}
			}
		}, 20 * tempo);
	}
	
}
