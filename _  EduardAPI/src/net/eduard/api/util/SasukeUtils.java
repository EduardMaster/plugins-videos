package net.eduard.api.util;

import java.io.File;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.setup.Extra;


/**
 * 
 * @author SasukeMCHCs
 * 
 */
public final class SasukeUtils {


	public static Set<Class<?>> getPackages(File file, String name) {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		try {
			JarFile jar = new JarFile(file);
			for (Enumeration<JarEntry> entry = jar.entries(); entry.hasMoreElements();) {
				JarEntry jarEntry = (JarEntry) entry.nextElement();
				String named = jarEntry.getName().replace("/", ".");
				if ((named.startsWith(name)) && (named.endsWith(".class")) && (!named.contains("$"))) {
					classes.add(Class.forName(named.substring(0, named.length() - 6)));
				}
			}
			jar.close();
		} catch (Exception localException) {
		}
		return classes;
	}


	
	
	public static ArrayList<Class<?>> getClassesForPackage(JavaPlugin plugin, String pkgname) {
		ArrayList<Class<?>> classes = new ArrayList<>();

		CodeSource src = plugin.getClass().getProtectionDomain().getCodeSource();
		if (src != null) {
			URL resource = src.getLocation();
			resource.getPath();
			Extra.processJarfile(resource, pkgname, classes);
		}
		ArrayList<String> names = new ArrayList<>();
		ArrayList<Class<?>> classi = new ArrayList<>();
		for (Class<?> classy : classes) {
			names.add(classy.getSimpleName());
			classi.add(classy);
		}
		classes.clear();
		Collections.sort(names, String.CASE_INSENSITIVE_ORDER);
		for (String s : names) {
			for (Class<?> classy : classi)
				if (classy.getSimpleName().equals(s)) {
					classes.add(classy);
					break;
				}
		}
		return classes;
	}
}
