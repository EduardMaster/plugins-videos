package net.eduard.essentials.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.manager.EventsManager;
import net.eduard.essentials.EssentialsPlugin;

public class BlockCommands extends EventsManager
{
	
	

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChatTabCompleteEvent(PlayerChatTabCompleteEvent e) {
		for (String msg : EssentialsPlugin.getInstance().getMessages("blocked-tab-commads")){
			if (e.getChatMessage().toLowerCase().startsWith(msg.toLowerCase())) {
			e.getTabCompletions().clear();
			
			}
			
		}

}


	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		for (String msg : EssentialsPlugin.getInstance().getMessages("blocked-commads")){
			if (e.getMessage().toLowerCase().startsWith(msg.toLowerCase())) {
				e.setCancelled(true);
				p.sendMessage(Mine.MSG_NO_PERMISSION);
			}
			
		}
	
	}

	
	
}
