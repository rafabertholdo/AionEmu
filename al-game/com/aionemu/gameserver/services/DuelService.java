/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.gameserver.model.DuelResult;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.RequestResponseHandler;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DUEL;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.skillengine.model.SkillTargetSlot;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.world.World;
/*     */ import javolution.util.FastMap;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DuelService
/*     */ {
/*  40 */   private static Logger log = Logger.getLogger(DuelService.class);
/*     */   
/*     */   private FastMap<Integer, Integer> duels;
/*     */ 
/*     */   
/*     */   public static final DuelService getInstance() {
/*  46 */     return SingletonHolder.instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DuelService() {
/*  55 */     this.duels = new FastMap();
/*  56 */     log.info("DuelService started.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDuelRequest(Player requester, Player responder) {
/*  73 */     if (requester.isEnemyPlayer(responder) || isDueling(requester.getObjectId())) {
/*     */       return;
/*     */     }
/*  76 */     RequestResponseHandler rrh = new RequestResponseHandler((Creature)requester)
/*     */       {
/*     */         public void denyRequest(Creature requester, Player responder)
/*     */         {
/*  80 */           DuelService.this.rejectDuelRequest((Player)requester, responder);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public void acceptRequest(Creature requester, Player responder) {
/*  86 */           DuelService.this.startDuel((Player)requester, responder);
/*     */         }
/*     */       };
/*  89 */     responder.getResponseRequester().putRequest(50028, rrh);
/*  90 */     PacketSendUtility.sendPacket(responder, (AionServerPacket)new SM_QUESTION_WINDOW(50028, 0, new Object[] { requester.getName() }));
/*     */     
/*  92 */     PacketSendUtility.sendPacket(responder, (AionServerPacket)SM_SYSTEM_MESSAGE.DUEL_ASKED_BY(requester.getName()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void confirmDuelWith(Player requester, Player responder) {
/* 108 */     if (requester.isEnemyPlayer(responder)) {
/*     */       return;
/*     */     }
/* 111 */     RequestResponseHandler rrh = new RequestResponseHandler((Creature)responder)
/*     */       {
/*     */         public void denyRequest(Creature requester, Player responder)
/*     */         {
/* 115 */           DuelService.log.debug("[Duel] Player " + responder.getName() + " confirmed his duel with " + requester.getName());
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public void acceptRequest(Creature requester, Player responder) {
/* 121 */           DuelService.this.cancelDuelRequest(responder, (Player)requester);
/*     */         }
/*     */       };
/* 124 */     requester.getResponseRequester().putRequest(50030, rrh);
/* 125 */     PacketSendUtility.sendPacket(requester, (AionServerPacket)new SM_QUESTION_WINDOW(50030, 0, new Object[] { responder.getName() }));
/*     */     
/* 127 */     PacketSendUtility.sendPacket(requester, (AionServerPacket)SM_SYSTEM_MESSAGE.DUEL_ASKED_TO(responder.getName()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void rejectDuelRequest(Player requester, Player responder) {
/* 140 */     log.debug("[Duel] Player " + responder.getName() + " rejected duel request from " + requester.getName());
/* 141 */     PacketSendUtility.sendPacket(requester, (AionServerPacket)SM_SYSTEM_MESSAGE.DUEL_REJECTED_BY(responder.getName()));
/* 142 */     PacketSendUtility.sendPacket(responder, (AionServerPacket)SM_SYSTEM_MESSAGE.DUEL_REJECT_DUEL_OF(requester.getName()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void cancelDuelRequest(Player owner, Player target) {
/* 154 */     log.debug("[Duel] Player " + owner.getName() + " cancelled his duel request with " + target.getName());
/* 155 */     PacketSendUtility.sendPacket(target, (AionServerPacket)SM_SYSTEM_MESSAGE.DUEL_CANCEL_DUEL_BY(owner.getName()));
/* 156 */     PacketSendUtility.sendPacket(owner, (AionServerPacket)SM_SYSTEM_MESSAGE.DUEL_CANCEL_DUEL_WITH(target.getName()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void startDuel(Player requester, Player responder) {
/* 169 */     PacketSendUtility.sendPacket(requester, (AionServerPacket)SM_DUEL.SM_DUEL_STARTED(responder.getObjectId()));
/* 170 */     PacketSendUtility.sendPacket(responder, (AionServerPacket)SM_DUEL.SM_DUEL_STARTED(requester.getObjectId()));
/* 171 */     createDuel(requester.getObjectId(), responder.getObjectId());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loseDuel(Player player) {
/* 181 */     if (!isDueling(player.getObjectId())) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 188 */     player.getEffectController().removeAbnormalEffectsByTargetSlot(SkillTargetSlot.DEBUFF);
/* 189 */     player.getController().cancelCurrentSkill();
/*     */     
/* 191 */     int opponnentId = ((Integer)this.duels.get(Integer.valueOf(player.getObjectId()))).intValue();
/* 192 */     Player opponent = World.getInstance().findPlayer(opponnentId);
/*     */     
/* 194 */     if (opponent != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 200 */       opponent.getEffectController().removeAbnormalEffectsByTargetSlot(SkillTargetSlot.DEBUFF);
/* 201 */       opponent.getController().cancelCurrentSkill();
/*     */       
/* 203 */       PacketSendUtility.sendPacket(opponent, (AionServerPacket)SM_DUEL.SM_DUEL_RESULT(DuelResult.DUEL_WON, player.getName()));
/* 204 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_DUEL.SM_DUEL_RESULT(DuelResult.DUEL_LOST, opponent.getName()));
/*     */     }
/*     */     else {
/*     */       
/* 208 */       log.warn("CHECKPOINT : duel opponent is already out of world");
/*     */     } 
/*     */     
/* 211 */     removeDuel(player.getObjectId(), opponnentId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDie(Player player) {
/* 220 */     loseDuel(player);
/* 221 */     player.getLifeStats().setCurrentHp(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDueling(int playerObjId) {
/* 230 */     return (this.duels.containsKey(Integer.valueOf(playerObjId)) && this.duels.containsValue(Integer.valueOf(playerObjId)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDueling(int playerObjId, int targetObjId) {
/* 240 */     return (this.duels.containsKey(Integer.valueOf(playerObjId)) && ((Integer)this.duels.get(Integer.valueOf(playerObjId))).intValue() == targetObjId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createDuel(int requesterObjId, int responderObjId) {
/* 249 */     this.duels.put(Integer.valueOf(requesterObjId), Integer.valueOf(responderObjId));
/* 250 */     this.duels.put(Integer.valueOf(responderObjId), Integer.valueOf(requesterObjId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void removeDuel(int requesterObjId, int responderObjId) {
/* 259 */     this.duels.remove(Integer.valueOf(requesterObjId));
/* 260 */     this.duels.remove(Integer.valueOf(responderObjId));
/*     */   }
/*     */ 
/*     */   
/*     */   private static class SingletonHolder
/*     */   {
/* 266 */     protected static final DuelService instance = new DuelService();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\DuelService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */