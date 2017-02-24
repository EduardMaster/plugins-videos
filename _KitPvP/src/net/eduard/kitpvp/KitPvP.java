package net.eduard.kitpvp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import net.eduard.api.API;
import net.eduard.api.config.Section;
import net.eduard.api.dev.Potions;
import net.eduard.api.dev.Score;
import net.eduard.api.dev.Tag;
import net.eduard.api.gui.Events;
import net.eduard.api.gui.Gui;
import net.eduard.api.gui.Slot;
import net.eduard.api.util.Save;

public class KitPvP implements Save {
	public static List<Player> staffChat = new ArrayList<>();
	public static List<Player> admins = new ArrayList<>();
	public static HashMap<Player, String> messages = new HashMap<>();
	public static HashMap<Player, Score> scoreboards = new HashMap<>();
	public static List<Material> canPickUp = Arrays.asList(Material.EXP_BOTTLE, Material.MUSHROOM_SOUP);
	public static List<Material> canDrop = Arrays.asList(Material.MUSHROOM_SOUP, Material.BOWL);
	private Gui warp = new Gui(6, "§aWarps");
	public static boolean chat = true;
	public static boolean pvp = true;
	private String joinMessage = "§b§l+§e$player";
	private String quitMessage = "§b§l-§e$player";
	private List<String> aplicate = Arrays.asList("§6Trial: www.google.com.br", "§bBuilder: www.google.com.br");
	private List<String> youtuber = Arrays.asList("§6Trial: www.google.com.br", "§bBuilder: www.google.com.br");
	private List<String> motd = Arrays.asList("§bEntre e divirta-se Com o PvP", "www.google.com.br");
	private List<String> blackWords = Arrays.asList("filho da puta", "fdp");

	private Slot testAutoSoup = new Slot(API.newItem(Material.BOWL, "§aTestar Auto-Soup"), 3);
	private Slot testHack = new Slot(API.newItem(Material.MAGMA_CREAM, "§aTroca Rapida"), 2);
	private Slot testNoFall = new Slot(API.newItem(Material.FEATHER, "§aTestar No-Fall"), 4);
	private Slot testInfo = new Slot(API.newItem(Material.BLAZE_ROD, "§aVer Informações"), 5);
	private Slot testAntKB = new Slot(
			API.add(API.newItem(Material.STICK, "§aTestar Knockback"), Enchantment.KNOCKBACK, 10), 4);
	private boolean canRepeteWords;
	private String blackWordsTag = "***";
	private List<Tag> tags = new ArrayList<>();
	private Slot slotNockback = new Slot(
			API.add(API.newItem(Material.STICK, "§6Testar Ant NockBack"), Enchantment.KNOCKBACK, 10), 5);
	private String staff = "§e[§6§lSTAFF§e]§b";
	private int maxPlayers = 10000;
	private String format = "$staff$prefix$player$suffix: $message";
	private Location spawn=Bukkit.getWorlds().get(0).getSpawnLocation();

	public KitPvP() {
		warp.set(
				5, API
						.newItem(Material.BOWL,
								"§aWarp Archer"),
				new Events()
						.setItems(Arrays.asList(
								API.add(API.add(API.newItem(Material.BOW, "§6Archer"), Enchantment.ARROW_DAMAGE, 5),
										Enchantment.ARROW_INFINITE, 1),
								new ItemStack(Material.ARROW)))
						.setTeleport(Bukkit.getWorlds().get(0).getSpawnLocation()));
		
		warp.set(6, API.newItem(Material.BOWL, "§aWarp 1v1"),
				new Events().setItems(Arrays.asList(API.newItem(Material.BLAZE_ROD, "§61v1")))
						.setTeleport(Bukkit.getWorlds().get(0).getSpawnLocation()));
		warp.set(7, API.newItem(Material.BOWL, "§aWarp Staff"));
		warp.set(8, API.newItem(Material.BOWL, "§aWarp Challenge"),
				new Events().setItems(Arrays.asList(API.newItem(Material.LEATHER_CHESTPLATE, "§6Challenge"))));
		warp.set(12, API.newItem(Material.BOWL, "§aWarp FPS"), new Events().setItems(
				Arrays.asList(API.add(API.newItem(Material.DIAMOND_SWORD, "§6FPS"), Enchantment.DAMAGE_ALL, 5))));
		warp.set(13, API.newItem(Material.BOWL, "§aWarp Spawn"),
				new Events().setTeleport(Bukkit.getWorlds().get(0).getSpawnLocation()));
		warp.set(14, API.newItem(Material.BOWL, "§aWarp Regras"),
				new Events().setTeleport(Bukkit.getWorlds().get(0).getSpawnLocation()));
		warp.set(15, API.newItem(Material.BOWL, "§aWarp MDR"),
				new Events().setItems(Arrays.asList(API.newItem(Material.LEATHER_CHESTPLATE, "§6MDR")))
						.setPotion(new Potions(PotionEffectType.SPEED, 0, 2000000)));
		warp.set(16, API.newItem(Material.BOWL, "§aWarp MDR"),
				new Events().setItems(Arrays.asList(API.newItem(Material.LEATHER_CHESTPLATE, "§6RDM"))));

	}

	public static Player pegarJogarMaisProximo(Player player) {
		double dis = 0;
		Player target = null;
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (dis == 0) {
				dis = p.getLocation().distance(player.getLocation());
				target = p;
			} else {
				double newdis = p.getLocation().distance(player.getLocation());
				;
				if (newdis < dis) {
					dis = newdis;
					target = p;
				}

			}
		}
		return target;
	}

	public String getJoinMessage() {
		return joinMessage;
	}

	public void setJoinMessage(String joinMessage) {
		this.joinMessage = joinMessage;
	}

	public String getQuitMessage() {
		return quitMessage;
	}

	public void setQuitMessage(String quitMessage) {
		this.quitMessage = quitMessage;
	}

	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}

	public String getStaff() {
		return staff;
	}

	public void setStaff(String staff) {
		this.staff = staff;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public List<String> getYoutuber() {
		return youtuber;
	}

	public void setYoutuber(List<String> youtuber) {
		this.youtuber = youtuber;
	}

	public List<String> getAplicate() {
		return aplicate;
	}

	public void setAplicate(List<String> aplicate) {
		this.aplicate = aplicate;
	}

	public List<String> getMotd() {
		return motd;
	}

	public void setMotd(List<String> motd) {
		this.motd = motd;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public List<String> getBlackWords() {
		return blackWords;
	}

	public void setBlackWords(List<String> blackWords) {
		this.blackWords = blackWords;
	}

	public String getBlackWordsTag() {
		return blackWordsTag;
	}

	public void setBlackWordsTag(String blackWordsTag) {
		this.blackWordsTag = blackWordsTag;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public boolean isCanRepeteWords() {
		return canRepeteWords;
	}

	public void setCanRepeteWords(boolean canRepeteWords) {
		this.canRepeteWords = canRepeteWords;
	}

	public Slot getSlotNockback() {
		return slotNockback;
	}

	public void setSlotNockback(Slot slotNockback) {
		this.slotNockback = slotNockback;
	}

	public Slot getTestAutoSoup() {
		return testAutoSoup;
	}

	public void setTestAutoSoup(Slot testAutoSoup) {
		this.testAutoSoup = testAutoSoup;
	}

	public Slot getTestHack() {
		return testHack;
	}

	public void setTestHack(Slot testHack) {
		this.testHack = testHack;
	}

	public Slot getTestNoFall() {
		return testNoFall;
	}

	public void setTestNoFall(Slot testNoFall) {
		this.testNoFall = testNoFall;
	}

	public Slot getTestAntKB() {
		return testAntKB;
	}

	public void setTestAntKB(Slot testAntKB) {
		this.testAntKB = testAntKB;
	}

	public Slot getTestInfo() {
		return testInfo;
	}

	public void setTestInfo(Slot testInfo) {
		this.testInfo = testInfo;
	}

	public Object get(Section section) {

		return null;
	}

	public void save(Section section, Object value) {

	}

}
