package com.aionemu.gameserver.services;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.dao.BrokerDAO;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.broker.BrokerItemMask;
import com.aionemu.gameserver.model.broker.BrokerMessages;
import com.aionemu.gameserver.model.broker.BrokerPlayerCache;
import com.aionemu.gameserver.model.broker.BrokerRace;
import com.aionemu.gameserver.model.gameobjects.BrokerItem;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ADD_ITEMS;
import com.aionemu.gameserver.network.aion.serverpackets.SM_BROKER_ITEMS;
import com.aionemu.gameserver.network.aion.serverpackets.SM_BROKER_REGISTERED_LIST;
import com.aionemu.gameserver.network.aion.serverpackets.SM_BROKER_REGISTRATION_SERVICE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_BROKER_SETTLED_LIST;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.taskmanager.AbstractFIFOPeriodicTaskManager;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javolution.util.FastMap;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

public class BrokerService {
  private Map<Integer, BrokerItem> elyosBrokerItems = (Map<Integer, BrokerItem>) (new FastMap()).shared();
  private Map<Integer, BrokerItem> elyosSettledItems = (Map<Integer, BrokerItem>) (new FastMap()).shared();
  private Map<Integer, BrokerItem> asmodianBrokerItems = (Map<Integer, BrokerItem>) (new FastMap()).shared();
  private Map<Integer, BrokerItem> asmodianSettledItems = (Map<Integer, BrokerItem>) (new FastMap()).shared();

  private static final Logger log = Logger.getLogger(BrokerService.class);

  private final int DELAY_BROKER_SAVE = 6000;
  private final int DELAY_BROKER_CHECK = 60000;

  private BrokerPeriodicTaskManager saveManager;

  private Map<Integer, BrokerPlayerCache> playerBrokerCache = (Map<Integer, BrokerPlayerCache>) (new FastMap())
      .shared();

  public static final BrokerService getInstance() {
    return SingletonHolder.instance;
  }

  public BrokerService() {
    initBrokerService();

    this.saveManager = new BrokerPeriodicTaskManager(6000);
    ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {

      public void run() {
        BrokerService.this.checkExpiredItems();
      }
    }, 60000L, 60000L);
  }

  private void initBrokerService() {
    log.info("Loading broker...");
    int loadedBrokerItemsCount = 0;
    int loadedSettledItemsCount = 0;

    List<BrokerItem> brokerItems = ((BrokerDAO) DAOManager.getDAO(BrokerDAO.class)).loadBroker();

    for (BrokerItem item : brokerItems) {

      if (item.getItemBrokerRace() == BrokerRace.ASMODIAN) {

        if (item.isSettled()) {

          this.asmodianSettledItems.put(Integer.valueOf(item.getItemUniqueId()), item);
          loadedSettledItemsCount++;

          continue;
        }
        this.asmodianBrokerItems.put(Integer.valueOf(item.getItemUniqueId()), item);
        loadedBrokerItemsCount++;
        continue;
      }
      if (item.getItemBrokerRace() == BrokerRace.ELYOS) {

        if (item.isSettled()) {

          this.elyosSettledItems.put(Integer.valueOf(item.getItemUniqueId()), item);
          loadedSettledItemsCount++;

          continue;
        }
        this.elyosBrokerItems.put(Integer.valueOf(item.getItemUniqueId()), item);
        loadedBrokerItemsCount++;
      }
    }

    log.info("Broker loaded with " + loadedBrokerItemsCount + " broker items, " + loadedSettledItemsCount
        + " settled items.");
  }

  public void showRequestedItems(Player player, int clientMask, int sortType, int startPage) {
    BrokerItem[] searchItems = null;
    int playerBrokerMaskCache = getPlayerMask(player);
    BrokerItemMask brokerMaskById = BrokerItemMask.getBrokerMaskById(clientMask);
    boolean isChidrenMask = brokerMaskById.isChildrenMask(playerBrokerMaskCache);
    if ((getFilteredItems(player)).length == 0 || !isChidrenMask) {

      searchItems = getItemsByMask(player, clientMask, false);
    } else if (isChidrenMask) {

      searchItems = getItemsByMask(player, clientMask, true);
    } else {

      searchItems = getFilteredItems(player);
    }
    if (searchItems == null || searchItems.length < 0) {
      return;
    }
    int totalSearchItemsCount = searchItems.length;

    getPlayerCache(player).setBrokerSortTypeCache(sortType);
    getPlayerCache(player).setBrokerStartPageCache(startPage);

    sortBrokerItems(searchItems, sortType);
    searchItems = getRequestedPage(searchItems, startPage);

    PacketSendUtility.sendPacket(player,
        (AionServerPacket) new SM_BROKER_ITEMS(searchItems, totalSearchItemsCount, startPage));
  }

  private BrokerItem[] getItemsByMask(Player player, int clientMask, boolean cached) {
    List<BrokerItem> searchItems = new ArrayList<BrokerItem>();

    BrokerItemMask brokerMask = BrokerItemMask.getBrokerMaskById(clientMask);

    if (cached) {

      BrokerItem[] brokerItems = getFilteredItems(player);
      if (brokerItems == null) {
        return null;
      }
      for (BrokerItem item : brokerItems) {

        if (item != null && item.getItem() != null) {

          if (brokerMask.isMatches(item.getItem())) {
            searchItems.add(item);
          }
        }
      }
    } else {

      Map<Integer, BrokerItem> brokerItems = getRaceBrokerItems(player.getCommonData().getRace());
      if (brokerItems == null)
        return null;
      for (BrokerItem item : brokerItems.values()) {

        if (item == null || item.getItem() == null) {
          continue;
        }
        if (brokerMask.isMatches(item.getItem())) {
          searchItems.add(item);
        }
      }
    }

    BrokerItem[] items = searchItems.<BrokerItem>toArray(new BrokerItem[searchItems.size()]);
    getPlayerCache(player).setBrokerListCache(items);
    getPlayerCache(player).setBrokerMaskCache(clientMask);

    return items;
  }

  private void sortBrokerItems(BrokerItem[] brokerItems, int sortType) {
    Arrays.sort(brokerItems, BrokerItem.getComparatoryByType(sortType));
  }

  private BrokerItem[] getRequestedPage(BrokerItem[] brokerItems, int startPage) {
    List<BrokerItem> page = new ArrayList<BrokerItem>();
    int startingElement = startPage * 9;

    for (int i = startingElement, limit = 0; i < brokerItems.length && limit < 45; i++, limit++) {
      page.add(brokerItems[i]);
    }

    return page.<BrokerItem>toArray(new BrokerItem[page.size()]);
  }

  private Map<Integer, BrokerItem> getRaceBrokerItems(Race race) {
    switch (race) {

      case ASMODIAN:
        return this.elyosBrokerItems;
      case ELYOS:
        return this.asmodianBrokerItems;
    }
    return null;
  }

  private Map<Integer, BrokerItem> getRaceBrokerSettledItems(Race race) {
    switch (race) {

      case ASMODIAN:
        return this.elyosSettledItems;
      case ELYOS:
        return this.asmodianSettledItems;
    }
    return null;
  }

  public void buyBrokerItem(Player player, int itemUniqueId) {
    if (player.getInventory().isFull()) {

      PacketSendUtility.sendPacket(player, (AionServerPacket) SM_SYSTEM_MESSAGE.MSG_FULL_INVENTORY);

      return;
    }
    boolean isEmptyCache = ((getFilteredItems(player)).length == 0);
    Race playerRace = player.getCommonData().getRace();

    BrokerItem buyingItem = getRaceBrokerItems(playerRace).get(Integer.valueOf(itemUniqueId));

    if (buyingItem == null) {
      return;
    }
    Item item = buyingItem.getItem();
    long price = buyingItem.getPrice();

    if (player.getInventory().getKinahItem().getItemCount() < price) {
      return;
    }
    getRaceBrokerItems(playerRace).remove(Integer.valueOf(itemUniqueId));
    putToSettled(playerRace, buyingItem, true);

    if (!isEmptyCache) {

      BrokerItem[] newCache = (BrokerItem[]) ArrayUtils.removeElement((Object[]) getFilteredItems(player), buyingItem);
      getPlayerCache(player).setBrokerListCache(newCache);
    }

    ItemService.decreaseKinah(player, price);
    ItemService.addFullItem(player, player.getInventory(), item);

    BrokerOpSaveTask bost = new BrokerOpSaveTask(buyingItem);
    this.saveManager.add(bost);

    showRequestedItems(player, getPlayerCache(player).getBrokerMaskCache(),
        getPlayerCache(player).getBrokerSortTypeCache(), getPlayerCache(player).getBrokerStartPageCache());
  }

  private void putToSettled(Race race, BrokerItem brokerItem, boolean isSold) {
    if (isSold) {
      brokerItem.removeItem();
    } else {
      brokerItem.setSettled();
    }
    brokerItem.setPersistentState(PersistentState.UPDATE_REQUIRED);

    switch (race) {

      case ELYOS:
        this.asmodianSettledItems.put(Integer.valueOf(brokerItem.getItemUniqueId()), brokerItem);
        break;

      case ASMODIAN:
        this.elyosSettledItems.put(Integer.valueOf(brokerItem.getItemUniqueId()), brokerItem);
        break;
    }

    Player seller = World.getInstance().findPlayer(brokerItem.getSellerId());

    this.saveManager.add(new BrokerOpSaveTask(brokerItem));

    if (seller != null) {
      PacketSendUtility.sendPacket(seller, (AionServerPacket) new SM_BROKER_SETTLED_LIST(true));
    }
  }

  public void registerItem(Player player, int itemUniqueId, int price) {
    BrokerRace brRace;
    Item itemToRegister = player.getInventory().getItemByObjId(itemUniqueId);
    Race playerRace = player.getCommonData().getRace();

    if (itemToRegister == null) {
      return;
    }

    if (!itemToRegister.isTradeable()) {
      return;
    }

    if (playerRace == Race.ASMODIANS) {
      brRace = BrokerRace.ASMODIAN;
    } else if (playerRace == Race.ELYOS) {
      brRace = BrokerRace.ELYOS;
    } else {
      return;
    }
    int registrationCommition = Math.round(price * 0.02F);

    if (registrationCommition < 10) {
      registrationCommition = 10;
    }
    if (player.getInventory().getKinahItem().getItemCount() < registrationCommition) {

      PacketSendUtility.sendPacket(player,
          (AionServerPacket) new SM_BROKER_REGISTRATION_SERVICE(BrokerMessages.NO_ENOUGHT_KINAH.getId()));

      return;
    }

    ItemService.decreaseKinah(player, registrationCommition);
    ItemService.removeItemFromInventory(player, itemToRegister, false);

    itemToRegister.setItemLocation(126);

    BrokerItem newBrokerItem = new BrokerItem(itemToRegister, price, player.getName(), player.getObjectId(), brRace);

    switch (brRace) {

      case ASMODIAN:
        this.asmodianBrokerItems.put(Integer.valueOf(newBrokerItem.getItemUniqueId()), newBrokerItem);
        break;

      case ELYOS:
        this.elyosBrokerItems.put(Integer.valueOf(newBrokerItem.getItemUniqueId()), newBrokerItem);
        break;
    }

    BrokerOpSaveTask bost = new BrokerOpSaveTask(newBrokerItem);
    this.saveManager.add(bost);

    PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_BROKER_REGISTRATION_SERVICE(newBrokerItem));
  }

  public void showRegisteredItems(Player player) {
    Map<Integer, BrokerItem> brokerItems = getRaceBrokerItems(player.getCommonData().getRace());

    List<BrokerItem> registeredItems = new ArrayList<BrokerItem>();
    int playerId = player.getObjectId();

    for (BrokerItem item : brokerItems.values()) {

      if (item != null && item.getItem() != null && playerId == item.getSellerId()) {
        registeredItems.add(item);
      }
    }
    PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_BROKER_REGISTERED_LIST(registeredItems));
  }

  public void cancelRegisteredItem(Player player, int brokerItemId) {
    Map<Integer, BrokerItem> brokerItems = getRaceBrokerItems(player.getCommonData().getRace());
    BrokerItem brokerItem = brokerItems.get(Integer.valueOf(brokerItemId));

    if (brokerItem != null) {

      ItemService.addFullItem(player, player.getInventory(), brokerItem.getItem());
      brokerItem.setPersistentState(PersistentState.DELETED);
      this.saveManager.add(new BrokerOpSaveTask(brokerItem));
      brokerItems.remove(Integer.valueOf(brokerItemId));
    }
    showRegisteredItems(player);
  }

  public void showSettledItems(Player player) {
    Map<Integer, BrokerItem> brokerSettledItems = getRaceBrokerSettledItems(player.getCommonData().getRace());

    List<BrokerItem> settledItems = new ArrayList<BrokerItem>();

    int playerId = player.getObjectId();
    int totalKinah = 0;

    for (BrokerItem item : brokerSettledItems.values()) {

      if (item != null && playerId == item.getSellerId()) {

        settledItems.add(item);

        if (item.isSold()) {
          totalKinah = (int) (totalKinah + item.getPrice());
        }
      }
    }
    PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_BROKER_SETTLED_LIST(settledItems, totalKinah));
  }

  public void settleAccount(Player player) {
    Race playerRace = player.getCommonData().getRace();
    Map<Integer, BrokerItem> brokerSettledItems = getRaceBrokerSettledItems(playerRace);
    List<BrokerItem> collectedItems = new ArrayList<BrokerItem>();
    int playerId = player.getObjectId();
    int kinahCollect = 0;
    boolean itemsLeft = false;

    for (BrokerItem item : brokerSettledItems.values()) {

      if (item.getSellerId() == playerId) {
        collectedItems.add(item);
      }
    }
    for (BrokerItem item : collectedItems) {

      if (item.isSold()) {

        boolean result = false;
        switch (playerRace) {

          case ELYOS:
            result = (this.asmodianSettledItems.remove(Integer.valueOf(item.getItemUniqueId())) != null);
            break;
          case ASMODIAN:
            result = (this.elyosSettledItems.remove(Integer.valueOf(item.getItemUniqueId())) != null);
            break;
        }

        if (result) {

          item.setPersistentState(PersistentState.DELETED);
          this.saveManager.add(new BrokerOpSaveTask(item));
          kinahCollect = (int) (kinahCollect + item.getPrice());
        }

        continue;
      }
      if (item.getItem() != null) {

        Item resultItem = player.getInventory().putToBag(item.getItem());
        if (resultItem != null) {

          boolean result = false;
          switch (playerRace) {

            case ELYOS:
              result = (this.asmodianSettledItems.remove(Integer.valueOf(item.getItemUniqueId())) != null);
              break;
            case ASMODIAN:
              result = (this.elyosSettledItems.remove(Integer.valueOf(item.getItemUniqueId())) != null);
              break;
          }

          if (result) {

            item.setPersistentState(PersistentState.DELETED);
            this.saveManager.add(new BrokerOpSaveTask(item));
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_ADD_ITEMS(Collections.singletonList(resultItem)));
          }

          continue;
        }
        itemsLeft = true;

        continue;
      }
      log.warn("Broker settled item missed. ObjID: " + item.getItemUniqueId());
    }

    ItemService.increaseKinah(player, kinahCollect);
    showSettledItems(player);

    if (!itemsLeft) {
      PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_BROKER_SETTLED_LIST(false));
    }
  }

  private void checkExpiredItems() {
    Map<Integer, BrokerItem> asmoBrokerItems = getRaceBrokerItems(Race.ASMODIANS);
    Map<Integer, BrokerItem> elyosBrokerItems = getRaceBrokerItems(Race.ELYOS);

    Timestamp currentTime = new Timestamp(Calendar.getInstance().getTimeInMillis());

    for (BrokerItem item : asmoBrokerItems.values()) {

      if (item != null && item.getExpireTime().getTime() <= currentTime.getTime()) {

        putToSettled(Race.ASMODIANS, item, false);
        this.asmodianBrokerItems.remove(Integer.valueOf(item.getItemUniqueId()));
      }
    }

    for (BrokerItem item : elyosBrokerItems.values()) {

      if (item != null && item.getExpireTime().getTime() <= currentTime.getTime()) {

        putToSettled(Race.ELYOS, item, false);
        this.elyosBrokerItems.remove(Integer.valueOf(item.getItemUniqueId()));
      }
    }
  }

  public void onPlayerLogin(Player player) {
    Map<Integer, BrokerItem> brokerSettledItems = getRaceBrokerSettledItems(player.getCommonData().getRace());

    int playerId = player.getObjectId();

    for (BrokerItem item : brokerSettledItems.values()) {

      if (item != null && playerId == item.getSellerId()) {

        PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_BROKER_SETTLED_LIST(true));
        break;
      }
    }
  }

  private BrokerPlayerCache getPlayerCache(Player player) {
    BrokerPlayerCache cacheEntry = this.playerBrokerCache.get(Integer.valueOf(player.getObjectId()));
    if (cacheEntry == null) {

      cacheEntry = new BrokerPlayerCache();
      this.playerBrokerCache.put(Integer.valueOf(player.getObjectId()), cacheEntry);
    }
    return cacheEntry;
  }

  public void removePlayerCache(Player player) {
    this.playerBrokerCache.remove(Integer.valueOf(player.getObjectId()));
  }

  private int getPlayerMask(Player player) {
    return getPlayerCache(player).getBrokerMaskCache();
  }

  private BrokerItem[] getFilteredItems(Player player) {
    return getPlayerCache(player).getBrokerListCache();
  }

  public static final class BrokerPeriodicTaskManager extends AbstractFIFOPeriodicTaskManager<BrokerOpSaveTask> {
    private static final String CALLED_METHOD_NAME = "brokerOperation()";

    public BrokerPeriodicTaskManager(int period) {
      super(period);
    }

    protected void callTask(BrokerService.BrokerOpSaveTask task) {
      task.run();
    }

    protected String getCalledMethodName() {
      return "brokerOperation()";
    }
  }

  public static final class BrokerOpSaveTask implements Runnable {
    private BrokerItem brokerItem;

    public BrokerOpSaveTask(BrokerItem brokerItem) {
      this.brokerItem = brokerItem;
    }

    public void run() {
      if (this.brokerItem != null) {
        ((BrokerDAO) DAOManager.getDAO(BrokerDAO.class)).store(this.brokerItem);
      }
    }
  }

  private static class SingletonHolder {
    protected static final BrokerService instance = new BrokerService();
  }
}
