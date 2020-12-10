/*     */ package quest.eltnen;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.TeleportService;
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
/*     */ public class _1482ATeleportationAdventure
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1482;
/*     */   
/*     */   public _1482ATeleportationAdventure() {
/*  39 */     super(Integer.valueOf(1482));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  45 */     this.qe.setNpcQuestData(203919).addOnQuestStart(1482);
/*  46 */     this.qe.setNpcQuestData(203919).addOnTalkEvent(1482);
/*  47 */     this.qe.setNpcQuestData(203337).addOnTalkEvent(1482);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  53 */     Player player = env.getPlayer();
/*  54 */     QuestState qs = player.getQuestStateList().getQuestState(1482);
/*     */     
/*  56 */     int targetId = 0;
/*  57 */     if (env.getVisibleObject() instanceof Npc) {
/*  58 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  60 */     if (qs == null || qs.getStatus() == QuestStatus.NONE)
/*     */     {
/*  62 */       if (targetId == 203919) {
/*     */         
/*  64 */         if (env.getDialogId().intValue() == 25)
/*     */         {
/*  66 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4762);
/*     */         }
/*     */         
/*  69 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */     }
/*  72 */     if (qs == null) {
/*  73 */       return false;
/*     */     }
/*  75 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       long itemCount1;
/*  77 */       switch (targetId) {
/*     */ 
/*     */         
/*     */         case 203337:
/*  81 */           switch (env.getDialogId().intValue()) {
/*     */ 
/*     */             
/*     */             case 25:
/*  85 */               switch (qs.getQuestVarById(0)) {
/*     */ 
/*     */                 
/*     */                 case 0:
/*  89 */                   return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */ 
/*     */                 
/*     */                 case 1:
/*  93 */                   itemCount1 = player.getInventory().getItemCountByItemId(182201399);
/*  94 */                   if (itemCount1 >= 3L) {
/*     */                     
/*  96 */                     qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  97 */                     updateQuestStatus(player, qs);
/*  98 */                     return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
/*     */                   } 
/*     */                   
/* 101 */                   return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
/*     */ 
/*     */                 
/*     */                 case 2:
/* 105 */                   return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
/*     */ 
/*     */                 
/*     */                 case 3:
/* 109 */                   return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002);
/*     */               } 
/*     */             
/*     */             
/*     */             
/*     */             case 10000:
/* 115 */               qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 116 */               updateQuestStatus(player, qs);
/* 117 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */               
/* 119 */               return true;
/*     */ 
/*     */             
/*     */             case 10002:
/* 123 */               qs.setQuestVar(3);
/* 124 */               qs.setStatus(QuestStatus.REWARD);
/* 125 */               updateQuestStatus(player, qs);
/* 126 */               TeleportService.teleportTo(player, 220020000, 1, 638.0F, 2337.0F, 425.0F, (byte)20, 0);
/* 127 */               return true;
/*     */           } 
/*     */           
/* 130 */           return defaultQuestStartDialog(env);
/*     */       } 
/*     */ 
/*     */ 
/*     */     
/* 135 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 137 */       if (targetId == 203337) {
/*     */         
/* 139 */         if (env.getDialogId().intValue() == 1009) {
/* 140 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */         }
/* 142 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 145 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1482ATeleportationAdventure.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */