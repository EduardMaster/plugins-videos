package net.eduard.api.kits;

import net.eduard.api.gui.Kit;
import net.eduard.api.gui.KitType;

public class None extends Kit{

	public None() {
		super("None",KitType.DEFAULT);
		setShowOnGui(false);
	}
}
