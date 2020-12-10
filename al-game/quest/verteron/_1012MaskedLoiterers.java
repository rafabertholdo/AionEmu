/*     */ package quest.verteron;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
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
/*     */ public class _1012MaskedLoiterers
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1012;
/*     */   
/*     */   public _1012MaskedLoiterers() {
/*  41 */     super(Integer.valueOf(1012));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  47 */     this.qe.setNpcQuestData(203111).addOnTalkEvent(1012);
/*  48 */     this.qe.setQuestEnterZone(ZoneName.Q1012).add(1012);
/*  49 */     this.qe.addQuestLvlUp(1012);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  55 */     Player player = env.getPlayer();
/*  56 */     QuestState qs = player.getQuestStateList().getQuestState(1012);
/*  57 */     boolean lvlCheck = QuestService.checkLevelRequirement(1012, player.getCommonData().getLevel());
/*  58 */     if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
/*  59 */       return false; 
/*  60 */     qs.setStatus(QuestStatus.START);
/*  61 */     updateQuestStatus(player, qs);
/*  62 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  68 */     Player player = env.getPlayer();
/*  69 */     QuestState qs = player.getQuestStateList().getQuestState(1012);
/*  70 */     if (qs == null) {
/*  71 */       return false;
/*     */     }
/*  73 */     int var = qs.getQuestVarById(0);
/*  74 */     int targetId = 0;
/*  75 */     if (env.getVisibleObject() instanceof Npc) {
/*  76 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  78 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  80 */       if (targetId == 203111)
/*     */       {
/*  82 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/*  85 */             if (var == 0)
/*  86 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*     */           case 10000:
/*  88 */             if (var == 0 || var == 0) {
/*     */               
/*  90 */               qs.setQuestVarById(0, var + 1);
/*  91 */               updateQuestStatus(player, qs);
/*  92 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */               
/*  94 */               return true;
/*     */             } 
/*  96 */             if (var == 2)
/*  97 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */           case 10001:
/*  99 */             if (var == 2 || var == 2) {
/*     */               
/* 101 */               qs.setQuestVarById(0, var + 1);
/* 102 */               updateQuestStatus(player, qs);
/* 103 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */               
/* 105 */               return true;
/*     */             } 
/* 107 */             if (var == 3) {
/*     */               
/* 109 */               long itemCount = player.getInventory().getItemCountByItemId(182200010);
/* 110 */               if (itemCount >= 5L) {
/*     */                 
/* 112 */                 if (env.getDialogId().intValue() == 33)
/*     */                 {
/* 114 */                   return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1694);
/*     */                 }
/*     */ 
/*     */                 
/* 118 */                 ItemService.removeItemFromInventoryByItemId(player, 182200010);
/* 119 */                 qs.setQuestVarById(0, var + 1);
/* 120 */                 qs.setStatus(QuestStatus.REWARD);
/* 121 */                 updateQuestStatus(player, qs);
/* 122 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */                 
/* 124 */                 return true;
/*     */               } 
/*     */ 
/*     */               
/* 128 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1779);
/*     */             } 
/* 130 */             return true;
/*     */         } 
/*     */       
/*     */       }
/* 134 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 136 */       if (targetId == 203111)
/* 137 */         return defaultQuestEndDialog(env); 
/*     */     } 
/* 139 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
/* 145 */     if (zoneName != ZoneName.Q1012)
/* 146 */       return false; 
/* 147 */     Player player = env.getPlayer();
/* 148 */     QuestState qs = player.getQuestStateList().getQuestState(1012);
/* 149 */     if (qs == null || qs.getQuestVars().getQuestVars() != 1)
/* 150 */       return false; 
/* 151 */     env.setQuestId(Integer.valueOf(1012));
/* 152 */     qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 153 */     updateQuestStatus(player, qs);
/* 154 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\verteron\_1012MaskedLoiterers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */