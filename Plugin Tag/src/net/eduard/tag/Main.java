
package net.eduard.tag;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.config.Config;
import net.eduard.api.dev.Tag;
import net.eduard.tag.command.DeleteTagCMD;
import net.eduard.tag.command.SetTagCMD;
import net.eduard.tag.command.TagCMD;
import net.eduard.tag.command.TagsCMD;
import net.eduard.tag.event.TagEvent;

public class Main extends JavaPlugin  {

	public static Config config;
	private static HashMap<String, Tag> tags = new HashMap<>();
	private static Config tagsConfig;
	public static boolean hasTags() {
		return !tags.isEmpty();
	}
	public static void saveTags() {
		
		for(Entry<String, Tag> map: tags.entrySet()) {
			String name = map.getKey();
			Tag tag = map.getValue();
			tagsConfig.set(name,tag);
		}
		tagsConfig.saveConfig();
	}
	public static void reloadTags() {
		Config c = config.createConfig("Tags.yml");
		for(String key:c.getKeys()) {
			Tag tag = (Tag)c.get(key);
			tags.put(key, tag);
		}
	}

	public static Tag getTag(String name) {
		return tags.get(name.toLowerCase());
	}
	public void onEnable() {

		config = new Config(this);
		config.add("ChangeChat", true);
		
		config.add("Chat", "$prefix$player&7:&f $suffix$message");
		config.add("ShowTag", "$prefix$player&f:$suffix $tag $prefix $tag");
		config.add("SetTag", "&bTag criada: prefix: $prefix $tag suffix: $suffix $tag");
		config.add("Tag", "&6Sua tag foi alterada para &e$tag");
		config.add("DeleteTag", "&cTag removida: &f$tag");
		config.add("noTag", "&cTag invalida &e$tag");
		config.add("noTags", "&cNao foi criada nenhuma Tag!");
		config.saveConfig();
		tagsConfig = config.createConfig("Tags.yml");
		new TagEvent();
		new TagCMD();
		new DeleteTagCMD();
		new TagsCMD();
		new SetTagCMD();
		reloadTags();
	}
	public void onDisable() {
		saveTags();
	}

	public static void showTags(CommandSender sender) {
		for (Tag tag:tags.values()) {
			if (sender.hasPermission("tags."+tag.getName())) {
				sender.sendMessage(tag.getPrefix()+sender.getName()+": "+tag.getSuffix()+tag.getName());
			}
		}

	}

	public static Tag create(String name, Tag tag) {
		tags.put(name.toLowerCase(), tag);
		tagsConfig.saveConfig();
		return tag;
	}

	public static boolean exists(String name) {
		return tags.containsKey(name.toLowerCase());
	}

	public static void showHelp(CommandSender sender) {

		if (sender.hasPermission("tag.admin")) {
			sender.sendMessage("§a/settag <name> <prefix> [suffix] §bCrie uma Tag");
			sender.sendMessage("§a/deletetag <name> §bRemova Tags");
		}
		sender.sendMessage("§a/tags §bListe as Tags");
		sender.sendMessage("§a/tag <name> [player] §bEscolha uma tag");
	}
	public static void delete(String name) {
		tags.remove(name.toLowerCase());
	}

}
