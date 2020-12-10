/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.dataholders.DataManager;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.SkillListEntry;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SM_SKILL_LIST
/*    */   extends AionServerPacket
/*    */ {
/*    */   private SkillListEntry[] skillList;
/*    */   private int messageId;
/*    */   private int skillNameId;
/*    */   private String skillLvl;
/*    */   public static final int YOU_LEARNED_SKILL = 1300050;
/*    */   
/*    */   public SM_SKILL_LIST(Player player) {
/* 52 */     this.skillList = player.getSkillList().getAllSkills();
/* 53 */     this.messageId = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public SM_SKILL_LIST(SkillListEntry skillListEntry, int messageId) {
/* 58 */     this.skillList = new SkillListEntry[] { skillListEntry };
/* 59 */     this.messageId = messageId;
/* 60 */     this.skillNameId = DataManager.SKILL_DATA.getSkillTemplate(skillListEntry.getSkillId()).getNameId();
/* 61 */     this.skillLvl = String.valueOf(skillListEntry.getSkillLevel());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 71 */     int size = this.skillList.length;
/* 72 */     writeH(buf, size);
/*    */     
/* 74 */     if (size > 0)
/*    */     {
/* 76 */       for (SkillListEntry entry : this.skillList) {
/*    */         
/* 78 */         writeH(buf, entry.getSkillId());
/* 79 */         writeH(buf, entry.getSkillLevel());
/* 80 */         writeC(buf, 0);
/* 81 */         writeC(buf, entry.getExtraLvl());
/* 82 */         writeD(buf, 0);
/* 83 */         writeC(buf, entry.isStigma() ? 1 : 0);
/*    */       } 
/*    */     }
/* 86 */     writeD(buf, this.messageId);
/* 87 */     if (this.messageId != 0);
/*    */     
/* 89 */     writeH(buf, 36);
/* 90 */     writeD(buf, this.skillNameId);
/* 91 */     writeH(buf, 0);
/* 92 */     writeS(buf, this.skillLvl);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_SKILL_LIST.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */