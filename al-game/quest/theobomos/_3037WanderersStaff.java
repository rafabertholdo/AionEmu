/*     */ package quest.theobomos;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class _3037WanderersStaff
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 3037;
/*     */   
/*     */   public _3037WanderersStaff() {
/*  21 */     super(Integer.valueOf(3037));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  27 */     this.qe.setNpcQuestData(798166).addOnQuestStart(3037);
/*  28 */     this.qe.setNpcQuestData(798166).addOnTalkEvent(3037);
/*  29 */     this.qe.setNpcQuestData(798199).addOnTalkEvent(3037);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  35 */     Player player = env.getPlayer();
/*  36 */     int targetId = 0;
/*  37 */     if (env.getVisibleObject() instanceof Npc)
/*  38 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  39 */     QuestState qs = player.getQuestStateList().getQuestState(3037);
/*  40 */     if (targetId == 798166) {
/*     */       
/*  42 */       if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */         
/*  44 */         if (env.getDialogId().intValue() == 25) {
/*  45 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  47 */         return defaultQuestStartDialog(env);
/*     */       } 
/*  49 */       if (qs != null && qs.getStatus() == QuestStatus.START) {
/*     */         
/*  51 */         if (env.getDialogId().intValue() == 25)
/*  52 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*  53 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/*  55 */           qs.setStatus(QuestStatus.REWARD);
/*  56 */           updateQuestStatus(player, qs);
/*  57 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  58 */           return true;
/*     */         } 
/*     */         
/*  61 */         return defaultQuestEndDialog(env);
/*     */       } 
/*  63 */       if (qs != null && qs.getStatus() == QuestStatus.REWARD)
/*     */       {
/*  65 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     }
/*  68 */     else if (targetId == 798199) {
/*     */       
/*  70 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0)
/*     */       {
/*  72 */         if (env.getDialogId().intValue() == 25)
/*  73 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  74 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/*  76 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  77 */           updateQuestStatus(player, qs);
/*  78 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  79 */           return true;
/*     */         } 
/*     */         
/*  82 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  85 */     } else if (targetId == 798166) {
/*     */       
/*  87 */       if (qs != null) {
/*     */         
/*  89 */         if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
/*  90 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*  91 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/*  93 */           qs.setQuestVar(3);
/*  94 */           qs.setStatus(QuestStatus.REWARD);
/*  95 */           updateQuestStatus(player, qs);
/*  96 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/*  99 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 102 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\theobomos\_3037WanderersStaff.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */