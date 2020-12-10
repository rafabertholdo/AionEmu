/*     */ package quest.poeta;
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
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
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
/*     */ public class _1001TheKerubThreat
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1001;
/*     */   
/*     */   public _1001TheKerubThreat() {
/*  40 */     super(Integer.valueOf(1001));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  46 */     this.qe.setNpcQuestData(210670).addOnKillEvent(1001);
/*  47 */     this.qe.setNpcQuestData(203071).addOnTalkEvent(1001);
/*  48 */     this.qe.setNpcQuestData(203067).addOnTalkEvent(1001);
/*  49 */     this.qe.addQuestLvlUp(1001);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/*  55 */     Player player = env.getPlayer();
/*  56 */     QuestState qs = player.getQuestStateList().getQuestState(1001);
/*  57 */     if (qs == null) {
/*  58 */       return false;
/*     */     }
/*  60 */     int var = qs.getQuestVarById(0);
/*  61 */     int targetId = 0;
/*  62 */     if (env.getVisibleObject() instanceof Npc) {
/*  63 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  65 */     if (qs.getStatus() != QuestStatus.START)
/*  66 */       return false; 
/*  67 */     if (targetId == 210670)
/*     */     {
/*  69 */       if (var > 0 && var < 6) {
/*     */         
/*  71 */         qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  72 */         updateQuestStatus(player, qs);
/*  73 */         return true;
/*     */       } 
/*     */     }
/*  76 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  82 */     Player player = env.getPlayer();
/*  83 */     QuestState qs = player.getQuestStateList().getQuestState(1001);
/*  84 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED)
/*  85 */       return false; 
/*  86 */     qs.setStatus(QuestStatus.START);
/*  87 */     updateQuestStatus(player, qs);
/*  88 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  94 */     Player player = env.getPlayer();
/*  95 */     QuestState qs = player.getQuestStateList().getQuestState(1001);
/*  96 */     if (qs == null) {
/*  97 */       return false;
/*     */     }
/*  99 */     int var = qs.getQuestVarById(0);
/* 100 */     int targetId = 0;
/* 101 */     if (env.getVisibleObject() instanceof Npc) {
/* 102 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 104 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/* 106 */       if (targetId == 203071)
/*     */       {
/* 108 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 1012:
/* 111 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 15));
/* 112 */             return false;
/*     */           case 25:
/* 114 */             if (var == 0)
/* 115 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/* 116 */             if (var == 6)
/* 117 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 118 */             if (var == 7)
/* 119 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 120 */             return false;
/*     */           case 33:
/*     */           case 10002:
/* 123 */             if (var == 7) {
/*     */               
/* 125 */               long itemCount = player.getInventory().getItemCountByItemId(182200001);
/* 126 */               if (itemCount >= 5L) {
/*     */                 
/* 128 */                 if (env.getDialogId().intValue() == 33)
/*     */                 {
/* 130 */                   return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1694);
/*     */                 }
/*     */ 
/*     */                 
/* 134 */                 ItemService.removeItemFromInventoryByItemId(player, 182200001);
/* 135 */                 qs.setQuestVarById(0, var + 1);
/* 136 */                 qs.setStatus(QuestStatus.REWARD);
/* 137 */                 updateQuestStatus(player, qs);
/* 138 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 139 */                 return true;
/*     */               } 
/*     */ 
/*     */               
/* 143 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1779);
/*     */             } 
/* 145 */             return true;
/*     */           case 10000:
/*     */           case 10001:
/* 148 */             if (var == 0 || var == 6) {
/*     */               
/* 150 */               qs.setQuestVarById(0, var + 1);
/* 151 */               updateQuestStatus(player, qs);
/* 152 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */             } 
/* 154 */             return true;
/*     */         } 
/* 156 */         return false;
/*     */       }
/*     */     
/*     */     }
/* 160 */     else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 162 */       if (targetId == 203067)
/*     */       {
/* 164 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     } 
/* 167 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\poeta\_1001TheKerubThreat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */