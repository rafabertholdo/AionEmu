/*     */ package quest.heiron;
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
/*     */ public class _1609MessageToArbolusHaven
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1609;
/*     */   
/*     */   public _1609MessageToArbolusHaven() {
/*  37 */     super(Integer.valueOf(1609));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  43 */     this.qe.setNpcQuestData(204574).addOnQuestStart(1609);
/*  44 */     this.qe.setNpcQuestData(204574).addOnTalkEvent(1609);
/*  45 */     this.qe.setNpcQuestData(204557).addOnTalkEvent(1609);
/*  46 */     this.qe.setNpcQuestData(730024).addOnTalkEvent(1609);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  52 */     Player player = env.getPlayer();
/*  53 */     int targetId = 0;
/*  54 */     if (env.getVisibleObject() instanceof Npc)
/*  55 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  56 */     QuestState qs = player.getQuestStateList().getQuestState(1609);
/*  57 */     if (targetId == 204574) {
/*     */       
/*  59 */       if (qs == null || qs.getStatus() == QuestStatus.NONE)
/*     */       {
/*  61 */         if (env.getDialogId().intValue() == 25) {
/*  62 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  64 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  67 */     } else if (targetId == 204557) {
/*     */       
/*  69 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0)
/*     */       {
/*  71 */         if (env.getDialogId().intValue() == 25)
/*  72 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  73 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/*  75 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  76 */           updateQuestStatus(player, qs);
/*  77 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/*  79 */           return true;
/*     */         } 
/*     */         
/*  82 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  85 */     } else if (targetId == 730024) {
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


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\heiron\_1609MessageToArbolusHaven.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */