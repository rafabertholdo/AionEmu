/*     */ package quest.altgard;
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
/*     */ public class _2223AMythicalMonster
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2223;
/*     */   
/*     */   public _2223AMythicalMonster() {
/*  43 */     super(Integer.valueOf(2223));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  49 */     this.qe.setNpcQuestData(203616).addOnQuestStart(2223);
/*  50 */     this.qe.setNpcQuestData(203616).addOnTalkEvent(2223);
/*  51 */     this.qe.setNpcQuestData(211621).addOnKillEvent(2223);
/*  52 */     this.deletebleItems = new int[] { 182203217 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  58 */     Player player = env.getPlayer();
/*  59 */     int targetId = 0;
/*  60 */     if (env.getVisibleObject() instanceof Npc)
/*  61 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  62 */     QuestState qs = player.getQuestStateList().getQuestState(2223);
/*     */     
/*  64 */     if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */       
/*  66 */       if (targetId == 203616)
/*     */       {
/*  68 */         if (env.getDialogId().intValue() == 25) {
/*  69 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  71 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  74 */     } else if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  76 */       int var = qs.getQuestVarById(0);
/*  77 */       switch (targetId) {
/*     */         
/*     */         case 203620:
/*  80 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/*  83 */               if (var == 0)
/*  84 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */               break;
/*     */             case 10000:
/*  87 */               if (var == 0) {
/*     */                 
/*  89 */                 if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182203217, 1))))
/*  90 */                   return true; 
/*  91 */                 qs.setQuestVarById(0, var + 1);
/*  92 */                 updateQuestStatus(player, qs);
/*  93 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  94 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */       } 
/*  99 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 101 */       if (targetId == 203616)
/* 102 */         return defaultQuestEndDialog(env); 
/*     */     } 
/* 104 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 110 */     Player player = env.getPlayer();
/* 111 */     QuestState qs = player.getQuestStateList().getQuestState(2223);
/* 112 */     if (qs == null || qs.getStatus() != QuestStatus.START) {
/* 113 */       return false;
/*     */     }
/*     */     
/* 116 */     int targetId = 0;
/* 117 */     int var = 0;
/* 118 */     if (env.getVisibleObject() instanceof Npc)
/* 119 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/* 120 */     switch (targetId) {
/*     */       
/*     */       case 211621:
/* 123 */         var = qs.getQuestVarById(0);
/* 124 */         if (var == 1) {
/*     */           
/* 126 */           qs.setStatus(QuestStatus.REWARD);
/* 127 */           updateQuestStatus(player, qs);
/*     */         } 
/*     */         break;
/*     */     } 
/* 131 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\altgard\_2223AMythicalMonster.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */