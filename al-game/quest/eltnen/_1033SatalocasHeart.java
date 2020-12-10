/*     */ package quest.eltnen;
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
/*     */ import com.aionemu.gameserver.services.QuestService;
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
/*     */ public class _1033SatalocasHeart
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1033;
/*  38 */   private static final int[] mob_ids = new int[] { 210799 };
/*     */ 
/*     */   
/*     */   public _1033SatalocasHeart() {
/*  42 */     super(Integer.valueOf(1033));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  48 */     this.qe.setNpcQuestData(203900).addOnTalkEvent(1033);
/*  49 */     this.qe.setNpcQuestData(203996).addOnTalkEvent(1033);
/*  50 */     for (int mob_id : mob_ids)
/*  51 */       this.qe.setNpcQuestData(mob_id).addOnKillEvent(1033); 
/*  52 */     this.qe.addQuestLvlUp(1033);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/*  58 */     Player player = env.getPlayer();
/*  59 */     QuestState qs = player.getQuestStateList().getQuestState(1033);
/*  60 */     if (qs == null || qs.getStatus() != QuestStatus.START) {
/*  61 */       return false;
/*     */     }
/*  63 */     int var = qs.getQuestVarById(0);
/*  64 */     int targetId = 0;
/*  65 */     if (env.getVisibleObject() instanceof Npc)
/*  66 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  67 */     switch (targetId) {
/*     */       
/*     */       case 210799:
/*  70 */         if (var >= 10 && var < 11) {
/*     */           
/*  72 */           qs.setQuestVarById(0, var + 1);
/*  73 */           updateQuestStatus(player, qs);
/*  74 */           return true;
/*     */         } 
/*     */         
/*  77 */         if (var == 11) {
/*     */           
/*  79 */           qs.setQuestVar(11);
/*  80 */           updateQuestStatus(player, qs);
/*  81 */           return true;
/*     */         }  break;
/*     */     } 
/*  84 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  90 */     Player player = env.getPlayer();
/*  91 */     QuestState qs = player.getQuestStateList().getQuestState(1033);
/*  92 */     boolean lvlCheck = QuestService.checkLevelRequirement(1033, player.getCommonData().getLevel());
/*  93 */     if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
/*  94 */       return false; 
/*  95 */     qs.setStatus(QuestStatus.START);
/*  96 */     updateQuestStatus(player, qs);
/*  97 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/* 103 */     Player player = env.getPlayer();
/* 104 */     int targetId = 0;
/* 105 */     if (env.getVisibleObject() instanceof Npc)
/* 106 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/* 107 */     QuestState qs = player.getQuestStateList().getQuestState(1033);
/* 108 */     if (qs == null)
/* 109 */       return false; 
/* 110 */     if (targetId == 203900) {
/*     */       
/* 112 */       if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0) {
/*     */         
/* 114 */         if (env.getDialogId().intValue() == 25)
/* 115 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/* 116 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/* 118 */           qs.setQuestVar(1);
/* 119 */           updateQuestStatus(player, qs);
/* 120 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 122 */           return true;
/*     */         } 
/*     */         
/* 125 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */       
/* 128 */       if (qs.getStatus() == QuestStatus.REWARD)
/*     */       {
/* 130 */         return defaultQuestEndDialog(env);
/*     */       
/*     */       }
/*     */     }
/* 134 */     else if (targetId == 203996) {
/*     */       
/* 136 */       if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1) {
/*     */         
/* 138 */         if (env.getDialogId().intValue() == 25)
/* 139 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 140 */         if (env.getDialogId().intValue() == 10002) {
/*     */           
/* 142 */           qs.setQuestVar(10);
/* 143 */           updateQuestStatus(player, qs);
/* 144 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 146 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 42));
/* 147 */           return true;
/*     */         } 
/*     */         
/* 150 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */       
/* 153 */       if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 11) {
/*     */         
/* 155 */         if (env.getDialogId().intValue() == 25) {
/*     */           
/* 157 */           qs.setQuestVar(qs.getQuestVarById(0) + 1);
/* 158 */           qs.setStatus(QuestStatus.REWARD);
/* 159 */           updateQuestStatus(player, qs);
/* 160 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2205);
/*     */         } 
/*     */         
/* 163 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */     } 
/*     */     
/* 167 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1033SatalocasHeart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */