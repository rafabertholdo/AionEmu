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
/*     */ 
/*     */ 
/*     */ public class _2012Encroachers
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2012;
/*  39 */   private static final int[] mob_ids = new int[] { 210715 };
/*     */ 
/*     */   
/*     */   public _2012Encroachers() {
/*  43 */     super(Integer.valueOf(2012));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  49 */     this.qe.setNpcQuestData(203559).addOnTalkEvent(2012);
/*  50 */     this.qe.addQuestLvlUp(2012);
/*  51 */     for (int mob_id : mob_ids) {
/*  52 */       this.qe.setNpcQuestData(mob_id).addOnKillEvent(2012);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  59 */     Player player = env.getPlayer();
/*  60 */     QuestState qs = player.getQuestStateList().getQuestState(2012);
/*  61 */     boolean lvlCheck = QuestService.checkLevelRequirement(2012, player.getCommonData().getLevel());
/*  62 */     if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED) {
/*  63 */       return false;
/*     */     }
/*  65 */     qs.setStatus(QuestStatus.START);
/*  66 */     updateQuestStatus(player, qs);
/*  67 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  74 */     Player player = env.getPlayer();
/*  75 */     QuestState qs = player.getQuestStateList().getQuestState(2012);
/*  76 */     if (qs == null) {
/*  77 */       return false;
/*     */     }
/*  79 */     int var = qs.getQuestVarById(0);
/*  80 */     int targetId = 0;
/*  81 */     if (env.getVisibleObject() instanceof Npc) {
/*  82 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*     */     
/*  85 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  87 */       if (targetId == 203559)
/*     */       {
/*     */         
/*  90 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/*  93 */             if (var == 0)
/*  94 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*  95 */             if (var <= 5)
/*     */             {
/*  97 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
/*     */             }
/*  99 */             if (var >= 5) {
/*     */               
/* 101 */               qs.setStatus(QuestStatus.REWARD);
/* 102 */               updateQuestStatus(player, qs);
/*     */             } 
/*     */           case 10000:
/*     */           case 10001:
/* 106 */             if (var == 0 || var == 5) {
/*     */               
/* 108 */               qs.setQuestVarById(0, var + 1);
/* 109 */               updateQuestStatus(player, qs);
/* 110 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */               
/* 112 */               return true;
/*     */             } 
/*     */             break;
/*     */         } 
/*     */       
/*     */       }
/* 118 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 120 */       if (targetId == 203559)
/*     */       {
/* 122 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     } 
/* 125 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 132 */     Player player = env.getPlayer();
/* 133 */     QuestState qs = player.getQuestStateList().getQuestState(2012);
/* 134 */     if (qs == null) {
/* 135 */       return false;
/*     */     }
/* 137 */     int var = qs.getQuestVarById(0);
/* 138 */     int targetId = 0;
/* 139 */     if (env.getVisibleObject() instanceof Npc) {
/* 140 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 142 */     if (qs.getStatus() != QuestStatus.START) {
/* 143 */       return false;
/*     */     }
/* 145 */     switch (targetId) {
/*     */       
/*     */       case 210715:
/* 148 */         if (var > 0 && var < 4) {
/*     */           
/* 150 */           qs.setQuestVarById(0, var + 1);
/* 151 */           updateQuestStatus(player, qs);
/* 152 */           return true;
/*     */         } 
/* 154 */         if (var == 4) {
/*     */           
/* 156 */           qs.setStatus(QuestStatus.REWARD);
/* 157 */           updateQuestStatus(player, qs);
/* 158 */           return true;
/*     */         }  break;
/*     */     } 
/* 161 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\altgard\_2012Encroachers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */