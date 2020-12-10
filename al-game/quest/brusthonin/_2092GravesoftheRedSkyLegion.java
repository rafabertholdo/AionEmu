/*     */ package quest.brusthonin;
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
/*     */ import com.aionemu.gameserver.world.zone.ZoneName;
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
/*     */ public class _2092GravesoftheRedSkyLegion
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2092;
/*  38 */   private static final int[] npc_ids = new int[] { 205150, 700394, 205188, 205190, 730163, 730162, 730161, 730160, 730159, 730158, 730156, 205208, 205209, 205210, 205211, 205212, 205213, 205214 };
/*     */ 
/*     */   
/*     */   public _2092GravesoftheRedSkyLegion() {
/*  42 */     super(Integer.valueOf(2092));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  48 */     this.qe.setQuestEnterZone(ZoneName.Q2092).add(2092);
/*  49 */     this.qe.addQuestLvlUp(2092);
/*  50 */     this.qe.setNpcQuestData(214402).addOnKillEvent(2092);
/*  51 */     for (int npc_id : npc_ids)
/*  52 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(2092); 
/*  53 */     this.deletebleItems = new int[] { 182209008, 152000857, 152010317 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  59 */     Player player = env.getPlayer();
/*  60 */     QuestState qs = player.getQuestStateList().getQuestState(2092);
/*  61 */     boolean lvlCheck = QuestService.checkLevelRequirement(2092, player.getCommonData().getLevel());
/*  62 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
/*  63 */       return false;
/*     */     }
/*  65 */     QuestState qs2 = player.getQuestStateList().getQuestState(2091);
/*  66 */     if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
/*  67 */       return false; 
/*  68 */     qs.setStatus(QuestStatus.START);
/*  69 */     updateQuestStatus(player, qs);
/*  70 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  76 */     Player player = env.getPlayer();
/*  77 */     QuestState qs = player.getQuestStateList().getQuestState(2092);
/*  78 */     Npc npc = (Npc)env.getVisibleObject();
/*     */     
/*  80 */     if (qs == null) {
/*  81 */       return false;
/*     */     }
/*  83 */     int var = qs.getQuestVarById(0);
/*  84 */     int targetId = 0;
/*  85 */     if (env.getVisibleObject() instanceof Npc) {
/*  86 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  88 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  90 */       if (targetId == 205150)
/*  91 */         return defaultQuestEndDialog(env); 
/*  92 */       return false;
/*     */     } 
/*  94 */     if (qs.getStatus() != QuestStatus.START)
/*     */     {
/*  96 */       return false;
/*     */     }
/*  98 */     if (targetId == 205150) {
/*     */       
/* 100 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 103 */           if (var == 0)
/* 104 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/* 105 */           return true;
/*     */         case 10000:
/* 107 */           if (var == 0) {
/*     */             
/* 109 */             qs.setQuestVarById(0, var + 1);
/* 110 */             updateQuestStatus(player, qs);
/* 111 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 112 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 116 */     } else if (targetId == 205188) {
/*     */       
/* 118 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 121 */           if (var == 2)
/* 122 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*     */         case 10002:
/* 124 */           if (var == 2) {
/*     */             
/* 126 */             qs.setQuestVarById(0, var + 1);
/* 127 */             updateQuestStatus(player, qs);
/* 128 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 129 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 133 */     } else if (targetId == 205190) {
/*     */       
/* 135 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 138 */           if (var == 3)
/* 139 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/* 140 */           if (var == 4)
/* 141 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 142 */           return true;
/*     */         case 10003:
/* 144 */           if (var == 3) {
/*     */             
/* 146 */             qs.setQuestVarById(0, var + 1);
/* 147 */             updateQuestStatus(player, qs);
/* 148 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 149 */             return true;
/*     */           } 
/*     */         case 33:
/* 152 */           if (var == 4) {
/*     */             
/* 154 */             if (QuestService.collectItemCheck(env, true)) {
/*     */               
/* 156 */               qs.setQuestVarById(0, var + 1);
/* 157 */               updateQuestStatus(player, qs);
/* 158 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10000);
/*     */             } 
/* 160 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
/*     */           } 
/*     */           break;
/*     */       } 
/* 164 */     } else if (targetId == 730163) {
/*     */       
/* 166 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case -1:
/* 169 */           if (var == 5) {
/*     */             
/* 171 */             QuestService.addNewSpawn(220050000, 1, 205214, npc.getX(), npc.getY(), npc.getZ(), (byte)0, true);
/*     */             
/* 173 */             npc.getController().onDespawn(true);
/* 174 */             npc.getController().scheduleRespawn();
/* 175 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 179 */     } else if (targetId == 205214) {
/*     */       
/* 181 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 184 */           if (var == 5)
/* 185 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2717); 
/*     */         case 10005:
/* 187 */           if (var == 5) {
/*     */             
/* 189 */             qs.setQuestVarById(0, var + 1);
/* 190 */             updateQuestStatus(player, qs);
/* 191 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 192 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 196 */     } else if (targetId == 730162) {
/*     */       
/* 198 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case -1:
/* 201 */           if (var == 5) {
/*     */             
/* 203 */             QuestService.addNewSpawn(220050000, 1, 205213, npc.getX(), npc.getY(), npc.getZ(), (byte)0, true);
/*     */             
/* 205 */             npc.getController().onDespawn(true);
/* 206 */             npc.getController().scheduleRespawn();
/* 207 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 211 */     } else if (targetId == 205213) {
/*     */       
/* 213 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 216 */           if (var == 5)
/* 217 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2717); 
/*     */         case 10005:
/* 219 */           if (var == 5) {
/*     */             
/* 221 */             qs.setQuestVarById(0, var + 1);
/* 222 */             updateQuestStatus(player, qs);
/* 223 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 224 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 228 */     } else if (targetId == 730161) {
/*     */       
/* 230 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case -1:
/* 233 */           if (var == 5) {
/*     */             
/* 235 */             QuestService.addNewSpawn(220050000, 1, 205212, npc.getX(), npc.getY(), npc.getZ(), (byte)0, true);
/*     */             
/* 237 */             npc.getController().onDespawn(true);
/* 238 */             npc.getController().scheduleRespawn();
/* 239 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 243 */     } else if (targetId == 205212) {
/*     */       
/* 245 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 248 */           if (var == 5)
/* 249 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2717); 
/*     */         case 10005:
/* 251 */           if (var == 5) {
/*     */             
/* 253 */             qs.setQuestVarById(0, var + 1);
/* 254 */             updateQuestStatus(player, qs);
/* 255 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 256 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 260 */     } else if (targetId == 730160) {
/*     */       
/* 262 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case -1:
/* 265 */           if (var == 5) {
/*     */             
/* 267 */             QuestService.addNewSpawn(220050000, 1, 205211, npc.getX(), npc.getY(), npc.getZ(), (byte)0, true);
/*     */             
/* 269 */             npc.getController().onDespawn(true);
/* 270 */             npc.getController().scheduleRespawn();
/* 271 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 275 */     } else if (targetId == 205211) {
/*     */       
/* 277 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 280 */           if (var == 5)
/* 281 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2717); 
/*     */         case 10005:
/* 283 */           if (var == 5) {
/*     */             
/* 285 */             qs.setQuestVarById(0, var + 1);
/* 286 */             updateQuestStatus(player, qs);
/* 287 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 288 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 292 */     } else if (targetId == 730159) {
/*     */       
/* 294 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case -1:
/* 297 */           if (var == 5) {
/*     */             
/* 299 */             QuestService.addNewSpawn(220050000, 1, 205210, npc.getX(), npc.getY(), npc.getZ(), (byte)0, true);
/*     */             
/* 301 */             npc.getController().onDespawn(true);
/* 302 */             npc.getController().scheduleRespawn();
/* 303 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 307 */     } else if (targetId == 205210) {
/*     */       
/* 309 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 312 */           if (var == 5)
/* 313 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2717); 
/*     */         case 10005:
/* 315 */           if (var == 5) {
/*     */             
/* 317 */             qs.setQuestVarById(0, var + 1);
/* 318 */             updateQuestStatus(player, qs);
/* 319 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 320 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 324 */     } else if (targetId == 730158) {
/*     */       
/* 326 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case -1:
/* 329 */           if (var == 5) {
/*     */             
/* 331 */             QuestService.addNewSpawn(220050000, 1, 205209, npc.getX(), npc.getY(), npc.getZ(), (byte)0, true);
/*     */             
/* 333 */             npc.getController().onDespawn(true);
/* 334 */             npc.getController().scheduleRespawn();
/* 335 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 339 */     } else if (targetId == 205209) {
/*     */       
/* 341 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 344 */           if (var == 5)
/* 345 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2717); 
/*     */         case 10005:
/* 347 */           if (var == 5) {
/*     */             
/* 349 */             qs.setQuestVarById(0, var + 1);
/* 350 */             updateQuestStatus(player, qs);
/* 351 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 352 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 356 */     } else if (targetId == 730156) {
/*     */       
/* 358 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case -1:
/* 361 */           if (var == 5) {
/*     */             
/* 363 */             QuestService.addNewSpawn(220050000, 1, 205208, npc.getX(), npc.getY(), npc.getZ(), (byte)0, true);
/*     */             
/* 365 */             npc.getController().onDespawn(true);
/* 366 */             npc.getController().scheduleRespawn();
/* 367 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 371 */     } else if (targetId == 205208) {
/*     */       
/* 373 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 376 */           if (var == 5)
/* 377 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2717); 
/*     */         case 10005:
/* 379 */           if (var == 5) {
/*     */             
/* 381 */             qs.setQuestVarById(0, var + 1);
/* 382 */             updateQuestStatus(player, qs);
/* 383 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 384 */             return true;
/*     */           }  break;
/*     */       } 
/*     */     } 
/* 388 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
/* 394 */     if (zoneName != ZoneName.Q2092)
/* 395 */       return false; 
/* 396 */     Player player = env.getPlayer();
/* 397 */     QuestState qs = player.getQuestStateList().getQuestState(2092);
/* 398 */     if (qs == null || qs.getQuestVars().getQuestVars() != 1)
/* 399 */       return false; 
/* 400 */     env.setQuestId(Integer.valueOf(2092));
/* 401 */     qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 402 */     updateQuestStatus(player, qs);
/* 403 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 410 */     Player player = env.getPlayer();
/* 411 */     QuestState qs = player.getQuestStateList().getQuestState(2092);
/* 412 */     if (qs == null || qs.getStatus() != QuestStatus.START) {
/* 413 */       return false;
/*     */     }
/* 415 */     int var = qs.getQuestVarById(0);
/* 416 */     int targetId = 0;
/* 417 */     if (env.getVisibleObject() instanceof Npc) {
/* 418 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 420 */     if (targetId == 214402) {
/*     */       
/* 422 */       if (var > 5 && var < 20) {
/*     */         
/* 424 */         qs.setQuestVarById(0, var + 1);
/* 425 */         updateQuestStatus(player, qs);
/* 426 */         return true;
/*     */       } 
/* 428 */       if (var == 20) {
/*     */         
/* 430 */         qs.setStatus(QuestStatus.REWARD);
/* 431 */         updateQuestStatus(player, qs);
/* 432 */         return true;
/*     */       } 
/*     */     } 
/* 435 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\brusthonin\_2092GravesoftheRedSkyLegion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */