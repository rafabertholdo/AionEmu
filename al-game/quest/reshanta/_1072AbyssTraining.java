/*     */ package quest.reshanta;
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
/*     */ public class _1072AbyssTraining
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1072;
/*  36 */   private static final int[] npc_ids = new int[] { 278627, 278628, 278629, 278630, 278631, 278632, 278633, 278554 };
/*     */ 
/*     */   
/*     */   public _1072AbyssTraining() {
/*  40 */     super(Integer.valueOf(1072));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  46 */     this.qe.addQuestLvlUp(1072);
/*  47 */     for (int npc_id : npc_ids) {
/*  48 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1072);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  54 */     Player player = env.getPlayer();
/*  55 */     QuestState qs = player.getQuestStateList().getQuestState(1072);
/*  56 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED) {
/*  57 */       return false;
/*     */     }
/*  59 */     QuestState qs2 = player.getQuestStateList().getQuestState(1701);
/*  60 */     if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
/*  61 */       return false; 
/*  62 */     qs.setStatus(QuestStatus.START);
/*  63 */     updateQuestStatus(player, qs);
/*  64 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  70 */     Player player = env.getPlayer();
/*  71 */     QuestState qs = player.getQuestStateList().getQuestState(1072);
/*  72 */     if (qs == null) {
/*  73 */       return false;
/*     */     }
/*  75 */     int var = qs.getQuestVarById(0);
/*  76 */     int targetId = 0;
/*  77 */     if (env.getVisibleObject() instanceof Npc) {
/*  78 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  80 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  82 */       if (targetId == 278554) {
/*     */         
/*  84 */         if (env.getDialogId().intValue() == -1)
/*  85 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002); 
/*  86 */         if (env.getDialogId().intValue() == 1009)
/*  87 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5); 
/*  88 */         return defaultQuestEndDialog(env);
/*     */       } 
/*  90 */       return false;
/*     */     } 
/*  92 */     if (qs.getStatus() != QuestStatus.START)
/*     */     {
/*  94 */       return false;
/*     */     }
/*  96 */     if (targetId == 278627) {
/*     */       
/*  98 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 101 */           if (var == 0)
/* 102 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*     */         case 1013:
/* 104 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 262));
/*     */           break;
/*     */         case 10000:
/* 107 */           if (var == 0) {
/*     */             
/* 109 */             qs.setQuestVarById(0, var + 1);
/* 110 */             updateQuestStatus(player, qs);
/* 111 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 112 */             return true;
/*     */           } 
/* 114 */           return false;
/*     */       } 
/*     */     
/* 117 */     } else if (targetId == 278628) {
/*     */       
/* 119 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 122 */           if (var == 1)
/* 123 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */         case 1353:
/* 125 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 263));
/*     */           break;
/*     */         case 10001:
/* 128 */           if (var == 1) {
/*     */             
/* 130 */             qs.setQuestVarById(0, var + 1);
/* 131 */             updateQuestStatus(player, qs);
/* 132 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 133 */             return true;
/*     */           } 
/* 135 */           return false;
/*     */       } 
/*     */     
/* 138 */     } else if (targetId == 278629) {
/*     */       
/* 140 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 143 */           if (var == 2)
/* 144 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*     */         case 1694:
/* 146 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 264));
/*     */           break;
/*     */         case 10002:
/* 149 */           if (var == 2) {
/*     */             
/* 151 */             qs.setQuestVarById(0, var + 1);
/* 152 */             updateQuestStatus(player, qs);
/* 153 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 154 */             return true;
/*     */           } 
/* 156 */           return false;
/*     */       } 
/*     */     
/* 159 */     } else if (targetId == 278630) {
/*     */       
/* 161 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 164 */           if (var == 3)
/* 165 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/*     */         case 2035:
/* 167 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 265));
/*     */           break;
/*     */         case 10003:
/* 170 */           if (var == 3) {
/*     */             
/* 172 */             qs.setQuestVarById(0, var + 1);
/* 173 */             updateQuestStatus(player, qs);
/* 174 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 175 */             return true;
/*     */           } 
/* 177 */           return false;
/*     */       } 
/*     */     
/* 180 */     } else if (targetId == 278631) {
/*     */       
/* 182 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 185 */           if (var == 4)
/* 186 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*     */         case 2376:
/* 188 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 266));
/*     */           break;
/*     */         case 10004:
/* 191 */           if (var == 4) {
/*     */             
/* 193 */             qs.setQuestVarById(0, var + 1);
/* 194 */             updateQuestStatus(player, qs);
/* 195 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 196 */             return true;
/*     */           } 
/* 198 */           return false;
/*     */       } 
/*     */     
/* 201 */     } else if (targetId == 278632) {
/*     */       
/* 203 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 206 */           if (var == 5)
/* 207 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
/*     */         case 2717:
/* 209 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 267));
/*     */           break;
/*     */         case 10005:
/* 212 */           if (var == 5) {
/*     */             
/* 214 */             qs.setQuestVarById(0, var + 1);
/* 215 */             updateQuestStatus(player, qs);
/* 216 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 217 */             return true;
/*     */           } 
/* 219 */           return false;
/*     */       } 
/*     */     
/* 222 */     } else if (targetId == 278633) {
/*     */       
/* 224 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 227 */           if (var == 6)
/* 228 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
/*     */         case 3058:
/* 230 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 268));
/*     */           break;
/*     */         case 10255:
/* 233 */           if (var == 6) {
/*     */             
/* 235 */             qs.setStatus(QuestStatus.REWARD);
/* 236 */             updateQuestStatus(player, qs);
/* 237 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 238 */             return true;
/*     */           } 
/* 240 */           return false;
/*     */       } 
/*     */     } 
/* 243 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\reshanta\_1072AbyssTraining.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */