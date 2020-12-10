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
/*     */ 
/*     */ public class _2288PutYourMoneyWhereYourMouthIs
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2288;
/*     */   
/*     */   public _2288PutYourMoneyWhereYourMouthIs() {
/*  39 */     super(Integer.valueOf(2288));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  45 */     this.qe.setNpcQuestData(203621).addOnQuestStart(2288);
/*  46 */     this.qe.setNpcQuestData(203621).addOnTalkEvent(2288);
/*  47 */     this.qe.setNpcQuestData(210564).addOnKillEvent(2288);
/*  48 */     this.qe.setNpcQuestData(210584).addOnKillEvent(2288);
/*  49 */     this.qe.setNpcQuestData(210581).addOnKillEvent(2288);
/*  50 */     this.qe.setNpcQuestData(201047).addOnKillEvent(2288);
/*  51 */     this.qe.setNpcQuestData(210436).addOnKillEvent(2288);
/*  52 */     this.qe.setNpcQuestData(210437).addOnKillEvent(2288);
/*  53 */     this.qe.setNpcQuestData(210440).addOnKillEvent(2288);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/*  59 */     Player player = env.getPlayer();
/*  60 */     QuestState qs = player.getQuestStateList().getQuestState(2288);
/*  61 */     if (qs == null) {
/*  62 */       return false;
/*     */     }
/*  64 */     int var = qs.getQuestVarById(0);
/*  65 */     int targetId = 0;
/*  66 */     if (env.getVisibleObject() instanceof Npc) {
/*  67 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  69 */     if (qs.getStatus() != QuestStatus.START)
/*  70 */       return false; 
/*  71 */     if (targetId == 210564 || targetId == 210584 || targetId == 210581 || targetId == 210436 || targetId == 201047 || targetId == 210437 || targetId == 210440) {
/*     */       
/*  73 */       if (var > 0 && var < 4) {
/*     */         
/*  75 */         qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  76 */         updateQuestStatus(player, qs);
/*  77 */         return true;
/*     */       } 
/*  79 */       if (var == 6) {
/*     */         
/*  81 */         qs.setStatus(QuestStatus.REWARD);
/*  82 */         updateQuestStatus(player, qs);
/*  83 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  84 */         return true;
/*     */       } 
/*     */     } 
/*  87 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  92 */     Player player = env.getPlayer();
/*  93 */     int targetId = 0;
/*  94 */     if (env.getVisibleObject() instanceof Npc)
/*  95 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  96 */     QuestState qs = player.getQuestStateList().getQuestState(2288);
/*  97 */     if (targetId == 203621) {
/*     */       
/*  99 */       if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */         
/* 101 */         if (env.getDialogId().intValue() == 25) {
/* 102 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/* 104 */         return defaultQuestStartDialog(env);
/*     */       } 
/* 106 */       if (qs.getStatus() == QuestStatus.START) {
/*     */         
/* 108 */         if (env.getDialogId().intValue() == 25 && qs.getQuestVarById(0) == 4) {
/*     */           
/* 110 */           qs.setStatus(QuestStatus.REWARD);
/* 111 */           updateQuestStatus(player, qs);
/* 112 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
/*     */         } 
/* 114 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/* 116 */           qs.setQuestVarById(0, 1);
/* 117 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 0);
/*     */         } 
/*     */         
/* 120 */         return defaultQuestStartDialog(env);
/*     */       } 
/* 122 */       if (qs.getStatus() == QuestStatus.REWARD)
/*     */       {
/* 124 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     } 
/* 127 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\altgard\_2288PutYourMoneyWhereYourMouthIs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */