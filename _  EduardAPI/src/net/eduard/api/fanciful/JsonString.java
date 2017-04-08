package net.eduard.api.fanciful;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import net.minecraft.util.com.google.gson.stream.JsonWriter;

public  class JsonString
implements JsonRepresentedObject, ConfigurationSerializable
{
private String _value;

public JsonString(CharSequence value)
{
  this._value = (value == null ? null : value.toString());
}

public void writeJson(JsonWriter writer) throws IOException
{
  writer.value(getValue());
}

public String getValue() {
  return this._value;
}

public Map<String, Object> serialize() {
  HashMap theSingleValue = new HashMap();
  theSingleValue.put("stringValue", this._value);
  return theSingleValue;
}

public static JsonString deserialize(Map<String, Object> map) {
  return new JsonString(map.get("stringValue").toString());
}

public String toString()
{
  return this._value;
}
}
