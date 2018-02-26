package net.eduard.live.mobstuckers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	public static ArrayList<Player> jogadoresSendoTelados = new ArrayList<>();
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		getCommand("carne").setExecutor(new CarneComando());
		getCommand("telar").setExecutor(new TelarComando());
		getCommand("destelar").setExecutor(new DestelarComando());
	}

	@EventHandler
	public void sair(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (jogadoresSendoTelados.contains(p)) {
			
			p.setBanned(true);
			p.kickPlayer("§cVoce foi banido do servidor por sair na telagem!");
		}
	}

	@EventHandler
	public void morrer(EntityDeathEvent e) {
		if (e.getEntity() instanceof Player)
			return;

		LivingEntity morreu = e.getEntity();
		List<ItemStack> drops = e.getDrops();

		if (morreu.getType() == EntityType.SPIDER) {
			drops.clear();
			int stack = getStack(morreu);
			for (int contador = 1; contador <= stack; contador++) {
				drops.add(new ItemStack(Material.STRING));
			}
			e.setDroppedExp(5 + new Random().nextInt(10));
			e.setDroppedExp(e.getDroppedExp() * stack);
			morreu.getKiller().sendMessage("§aStack da aranha " + stack + " dropou xp " + e.getDroppedExp());

		}

	}

	@EventHandler
	public void nascer(EntitySpawnEvent e) {
		if (e.getEntity() instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) e.getEntity();
			double range = 9D;
			for (Entity entidadePerto : livingEntity.getNearbyEntities(range, range, range)) {
				if (entidadePerto instanceof LivingEntity) {
					LivingEntity livingEntity2 = (LivingEntity) entidadePerto;

					if (livingEntity2.getType() == livingEntity.getType()) {
						e.setCancelled(true);
						addStack(livingEntity2, 1);
						update(livingEntity2);

						return;
					}

				}
			}
			addStack(livingEntity, 1);
			update(livingEntity);

		}
	}

	@SuppressWarnings("deprecation")
	public static void update(LivingEntity entidade) {
		int stack = getStack(entidade);
		entidade.setCustomNameVisible(true);
		if (entidade.getType() == EntityType.SPIDER) {
			entidade.setMaxHealth(5 * stack);
			entidade.setHealth(entidade.getMaxHealth());

		}
		entidade.setCustomName(
				"§a" + entidade.getType().getName() + " §2x§f" + stack + " §c❤" + (int) entidade.getHealth());

	}

	public static void setStack(LivingEntity entity, int stack) {
		stacks.put(entity, stack);
	}

	public static void addStack(LivingEntity entity, int stack) {
		setStack(entity, getStack(entity) + stack);
	}

	public static void removeStack(LivingEntity entity, int stack) {
		setStack(entity, getStack(entity) - stack);
	}

	public static int getStack(LivingEntity entity) {
		return stacks.getOrDefault(entity, 0);
	}

	public static Map<Entity, Integer> stacks = new HashMap<>();
}
