package com.aionemu.gameserver.services;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PrivateStore;
import com.aionemu.gameserver.model.gameobjects.player.Storage;
import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
import com.aionemu.gameserver.model.trade.TradeItem;
import com.aionemu.gameserver.model.trade.TradeList;
import com.aionemu.gameserver.model.trade.TradePSItem;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PRIVATE_STORE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PRIVATE_STORE_NAME;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.Iterator;

public class PrivateStoreService {
  public static void addItem(Player activePlayer, TradePSItem[] tradePSItems) {
    if (CreatureState.ACTIVE.getId() != activePlayer.getState()) {
      return;
    }

    if (activePlayer.getStore() == null) {
      createStore(activePlayer);
    }

    PrivateStore store = activePlayer.getStore();

    for (int i = 0; i < tradePSItems.length; i++) {

      Item item = getItemByObjId(activePlayer, tradePSItems[i].getItemObjId());
      if (item != null && item.isTradeable()) {

        if (!validateItem(item, tradePSItems[i].getItemId(), tradePSItems[i].getCount())) {
          return;
        }

        store.addItemToSell(tradePSItems[i].getItemObjId(), tradePSItems[i]);
      }
    }
  }

  private static boolean validateItem(Item item, int itemId, long itemAmount) {
    return (item.getItemTemplate().getTemplateId() == itemId && itemAmount <= item.getItemCount());
  }

  private static void createStore(Player activePlayer) {
    activePlayer.setStore(new PrivateStore(activePlayer));
    activePlayer.setState(CreatureState.PRIVATE_SHOP);
    PacketSendUtility.broadcastPacket(activePlayer,
        (AionServerPacket) new SM_EMOTION((Creature) activePlayer, EmotionType.OPEN_PRIVATESHOP, 0, 0), true);

    if (CreatureState.PRIVATE_SHOP.getId() != activePlayer.getState()) {
      return;
    }
  }

  public static void closePrivateStore(Player activePlayer) {
    activePlayer.setStore(null);
    activePlayer.unsetState(CreatureState.PRIVATE_SHOP);
    PacketSendUtility.broadcastPacket(activePlayer,
        (AionServerPacket) new SM_EMOTION((Creature) activePlayer, EmotionType.CLOSE_PRIVATESHOP, 0, 0), true);
  }

  public static void sellStoreItem(Player seller, Player buyer, TradeList tradeList) {
    if (!validateParticipants(seller, buyer)) {
      return;
    }

    PrivateStore store = seller.getStore();

    tradeList = loadObjIds(seller, tradeList);
    if (tradeList == null) {
      return;
    }

    Storage inventory = buyer.getInventory();
    int freeSlots = inventory.getLimit() - inventory.getAllItems().size() + 1;
    if (freeSlots < tradeList.size()) {
      return;
    }

    long price = getTotalPrice(store, tradeList);

    if (!ItemService.decreaseKinah(buyer, price)) {
      return;
    }

    ItemService.increaseKinah(seller, price);

    for (TradeItem tradeItem : tradeList.getTradeItems()) {

      Item item = getItemByObjId(seller, tradeItem.getItemId());
      if (item != null) {

        TradePSItem storeItem = store.getTradeItemById(tradeItem.getItemId());
        if (item.getItemCount() == tradeItem.getCount()) {

          ItemService.removeItem(seller, seller.getInventory(), item, false);
          ItemService.addFullItem(buyer, buyer.getInventory(), item);
          store.removeItem(storeItem.getItemObjId());

          continue;
        }
        ItemService.decreaseItemCount(seller, item, tradeItem.getCount());
        ItemService.addItem(buyer, item.getItemId(), tradeItem.getCount());
        store.getTradeItemById(storeItem.getItemObjId()).decreaseCount(tradeItem.getCount());
        if (store.getTradeItemById(storeItem.getItemObjId()).getCount() == 0L) {
          store.removeItem(storeItem.getItemObjId());
        }
      }
    }

    if (store.getSoldItems().size() == 0) {
      closePrivateStore(seller);
    } else {
      PacketSendUtility.sendPacket(buyer, (AionServerPacket) new SM_PRIVATE_STORE(store));
    }
  }

  private static TradeList loadObjIds(Player seller, TradeList tradeList) {
    PrivateStore store = seller.getStore();
    TradeList newTradeList = new TradeList();

    for (TradeItem tradeItem : tradeList.getTradeItems()) {

      int i = 0;
      for (Iterator<Integer> i$ = store.getSoldItems().keySet().iterator(); i$.hasNext();) {
        int itemObjId = ((Integer) i$.next()).intValue();

        if (i == tradeItem.getItemId())
          newTradeList.addPSItem(itemObjId, tradeItem.getCount());
        i++;
      }

    }

    if (newTradeList.size() == 0 || !validateBuyItems(seller, newTradeList)) {
      return null;
    }
    return newTradeList;
  }

  private static boolean validateParticipants(Player itemOwner, Player newOwner) {
    return (itemOwner != null && newOwner != null && itemOwner.isOnline() && newOwner.isOnline());
  }

  private static boolean validateBuyItems(Player seller, TradeList tradeList) {
    for (TradeItem tradeItem : tradeList.getTradeItems()) {

      Item item = seller.getInventory().getItemByObjId(tradeItem.getItemId());

      if (item == null) {
        return false;
      }
    }
    return true;
  }

  private static Item getItemByObjId(Player seller, int itemObjId) {
    return seller.getInventory().getItemByObjId(itemObjId);
  }

  private static long getTotalPrice(PrivateStore store, TradeList tradeList) {
    long totalprice = 0L;
    for (TradeItem tradeItem : tradeList.getTradeItems()) {

      TradePSItem item = store.getTradeItemById(tradeItem.getItemId());
      totalprice += item.getPrice() * tradeItem.getCount();
    }
    return totalprice;
  }

  public static void openPrivateStore(Player activePlayer, String name) {
    if (name != null) {

      activePlayer.getStore().setStoreMessage(name);
      PacketSendUtility.broadcastPacket(activePlayer,
          (AionServerPacket) new SM_PRIVATE_STORE_NAME(activePlayer.getObjectId(), name), true);

    } else {

      PacketSendUtility.broadcastPacket(activePlayer,
          (AionServerPacket) new SM_PRIVATE_STORE_NAME(activePlayer.getObjectId(), ""), true);
    }
  }
}
