package com.aionemu.gameserver.controllers;

import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.model.ChatType;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.RequestResponseHandler;
import com.aionemu.gameserver.model.templates.BindPointTemplate;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LEVEL_UPDATE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MESSAGE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.TeleportService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.WorldType;
import org.apache.log4j.Logger;

public class BindpointController extends NpcController {
  private static Logger log = Logger.getLogger(BindpointController.class);

  private BindPointTemplate bindPointTemplate;

  public void setBindPointTemplate(BindPointTemplate bindPointTemplate) {
    this.bindPointTemplate = bindPointTemplate;
  }

  private void bindHere(Player player) {
    RequestResponseHandler responseHandler = new RequestResponseHandler((Creature) getOwner()) {

      public void acceptRequest(Creature requester, Player responder) {
        if (responder.getCommonData().getBindPoint() != BindpointController.this.bindPointTemplate.getBindId()) {
          if (ItemService.decreaseKinah(responder, BindpointController.this.bindPointTemplate.getPrice())) {

            responder.getCommonData().setBindPoint(BindpointController.this.bindPointTemplate.getBindId());
            TeleportService.sendSetBindPoint(responder);
            PacketSendUtility.broadcastPacket(responder, (AionServerPacket) new SM_LEVEL_UPDATE(responder.getObjectId(),
                2, responder.getCommonData().getLevel()), true);
            PacketSendUtility.sendPacket(responder,
                (AionServerPacket) SM_SYSTEM_MESSAGE.STR_DEATH_REGISTER_RESURRECT_POINT());
          } else {

            PacketSendUtility.sendPacket(responder,
                (AionServerPacket) SM_SYSTEM_MESSAGE.STR_CANNOT_REGISTER_RESURRECT_POINT_NOT_ENOUGH_FEE());
            return;
          }
        }
      }

      public void denyRequest(Creature requester, Player responder) {
      }
    };
    boolean requested = player.getResponseRequester().putRequest(160012, responseHandler);
    if (requested) {

      String price = Integer.toString(this.bindPointTemplate.getPrice());
      PacketSendUtility.sendPacket(player,
          (AionServerPacket) new SM_QUESTION_WINDOW(160012, 0, new Object[] { price }));
    }
  }

  public void onDialogRequest(Player player) {
    if (this.bindPointTemplate == null) {

      log.info("There is no bind point template for npc: " + getOwner().getNpcId());

      return;
    }
    if (player.getCommonData().getBindPoint() == this.bindPointTemplate.getBindId()) {

      PacketSendUtility.sendPacket(player,
          (AionServerPacket) SM_SYSTEM_MESSAGE.STR_ALREADY_REGISTER_THIS_RESURRECT_POINT());

      return;
    }
    WorldType worldType = World.getInstance().getWorldMap(player.getWorldId()).getWorldType();
    if (!CustomConfig.ENABLE_CROSS_FACTION_BINDING) {

      if (worldType == WorldType.ASMODAE && player.getCommonData().getRace() == Race.ELYOS) {

        PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_MESSAGE(0, null,
            "Elyos cannot bind in Asmodian territory.", ChatType.ANNOUNCEMENTS));

        return;
      }
      if (worldType == WorldType.ELYSEA && player.getCommonData().getRace() == Race.ASMODIANS) {

        PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_MESSAGE(0, null,
            "Asmodians cannot bind in Elyos territory.", ChatType.ANNOUNCEMENTS));
        return;
      }
      if (worldType == WorldType.ABYSS) {

        if (player.getCommonData().getRace() == Race.ELYOS
            && player.getTarget().getObjectTemplate().getTemplateId() == 700401) {

          PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_MESSAGE(0, null,
              "Elyos cannot bind in Asmodian territory.", ChatType.ANNOUNCEMENTS));
          return;
        }
        if (player.getCommonData().getRace() == Race.ASMODIANS
            && player.getTarget().getObjectTemplate().getTemplateId() == 730071) {

          PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_MESSAGE(0, null,
              "Asmodians cannot bind in Elyos territory.", ChatType.ANNOUNCEMENTS));
          return;
        }
      }
    }
    if (worldType == WorldType.PRISON) {

      PacketSendUtility.sendPacket(player,
          (AionServerPacket) new SM_MESSAGE(0, null, "You cannot bind here.", ChatType.ANNOUNCEMENTS));

      return;
    }

    bindHere(player);
  }
}
