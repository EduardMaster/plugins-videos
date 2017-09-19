package net.eduard.api.setup;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.setup.ExtraAPI.EventsManager;
import net.eduard.api.setup.StorageAPI.Storable;

public final class PlayerAPI {
	public static class PlayerClickEntity extends PlayerInteract {

		private PlayerClickEntityEffect clickEntityEffect;

		public PlayerClickEntity(ItemStack item) {
			super(item, true, true, false);
		}
		public PlayerClickEntity() {
			this(null);
		}
		public PlayerClickEntity(ItemStack item,
				PlayerClickEntityEffect clickEntityEffect) {
			this(item);
			setClickEntityEffect(clickEntityEffect);
		}

		public PlayerClickEntity(Material material,
				PlayerClickEntityEffect clickEntityEffect) {
			this(new ItemStack(material),clickEntityEffect);
			setItemComparationType(ItemComparationType.BY_TYPE);
		}

		@EventHandler
		public void onClickAtEntity(PlayerInteractEntityEvent event) {
			Player player = event.getPlayer();
			if (!isInteractWithAnyItem()) {
				if (!getItemComparationType().compare(getItem(),
						event.getPlayer().getItemInHand()))
					return;
			}
			clickEntityEffect.onClickAtEntity(player, event.getRightClicked(),
					getItem());

		}

		public PlayerClickEntityEffect getClickEntityEffect() {
			return clickEntityEffect;
		}

		public void setClickEntityEffect(
				PlayerClickEntityEffect clickEntityEffect) {
			this.clickEntityEffect = clickEntityEffect;
		}

		@Override
		public Object restore(Map<String, Object> map) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void store(Map<String, Object> map, Object object) {
			// TODO Auto-generated method stub

		}

	}

	public static interface PlayerClickEffect {
		
		public abstract void onClick(Player player,Block block,ItemStack item);

	}
	public static class PlayerClick extends PlayerInteract  {

		private PlayerClickEffect clickEffect;

		private ClickComparationType comparationType =ClickComparationType.WITH_RIGHT;

		private boolean compareClick = true;
		
		public PlayerClick() {
			this(null);
		}
		public PlayerClick(ItemStack item) {
			super(item, true, false, true);
		}
		public PlayerClick(Material material, PlayerClickEffect clickEffect) {
			this(new ItemStack(material),clickEffect);
			setItemComparationType(ItemComparationType.BY_TYPE);
		}
		public PlayerClick(ItemStack item, PlayerClickEffect clickEffect) {
			this(item);
			setClickEffect(clickEffect);
		}
		@EventHandler
		public void onClick(PlayerInteractEvent event) {
			if (event.getAction() == Action.PHYSICAL)
				return;
			if (!isInteractWithAnyItem()) {
				if (!getItemComparationType().compare(getItem(), event.getItem()))
					return;
			}
			if (compareClick)
				if (!comparationType.compare(event.getAction().name()))
					return;
			event.setCancelled(true);
			clickEffect.onClick(event.getPlayer(), event.getClickedBlock(),
					getItem());
		}
		public PlayerClickEffect getClickEffect() {
			return clickEffect;
		}
		public void setClickEffect(PlayerClickEffect clickEffect) {
			this.clickEffect = clickEffect;
		}
		public ClickComparationType getComparationType() {
			return comparationType;
		}
		public void setComparationType(ClickComparationType comparationType) {
			this.comparationType = comparationType;
		}
		@Override
		public Object restore(Map<String, Object> map) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public void store(Map<String, Object> map, Object object) {
			// TODO Auto-generated method stub
			
		}

	}

	public static interface PlayerClickEntityEffect {
		
		public abstract void onClickAtEntity(Player player,Entity entity,ItemStack item);

	}

	public static interface PlayerEffect{
		
		public void effect(Player player);
	}
	public static enum ClickComparationType {
		ON_BLOCK, WITH_LEFT_ON_AIR, WITH_RIGHT_ON_AIR, ON_AIR, WITH_RIGHT, WITH_LEFT, WITH_RIGHT_ON_BLOCK, WITH_LEFT_ON_BLOCK;
		
		public boolean compare(String action) {
			switch (this) {
			
				case ON_AIR :
					return action.contains("AIR");
				case ON_BLOCK :
					return action.contains("BLOCK");
				case WITH_LEFT :
					return action.contains("LEFT");
				case WITH_RIGHT :
					return action.contains("RIGHT");
				case WITH_LEFT_ON_AIR :
					return action.equals("LEFT_CLICK_AIR");
				case WITH_LEFT_ON_BLOCK :
					return action.equals("LEFT_CLICK_BLOCK");
				case WITH_RIGHT_ON_AIR :
					return action.equals("RIGHT_CLICK_AIR");
				case WITH_RIGHT_ON_BLOCK :
					return action.equals("RIGHT_CLICK_BLOCK");

			}
			return false;
		}

	}
	public static enum ItemComparationType {

		BY_NAME, BY_TYPE_NAME, BY_TYPE, BY_SIMILIARITY, BY_EQUALITY;

		public boolean compare(ItemStack item, ItemStack other) {
			if (item == null)
				return false;
			if (other == null)
				return false;
			switch (this) {
				case BY_NAME :
					return item.getItemMeta().getDisplayName()
							.equals(other.getItemMeta().getDisplayName());
				case BY_SIMILIARITY :
					return item.isSimilar(other);
				case BY_EQUALITY :
					return item.equals(other);
				case BY_TYPE :
					return item.getType() == other.getType();
				case BY_TYPE_NAME :
					return item.getType().name().toLowerCase()
							.contains(other.getType().name().toLowerCase());
			}

			return false;
		}
	}

	public static abstract class PlayerInteract extends EventsManager implements  Storable{
		
		private ItemStack item;
		
		private ItemComparationType itemComparationType = ItemComparationType.BY_SIMILIARITY;
		
		private transient boolean interactWithAnyItem;
		
		private transient boolean interactWithItem;
		
		private transient boolean interactWithEntity;
		
		private transient boolean interactWithBlock;

		protected PlayerInteract(ItemStack item, boolean interactWithItem,
				boolean interactWithEntity, boolean interactWithBlock) {
			super();
			this.item = item;
			this.interactWithItem = interactWithItem;
			this.interactWithEntity = interactWithEntity;
			this.interactWithBlock = interactWithBlock;
		}

		public ItemStack getItem() {
			return item;
		}

		public void setItem(ItemStack item) {
			this.item = item;
		}

		protected boolean isInteractWithItem() {
			return interactWithItem;
		}

		protected void setInteractWithItem(boolean interactWithItem) {
			this.interactWithItem = interactWithItem;
		}

		protected boolean isInteractWithEntity() {
			return interactWithEntity;
		}

		protected void setInteractWithEntity(boolean interactWithEntity) {
			this.interactWithEntity = interactWithEntity;
		}

		protected boolean isInteractWithBlock() {
			return interactWithBlock;
		}

		protected void setInteractWithBlock(boolean interactWithBlock) {
			this.interactWithBlock = interactWithBlock;
		}

		public ItemComparationType getItemComparationType() {
			return itemComparationType;
		}

		public void setItemComparationType(ItemComparationType itemComparationType) {
			this.itemComparationType = itemComparationType;
		}

		protected boolean isInteractWithAnyItem() {
			return interactWithAnyItem;
		}

		protected void setInteractWithAnyItem(boolean interactWithAnyItem) {
			this.interactWithAnyItem = interactWithAnyItem;
		}


	}

}
