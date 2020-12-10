package com.aionemu.gameserver.services;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.RequestResponseHandler;
import com.aionemu.gameserver.model.gameobjects.player.StorageType;
import com.aionemu.gameserver.model.templates.WarehouseExpandTemplate;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_WAREHOUSE_INFO;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.List;
import org.apache.log4j.Logger;

public class WarehouseService {
  private static final Logger log = Logger.getLogger(WarehouseService.class);

  private static final int MIN_EXPAND = 0;

  private static final int MAX_EXPAND = 10;

  public static void expandWarehouse(final Player player, Npc npc) {
    WarehouseExpandTemplate expandTemplate = DataManager.WAREHOUSEEXPANDER_DATA
        .getWarehouseExpandListTemplate(npc.getNpcId());

    if (expandTemplate == null) {

      log.error("Warehouse Expand Template could not be found for Npc ID: " + npc.getObjectId());

      return;
    }
    if (npcCanExpandLevel(expandTemplate, player.getWarehouseSize() + 1)
        && validateNewSize(player.getWarehouseSize() + 1)) {

      if (validateNewSize(player.getWarehouseSize() + 1)) {

        final int price = getPriceByLevel(expandTemplate, player.getWarehouseSize() + 1);
        RequestResponseHandler responseHandler = new RequestResponseHandler((Creature) npc) {
          public void acceptRequest(Creature requester, Player responder) {
            if (!ItemService.decreaseKinah(responder, price)) {

              PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_SYSTEM_MESSAGE(1300831, new Object[0]));
              return;
            }
            WarehouseService.expand(responder);
          }

          public void denyRequest(Creature requester, Player responder) {
          }
        };
        boolean result = player.getResponseRequester().putRequest(900686, responseHandler);
        if (result) {
          PacketSendUtility.sendPacket(player,
              (AionServerPacket) new SM_QUESTION_WINDOW(900686, 0, new Object[] { String.valueOf(price) }));
        }
      } else {

        PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_SYSTEM_MESSAGE(1300430, new Object[0]));
      }
    }
  }

  public static void expand(Player player) {
    if (!validateNewSize(player.getWarehouseSize() + 1))
      return;
    PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_SYSTEM_MESSAGE(1300433, new Object[] { "8" }));
    player.setWarehouseSize(player.getWarehouseSize() + 1);

    sendWarehouseInfo(player, false);
  }

  private static boolean validateNewSize(int level) {
    if (level < 0 || level > 10)
      return false;
    return true;
  }

  private static boolean npcCanExpandLevel(WarehouseExpandTemplate clist, int level) {
    if (!clist.contains(level))
      return false;
    return true;
  }

  private static int getPriceByLevel(WarehouseExpandTemplate clist, int level) {
    return clist.get(level).getPrice();
  }

  public static void sendWarehouseInfo(Player player, boolean sendAccountWh) {
    List<Item> items = player.getStorage(StorageType.REGULAR_WAREHOUSE.getId()).getStorageItems();

    int whSize = player.getWarehouseSize();
    int itemsSize = items.size();

    boolean firstPacket = true;
    if (itemsSize != 0) {

      int index = 0;

      while (index + 10 < itemsSize) {

        PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_WAREHOUSE_INFO(items.subList(index, index + 10),
            StorageType.REGULAR_WAREHOUSE.getId(), whSize, firstPacket));

        index += 10;
        firstPacket = false;
      }
      PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_WAREHOUSE_INFO(items.subList(index, itemsSize),
          StorageType.REGULAR_WAREHOUSE.getId(), whSize, firstPacket));
    }

    PacketSendUtility.sendPacket(player,
        (AionServerPacket) new SM_WAREHOUSE_INFO(null, StorageType.REGULAR_WAREHOUSE.getId(), whSize, false));

    if (sendAccountWh) {

      PacketSendUtility.sendPacket(player,
          (AionServerPacket) new SM_WAREHOUSE_INFO(
              player.getStorage(StorageType.ACCOUNT_WAREHOUSE.getId()).getAllItems(),
              StorageType.ACCOUNT_WAREHOUSE.getId(), 0, true));
    }

    PacketSendUtility.sendPacket(player,
        (AionServerPacket) new SM_WAREHOUSE_INFO(null, StorageType.ACCOUNT_WAREHOUSE.getId(), 0, false));
  }
}
