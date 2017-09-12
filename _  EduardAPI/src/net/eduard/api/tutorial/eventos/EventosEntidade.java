
package net.eduard.api.tutorial.eventos;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreeperPowerEvent;
import org.bukkit.event.entity.EntityBreakDoorEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.entity.EntityPortalExitEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.entity.EntityUnleashEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.entity.SheepDyeWoolEvent;
import org.bukkit.event.entity.SheepRegrowWoolEvent;
import org.bukkit.event.entity.SlimeSplitEvent;

import net.eduard.api.API;
import net.eduard.api.manager.TimeManager;

public class EventosEntidade extends TimeManager {

	@EventHandler
	public void EntidadeAtiraComFlecha(EntityShootBowEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeBlocoFormar(EntityBlockFormEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeCarneiroTrocaCor(SheepDyeWoolEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeCarneiroTrocaCor(SheepRegrowWoolEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeCavaloPulo(HorseJumpEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeCreaturaSpawn(CreatureSpawnEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeCreeperPower(CreeperPowerEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeCriarPortal(EntityCreatePortalEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeDano(EntityDamageEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeDanoPorBloco(EntityDamageByBlockEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeDanoPorEntidade(EntityDamageByEntityEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeDesprender(EntityUnleashEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeExplode(EntityExplodeEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeExplosionPrime(ExplosionPrimeEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeFoodChange(FoodLevelChangeEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeInteragir(EntityInteractEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeItemSome(ItemDespawnEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeItemSpawn(ItemSpawnEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeMira(EntityTargetEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeMiraUnidadeViva(EntityTargetLivingEntityEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeMorrer(EntityDeathEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeMudarBloco(EntityChangeBlockEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadePegandoFogoPorBloco(EntityCombustByBlockEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadePegandoFogoPorEntidade(EntityCombustByEntityEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadePegarFogo(EntityCombustEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadePortal(EntityPortalEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadePortalEntrar(EntityPortalEnterEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadePortalSair(EntityPortalExitEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeProjectileAcerta(ProjectileHitEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeProjectileLancar(ProjectileLaunchEvent e) {
		API.broadcast(e.getEventName());
	}

	// Entidade
	@EventHandler
	public void EntidadeQuebraPorta(EntityBreakDoorEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeRecuperaVida(EntityRegainHealthEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeSlimeMutiplica(SlimeSplitEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeTame(EntityTameEvent e) {
		API.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeTeleportar(EntityTeleportEvent e) {
		API.broadcast(e.getEventName());
	}
}
