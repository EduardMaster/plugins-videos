package net.eduard.api.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.API;
import net.eduard.api.config.Section;
import net.eduard.api.dev.Jump;
import net.eduard.api.dev.Potions;
import net.eduard.api.dev.Sounds;
import net.eduard.api.util.PlayerEffect;
import net.eduard.api.util.Save;

public class Events implements Save, PlayerEffect {
	private String permission;
	private PlayerEffect customEffect;
	private Potions potion;
	private List<ItemStack> items = new ArrayList<>();
	private Location teleport;
	private String command;
	private boolean closeInventory;
	private boolean clearInventory;
	private boolean clearHotBar;
	private boolean clearArmours;
	private boolean clearItems;
	private Sounds sound;
	private Jump jump;
	private String message;
	public Events effect(PlayerEffect effect) {
		setCustomEffect(effect);
		return this;
	}

	public void effect(Player p) {
		if (customEffect != null) {
			customEffect.effect(p);
		}

		if (permission != null)
			if (!p.hasPermission(permission))
				return;

		if (command != null) {
			p.chat("/" + command);
		}
		if (message != null) {
			p.sendMessage(message);
		}
		if (closeInventory) {
			p.closeInventory();

		}
		if (clearInventory) {
			API.clearInventory(p);
		}
		if (clearHotBar) {
			API.clearHotBar(p);
		}
		if (clearArmours) {
			API.clearArmours(p);
		}
		if (clearItems) {
			API.clearItens(p);
		}
		if (sound != null) {
			sound.create(p);
		}
		if (jump != null) {
			jump.create(p);
		}

	}

	public Object get(Section section) {
		return null;
	}

	public String getCommand() {
		return command;
	}

	public String getMessage() {
		return message;
	}

	public Sounds getSound() {
		return sound;
	}

	public boolean isClearArmours() {
		return clearArmours;
	}

	public boolean isClearHotBar() {
		return clearHotBar;
	}

	public boolean isClearInventory() {
		return clearInventory;
	}

	public boolean isClearItems() {
		return clearItems;
	}

	public boolean isCloseInventory() {
		return closeInventory;
	}

	public void save(Section section, Object value) {

	}

	public Events setClearArmours(boolean clearArmours) {
		this.clearArmours = clearArmours;
		return this;
	}

	public Events setClearHotBar(boolean clearHotBar) {
		this.clearHotBar = clearHotBar;
		return this;
	}

	public Events setClearInventory(boolean clearInventory) {
		this.clearInventory = clearInventory;
		return this;
	}

	public Events setClearItems(boolean clearItems) {
		this.clearItems = clearItems;
		return this;
	}

	public Events setCloseInventory(boolean closeInventory) {
		this.closeInventory = closeInventory;
		return this;
	}

	public Events setCommand(String command) {
		this.command = command;
		return this;
	}

	public Events setMessage(String message) {
		this.message = message;
		return this;
	}

	public Events setSound(Sounds sound) {
		this.sound = sound;
		return this;
	}

	public String getPermission() {
		return permission;
	}

	public Events setPermission(String permission) {
		this.permission = permission;
		return this;
	}

	public PlayerEffect getCustomEffect() {
		return customEffect;
	}

	public Events setCustomEffect(PlayerEffect customEffect) {
		this.customEffect = customEffect;
		return this;
	}

	public Potions getPotion() {
		return potion;
	}

	public Events setPotion(Potions potion) {
		this.potion = potion;
		return this;
	}

	public List<ItemStack> getItems() {
		return items;
	}

	public Events setItems(List<ItemStack> items) {
		this.items = items;
		return this;
	}

	public Location getTeleport() {
		return teleport;
	}

	public Events setTeleport(Location teleport) {
		this.teleport = teleport;
		return this;
	}
}
