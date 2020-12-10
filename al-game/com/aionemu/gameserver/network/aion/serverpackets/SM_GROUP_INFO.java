/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.group.LootDistribution;
/*    */ import com.aionemu.gameserver.model.group.LootGroupRules;
/*    */ import com.aionemu.gameserver.model.group.LootRuleType;
/*    */ import com.aionemu.gameserver.model.group.PlayerGroup;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import java.nio.ByteBuffer;
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
/*    */ public class SM_GROUP_INFO
/*    */   extends AionServerPacket
/*    */ {
/*    */   private int groupid;
/*    */   private int leaderid;
/*    */   private LootRuleType lootruletype;
/*    */   private LootDistribution autodistribution;
/*    */   private int common_item_above;
/*    */   private int superior_item_above;
/*    */   private int heroic_item_above;
/*    */   private int fabled_item_above;
/*    */   private int ethernal_item_above;
/*    */   private int over_ethernal;
/*    */   private int over_over_ethernal;
/*    */   
/*    */   public SM_GROUP_INFO(PlayerGroup group) {
/* 50 */     this.groupid = group.getGroupId();
/* 51 */     this.leaderid = group.getGroupLeader().getObjectId();
/*    */     
/* 53 */     LootGroupRules lootRules = group.getLootGroupRules();
/* 54 */     this.lootruletype = lootRules.getLootRule();
/* 55 */     this.autodistribution = lootRules.getAutodistribution();
/* 56 */     this.common_item_above = lootRules.getCommonItemAbove();
/* 57 */     this.superior_item_above = lootRules.getSuperiorItemAbove();
/* 58 */     this.heroic_item_above = lootRules.getHeroicItemAbove();
/* 59 */     this.fabled_item_above = lootRules.getFabledItemAbove();
/* 60 */     this.ethernal_item_above = lootRules.getEthernalItemAbove();
/* 61 */     this.over_ethernal = lootRules.getOverEthernal();
/* 62 */     this.over_over_ethernal = lootRules.getOverOverEthernal();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 68 */     writeD(buf, this.groupid);
/* 69 */     writeD(buf, this.leaderid);
/* 70 */     writeD(buf, this.lootruletype.getId());
/* 71 */     writeD(buf, this.autodistribution.getId());
/* 72 */     writeD(buf, this.common_item_above);
/* 73 */     writeD(buf, this.superior_item_above);
/* 74 */     writeD(buf, this.heroic_item_above);
/* 75 */     writeD(buf, this.fabled_item_above);
/* 76 */     writeD(buf, this.ethernal_item_above);
/* 77 */     writeD(buf, this.over_ethernal);
/* 78 */     writeD(buf, this.over_over_ethernal);
/* 79 */     writeD(buf, 0);
/* 80 */     writeH(buf, 0);
/* 81 */     writeC(buf, 0);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_GROUP_INFO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */