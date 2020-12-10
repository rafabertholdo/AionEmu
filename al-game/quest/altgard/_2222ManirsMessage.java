/*     */ package quest.altgard;
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
/*     */ public class _2222ManirsMessage
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2222;
/*     */   
/*     */   public _2222ManirsMessage() {
/*  38 */     super(Integer.valueOf(2222));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  44 */     this.qe.setNpcQuestData(203607).addOnQuestStart(2222);
/*  45 */     this.qe.setNpcQuestData(203607).addOnTalkEvent(2222);
/*  46 */     this.qe.setNpcQuestData(203609).addOnTalkEvent(2222);
/*  47 */     this.qe.setNpcQuestData(203631).addOnTalkEvent(2222);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  53 */     Player player = env.getPlayer();
/*  54 */     int targetId = 0;
/*  55 */     if (env.getVisibleObject() instanceof Npc)
/*  56 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  57 */     QuestState qs = player.getQuestStateList().getQuestState(2222);
/*  58 */     if (targetId == 203607) {
/*     */       
/*  60 */       if (qs == null)
/*     */       {
/*  62 */         if (env.getDialogId().intValue() == 25) {
/*  63 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  65 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  68 */     } else if (targetId == 203609) {
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
/*  85 */     } else if (targetId == 203631) {
/*     */       
/*  87 */       if (qs != null) {
/*     */         
/*  89 */         if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
/*  90 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*  91 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/*  93 */           qs.setQuestVar(2);
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


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\altgard\_2222ManirsMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */