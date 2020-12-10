/*     */ package quest.altgard;
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
/*     */ public class _2011FungusAmongUs
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2011;
/*     */   
/*     */   public _2011FungusAmongUs() {
/*  40 */     super(Integer.valueOf(2011));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  46 */     int[] talkNpcs = { 203558, 203572, 203558 };
/*  47 */     this.qe.setNpcQuestData(700092).addOnKillEvent(2011);
/*  48 */     this.qe.addQuestLvlUp(2011);
/*  49 */     for (int id : talkNpcs) {
/*  50 */       this.qe.setNpcQuestData(id).addOnTalkEvent(2011);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/*  56 */     Player player = env.getPlayer();
/*  57 */     QuestState qs = player.getQuestStateList().getQuestState(2011);
/*  58 */     if (qs == null) {
/*  59 */       return false;
/*     */     }
/*  61 */     int var = qs.getQuestVarById(0);
/*  62 */     int targetId = 0;
/*  63 */     if (env.getVisibleObject() instanceof Npc) {
/*  64 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  66 */     if (qs.getStatus() != QuestStatus.START)
/*  67 */       return false; 
/*  68 */     if (targetId == 700092) {
/*     */       
/*  70 */       if (var > 0 && var < 6) {
/*     */         
/*  72 */         qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  73 */         updateQuestStatus(player, qs);
/*  74 */         return true;
/*     */       } 
/*  76 */       if (var == 6) {
/*     */         
/*  78 */         qs.setStatus(QuestStatus.REWARD);
/*  79 */         updateQuestStatus(player, qs);
/*  80 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  81 */         return true;
/*     */       } 
/*     */     } 
/*  84 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  90 */     Player player = env.getPlayer();
/*  91 */     QuestState qs = player.getQuestStateList().getQuestState(2011);
/*  92 */     if (qs == null) {
/*  93 */       return false;
/*     */     }
/*  95 */     int var = qs.getQuestVarById(0);
/*  96 */     int targetId = 0;
/*  97 */     if (env.getVisibleObject() instanceof Npc) {
/*  98 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 100 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/* 102 */       if (targetId == 203558) {
/*     */         
/* 104 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/* 107 */             if (var == 0)
/* 108 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*     */           case 10000:
/* 110 */             if (var == 0) {
/*     */               
/* 112 */               qs.setQuestVarById(0, var + 1);
/* 113 */               updateQuestStatus(player, qs);
/* 114 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */               
/* 116 */               return true;
/*     */             } 
/*     */             break;
/*     */         } 
/* 120 */       } else if (targetId == 203572) {
/*     */         
/* 122 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/* 125 */             if (var == 1)
/* 126 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */           case 1352:
/* 128 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 60));
/*     */             break;
/*     */           case 10001:
/* 131 */             if (var == 1) {
/*     */               
/* 133 */               qs.setQuestVarById(0, var + 1);
/* 134 */               updateQuestStatus(player, qs);
/* 135 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */               
/* 137 */               return true;
/*     */             } 
/*     */             break;
/*     */         } 
/*     */       
/*     */       } 
/* 143 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 145 */       if (targetId == 203558)
/*     */       {
/* 147 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     } 
/* 150 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/* 156 */     Player player = env.getPlayer();
/* 157 */     QuestState qs = player.getQuestStateList().getQuestState(2011);
/* 158 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED)
/* 159 */       return false; 
/* 160 */     QuestState qs2 = player.getQuestStateList().getQuestState(2200);
/* 161 */     if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE) {
/* 162 */       return false;
/*     */     }
/* 164 */     qs.setStatus(QuestStatus.START);
/* 165 */     updateQuestStatus(player, qs);
/* 166 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\altgard\_2011FungusAmongUs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */