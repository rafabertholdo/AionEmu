package com.aionemu.gameserver.services;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.trade.Exchange;
import com.aionemu.gameserver.model.trade.ExchangeItem;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EXCHANGE_ADD_ITEM;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EXCHANGE_ADD_KINAH;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EXCHANGE_CONFIRMATION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EXCHANGE_REQUEST;
import com.aionemu.gameserver.restrictions.RestrictionsManager;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class ExchangeService {
  private static final Logger log = Logger.getLogger(ExchangeService.class);

  private Map<Integer, Exchange> exchanges;

  public static final ExchangeService getInstance() {
    return SingletonHolder.instance;
  }

  private ExchangeService() {
    this.exchanges = new HashMap<Integer, Exchange>();
    log.info("ExchangeService: Initialized.");
  }

  public void registerExchange(Player player1, Player player2) {
    if (!validateParticipants(player1, player2)) {
      return;
    }
    player1.setTrading(true);
    player2.setTrading(true);

    this.exchanges.put(Integer.valueOf(player1.getObjectId()), new Exchange(player1, player2));
    this.exchanges.put(Integer.valueOf(player2.getObjectId()), new Exchange(player2, player1));

    PacketSendUtility.sendPacket(player2, (AionServerPacket) new SM_EXCHANGE_REQUEST(player1.getName()));
    PacketSendUtility.sendPacket(player1, (AionServerPacket) new SM_EXCHANGE_REQUEST(player2.getName()));
  }

  private boolean validateParticipants(Player player1, Player player2) {
    return (RestrictionsManager.canTrade(player1) && RestrictionsManager.canTrade(player2));
  }

  private Player getCurrentParter(Player player) {
    Exchange exchange = this.exchanges.get(Integer.valueOf(player.getObjectId()));
    return (exchange != null) ? exchange.getTargetPlayer() : null;
  }

  private Exchange getCurrentExchange(Player player) {
    return this.exchanges.get(Integer.valueOf(player.getObjectId()));
  }

  public Exchange getCurrentParnterExchange(Player player) {
    Player partner = getCurrentParter(player);
    return (partner != null) ? getCurrentExchange(partner) : null;
  }

  public void addKinah(Player activePlayer, long itemCount) {
    Exchange currentExchange = getCurrentExchange(activePlayer);
    if (currentExchange.isLocked()) {
      return;
    }
    if (itemCount < 1L) {
      return;
    }

    long availableCount = activePlayer.getInventory().getKinahItem().getItemCount();

    availableCount -= currentExchange.getKinahCount();

    long countToAdd = (availableCount > itemCount) ? itemCount : availableCount;

    if (countToAdd > 0L) {

      Player partner = getCurrentParter(activePlayer);
      PacketSendUtility.sendPacket(activePlayer, (AionServerPacket) new SM_EXCHANGE_ADD_KINAH(countToAdd, 0));
      PacketSendUtility.sendPacket(partner, (AionServerPacket) new SM_EXCHANGE_ADD_KINAH(countToAdd, 1));
      currentExchange.addKinah(countToAdd);
    }
  }

  public void addItem(Player activePlayer, int itemObjId, long itemCount) {
    Item item = activePlayer.getInventory().getItemByObjId(itemObjId);
    if (item == null) {
      return;
    }

    if (!item.isTradeable()) {
      return;
    }
    if (itemCount < 1L) {
      return;
    }
    if (itemCount > item.getItemCount()) {
      return;
    }
    Player partner = getCurrentParter(activePlayer);
    Exchange currentExchange = getCurrentExchange(activePlayer);

    if (currentExchange == null) {
      return;
    }
    if (currentExchange.isLocked()) {
      return;
    }
    if (currentExchange.isExchangeListFull()) {
      return;
    }
    ExchangeItem exchangeItem = (ExchangeItem) currentExchange.getItems().get(Integer.valueOf(item.getObjectId()));

    long actuallAddCount = 0L;

    if (exchangeItem == null) {

      if (item.getItemCount() == itemCount) {
        exchangeItem = new ExchangeItem(itemObjId, itemCount, item);
      } else {

        Item newItem = ItemService.newItem(item.getItemId(), itemCount);
        newItem.setPersistentState(PersistentState.NOACTION);
        exchangeItem = new ExchangeItem(itemObjId, itemCount, newItem);
      }
      currentExchange.addItem(itemObjId, exchangeItem);
      actuallAddCount = itemCount;

    } else {

      if (item.getItemCount() == exchangeItem.getItemCount()) {
        return;
      }
      long possibleToAdd = item.getItemCount() - exchangeItem.getItemCount();
      actuallAddCount = (itemCount > possibleToAdd) ? possibleToAdd : itemCount;
      exchangeItem.addCount(actuallAddCount);
    }

    PacketSendUtility.sendPacket(activePlayer, (AionServerPacket) new SM_EXCHANGE_ADD_ITEM(0, exchangeItem.getItem()));
    PacketSendUtility.sendPacket(partner, (AionServerPacket) new SM_EXCHANGE_ADD_ITEM(1, exchangeItem.getItem()));
  }

  public void lockExchange(Player activePlayer) {
    Exchange exchange = getCurrentExchange(activePlayer);
    if (exchange != null) {

      exchange.lock();
      Player currentParter = getCurrentParter(activePlayer);
      PacketSendUtility.sendPacket(currentParter, (AionServerPacket) new SM_EXCHANGE_CONFIRMATION(3));
    }
  }

  public void cancelExchange(Player activePlayer) {
    Player currentParter = getCurrentParter(activePlayer);
    cleanupExchanges(activePlayer, currentParter);
    if (currentParter != null) {
      PacketSendUtility.sendPacket(currentParter, (AionServerPacket) new SM_EXCHANGE_CONFIRMATION(1));
    }
  }

  public void confirmExchange(Player activePlayer) {
    Exchange currentExchange = getCurrentExchange(activePlayer);
    currentExchange.confirm();

    Player currentPartner = getCurrentParter(activePlayer);
    PacketSendUtility.sendPacket(currentPartner, (AionServerPacket) new SM_EXCHANGE_CONFIRMATION(2));

    if (getCurrentExchange(currentPartner).isConfirmed()) {
      performTrade(activePlayer, currentPartner);
    }
  }

  private void performTrade(Player activePlayer, Player currentPartner) {
    if (!validateExchange(activePlayer, currentPartner)) {
      return;
    }
    PacketSendUtility.sendPacket(activePlayer, (AionServerPacket) new SM_EXCHANGE_CONFIRMATION(0));
    PacketSendUtility.sendPacket(currentPartner, (AionServerPacket) new SM_EXCHANGE_CONFIRMATION(0));

    doExchanges(activePlayer, currentPartner);
    doExchanges(currentPartner, activePlayer);

    cleanupExchanges(activePlayer, currentPartner);
  }

  private void cleanupExchanges(Player activePlayer, Player currentPartner) {
    if (activePlayer != null) {

      Exchange exchange = this.exchanges.remove(Integer.valueOf(activePlayer.getObjectId()));
      if (exchange != null)
        exchange.clear();
      activePlayer.setTrading(false);
    }

    if (currentPartner != null) {

      Exchange exchange = this.exchanges.remove(Integer.valueOf(currentPartner.getObjectId()));
      if (exchange != null)
        exchange.clear();
      currentPartner.setTrading(false);
    }
  }

  private void doExchanges(Player sourcePlayer, Player targetPlayer) {
    Exchange exchange = getCurrentExchange(sourcePlayer);

    for (ExchangeItem exchangeItem : exchange.getItems().values()) {

      Item itemInInventory = sourcePlayer.getInventory().getItemByObjId(exchangeItem.getItemObjId());
      if (exchangeItem.getItemCount() == itemInInventory.getItemCount()) {

        ItemService.removeItem(sourcePlayer, sourcePlayer.getInventory(), itemInInventory, false);
        ItemService.addFullItem(targetPlayer, targetPlayer.getInventory(), itemInInventory);

        continue;
      }
      ItemService.decreaseItemCount(sourcePlayer, itemInInventory, exchangeItem.getItemCount());
      ItemService.addItem(targetPlayer, itemInInventory.getItemId(), exchangeItem.getItemCount());
    }

    long kinah = exchange.getKinahCount();
    if (kinah > 0L) {

      ItemService.decreaseKinah(sourcePlayer, exchange.getKinahCount());
      ItemService.increaseKinah(targetPlayer, exchange.getKinahCount());
    }
  }

  private boolean validateExchange(Player activePlayer, Player currentPartner) {
    Exchange exchange1 = getCurrentExchange(activePlayer);
    Exchange exchange2 = getCurrentExchange(currentPartner);

    return (validateInventorySize(activePlayer, exchange2) && validateInventorySize(currentPartner, exchange1));
  }

  private boolean validateInventorySize(Player activePlayer, Exchange exchange) {
    int numberOfFreeSlots = activePlayer.getInventory().getNumberOfFreeSlots();
    return (numberOfFreeSlots >= exchange.getItems().size());
  }

  private static class SingletonHolder {
    protected static final ExchangeService instance = new ExchangeService();
  }
}
