package net.eduard.tutoriais.kits;

import net.eduard.api.lib.modules.KitType;
import net.eduard.api.server.kits.KitAbility;

public class None extends KitAbility{

	public None() {
		super("None",KitType.DEFAULT);
		setShowOnGui(false);
	}
}
