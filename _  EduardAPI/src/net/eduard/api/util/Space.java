package net.eduard.api.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;

import net.eduard.api.API;
import net.eduard.api.player.LocationEffect;

public class Space implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<SpaceBlock> blocks = new ArrayList<>();

	@SuppressWarnings("deprecation")
	public void paste(Location playerLocation) {
		for (SpaceBlock block : blocks) {
			playerLocation.getWorld()
					.getBlockAt(playerLocation.getBlockX() - block.getX(),
							playerLocation.getBlockY() - block.getY(),
							playerLocation.getBlockZ() - block.getZ())
					.setTypeIdAndData(block.getId(), (byte) block.getData(),
							false);
		}
	}
	public static Space copy(Location pos1, Location pos2,
			Location playerPosition, boolean copyAir) {
		Space space = new Space();
		API.getLocations(pos1, pos2, new LocationEffect() {

			@SuppressWarnings("deprecation")
			@Override
			public boolean effect(Location location) {
				Material type = location.getBlock().getType();
				boolean use = false;
				if (type == Material.AIR && copyAir) {
					use = true;
				} else {
					use = true;
				}
				if (use) {
					SpaceBlock block = new SpaceBlock(
							playerPosition.getBlockX() - location.getBlockX(),
							playerPosition.getBlockY() - location.getBlockY(),
							playerPosition.getBlockZ() - location.getBlockZ(),
							location.getBlock().getTypeId(),
							location.getBlock().getData());
					space.blocks.add(block);
				}
				return false;
			}
		});

		return space;

	}
	public static Space load(File file) {
		try {
			if (file.exists()) {
				ObjectInputStream in = new ObjectInputStream(
						new FileInputStream(file));
				Object object = in.readObject();
				in.close();
				return (Space) object;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void save(File file) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(file));
			out.writeObject(this);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<SpaceBlock> getBlocks() {
		return blocks;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setBlocks(List<SpaceBlock> blocks) {
		this.blocks = blocks;
	}
	public static class SpaceBlock implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private int x,y,z,id,data;

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getZ() {
			return z;
		}

		public void setZ(int z) {
			this.z = z;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getData() {
			return data;
		}

		public void setData(int data) {
			this.data = data;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		public SpaceBlock(int x, int y, int z, int id, int data) {
			super();
			this.x = x;
			this.y = y;
			this.z = z;
			this.id = id;
			this.data = data;
		}
		
	}
}
