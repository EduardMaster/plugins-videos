
package net.eduard.api.tutorial;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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

public class EventosEntidade implements Listener {

	@EventHandler
	public void EntidadeAtiraComFlecha(EntityShootBowEvent e) {

	}

	@EventHandler
	public void EntidadeBlocoFormar(EntityBlockFormEvent e) {

	}

	@EventHandler
	public void EntidadeCarneiroTrocaCor(SheepDyeWoolEvent e) {

	}

	@EventHandler
	public void EntidadeCarneiroTrocaCor(SheepRegrowWoolEvent e) {

	}

	@EventHandler
	public void EntidadeCavaloPulo(HorseJumpEvent e) {

	}

	@EventHandler
	public void EntidadeCreaturaSpawn(CreatureSpawnEvent e) {

	}

	@EventHandler
	public void EntidadeCreeperPower(CreeperPowerEvent e) {

	}

	@EventHandler
	public void EntidadeCriarPortal(EntityCreatePortalEvent e) {

	}

	@EventHandler
	public void EntidadeDano(EntityDamageEvent e) {

	}

	@EventHandler
	public void EntidadeDanoPorBloco(EntityDamageByBlockEvent e) {

	}

	@EventHandler
	public void EntidadeDanoPorEntidade(EntityDamageByEntityEvent e) {

	}

	@EventHandler
	public void EntidadeDesprender(EntityUnleashEvent e) {

	}

	@EventHandler
	public void EntidadeExplode(EntityExplodeEvent e) {

	}

	@EventHandler
	public void EntidadeExplosionPrime(ExplosionPrimeEvent e) {

	}

	@EventHandler
	public void EntidadeFoodChange(FoodLevelChangeEvent e) {

	}

	@EventHandler
	public void EntidadeInteragir(EntityInteractEvent e) {

	}

	@EventHandler
	public void EntidadeItemSome(ItemDespawnEvent e) {

	}

	@EventHandler
	public void EntidadeItemSpawn(ItemSpawnEvent e) {

	}

	@EventHandler
	public void EntidadeMira(EntityTargetEvent e) {

	}

	@EventHandler
	public void EntidadeMiraUnidadeViva(EntityTargetLivingEntityEvent e) {

	}

	@EventHandler
	public void EntidadeMorrer(EntityDeathEvent e) {

	}

	@EventHandler
	public void EntidadeMudarBloco(EntityChangeBlockEvent e) {

	}

	@EventHandler
	public void EntidadePegandoFogoPorBloco(EntityCombustByBlockEvent e) {

	}

	@EventHandler
	public void EntidadePegandoFogoPorEntidade(EntityCombustByEntityEvent e) {

	}

	@EventHandler
	public void EntidadePegarFogo(EntityCombustEvent e) {

	}

	@EventHandler
	public void EntidadePortal(EntityPortalEvent e) {

	}

	@EventHandler
	public void EntidadePortalEntrar(EntityPortalEnterEvent e) {

	}

	@EventHandler
	public void EntidadePortalSair(EntityPortalExitEvent e) {

	}

	@EventHandler
	public void EntidadeProjectileAcerta(ProjectileHitEvent e) {

	}

	@EventHandler
	public void EntidadeProjectileLancar(ProjectileLaunchEvent e) {

	}

	// Entidade
	@EventHandler
	public void EntidadeQuebraPorta(EntityBreakDoorEvent e) {

	}

	@EventHandler
	public void EntidadeRecuperaVida(EntityRegainHealthEvent e) {

	}

	@EventHandler
	public void EntidadeSlimeMutiplica(SlimeSplitEvent e) {

	}

	@EventHandler
	public void EntidadeTame(EntityTameEvent e) {

	}

	@EventHandler
	public void EntidadeTeleportar(EntityTeleportEvent e) {

	}
}
