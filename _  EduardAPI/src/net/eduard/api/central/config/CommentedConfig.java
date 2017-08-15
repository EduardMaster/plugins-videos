package net.eduard.api.central.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConstructor;
import org.bukkit.configuration.file.YamlRepresenter;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

public class CommentedConfig extends YamlConfiguration
{
  private HashMap<String, String> comments;
  private File file;
  private final DumperOptions yamlOptions = new DumperOptions();
  private final Representer yamlRepresenter = new YamlRepresenter();
  private final Yaml yaml = new Yaml(new YamlConstructor(), this.yamlRepresenter, this.yamlOptions);

  public CommentedConfig(File file)
  {
    this.comments = new HashMap<String, String>();
    this.file = file;
  }

  public String saveToString()
  {
    this.yamlOptions.setIndent(options().indent());
    this.yamlOptions.setDefaultScalarStyle(DumperOptions.ScalarStyle.LITERAL);
    this.yamlOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
    this.yamlRepresenter.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

    String header = buildHeader();
    String dump = this.yaml.dump(getValues(false));

    if (dump.equals("{}\n")) {
      dump = "";
    }

    return header + dump;
  }

  public boolean load() {
    boolean loaded = true;
    try {
      load(this.file);
    } catch (IOException|InvalidConfigurationException e) {
      loaded = false;
      System.out.println("Exception while loading the file: " + e.getMessage());
      e.printStackTrace();
    }
    return loaded;
  }

  public boolean save() {
    boolean saved = true;
    try
    {
      super.save(this.file);
    } catch (IOException e) {
      saved = false;
      System.out.println("Failed to save the file: " + e.getMessage());
      e.printStackTrace();
    }

    if ((!this.comments.isEmpty()) && (saved))
    {
      String[] yamlContents = convertFileToString(this.file).split("[" + System.getProperty("line.separator") + "]");

      String newContents = "";

      String currentPath = "";

      boolean commentedPath = false;

      int depth = 0;

      for (String line : yamlContents)
      {
        boolean node;
        if ((line.contains(": ")) || ((line.length() > 1) && (line.charAt(line.length() - 1) == ':')))
        {
          commentedPath = false;

           node = true;

          int index = line.indexOf(": ");
          if (index < 0) {
            index = line.length() - 1;
          }

          if (currentPath.isEmpty()) {
            currentPath = line.substring(0, index);
          }
          else {
            int whiteSpace = 0;
            for (int n = 0; (n < line.length()) && 
              (line.charAt(n) == ' '); n++)
            {
              whiteSpace++;
            }

            if (whiteSpace / 2 > depth)
            {
              currentPath = currentPath + "." + line.substring(whiteSpace, index);
              depth++;
            } else if (whiteSpace / 2 < depth)
            {
              int newDepth = whiteSpace / 2;
              for (int i = 0; i < depth - newDepth; i++) {
                currentPath = currentPath.replace(currentPath.substring(currentPath.lastIndexOf('.')), "");
              }

              int lastIndex = currentPath.lastIndexOf('.');
              if (lastIndex < 0)
              {
                currentPath = "";
              }
              else
              {
                currentPath = currentPath.replace(currentPath.substring(currentPath.lastIndexOf('.')), "");
                currentPath = currentPath + ".";
              }

              currentPath = currentPath + line.substring(whiteSpace, index);

              depth = newDepth;
            }
            else
            {
              int lastIndex = currentPath.lastIndexOf('.');
              if (lastIndex < 0)
              {
                currentPath = "";
              }
              else
              {
                currentPath = currentPath.replace(currentPath.substring(currentPath.lastIndexOf('.')), "");
                currentPath = currentPath + ".";
              }

              currentPath = currentPath + line.substring(whiteSpace, index);
            }
          }
        }
        else
        {
          node = false;
        }
        if (node) {
          String comment = null;
          if (!commentedPath)
          {
            comment = (String)this.comments.get(currentPath);
          }
          if (comment != null)
          {
            line = comment + System.getProperty("line.separator") + line + System.getProperty("line.separator");
            commentedPath = true;
          }
          else {
            line = line + System.getProperty("line.separator");
          }
        }

        newContents = newContents + line + (!node ? System.getProperty("line.separator") : "");
      }

      while (newContents.startsWith(System.getProperty("line.separator"))) {
        newContents = newContents.replaceFirst(System.getProperty("line.separator"), "");
      }

      if (!stringToFile(newContents, this.file)) {
        saved = false;
      }
    }
    return saved;
  }

  public void addComment(String path, String[] commentLines)
  {
    StringBuilder commentstring = new StringBuilder();
    String leadingSpaces = "";
    for (int n = 0; n < path.length(); n++) {
      if (path.charAt(n) == '.') {
        leadingSpaces = leadingSpaces + "  ";
      }
    }
    for (String line : commentLines) {
      if (!line.isEmpty())
        line = leadingSpaces + line;
      else {
        line = " ";
      }
      if (commentstring.length() > 0) {
        commentstring.append("\r\n");
      }
      commentstring.append(line);
    }
    this.comments.put(path, commentstring.toString());
  }

  @SuppressWarnings({"resource", "unused"})
public String convertFileToString(File file)
  {
    if ((file != null) && (file.exists()) && (file.canRead()) && (!file.isDirectory())) {
      char[] buffer = new char[1024];
      String s = "";
      try { Writer writer = new StringWriter(); Throwable localThrowable6 = null;
        try { Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

          Throwable localThrowable7 = null;
          try
          {
            int n;
            while ((n = reader.read(buffer)) != -1) {
              writer.write(buffer, 0, n);
            }
            s = writer.toString();
          }
          catch (Throwable localThrowable1)
          {
            localThrowable7 = localThrowable1; throw localThrowable1; } finally {  } } catch (Throwable localThrowable4) { localThrowable6 = localThrowable4; throw localThrowable4;
        }
        finally
        {
          if (writer != null) if (localThrowable6 != null) try { writer.close(); } catch (Throwable localThrowable5) { localThrowable6.addSuppressed(localThrowable5); } else writer.close();  
        } } catch (IOException e) { System.out.println("Failed to convert the file to a string: " + e.getMessage());
        e.printStackTrace();
      }
      return s;
    }
    return "";
  }

  public boolean stringToFile(String source, File file)
  {
    try
    {
      OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
      Throwable localThrowable3 = null;
      try { out.write(source);
        return true;
      }
      catch (Throwable localThrowable4)
      {
        localThrowable3 = localThrowable4; 
        throw localThrowable4;
      }
      finally {
        if (out != null) if (localThrowable3 != null) try { out.close(); } catch (Throwable localThrowable2) { localThrowable3.addSuppressed(localThrowable2); } else out.close();  
      } } catch (IOException e) { System.out.println("Failed to convert the string to a file: " + e.getMessage());
      e.printStackTrace(); }
    return false;
  }
}