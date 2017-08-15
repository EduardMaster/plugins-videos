package net.eduard.api.config.master;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

public class MasterConfig {
	
	public static void main(String[] args) {
		try {

			String teste = getContent(getResource("teste.yml"));
			
			new MasterSection().reload(teste);
		} catch (Exception e) {
		}

	}
	public static Path getResource(String name) throws URISyntaxException {
		return Paths.get(MasterSection.class.getResource(name).toURI());
	}
	public static String getContent(Path path) {
		StringBuilder builder = new StringBuilder();
		try {

			for (String line : Files.readAllLines(path,
					Charset.forName("UTF-8"))) {
				builder.append(line);
				builder.append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return builder.toString();

	}
	public static List<String> getLines(Path path) throws IOException {

		return Files.readAllLines(path, Charset.forName("UTF-8"));
	}
	
	private JavaPlugin plugin;
	private File file;
	private MasterSection config;
	private String name;
	private List<String> lines;
	public JavaPlugin getPlugin() {
		return plugin;
	}
	public void setPlugin(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public MasterSection getConfig() {
		return config;
	}
	public void setConfig(MasterSection config) {
		this.config = config;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getLines() {
		return lines;
	}
	public void setLines(List<String> lines) {
		this.lines = lines;
	}
	

}
