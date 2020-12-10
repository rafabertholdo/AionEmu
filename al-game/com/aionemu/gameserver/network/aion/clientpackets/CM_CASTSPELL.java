/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.dataholders.DataManager;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.skillengine.model.SkillTemplate;
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
/*    */ public class CM_CASTSPELL
/*    */   extends AionClientPacket
/*    */ {
/*    */   private int spellid;
/*    */   private int targetType;
/*    */   private float x;
/*    */   private float y;
/*    */   private float z;
/*    */   private int targetObjectId;
/*    */   private int time;
/*    */   private int level;
/*    */   
/*    */   public CM_CASTSPELL(int opcode) {
/* 31 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 40 */     this.spellid = readH();
/* 41 */     this.level = readC();
/*    */     
/* 43 */     this.targetType = readC();
/*    */     
/* 45 */     switch (this.targetType) {
/*    */       
/*    */       case 0:
/* 48 */         this.targetObjectId = readD();
/*    */         break;
/*    */       case 1:
/* 51 */         this.x = readF();
/* 52 */         this.y = readF();
/* 53 */         this.z = readF();
/*    */         break;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 59 */     this.time = readH();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 68 */     Player player = ((AionConnection)getConnection()).getActivePlayer();
/*    */     
/* 70 */     if (player.isProtectionActive())
/*    */     {
/* 72 */       player.getController().stopProtectionActiveTask();
/*    */     }
/*    */     
/* 75 */     if (!player.getLifeStats().isAlreadyDead()) {
/*    */       
/* 77 */       SkillTemplate template = DataManager.SKILL_DATA.getSkillTemplate(this.spellid);
/* 78 */       if (template == null || template.isPassive())
/*    */         return; 
/* 80 */       if (!player.getSkillList().isSkillPresent(this.spellid))
/*    */         return; 
/* 82 */       player.getController().useSkill(this.spellid, this.targetType, this.x, this.y, this.z);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_CASTSPELL.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */