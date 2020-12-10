/*     */ package quest.sanctum;
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
/*     */ public class _1988AMeetingwithaSage
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1988;
/*     */   
/*     */   public _1988AMeetingwithaSage() {
/*  39 */     super(Integer.valueOf(1988));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  45 */     this.qe.setNpcQuestData(203725).addOnQuestStart(1988);
/*  46 */     this.qe.setNpcQuestData(203989).addOnQuestStart(1988);
/*  47 */     this.qe.setNpcQuestData(798018).addOnQuestStart(1988);
/*  48 */     this.qe.setNpcQuestData(203771).addOnQuestStart(1988);
/*  49 */     this.qe.setNpcQuestData(203725).addOnTalkEvent(1988);
/*  50 */     this.qe.setNpcQuestData(203771).addOnTalkEvent(1988);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  56 */     Player player = env.getPlayer();
/*     */     
/*  58 */     int targetId = 0;
/*  59 */     if (env.getVisibleObject() instanceof Npc)
/*  60 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  61 */     QuestState qs = player.getQuestStateList().getQuestState(1988);
/*     */     
/*  63 */     if (qs == null || qs.getStatus() == QuestStatus.NONE)
/*     */     {
/*  65 */       if (targetId == 203725) {
/*     */         
/*  67 */         if (env.getDialogId().intValue() == 25) {
/*  68 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  70 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */     }
/*     */     
/*  74 */     if (qs == null) {
/*  75 */       return false;
/*     */     }
/*  77 */     int var = qs.getQuestVarById(0);
/*     */     
/*  79 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  81 */       if (targetId == 203989 && var == 0) {
/*     */         
/*  83 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/*  86 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
/*     */           case 10000:
/*  88 */             qs.setQuestVarById(0, var + 1);
/*  89 */             updateQuestStatus(player, qs);
/*  90 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  91 */             return true;
/*     */         } 
/*     */       
/*  94 */       } else if (targetId == 798018 && var == 1) {
/*     */         
/*  96 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/*  99 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
/*     */           case 10001:
/* 101 */             qs.setQuestVarById(0, var + 1);
/* 102 */             updateQuestStatus(player, qs);
/* 103 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 104 */             return true;
/*     */         } 
/*     */       
/* 107 */       } else if (targetId == 203771 && var == 2) {
/*     */         
/* 109 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/* 112 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
/*     */           case 2035:
/* 114 */             if (player.getInventory().getItemCountByItemId(186000039) == 1L) {
/*     */               
/* 116 */               qs.setStatus(QuestStatus.REWARD);
/* 117 */               updateQuestStatus(player, qs);
/* 118 */               ItemService.removeItemFromInventoryByItemId(player, 186000039);
/* 119 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2035);
/*     */             } 
/*     */             
/* 122 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2120);
/*     */         } 
/*     */       
/*     */       } 
/* 126 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 128 */       if (targetId == 203771)
/* 129 */         return defaultQuestEndDialog(env); 
/*     */     } 
/* 131 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\sanctum\_1988AMeetingwithaSage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */