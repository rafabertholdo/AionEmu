/*     */ package quest.ishalgen;
/*     */ 
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
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
/*     */ import com.aionemu.gameserver.services.InstanceService;
/*     */ import com.aionemu.gameserver.services.QuestService;
/*     */ import com.aionemu.gameserver.services.TeleportService;
/*     */ import com.aionemu.gameserver.skillengine.SkillEngine;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import com.aionemu.gameserver.world.WorldMapInstance;
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
/*     */ public class _2002WheresRae
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2002;
/*  45 */   private static final int[] npc_ids = new int[] { 203519, 203534, 203553, 700045, 203516, 205020, 203537 };
/*     */ 
/*     */   
/*     */   public _2002WheresRae() {
/*  49 */     super(Integer.valueOf(2002));
/*     */   }
/*     */ 
/*     */   
/*     */   public void register() {
/*  54 */     this.qe.addQuestLvlUp(2002);
/*  55 */     this.qe.setNpcQuestData(210377).addOnKillEvent(2002);
/*  56 */     this.qe.setNpcQuestData(210378).addOnKillEvent(2002);
/*  57 */     for (int npc_id : npc_ids) {
/*  58 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(2002);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  64 */     final Player player = env.getPlayer();
/*  65 */     final QuestState qs = player.getQuestStateList().getQuestState(2002);
/*  66 */     if (qs == null) {
/*  67 */       return false;
/*     */     }
/*  69 */     int var = qs.getQuestVarById(0);
/*  70 */     int targetId = 0;
/*  71 */     if (env.getVisibleObject() instanceof Npc)
/*  72 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  73 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  75 */       switch (targetId) {
/*     */ 
/*     */         
/*     */         case 203519:
/*  79 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/*  82 */               if (var == 0)
/*  83 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*     */             case 10000:
/*  85 */               if (var == 0) {
/*     */                 
/*  87 */                 qs.setQuestVarById(0, var + 1);
/*  88 */                 updateQuestStatus(player, qs);
/*  89 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  90 */                 return true;
/*     */               } 
/*     */               break;
/*     */           } 
/*     */         
/*     */         case 203534:
/*  96 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/*  99 */               if (var == 1)
/* 100 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */             case 1353:
/* 102 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 52));
/*     */               break;
/*     */             case 10001:
/* 105 */               if (var == 1) {
/*     */                 
/* 107 */                 qs.setQuestVarById(0, var + 1);
/* 108 */                 updateQuestStatus(player, qs);
/* 109 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 110 */                 return true;
/*     */               } 
/*     */               break;
/*     */           } 
/*     */         
/*     */         case 790002:
/* 116 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 119 */               if (var == 2)
/* 120 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 121 */               if (var == 10)
/* 122 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/* 123 */               if (var == 11)
/* 124 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 125 */               if (var == 12)
/* 126 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2462); 
/* 127 */               if (var == 13)
/* 128 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
/*     */             case 10002:
/*     */             case 10003:
/*     */             case 10005:
/* 132 */               if (var == 2 || var == 10) {
/*     */                 
/* 134 */                 qs.setQuestVarById(0, var + 1);
/* 135 */                 updateQuestStatus(player, qs);
/* 136 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 137 */                 return true;
/*     */               } 
/* 139 */               if (var == 13) {
/*     */                 
/* 141 */                 qs.setQuestVarById(0, 14);
/* 142 */                 updateQuestStatus(player, qs);
/* 143 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 144 */                 return true;
/*     */               } 
/*     */               break;
/*     */             case 10004:
/* 148 */               if (var == 12) {
/*     */                 
/* 150 */                 qs.setQuestVarById(0, 99);
/* 151 */                 updateQuestStatus(player, qs);
/* 152 */                 WorldMapInstance newInstance = InstanceService.getNextAvailableInstance(320010000);
/* 153 */                 InstanceService.registerPlayerWithInstance(newInstance, player);
/* 154 */                 TeleportService.teleportTo(player, 320010000, newInstance.getInstanceId(), 457.65F, 426.8F, 230.4F, 75);
/* 155 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 156 */                 return true;
/*     */               } 
/*     */             case 33:
/* 159 */               if (var == 11) {
/*     */                 
/* 161 */                 if (QuestService.collectItemCheck(env, true)) {
/*     */                   
/* 163 */                   qs.setQuestVarById(0, 12);
/* 164 */                   updateQuestStatus(player, qs);
/* 165 */                   return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2461);
/*     */                 } 
/*     */                 
/* 168 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2376);
/*     */               } 
/*     */               break;
/*     */           } 
/*     */           
/*     */           break;
/*     */         case 205020:
/* 175 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 178 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_FLYTELEPORT, 3001, 0));
/* 179 */               ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */                   {
/*     */                     public void run()
/*     */                     {
/* 183 */                       qs.setQuestVar(13);
/* 184 */                       _2002WheresRae.this.updateQuestStatus(player, qs);
/* 185 */                       TeleportService.teleportTo(player, 220010000, 1, 940.15F, 2295.64F, 265.7F, 43);
/*     */                     }
/*     */                   }38000L);
/* 188 */               return true;
/*     */           } 
/* 190 */           return false;
/*     */ 
/*     */         
/*     */         case 700045:
/* 194 */           if (var == 11 && env.getDialogId().intValue() == -1) {
/*     */             
/* 196 */             final int targetObjectId = env.getVisibleObject().getObjectId();
/* 197 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/* 198 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/* 199 */             ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */                 {
/*     */                   public void run()
/*     */                   {
/* 203 */                     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/* 204 */                     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/* 205 */                     SkillEngine.getInstance().getSkill((Creature)player, 8343, 1, (VisibleObject)player).useSkill();
/*     */                   }
/*     */                 }3000L);
/*     */           } 
/* 209 */           return true;
/*     */         case 203537:
/* 211 */           if (var == 14 && env.getDialogId().intValue() == -1) {
/*     */             
/* 213 */             qs.setQuestVarById(0, var + 1);
/* 214 */             updateQuestStatus(player, qs);
/* 215 */             Npc npc = (Npc)env.getVisibleObject();
/* 216 */             QuestService.addNewSpawn(player.getWorldId(), player.getInstanceId(), 203553, npc.getX(), npc.getY(), npc.getZ(), npc.getHeading(), true);
/* 217 */             npc.getController().onDie(null);
/* 218 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 256));
/* 219 */             return true;
/*     */           } 
/*     */           break;
/*     */         case 203553:
/* 223 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 226 */               if (var == 15)
/* 227 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
/*     */             case 10006:
/* 229 */               if (var == 15) {
/*     */                 
/* 231 */                 env.getVisibleObject().getController().delete();
/* 232 */                 qs.setStatus(QuestStatus.REWARD);
/* 233 */                 updateQuestStatus(player, qs);
/* 234 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 235 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */       } 
/* 240 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 242 */       if (targetId == 203516) {
/*     */         
/* 244 */         if (env.getDialogId().intValue() == -1)
/* 245 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3398); 
/* 246 */         if (env.getDialogId().intValue() == 10007) {
/* 247 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */         }
/* 249 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 252 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 258 */     Player player = env.getPlayer();
/* 259 */     QuestState qs = player.getQuestStateList().getQuestState(2002);
/* 260 */     if (qs == null) {
/* 261 */       return false;
/*     */     }
/* 263 */     int var = qs.getQuestVarById(0);
/* 264 */     int targetId = 0;
/* 265 */     if (env.getVisibleObject() instanceof Npc) {
/* 266 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 268 */     if (qs.getStatus() != QuestStatus.START)
/* 269 */       return false; 
/* 270 */     switch (targetId) {
/*     */       
/*     */       case 210377:
/*     */       case 210378:
/* 274 */         if (var >= 3 && var < 10) {
/*     */           
/* 276 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 277 */           updateQuestStatus(player, qs);
/* 278 */           return true;
/*     */         }  break;
/*     */     } 
/* 281 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/* 287 */     Player player = env.getPlayer();
/* 288 */     QuestState qs = player.getQuestStateList().getQuestState(2002);
/* 289 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED)
/* 290 */       return false; 
/* 291 */     qs.setStatus(QuestStatus.START);
/* 292 */     updateQuestStatus(player, qs);
/* 293 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\ishalgen\_2002WheresRae.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */