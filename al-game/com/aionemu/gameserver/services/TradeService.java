package com.aionemu.gameserver.services;

import com.aionemu.gameserver.configs.main.OptionsConfig;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.dataholders.GoodsListData;
import com.aionemu.gameserver.dataholders.TradeListData;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.Storage;
import com.aionemu.gameserver.model.templates.TradeListTemplate;
import com.aionemu.gameserver.model.templates.goods.GoodsList;
import com.aionemu.gameserver.model.trade.TradeItem;
import com.aionemu.gameserver.model.trade.TradeList;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;

public class TradeService {
  private static final Logger log = Logger.getLogger(TradeService.class);

  private static final TradeListData tradeListData = DataManager.TRADE_LIST_DATA;
  private static final GoodsListData goodsListData = DataManager.GOODSLIST_DATA;

  public static boolean performBuyFromShop(Player player, TradeList tradeList) {
    if (!validateBuyItems(tradeList, player)) {

      PacketSendUtility.sendMessage(player, "Some items are not allowed to be sold by this npc.");
      return false;
    }

    Storage inventory = player.getInventory();

    Npc npc = (Npc) World.getInstance().findAionObject(tradeList.getSellerObjId());
    int tradeModifier = tradeListData.getTradeListTemplate(npc.getNpcId()).getSellPriceRate();

    if (!tradeList.calculateBuyListPrice(player, tradeModifier)) {
      return false;
    }

    int freeSlots = inventory.getLimit() - inventory.getAllItems().size() + 1;
    if (freeSlots < tradeList.size()) {
      return false;
    }
    long tradeListPrice = tradeList.getRequiredKinah();

    for (TradeItem tradeItem : tradeList.getTradeItems()) {

      if (!ItemService.addItem(player, tradeItem.getItemTemplate().getTemplateId(), tradeItem.getCount())) {

        if (OptionsConfig.LOG_AUDIT) {
          log.warn(String.format("[AUDIT] Itemservice couldnt add all items on buy: %d %d %d %d",
              new Object[] { Integer.valueOf(player.getObjectId()),
                  Integer.valueOf(tradeItem.getItemTemplate().getTemplateId()), Long.valueOf(tradeItem.getCount()),
                  Long.valueOf(tradeItem.getCount()) }));
        }
        ItemService.decreaseKinah(player, tradeListPrice);
        return false;
      }
    }
    ItemService.decreaseKinah(player, tradeListPrice);

    return true;
  }

  public static boolean performBuyFromAbyssShop(Player player, TradeList tradeList) {
    if (!validateBuyItems(tradeList, player)) {

      PacketSendUtility.sendMessage(player, "Some items are not allowed to be selled from this npc");
      return false;
    }
    Storage inventory = player.getInventory();
    int freeSlots = inventory.getLimit() - inventory.getAllItems().size() + 1;

    if (!tradeList.calculateAbyssBuyListPrice(player)) {
      return false;
    }
    if (tradeList.getRequiredAp() < 0) {

      if (OptionsConfig.LOG_AUDIT)
        log.warn("[AUDIT] Player: " + player.getName() + " posible client hack. tradeList.getRequiredAp() < 0");
      return false;
    }

    if (freeSlots < tradeList.size()) {
      return false;
    }
    for (TradeItem tradeItem : tradeList.getTradeItems()) {

      if (!ItemService.addItem(player, tradeItem.getItemTemplate().getTemplateId(), tradeItem.getCount())) {

        if (OptionsConfig.LOG_AUDIT) {
          log.warn(String.format("[AUDIT] Itemservice couldnt add all items on buy: %d %d %d %d",
              new Object[] { Integer.valueOf(player.getObjectId()),
                  Integer.valueOf(tradeItem.getItemTemplate().getTemplateId()), Long.valueOf(tradeItem.getCount()),
                  Long.valueOf(tradeItem.getCount()) }));
        }
        player.getCommonData().addAp(-tradeList.getRequiredAp());
        return false;
      }
    }

    player.getCommonData().addAp(-tradeList.getRequiredAp());
    Map<Integer, Integer> requiredItems = tradeList.getRequiredItems();
    for (Integer itemId : requiredItems.keySet()) {
      ItemService.decreaseItemCountByItemId(player, itemId.intValue(),
          ((Integer) requiredItems.get(itemId)).intValue());
    }

    return true;
  }

  private static boolean validateBuyItems(TradeList tradeList, Player player) {
    Npc npc = (Npc) World.getInstance().findAionObject(tradeList.getSellerObjId());
    TradeListTemplate tradeListTemplate = tradeListData.getTradeListTemplate(npc.getObjectTemplate().getTemplateId());

    Set<Integer> allowedItems = new HashSet<Integer>();
    for (TradeListTemplate.TradeTab tradeTab : tradeListTemplate.getTradeTablist()) {

      GoodsList goodsList = goodsListData.getGoodsListById(tradeTab.getId());
      if (goodsList != null && goodsList.getItemIdList() != null) {
        allowedItems.addAll(goodsList.getItemIdList());
      }
    }

    for (TradeItem tradeItem : tradeList.getTradeItems()) {

      if (tradeItem.getCount() < 1L) {

        if (OptionsConfig.LOG_AUDIT)
          log.warn("[AUDIT] Player: " + player.getName() + " posible client hack. Trade count < 1");
        return false;
      }

      if (tradeItem.getItemTemplate().getMaxStackCount() < tradeItem.getCount()) {

        if (OptionsConfig.LOG_AUDIT)
          log.warn("[AUDIT] Player: " + player.getName() + " posible client hack. item count > MaxStackCount");
        return false;
      }

      if (!allowedItems.contains(Integer.valueOf(tradeItem.getItemId()))) {

        if (OptionsConfig.LOG_AUDIT)
          log.warn("[AUDIT] Player: " + player.getName() + " posible client hack. Tade item not in GoodsList");
        return false;
      }
    }
    return true;
  }

  public static boolean performSellToShop(Player player, TradeList tradeList) {
    Storage inventory = player.getInventory();

    long kinahReward = 0L;
    for (TradeItem tradeItem : tradeList.getTradeItems()) {

      Item item = inventory.getItemByObjId(tradeItem.getItemId());

      if (item == null) {
        return false;
      }
      if (item.getItemCount() - tradeItem.getCount() < 0L) {

        if (OptionsConfig.LOG_AUDIT)
          log.warn("[AUDIT] Trade exploit, sell item count big: " + player.getName());
        return false;
      }
      if (ItemService.decreaseItemCount(player, item, tradeItem.getCount()) == 0L) {

        kinahReward += item.getItemTemplate().getPrice() * tradeItem.getCount();
      }
    }

    kinahReward = player.getPrices().getKinahForSell(kinahReward);
    ItemService.increaseKinah(player, kinahReward);

    return true;
  }

  public static TradeListData getTradeListData() {
    return tradeListData;
  }
}
