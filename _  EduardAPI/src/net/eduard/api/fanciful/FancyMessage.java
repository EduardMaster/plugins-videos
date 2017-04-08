package net.eduard.api.fanciful;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

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

import net.minecraft.util.com.google.gson.JsonArray;
import net.minecraft.util.com.google.gson.JsonElement;
import net.minecraft.util.com.google.gson.JsonObject;
import net.minecraft.util.com.google.gson.JsonParser;
import net.minecraft.util.com.google.gson.stream.JsonWriter;

public class FancyMessage  implements JsonRepresentedObject, Cloneable, Iterable<MessagePart>, ConfigurationSerializable
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