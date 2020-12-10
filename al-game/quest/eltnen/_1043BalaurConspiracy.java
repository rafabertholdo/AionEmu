/*     */ package quest.eltnen;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.TeleportService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.world.WorldMapType;
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
/*     */ public class _1043BalaurConspiracy
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1043;
/*     */   
/*     */   public _1043BalaurConspiracy() {
/*  40 */     super(Integer.valueOf(1043));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  46 */     this.qe.addQuestLvlUp(1043);
/*  47 */     this.qe.setNpcQuestData(203901).addOnTalkEvent(1043);
/*  48 */     this.qe.setNpcQuestData(204020).addOnTalkEvent(1043);
/*  49 */     this.qe.setNpcQuestData(204044).addOnTalkEvent(1043);
/*  50 */     this.qe.setNpcQuestData(211629).addOnKillEvent(1043);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  56 */     Player player = env.getPlayer();
/*  57 */     QuestState qs = player.getQuestStateList().getQuestState(1043);
/*     */     
/*  59 */     int targetId = 0;
/*  60 */     if (env.getVisibleObject() instanceof Npc) {
/*  61 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  63 */     if (qs == null) {
/*  64 */       return false;
/*     */     }
/*  66 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  68 */       switch (targetId) {
/*     */ 
/*     */         
/*     */         case 203901:
/*  72 */           switch (env.getDialogId().intValue()) {
/*     */ 
/*     */             
/*     */             case 25:
/*  76 */               if (qs.getQuestVarById(0) == 0)
/*     */               {
/*  78 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */               }
/*     */ 
/*     */             
/*     */             case 10000:
/*  83 */               qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  84 */               updateQuestStatus(player, qs);
/*  85 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */               
/*  87 */               return true;
/*     */           } 
/*     */         
/*     */         
/*     */         
/*     */         case 204020:
/*  93 */           switch (env.getDialogId().intValue()) {
/*     */ 
/*     */             
/*     */             case 25:
/*  97 */               if (qs.getQuestVarById(0) == 1)
/*     */               {
/*  99 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
/*     */               }
/*     */ 
/*     */             
/*     */             case 10001:
/* 104 */               qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 105 */               updateQuestStatus(player, qs);
/* 106 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */               
/* 108 */               return true;
/*     */           } 
/*     */         
/*     */         
/*     */         
/*     */         case 204044:
/* 114 */           switch (env.getDialogId().intValue()) {
/*     */ 
/*     */             
/*     */             case 25:
/* 118 */               switch (qs.getQuestVarById(0)) {
/*     */ 
/*     */                 
/*     */                 case 2:
/* 122 */                   return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
/*     */ 
/*     */                 
/*     */                 case 4:
/* 126 */                   return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
/*     */               } 
/*     */             
/*     */             
/*     */             
/*     */             case 10002:
/* 132 */               qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 133 */               updateQuestStatus(player, qs);
/* 134 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */               
/* 136 */               return true;
/*     */ 
/*     */             
/*     */             case 10003:
/* 140 */               qs.setQuestVar(4);
/* 141 */               qs.setStatus(QuestStatus.REWARD);
/* 142 */               updateQuestStatus(player, qs);
/* 143 */               TeleportService.teleportTo(player, WorldMapType.ELTNEN.getId(), 2502.1948F, 782.9152F, 408.97723F, 0);
/*     */               
/* 145 */               return true;
/*     */           } 
/*     */           
/*     */           break;
/*     */       } 
/*     */     
/* 151 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 153 */       if (targetId == 203901) {
/*     */         
/* 155 */         switch (env.getDialogId().intValue()) {
/*     */ 
/*     */           
/*     */           case 25:
/* 159 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
/*     */ 
/*     */           
/*     */           case 1009:
/* 163 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */         } 
/*     */         
/* 166 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/*     */     
/* 170 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/* 176 */     Player player = env.getPlayer();
/* 177 */     QuestState qs = player.getQuestStateList().getQuestState(1043);
/*     */     
/* 179 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED)
/*     */     {
/* 181 */       return false;
/*     */     }
/*     */     
/* 184 */     int[] quests = { 1300, 1031, 1032, 1033, 1034, 1036, 1037, 1035, 1038, 1039, 1040, 1041, 1042 };
/* 185 */     for (int id : quests) {
/*     */       
/* 187 */       QuestState qs2 = player.getQuestStateList().getQuestState(id);
/* 188 */       if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE) {
/* 189 */         return false;
/*     */       }
/*     */     } 
/* 192 */     qs.setStatus(QuestStatus.START);
/* 193 */     updateQuestStatus(player, qs);
/* 194 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 200 */     Player player = env.getPlayer();
/* 201 */     QuestState qs = player.getQuestStateList().getQuestState(1043);
/*     */     
/* 203 */     if (qs == null || qs.getStatus() != QuestStatus.START || qs.getQuestVarById(0) != 3)
/*     */     {
/* 205 */       return false;
/*     */     }
/*     */     
/* 208 */     int targetId = 0;
/* 209 */     if (env.getVisibleObject() instanceof Npc) {
/* 210 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 212 */     if (targetId == 211629) {
/*     */       
/* 214 */       qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 215 */       updateQuestStatus(player, qs);
/* 216 */       return true;
/*     */     } 
/* 218 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1043BalaurConspiracy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */