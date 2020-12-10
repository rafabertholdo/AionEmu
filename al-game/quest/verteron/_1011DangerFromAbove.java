/*     */ package quest.verteron;
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
/*     */ public class _1011DangerFromAbove
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1011;
/*     */   
/*     */   public _1011DangerFromAbove() {
/*  40 */     super(Integer.valueOf(1011));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  46 */     int[] talkNpcs = { 203109, 203122, 203109 };
/*  47 */     this.qe.setNpcQuestData(700091).addOnKillEvent(1011);
/*  48 */     this.qe.addQuestLvlUp(1011);
/*  49 */     for (int id : talkNpcs) {
/*  50 */       this.qe.setNpcQuestData(id).addOnTalkEvent(1011);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/*  56 */     Player player = env.getPlayer();
/*  57 */     QuestState qs = player.getQuestStateList().getQuestState(1011);
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
/*  68 */     if (targetId == 700091) {
/*     */       
/*  70 */       if (var > 0 && var < 4) {
/*     */         
/*  72 */         qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  73 */         updateQuestStatus(player, qs);
/*  74 */         return true;
/*     */       } 
/*  76 */       if (var == 4) {
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
/*  91 */     QuestState qs = player.getQuestStateList().getQuestState(1011);
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
/* 102 */       if (targetId == 203109) {
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
/* 120 */       } else if (targetId == 203122) {
/*     */         
/* 122 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/* 125 */             if (var == 1)
/* 126 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */           case 1353:
/* 128 */             if (var == 1)
/* 129 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 24)); 
/* 130 */             return false;
/*     */           case 10001:
/* 132 */             if (var == 1) {
/*     */               
/* 134 */               qs.setQuestVarById(0, var + 1);
/* 135 */               updateQuestStatus(player, qs);
/* 136 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */               
/* 138 */               return true;
/*     */             } 
/*     */             break;
/*     */         } 
/*     */       
/*     */       } 
/* 144 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 146 */       if (targetId == 203109) {
/*     */         
/* 148 */         if (env.getDialogId().intValue() == -1)
/* 149 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 150 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 153 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/* 159 */     Player player = env.getPlayer();
/* 160 */     QuestState qs = player.getQuestStateList().getQuestState(1011);
/* 161 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED)
/* 162 */       return false; 
/* 163 */     QuestState qs2 = player.getQuestStateList().getQuestState(1130);
/* 164 */     if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE) {
/* 165 */       return false;
/*     */     }
/* 167 */     qs.setStatus(QuestStatus.START);
/* 168 */     updateQuestStatus(player, qs);
/* 169 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\verteron\_1011DangerFromAbove.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */