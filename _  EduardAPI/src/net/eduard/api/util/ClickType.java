package net.eduard.api.util;

import org.bukkit.event.block.Action;

public enum ClickType {
	AIR_CLICK, BLOCK_CLICK, RIGHT_CLICK, LEFT_CLICK, ALL_CLICK, RIGHT_CLICK_AIR,
	RIGHT_CLICK_BLOCK, LEFT_CLICK_BLOCK, LEFT_CLICK_AIR,CLICK_ENTITY;

public boolean click(Action action) {

	if (this == ALL_CLICK) {
		return action != Action.PHYSICAL;
	} else if (this == AIR_CLICK) {
		return action.name().contains("AIR");
	} else if (this == BLOCK_CLICK) {
		return action.name().contains("BLOCK");
	} else if (this == RIGHT_CLICK) {
		return action.name().contains("RIGHT");
	} else if (this == LEFT_CLICK) {
		return action.name().contains("LEFT");
	} else {
		return name().equals(action.name());
	}
}}