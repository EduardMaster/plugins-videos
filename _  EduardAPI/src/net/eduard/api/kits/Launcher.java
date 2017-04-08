package net.eduard.api.kits;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import net.eduard.api.config.Section;
import net.eduard.api.gui.Kit;
import net.eduard.api.manager.EventAPI;
import net.eduard.api.player.Jump;
import net.eduard.api.player.LaunchPad;
import net.eduard.api.player.SoundEffect;

public class Launcher extends Kit {

	private LaunchPad pad= new LaunchPad(-1,19, new Jump(SoundEffect.create(Sound.BAT_TAKEOFF), new Vector(0, 2.5, 0)));
	

	public Launcher() {
		setIcon(Material.SPONGE, "§fGanhe 20 esponjas especiais");
		add(new ItemStack(Material.SPONGE, 20));
	};
	@Override
	public EventAPI register(Plugin plugin) {
		pad.register(plugin);
		return super.register(plugin);
	}
	@Override
	public void save(Section section, Object value) {
		section.set("pad", pad);
	}

}
