package net.eduard.api.fanciful;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import net.minecraft.util.com.google.gson.stream.JsonWriter;

public  class ComplexTextTypeComponent extends TextualComponent implements ConfigurationSerializable
{
  private String _key;
  private Map<String, String> _value;

  public ComplexTextTypeComponent(String key, Map<String, String> values) {
    setKey(key);
    setValue(values);
  }

  public ComplexTextTypeComponent(String key, ImmutableMap<Object, Object> build) {
		// TODO Auto-generated constructor stub
	}

	public String getKey()
  {
    return this._key;
  }

  public void setKey(String key) {
    Preconditions.checkArgument((key != null) && (!key.isEmpty()), "The key must be specified.");
    this._key = key;
  }

  public Map<String, String> getValue() {
    return this._value;
  }

  public void setValue(Map<String, String> value) {
    Preconditions.checkArgument(value != null, "The value must be specified.");
    this._value = value;
  }

  public TextualComponent clone()
    throws CloneNotSupportedException
  {
    return new ComplexTextTypeComponent(getKey(), getValue());
  }

  public void writeJson(JsonWriter writer) throws IOException
  {
    writer.name(getKey());
    writer.beginObject();
    for (Map.Entry jsonPair : this._value.entrySet()) {
      writer.name((String)jsonPair.getKey()).value((String)jsonPair.getValue());
    }
    writer.endObject();
  }


	public Map<String, Object> serialize()
  {
    return new HashMap()
    {
    };
  }

  public static ComplexTextTypeComponent deserialize(Map<String, Object> map)
  {
    String key = null;
    Map value = new HashMap();
    for (Map.Entry valEntry : map.entrySet()) {
      if (((String)valEntry.getKey()).equals("key"))
        key = (String)valEntry.getValue();
      else if (((String)valEntry.getKey()).startsWith("value.")) {
        value.put(((String)valEntry.getKey()).substring(6), valEntry.getValue().toString());
      }
    }
    return new ComplexTextTypeComponent(key, value);
  }

  public String getReadableString()
  {
    return getKey();
  }
}

