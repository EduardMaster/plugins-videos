package net.eduard.api.fanciful;

import java.io.IOException;

import net.minecraft.util.com.google.gson.stream.JsonWriter;

public interface JsonRepresentedObject {
	public abstract void writeJson(JsonWriter paramJsonWriter)
			throws IOException;
}