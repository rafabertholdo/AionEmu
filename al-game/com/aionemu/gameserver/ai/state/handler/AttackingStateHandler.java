/*    */ package com.aionemu.gameserver.ai.state.handler;
/*    */ 
/*    */ import com.aionemu.gameserver.ai.AI;
/*    */ import com.aionemu.gameserver.ai.desires.Desire;
/*    */ import com.aionemu.gameserver.ai.desires.impl.AttackDesire;
/*    */ import com.aionemu.gameserver.ai.desires.impl.MoveToTargetDesire;
/*    */ import com.aionemu.gameserver.ai.desires.impl.SkillUseDesire;
/*    */ import com.aionemu.gameserver.ai.state.AIState;
/*    */ import com.aionemu.gameserver.model.EmotionType;
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*    */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*    */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*    */ import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
/*    */ import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
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
/*    */ 
/*    */ public class AttackingStateHandler
/*    */   extends StateHandler
/*    */ {
/*    */   public AIState getState() {
/* 42 */     return AIState.ATTACKING;
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
/* 53 */     ai.clearDesires();
/*    */     
/* 55 */     Creature target = ((Npc)ai.getOwner()).getAggroList().getMostHated();
/* 56 */     if (target == null) {
/*    */       return;
/*    */     }
/* 59 */     Npc owner = (Npc)ai.getOwner();
/* 60 */     owner.setTarget((VisibleObject)target);
/* 61 */     PacketSendUtility.broadcastPacket((VisibleObject)owner, (AionServerPacket)new SM_LOOKATOBJECT((VisibleObject)owner));
/*    */     
/* 63 */     owner.setState(CreatureState.WEAPON_EQUIPPED);
/* 64 */     PacketSendUtility.broadcastPacket((VisibleObject)owner, (AionServerPacket)new SM_EMOTION((Creature)owner, EmotionType.START_EMOTE2, 0, target.getObjectId()));
/*    */     
/* 66 */     PacketSendUtility.broadcastPacket((VisibleObject)owner, (AionServerPacket)new SM_EMOTION((Creature)owner, EmotionType.ATTACKMODE, 0, target.getObjectId()));
/*    */ 
/*    */     
/* 69 */     owner.getMoveController().setSpeed(owner.getGameStats().getCurrentStat(StatEnum.SPEED) / 1000.0F);
/* 70 */     owner.getMoveController().setDistance(owner.getGameStats().getCurrentStat(StatEnum.ATTACK_RANGE) / 1000.0F);
/*    */     
/* 72 */     if (owner.getNpcSkillList() != null)
/* 73 */       ai.addDesire((Desire)new SkillUseDesire((Creature)owner, AIState.USESKILL.getPriority())); 
/* 74 */     ai.addDesire((Desire)new AttackDesire(owner, target, AIState.ATTACKING.getPriority()));
/* 75 */     if (owner.getGameStats().getCurrentStat(StatEnum.SPEED) != 0) {
/* 76 */       ai.addDesire((Desire)new MoveToTargetDesire(owner, target, AIState.ATTACKING.getPriority()));
/*    */     }
/* 78 */     ai.schedule();
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\state\handler\AttackingStateHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */