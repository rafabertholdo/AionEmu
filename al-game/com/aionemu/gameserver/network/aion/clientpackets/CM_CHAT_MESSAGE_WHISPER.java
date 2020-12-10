package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.configs.main.OptionsConfig;
import com.aionemu.gameserver.model.ChatType;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MESSAGE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.restrictions.RestrictionsManager;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.world.World;
import org.apache.log4j.Logger;

public class CM_CHAT_MESSAGE_WHISPER extends AionClientPacket {
  private static final Logger log = Logger.getLogger(CM_CHAT_MESSAGE_WHISPER.class);

  private String name;

  private String message;

  public CM_CHAT_MESSAGE_WHISPER(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.name = readS();
    this.message = readS();
  }

  protected void runImpl() {
    String formatname = Util.convertName(this.name);

    Player sender = ((AionConnection) getConnection()).getActivePlayer();
    Player receiver = World.getInstance().findPlayer(formatname);

    if (OptionsConfig.LOG_CHAT) {
      log.info(String.format("[MESSAGE] [%s] W: %s, Message: %s",
          new Object[] { sender.getName(), formatname, this.message }));
    }
    if (receiver == null) {

      sendPacket((AionServerPacket) SM_SYSTEM_MESSAGE.PLAYER_IS_OFFLINE(formatname));
    } else if (sender.getLevel() < CustomConfig.LEVEL_TO_WHISPER) {

      sendPacket((AionServerPacket) SM_SYSTEM_MESSAGE
          .LEVEL_NOT_ENOUGH_FOR_WHISPER(String.valueOf(CustomConfig.LEVEL_TO_WHISPER)));
    } else if (receiver.getBlockList().contains(sender.getObjectId())) {

      sendPacket((AionServerPacket) SM_SYSTEM_MESSAGE.YOU_ARE_BLOCKED_BY(receiver.getName()));

    } else if (RestrictionsManager.canChat(sender)) {
      PacketSendUtility.sendPacket(receiver, (AionServerPacket) new SM_MESSAGE(sender, this.message, ChatType.WHISPER));
    }
  }
}
