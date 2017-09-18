package net.eduard.fake;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import net.eduard.api.setup.RexAPI;

public final class FakeAPI {
	
	private static Map<Player,String> original = new HashMap<>();
	private static Map<Player,String> data = new HashMap<>();
	
	public static Map<Player, String> getOriginal() {
		return original;
	}
	public static Map<Player, String> getData() {
		return data;
	}
	public static boolean isFake(Player player){
		
		return !original.get(player).equals(data.get(player));
	}
	public static void fake(Player player,String name){
		data.put(player, name);
		player.setDisplayName(name);
		player.setPlayerListName(name); 
		RexAPI .changeName(player, name);
	}
	public static void reset(Player player){
		fake(player,original.get(player));
		player.sendMessage(Main.config.message("name_reset_to_original"));
	}

}
