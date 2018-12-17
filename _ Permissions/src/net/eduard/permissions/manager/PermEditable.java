package net.eduard.permissions.manager;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachmentInfo;

public class PermEditable  extends  PermissibleBase{

	private Player player;
	public PermEditable(Player  player) {
		super(player);
		setPlayer(player);
	}
	public boolean hasPermission(Permission perm) {

		if (player.isOp()) {
			return true;
		}
		for (PermissionAttachmentInfo permission : getEffectivePermissions()) {
			String permissao = permission.getPermission();
			boolean hasPerm = permission.getValue();

		}
		
		return false;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}

}
