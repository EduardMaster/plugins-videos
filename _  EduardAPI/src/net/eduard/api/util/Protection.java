package net.eduard.api.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.API;

public class Protection implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ip;
	private String key;
	private long before;
	private long timeForUseInSeconds;
	
	
	public static void main(String[] args) {
		File f = new File("F:/Tudo/Minecraft - Server/key.yml");
		Protection p = new Protection("25.13.66.79", 60*60*24*30);
		System.out.println(desobfuscar(p.getKey()));
		p.save(f);
	}

	private static String obfuscar(String str) {
		String build = "";
		for (int i = 0; i < str.length(); i++) {
			build = build.equals("")
					? "" + (str.charAt(i) + str.length() * str.length())
					: build + ";"
							+ (str.charAt(i) + str.length() * str.length());
		}
		return build;
	}

	private static String desobfuscar(String str) {
		final String[] split = str.split(";");
		final int[] parse = new int[split.length];
		for (int i = 0; i < split.length; i++) {
			parse[i] = Integer.parseInt(split[i]) - split.length * split.length;
		}
		String build = "";
		for (int i = 0; i < split.length; i++) {
			build = build + (char) parse[i];
		}
		return build;
	}

	private Protection(String ip, long timeForUseInSeconds) {
		this.ip = obfuscar(ip);
		this.key = obfuscar(UUID.randomUUID().toString());
		this.before = System.currentTimeMillis();
		this.timeForUseInSeconds = timeForUseInSeconds;
	}
	public boolean canUsePlugin(String key) {
		if ((desobfuscar(ip).equals(Bukkit.getIp())
				| Bukkit.getIp().equals("localhost"))
				&& desobfuscar(this.key).equals(key)) {
			return (before + (timeForUseInSeconds * 1000))> (System.currentTimeMillis() );
		}
		return false;
	}
	
	public static void test(JavaPlugin plugin){
		File f = new File(plugin.getDataFolder(),"key.yml");
		if (!f.exists())
		{
			API.console("§bProtector §cNao possui uma Key! §fPlugin desativado!");
			Bukkit.getPluginManager().disablePlugin(plugin);
			return;
		}
		Protection protect = Protection.load(f);
		if (!protect.canUsePlugin(plugin.getConfig().getString("key"))){
			API.console("§bProtector §cKey invalida! §fPlugin desativado!");
			Bukkit.getPluginManager().disablePlugin(plugin);
			return;
		}
		API.console("§bProtector §aKey valida! §fPlugin ativado com sucesso!");
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
	private static Protection load(File file) {
		try {
			if (file.exists()) {
				ObjectInputStream in = new ObjectInputStream(
						new FileInputStream(file));
				Object object = in.readObject();
				in.close();
				return (Protection) object;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public long getBefore() {
		return before;
	}
	public void setBefore(long before) {
		this.before = before;
	}
	public long getTimeForUseInSeconds() {
		return timeForUseInSeconds;
	}
	public void setTimeForUseInSeconds(long timeForUseInSeconds) {
		this.timeForUseInSeconds = timeForUseInSeconds;
	}

}
