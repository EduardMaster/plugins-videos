package net.eduard.api.manager;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import net.eduard.api.API;
import net.eduard.api.config.ConfigSection;
import net.eduard.api.game.Potions;
import net.eduard.api.gui.Gui;
import net.eduard.api.gui.Slot;
import net.eduard.api.setup.ItemAPI;
import net.eduard.api.util.Save;

public class KitPvP implements Save {
	public static List<Material> canPickUp = Arrays.asList(
			new Material[]{Material.EXP_BOTTLE, Material.MUSHROOM_SOUP});
	public static List<Material> canDrop = Arrays
			.asList(new Material[]{Material.BOWL, Material.ENDER_PEARL});
	private Gui warp = new Gui(6, "§aWarps");
	private String joinMessage = "§b§l+§e$player";
	private String quitMessage = "§b§l-§e$player";
	private Location pos1pvp = Bukkit.getWorlds().get(0).getSpawnLocation();
	private Location pos2pvp = Bukkit.getWorlds().get(0).getSpawnLocation();

	private List<String> aplicate = Arrays.asList(new String[]{
			"§6Trial: www.google.com.br", "§bBuilder: www.google.com.br"});

	private List<String> youtuber = Arrays.asList(new String[]{
			"§6Trial: www.google.com.br", "§bBuilder: www.google.com.br"});

	private List<String> motd = Arrays.asList(new String[]{
			"§bEntre e divirta-se Com o PvP", "www.google.com.br"});

	private int maxPlayers = 10000;
	private String chatFormat = "$prefix $player$suffix: $message";
	private Location spawn = Bukkit.getWorlds().get(0).getSpawnLocation();

	public KitPvP() {
		this.warp.set((Slot) new Slot(
				API.newItem(Material.ENDER_PEARL, "§aWarp Archer"), 11)
						.setTeleport(API.getSpawn()).setItems(new ItemStack[]{
								ItemAPI.addEnchant(
										ItemAPI.addEnchant(
												API.newItem(Material.BOW,
														"§6Archer"),
												Enchantment.ARROW_DAMAGE, 5),
										Enchantment.ARROW_INFINITE, 1),
								new ItemStack(Material.ARROW)}));
		this.warp.set((Slot) new Slot(
				API.newItem(Material.ENDER_PEARL, "§aWarp 1v1"), 12)
						.setTeleport(API.getSpawn()).setItems(new ItemStack[]{
								API.newItem(Material.BLAZE_ROD, "§61v1")}));
		this.warp
				.set((Slot) new Slot(
						API.newItem(Material.ENDER_PEARL, "§aWarp Staff"), 13)
								.setTeleport(API.getSpawn())
								.setItems(new ItemStack[]{
										API.newItem(Material.LEATHER_CHESTPLATE,
												"§6Challenge")}));
		this.warp.set((Slot) new Slot(
				API.newItem(Material.ENDER_PEARL, "§aWarp Challenge"), 14)
						.setTeleport(API.getSpawn())
						.setItems(new ItemStack[]{API.newItem(
								Material.LEATHER_CHESTPLATE, "§6Challenge")}));
		this.warp.set((Slot) new Slot(
				API.newItem(Material.ENDER_PEARL, "§aWarp FPS"), 15)
						.setTeleport(API.getSpawn())
						.setItems(new ItemStack[]{ItemAPI.addEnchant(
								API.newItem(Material.DIAMOND_SWORD, "§6FPS"),
								Enchantment.DAMAGE_ALL, 5)}));
		this.warp.set((Slot) new Slot(
				API.newItem(Material.ENDER_PEARL, "§aWarp Spawn"), 16)
						.setTeleport(API.getSpawn()));
		this.warp.set((Slot) new Slot(
				API.newItem(Material.ENDER_PEARL, "§aWarp Regras"), 21)
						.setTeleport(API.getSpawn()));

		this.warp.set((Slot) new Slot(
				API.newItem(Material.ENDER_PEARL, "§aWarp RDM"), 22)
						.setTeleport(API.getSpawn())
						.setItems(API.newItem(Material.LEATHER_CHESTPLATE,
								"§6RDM"))
						.setPotions(new Potions(PotionEffectType.SPEED, 0,
								2000000)));;

		this.warp.set((Slot) new Slot(
				API.newItem(Material.ENDER_PEARL, "§aWarp MDR"),
				23).setTeleport(API.getSpawn()).setItems(new ItemStack[]{
						API.newItem(Material.LEATHER_CHESTPLATE, "§6MDR")}));

		this.warp.setItem(API.newItem(Material.COMPASS, "§6Warps"));

	}

	public Gui getWarp() {
		return this.warp;
	}

	public String getJoinMessage() {
		return this.joinMessage;
	}

	public void setJoinMessage(String joinMessage) {
		this.joinMessage = joinMessage;
	}

	public String getQuitMessage() {
		return this.quitMessage;
	}

	public void setQuitMessage(String quitMessage) {
		this.quitMessage = quitMessage;
	}

	public Location getSpawn() {
		return this.spawn;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}
	public List<String> getYoutuber() {
		return this.youtuber;
	}

	public void setYoutuber(List<String> youtuber) {
		this.youtuber = youtuber;
	}

	public List<String> getAplicate() {
		return this.aplicate;
	}

	public void setAplicate(List<String> aplicate) {
		this.aplicate = aplicate;
	}

	public List<String> getMotd() {
		return this.motd;
	}

	public void setMotd(List<String> motd) {
		this.motd = motd;
	}

	public int getMaxPlayers() {
		return this.maxPlayers;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public Object get(ConfigSection section) {

		return null;
	}

	public void save(ConfigSection section, Object value) {
	}

	public String getChatFormat() {
		return this.chatFormat;
	}
	public void setChatFormat(String chatFormat) {
		this.chatFormat = chatFormat;
	}

	public Location getPos1pvp() {
		return pos1pvp;
	}

	public void setPos1pvp(Location pos1pvp) {
		this.pos1pvp = pos1pvp;
	}

	public Location getPos2pvp() {
		return pos2pvp;
	}

	public void setPos2pvp(Location pos2pvp) {
		this.pos2pvp = pos2pvp;
	}
}