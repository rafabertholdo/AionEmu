/*     */ package quest.ishalgen;
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
/*     */ public class _2098ButWhatweMake
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2098;
/*     */   
/*     */   public _2098ButWhatweMake() {
/*  40 */     super(Integer.valueOf(2098));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  46 */     this.qe.addQuestLvlUp(2098);
/*  47 */     this.qe.setNpcQuestData(203550).addOnTalkEvent(2098);
/*  48 */     this.qe.setNpcQuestData(204361).addOnTalkEvent(2098);
/*  49 */     this.qe.setNpcQuestData(204408).addOnTalkEvent(2098);
/*  50 */     this.qe.setNpcQuestData(205198).addOnTalkEvent(2098);
/*  51 */     this.qe.setNpcQuestData(204805).addOnTalkEvent(2098);
/*  52 */     this.qe.setNpcQuestData(204808).addOnTalkEvent(2098);
/*  53 */     this.qe.setNpcQuestData(203546).addOnTalkEvent(2098);
/*  54 */     this.qe.setNpcQuestData(204387).addOnTalkEvent(2098);
/*  55 */     this.qe.setNpcQuestData(205190).addOnTalkEvent(2098);
/*  56 */     this.qe.setNpcQuestData(204207).addOnTalkEvent(2098);
/*  57 */     this.qe.setNpcQuestData(204301).addOnTalkEvent(2098);
/*  58 */     this.qe.setNpcQuestData(205155).addOnTalkEvent(2098);
/*  59 */     this.qe.setNpcQuestData(204784).addOnTalkEvent(2098);
/*  60 */     this.qe.setNpcQuestData(278001).addOnTalkEvent(2098);
/*  61 */     this.qe.setNpcQuestData(204053).addOnTalkEvent(2098);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  67 */     Player player = env.getPlayer();
/*  68 */     QuestState qs = player.getQuestStateList().getQuestState(2098);
/*  69 */     if (qs != null)
/*  70 */       return false; 
/*  71 */     boolean lvlCheck = QuestService.checkLevelRequirement(2098, player.getCommonData().getLevel());
/*  72 */     if (!lvlCheck)
/*  73 */       return false; 
/*  74 */     env.setQuestId(Integer.valueOf(2098));
/*  75 */     QuestService.startQuest(env, QuestStatus.START);
/*  76 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  82 */     Player player = env.getPlayer();
/*  83 */     QuestState qs = player.getQuestStateList().getQuestState(2098);
/*  84 */     if (qs == null) {
/*  85 */       return false;
/*     */     }
/*  87 */     int var = qs.getQuestVars().getQuestVars();
/*  88 */     int targetId = 0;
/*  89 */     if (env.getVisibleObject() instanceof Npc) {
/*  90 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  92 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  94 */       switch (targetId) {
/*     */         
/*     */         case 203550:
/*  97 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 100 */               if (var == 0)
/* 101 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*     */             case 10000:
/* 103 */               if (var == 0) {
/*     */                 
/* 105 */                 qs.setQuestVarById(0, var + 1);
/* 106 */                 updateQuestStatus(player, qs);
/* 107 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 108 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         case 204361:
/* 113 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 116 */               if (var == 1)
/* 117 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */             case 10001:
/* 119 */               if (var == 1) {
/*     */                 
/* 121 */                 qs.setQuestVarById(0, var + 1);
/* 122 */                 updateQuestStatus(player, qs);
/* 123 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 124 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         case 204408:
/* 129 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 132 */               if (var == 2)
/* 133 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 134 */               return true;
/*     */             case 10002:
/* 136 */               if (var == 2) {
/*     */                 
/* 138 */                 qs.setQuestVarById(0, var + 1);
/* 139 */                 updateQuestStatus(player, qs);
/* 140 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 141 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         case 205198:
/* 146 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 149 */               if (var == 3)
/* 150 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/* 151 */               return true;
/*     */             case 10003:
/* 153 */               if (var == 3) {
/*     */                 
/* 155 */                 qs.setQuestVarById(0, var + 1);
/* 156 */                 updateQuestStatus(player, qs);
/* 157 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 158 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         case 204805:
/* 163 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 166 */               if (var == 4)
/* 167 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 168 */               return true;
/*     */             case 10004:
/* 170 */               if (var == 4) {
/*     */                 
/* 172 */                 qs.setQuestVarById(0, var + 1);
/* 173 */                 updateQuestStatus(player, qs);
/* 174 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 175 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         case 204808:
/* 180 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 183 */               if (var == 5)
/* 184 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
/* 185 */               return true;
/*     */             case 10005:
/* 187 */               if (var == 5) {
/*     */                 
/* 189 */                 qs.setQuestVarById(0, var + 1);
/* 190 */                 updateQuestStatus(player, qs);
/* 191 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 192 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         case 203546:
/* 197 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 200 */               if (var == 6)
/* 201 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
/* 202 */               return true;
/*     */             case 10006:
/* 204 */               if (var == 6) {
/*     */                 
/* 206 */                 qs.setQuestVarById(0, var + 1);
/* 207 */                 updateQuestStatus(player, qs);
/* 208 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 209 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         case 204387:
/* 214 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 217 */               if (var == 7)
/* 218 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3398); 
/* 219 */               return true;
/*     */             case 10007:
/* 221 */               if (var == 7) {
/*     */                 
/* 223 */                 qs.setQuestVarById(0, var + 1);
/* 224 */                 updateQuestStatus(player, qs);
/* 225 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 226 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         case 205190:
/* 231 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 234 */               if (var == 8)
/* 235 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3739); 
/* 236 */               return true;
/*     */             case 10008:
/* 238 */               if (var == 8) {
/*     */                 
/* 240 */                 qs.setQuestVarById(0, var + 1);
/* 241 */                 updateQuestStatus(player, qs);
/* 242 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 243 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         case 204207:
/* 248 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 251 */               if (var == 9)
/* 252 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4080); 
/* 253 */               return true;
/*     */             case 10009:
/* 255 */               if (var == 9) {
/*     */                 
/* 257 */                 qs.setQuestVarById(0, var + 1);
/* 258 */                 updateQuestStatus(player, qs);
/* 259 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 260 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         case 204301:
/* 265 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 268 */               if (var == 10)
/* 269 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1608); 
/* 270 */               return true;
/*     */             case 10010:
/* 272 */               if (var == 10) {
/*     */                 
/* 274 */                 qs.setQuestVarById(0, var + 1);
/* 275 */                 updateQuestStatus(player, qs);
/* 276 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 277 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         case 205155:
/* 282 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 285 */               if (var == 11)
/* 286 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1949); 
/* 287 */               return true;
/*     */             case 10011:
/* 289 */               if (var == 11) {
/*     */                 
/* 291 */                 qs.setQuestVarById(0, var + 1);
/* 292 */                 updateQuestStatus(player, qs);
/* 293 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 294 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         case 204784:
/* 299 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 302 */               if (var == 12)
/* 303 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2290); 
/* 304 */               return true;
/*     */             case 10012:
/* 306 */               if (var == 12) {
/*     */                 
/* 308 */                 qs.setQuestVarById(0, var + 1);
/* 309 */                 updateQuestStatus(player, qs);
/* 310 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 311 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         case 278001:
/* 316 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 319 */               if (var == 13)
/* 320 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2631); 
/* 321 */               return true;
/*     */             case 10013:
/* 323 */               if (var == 13) {
/*     */                 
/* 325 */                 qs.setQuestVarById(0, var + 1);
/* 326 */                 updateQuestStatus(player, qs);
/* 327 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 328 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         case 204053:
/* 333 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 336 */               if (var == 14)
/* 337 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2972); 
/* 338 */               return true;
/*     */             case 10255:
/* 340 */               if (var == 14) {
/*     */                 
/* 342 */                 qs.setStatus(QuestStatus.REWARD);
/* 343 */                 updateQuestStatus(player, qs);
/* 344 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */               } 
/*     */               break;
/*     */           } 
/*     */           break;
/*     */       } 
/* 350 */     } else if (qs.getStatus() == QuestStatus.REWARD && targetId == 203550) {
/* 351 */       return defaultQuestEndDialog(env);
/* 352 */     }  return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\ishalgen\_2098ButWhatweMake.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */