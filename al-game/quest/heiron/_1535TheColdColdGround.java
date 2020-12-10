/*     */ package quest.heiron;
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
/*     */ public class _1535TheColdColdGround
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1535;
/*     */   
/*     */   public _1535TheColdColdGround() {
/*  43 */     super(Integer.valueOf(1535));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  49 */     this.qe.setNpcQuestData(204580).addOnQuestStart(1535);
/*  50 */     this.qe.setNpcQuestData(204580).addOnTalkEvent(1535);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  56 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  62 */     Player player = env.getPlayer();
/*  63 */     int targetId = 0;
/*  64 */     if (env.getVisibleObject() instanceof Npc) {
/*  65 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  67 */     if (targetId != 204580) {
/*  68 */       return false;
/*     */     }
/*  70 */     QuestState qs = player.getQuestStateList().getQuestState(1535);
/*  71 */     if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */       
/*  73 */       if (env.getDialogId().intValue() == 25) {
/*  74 */         return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4762);
/*     */       }
/*  76 */       return defaultQuestStartDialog(env);
/*     */     } 
/*     */     
/*  79 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  81 */       boolean abexSkins = (player.getInventory().getItemCountByItemId(182201818) > 4L);
/*  82 */       boolean worgSkins = (player.getInventory().getItemCountByItemId(182201819) > 2L);
/*  83 */       boolean karnifSkins = (player.getInventory().getItemCountByItemId(182201820) > 0L);
/*     */       
/*  85 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case -1:
/*     */         case 25:
/*  89 */           if (abexSkins || worgSkins || karnifSkins)
/*  90 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */         case 10000:
/*  92 */           if (abexSkins) {
/*     */             
/*  94 */             qs.setQuestVarById(0, 1);
/*  95 */             qs.setStatus(QuestStatus.REWARD);
/*  96 */             updateQuestStatus(player, qs);
/*  97 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */           } 
/*     */           break;
/*     */         case 10001:
/* 101 */           if (worgSkins) {
/*     */             
/* 103 */             qs.setQuestVarById(0, 2);
/* 104 */             qs.setStatus(QuestStatus.REWARD);
/* 105 */             updateQuestStatus(player, qs);
/* 106 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 6);
/*     */           } 
/*     */           break;
/*     */         case 10002:
/* 110 */           if (karnifSkins) {
/*     */             
/* 112 */             qs.setQuestVarById(0, 3);
/* 113 */             qs.setStatus(QuestStatus.REWARD);
/* 114 */             updateQuestStatus(player, qs);
/* 115 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 7);
/*     */           } 
/*     */           break;
/*     */       } 
/* 119 */       return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
/*     */     } 
/* 121 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 123 */       int var = qs.getQuestVarById(0);
/* 124 */       if (var == 1) {
/*     */         
/* 126 */         ItemService.removeItemFromInventoryByItemId(player, 182201818);
/* 127 */         return defaultQuestEndDialog(env);
/*     */       } 
/* 129 */       if (var == 2) {
/*     */ 
/*     */         
/* 132 */         if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(162000010, 5)))) {
/*     */ 
/*     */           
/* 135 */           qs.setStatus(QuestStatus.START);
/* 136 */           updateQuestStatus(player, qs);
/*     */         }
/*     */         else {
/*     */           
/* 140 */           ItemService.removeItemFromInventoryByItemId(player, 182201819);
/*     */         } 
/* 142 */         defaultQuestEndDialog(env);
/* 143 */         return true;
/*     */       } 
/* 145 */       if (var == 3) {
/*     */ 
/*     */         
/* 148 */         if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(162000015, 5)))) {
/*     */ 
/*     */           
/* 151 */           qs.setStatus(QuestStatus.START);
/* 152 */           updateQuestStatus(player, qs);
/*     */         }
/*     */         else {
/*     */           
/* 156 */           ItemService.removeItemFromInventoryByItemId(player, 182201820);
/*     */         } 
/* 158 */         defaultQuestEndDialog(env);
/* 159 */         return true;
/*     */       } 
/* 161 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */     } 
/* 163 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\heiron\_1535TheColdColdGround.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */