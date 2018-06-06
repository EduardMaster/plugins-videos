package net.eduard.tutoriais.kits;

import net.eduard.api.lib.game.Ability;
import net.eduard.api.lib.game.KitType;

public class None extends Ability{

	public None() {
		super("None",KitType.DEFAULT);
		setShowOnGui(false);
	}
}
