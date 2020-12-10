/*     */ package quest.heiron;
/*     */ 
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
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
/*     */ public class _1055EternalRest
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1055;
/*  45 */   private static final int[] npc_ids = new int[] { 204629, 204625, 204628, 204627, 204626, 204622, 700270 };
/*     */ 
/*     */   
/*     */   public _1055EternalRest() {
/*  49 */     super(Integer.valueOf(1055));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  55 */     this.qe.addQuestLvlUp(1055);
/*  56 */     for (int npc_id : npc_ids) {
/*  57 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1055);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  63 */     Player player = env.getPlayer();
/*  64 */     QuestState qs = player.getQuestStateList().getQuestState(1055);
/*  65 */     boolean lvlCheck = QuestService.checkLevelRequirement(1055, player.getCommonData().getLevel());
/*  66 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
/*  67 */       return false;
/*     */     }
/*  69 */     QuestState qs2 = player.getQuestStateList().getQuestState(1500);
/*  70 */     if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
/*  71 */       return false; 
/*  72 */     qs.setStatus(QuestStatus.START);
/*  73 */     updateQuestStatus(player, qs);
/*  74 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  80 */     final Player player = env.getPlayer();
/*  81 */     final QuestState qs = player.getQuestStateList().getQuestState(1055);
/*  82 */     if (qs == null) {
/*  83 */       return false;
/*     */     }
/*  85 */     int var = qs.getQuestVarById(0);
/*  86 */     int targetId = 0;
/*  87 */     if (env.getVisibleObject() instanceof Npc) {
/*  88 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  90 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  92 */       if (targetId == 204629) {
/*  93 */         return defaultQuestEndDialog(env);
/*     */       }
/*  95 */     } else if (qs.getStatus() != QuestStatus.START) {
/*     */       
/*  97 */       return false;
/*     */     } 
/*  99 */     if (targetId == 204629) {
/*     */       
/* 101 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 104 */           if (var == 0)
/* 105 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/* 106 */           if (var == 2)
/* 107 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*     */         case 10000:
/* 109 */           if (var == 0) {
/*     */             
/* 111 */             qs.setQuestVarById(0, var + 1);
/* 112 */             updateQuestStatus(player, qs);
/* 113 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 114 */             return true;
/*     */           } 
/*     */         case 10001:
/* 117 */           if (var == 1) {
/*     */             
/* 119 */             qs.setQuestVarById(0, var + 1);
/* 120 */             updateQuestStatus(player, qs);
/* 121 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 122 */             return true;
/*     */           } 
/* 124 */           return false;
/*     */       } 
/*     */     
/* 127 */     } else if (targetId == 204625) {
/*     */       
/* 129 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 132 */           if (var == 1)
/* 133 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 134 */           if (var == 2)
/* 135 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 136 */           if (var == 4)
/* 137 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*     */         case 33:
/* 139 */           if (QuestService.collectItemCheck(env, true)) {
/*     */             
/* 141 */             qs.setQuestVarById(0, var + 1);
/* 142 */             updateQuestStatus(player, qs);
/* 143 */             ItemService.addItems(player, Collections.singletonList(new QuestItems(182201613, 1)));
/* 144 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10000);
/*     */           } 
/*     */           
/* 147 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
/*     */         case 10001:
/* 149 */           if (var == 1) {
/*     */             
/* 151 */             qs.setQuestVarById(0, var + 1);
/* 152 */             updateQuestStatus(player, qs);
/* 153 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 154 */             return true;
/*     */           } 
/*     */         case 10255:
/* 157 */           if (var == 4) {
/*     */             
/* 159 */             qs.setStatus(QuestStatus.REWARD);
/* 160 */             updateQuestStatus(player, qs);
/* 161 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 162 */             return true;
/*     */           } 
/* 164 */           return false;
/*     */       } 
/*     */     
/* 167 */     } else if (targetId == 204628) {
/*     */       
/* 169 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 172 */           if (var == 2)
/* 173 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1694); 
/*     */         case 10002:
/* 175 */           if (var == 2) {
/*     */             
/* 177 */             if (player.getInventory().getItemCountByItemId(182201609) == 0L)
/* 178 */               ItemService.addItems(player, Collections.singletonList(new QuestItems(182201609, 1))); 
/* 179 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 180 */             return true;
/*     */           } 
/* 182 */           return false;
/*     */       } 
/*     */     
/* 185 */     } else if (targetId == 204627) {
/*     */       
/* 187 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 190 */           if (var == 2)
/* 191 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1781); 
/*     */         case 10002:
/* 193 */           if (var == 2) {
/*     */             
/* 195 */             if (player.getInventory().getItemCountByItemId(182201610) == 0L)
/* 196 */               ItemService.addItems(player, Collections.singletonList(new QuestItems(182201610, 1))); 
/* 197 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 198 */             return true;
/*     */           } 
/* 200 */           return false;
/*     */       } 
/*     */     
/* 203 */     } else if (targetId == 204626) {
/*     */       
/* 205 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 208 */           if (var == 2)
/* 209 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1864); 
/*     */         case 10002:
/* 211 */           if (var == 2) {
/*     */             
/* 213 */             if (player.getInventory().getItemCountByItemId(182201611) == 0L)
/* 214 */               ItemService.addItems(player, Collections.singletonList(new QuestItems(182201611, 1))); 
/* 215 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 216 */             return true;
/*     */           } 
/* 218 */           return false;
/*     */       } 
/*     */     
/* 221 */     } else if (targetId == 204622) {
/*     */       
/* 223 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 226 */           if (var == 2)
/* 227 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1949); 
/*     */         case 10002:
/* 229 */           if (var == 2) {
/*     */             
/* 231 */             if (player.getInventory().getItemCountByItemId(182201612) == 0L)
/* 232 */               ItemService.addItems(player, Collections.singletonList(new QuestItems(182201612, 1))); 
/* 233 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 234 */             return true;
/*     */           } 
/* 236 */           return false;
/*     */       } 
/*     */     
/* 239 */     } else if (targetId == 700270 && qs.getQuestVarById(0) == 3) {
/*     */       
/* 241 */       if (env.getDialogId().intValue() == -1) {
/*     */         
/* 243 */         final int targetObjectId = env.getVisibleObject().getObjectId();
/* 244 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/* 245 */         PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/* 246 */         ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 250 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/* 251 */                 PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/* 252 */                 ItemService.decreaseItemCountByItemId(player, 182201613, 1L);
/* 253 */                 qs.setQuestVarById(0, 4);
/* 254 */                 _1055EternalRest.this.updateQuestStatus(player, qs);
/*     */               }
/*     */             }3000L);
/*     */       } 
/*     */     } 
/* 259 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\heiron\_1055EternalRest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */