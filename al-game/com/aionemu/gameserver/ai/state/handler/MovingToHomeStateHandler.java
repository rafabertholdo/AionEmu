/*    */ package com.aionemu.gameserver.ai.state.handler;
/*    */ 
/*    */ import com.aionemu.gameserver.ai.AI;
/*    */ import com.aionemu.gameserver.ai.desires.Desire;
/*    */ import com.aionemu.gameserver.ai.desires.impl.MoveToHomeDesire;
/*    */ import com.aionemu.gameserver.ai.state.AIState;
/*    */ import com.aionemu.gameserver.model.EmotionType;
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*    */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*    */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_LOOKATOBJECT;
/*    */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MovingToHomeStateHandler
/*    */   extends StateHandler
/*    */ {
/*    */   public AIState getState() {
/* 37 */     return AIState.MOVINGTOHOME;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void handleState(AIState state, AI<?> ai) {
/* 48 */     ai.clearDesires();
/* 49 */     Npc npc = (Npc)ai.getOwner();
/* 50 */     npc.setTarget(null);
/* 51 */     PacketSendUtility.broadcastPacket((VisibleObject)npc, (AionServerPacket)new SM_LOOKATOBJECT((VisibleObject)npc));
/* 52 */     npc.getAggroList().clear();
/* 53 */     PacketSendUtility.broadcastPacket((VisibleObject)npc, (AionServerPacket)new SM_EMOTION((Creature)npc, EmotionType.START_EMOTE2, 0, 0));
/* 54 */     PacketSendUtility.broadcastPacket((VisibleObject)npc, (AionServerPacket)new SM_EMOTION((Creature)npc, EmotionType.NEUTRALMODE, 0, 0));
/* 55 */     ai.addDesire((Desire)new MoveToHomeDesire(npc, AIState.MOVINGTOHOME.getPriority()));
/*    */     
/* 57 */     ai.schedule();
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\state\handler\MovingToHomeStateHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */