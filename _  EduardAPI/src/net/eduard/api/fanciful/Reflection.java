package net.eduard.api.fanciful;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;

public final class Reflection {
	private static String _versionString;
	private static final Map<String, Class<?>> _loadedNMSClasses = new HashMap();

	private static final Map<String, Class<?>> _loadedOBCClasses = new HashMap();

	private static final Map<Class<?>, Map<String, Field>> _loadedFields = new HashMap();

	private static final Map<Class<?>, Map<String, Map<ArrayWrapper<Class<?>>, Method>>> _loadedMethods = new HashMap();

	public static synchronized String getVersion() {
		if (_versionString == null) {
			if (Bukkit.getServer() == null) {
				return null;
			}
			String name = Bukkit.getServer().getClass().getPackage().getName();
			_versionString = name.substring(name.lastIndexOf('.') + 1) + ".";
		}

		return _versionString;
	}

	public static synchronized Class<?> getNMSClass(String className) {
		if (_loadedNMSClasses.containsKey(className)) {
			return (Class) _loadedNMSClasses.get(className);
		}

		String fullName = "net.minecraft.server." + getVersion() + className;
		Class clazz = null;
		try {
			clazz = Class.forName(fullName);
		} catch (Exception e) {
			e.printStackTrace();
			_loadedNMSClasses.put(className, null);
			return null;
		}
		_loadedNMSClasses.put(className, clazz);
		return clazz;
	}

	public static synchronized Class<?> getOBCClass(String className) {
		if (_loadedOBCClasses.containsKey(className)) {
			return (Class) _loadedOBCClasses.get(className);
		}

		String fullName = "org.bukkit.craftbukkit." + getVersion() + className;
		Class clazz = null;
		try {
			clazz = Class.forName(fullName);
		} catch (Exception e) {
			e.printStackTrace();
			_loadedOBCClasses.put(className, null);
			return null;
		}
		_loadedOBCClasses.put(className, clazz);
		return clazz;
	}

	public static synchronized Object getHandle(Object obj) {
		try {
			return getMethod(obj.getClass(), "getHandle", new Class[0])
					.invoke(obj, new Object[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static synchronized Field getField(Class<?> clazz, String name) {
		Map loaded;
		if (!_loadedFields.containsKey(clazz)) {
			loaded = new HashMap();
			_loadedFields.put(clazz, loaded);
		} else {
			loaded = (Map) _loadedFields.get(clazz);
		}
		if (loaded.containsKey(name)) {
			return (Field) loaded.get(name);
		}
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			loaded.put(name, field);
			return field;
		} catch (Exception e) {
			e.printStackTrace();

			loaded.put(name, null);
		}
		return null;
	}

	public static synchronized Method getMethod(Class<?> clazz, String name,
			Class<?>[] args) {
		if (!_loadedMethods.containsKey(clazz)) {
			_loadedMethods.put(clazz, new HashMap());
		}

		Map loadedMethodNames = (Map) _loadedMethods.get(clazz);
		if (!loadedMethodNames.containsKey(name)) {
			loadedMethodNames.put(name, new HashMap());
		}

		Map loadedSignatures = (Map) loadedMethodNames.get(name);
		ArrayWrapper wrappedArg = new ArrayWrapper(args);
		if (loadedSignatures.containsKey(wrappedArg)) {
			return (Method) loadedSignatures.get(wrappedArg);
		}

		for (Method m : clazz.getMethods()) {
			if ((m.getName().equals(name))
					&& (Arrays.equals(args, m.getParameterTypes()))) {
				m.setAccessible(true);
				loadedSignatures.put(wrappedArg, m);
				return m;
			}
		}
		loadedSignatures.put(wrappedArg, null);
		return null;
	}
}