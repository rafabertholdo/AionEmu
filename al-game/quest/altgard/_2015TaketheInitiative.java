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
/*     */ public class _2015TaketheInitiative
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2015;
/*     */   
/*     */   public _2015TaketheInitiative() {
/*  39 */     super(Integer.valueOf(2015));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  45 */     this.qe.addQuestLvlUp(2015);
/*  46 */     this.qe.setNpcQuestData(203631).addOnTalkEvent(2015);
/*  47 */     this.qe.setNpcQuestData(210510).addOnKillEvent(2015);
/*  48 */     this.qe.setNpcQuestData(210504).addOnKillEvent(2015);
/*  49 */     this.qe.setNpcQuestData(210506).addOnKillEvent(2015);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  55 */     Player player = env.getPlayer();
/*  56 */     QuestState qs = player.getQuestStateList().getQuestState(2015);
/*  57 */     if (qs == null) {
/*  58 */       return false;
/*     */     }
/*  60 */     int var = qs.getQuestVarById(0);
/*  61 */     int targetId = 0;
/*  62 */     if (env.getVisibleObject() instanceof Npc) {
/*  63 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  65 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  67 */       switch (targetId) {
/*     */         
/*     */         case 203631:
/*  70 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case -1:
/*  73 */               if (qs.getQuestVarById(1) >= 1 && qs.getQuestVarById(2) >= 5 && qs.getQuestVarById(3) >= 5) {
/*     */                 
/*  75 */                 qs.setQuestVarById(0, var + 1);
/*  76 */                 qs.setStatus(QuestStatus.REWARD);
/*  77 */                 updateQuestStatus(player, qs);
/*  78 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
/*     */               } 
/*     */               break;
/*     */             case 25:
/*  82 */               if (var == 0)
/*  83 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*     */               break;
/*     */             case 10000:
/*  86 */               qs.setQuestVarById(0, var + 1);
/*  87 */               updateQuestStatus(player, qs);
/*  88 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  89 */               return true;
/*     */           } 
/*     */           break;
/*     */       } 
/*  93 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  95 */       if (targetId == 203631)
/*     */       {
/*  97 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     } 
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 106 */     Player player = env.getPlayer();
/* 107 */     QuestState qs = player.getQuestStateList().getQuestState(2015);
/* 108 */     if (qs == null || qs.getStatus() != QuestStatus.START) {
/* 109 */       return false;
/*     */     }
/*     */     
/* 112 */     int targetId = 0;
/* 113 */     int var = 0;
/* 114 */     if (env.getVisibleObject() instanceof Npc)
/* 115 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/* 116 */     switch (targetId) {
/*     */       
/*     */       case 210510:
/* 119 */         var = qs.getQuestVarById(1);
/* 120 */         if (var == 0) {
/*     */           
/* 122 */           qs.setQuestVarById(1, 1);
/* 123 */           updateQuestStatus(player, qs);
/*     */         } 
/*     */         break;
/*     */       case 210504:
/* 127 */         var = qs.getQuestVarById(2);
/* 128 */         if (var < 5) {
/*     */           
/* 130 */           qs.setQuestVarById(2, var + 1);
/* 131 */           updateQuestStatus(player, qs);
/*     */         } 
/*     */         break;
/*     */       case 210506:
/* 135 */         var = qs.getQuestVarById(3);
/* 136 */         if (var < 5) {
/*     */           
/* 138 */           qs.setQuestVarById(3, var + 1);
/* 139 */           updateQuestStatus(player, qs);
/*     */         }  break;
/*     */     } 
/* 142 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/* 148 */     Player player = env.getPlayer();
/* 149 */     QuestState qs = player.getQuestStateList().getQuestState(2015);
/* 150 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED)
/* 151 */       return false; 
/* 152 */     QuestState qs2 = player.getQuestStateList().getQuestState(2014);
/* 153 */     if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
/* 154 */       return false; 
/* 155 */     qs.setStatus(QuestStatus.START);
/* 156 */     updateQuestStatus(player, qs);
/* 157 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\altgard\_2015TaketheInitiative.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */