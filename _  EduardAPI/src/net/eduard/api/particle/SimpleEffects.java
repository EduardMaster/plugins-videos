package net.eduard.api.particle;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class SimpleEffects
{
  public static double cos(double i)
  {
    return Math.cos(i);
  }

  public static double sin(double i) {
    return Math.sin(i);
  }
  public static Plugin getPlugin(){
	  return JavaPlugin.getProvidingPlugin(SimpleEffects.class);
  }

  public static void coneEffect(Location loc) {
    new BukkitRunnable()
    {
      double phi = 0.0D;

      public void run() { this.phi += 0.3926990816987241D;

        for (double t = 0.0D; t <= 6.283185307179586D; t += 0.1963495408493621D) {
          for (double i = 0.0D; i <= 1.0D; i += 1.0D) {
            double x = 0.4D * (6.283185307179586D - t) * 0.5D * SimpleEffects.cos(t + this.phi + i * 3.141592653589793D);
            double y = 0.5D * t;
            double z = 0.4D * (6.283185307179586D - t) * 0.5D * SimpleEffects.sin(t + this.phi + i * 3.141592653589793D);
            loc.add(x, y, z);
            ParticleEffect.HEART.display(loc, 0.0F, 0.0F, 0.0F, 0.0F, 1);
            loc.subtract(x, y, z);
          }
        }

        if (this.phi > 31.415926535897931D)
          cancel();
      }
    }
    .runTaskTimer(getPlugin(), 0L, 3L);
  }

  public static void agualaEffect(Location loc) {
    new BukkitRunnable() {
      double phi = 0.0D;

      public void run() { this.phi += 0.3141592653589793D;
        for (double t = 0.0D; t <= 15.707963267948966D; t += 0.07853981633974483D) {
          double r = 1.2D;
          double x = r * SimpleEffects.cos(t) * SimpleEffects.sin(this.phi);
          double y = r * SimpleEffects.cos(this.phi) + 1.2D;
          double z = r * SimpleEffects.sin(t) * SimpleEffects.sin(this.phi);
          loc.add(x, y, z);
          ParticleEffect.DRIP_WATER.display(loc, 0.0F, 0.0F, 0.0F, 0.0F, 1);

          loc.subtract(x, y, z);
        }

        if (this.phi > 3.141592653589793D)
          cancel();
      }
    }
    .runTaskTimer(getPlugin(), 0L, 1L);
  }

  public static void fireBenderEffect(Location loc) {
    new BukkitRunnable() {
      double phi = 0.0D;

      public void run() { this.phi += 0.3141592653589793D;
        for (double t = 0.0D; t <= 15.707963267948966D; t += 0.07853981633974483D) {
          double r = 1.2D;
          double x = r * SimpleEffects.cos(t) * SimpleEffects.sin(this.phi);
          double y = r * SimpleEffects.cos(this.phi) + 1.2D;
          double z = r * SimpleEffects.sin(t) * SimpleEffects.sin(this.phi);
          loc.add(x, y, z);
          ParticleEffect.FLAME.display(loc, 0.0F, 0.0F, 0.0F, 0.0F, 1);

          loc.subtract(x, y, z);
        }

        if (this.phi > 3.141592653589793D)
          cancel();
      }
    }
    .runTaskTimer(getPlugin(), 0L, 1L);
  }
}