package net.eduard.kits;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import net.eduard.api.config.Section;
import net.eduard.api.dev.Jump;
import net.eduard.api.dev.LaunchPad;
import net.eduard.api.dev.Sounds;
import net.eduard.api.gui.Kit;

public class Launcher extends Kit {

	public Launcher() {
		setIcon(Material.SPONGE, "Ganhe 20 esponjas especiais");
		add(new ItemStack(Material.SPONGE, 20));
	}

	private LaunchPad pad= new LaunchPad(20, new Jump(new Vector(0, 2.5, 0), Sounds.create(Sound.BAT_TAKEOFF)));;

	public void save(Section section, Object value) {
		pad.unregister();
		section.set("pad", pad);
	}

}
