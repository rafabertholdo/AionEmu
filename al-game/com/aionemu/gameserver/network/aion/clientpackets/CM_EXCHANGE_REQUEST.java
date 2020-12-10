package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.DeniedStatus;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.RequestResponseHandler;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.SystemMessageId;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.ExchangeService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;

public class CM_EXCHANGE_REQUEST extends AionClientPacket {
  public Integer targetObjectId;

  public CM_EXCHANGE_REQUEST(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.targetObjectId = Integer.valueOf(readD());
  }

  protected void runImpl() {
    final Player activePlayer = ((AionConnection) getConnection()).getActivePlayer();
    final Player targetPlayer = World.getInstance().findPlayer(this.targetObjectId.intValue());

    if (activePlayer != targetPlayer) {

      if (targetPlayer != null) {

        if (targetPlayer.getPlayerSettings().isInDeniedStatus(DeniedStatus.TRADE)) {

          sendPacket((AionServerPacket) SM_SYSTEM_MESSAGE.STR_MSG_REJECTED_TRADE(targetPlayer.getName()));
          return;
        }
        sendPacket((AionServerPacket) SM_SYSTEM_MESSAGE.REQUEST_TRADE(targetPlayer.getName()));

        RequestResponseHandler responseHandler = new RequestResponseHandler((Creature) activePlayer) {
          public void acceptRequest(Creature requester, Player responder) {
            ExchangeService.getInstance().registerExchange(activePlayer, targetPlayer);
          }

          public void denyRequest(Creature requester, Player responder) {
            PacketSendUtility.sendPacket(activePlayer,
                (AionServerPacket) new SM_SYSTEM_MESSAGE(SystemMessageId.EXCHANGE_HE_REJECTED_EXCHANGE,
                    new Object[] { this.val$targetPlayer.getName() }));
          }
        };

        boolean requested = targetPlayer.getResponseRequester().putRequest(90001, responseHandler);
        if (requested) {
          PacketSendUtility.sendPacket(targetPlayer,
              (AionServerPacket) new SM_QUESTION_WINDOW(90001, 0, new Object[] { activePlayer.getName() }));
        }
      }
    }
  }
}
