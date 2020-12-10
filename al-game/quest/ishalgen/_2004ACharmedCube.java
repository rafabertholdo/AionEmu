/*     */ package quest.ishalgen;
/*     */ 
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.services.QuestService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
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
/*     */ public class _2004ACharmedCube
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2004;
/*     */   
/*     */   public _2004ACharmedCube() {
/*  44 */     super(Integer.valueOf(2004));
/*     */   }
/*     */ 
/*     */   
/*     */   public void register() {
/*  49 */     this.qe.addQuestLvlUp(2004);
/*  50 */     this.qe.setNpcQuestData(203539).addOnTalkEvent(2004);
/*  51 */     this.qe.setNpcQuestData(700047).addOnTalkEvent(2004);
/*  52 */     this.qe.setNpcQuestData(203550).addOnTalkEvent(2004);
/*  53 */     this.qe.setNpcQuestData(210402).addOnKillEvent(2004);
/*  54 */     this.qe.setNpcQuestData(210403).addOnKillEvent(2004);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  60 */     final Player player = env.getPlayer();
/*  61 */     QuestState qs = player.getQuestStateList().getQuestState(2004);
/*  62 */     if (qs == null) {
/*  63 */       return false;
/*     */     }
/*  65 */     int var = qs.getQuestVarById(0);
/*  66 */     int targetId = 0;
/*  67 */     if (env.getVisibleObject() instanceof Npc)
/*  68 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  69 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       final int targetObjectId;
/*  71 */       switch (targetId) {
/*     */ 
/*     */         
/*     */         case 203539:
/*  75 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/*  78 */               if (var == 0)
/*  79 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*  80 */               if (var == 1)
/*  81 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */               break;
/*     */             case 10000:
/*     */             case 10001:
/*  85 */               if (var == 0 || var == 1) {
/*     */                 
/*  87 */                 qs.setQuestVarById(0, var + 1);
/*  88 */                 updateQuestStatus(player, qs);
/*  89 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  90 */                 return true;
/*     */               } 
/*     */               break;
/*     */             case 33:
/*  94 */               if (var == 1) {
/*     */                 
/*  96 */                 if (player.getInventory().getItemCountByItemId(182203005) > 0L)
/*     */                 {
/*  98 */                   return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1438);
/*     */                 }
/*     */                 
/* 101 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1353);
/*     */               } 
/*     */               break;
/*     */           } 
/* 105 */           return false;
/*     */ 
/*     */         
/*     */         case 700047:
/* 109 */           targetObjectId = env.getVisibleObject().getObjectId();
/* 110 */           if (env.getDialogId().intValue() == -1)
/*     */           {
/* 112 */             if (var == 1) {
/*     */               
/* 114 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/*     */               
/* 116 */               PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/*     */               
/* 118 */               ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */                   {
/*     */                     public void run()
/*     */                     {
/* 122 */                       Npc npc = (Npc)player.getTarget();
/* 123 */                       if (!player.isTargeting(targetObjectId))
/*     */                         return; 
/* 125 */                       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/*     */                       
/* 127 */                       PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/*     */                       
/* 129 */                       QuestService.addNewSpawn(player.getWorldId(), player.getInstanceId(), 211755, npc.getX(), npc.getY(), npc.getZ(), npc.getHeading(), true);
/* 130 */                       npc.getController().onDie(null);
/*     */                     }
/*     */                   }3000L);
/*     */             } 
/*     */           }
/* 135 */           return false;
/*     */ 
/*     */         
/*     */         case 203550:
/* 139 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 142 */               if (var == 2)
/* 143 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 144 */               if (var == 6)
/* 145 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/*     */               break;
/*     */             case 10002:
/* 148 */               if (var == 2) {
/*     */                 
/* 150 */                 ItemService.removeItemFromInventoryByItemId(player, 182203005);
/* 151 */                 qs.setQuestVarById(0, 3);
/* 152 */                 updateQuestStatus(player, qs);
/* 153 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 154 */                 return true;
/*     */               } 
/*     */               break;
/*     */             case 10003:
/* 158 */               if (var == 6) {
/*     */                 
/* 160 */                 qs.setStatus(QuestStatus.REWARD);
/* 161 */                 updateQuestStatus(player, qs);
/* 162 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 163 */                 return true;
/*     */               } 
/*     */               break;
/*     */           } 
/*     */           break;
/*     */       } 
/* 169 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 171 */       if (targetId == 203539) {
/*     */         
/* 173 */         if (env.getDialogId().intValue() == -1) {
/* 174 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
/*     */         }
/* 176 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 179 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 185 */     Player player = env.getPlayer();
/* 186 */     QuestState qs = player.getQuestStateList().getQuestState(2004);
/* 187 */     if (qs == null) {
/* 188 */       return false;
/*     */     }
/* 190 */     int var = qs.getQuestVarById(0);
/* 191 */     int targetId = 0;
/* 192 */     if (env.getVisibleObject() instanceof Npc) {
/* 193 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 195 */     if (qs.getStatus() != QuestStatus.START)
/* 196 */       return false; 
/* 197 */     switch (targetId) {
/*     */       
/*     */       case 210402:
/*     */       case 210403:
/* 201 */         if (var >= 3 && var < 8) {
/*     */           
/* 203 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 204 */           updateQuestStatus(player, qs);
/* 205 */           return true;
/*     */         } 
/* 207 */         if (var == 8) {
/*     */           
/* 209 */           qs.setStatus(QuestStatus.REWARD);
/* 210 */           updateQuestStatus(player, qs);
/* 211 */           return true;
/*     */         }  break;
/*     */     } 
/* 214 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/* 219 */     Player player = env.getPlayer();
/* 220 */     QuestState qs = player.getQuestStateList().getQuestState(2004);
/* 221 */     boolean lvlCheck = QuestService.checkLevelRequirement(2004, player.getCommonData().getLevel());
/* 222 */     if (!lvlCheck || qs == null || qs.getStatus() != QuestStatus.LOCKED)
/* 223 */       return false; 
/* 224 */     qs.setStatus(QuestStatus.START);
/* 225 */     updateQuestStatus(player, qs);
/* 226 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\ishalgen\_2004ACharmedCube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */