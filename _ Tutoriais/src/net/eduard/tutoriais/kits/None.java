package net.eduard.tutoriais.kits;

import net.eduard.api.lib.game.KitAbility;
import net.eduard.api.lib.game.KitType;

public class None extends KitAbility{

	public None() {
		super("None",KitType.DEFAULT);
		setShowOnGui(false);
	}
}
