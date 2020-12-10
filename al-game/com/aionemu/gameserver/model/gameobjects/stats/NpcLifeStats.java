/*    */ package com.aionemu.gameserver.model.gameobjects.stats;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*    */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
/*    */ import com.aionemu.gameserver.services.LifeStatsRestoreService;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NpcLifeStats
/*    */   extends CreatureLifeStats<Npc>
/*    */ {
/*    */   public NpcLifeStats(Npc owner) {
/* 35 */     super((Creature)owner, owner.getGameStats().getCurrentStat(StatEnum.MAXHP), owner.getGameStats().getCurrentStat(StatEnum.MAXMP));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onIncreaseHp(SM_ATTACK_STATUS.TYPE type, int value) {
/* 42 */     sendAttackStatusPacketUpdate(type, value);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onIncreaseMp(SM_ATTACK_STATUS.TYPE type, int value) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onReduceHp() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onReduceMp() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void triggerRestoreTask() {
/* 66 */     if (this.lifeRestoreTask == null && !this.alreadyDead)
/*    */     {
/* 68 */       this.lifeRestoreTask = LifeStatsRestoreService.getInstance().scheduleHpRestoreTask(this);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\stats\NpcLifeStats.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */