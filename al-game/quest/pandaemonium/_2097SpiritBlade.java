/*     */ package quest.pandaemonium;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.services.QuestService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import java.util.Collections;
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
/*     */ 
/*     */ 
/*     */ public class _2097SpiritBlade
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2097;
/*     */   
/*     */   public _2097SpiritBlade() {
/*  44 */     super(Integer.valueOf(2097));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  50 */     this.qe.addQuestLvlUp(2097);
/*  51 */     this.qe.setNpcQuestData(203550).addOnTalkEvent(2097);
/*  52 */     this.qe.setNpcQuestData(203546).addOnTalkEvent(2097);
/*  53 */     this.qe.setNpcQuestData(279034).addOnTalkEvent(2097);
/*  54 */     this.qe.setQuestItemIds(182207085).add(2097);
/*  55 */     this.deletebleItems = new int[] { 182207086, 182207087, 182207088 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  61 */     Player player = env.getPlayer();
/*  62 */     QuestState qs = player.getQuestStateList().getQuestState(2097);
/*  63 */     if (qs == null) {
/*  64 */       return false;
/*     */     }
/*  66 */     int var = qs.getQuestVars().getQuestVars();
/*  67 */     int targetId = 0;
/*  68 */     if (env.getVisibleObject() instanceof Npc) {
/*  69 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  71 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  73 */       switch (targetId) {
/*     */         
/*     */         case 203550:
/*  76 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/*  79 */               if (var == 0)
/*  80 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*     */             case 10000:
/*  82 */               if (var == 0) {
/*     */                 
/*  84 */                 qs.setQuestVarById(0, var + 1);
/*  85 */                 updateQuestStatus(player, qs);
/*  86 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  87 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         case 203546:
/*  92 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/*  95 */               if (var == 1)
/*  96 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  97 */               return true;
/*     */             case 10001:
/*  99 */               if (var == 1) {
/*     */                 
/* 101 */                 qs.setQuestVarById(0, var + 1);
/* 102 */                 updateQuestStatus(player, qs);
/* 103 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 104 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         case 279034:
/* 109 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 112 */               if (var == 2)
/* 113 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*     */             case 33:
/* 115 */               if (var == 2) {
/*     */                 
/* 117 */                 if (QuestService.collectItemCheck(env, true)) {
/*     */                   
/* 119 */                   qs.setStatus(QuestStatus.REWARD);
/* 120 */                   updateQuestStatus(player, qs);
/* 121 */                   ItemService.addItems(player, Collections.singletonList(new QuestItems(182207085, 1)));
/* 122 */                   PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 123 */                   return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10000);
/*     */                 } 
/* 125 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
/*     */               } 
/*     */               break;
/*     */           } 
/*     */           break;
/*     */       } 
/* 131 */     } else if (qs.getStatus() == QuestStatus.REWARD && targetId == 203550) {
/* 132 */       return defaultQuestEndDialog(env);
/* 133 */     }  return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/* 139 */     Player player = env.getPlayer();
/* 140 */     QuestState qs = player.getQuestStateList().getQuestState(2097);
/* 141 */     if (qs != null)
/* 142 */       return false; 
/* 143 */     boolean lvlCheck = QuestService.checkLevelRequirement(2097, player.getCommonData().getLevel());
/* 144 */     if (!lvlCheck)
/* 145 */       return false; 
/* 146 */     env.setQuestId(Integer.valueOf(2097));
/* 147 */     QuestService.startQuest(env, QuestStatus.START);
/* 148 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\pandaemonium\_2097SpiritBlade.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */