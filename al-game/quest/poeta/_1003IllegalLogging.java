/*     */ package quest.poeta;
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
/*     */ public class _1003IllegalLogging
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1003;
/*  37 */   private static final int[] mob_ids = new int[] { 210096, 210149, 210145, 210146, 210150, 210151, 210092, 210160, 210154 };
/*     */ 
/*     */   
/*     */   public _1003IllegalLogging() {
/*  41 */     super(Integer.valueOf(1003));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  47 */     this.qe.setNpcQuestData(203081).addOnTalkEvent(1003);
/*  48 */     this.qe.addQuestLvlUp(1003);
/*  49 */     for (int mob_id : mob_ids) {
/*  50 */       this.qe.setNpcQuestData(mob_id).addOnKillEvent(1003);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  56 */     Player player = env.getPlayer();
/*  57 */     QuestState qs = player.getQuestStateList().getQuestState(1003);
/*  58 */     boolean lvlCheck = QuestService.checkLevelRequirement(1003, player.getCommonData().getLevel());
/*  59 */     if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
/*  60 */       return false; 
/*  61 */     qs.setStatus(QuestStatus.START);
/*  62 */     updateQuestStatus(player, qs);
/*  63 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  69 */     Player player = env.getPlayer();
/*  70 */     QuestState qs = player.getQuestStateList().getQuestState(1003);
/*  71 */     if (qs == null) {
/*  72 */       return false;
/*     */     }
/*  74 */     int var = qs.getQuestVarById(0);
/*  75 */     int targetId = 0;
/*  76 */     if (env.getVisibleObject() instanceof Npc) {
/*  77 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  79 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  81 */       if (targetId == 203081)
/*     */       {
/*  83 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/*  86 */             if (var == 0)
/*  87 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*  88 */             if (var == 13) {
/*  89 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
/*     */             }
/*     */           case 10000:
/*     */           case 10001:
/*  93 */             if (var == 0 || var == 13) {
/*     */               
/*  95 */               qs.setQuestVarById(0, var + 1);
/*  96 */               updateQuestStatus(player, qs);
/*  97 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  98 */               return true;
/*     */             } 
/*     */             break;
/*     */         } 
/*     */       }
/* 103 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 105 */       return defaultQuestEndDialog(env);
/*     */     } 
/* 107 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 113 */     Player player = env.getPlayer();
/* 114 */     QuestState qs = player.getQuestStateList().getQuestState(1003);
/* 115 */     if (qs == null) {
/* 116 */       return false;
/*     */     }
/* 118 */     int var = qs.getQuestVarById(0);
/* 119 */     int targetId = 0;
/* 120 */     if (env.getVisibleObject() instanceof Npc) {
/* 121 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 123 */     if (qs.getStatus() != QuestStatus.START)
/* 124 */       return false; 
/* 125 */     switch (targetId) {
/*     */       
/*     */       case 210092:
/*     */       case 210096:
/*     */       case 210145:
/*     */       case 210146:
/*     */       case 210149:
/*     */       case 210150:
/*     */       case 210151:
/*     */       case 210154:
/* 135 */         if (var >= 1 && var <= 12) {
/*     */           
/* 137 */           qs.setQuestVarById(0, var + 1);
/* 138 */           updateQuestStatus(player, qs);
/* 139 */           return true;
/*     */         } 
/*     */         break;
/*     */       case 210160:
/* 143 */         if (var >= 14 && var <= 15) {
/*     */           
/* 145 */           qs.setQuestVarById(0, var + 1);
/* 146 */           updateQuestStatus(player, qs);
/* 147 */           return true;
/*     */         } 
/* 149 */         if (var == 16) {
/*     */           
/* 151 */           qs.setStatus(QuestStatus.REWARD);
/* 152 */           updateQuestStatus(player, qs);
/* 153 */           return true;
/*     */         }  break;
/*     */     } 
/* 156 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\poeta\_1003IllegalLogging.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */