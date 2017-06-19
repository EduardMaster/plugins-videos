package net.eduard.api.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.API;
import net.eduard.api.config.Save;
import net.eduard.api.config.Section;
import net.eduard.api.manager.GameAPI;
import net.eduard.api.manager.ItemAPI;
import net.eduard.api.manager.Manager;
import net.eduard.api.manager.RexAPI;
import net.eduard.api.manager.VaultAPI;
import net.eduard.api.util.Cs;
import net.eduard.api.util.PlayerEffect;

public class KitManager extends Manager implements Save {
	public static final Kit KIT_NONE = new Kit("Nenhum",KitType.NONE);
	private String prePerm = "kits.";
	private Click kitSelector;
	private Click kitShop;
	private String noneKit = "§8Nenhum";
	private List<KitType> types=new ArrayList<>();
	private boolean kitsEnabled = true;
	private double defaultKitPrice = 0;
	private String kitsDisabled = "§cOs kits foram desabilitados!";
	private Slot confirmShop = new Slot(
			API.newItem(Material.EMERALD, "§bConfirmar Compra"), 0);
	private Slot cancelShop = new Slot(API.newItem(Material.BED, "§bAvançar"),
			8);;
	private ItemStack soup = API.newItem("§6Sopa", Material.MUSHROOM_SOUP);
	private int pagesAmount = 3;
	private Slot nextPageItem = new Slot(
			API.newItem(Material.BEACON, "§bAvançar"), 8);;
	private ItemStack emptySlotItem = API.newItem(" ",
			Material.STAINED_GLASS_PANE, 15);
	private ItemStack hotBarItem = API.newItem("§6§lKit§f§lPvP",
			Material.STAINED_GLASS_PANE, 10);
	private Slot previousPageItem = new Slot(
			API.newItem(Material.BED, "§bVoltar"), 0);;
	private String pageTag = "§8Página §0$page";
	private String selectorTitle = "§5§lSeus Kits: ";
	private String shopTitle = "§1§lCompre Kits: ";
	private String kitSelected = "§6Voce escolheu o Kit §e$kit";
	private String kitGived = "§6Voce ganhou o Kit §e$kit";
	private String kitBuyed = "§§Voce comprou o Kit §e$kit";
	private String noKitBuyed = "§§Voce nao tem dinheiro para comprar o kit §e$kit";
	private String guiShopTitle = "§cKit §4§l$kit §cseu preço: §a§l$price";
	private boolean giveSoups, fillHotBar = true, onSelectGainKit = true;
	private transient Map<Player, Gui[]> guisKitSelectors = new HashMap<>();
	private transient Map<Player, Gui[]> guisKitShops = new HashMap<>();
	private List<Integer> blackLists = new ArrayList<>();
	private List<ItemStack> globalItems = new ArrayList<>();
	private transient Map<Player, Kit> buying = new HashMap<>();
	private transient Map<Player, Kit> players = new HashMap<>();
	private transient Map<String, Kit> kits = new HashMap<>();

	public Map<String, Kit> getKits() {
		return kits;
	}

	public String getPrePerm() {
		return prePerm;
	}

	public void setPrePerm(String prePerm) {
		this.prePerm = prePerm;
	}

	public boolean isFillHotBar() {
		return fillHotBar;
	}

	public void setFillHotBar(boolean fillHotBar) {
		this.fillHotBar = fillHotBar;
	}

	public boolean isOnSelectGainKit() {
		return onSelectGainKit;
	}

	public void setOnSelectGainKit(boolean onSelectGainKit) {
		this.onSelectGainKit = onSelectGainKit;
	}

	public KitManager() {
		kitSelector = (Click) new Click(
				API.newItem(Material.COMPASS, "§4§lKitSelector"),
				new ClickEffect() {

					@Override
					public void effect(PlayerInteractEntityEvent e) {

					}

					@Override
					public void effect(PlayerInteractEvent e) {
						Player p = e.getPlayer();
						if (guisKitSelectors.containsKey(p)) {
							openKitSelector(p);
						}
					}
				}).setSlot(4);
		kitShop = (Click) new Click(
				API.newItem(Material.EMERALD, "§b§lKitShop"),
				new ClickEffect() {

					@Override
					public void effect(PlayerInteractEntityEvent e) {

					}

					@Override
					public void effect(PlayerInteractEvent e) {
						Player p = e.getPlayer();
						if (guisKitShops.containsKey(p)) {
							openKitShop(p);
						}
					}
				}).setSlot(2);

		for (int i = 0; i < 6 * 9; i++) {
			if (i < 8) {
				blackLists.add(i);
			} else if (i > 5 * 9 - 1) {
				blackLists.add(i);
			} else if ((i + 1) % 9 == 0) {
				blackLists.add(i);
				blackLists.add(i + 1);
			}
		}
		globalItems.add(new ItemStack(Material.STONE_SWORD));
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void event(InventoryClickEvent e) {
		if (e.getCurrentItem() == null)
			return;
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (buying.containsKey(p)) {
				e.setCancelled(true);
				if (e.getCurrentItem().equals(confirmShop.getItem())) {
					Kit kit = buying.get(p);
					if (VaultAPI.hasVault()) {
						if (VaultAPI.getEconomy().has(p.getName(),
								kit.getPrice())) {
							VaultAPI.getEconomy().withdrawPlayer(p.getName(),
									kit.getPrice());
							VaultAPI.getPermission().playerAdd(p,
									prePerm + kit.getName().toLowerCase());
							p.sendMessage(kitBuyed
									.replace("$kit", kit.getName())
									.replace("$price", "" + kit.getPrice()));
							generateGuis(p);
						} else {
							p.sendMessage(noKitBuyed
									.replace("$kit", kit.getName())
									.replace("$price", "" + kit.getPrice()));
						}
					}
					p.closeInventory();
				} else if (e.getCurrentItem().equals(cancelShop.getItem())) {
					p.closeInventory();
				}

			}

		}
	}

	@EventHandler
	public void event(InventoryCloseEvent e) {
		if (buying.containsKey(e.getPlayer())) {
			buying.remove(e.getPlayer());
		}
	}

	public void gainKit(Player player) {
		if (!isKitsEnabled())return;
		Kit kit = players.get(player);
		removeKits(player);
		GameAPI.refreshAll(player);
		PlayerInventory inv = player.getInventory();
		for (ItemStack item : kit.getItems()) {
			inv.addItem(item);
		}
		for (String sub : kit.getKits()) {
			Kit subKit = kits.get(sub);
			for (ItemStack item : subKit.getItems()) {
				inv.addItem(item);
			}
			subKit.add(player);
		}
		kit.add(player);
		for (ItemStack item : globalItems) {
			inv.addItem(item);;
		}
		if (giveSoups) {
			ItemAPI.fill(inv, soup);
			ItemAPI.setEquip(player, Color.GREEN, "§4§lINSANE");
		}
		player.sendMessage(kitGived.replace("$kit", kit.getName()));
	}

	public void generateGuis(Player player) {
		if (guisKitSelectors.containsKey(player)) {
			for (Gui gui : guisKitSelectors.get(player)) {
				gui.unregister();
			}
			guisKitSelectors.remove(player);
		}
		if (guisKitShops.containsKey(player)) {
			for (Gui gui : guisKitShops.get(player)) {
				gui.unregister();
			}
			guisKitShops.remove(player);
		}
		guisKitSelectors.put(player, new Gui[pagesAmount]);
		guisKitShops.put(player, new Gui[pagesAmount]);
		for (int i = 0; i < pagesAmount; i++) {
			Gui inv = new Gui(6,
					selectorTitle + pageTag.replace("$page", "" + (i + 1)));
			Gui shop = new Gui(6,
					shopTitle + pageTag.replace("$page", "" + (i + 1)));
			if (i > 0) {
				inv.setOpen(false);
				shop.setOpen(false);
			}
			inv.register(getPlugin());
			shop.register(getPlugin());
			for (Integer id : blackLists) {
				Slot emptySlot = new Slot(emptySlotItem, id);
				inv.set(emptySlot);
				shop.set(emptySlot);
			}
			final int x = i;
			inv.set((Slot) nextPageItem.clone().setEffect(new PlayerEffect() {

				@Override
				public void effect(Player p) {
					openKitSelector(p, getPage(x + 1));
				}
			}));
			inv.set((Slot) previousPageItem.clone()
					.setEffect(new PlayerEffect() {

						@Override
						public void effect(Player p) {
							openKitSelector(p, getPage(x - 1));
						}
					}));
			shop.set((Slot) nextPageItem.clone().setEffect(new PlayerEffect() {

				@Override
				public void effect(Player p) {
					openKitShop(p, getPage(x + 1));
				}
			}));
			shop.set((Slot) previousPageItem.clone()
					.setEffect(new PlayerEffect() {

						@Override
						public void effect(Player p) {
							openKitShop(p, getPage(x - 1));
						}
					}));
			guisKitSelectors.get(player)[i] = inv;
			guisKitShops.get(player)[i] = shop;
		}
		int idKit = 0;
		int idShop = 0;
		int pageKit = 0;
		int pageShop = 0;
		for (Kit kit : kits.values()) {
			while (blackLists.contains(idKit)) {
				idKit++;
				if (idKit >= 6 * 9) {
					idKit = 10;
					pageKit++;
				}
			}
			while (blackLists.contains(idShop)) {
				idShop++;
				if (idShop >= 6 * 9) {
					idShop = 10;
					pageShop++;
				}
			}
			if (!kit.isShowOnGui())continue;
			if (!player.hasPermission(prePerm + kit.getName())) {
				Slot slot = (Slot) new Slot(kit.getIcon(), idShop)
						.setEffect(new PlayerEffect() {

							@Override
							public void effect(Player p) {
								Inventory inv = API
										.newInventory(
												guiShopTitle
														.replace("$kit",
																kit.getName())
														.replace("$price",
																"" + kit.getPrice()),
												9);
								inv.setItem(getConfirmShop().getSlot(),
										getConfirmShop().getItem());
								inv.setItem(getCancelShop().getSlot(),
										getCancelShop().getItem());
								while (!ItemAPI.isFull(inv)) {
									inv.setItem(inv.firstEmpty(),
											emptySlotItem);
								}
								p.openInventory(inv);
								buying.put(p, kit);
							}
						});
				guisKitShops.get(player)[pageShop].set(slot);
				idShop++;
			} else {
				Slot slot = (Slot) new Slot(kit.getIcon(), idKit)
						.setCloseInventory(true).setEffect(new PlayerEffect() {
							public void effect(Player p) {
								selectKit(p, kit);
							}
						});
				guisKitSelectors.get(player)[pageKit].set(slot);
				idKit++;
			}

		}
	}
	public Kit getKit(Player player) {
		if (hasKit(player)){
			return players.get(player);			
		}
		players.put(player, KIT_NONE);
		return KIT_NONE;
	}
	public boolean hasKit(Player player){
		return players.containsKey(player);
	}

	public Object get(Section section) {
		section.getSection("kits").set("!Map");
		for (Section key : section.getValues("kits")) {
			kits.put(key.getKey(), (Kit) key.getValue());
		}
		return null;
	}

	public Slot getCancelShop() {
		return cancelShop;
	}

	public Slot getConfirmShop() {
		return confirmShop;
	}

	public ItemStack getEmptySlotItem() {
		return emptySlotItem;
	}

	public String getGuiShopTitle() {
		return guiShopTitle;
	}

	public ItemStack getHotBarItem() {
		return hotBarItem;
	}

	public Kit getKit(KitType type) {
		return kits.get(type.name());
	}

	public String getKitBuyed() {
		return kitBuyed;
	}

	public String getKitGived() {
		return kitGived;
	}

	public String getKitSelected() {
		return kitSelected;
	}

	public Click getKitSelector() {
		return kitSelector;
	}

	public Click getKitShop() {
		return kitShop;
	}

	public Slot getNextPageItem() {
		return nextPageItem;
	}

	public String getNoKitBuyed() {
		return noKitBuyed;
	}

	private int getPage(int id) {
		if (id == pagesAmount) {
			id--;
		} else if (id < 0) {
			id++;
		}
		return id;

	}

	public int getPagesAmount() {
		return pagesAmount;
	}

	public String getPageTag() {
		return pageTag;
	}

	public String getPrePermission() {
		return prePerm;
	}

	public Slot getPreviousPageItem() {
		return previousPageItem;
	}

	public String getSelectorTitle() {
		return selectorTitle;
	}

	public String getShopTitle() {
		return shopTitle;
	}

	public ItemStack getSoup() {
		return soup;
	}

	public void giveItems(Player player) {
		PlayerInventory inv = player.getInventory();
		ItemAPI.setSlot(inv, kitSelector);
		ItemAPI.setSlot(inv, kitShop);
		if (fillHotBar) {
			ItemAPI.addHotBar(player, hotBarItem);
		}

	}

	public boolean isGiveSoups() {
		return giveSoups;
	}

	public void openKitSelector(Player player) {
		if (kitsEnabled)
			openKitSelector(player, 0);
		else
			Cs.chat(player, kitsDisabled);
	}

	public void openKitSelector(Player player, int page) {
		guisKitSelectors.get(player)[page].open(player);
	}

	public void openKitShop(Player player) {
		openKitShop(player, 0);
	}

	public void openKitShop(Player player, int page) {
		guisKitShops.get(player)[page].open(player);
	}

	public boolean register(String name, Kit kit) {
		if (kits.containsKey(name))
			return false;
		kits.put(name, kit);
		return true;
	}
	public KitManager register(JavaPlugin plugin) {
		for (Kit kit : kits.values()) {
			kit.register(plugin);
			if (kit.getClick() != null) {
				kit.getClick().register(plugin);
			}
			if (kit.getPrice() == 0) {
				kit.setPrice(defaultKitPrice);
			}
		}

		kitSelector.setClick(new ClickEffect() {

			@Override
			public void effect(PlayerInteractEntityEvent e) {

			}

			@Override
			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (guisKitSelectors.containsKey(p)) {
					openKitSelector(p);
				}
			}
		});
		kitShop.setClick(new ClickEffect() {

			@Override
			public void effect(PlayerInteractEntityEvent e) {

			}

			@Override
			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (guisKitShops.containsKey(p)) {
					openKitShop(p);
				}
			}
		});
		kitSelector.register(plugin);
		kitShop.register(plugin);
		return this;
	}

	public void reloadDefaults() {
		for (KitType type : KitType.values()) {
			if (type==KitType.NONE|type==KitType.ANOTHER)continue;
			try {
				
				Kit kit = (Kit) RexAPI
						.getNew("#k" + Cs.toTitle(type.name(), ""));
				register(type.name(), kit);

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	}

	public void removeKit(Player player) {
		removeKits(player);
		players.remove(player);
	}

	public void removeKits(Player player) {
		for (Kit kit : kits.values()) {
			kit.getPlayers().remove(player);
		}
	}

	public void save(Section section, Object value) {
		Section kit = section.getSection("kits");
		for (Entry<String, Kit> map : kits.entrySet()) {
			kit.set(map.getKey(), map.getValue());
		}
	}

	public void selectKit(Player player, Kit kit) {
		players.put(player, kit);
		player.sendMessage(kitSelected.replace("$kit", kit.getName()));
		if (onSelectGainKit) {
			gainKit(player);
		}

	}
	public void selectKit(Player player, KitType type) {
		if (kits.containsKey(type.name())) {
			Kit kit = kits.get(type.name());
			selectKit(player, kit);
		}
	}
	public void registerKits(KitType... kits) {
		getTypes().clear();
		for (KitType type : kits) {
			try {
				Kit kit = (Kit) RexAPI
						.getNew("#k" + Cs.toTitle(type.name(), ""));
				register(type.name(), kit);
Cs.consoleMessage("§bKitAPI §fo Kit §a" + type.name()
						+ "§f foi registrado!");

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
		getTypes().addAll(Arrays.asList(kits));
	}

	public void setCancelShop(Slot cancelShop) {
		this.cancelShop = cancelShop;
	}

	public void setConfirmShop(Slot confirmShop) {
		this.confirmShop = confirmShop;
	}

	public void setEmptySlotItem(ItemStack emptySlotItem) {
		this.emptySlotItem = emptySlotItem;
	}

	public void setGiveSoups(boolean giveSoups) {
		this.giveSoups = giveSoups;
	}

	public void setGuiShopTitle(String guiShopTitle) {
		this.guiShopTitle = guiShopTitle;
	}

	public void setHotBarItem(ItemStack hotBarItem) {
		this.hotBarItem = hotBarItem;
	}

	public void setKitBuyed(String kitBuyed) {
		this.kitBuyed = kitBuyed;
	}

	public void setKitGived(String kitGived) {
		this.kitGived = kitGived;
	}

	public void setKitSelected(String kitSelected) {
		this.kitSelected = kitSelected;
	}

	public void setKitSelector(Click kitSelector) {
		this.kitSelector = kitSelector;
	}

	public void setKitShop(Click kitShop) {
		this.kitShop = kitShop;
	}

	public void setNextPageItem(Slot nextPageItem) {
		this.nextPageItem = nextPageItem;
	}

	public void setNoKitBuyed(String noKitBuyed) {
		this.noKitBuyed = noKitBuyed;
	}

	public void setPagesAmount(int pagesAmount) {
		this.pagesAmount = pagesAmount;
	}

	public void setPageTag(String pageTag) {
		this.pageTag = pageTag;
	}

	public void setPrePermission(String permission) {
		this.prePerm = permission;
	}

	public void setPreviousPageItem(Slot previousPageItem) {
		this.previousPageItem = previousPageItem;
	}

	public void setSelectorTitle(String selectorTitle) {
		this.selectorTitle = selectorTitle;
	}

	public void setShopTitle(String shopTitle) {
		this.shopTitle = shopTitle;
	}

	public void setSoup(ItemStack soup) {
		this.soup = soup;
	}

	public void removeGuis(Player p) {
		guisKitSelectors.remove(p);
		guisKitShops.remove(p);
	}

	public List<ItemStack> getGlobalItems() {
		return globalItems;
	}

	public void setGlobalItems(List<ItemStack> globalItems) {
		this.globalItems = globalItems;
	}

	public double getDefaultKitPrice() {
		return defaultKitPrice;
	}

	public void setDefaultKitPrice(double defaultKitPrice) {
		this.defaultKitPrice = defaultKitPrice;
	}


	public List<KitType> getTypes() {
		return types;
	}

	public void setTypes(List<KitType> types) {
		this.types = types;
	}

	public boolean isKitsEnabled() {
		return kitsEnabled;
	}

	public void setKitsEnabled(boolean kitsEnabled) {
		this.kitsEnabled = kitsEnabled;
	}

	public String getKitsDisabled() {
		return kitsDisabled;
	}

	public void setKitsDisabled(String kitsDisabled) {
		this.kitsDisabled = kitsDisabled;
	}

	public String getNoneKit() {
		return noneKit;
	}

	public void setNoneKit(String noneKit) {
		this.noneKit = noneKit;
	}

}
