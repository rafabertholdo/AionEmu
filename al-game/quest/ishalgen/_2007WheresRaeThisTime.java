/*     */ package quest.ishalgen;
/*     */ 
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
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
/*     */ 
/*     */ public class _2007WheresRaeThisTime
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2007;
/*     */   
/*     */   public _2007WheresRaeThisTime() {
/*  44 */     super(Integer.valueOf(2007));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  50 */     int[] talkNpcs = { 203516, 203519, 203539, 203552, 203554, 700081, 700082, 700083 };
/*  51 */     this.qe.addQuestLvlUp(2007);
/*  52 */     for (int id : talkNpcs) {
/*  53 */       this.qe.setNpcQuestData(id).addOnTalkEvent(2007);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  59 */     Player player = env.getPlayer();
/*  60 */     QuestState qs = player.getQuestStateList().getQuestState(2007);
/*  61 */     if (qs == null) {
/*  62 */       return false;
/*     */     }
/*  64 */     int var = qs.getQuestVarById(0);
/*  65 */     int targetId = 0;
/*  66 */     if (env.getVisibleObject() instanceof Npc) {
/*  67 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  69 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  71 */       switch (targetId) {
/*     */         
/*     */         case 203516:
/*  74 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/*  77 */               if (var == 0)
/*  78 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*     */             case 10000:
/*  80 */               if (var == 0) {
/*     */                 
/*  82 */                 qs.setQuestVarById(0, var + 1);
/*  83 */                 updateQuestStatus(player, qs);
/*  84 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  85 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         case 203519:
/*  90 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/*  93 */               if (var == 1)
/*  94 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */             case 10001:
/*  96 */               if (var == 1) {
/*     */                 
/*  98 */                 qs.setQuestVarById(0, var + 1);
/*  99 */                 updateQuestStatus(player, qs);
/* 100 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 101 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         case 203539:
/* 106 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 109 */               if (var == 2)
/* 110 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*     */             case 1694:
/* 112 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 55));
/*     */               break;
/*     */             case 10002:
/* 115 */               if (var == 2) {
/*     */                 
/* 117 */                 qs.setQuestVarById(0, var + 1);
/* 118 */                 updateQuestStatus(player, qs);
/* 119 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 120 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         case 203552:
/* 125 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 128 */               if (var == 3)
/* 129 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/*     */             case 10003:
/* 131 */               if (var == 3) {
/*     */                 
/* 133 */                 qs.setQuestVarById(0, var + 1);
/* 134 */                 updateQuestStatus(player, qs);
/* 135 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 136 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         case 203554:
/* 141 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 144 */               if (var == 4)
/* 145 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 146 */               if (var == 8)
/* 147 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
/*     */             case 10004:
/* 149 */               if (var == 4) {
/*     */                 
/* 151 */                 qs.setQuestVarById(0, var + 1);
/* 152 */                 updateQuestStatus(player, qs);
/* 153 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 154 */                 return true;
/*     */               } 
/*     */               break;
/*     */             case 10005:
/* 158 */               if (var == 8) {
/*     */                 
/* 160 */                 qs.setStatus(QuestStatus.REWARD);
/* 161 */                 updateQuestStatus(player, qs);
/* 162 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 163 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         case 700081:
/* 168 */           if (var == 5) {
/*     */             
/* 170 */             destroy(6, env);
/* 171 */             return false;
/*     */           } 
/*     */           break;
/*     */         case 700082:
/* 175 */           if (var == 6) {
/*     */             
/* 177 */             destroy(7, env);
/* 178 */             return false;
/*     */           } 
/*     */           break;
/*     */         case 700083:
/* 182 */           if (var == 7) {
/*     */             
/* 184 */             destroy(-1, env);
/* 185 */             return false;
/*     */           } 
/*     */           break;
/*     */       } 
/*     */     
/* 190 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 192 */       if (targetId == 203516) {
/*     */         
/* 194 */         if (env.getDialogId().intValue() == -1) {
/*     */           
/* 196 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 58));
/* 197 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057);
/*     */         } 
/*     */         
/* 200 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 203 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/* 209 */     Player player = env.getPlayer();
/* 210 */     QuestState qs = player.getQuestStateList().getQuestState(2007);
/* 211 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED)
/* 212 */       return false; 
/* 213 */     int[] quests = { 2001, 2002, 2003, 2004, 2005, 2006 };
/* 214 */     for (int id : quests) {
/*     */       
/* 216 */       QuestState qs2 = player.getQuestStateList().getQuestState(id);
/* 217 */       if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
/* 218 */         return false; 
/*     */     } 
/* 220 */     qs.setStatus(QuestStatus.START);
/* 221 */     updateQuestStatus(player, qs);
/* 222 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void destroy(final int var, QuestEnv env) {
/* 227 */     final int targetObjectId = env.getVisibleObject().getObjectId();
/*     */     
/* 229 */     final Player player = env.getPlayer();
/* 230 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/* 231 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/* 232 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 236 */             if (player.getTarget().getObjectId() != targetObjectId)
/*     */               return; 
/* 238 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/* 239 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/* 240 */             PacketSendUtility.broadcastPacket(player.getTarget(), (AionServerPacket)new SM_EMOTION((Creature)player.getTarget(), EmotionType.EMOTE, 128, 0));
/* 241 */             QuestState qs = player.getQuestStateList().getQuestState(2007);
/* 242 */             if (var == -1)
/* 243 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 56)); 
/* 244 */             qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 245 */             _2007WheresRaeThisTime.this.updateQuestStatus(player, qs);
/*     */           }
/*     */         }3000L);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\ishalgen\_2007WheresRaeThisTime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */