
package net.eduard.api.tutorial;

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

import net.eduard.api.manager.Manager;
import net.eduard.api.util.Cs;

public class EventosEntidade extends Manager {

	@EventHandler
	public void EntidadeAtiraComFlecha(EntityShootBowEvent e) {
		Cs.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeBlocoFormar(EntityBlockFormEvent e) {
		Cs.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeCarneiroTrocaCor(SheepDyeWoolEvent e) {
		Cs.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeCarneiroTrocaCor(SheepRegrowWoolEvent e) {
		Cs.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeCavaloPulo(HorseJumpEvent e) {
		Cs.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeCreaturaSpawn(CreatureSpawnEvent e) {
		Cs.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeCreeperPower(CreeperPowerEvent e) {
		Cs.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeCriarPortal(EntityCreatePortalEvent e) {
		Cs.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeDano(EntityDamageEvent e) {
		Cs.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeDanoPorBloco(EntityDamageByBlockEvent e) {
		Cs.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeDanoPorEntidade(EntityDamageByEntityEvent e) {
		Cs.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeDesprender(EntityUnleashEvent e) {
		Cs.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeExplode(EntityExplodeEvent e) {
		Cs.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeExplosionPrime(ExplosionPrimeEvent e) {
		Cs.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeFoodChange(FoodLevelChangeEvent e) {
		Cs.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeInteragir(EntityInteractEvent e) {
		Cs.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeItemSome(ItemDespawnEvent e) {
		Cs.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeItemSpawn(ItemSpawnEvent e) {
		Cs.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeMira(EntityTargetEvent e) {
		Cs.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeMiraUnidadeViva(EntityTargetLivingEntityEvent e) {
		Cs.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeMorrer(EntityDeathEvent e) {
		Cs.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeMudarBloco(EntityChangeBlockEvent e) {
		Cs.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadePegandoFogoPorBloco(EntityCombustByBlockEvent e) {
		Cs.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadePegandoFogoPorEntidade(EntityCombustByEntityEvent e) {
		Cs.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadePegarFogo(EntityCombustEvent e) {
		Cs.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadePortal(EntityPortalEvent e) {
		Cs.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadePortalEntrar(EntityPortalEnterEvent e) {
		Cs.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadePortalSair(EntityPortalExitEvent e) {
		Cs.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeProjectileAcerta(ProjectileHitEvent e) {
		Cs.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeProjectileLancar(ProjectileLaunchEvent e) {
		Cs.broadcast(e.getEventName());
	}

	// Entidade
	@EventHandler
	public void EntidadeQuebraPorta(EntityBreakDoorEvent e) {
		Cs.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeRecuperaVida(EntityRegainHealthEvent e) {
		Cs.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeSlimeMutiplica(SlimeSplitEvent e) {
		Cs.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeTame(EntityTameEvent e) {
		Cs.broadcast(e.getEventName());
	}

	@EventHandler
	public void EntidadeTeleportar(EntityTeleportEvent e) {
		Cs.broadcast(e.getEventName());
	}
}
