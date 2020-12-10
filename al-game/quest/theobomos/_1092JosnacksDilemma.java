/*     */ package quest.theobomos;
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
/*     */ public class _1092JosnacksDilemma
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1092;
/*  37 */   private static final int[] npc_ids = new int[] { 798155, 798206, 700390, 700388, 700389 };
/*     */ 
/*     */   
/*     */   public _1092JosnacksDilemma() {
/*  41 */     super(Integer.valueOf(1092));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  47 */     this.qe.addQuestLvlUp(1092);
/*  48 */     for (int npc_id : npc_ids)
/*  49 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1092); 
/*  50 */     this.deletebleItems = new int[] { 182208012 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  56 */     Player player = env.getPlayer();
/*  57 */     QuestState qs = player.getQuestStateList().getQuestState(1092);
/*  58 */     boolean lvlCheck = QuestService.checkLevelRequirement(1092, player.getCommonData().getLevel());
/*  59 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
/*  60 */       return false;
/*     */     }
/*  62 */     QuestState qs2 = player.getQuestStateList().getQuestState(1091);
/*  63 */     if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
/*  64 */       return false; 
/*  65 */     qs.setStatus(QuestStatus.START);
/*  66 */     updateQuestStatus(player, qs);
/*  67 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  73 */     Player player = env.getPlayer();
/*  74 */     QuestState qs = player.getQuestStateList().getQuestState(1092);
/*  75 */     Npc npc = (Npc)env.getVisibleObject();
/*     */     
/*  77 */     if (qs == null) {
/*  78 */       return false;
/*     */     }
/*  80 */     int var = qs.getQuestVarById(0);
/*  81 */     int targetId = 0;
/*  82 */     if (env.getVisibleObject() instanceof Npc) {
/*  83 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  85 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  87 */       if (targetId == 798155)
/*  88 */         return defaultQuestEndDialog(env); 
/*  89 */       return false;
/*     */     } 
/*  91 */     if (qs.getStatus() != QuestStatus.START)
/*     */     {
/*  93 */       return false;
/*     */     }
/*  95 */     if (targetId == 798155) {
/*     */       
/*  97 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 100 */           if (var == 0)
/* 101 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/* 102 */           if (var == 3)
/* 103 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/* 104 */           if (var == 4)
/* 105 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 106 */           return true;
/*     */         case 10000:
/* 108 */           if (var == 0) {
/*     */             
/* 110 */             qs.setQuestVarById(0, var + 1);
/* 111 */             updateQuestStatus(player, qs);
/* 112 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 113 */             return true;
/*     */           } 
/*     */         case 10003:
/* 116 */           if (var == 3) {
/*     */             
/* 118 */             qs.setQuestVarById(0, var + 1);
/* 119 */             updateQuestStatus(player, qs);
/* 120 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 121 */             return true;
/*     */           } 
/*     */         case 33:
/* 124 */           if (var == 4) {
/*     */             
/* 126 */             if (QuestService.collectItemCheck(env, true)) {
/*     */               
/* 128 */               qs.setStatus(QuestStatus.REWARD);
/* 129 */               updateQuestStatus(player, qs);
/* 130 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
/*     */             } 
/* 132 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10008);
/*     */           } 
/*     */           break;
/*     */       } 
/* 136 */     } else if (targetId == 798206) {
/*     */       
/* 138 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 141 */           if (var == 1)
/* 142 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */         case 10001:
/* 144 */           if (var == 1) {
/*     */             
/* 146 */             qs.setQuestVarById(0, var + 1);
/* 147 */             updateQuestStatus(player, qs);
/* 148 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 149 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 153 */     } else if (targetId == 700388) {
/*     */       
/* 155 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case -1:
/* 158 */           if (var == 2) {
/*     */             
/* 160 */             updateQuestStatus(player, qs);
/* 161 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 165 */     } else if (targetId == 700389) {
/*     */       
/* 167 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case -1:
/* 170 */           if (var == 2) {
/*     */             
/* 172 */             qs.setQuestVarById(0, var + 1);
/* 173 */             updateQuestStatus(player, qs);
/* 174 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 178 */     } else if (targetId == 700390) {
/*     */       
/* 180 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case -1:
/* 183 */           if (var == 4) {
/*     */             
/* 185 */             QuestService.addNewSpawn(210070000, 6, 214552, npc.getX(), npc.getY(), npc.getZ(), (byte)0, true);
/*     */             
/* 187 */             npc.getController().onDespawn(true);
/* 188 */             npc.getController().scheduleRespawn();
/* 189 */             return true;
/*     */           }  break;
/*     */       } 
/*     */     } 
/* 193 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\theobomos\_1092JosnacksDilemma.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */