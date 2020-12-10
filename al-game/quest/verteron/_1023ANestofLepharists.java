/*     */ package quest.verteron;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.services.QuestService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.world.zone.ZoneName;
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
/*     */ public class _1023ANestofLepharists
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1023;
/*     */   
/*     */   public _1023ANestofLepharists() {
/*  42 */     super(Integer.valueOf(1023));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  48 */     this.qe.setNpcQuestData(203098).addOnTalkEvent(1023);
/*  49 */     this.qe.setNpcQuestData(203183).addOnTalkEvent(1023);
/*  50 */     this.qe.setQuestEnterZone(ZoneName.MYSTERIOUS_SHIPWRECK).add(1023);
/*  51 */     this.qe.addQuestLvlUp(1023);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  57 */     Player player = env.getPlayer();
/*  58 */     QuestState qs = player.getQuestStateList().getQuestState(1023);
/*  59 */     boolean lvlCheck = QuestService.checkLevelRequirement(1023, player.getCommonData().getLevel());
/*  60 */     if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
/*  61 */       return false; 
/*  62 */     qs.setStatus(QuestStatus.START);
/*  63 */     updateQuestStatus(player, qs);
/*  64 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  70 */     Player player = env.getPlayer();
/*  71 */     QuestState qs = player.getQuestStateList().getQuestState(1023);
/*  72 */     if (qs == null) {
/*  73 */       return false;
/*     */     }
/*  75 */     int var = qs.getQuestVarById(0);
/*  76 */     int targetId = 0;
/*  77 */     if (env.getVisibleObject() instanceof Npc) {
/*  78 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  80 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  82 */       if (targetId == 203098) {
/*     */         
/*  84 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/*  87 */             if (var == 0)
/*  88 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*     */           case 10000:
/*  90 */             if (var == 0) {
/*     */               
/*  92 */               qs.setQuestVarById(0, var + 1);
/*  93 */               updateQuestStatus(player, qs);
/*  94 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */               
/*  96 */               return true;
/*     */             } 
/*     */             break;
/*     */         } 
/* 100 */       } else if (targetId == 203183) {
/*     */         
/* 102 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/* 105 */             if (var == 1)
/* 106 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*     */           case 1012:
/* 108 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 30));
/*     */             break;
/*     */           case 10000:
/* 111 */             if (var == 1 || var == 1) {
/*     */               
/* 113 */               qs.setQuestVarById(0, var + 1);
/* 114 */               updateQuestStatus(player, qs);
/* 115 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */               
/* 117 */               return true;
/*     */             } 
/* 119 */             if (var == 3)
/* 120 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */           case 10001:
/* 122 */             if (var == 3 || var == 3) {
/*     */               
/* 124 */               qs.setQuestVarById(0, var + 1);
/* 125 */               updateQuestStatus(player, qs);
/* 126 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */               
/* 128 */               return true;
/*     */             } 
/* 130 */             if (var == 4) {
/*     */               
/* 132 */               long itemCount = player.getInventory().getItemCountByItemId(182200026);
/* 133 */               if (itemCount >= 1L) {
/*     */                 
/* 135 */                 if (env.getDialogId().intValue() == 33)
/*     */                 {
/* 137 */                   return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1694);
/*     */                 }
/*     */ 
/*     */                 
/* 141 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 23));
/* 142 */                 ItemService.removeItemFromInventoryByItemId(player, 182200010);
/* 143 */                 qs.setQuestVarById(0, var + 1);
/* 144 */                 qs.setStatus(QuestStatus.REWARD);
/* 145 */                 updateQuestStatus(player, qs);
/* 146 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */                 
/* 148 */                 return true;
/*     */               } 
/*     */ 
/*     */               
/* 152 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1779);
/*     */             } 
/* 154 */             return true;
/*     */         } 
/*     */       
/*     */       } 
/* 158 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 160 */       if (targetId == 203098)
/* 161 */         return defaultQuestEndDialog(env); 
/*     */     } 
/* 163 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
/* 169 */     if (zoneName != ZoneName.MYSTERIOUS_SHIPWRECK)
/* 170 */       return false; 
/* 171 */     Player player = env.getPlayer();
/* 172 */     QuestState qs = player.getQuestStateList().getQuestState(1023);
/* 173 */     if (qs == null || qs.getQuestVars().getQuestVars() != 2)
/* 174 */       return false; 
/* 175 */     env.setQuestId(Integer.valueOf(1023));
/* 176 */     qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 177 */     updateQuestStatus(player, qs);
/* 178 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\verteron\_1023ANestofLepharists.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */