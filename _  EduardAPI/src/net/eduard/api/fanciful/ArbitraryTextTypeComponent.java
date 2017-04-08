package net.eduard.api.fanciful;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.google.common.base.Preconditions;

import net.minecraft.util.com.google.gson.stream.JsonWriter;

public class ArbitraryTextTypeComponent extends TextualComponent
implements ConfigurationSerializable
{
private String _key;
private String _value;

public ArbitraryTextTypeComponent(String key, String value)
{
  setKey(key);
  setValue(value);
}

public String getKey()
{
  return this._key;
}

public void setKey(String key) {
  Preconditions.checkArgument((key != null) && (!key.isEmpty()), "The key must be specified.");
  this._key = key;
}

public String getValue() {
  return this._value;
}

public void setValue(String value) {
  Preconditions.checkArgument(value != null, "The value must be specified.");
  this._value = value;
}

public TextualComponent clone()
  throws CloneNotSupportedException
{
  return new ArbitraryTextTypeComponent(getKey(), getValue());
}

public void writeJson(JsonWriter writer) throws IOException
{
  writer.name(getKey()).value(getValue());
}


public Map<String, Object> serialize()
{
  return new HashMap<String, Object>()
  {
  };
}

public static ArbitraryTextTypeComponent deserialize(Map<String, Object> map)
{
  return new ArbitraryTextTypeComponent(map.get("key").toString(), map.get("value").toString());
}

public String getReadableString()
{
  return getValue();
}
}
