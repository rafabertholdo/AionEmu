package com.aionemu.gameserver.services;

import com.aionemu.gameserver.model.DuelResult;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.RequestResponseHandler;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DUEL;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.skillengine.model.SkillTargetSlot;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import javolution.util.FastMap;
import org.apache.log4j.Logger;

public class DuelService {
  private static Logger log = Logger.getLogger(DuelService.class);

  private FastMap<Integer, Integer> duels;

  public static final DuelService getInstance() {
    return SingletonHolder.instance;
  }

  private DuelService() {
    this.duels = new FastMap();
    log.info("DuelService started.");
  }

  public void onDuelRequest(Player requester, Player responder) {
    if (requester.isEnemyPlayer(responder) || isDueling(requester.getObjectId())) {
      return;
    }
    RequestResponseHandler rrh = new RequestResponseHandler((Creature) requester) {
      public void denyRequest(Creature requester, Player responder) {
        DuelService.this.rejectDuelRequest((Player) requester, responder);
      }

      public void acceptRequest(Creature requester, Player responder) {
        DuelService.this.startDuel((Player) requester, responder);
      }
    };
    responder.getResponseRequester().putRequest(50028, rrh);
    PacketSendUtility.sendPacket(responder,
        (AionServerPacket) new SM_QUESTION_WINDOW(50028, 0, new Object[] { requester.getName() }));

    PacketSendUtility.sendPacket(responder, (AionServerPacket) SM_SYSTEM_MESSAGE.DUEL_ASKED_BY(requester.getName()));
  }

  public void confirmDuelWith(Player requester, Player responder) {
    if (requester.isEnemyPlayer(responder)) {
      return;
    }
    RequestResponseHandler rrh = new RequestResponseHandler((Creature) responder) {
      public void denyRequest(Creature requester, Player responder) {
        DuelService.log
            .debug("[Duel] Player " + responder.getName() + " confirmed his duel with " + requester.getName());
      }

      public void acceptRequest(Creature requester, Player responder) {
        DuelService.this.cancelDuelRequest(responder, (Player) requester);
      }
    };
    requester.getResponseRequester().putRequest(50030, rrh);
    PacketSendUtility.sendPacket(requester,
        (AionServerPacket) new SM_QUESTION_WINDOW(50030, 0, new Object[] { responder.getName() }));

    PacketSendUtility.sendPacket(requester, (AionServerPacket) SM_SYSTEM_MESSAGE.DUEL_ASKED_TO(responder.getName()));
  }

  private void rejectDuelRequest(Player requester, Player responder) {
    log.debug("[Duel] Player " + responder.getName() + " rejected duel request from " + requester.getName());
    PacketSendUtility.sendPacket(requester, (AionServerPacket) SM_SYSTEM_MESSAGE.DUEL_REJECTED_BY(responder.getName()));
    PacketSendUtility.sendPacket(responder,
        (AionServerPacket) SM_SYSTEM_MESSAGE.DUEL_REJECT_DUEL_OF(requester.getName()));
  }

  private void cancelDuelRequest(Player owner, Player target) {
    log.debug("[Duel] Player " + owner.getName() + " cancelled his duel request with " + target.getName());
    PacketSendUtility.sendPacket(target, (AionServerPacket) SM_SYSTEM_MESSAGE.DUEL_CANCEL_DUEL_BY(owner.getName()));
    PacketSendUtility.sendPacket(owner, (AionServerPacket) SM_SYSTEM_MESSAGE.DUEL_CANCEL_DUEL_WITH(target.getName()));
  }

  private void startDuel(Player requester, Player responder) {
    PacketSendUtility.sendPacket(requester, (AionServerPacket) SM_DUEL.SM_DUEL_STARTED(responder.getObjectId()));
    PacketSendUtility.sendPacket(responder, (AionServerPacket) SM_DUEL.SM_DUEL_STARTED(requester.getObjectId()));
    createDuel(requester.getObjectId(), responder.getObjectId());
  }

  public void loseDuel(Player player) {
    if (!isDueling(player.getObjectId())) {
      return;
    }

    player.getEffectController().removeAbnormalEffectsByTargetSlot(SkillTargetSlot.DEBUFF);
    player.getController().cancelCurrentSkill();

    int opponnentId = ((Integer) this.duels.get(Integer.valueOf(player.getObjectId()))).intValue();
    Player opponent = World.getInstance().findPlayer(opponnentId);

    if (opponent != null) {

      opponent.getEffectController().removeAbnormalEffectsByTargetSlot(SkillTargetSlot.DEBUFF);
      opponent.getController().cancelCurrentSkill();

      PacketSendUtility.sendPacket(opponent,
          (AionServerPacket) SM_DUEL.SM_DUEL_RESULT(DuelResult.DUEL_WON, player.getName()));
      PacketSendUtility.sendPacket(player,
          (AionServerPacket) SM_DUEL.SM_DUEL_RESULT(DuelResult.DUEL_LOST, opponent.getName()));
    } else {

      log.warn("CHECKPOINT : duel opponent is already out of world");
    }

    removeDuel(player.getObjectId(), opponnentId);
  }

  public void onDie(Player player) {
    loseDuel(player);
    player.getLifeStats().setCurrentHp(1);
  }

  public boolean isDueling(int playerObjId) {
    return (this.duels.containsKey(Integer.valueOf(playerObjId))
        && this.duels.containsValue(Integer.valueOf(playerObjId)));
  }

  public boolean isDueling(int playerObjId, int targetObjId) {
    return (this.duels.containsKey(Integer.valueOf(playerObjId))
        && ((Integer) this.duels.get(Integer.valueOf(playerObjId))).intValue() == targetObjId);
  }

  private void createDuel(int requesterObjId, int responderObjId) {
    this.duels.put(Integer.valueOf(requesterObjId), Integer.valueOf(responderObjId));
    this.duels.put(Integer.valueOf(responderObjId), Integer.valueOf(requesterObjId));
  }

  private void removeDuel(int requesterObjId, int responderObjId) {
    this.duels.remove(Integer.valueOf(requesterObjId));
    this.duels.remove(Integer.valueOf(responderObjId));
  }

  private static class SingletonHolder {
    protected static final DuelService instance = new DuelService();
  }
}
