package net.eduard.api.fanciful;

import java.io.IOException;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerialization;

import com.google.common.collect.ImmutableMap;

import net.minecraft.util.com.google.gson.stream.JsonWriter;

public abstract class TextualComponent implements Cloneable {
	static {
		ConfigurationSerialization
				.registerClass(ArbitraryTextTypeComponent.class);
		ConfigurationSerialization
				.registerClass(ComplexTextTypeComponent.class);
	}

	public String toString() {
		return getReadableString();
	}

	public abstract String getKey();

	public abstract String getReadableString();

	public abstract TextualComponent clone() throws CloneNotSupportedException;

	public abstract void writeJson(JsonWriter paramJsonWriter)
			throws IOException;

	static TextualComponent deserialize(Map<String, Object> map) {
		if ((map.containsKey("key")) && (map.size() == 2)
				&& (map.containsKey("value"))) {
			return ArbitraryTextTypeComponent.deserialize(map);
		}
		if ((map.size() >= 2) && (map.containsKey("key"))
				&& (!map.containsKey("value"))) {
			return ComplexTextTypeComponent.deserialize(map);
		}

		return null;
	}

	static boolean isTextKey(String key) {
		return (key.equals("translate")) || (key.equals("text"))
				|| (key.equals("score")) || (key.equals("selector"));
	}

	static boolean isTranslatableText(TextualComponent component) {
		return ((component instanceof ComplexTextTypeComponent))
				&& (((ComplexTextTypeComponent) component).getKey()
						.equals("translate"));
	}

	public static TextualComponent rawText(String textValue) {
		return (TextualComponent) new ArbitraryTextTypeComponent("text", textValue);
	}

	public static TextualComponent localizedText(String translateKey) {
		return (TextualComponent) new ArbitraryTextTypeComponent("translate", translateKey);
	}

	private static void throwUnsupportedSnapshot() {
		throw new UnsupportedOperationException(
				"This feature is only supported in snapshot releases.");
	}

	public static TextualComponent objectiveScore(String scoreboardObjective) {
		return objectiveScore("*", scoreboardObjective);
	}

	public static TextualComponent objectiveScore(String playerName,
			String scoreboardObjective) {
		throwUnsupportedSnapshot();

		return new ComplexTextTypeComponent("score",
				ImmutableMap.builder().put("name", playerName)
						.put("objective", scoreboardObjective).build());
	}

	public static TextualComponent selector(String selector) {
		throwUnsupportedSnapshot();

		return new ArbitraryTextTypeComponent("selector", selector);
	}

}
