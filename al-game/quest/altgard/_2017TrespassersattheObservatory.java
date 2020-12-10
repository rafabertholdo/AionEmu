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
/*     */ public class _2017TrespassersattheObservatory
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2017;
/*     */   
/*     */   public _2017TrespassersattheObservatory() {
/*  40 */     super(Integer.valueOf(2017));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  46 */     this.qe.addQuestLvlUp(2017);
/*  47 */     this.qe.setNpcQuestData(203654).addOnTalkEvent(2017);
/*  48 */     this.qe.setNpcQuestData(210528).addOnKillEvent(2017);
/*  49 */     this.qe.setNpcQuestData(210721).addOnKillEvent(2017);
/*  50 */     this.qe.setNpcQuestData(203558).addOnTalkEvent(2017);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  56 */     Player player = env.getPlayer();
/*  57 */     QuestState qs = player.getQuestStateList().getQuestState(2017);
/*  58 */     if (qs == null) {
/*  59 */       return false;
/*     */     }
/*  61 */     int var = qs.getQuestVarById(0);
/*  62 */     int targetId = 0;
/*  63 */     if (env.getVisibleObject() instanceof Npc) {
/*  64 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  66 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  68 */       switch (targetId) {
/*     */         
/*     */         case 203654:
/*  71 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/*  74 */               if (var == 0)
/*  75 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*  76 */               if (var == 6)
/*  77 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  78 */               if (var == 7)
/*  79 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*     */               break;
/*     */             case 10000:
/*     */             case 10001:
/*  83 */               if (var == 0 || var == 6) {
/*     */                 
/*  85 */                 qs.setQuestVarById(0, var + 1);
/*  86 */                 updateQuestStatus(player, qs);
/*  87 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  88 */                 return true;
/*     */               } 
/*     */               break;
/*     */             case 33:
/*  92 */               if (var == 7) {
/*     */                 
/*  94 */                 if (QuestService.collectItemCheck(env, true)) {
/*     */                   
/*  96 */                   qs.setStatus(QuestStatus.REWARD);
/*  97 */                   updateQuestStatus(player, qs);
/*  98 */                   return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1694);
/*     */                 } 
/*     */                 
/* 101 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1779);
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */       } 
/* 106 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 108 */       if (targetId == 203558) {
/*     */         
/* 110 */         if (env.getDialogId().intValue() == -1) {
/* 111 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
/*     */         }
/* 113 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 116 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 122 */     Player player = env.getPlayer();
/* 123 */     QuestState qs = player.getQuestStateList().getQuestState(2017);
/* 124 */     if (qs == null || qs.getStatus() != QuestStatus.START) {
/* 125 */       return false;
/*     */     }
/*     */     
/* 128 */     int targetId = 0;
/* 129 */     int var = 0;
/* 130 */     if (env.getVisibleObject() instanceof Npc)
/* 131 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/* 132 */     switch (targetId) {
/*     */       
/*     */       case 210528:
/*     */       case 210721:
/* 136 */         var = qs.getQuestVarById(0);
/* 137 */         if (var < 6) {
/*     */           
/* 139 */           qs.setQuestVarById(0, var + 1);
/* 140 */           updateQuestStatus(player, qs);
/*     */         } 
/*     */         break;
/*     */     } 
/* 144 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/* 150 */     Player player = env.getPlayer();
/* 151 */     QuestState qs = player.getQuestStateList().getQuestState(2017);
/* 152 */     boolean lvlCheck = QuestService.checkLevelRequirement(2017, player.getCommonData().getLevel());
/* 153 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck)
/* 154 */       return false; 
/* 155 */     QuestState qs2 = player.getQuestStateList().getQuestState(2015);
/* 156 */     if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
/* 157 */       return false; 
/* 158 */     qs.setStatus(QuestStatus.START);
/* 159 */     updateQuestStatus(player, qs);
/* 160 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\altgard\_2017TrespassersattheObservatory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */