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
/*     */ import com.aionemu.gameserver.services.TeleportService;
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
/*     */ public class _1040ScoutingtheScouts
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1040;
/*     */   
/*     */   public _1040ScoutingtheScouts() {
/*  41 */     super(Integer.valueOf(1040));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  47 */     this.qe.addQuestLvlUp(1040);
/*  48 */     this.qe.setNpcQuestData(212010).addOnKillEvent(1040);
/*  49 */     this.qe.setNpcQuestData(204046).addOnKillEvent(1040);
/*  50 */     this.qe.setNpcQuestData(203989).addOnTalkEvent(1040);
/*  51 */     this.qe.setNpcQuestData(203901).addOnTalkEvent(1040);
/*  52 */     this.qe.setNpcQuestData(204020).addOnTalkEvent(1040);
/*  53 */     this.qe.setNpcQuestData(204024).addOnTalkEvent(1040);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  59 */     Player player = env.getPlayer();
/*  60 */     QuestState qs = player.getQuestStateList().getQuestState(1040);
/*  61 */     boolean lvlCheck = QuestService.checkLevelRequirement(1040, player.getCommonData().getLevel());
/*  62 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
/*  63 */       return false;
/*     */     }
/*  65 */     qs.setStatus(QuestStatus.START);
/*  66 */     updateQuestStatus(player, qs);
/*  67 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/*  73 */     Player player = env.getPlayer();
/*  74 */     QuestState qs = player.getQuestStateList().getQuestState(1040);
/*  75 */     if (qs == null) {
/*  76 */       return false;
/*     */     }
/*  78 */     int var = qs.getQuestVarById(0);
/*  79 */     int targetId = 0;
/*  80 */     if (env.getVisibleObject() instanceof Npc) {
/*  81 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  83 */     if (qs.getStatus() != QuestStatus.START)
/*  84 */       return false; 
/*  85 */     if (targetId == 212010) {
/*     */       
/*  87 */       if (var > 0 && var < 4)
/*     */       {
/*  89 */         qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  90 */         updateQuestStatus(player, qs);
/*  91 */         return true;
/*     */       }
/*     */     
/*  94 */     } else if (targetId == 204046) {
/*     */       
/*  96 */       if (var > 7 && var < 9) {
/*     */         
/*  98 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 36));
/*  99 */         qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 100 */         updateQuestStatus(player, qs);
/* 101 */         return true;
/*     */       } 
/*     */     } 
/* 104 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/* 110 */     Player player = env.getPlayer();
/* 111 */     QuestState qs = player.getQuestStateList().getQuestState(1040);
/* 112 */     if (qs == null) {
/* 113 */       return false;
/*     */     }
/* 115 */     int var = qs.getQuestVarById(0);
/* 116 */     int targetId = 0;
/* 117 */     if (env.getVisibleObject() instanceof Npc) {
/* 118 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 120 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 122 */       if (targetId == 203989) {
/* 123 */         return defaultQuestEndDialog(env);
/*     */       }
/* 125 */     } else if (qs.getStatus() != QuestStatus.START) {
/*     */       
/* 127 */       return false;
/*     */     } 
/* 129 */     if (targetId == 203989) {
/*     */       
/* 131 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 134 */           if (var == 0)
/* 135 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/* 136 */           if (var == 4)
/* 137 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 138 */           return false;
/*     */         
/*     */         case 1013:
/* 141 */           if (var == 0)
/* 142 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 183)); 
/* 143 */           return false;
/*     */         case 10000:
/* 145 */           if (var == 0) {
/*     */             
/* 147 */             qs.setQuestVarById(0, var + 1);
/* 148 */             updateQuestStatus(player, qs);
/* 149 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 150 */             return true;
/*     */           } 
/*     */         case 10001:
/* 153 */           if (var == 4) {
/*     */             
/* 155 */             qs.setQuestVarById(0, var + 1);
/* 156 */             updateQuestStatus(player, qs);
/* 157 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 158 */             return true;
/*     */           } 
/* 160 */           return false;
/*     */       } 
/*     */     
/* 163 */     } else if (targetId == 203901) {
/*     */       
/* 165 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 168 */           if (var == 5)
/* 169 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 170 */           return false;
/*     */         
/*     */         case 10002:
/* 173 */           if (var == 5) {
/*     */             
/* 175 */             qs.setQuestVarById(0, var + 1);
/* 176 */             updateQuestStatus(player, qs);
/* 177 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 178 */             return true;
/*     */           } 
/* 180 */           return false;
/*     */       } 
/*     */     
/* 183 */     } else if (targetId == 204020) {
/*     */       
/* 185 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 188 */           if (var == 6)
/* 189 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/* 190 */           if (var == 10)
/* 191 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
/* 192 */           return false;
/*     */         
/*     */         case 10003:
/* 195 */           if (var == 6) {
/*     */             
/* 197 */             TeleportService.teleportTo(player, 210020000, 2211.0F, 811.0F, 513.0F, 0);
/* 198 */             qs.setQuestVarById(0, var + 1);
/* 199 */             updateQuestStatus(player, qs);
/* 200 */             return true;
/*     */           } 
/*     */         
/*     */         case 10006:
/* 204 */           if (var == 10) {
/*     */             
/* 206 */             qs.setStatus(QuestStatus.REWARD);
/* 207 */             updateQuestStatus(player, qs);
/* 208 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 209 */             return true;
/*     */           } 
/* 211 */           return false;
/*     */       } 
/*     */     
/* 214 */     } else if (targetId == 204024) {
/*     */       
/* 216 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 219 */           if (var == 7)
/* 220 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 221 */           if (var == 9)
/* 222 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
/* 223 */           return false;
/*     */         
/*     */         case 10004:
/* 226 */           if (var == 7) {
/*     */             
/* 228 */             qs.setQuestVarById(0, var + 1);
/* 229 */             updateQuestStatus(player, qs);
/* 230 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 231 */             return true;
/*     */           } 
/*     */         case 10005:
/* 234 */           if (var == 9) {
/*     */             
/* 236 */             TeleportService.teleportTo(player, 210020000, 1606.0F, 1529.0F, 318.0F, 0);
/* 237 */             qs.setQuestVarById(0, var + 1);
/* 238 */             updateQuestStatus(player, qs);
/* 239 */             return true;
/*     */           } 
/* 241 */           return false;
/*     */       } 
/*     */     
/*     */     } 
/* 245 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1040ScoutingtheScouts.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */