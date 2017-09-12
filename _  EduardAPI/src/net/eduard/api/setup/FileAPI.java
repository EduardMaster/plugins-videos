package net.eduard.api.setup;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
/**
 * API de criação e manipulação de Arquivos do Computador
 * 
 * @author Eduard
 *
 */
public final class FileAPI {
	/**
	 * Transforma um Texto em Vetor de Itens
	 * 
	 * @param data
	 *            Texto
	 * @return Vetor de Itens (Lista)
	 * 
	 */
	public static ItemStack[] itemFromBase64(final String data) {
		try {
			final ByteArrayInputStream inputStream = new ByteArrayInputStream(
					Base64Coder.decodeLines(data));
			final BukkitObjectInputStream dataInput = new BukkitObjectInputStream(
					inputStream);
			final ItemStack[] stacks = new ItemStack[dataInput.readInt()];
			for (int i = 0; i < stacks.length; ++i) {

				stacks[i] = (ItemStack) dataInput.readObject();

			}
			dataInput.close();
			return stacks;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Transforma um Vetor de Itens em um Texto
	 * 
	 * @param contents
	 *            Vetor de Itens
	 * @return Texto
	 */
	public static String itemtoBase64(final ItemStack[] contents) {

		try {
			final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BukkitObjectOutputStream dataOutput;
			dataOutput = new BukkitObjectOutputStream(outputStream);
			dataOutput.writeInt(contents.length);
			for (final ItemStack stack : contents) {
				dataOutput.writeObject(stack);
			}
			dataOutput.close();
			return Base64Coder.encodeLines(outputStream.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static boolean isDirectory(File file) {
		try {
			return (file.isDirectory());
		} catch (Exception e) {
			return isDirectory(file.getName());
		}

	}
	public static boolean isDirectory(String name) {

		if (name.endsWith(File.separator)) {
			return true;
		}
		if (name.endsWith("/")) {
			return true;
		}
		if (name.endsWith(File.pathSeparator)) {
			return true;

		}
		return false;

	}

	public static List<String> readLines(File file) {
		List<String> lines = new ArrayList<>();
		ExtraAPI.consoleMessage(
				"§bFileAPI §fLendo §a" + file.getName() + "§f em UTF-8");
		try {
			if (Charset.isSupported("UTF-8")) {
				lines = Files.readAllLines(file.toPath(),
						Charset.forName("UTF-8"));
				return lines;
			}
		} catch (Exception e) {
			ExtraAPI.consoleMessage("§bFileAPI §cFALHA");
		}
		ExtraAPI.consoleMessage("§bFileAPI §fLendo §a" + file.getName()
				+ "§f em " + Charset.defaultCharset().toString().toUpperCase());
		try {
			lines = Files.readAllLines(file.toPath(), Charset.defaultCharset());
		} catch (Exception e) {
			ExtraAPI.consoleMessage("§bFileAPI §cFALHA");
		}
		return lines;
	}

	public static void writeLines(File file, List<String> lines) {
	
		try {
			file.getParentFile().mkdirs();
			if (Charset.isSupported("UTF-8")) {
				ExtraAPI.consoleMessage(
						"§bFileAPI §fEscrevendo §a" + file.getName()+"§f em UTF-8");
				Files.write(file.toPath(), lines, StandardCharsets.UTF_8);
			} else {
				ExtraAPI.consoleMessage(
						"§bFileAPI §fEscrevendo §a" + file.getName()+"§f em "+Charset.defaultCharset().toString().toUpperCase());
				Files.write(file.toPath(), lines, Charset.defaultCharset());
			}
		} catch (Exception e) {
			ExtraAPI.consoleMessage("§bFileAPI §cFALHA");
		}

	}
	@SuppressWarnings("deprecation")
	public static void setItem(ConfigurationSection section, ItemStack item) {
		section.set("id", item.getTypeId());
		section.set("data", item.getDurability());
		if (item.hasItemMeta()) {
			ItemMeta meta = item.getItemMeta();
			if (meta.hasDisplayName()) {
				section.set("name", meta.getDisplayName());
			}
			if (meta.hasLore()) {
				List<String> lines = new ArrayList<>();
				for (String line : meta.getLore()) {
					lines.add(line);
				}
				section.set("lore", lines);
			}
		}
		StringBuilder text = new StringBuilder();
		for (Entry<Enchantment, Integer> enchant : item.getEnchantments()
				.entrySet()) {
			text.append(
					enchant.getKey().getId() + "-" + enchant.getValue() + ",");
		}
		section.set("enchant", text.toString());
	}

	public static void setLocation(ConfigurationSection section,
			Location location) {
		section.set("x", location.getX());
		section.set("y", location.getY());
		section.set("z", location.getZ());
		section.set("yaw", location.getYaw());
		section.set("pitch", location.getPitch());
	}

	public static Location getLocation(ConfigurationSection section) {
		World world = Bukkit.getWorld(section.getString("world"));
		double x = section.getDouble("x");
		double y = section.getDouble("y");
		double z = section.getDouble("z");
		float yaw = (float) section.getDouble("yaw");
		float pitch = (float) section.getDouble("pitch");
		return new Location(world, x, y, z, yaw, pitch);
	}

	public static Location toLocation(String text) {
		String[] split = text.split(",");
		World world = Bukkit.getWorld(split[0]);
		double x = Double.parseDouble(split[1]);
		double y = Double.parseDouble(split[2]);
		double z = Double.parseDouble(split[3]);
		float yaw = Float.parseFloat(split[4]);
		float pitch = Float.parseFloat(split[5]);
		return new Location(world, x, y, z, yaw, pitch);
	}
	public static String saveLocation(Location location) {
		StringBuilder text = new StringBuilder();
		text.append(location.getWorld().getName() + ",");
		text.append(location.getX() + ",");
		text.append(location.getY() + ",");
		text.append(location.getZ() + ",");
		text.append(location.getYaw() + ",");
		text.append(location.getPitch());
		return text.toString();
	}
	/**
	 * Pega um Objecto serializavel do Arquivo
	 * 
	 * @param file
	 *            Arquivo
	 * @return Objeto
	 */
	public static Object getSerializable(File file) {
		if (!file.exists()) {
			return null;
		}
		try {

			FileInputStream getStream = new FileInputStream(file);
			ObjectInputStream get = new ObjectInputStream(getStream);
			Object object = get.readObject();
			get.close();
			getStream.close();
			return object;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}
	/**
	 * Salva um Objecto no Arquivo em forma de serialização Java
	 * 
	 * @param object
	 *            Objeto (Dado)
	 * @param file
	 *            Arquivo
	 */
	public static void setSerializable(Object object, File file) {
		try {
			FileOutputStream saveStream = new FileOutputStream(file);
			ObjectOutputStream save = new ObjectOutputStream(saveStream);
			if (object instanceof Serializable) {
				save.writeObject(object);
			} else {
			}
			save.close();
			saveStream.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * Desfazr o ZIP do Arquivo
	 * 
	 * @param zipFilePath
	 *            Arquivo
	 * @param destDirectory
	 *            Destino
	 */
	public static void unzip(String zipFilePath, String destDirectory)

	{
		try {
			File destDir = new File(destDirectory);
			if (!destDir.exists()) {
				destDir.mkdir();
			}
			ZipInputStream zipIn = new ZipInputStream(
					new FileInputStream(zipFilePath));
			ZipEntry entry = zipIn.getNextEntry();

			while (entry != null) {
				String filePath = destDirectory + File.separator
						+ entry.getName();
				if (!entry.isDirectory()) {
					extractFile(zipIn, filePath);
				} else {
					File dir = new File(filePath);
					dir.mkdir();
				}
				zipIn.closeEntry();
				entry = zipIn.getNextEntry();
			}
			zipIn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * Defaz o ZIP do Arquivo
	 * 
	 * @param zipIn
	 *            Input Stream (Coneção de Algum Arquivo)
	 * @param filePath
	 *            Destino Arquivo
	 */
	public static void extractFile(ZipInputStream zipIn, String filePath) {
		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(filePath));
			byte[] bytesIn = new byte[4096];
			int read = 0;
			while ((read = zipIn.read(bytesIn)) != -1) {
				bos.write(bytesIn, 0, read);
			}
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
