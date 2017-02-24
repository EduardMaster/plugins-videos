package net.eduard.api.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import net.eduard.api.API;
import net.eduard.api.config.Section;
import net.eduard.api.config.VaultAPI;
import net.eduard.api.manager.RexAPI;
import net.eduard.api.util.ClickCheck;
import net.eduard.api.util.ClickType;
import net.eduard.api.util.KitType;
import net.eduard.api.util.PlayerEffect;
import net.eduard.api.util.Save;

public class KitManager implements Save {
	private String prePerm = "kits.";
	private Click kitSelector;
	private Click kitShop;
	private Slot confirmShop = new Slot(API.newItem(Material.CACTUS, "§bConfirmar Compra"), 0);
	private Slot cancelShop = new Slot(API.newItem(Material.BED, "§bAvançar"), 8);;
	private ItemStack soup = API.newItem("§6Sopa", Material.MUSHROOM_SOUP);
	private int pagesAmount = 3;
	private Slot nextPageItem = new Slot(API.newItem(Material.BEACON, "§bAvançar"), 8);;
	private ItemStack emptySlotItem = API.newItem(" ", Material.STAINED_GLASS_PANE, 15);
	private ItemStack hotBarItem = API.newItem("§6§lKit§f§lPvP", Material.STAINED_GLASS_PANE, 10);
	private Slot previousPageItem = new Slot(API.newItem(Material.BED, "§bVoltar"), 0);;
	private String pageTag = "§8Página §7$page";
	private String selectorTitle = "§8Seus Kits: ";
	private String shopTitle = "§7Compre Kits: ";
	private String kitSelected = "§6Voce escolheu o Kit $kit";
	private String kitGived = "§6Voce ganhou o Kit $kit";
	private String kitBuyed = "§§Voce comprou o Kit $kit";
	private String noKitBuyed = "§§Voce nao tem dinheiro para comprar este kit $kit";
	private String guiShopTitle = "§8Kit $kit seu preço: $price";
	private boolean giveSoups, fillHotBar = true, onSelectGainKit = true;
	private HashMap<Player, Gui[]> guisKitSelectors = new HashMap<>();
	private HashMap<Player, Gui[]> guisKitShops = new HashMap<>();
	private ArrayList<Integer> blackLists = new ArrayList<>();
	private HashMap<Player, Kit> buying = new HashMap<>();
	private HashMap<Player, Kit> players = new HashMap<>();

	private HashMap<String, Kit> kits = new HashMap<>();

	public KitManager() {
		kitSelector = (Click) new Click(API.newItem(Material.COMPASS, "§4§lKitSelector"), ClickType.RIGHT_CLICK,
				ClickCheck.IGNORING_AMOUNT) {

		}.setSlot(4);
		kitShop = (Click) new Click(API.newItem(Material.EMERALD, "§b§lKitShop"), ClickType.RIGHT_CLICK,
				ClickCheck.IGNORING_AMOUNT) {

		}.setSlot(2);

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
	}

	@EventHandler
	private void event(InventoryClickEvent e) {
		if (e.getCurrentItem() == null)
			return;
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (buying.containsKey(p)) {
				e.setCancelled(true);
				if (e.getCurrentItem().equals(confirmShop.getItem())) {
					Kit kit = buying.get(p);
					if (VaultAPI.hasVault()) {
						if (VaultAPI.getEconomy().has(p.getName(), kit.getPrice())) {
							VaultAPI.getEconomy().withdrawPlayer(p.getName(), kit.getPrice());
							VaultAPI.getPermission().playerAdd(p, prePerm + kit.getName().toLowerCase());
							p.sendMessage(
									kitBuyed.replace("$kit", kit.getName()).replace("$price", "" + kit.getPrice()));
							generateGuis(p);
						} else {
							p.sendMessage(
									noKitBuyed.replace("$kit", kit.getName()).replace("$price", "" + kit.getPrice()));
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
	private void event(InventoryCloseEvent e) {
		if (buying.containsKey(e.getPlayer())) {
			buying.remove(e.getPlayer());
		}
	}

	public void gainKit(Player player) {
		Kit kit = players.get(player);
		removeKits(player);
		API.refreshAll(player);
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
		player.sendMessage(kitGived.replace("$kit", kit.getName()));
	}

	public void generateGuis(Player player) {
		if (kitSelector.getCustomEffect() == null) {
			kitSelector.setCustomEffect(new PlayerEffect() {

				public void effect(Player p) {
					openKitSelector(p);
				}
			});
		}
		if (kitShop.getCustomEffect() == null) {
			kitShop.setCustomEffect(new PlayerEffect() {

				public void effect(Player p) {
					openKitShop(p);
				}
			});
		}
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
			Gui inv = new Gui(6, selectorTitle + pageTag.replace("$page", "" + (i + 1)));
			Gui shop = new Gui(6, shopTitle + pageTag.replace("$page", "" + (i + 1)));
			if (i > 0) {
				inv.canOpen(false);
				shop.canOpen(false);
			}
			inv.register();
			shop.register();
			for (Integer id : blackLists) {
				inv.set(id, emptySlotItem);
				shop.set(id, emptySlotItem);
			}
			final int x = i;
			inv.set(nextPageItem, new Events().setCustomEffect(new PlayerEffect() {

				public void effect(Player p) {
					openKitSelector(player, getPage(x + 1));
				}
			}));
			inv.set(previousPageItem, new Events().setCustomEffect(new PlayerEffect() {

				public void effect(Player p) {
					openKitSelector(player, getPage(x - 1));
				}
			}));
			shop.set(nextPageItem, new Events().setCustomEffect(new PlayerEffect() {

				public void effect(Player p) {
					openKitShop(player, getPage(x + 1));
				}
			}));
			shop.set(previousPageItem, new Events().setCustomEffect(new PlayerEffect() {

				public void effect(Player p) {
					openKitShop(player, getPage(x - 1));
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
			if (!player.hasPermission(prePerm + kit.getName())) {
				guisKitShops.get(player)[pageShop].set(idShop, kit.getIcon(), new Events() {
					public void effect(Player p) {
						Inventory inv = API.newInventory(
								guiShopTitle.replace("$kit", kit.getName()).replace("$price", "" + kit.getPrice()), 9);
						inv.setItem(getConfirmShop().getSlot(), getConfirmShop().getItem());
						inv.setItem(getCancelShop().getSlot(), getCancelShop().getItem());
						while (!API.isFull(inv)) {
							inv.setItem(inv.firstEmpty(), emptySlotItem);
						}
						p.openInventory(inv);
						buying.put(p, kit);
					}
				});
				idShop++;
			} else {
				guisKitSelectors.get(player)[pageKit].set(idKit, kit.getIcon(), new Events() {
					public void effect(Player p) {
						selectKit(p, kit);
						p.closeInventory();
					}
				});
				idKit++;
			}

		}
	}

	public Object get(Section section) {
		section.getSection("kits").set("!HashMap");
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
		API.setSlot(inv, kitSelector);
		API.setSlot(inv, kitShop);
		if (fillHotBar) {
			int id = 0;
			while (((id = inv.firstEmpty()) < 9)) {
				inv.setItem(id, hotBarItem);

			}
		}

	}

	public boolean isGiveSoups() {
		return giveSoups;
	}

	public void openKitSelector(Player player) {
		openKitSelector(player, 0);
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

	public void reloadDefaults() {
		for (KitType type : KitType.values()) {
			try {
				String pack = "net.eduard.kits.";
				Kit kit = (Kit) RexAPI.getNew(pack + API.toTitle(type.toString(), ""));
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

	public void selectKits(KitType... kits) {
		for (KitType type:kits) {
			try {
				String pack = "net.eduard.kits.";
				Kit kit = (Kit) RexAPI.getNew(pack + API.toTitle(type.toString(), ""));
				register(type.name(), kit);

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
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

}
