package net.eduard.api.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.config.Save;
import net.eduard.api.config.Section;
import net.eduard.api.game.Effects;
import net.eduard.api.game.Explosion;
import net.eduard.api.game.Jump;
import net.eduard.api.game.Potions;
import net.eduard.api.game.Sounds;
import net.eduard.api.util.PlayerEffect;
/**
 * Preço: 
 * @author Eduard-PC
 *
 */
public class Events extends Manager implements Save, Cloneable, PlayerEffect {
	private List<Potions> potions = new ArrayList<>();
	private List<ItemStack> items = new ArrayList<>();
	private PlayerEffect effect;
	private String permission;
	private Location teleport;
	private String command;
	private Effects display;
	private Sounds sound;
	private Explosion explosion;
	private Jump jump;
	private String message;
	private boolean closeInventory;
	private boolean clearInventory;
	private boolean clearHotBar;
	private boolean clearArmours;
	private boolean clearItems;

	public Events clone() {
		try {
			return (Events) super.clone();
		} catch (Exception e) {
			return null;
		}
	}
	public void makeCommand(Player p) {
		if (command != null) {
			p.chat("/" + command);
		}
	}
	public void sendMessage(Player p) {
		if (message != null) {
			p.sendMessage(message);
		}
	}
	public void display(Player p){
		if (display!=null){
			display.create(p);
		}
	}
	public void makeCustomEffect(Player p) {
		if (effect != null) {
			effect.effect(p);
		}
	}
	public void makeExplosion(Entity entity) {
		if (explosion != null) {
			explosion.create(entity);
		}
	}
	public void makeSound(Entity entity) {
		if (sound != null) {
			sound.create(entity);
		}
	}
	public void teleport(Entity entity) {
		if (teleport != null) {
			entity.teleport(teleport);
		}
	}
	public void jump(Entity entity){
		if (jump!=null){
			jump.create(entity);
		}
	}

	public boolean canContinue(Player p) {
		if (permission != null)
			if (!p.hasPermission(permission))
				return false;
		return true;
	}
	public void closeInventory(Player p) {
		if (closeInventory) {
			p.closeInventory();
		}
	}
	public void clearInventory(Player p) {
		if (clearInventory) {
			ItemAPI.clearInventory(p);
		}
	}
	public void clearItems(LivingEntity livingEntity) {
		if (clearItems) {
			ItemAPI.clearItens(livingEntity);
		}
	}
	public void clearArmours(LivingEntity livingEntity) {
		if (clearArmours) {
			ItemAPI.clearArmours(livingEntity);
		}

	}
	public void clearHotBar(Player p) {

		if (clearHotBar) {
			ItemAPI.clearHotBar(p);
		}
	}
	public void giveItems(Player p) {
		ItemAPI.give(items, p.getInventory());
	}
	public void givePotions(LivingEntity entity) {
		for (Potions potion : potions) {
			potion.create(entity);
		}
	}

	public void effect(Player p) {
		makeCustomEffect(p);
		if (!canContinue(p))
			return;
		makeCommand(p);
		makeSound(p);
		sendMessage(p);
		closeInventory(p);
		clearInventory(p);
		clearHotBar(p);
		clearItems(p);
		clearArmours(p);
		teleport(p);;
		jump(p);
		givePotions(p);
		giveItems(p);;
		display(p);
		

	}

	public String getPermission() {
		return permission;
	}
	public Events setPermission(String permission) {
		this.permission = permission;
		return this;
	}
	public List<ItemStack> getItems() {
		return items;

	}
	public Events setItems(List<ItemStack> items) {
		this.items = items;
		return this;
	}
	public Events setItems(ItemStack... items) {
		return setItems(Arrays.asList(items));
	}
	public Location getTeleport() {
		return teleport;
	}
	public Events setTeleport(Location teleport) {
		this.teleport = teleport;
		return this;
	}

	public String getCommand() {
		return command;
	}
	public Events setCommand(String command) {
		this.command = command;
		return this;
	}
	public boolean isCloseInventory() {
		return closeInventory;
	}
	public Events setCloseInventory(boolean closeInventory) {
		this.closeInventory = closeInventory;
		return this;
	}
	public boolean isClearInventory() {
		return clearInventory;
	}
	public Events setClearInventory(boolean clearInventory) {
		this.clearInventory = clearInventory;
		return this;
	}
	public boolean isClearHotBar() {
		return clearHotBar;
	}
	public Events setClearHotBar(boolean clearHotBar) {
		this.clearHotBar = clearHotBar;
		return this;
	}
	public boolean isClearArmours() {
		return clearArmours;
	}
	public Events setClearArmours(boolean clearArmours) {
		this.clearArmours = clearArmours;
		return this;
	}
	public boolean isClearItems() {
		return clearItems;
	}
	public Events setClearItems(boolean clearItems) {
		this.clearItems = clearItems;
		return this;
	}
	public String getMessage() {
		return message;
	}
	public Events setMessage(String message) {
		this.message = message;
		return this;
	}

	public List<Potions> getPotions() {
		return potions;
	}

	public Events setPotions(List<Potions> potions) {
		this.potions = potions;
		return this;
	}

	public PlayerEffect getEffect() {
		return effect;
	}

	public Events setEffect(PlayerEffect effect) {
		this.effect = effect;
		return this;
	}

	public Sounds getSound() {
		return sound;
	}

	public Events setSound(Sounds sound) {
		this.sound = sound;
		return this;
	}

	public Jump getJump() {
		return jump;
	}

	public Events setJump(Jump jump) {
		this.jump = jump;
		return this;
	}

	@Override
	public Object get(Section section) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void save(Section section, Object value) {
		// TODO Auto-generated method stub

	}
	public Events setPotions(Potions... potions){
		for (Potions potion:potions){
			this.potions.add(potion);
		}
		return this;
	}
	public Explosion getExplosion() {
		return explosion;
	}
	public Events setExplosion(Explosion explosion) {
		this.explosion = explosion;
		return this;
	}
	public Effects getDisplay() {
		return display;
	}
	public Events setDisplay(Effects display) {
		this.display = display;
		return this;
	}

}
