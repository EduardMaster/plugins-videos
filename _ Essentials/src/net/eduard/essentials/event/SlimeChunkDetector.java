package net.eduard.essentials.event;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import net.eduard.api.lib.manager.EventsManager;
import net.eduard.essentials.EssentialsPlugin;

public class SlimeChunkDetector extends EventsManager {
	@EventHandler
	public void split(SlimeSplitEvent e) {
		e.setCancelled(EssentialsPlugin.getInstance().getConfigs().getBoolean("no-slime-split"));
	}

	@EventHandler(ignoreCancelled = true)
	public void onSlimeChunk(PlayerMoveEvent e) {

		Player p = e.getPlayer();
		if (EssentialsPlugin.getInstance().getSlimeChunkActive().contains(p)) {

			long worldSeed = p.getWorld().getSeed();
			Chunk jogadorChunk = p.getWorld().getChunkAt(p.getLocation());
			int x = jogadorChunk.getX();
			int z = jogadorChunk.getZ();

			Random random = new Random(
					worldSeed + x * x * 4987142 + x * 5947611 + z * z * 4392871L + z * 389711 ^ 0x3AD8025F);

			if (random.nextInt(10) == 0)
				p.playSound(p.getLocation(), Sound.SLIME_WALK, 5.0F, 5.0F);
		}
	}
}