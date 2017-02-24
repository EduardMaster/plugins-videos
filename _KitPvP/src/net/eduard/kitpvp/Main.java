
package net.eduard.kitpvp;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.API;
import net.eduard.api.config.Config;
import net.eduard.kitpvp.command.AdminCMD;
import net.eduard.kitpvp.command.AplicateCMD;
import net.eduard.kitpvp.command.BroadcastCMD;
import net.eduard.kitpvp.command.ChatCMD;
import net.eduard.kitpvp.command.ClearChatCMD;
import net.eduard.kitpvp.command.ClearInventoryCMD;
import net.eduard.kitpvp.command.FlyCMD;
import net.eduard.kitpvp.command.InventorySeeCMD;
import net.eduard.kitpvp.command.InvisibleCMD;
import net.eduard.kitpvp.command.PingCMD;
import net.eduard.kitpvp.command.PvPCMD;
import net.eduard.kitpvp.command.ReportCMD;
import net.eduard.kitpvp.command.ScoreCMD;
import net.eduard.kitpvp.command.StaffChatCMD;
import net.eduard.kitpvp.command.TeleportAllCMD;
import net.eduard.kitpvp.command.VisibleCMD;
import net.eduard.kitpvp.command.YoutuberCMD;
import net.eduard.kitpvp.event.KitPvPEvent;

public class Main extends JavaPlugin {

	public static Main plugin;
	public static Config config;
	public static KitPvP kit;

	public void onEnable() {

		plugin = this;
		config = new Config(this);
		kit = new KitPvP();
		new KitPvPEvent();
		new AdminCMD();
		new AplicateCMD();
		new BroadcastCMD();
		new ChatCMD();
		new ClearChatCMD();
		new ClearInventoryCMD();
		new FlyCMD();
		new InventorySeeCMD();
		new InvisibleCMD();
		new InvisibleCMD();
		new PingCMD();
		new PvPCMD();
		new ReportCMD();
		new ScoreCMD();
		new StaffChatCMD();
		new TeleportAllCMD();
		new VisibleCMD();
		new YoutuberCMD();
		for (Player p : API.getPlayers()) {
			API.callEvent(new PlayerJoinEvent(p, null));
		}
	}

	public void onDisable() {

	}
}
