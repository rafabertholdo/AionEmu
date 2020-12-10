package com.aionemu.gameserver.services;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.RequestResponseHandler;
import com.aionemu.gameserver.model.templates.CubeExpandTemplate;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_CUBE_UPDATE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import org.apache.log4j.Logger;

public class CubeExpandService {
  private static final Logger log = Logger.getLogger(CubeExpandService.class);

  private static final int MIN_EXPAND = 0;

  private static final int MAX_EXPAND = 9;

  public static void expandCube(final Player player, Npc npc) {
    CubeExpandTemplate expandTemplate = DataManager.CUBEEXPANDER_DATA.getCubeExpandListTemplate(npc.getNpcId());

    if (expandTemplate == null) {

      log.error("Cube Expand Template could not be found for Npc ID: " + npc.getObjectId());

      return;
    }
    if (npcCanExpandLevel(expandTemplate, player.getCubeSize() + 1) && validateNewSize(player.getCubeSize() + 1)) {

      final int price = getPriceByLevel(expandTemplate, player.getCubeSize() + 1);

      RequestResponseHandler responseHandler = new RequestResponseHandler((Creature) npc) {
        public void acceptRequest(Creature requester, Player responder) {
          if (!ItemService.decreaseKinah(responder, price)) {

            PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.CUBEEXPAND_NOT_ENOUGH_KINAH);
            return;
          }
          CubeExpandService.expand(responder);
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

  public static void expand(Player player) {
    if (!validateNewSize(player.getCubeSize() + 1))
      return;
    PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_SYSTEM_MESSAGE(1300431, new Object[] { "9" }));
    player.setCubesize(player.getCubeSize() + 1);
    PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_CUBE_UPDATE(player, 0));
  }

  private static boolean validateNewSize(int level) {
    if (level < 0 || level > 9)
      return false;
    return true;
  }

  private static boolean npcCanExpandLevel(CubeExpandTemplate clist, int level) {
    if (!clist.contains(level))
      return false;
    return true;
  }

  private static int getPriceByLevel(CubeExpandTemplate clist, int level) {
    return clist.get(level).getPrice();
  }
}
