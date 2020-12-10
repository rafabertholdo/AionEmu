package com.aionemu.gameserver.utils;

import com.aionemu.commons.objects.filter.ObjectFilter;
import com.aionemu.gameserver.model.ChatType;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.legion.Legion;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MESSAGE;

public class PacketSendUtility {
  public static void sendMessage(Player player, String msg) {
    sendPacket(player, (AionServerPacket) new SM_MESSAGE(0, null, msg, ChatType.ANNOUNCEMENTS));
  }

  public static void sendSysMessage(Player player, String msg) {
    sendPacket(player, (AionServerPacket) new SM_MESSAGE(0, null, msg, ChatType.SYSTEM_NOTICE));
  }

  public static void sendPacket(Player player, AionServerPacket packet) {
    if (player.getClientConnection() != null) {
      player.getClientConnection().sendPacket(packet);
    }
  }

  public static void broadcastPacket(Player player, AionServerPacket packet, boolean toSelf) {
    if (toSelf) {
      sendPacket(player, packet);
    }
    broadcastPacket((VisibleObject) player, packet);
  }

  public static void broadcastPacketAndReceive(VisibleObject visibleObject, AionServerPacket packet) {
    if (visibleObject instanceof Player) {
      sendPacket((Player) visibleObject, packet);
    }
    broadcastPacket(visibleObject, packet);
  }

  public static void broadcastPacket(VisibleObject visibleObject, AionServerPacket packet) {
    for (VisibleObject obj : visibleObject.getKnownList().getKnownObjects().values()) {

      if (obj instanceof Player) {
        sendPacket((Player) obj, packet);
      }
    }
  }

  public static void broadcastPacket(Player player, AionServerPacket packet, boolean toSelf,
      ObjectFilter<Player> filter) {
    if (toSelf) {
      sendPacket(player, packet);
    }

    for (VisibleObject obj : player.getKnownList().getKnownObjects().values()) {

      if (obj instanceof Player) {

        Player target = (Player) obj;
        if (filter.acceptObject(target)) {
          sendPacket(target, packet);
        }
      }
    }
  }

  public static void broadcastPacketToLegion(Legion legion, AionServerPacket packet) {
    for (Player onlineLegionMember : legion.getOnlineLegionMembers()) {
      sendPacket(onlineLegionMember, packet);
    }
  }

  public static void broadcastPacketToLegion(Legion legion, AionServerPacket packet, int playerObjId) {
    for (Player onlineLegionMember : legion.getOnlineLegionMembers()) {

      if (onlineLegionMember.getObjectId() != playerObjId)
        sendPacket(onlineLegionMember, packet);
    }
  }
}
