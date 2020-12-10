/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.dataholders.DataManager;
/*    */ import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SHOW_NPC_ON_MAP;
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
/*    */ public class CM_OBJECT_SEARCH
/*    */   extends AionClientPacket
/*    */ {
/*    */   private int npcId;
/*    */   
/*    */   public CM_OBJECT_SEARCH(int opcode) {
/* 38 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 48 */     this.npcId = readD();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 57 */     SpawnTemplate spawnTemplate = DataManager.SPAWNS_DATA.getFirstSpawnByNpcId(this.npcId);
/* 58 */     if (spawnTemplate != null)
/*    */     {
/* 60 */       sendPacket((AionServerPacket)new SM_SHOW_NPC_ON_MAP(this.npcId, spawnTemplate.getWorldId(), spawnTemplate.getX(), spawnTemplate.getY(), spawnTemplate.getZ()));
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_OBJECT_SEARCH.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */