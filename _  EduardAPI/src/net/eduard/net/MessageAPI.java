package net.eduard.net;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.apache.commons.lang.Validate;
import org.bukkit.Achievement;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;

import net.minecraft.util.com.google.gson.JsonArray;
import net.minecraft.util.com.google.gson.JsonElement;
import net.minecraft.util.com.google.gson.JsonObject;
import net.minecraft.util.com.google.gson.JsonParser;
import net.minecraft.util.com.google.gson.stream.JsonWriter;

@SuppressWarnings({"unchecked" ,"rawtypes","unused", "serial"})
public class MessageAPI {
	  private static final class ComplexTextTypeComponent extends TextualComponent implements ConfigurationSerializable
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
	  public static final class JsonString
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
	  public static final class MessagePart
	  implements JsonRepresentedObject, ConfigurationSerializable, Cloneable
	{
	  ChatColor color = ChatColor.WHITE;
	  ArrayList<ChatColor> styles = new ArrayList();
	  String clickActionName = null; String clickActionData = null; String hoverActionName = null;
	  JsonRepresentedObject hoverActionData = null;
	  TextualComponent text = null;
	  String insertionData = null;
	  ArrayList<JsonRepresentedObject> translationReplacements = new ArrayList();
	  static final BiMap<ChatColor, String> stylesToNames;

	  static
	  {
	    ImmutableBiMap.Builder builder = ImmutableBiMap.builder();
	    for (ChatColor style : ChatColor.values())
	      if (style.isFormat())
	      {
	        String styleName;
	        switch (style) {
	        case RED:
	          styleName = "obfuscated";
	          break;
	        case UNDERLINE:
	          styleName = "underlined";
	          break;
	        case RESET:
	        case STRIKETHROUGH:
	        default:
	          styleName = style.name().toLowerCase();
	        }

	        builder.put(style, styleName);
	      }
	    stylesToNames = builder.build();

	    ConfigurationSerialization.registerClass(MessagePart.class);
	  }

	  MessagePart(TextualComponent text)
	  {
	    this.text = text;
	  }

	  MessagePart() {
	    this.text = null;
	  }

	  boolean hasText() {
	    return this.text != null;
	  }

	  public MessagePart clone()
	    throws CloneNotSupportedException
	  {
	    MessagePart obj = (MessagePart)super.clone();
	    obj.styles = ((ArrayList)this.styles.clone());
	    if ((this.hoverActionData instanceof JsonString))
	      obj.hoverActionData = new JsonString(((JsonString)this.hoverActionData).getValue());
	    else if ((this.hoverActionData instanceof FancyMessage)) {
	      obj.hoverActionData = ((FancyMessage)this.hoverActionData).clone();
	    }
	    obj.translationReplacements = ((ArrayList)this.translationReplacements.clone());
	    return obj;
	  }

	  public void writeJson(JsonWriter json)
	  {
	    try
	    {
	      json.beginObject();
	      this.text.writeJson(json);
	      json.name("color").value(this.color.name().toLowerCase());
	      for (ChatColor style : this.styles) {
	        json.name((String)stylesToNames.get(style)).value(true);
	      }
	      if ((this.clickActionName != null) && (this.clickActionData != null)) {
	        json.name("clickEvent")
	          .beginObject()
	          .name("action").value(this.clickActionName)
	          .name("value").value(this.clickActionData)
	          .endObject();
	      }
	      if ((this.hoverActionName != null) && (this.hoverActionData != null)) {
	        json.name("hoverEvent")
	          .beginObject()
	          .name("action").value(this.hoverActionName)
	          .name("value");
	        this.hoverActionData.writeJson(json);
	        json.endObject();
	      }
	      if (this.insertionData != null) {
	        json.name("insertion").value(this.insertionData);
	      }
	      if ((this.translationReplacements.size() > 0) && (this.text != null) && (TextualComponent.isTranslatableText(this.text))) {
	        json.name("with").beginArray();
	        for (JsonRepresentedObject obj : this.translationReplacements) {
	          obj.writeJson(json);
	        }
	        json.endArray();
	      }
	      json.endObject();
	    } catch (IOException e) {
	      Bukkit.getLogger().log(Level.WARNING, "A problem occured during writing of JSON string", e);
	    }
	  }

	  public Map<String, Object> serialize() {
	    HashMap map = new HashMap();
	    map.put("text", this.text);
	    map.put("styles", this.styles);
	    map.put("color", Character.valueOf(this.color.getChar()));
	    map.put("hoverActionName", this.hoverActionName);
	    map.put("hoverActionData", this.hoverActionData);
	    map.put("clickActionName", this.clickActionName);
	    map.put("clickActionData", this.clickActionData);
	    map.put("insertion", this.insertionData);
	    map.put("translationReplacements", this.translationReplacements);
	    return map;
	  }

	  public static MessagePart deserialize(Map<String, Object> serialized)
	  {
	    MessagePart part = new MessagePart((TextualComponent)serialized.get("text"));
	    part.styles = ((ArrayList)serialized.get("styles"));
	    part.color = ChatColor.getByChar(serialized.get("color").toString());
	    part.hoverActionName = ((String)serialized.get("hoverActionName"));
	    part.hoverActionData = (JsonRepresentedObject) ((Cloneable)serialized.get("hoverActionData"));
	    part.clickActionName = ((String)serialized.get("clickActionName"));
	    part.clickActionData = ((String)serialized.get("clickActionData"));
	    part.insertionData = ((String)serialized.get("insertion"));
	    part.translationReplacements = ((ArrayList)serialized.get("translationReplacements"));
	    return part;
	  }
	}
	 public static final class ArrayWrapper<E>
	  {
	    private E[] _array;

	    public ArrayWrapper(E[] elements)
	    {
	      setArray(elements);
	    }

	    public E[] getArray()
	    {
	      return this._array;
	    }

	    public void setArray(E[] array)
	    {
	      Validate.notNull(array, "The array must not be null.");
	      this._array = array;
	    }

	    public boolean equals(Object other)
	    {
	      if (!(other instanceof ArrayWrapper)) {
	        return false;
	      }
	      return Arrays.equals(this._array, ((ArrayWrapper)other)._array);
	    }

	    public int hashCode()
	    {
	      return Arrays.hashCode(this._array);
	    }

	    public static <T> T[] toArray(Iterable<? extends T> list, Class<T> c)
	    {
	      int size = -1;
	      if ((list instanceof Collection))
	      {
	       
			Collection coll = (Collection)list;
	        size = coll.size();
	      }

	      if (size < 0) {
	        size = 0;

	        for ( Object element : list) {
	          size++;
	        }
	      }

	      Object[] result = (Object[])Array.newInstance(c, size);
	      int i = 0;
	      for (Object element : list) {
	        result[(i++)] = element;
	      }
	      return (T[]) result;
	    }
	  }
	  public static abstract class TextualComponent  implements Cloneable
		{
		  static
		  {
		    ConfigurationSerialization.registerClass(ArbitraryTextTypeComponent.class);
		    ConfigurationSerialization.registerClass(ComplexTextTypeComponent.class);
		  }

		  public String toString()
		  {
		    return getReadableString();
		  }

		  public abstract String getKey();

		  public abstract String getReadableString();

		  public abstract TextualComponent clone()
		    throws CloneNotSupportedException;

		  public abstract void writeJson(JsonWriter paramJsonWriter)
		    throws IOException;

		  static TextualComponent deserialize(Map<String, Object> map)
		  {
		    if ((map.containsKey("key")) && (map.size() == 2) && (map.containsKey("value")))
		    {
		      return ArbitraryTextTypeComponent.deserialize(map);
		    }if ((map.size() >= 2) && (map.containsKey("key")) && (!map.containsKey("value")))
		    {
		      return ComplexTextTypeComponent.deserialize(map);
		    }

		    return null;
		  }

		  static boolean isTextKey(String key) {
		    return (key.equals("translate")) || (key.equals("text")) || (key.equals("score")) || (key.equals("selector"));
		  }

		  static boolean isTranslatableText(TextualComponent component) {
		    return ((component instanceof ComplexTextTypeComponent)) && (((ComplexTextTypeComponent)component).getKey().equals("translate"));
		  }

		  public static TextualComponent rawText(String textValue)
		  {
		    return new ArbitraryTextTypeComponent("text", textValue);
		  }

		  public static TextualComponent localizedText(String translateKey)
		  {
		    return new ArbitraryTextTypeComponent("translate", translateKey);
		  }

		  private static void throwUnsupportedSnapshot() {
		    throw new UnsupportedOperationException("This feature is only supported in snapshot releases.");
		  }

		  public static TextualComponent objectiveScore(String scoreboardObjective)
		  {
		    return objectiveScore("*", scoreboardObjective);
		  }

		  public static TextualComponent objectiveScore(String playerName, String scoreboardObjective)
		  {
		    throwUnsupportedSnapshot();

		    return new ComplexTextTypeComponent("score", ImmutableMap.builder()
		      .put("name", playerName)
		      .put("objective", scoreboardObjective)
		      .build());
		  }

		  public static TextualComponent selector(String selector)
		  {
		    throwUnsupportedSnapshot();

		    return new ArbitraryTextTypeComponent("selector", selector);
		  }

		}
	  private static final class ArbitraryTextTypeComponent extends TextualComponent
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
	      return new HashMap()
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

	public static interface JsonRepresentedObject
	  {
	    public abstract void writeJson(JsonWriter paramJsonWriter)
	      throws IOException;
	  }
	
	
	public static class FancyMessage  implements JsonRepresentedObject, Cloneable, Iterable<MessagePart>, ConfigurationSerializable
	{
	  private List<MessagePart> messageParts;
	  private String jsonString;
	  private boolean dirty;
	  private static Constructor<?> nmsPacketPlayOutChatConstructor;
	  private static Object nmsChatSerializerGsonInstance;
	  private static Method fromJsonMethod;
	  private static JsonParser _stringParser = new JsonParser();

	  static
	  {
	    ConfigurationSerialization.registerClass(FancyMessage.class);
	  }

	  public FancyMessage clone()
	    throws CloneNotSupportedException
	  {
	    FancyMessage instance = (FancyMessage)super.clone();
	    instance.messageParts = new ArrayList(this.messageParts.size());
	    for (int i = 0; i < this.messageParts.size(); i++) {
	      instance.messageParts.add(i, ((MessagePart)this.messageParts.get(i)).clone());
	    }
	    instance.dirty = false;
	    instance.jsonString = null;
	    return instance;
	  }

	  public FancyMessage(String firstPartText)
	  {
	    this(TextualComponent.rawText(firstPartText));
	  }

	  public FancyMessage(TextualComponent firstPartText) {
	    this.messageParts = new ArrayList();
	    this.messageParts.add(new MessagePart(firstPartText));
	    this.jsonString = null;
	    this.dirty = false;

	    if (nmsPacketPlayOutChatConstructor == null)
	      try {
	        nmsPacketPlayOutChatConstructor = Reflection.getNMSClass("PacketPlayOutChat").getDeclaredConstructor(new Class[] { Reflection.getNMSClass("IChatBaseComponent") });
	        nmsPacketPlayOutChatConstructor.setAccessible(true);
	      } catch (NoSuchMethodException e) {
	        Bukkit.getLogger().log(Level.SEVERE, "Could not find Minecraft method or constructor.", e);
	      } catch (SecurityException e) {
	        Bukkit.getLogger().log(Level.WARNING, "Could not access constructor.", e);
	      }
	  }

	  public FancyMessage()
	  {
	  }

	  public FancyMessage text(String text)
	  {
	    MessagePart latest = latest();
	    latest.text = TextualComponent.rawText(text);
	    this.dirty = true;
	    return this;
	  }

	  public FancyMessage text(TextualComponent text)
	  {
	    MessagePart latest = latest();
	    latest.text = text;
	    this.dirty = true;
	    return this;
	  }

	  public FancyMessage color(ChatColor color)
	  {
	    if (!color.isColor()) {
	      throw new IllegalArgumentException(color.name() + " is not a color");
	    }
	    latest().color = color;
	    this.dirty = true;
	    return this;
	  }

	  public FancyMessage style(ChatColor[] styles)
	  {
	    for (ChatColor style : styles) {
	      if (!style.isFormat()) {
	        throw new IllegalArgumentException(style.name() + " is not a style");
	      }
	    }
	    latest().styles.addAll(Arrays.asList(styles));
	    this.dirty = true;
	    return this;
	  }

	  public FancyMessage file(String path)
	  {
	    onClick("open_file", path);
	    return this;
	  }

	  public FancyMessage link(String url)
	  {
	    onClick("open_url", url);
	    return this;
	  }

	  public FancyMessage suggest(String command)
	  {
	    onClick("suggest_command", command);
	    return this;
	  }

	  public FancyMessage insert(String command)
	  {
	    latest().insertionData = command;
	    this.dirty = true;
	    return this;
	  }

	  public FancyMessage command(String command)
	  {
	    onClick("run_command", command);
	    return this;
	  }

	  public FancyMessage achievementTooltip(String name)
	  {
	    onHover("show_achievement", new JsonString("achievement." + name));
	    return this;
	  }

	  public FancyMessage achievementTooltip(Achievement which)
	  {
	    try
	    {
	      Object achievement = Reflection.getMethod(Reflection.getOBCClass("CraftStatistic"), "getNMSAchievement", new Class[] { Achievement.class }).invoke(null, new Object[] { which });
	      return achievementTooltip((String)Reflection.getField(Reflection.getNMSClass("Achievement"), "name").get(achievement));
	    } catch (IllegalAccessException e) {
	      Bukkit.getLogger().log(Level.WARNING, "Could not access method.", e);
	      return this;
	    } catch (IllegalArgumentException e) {
	      Bukkit.getLogger().log(Level.WARNING, "Argument could not be passed.", e);
	      return this;
	    } catch (InvocationTargetException e) {
	      Bukkit.getLogger().log(Level.WARNING, "A error has occured durring invoking of method.", e);
	    }return this;
	  }

	  public FancyMessage statisticTooltip(Statistic which)
	  {
	    Statistic.Type type = which.getType();
	    if (type != Statistic.Type.UNTYPED)
	      throw new IllegalArgumentException("That statistic requires an additional " + type + " parameter!");
	    try
	    {
	      Object statistic = Reflection.getMethod(Reflection.getOBCClass("CraftStatistic"), "getNMSStatistic", new Class[] { Statistic.class }).invoke(null, new Object[] { which });
	      return achievementTooltip((String)Reflection.getField(Reflection.getNMSClass("Statistic"), "name").get(statistic));
	    } catch (IllegalAccessException e) {
	      Bukkit.getLogger().log(Level.WARNING, "Could not access method.", e);
	      return this;
	    } catch (IllegalArgumentException e) {
	      Bukkit.getLogger().log(Level.WARNING, "Argument could not be passed.", e);
	      return this;
	    } catch (InvocationTargetException e) {
	      Bukkit.getLogger().log(Level.WARNING, "A error has occured durring invoking of method.", e);
	    }return this;
	  }

	  public FancyMessage statisticTooltip(Statistic which, Material item)
	  {
	    Statistic.Type type = which.getType();
	    if (type == Statistic.Type.UNTYPED) {
	      throw new IllegalArgumentException("That statistic needs no additional parameter!");
	    }
	    if (((type == Statistic.Type.BLOCK) && (item.isBlock())) || (type == Statistic.Type.ENTITY))
	      throw new IllegalArgumentException("Wrong parameter type for that statistic - needs " + type + "!");
	    try
	    {
	      Object statistic = Reflection.getMethod(Reflection.getOBCClass("CraftStatistic"), "getMaterialStatistic", new Class[] { Statistic.class, Material.class }).invoke(null, new Object[] { which, item });
	      return achievementTooltip((String)Reflection.getField(Reflection.getNMSClass("Statistic"), "name").get(statistic));
	    } catch (IllegalAccessException e) {
	      Bukkit.getLogger().log(Level.WARNING, "Could not access method.", e);
	      return this;
	    } catch (IllegalArgumentException e) {
	      Bukkit.getLogger().log(Level.WARNING, "Argument could not be passed.", e);
	      return this;
	    } catch (InvocationTargetException e) {
	      Bukkit.getLogger().log(Level.WARNING, "A error has occured durring invoking of method.", e);
	    }return this;
	  }

	  public FancyMessage statisticTooltip(Statistic which, EntityType entity)
	  {
	    Statistic.Type type = which.getType();
	    if (type == Statistic.Type.UNTYPED) {
	      throw new IllegalArgumentException("That statistic needs no additional parameter!");
	    }
	    if (type != Statistic.Type.ENTITY)
	      throw new IllegalArgumentException("Wrong parameter type for that statistic - needs " + type + "!");
	    try
	    {
	      Object statistic = Reflection.getMethod(Reflection.getOBCClass("CraftStatistic"), "getEntityStatistic", new Class[] { Statistic.class, EntityType.class }).invoke(null, new Object[] { which, entity });
	      return achievementTooltip((String)Reflection.getField(Reflection.getNMSClass("Statistic"), "name").get(statistic));
	    } catch (IllegalAccessException e) {
	      Bukkit.getLogger().log(Level.WARNING, "Could not access method.", e);
	      return this;
	    } catch (IllegalArgumentException e) {
	      Bukkit.getLogger().log(Level.WARNING, "Argument could not be passed.", e);
	      return this;
	    } catch (InvocationTargetException e) {
	      Bukkit.getLogger().log(Level.WARNING, "A error has occured durring invoking of method.", e);
	    }return this;
	  }

	  public FancyMessage itemTooltip(String itemJSON)
	  {
	    onHover("show_item", new JsonString(itemJSON));
	    return this;
	  }

	  public FancyMessage itemTooltip(ItemStack itemStack)
	  {
	    try
	    {
	      Object nmsItem = Reflection.getMethod(Reflection.getOBCClass("inventory.CraftItemStack"), "asNMSCopy", new Class[] { ItemStack.class }).invoke(null, new Object[] { itemStack });
	      return itemTooltip(Reflection.getMethod(Reflection.getNMSClass("ItemStack"), "save", new Class[] { Reflection.getNMSClass("NBTTagCompound") }).invoke(nmsItem, new Object[] { Reflection.getNMSClass("NBTTagCompound").newInstance() }).toString());
	    } catch (Exception e) {
	      e.printStackTrace();
	    }return this;
	  }

	  public FancyMessage tooltip(String text)
	  {
	    onHover("show_text", new JsonString(text));
	    return this;
	  }

	  public FancyMessage tooltip(Iterable<String> lines)
	  {
	    tooltip((String[])ArrayWrapper.toArray(lines, String.class));
	    return this;
	  }

	  public FancyMessage tooltip(String[] lines)
	  {
	    StringBuilder builder = new StringBuilder();
	    for (int i = 0; i < lines.length; i++) {
	      builder.append(lines[i]);
	      if (i != lines.length - 1) {
	        builder.append('\n');
	      }
	    }
	    tooltip(builder.toString());
	    return this;
	  }

	  public FancyMessage formattedTooltip(FancyMessage text)
	  {
	    for (MessagePart component : text.messageParts) {
	      if ((component.clickActionData != null) && (component.clickActionName != null))
	        throw new IllegalArgumentException("The tooltip text cannot have click data.");
	      if ((component.hoverActionData != null) && (component.hoverActionName != null)) {
	        throw new IllegalArgumentException("The tooltip text cannot have a tooltip.");
	      }
	    }
	    onHover("show_text", text);
	    return this;
	  }

	  public FancyMessage formattedTooltip(FancyMessage[] lines)
	  {
	    if (lines.length < 1) {
	      onHover(null, null);
	      return this;
	    }

	    FancyMessage result = new FancyMessage();
	    result.messageParts.clear();

	    for (int i = 0; i < lines.length; i++) {
	      try {
	        for (MessagePart component : lines[i]) {
	          if ((component.clickActionData != null) && (component.clickActionName != null))
	            throw new IllegalArgumentException("The tooltip text cannot have click data.");
	          if ((component.hoverActionData != null) && (component.hoverActionName != null)) {
	            throw new IllegalArgumentException("The tooltip text cannot have a tooltip.");
	          }
	          if (component.hasText()) {
	            result.messageParts.add(component.clone());
	          }
	        }
	        if (i != lines.length - 1)
	          result.messageParts.add(new MessagePart(TextualComponent.rawText("\n")));
	      }
	      catch (CloneNotSupportedException e) {
	        Bukkit.getLogger().log(Level.WARNING, "Failed to clone object", e);
	        return this;
	      }
	    }
	    return formattedTooltip(result.messageParts.isEmpty() ? null : result);
	  }

	  public FancyMessage formattedTooltip(Iterable<FancyMessage> lines)
	  {
	    return formattedTooltip((FancyMessage[])ArrayWrapper.toArray(lines, FancyMessage.class));
	  }

	  public FancyMessage translationReplacements(String[] replacements)
	  {
	    for (String str : replacements) {
	      latest().translationReplacements.add(new JsonString(str));
	    }
	    this.dirty = true;

	    return this;
	  }

	  public FancyMessage translationReplacements(FancyMessage[] replacements)
	  {
	    for (FancyMessage str : replacements) {
	      latest().translationReplacements.add(str);
	    }

	    this.dirty = true;

	    return this;
	  }

	  public FancyMessage translationReplacements(Iterable<FancyMessage> replacements)
	  {
	    return translationReplacements((FancyMessage[])ArrayWrapper.toArray(replacements, FancyMessage.class));
	  }

	  public FancyMessage then(String text)
	  {
	    return then(TextualComponent.rawText(text));
	  }

	  public FancyMessage then(TextualComponent text)
	  {
	    if (!latest().hasText()) {
	      throw new IllegalStateException("previous message part has no text");
	    }
	    this.messageParts.add(new MessagePart(text));
	    this.dirty = true;
	    return this;
	  }

	  public FancyMessage then()
	  {
	    if (!latest().hasText()) {
	      throw new IllegalStateException("previous message part has no text");
	    }
	    this.messageParts.add(new MessagePart());
	    this.dirty = true;
	    return this;
	  }

	  public void writeJson(JsonWriter writer) throws IOException
	  {
	    if (this.messageParts.size() == 1) {
	      latest().writeJson(writer);
	    } else {
	      writer.beginObject().name("text").value("").name("extra").beginArray();
	      for (MessagePart part : this) {
	        part.writeJson(writer);
	      }
	      writer.endArray().endObject();
	    }
	  }

	  public String toJSONString()
	  {
	    if ((!this.dirty) && (this.jsonString != null)) {
	      return this.jsonString;
	    }
	    StringWriter string = new StringWriter();
	    JsonWriter json = new JsonWriter(string);
	    try {
	      writeJson(json);
	      json.close();
	    } catch (IOException e) {
	      throw new RuntimeException("invalid message");
	    }
	    this.jsonString = string.toString();
	    this.dirty = false;
	    return this.jsonString;
	  }

	  public void send(Player player)
	  {
	    send(player, toJSONString());
	  }

	  private void send(CommandSender sender, String jsonString) {
	    if (!(sender instanceof Player)) {
	      sender.sendMessage(toOldMessageFormat());
	      return;
	    }
	    Player player = (Player)sender;
	    try {
	      Object handle = Reflection.getHandle(player);
	      Object connection = Reflection.getField(handle.getClass(), "playerConnection").get(handle);
	      Reflection.getMethod(connection.getClass(), "sendPacket", new Class[] { Reflection.getNMSClass("Packet") }).invoke(connection, new Object[] { createChatPacket(jsonString) });
	    } catch (IllegalArgumentException e) {
	      Bukkit.getLogger().log(Level.WARNING, "Argument could not be passed.", e);
	    } catch (IllegalAccessException e) {
	      Bukkit.getLogger().log(Level.WARNING, "Could not access method.", e);
	    } catch (InstantiationException e) {
	      Bukkit.getLogger().log(Level.WARNING, "Underlying class is abstract.", e);
	    } catch (InvocationTargetException e) {
	      Bukkit.getLogger().log(Level.WARNING, "A error has occured durring invoking of method.", e);
	    } catch (NoSuchMethodException e) {
	      Bukkit.getLogger().log(Level.WARNING, "Could not find method.", e);
	    } catch (ClassNotFoundException e) {
	      Bukkit.getLogger().log(Level.WARNING, "Could not find class.", e);
	    }
	  }

	  private Object createChatPacket(String json)
	    throws IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException
	  {
	    if (nmsChatSerializerGsonInstance == null)
	    {
	      String version = Reflection.getVersion();
	      String[] split = version.substring(1, version.length() - 1).split("_");

	      int minorVersion = Integer.parseInt(split[1]);
	      int revisionVersion = Integer.parseInt(split[2].substring(1));
	      Class chatSerializerClazz;
	      if ((minorVersion < 8) || ((minorVersion == 8) && (revisionVersion == 1)))
	        chatSerializerClazz = Reflection.getNMSClass("ChatSerializer");
	      else {
	        chatSerializerClazz = Reflection.getNMSClass("IChatBaseComponent$ChatSerializer");
	      }

	      if (chatSerializerClazz == null) {
	        throw new ClassNotFoundException("Can't find the ChatSerializer class");
	      }

	      for (Field declaredField : chatSerializerClazz.getDeclaredFields()) {
	        if ((Modifier.isFinal(declaredField.getModifiers())) && (Modifier.isStatic(declaredField.getModifiers())) && (declaredField.getType().getName().endsWith("Gson")))
	        {
	          declaredField.setAccessible(true);
	          nmsChatSerializerGsonInstance = declaredField.get(null);
	          fromJsonMethod = nmsChatSerializerGsonInstance.getClass().getMethod("fromJson", new Class[] { String.class, Class.class });
	          break;
	        }

	      }

	    }

	    Object serializedChatComponent = fromJsonMethod.invoke(nmsChatSerializerGsonInstance, new Object[] { json, Reflection.getNMSClass("IChatBaseComponent") });

	    return nmsPacketPlayOutChatConstructor.newInstance(new Object[] { serializedChatComponent });
	  }

	  public void send(CommandSender sender)
	  {
	    send(sender, toJSONString());
	  }

	  public void send(Iterable<? extends CommandSender> senders)
	  {
	    String string = toJSONString();
	    for (CommandSender sender : senders)
	      send(sender, string);
	  }

	  public String toOldMessageFormat()
	  {
	    StringBuilder result = new StringBuilder();
	    for (MessagePart part : this) {
	      result.append(part.color == null ? "" : part.color);
	      for (ChatColor formatSpecifier : part.styles) {
	        result.append(formatSpecifier);
	      }
	      result.append(part.text);
	    }
	    return result.toString();
	  }

	  private MessagePart latest() {
	    return (MessagePart)this.messageParts.get(this.messageParts.size() - 1);
	  }

	  private void onClick(String name, String data) {
	    MessagePart latest = latest();
	    latest.clickActionName = name;
	    latest.clickActionData = data;
	    this.dirty = true;
	  }

	  private void onHover(String name, JsonRepresentedObject data) {
	    MessagePart latest = latest();
	    latest.hoverActionName = name;
	    latest.hoverActionData = data;
	    this.dirty = true;
	  }

	  public Map<String, Object> serialize()
	  {
	    HashMap map = new HashMap();
	    map.put("messageParts", this.messageParts);

	    return map;
	  }

	  public static FancyMessage deserialize(Map<String, Object> serialized)
	  {
	    FancyMessage msg = new FancyMessage();
	    msg.messageParts = ((List)serialized.get("messageParts"));
	    msg.jsonString = (serialized.containsKey("JSON") ? serialized.get("JSON").toString() : null);
	    msg.dirty = (!serialized.containsKey("JSON"));
	    return msg;
	  }

	  public Iterator<MessagePart> iterator()
	  {
	    return this.messageParts.iterator();
	  }

	  public static FancyMessage deserialize(String json)
	  {
	    JsonObject serialized = _stringParser.parse(json).getAsJsonObject();
	    JsonArray extra = serialized.getAsJsonArray("extra");
	    FancyMessage returnVal = new FancyMessage();
	    returnVal.messageParts.clear();
	    for (JsonElement mPrt : extra) {
	      MessagePart component = new MessagePart();
	      JsonObject messagePart = mPrt.getAsJsonObject();
	      for (Map.Entry entry : messagePart.entrySet())
	      {
	        Map.Entry compositeNestedElement;
	        if (TextualComponent.isTextKey((String)entry.getKey()))
	        {
	          Map serializedMapForm = new HashMap();
	          serializedMapForm.put("key", entry.getKey());
	          if (((JsonElement)entry.getValue()).isJsonPrimitive())
	          {
	            serializedMapForm.put("value", ((JsonElement)entry.getValue()).getAsString());
	          }
	          else {
	            for (Iterator localIterator3 = ((JsonElement)entry.getValue()).getAsJsonObject().entrySet().iterator(); localIterator3.hasNext(); ) { compositeNestedElement = (Map.Entry)localIterator3.next();
	              serializedMapForm.put("value." + (String)compositeNestedElement.getKey(), ((JsonElement)compositeNestedElement.getValue()).getAsString());
	            }
	          }
	          component.text = TextualComponent.deserialize(serializedMapForm);
	        } else if (MessagePart.stylesToNames.inverse().containsKey(entry.getKey())) {
	          if (((JsonElement)entry.getValue()).getAsBoolean())
	            component.styles.add((ChatColor)MessagePart.stylesToNames.inverse().get(entry.getKey()));
	        }
	        else if (((String)entry.getKey()).equals("color")) {
	          component.color = ChatColor.valueOf(((JsonElement)entry.getValue()).getAsString().toUpperCase());
	        } else if (((String)entry.getKey()).equals("clickEvent")) {
	          JsonObject object = ((JsonElement)entry.getValue()).getAsJsonObject();
	          component.clickActionName = object.get("action").getAsString();
	          component.clickActionData = object.get("value").getAsString();
	        } else if (((String)entry.getKey()).equals("hoverEvent")) {
	          JsonObject object = ((JsonElement)entry.getValue()).getAsJsonObject();
	          component.hoverActionName = object.get("action").getAsString();
	          if (object.get("value").isJsonPrimitive())
	          {
	            component.hoverActionData = new JsonString(object.get("value").getAsString());
	          }
	          else
	          {
	            component.hoverActionData = deserialize(object.get("value").toString());
	          }
	        } else if (((String)entry.getKey()).equals("insertion")) {
	          component.insertionData = ((JsonElement)entry.getValue()).getAsString();
	        } else if (((String)entry.getKey()).equals("with")) {
	          for (JsonElement object : ((JsonElement)entry.getValue()).getAsJsonArray()) {
	            if (object.isJsonPrimitive()) {
	              component.translationReplacements.add(new JsonString(object.getAsString()));
	            }
	            else
	            {
	              component.translationReplacements.add(deserialize(object.toString()));
	            }
	          }
	        }
	      }
	      returnVal.messageParts.add(component);
	    }
	    return returnVal;
	  }
	}
	 public static final class Reflection
	  {
	    private static String _versionString;
	    private static final Map<String, Class<?>> _loadedNMSClasses = new HashMap();

	    private static final Map<String, Class<?>> _loadedOBCClasses = new HashMap();

	    private static final Map<Class<?>, Map<String, Field>> _loadedFields = new HashMap();

	    private static final Map<Class<?>, Map<String, Map<ArrayWrapper<Class<?>>, Method>>> _loadedMethods = new HashMap();

	    public static synchronized String getVersion()
	    {
	      if (_versionString == null) {
	        if (Bukkit.getServer() == null)
	        {
	          return null;
	        }
	        String name = Bukkit.getServer().getClass().getPackage().getName();
	        _versionString = name.substring(name.lastIndexOf('.') + 1) + ".";
	      }

	      return _versionString;
	    }

	    public static synchronized Class<?> getNMSClass(String className)
	    {
	      if (_loadedNMSClasses.containsKey(className)) {
	        return (Class)_loadedNMSClasses.get(className);
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

	    public static synchronized Class<?> getOBCClass(String className)
	    {
	      if (_loadedOBCClasses.containsKey(className)) {
	        return (Class)_loadedOBCClasses.get(className);
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

	    public static synchronized Object getHandle(Object obj)
	    {
	      try
	      {
	        return getMethod(obj.getClass(), "getHandle", new Class[0]).invoke(obj, new Object[0]);
	      } catch (Exception e) {
	        e.printStackTrace();
	      }return null;
	    }

	    public static synchronized Field getField(Class<?> clazz, String name)
	    {
	      Map loaded;
	      if (!_loadedFields.containsKey(clazz)) {
	        loaded = new HashMap();
	        _loadedFields.put(clazz, loaded);
	      } else {
	        loaded = (Map)_loadedFields.get(clazz);
	      }
	      if (loaded.containsKey(name))
	      {
	        return (Field)loaded.get(name);
	      }
	      try {
	        Field field = clazz.getDeclaredField(name);
	        field.setAccessible(true);
	        loaded.put(name, field);
	        return field;
	      }
	      catch (Exception e) {
	        e.printStackTrace();

	        loaded.put(name, null);
	      }return null;
	    }

	    public static synchronized Method getMethod(Class<?> clazz, String name, Class<?>[] args)
	    {
	      if (!_loadedMethods.containsKey(clazz)) {
	        _loadedMethods.put(clazz, new HashMap());
	      }

	      Map loadedMethodNames = (Map)_loadedMethods.get(clazz);
	      if (!loadedMethodNames.containsKey(name)) {
	        loadedMethodNames.put(name, new HashMap());
	      }

	      Map loadedSignatures = (Map)loadedMethodNames.get(name);
	      ArrayWrapper wrappedArg = new ArrayWrapper(args);
	      if (loadedSignatures.containsKey(wrappedArg)) {
	        return (Method)loadedSignatures.get(wrappedArg);
	      }

	      for (Method m : clazz.getMethods()) {
	        if ((m.getName().equals(name)) && (Arrays.equals(args, m.getParameterTypes()))) {
	          m.setAccessible(true);
	          loadedSignatures.put(wrappedArg, m);
	          return m;
	        }
	      }
	      loadedSignatures.put(wrappedArg, null);
	      return null;
	    }
	  }
}
