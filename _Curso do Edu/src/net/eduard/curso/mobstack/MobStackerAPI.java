package net.eduard.curso.mobstack;

import org.bukkit.entity.Entity;
import org.bukkit.metadata.FixedMetadataValue;

import net.eduard.curso.CursoMain;

public class MobStackerAPI {

	public static void setStack(Entity entity, int amount) {
		entity.setMetadata("mob-stack", new FixedMetadataValue(CursoMain.getInstance(), amount));
	}

	public static int getStack(Entity entity) {
		if (entity.hasMetadata("mob-stack"))

		{
			return entity.getMetadata("mob-stack").get(0).asInt();
		}
		return 0;

	}

}
