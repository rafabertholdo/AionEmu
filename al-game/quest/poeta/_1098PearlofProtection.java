/*     */ package quest.poeta;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import java.util.Collections;
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
/*     */ 
/*     */ public class _1098PearlofProtection
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1098;
/*     */   
/*     */   public _1098PearlofProtection() {
/*  43 */     super(Integer.valueOf(1098));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  49 */     this.qe.addQuestLvlUp(1098);
/*  50 */     this.qe.setNpcQuestData(790001).addOnQuestStart(1098);
/*  51 */     this.qe.setNpcQuestData(790001).addOnTalkEvent(1098);
/*  52 */     this.qe.setNpcQuestData(730008).addOnTalkEvent(1098);
/*  53 */     this.qe.setNpcQuestData(730019).addOnTalkEvent(1098);
/*  54 */     this.qe.setNpcQuestData(730133).addOnTalkEvent(1098);
/*  55 */     this.qe.setNpcQuestData(203183).addOnTalkEvent(1098);
/*  56 */     this.qe.setNpcQuestData(203989).addOnTalkEvent(1098);
/*  57 */     this.qe.setNpcQuestData(798155).addOnTalkEvent(1098);
/*  58 */     this.qe.setNpcQuestData(204549).addOnTalkEvent(1098);
/*  59 */     this.qe.setNpcQuestData(203752).addOnTalkEvent(1098);
/*  60 */     this.qe.setNpcQuestData(203164).addOnTalkEvent(1098);
/*  61 */     this.qe.setNpcQuestData(203917).addOnTalkEvent(1098);
/*  62 */     this.qe.setNpcQuestData(203996).addOnTalkEvent(1098);
/*  63 */     this.qe.setNpcQuestData(798176).addOnTalkEvent(1098);
/*  64 */     this.qe.setNpcQuestData(798212).addOnTalkEvent(1098);
/*  65 */     this.qe.setNpcQuestData(204535).addOnTalkEvent(1098);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  71 */     Player player = env.getPlayer();
/*  72 */     QuestState qs = player.getQuestStateList().getQuestState(1098);
/*  73 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED) {
/*  74 */       return false;
/*     */     }
/*  76 */     QuestState qs2 = player.getQuestStateList().getQuestState(1097);
/*  77 */     if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
/*  78 */       return false; 
/*  79 */     qs.setStatus(QuestStatus.START);
/*  80 */     updateQuestStatus(player, qs);
/*  81 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  87 */     Player player = env.getPlayer();
/*  88 */     int targetId = 0;
/*  89 */     if (env.getVisibleObject() instanceof Npc)
/*  90 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  91 */     QuestState qs = player.getQuestStateList().getQuestState(1098);
/*  92 */     if (targetId == 790001) {
/*     */       
/*  94 */       if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */         
/*  96 */         if (env.getDialogId().intValue() == 25)
/*  97 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*  98 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/* 100 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 101 */           updateQuestStatus(player, qs);
/* 102 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 104 */           if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182206062, 1))));
/*     */           
/* 106 */           return true;
/*     */         } 
/*     */ 
/*     */         
/* 110 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */       
/* 113 */       if (qs != null && qs.getStatus() == QuestStatus.REWARD)
/*     */       {
/* 115 */         if (env.getDialogId().intValue() == 25)
/* 116 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002); 
/* 117 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/* 119 */           qs.setQuestVar(14);
/* 120 */           qs.setStatus(QuestStatus.REWARD);
/* 121 */           updateQuestStatus(player, qs);
/* 122 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/* 125 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     
/*     */     }
/* 129 */     else if (targetId == 730008) {
/*     */ 
/*     */       
/* 132 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1)
/*     */       {
/* 134 */         if (env.getDialogId().intValue() == 25)
/* 135 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 136 */         if (env.getDialogId().intValue() == 10001) {
/*     */           
/* 138 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 139 */           updateQuestStatus(player, qs);
/* 140 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 142 */           return true;
/*     */         } 
/*     */         
/* 145 */         return defaultQuestStartDialog(env);
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 150 */     else if (targetId == 730019) {
/*     */       
/* 152 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 2)
/*     */       {
/* 154 */         if (env.getDialogId().intValue() == 25)
/* 155 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 156 */         if (env.getDialogId().intValue() == 10002) {
/*     */           
/* 158 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 159 */           updateQuestStatus(player, qs);
/* 160 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 162 */           return true;
/*     */         } 
/*     */         
/* 165 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*     */     }
/* 169 */     else if (targetId == 730133) {
/*     */       
/* 171 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 3)
/*     */       {
/* 173 */         if (env.getDialogId().intValue() == 25)
/* 174 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/* 175 */         if (env.getDialogId().intValue() == 10003) {
/*     */           
/* 177 */           ItemService.removeItemFromInventoryByItemId(player, 182206062);
/* 178 */           qs.setQuestVar(4);
/* 179 */           updateQuestStatus(player, qs);
/* 180 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 182 */           if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182206063, 1))));
/*     */           
/* 184 */           return true;
/*     */         } 
/*     */         
/* 187 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*     */     }
/* 191 */     else if (targetId == 203183) {
/*     */       
/* 193 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 4)
/*     */       {
/* 195 */         if (env.getDialogId().intValue() == 25)
/* 196 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 197 */         if (env.getDialogId().intValue() == 10004) {
/*     */           
/* 199 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 200 */           updateQuestStatus(player, qs);
/* 201 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 203 */           return true;
/*     */         } 
/*     */         
/* 206 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*     */     }
/* 210 */     else if (targetId == 203989) {
/*     */       
/* 212 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 5)
/*     */       {
/* 214 */         if (env.getDialogId().intValue() == 25)
/* 215 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
/* 216 */         if (env.getDialogId().intValue() == 10005) {
/*     */           
/* 218 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 219 */           updateQuestStatus(player, qs);
/* 220 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 222 */           return true;
/*     */         } 
/*     */         
/* 225 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*     */     }
/* 229 */     else if (targetId == 798155) {
/*     */       
/* 231 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 6)
/*     */       {
/* 233 */         if (env.getDialogId().intValue() == 25)
/* 234 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
/* 235 */         if (env.getDialogId().intValue() == 10006) {
/*     */           
/* 237 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 238 */           updateQuestStatus(player, qs);
/* 239 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 241 */           return true;
/*     */         } 
/*     */         
/* 244 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*     */     }
/* 248 */     else if (targetId == 204549) {
/*     */       
/* 250 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 7)
/*     */       {
/* 252 */         if (env.getDialogId().intValue() == 25)
/* 253 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3398); 
/* 254 */         if (env.getDialogId().intValue() == 10007) {
/*     */           
/* 256 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 257 */           updateQuestStatus(player, qs);
/* 258 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 260 */           return true;
/*     */         } 
/*     */         
/* 263 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*     */     }
/* 267 */     else if (targetId == 203752) {
/*     */       
/* 269 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 8)
/*     */       {
/* 271 */         if (env.getDialogId().intValue() == 25)
/* 272 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3739); 
/* 273 */         if (env.getDialogId().intValue() == 10008) {
/*     */           
/* 275 */           ItemService.removeItemFromInventoryByItemId(player, 182206063);
/* 276 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 277 */           updateQuestStatus(player, qs);
/* 278 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 280 */           if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182206064, 1))));
/*     */           
/* 282 */           return true;
/*     */         } 
/*     */         
/* 285 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*     */     }
/* 289 */     else if (targetId == 203164) {
/*     */       
/* 291 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 9)
/*     */       {
/* 293 */         if (env.getDialogId().intValue() == 25)
/* 294 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4080); 
/* 295 */         if (env.getDialogId().intValue() == 10009) {
/*     */           
/* 297 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 298 */           updateQuestStatus(player, qs);
/* 299 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 301 */           return true;
/*     */         } 
/*     */         
/* 304 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*     */     }
/* 308 */     else if (targetId == 203917) {
/*     */       
/* 310 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 10)
/*     */       {
/* 312 */         if (env.getDialogId().intValue() == 25)
/* 313 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1608); 
/* 314 */         if (env.getDialogId().intValue() == 10010) {
/*     */           
/* 316 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 317 */           updateQuestStatus(player, qs);
/* 318 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 320 */           return true;
/*     */         } 
/*     */         
/* 323 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*     */     }
/* 327 */     else if (targetId == 203996) {
/*     */       
/* 329 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 11)
/*     */       {
/* 331 */         if (env.getDialogId().intValue() == 25)
/* 332 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1949); 
/* 333 */         if (env.getDialogId().intValue() == 10011) {
/*     */           
/* 335 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 336 */           updateQuestStatus(player, qs);
/* 337 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 339 */           return true;
/*     */         } 
/*     */         
/* 342 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*     */     }
/* 346 */     else if (targetId == 798176) {
/*     */       
/* 348 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 12)
/*     */       {
/* 350 */         if (env.getDialogId().intValue() == 25)
/* 351 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2290); 
/* 352 */         if (env.getDialogId().intValue() == 10012) {
/*     */           
/* 354 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 355 */           updateQuestStatus(player, qs);
/* 356 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 358 */           return true;
/*     */         } 
/*     */         
/* 361 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*     */     }
/* 365 */     else if (targetId == 798212) {
/*     */       
/* 367 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 13)
/*     */       {
/* 369 */         if (env.getDialogId().intValue() == 25)
/* 370 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2631); 
/* 371 */         if (env.getDialogId().intValue() == 10013) {
/*     */           
/* 373 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 374 */           updateQuestStatus(player, qs);
/* 375 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 377 */           return true;
/*     */         } 
/*     */         
/* 380 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*     */     }
/* 384 */     else if (targetId == 204535) {
/*     */       
/* 386 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 14) {
/*     */         
/* 388 */         if (env.getDialogId().intValue() == 25)
/* 389 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2972); 
/* 390 */         if (env.getDialogId().intValue() == 10255) {
/*     */           
/* 392 */           ItemService.removeItemFromInventoryByItemId(player, 182206064);
/* 393 */           qs.setStatus(QuestStatus.REWARD);
/* 394 */           updateQuestStatus(player, qs);
/* 395 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 397 */           if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182206065, 1))));
/*     */           
/* 399 */           return true;
/*     */         } 
/*     */         
/* 402 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */     } 
/* 405 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\poeta\_1098PearlofProtection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */