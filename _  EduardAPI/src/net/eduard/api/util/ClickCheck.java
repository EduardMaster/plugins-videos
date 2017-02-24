package net.eduard.api.util;

import org.bukkit.inventory.ItemStack;

import net.eduard.api.API;
import net.eduard.api.gui.Click;

public enum ClickCheck {
	BY_NAME, BY_TYPE,BY_TYPE_NAMED, NORMAL, IGNORING_AMOUNT;
	public boolean click(Click click,ItemStack item) {
		boolean canEffect = false;
		switch (click.getCheck()) {
		case BY_NAME:
			if (API.getName(item).equals(API.getName(click.getItem()))) {
				canEffect = true;
			}
			break;
		case BY_TYPE_NAMED:
			if (item.getType().name().contains(click.getTypeNamed())) {
				canEffect = true;
			}
			break;
		case BY_TYPE:
			if (item.getType() == click.getItem().getType()) {
				canEffect = true;
			}
			break;
		case IGNORING_AMOUNT:
			if (item.isSimilar(click.getItem())) {
				canEffect = true;
			}
			break;
		case NORMAL:
			if (item.equals(click.getItem())) {
				canEffect = true;
			}
			break;

		default:
			break;
		}
		return canEffect;
	}

}
