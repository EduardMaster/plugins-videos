package net.eduard.api.schematic;

import java.util.Collections;
import java.util.Map;

public final class CompoundTag extends Tag
{
  private final Map<String, Tag> value;

  public CompoundTag(String name, Map<String, Tag> value)
  {
    super(name);
    this.value = Collections.unmodifiableMap(value);
  }

  public Map<String, Tag> getValue()
  {
    return this.value;
  }

  public String toString()
  {
    String name = getName();
    String append = "";
    if ((name != null) && (!name.equals(""))) {
      append = "(\"" + getName() + "\")";
    }
    StringBuilder bldr = new StringBuilder();
    bldr.append("TAG_Compound" + append + ": " + this.value.size() + " entries\r\n{\r\n");
    for (Map.Entry<String, Tag> entry : this.value.entrySet()) {
      bldr.append("   " + ((Tag)entry.getValue()).toString().replaceAll("\r\n", "\r\n   ") + "\r\n");
    }
    bldr.append("}");
    return bldr.toString();
  }
}