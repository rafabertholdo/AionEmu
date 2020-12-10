package com.aionemu.gameserver.services;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTIONNAIRE;
import com.aionemu.gameserver.utils.idfactory.IDFactory;
import com.aionemu.gameserver.world.World;
import org.apache.log4j.Logger;

public class HTMLService {
  private static final Logger log = Logger.getLogger(HTMLService.class);

  public static void pushSurvey(String html) {
    int messageId = IDFactory.getInstance().nextId();
    for (Player ply : World.getInstance().getAllPlayers()) {
      sendData(ply, messageId, html);
    }
  }

  public static void showHTML(Player player, String html) {
    sendData(player, IDFactory.getInstance().nextId(), html);
  }

  private static void sendData(Player player, int messageId, String html) {
    byte packet_count = (byte) (int) Math.ceil((html.length() / 32759 + 1));
    if (packet_count < 256) {
      byte i;
      for (i = 0; i < packet_count; i = (byte) (i + 1)) {

        try {
          int from = i * 32759, to = (i + 1) * 32759;
          if (from < 0)
            from = 0;
          if (to > html.length())
            to = html.length();
          String sub = html.substring(from, to);
          player.getClientConnection()
              .sendPacket((AionServerPacket) new SM_QUESTIONNAIRE(messageId, i, packet_count, sub));
        } catch (Exception e) {

          log.error(e);
        }
      }
    }
  }
}
