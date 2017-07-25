package net.eduard.api.world;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public class EmptyWorld extends ChunkGenerator {

	public byte[][] generateBlockSections(World world, Random random, int chunkX, int chunkZ,
			ChunkGenerator.BiomeGrid biomeGrid) {
		byte[][] result = new byte[world.getMaxHeight() / 16][];
		return result;
	}

	public Location getFixedSpawnLocation(World world, Random random) {
		return new Location(world, 100, 100, 100);
	}

	public void setBlock(byte[][] result, int x, int y, int z, byte blkid) {
		if (result[(y >> 4)] == null) {
			result[(y >> 4)] = new byte[4096];
		}
		result[(y >> 4)][((y & 0xF) << 8 | z << 4 | x)] = blkid;
	}

	@SuppressWarnings("deprecation")
	public byte getId(Material material) {
		return (byte) material.getId();
	}

	public byte getId(Material material, short data) {
		return 0;
	}

	public void setLayer(byte[][] result, int level, Material material) {
		int x, z;
		for (x = 0; x < 16; x++) {
			for (z = 0; z < 16; z++) {
				setBlock(result, x, level, z, getId(material));
			}
		}
	}

	public void setCorner(byte[][] result, int level, Material material) {
		int x, z;
		for (x = 0; x < 16; x++) {
			setBlock(result, x, level, 0, getId(material));
		}
		for (z = 0; z < 16; z++) {
			setBlock(result, 0, level, z, getId(material));
		}
	}

	/**
	 * Faze de Teste
	 */
	public void setPosCorner(byte[][] result, int level, Material material) {
		int x, z;
		for (x = 2; x < 15; x++) {
			setBlock(result, x, level, 1, getId(material));
		}
		for (z = 2; z < 15; z++) {
			setBlock(result, 1, level, z, getId(material));
		}
		for (x = 2; x < 15; x++) {
			setBlock(result, x, level, 1, getId(material));
		}
		for (z = 2; z < 15; z++) {
			setBlock(result, 1, level, z, getId(material));
		}
	}

	public void setLayer(byte[][] result, int minLevel, int maxLevel, Material material) {
		int y;
		for (y = minLevel; y <= maxLevel; y++) {
			setLayer(result, y, material);
		}
	}
}
