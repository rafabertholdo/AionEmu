/*    */ package com.aionemu.gameserver.model.gameobjects.stats;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*    */ import com.aionemu.gameserver.model.gameobjects.Summon;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SUMMON_UPDATE;
/*    */ import com.aionemu.gameserver.services.LifeStatsRestoreService;
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
/*    */ 
/*    */ 
/*    */ public class SummonLifeStats
/*    */   extends CreatureLifeStats<Summon>
/*    */ {
/*    */   public SummonLifeStats(Summon owner) {
/* 36 */     super((Creature)owner, owner.getGameStats().getCurrentStat(StatEnum.MAXHP), owner.getGameStats().getCurrentStat(StatEnum.MAXMP));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onIncreaseHp(SM_ATTACK_STATUS.TYPE type, int value) {
/* 43 */     Player player = getOwner().getMaster();
/* 44 */     sendAttackStatusPacketUpdate(type, value);
/*    */     
/* 46 */     if (player instanceof Player)
/*    */     {
/* 48 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SUMMON_UPDATE(getOwner()));
/*    */     }
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
/*    */   public Summon getOwner() {
/* 73 */     return (Summon)super.getOwner();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void triggerRestoreTask() {
/* 79 */     if (this.lifeRestoreTask == null && !this.alreadyDead)
/*    */     {
/* 81 */       this.lifeRestoreTask = LifeStatsRestoreService.getInstance().scheduleHpRestoreTask(this);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\stats\SummonLifeStats.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */