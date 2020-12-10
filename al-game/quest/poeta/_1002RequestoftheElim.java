/*     */ package quest.poeta;
/*     */ 
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ASCENSION_MORPH;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.InstanceService;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.services.QuestService;
/*     */ import com.aionemu.gameserver.services.TeleportService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import com.aionemu.gameserver.world.WorldMapInstance;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
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
/*     */ public class _1002RequestoftheElim
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1002;
/*     */   
/*     */   public _1002RequestoftheElim() {
/*  56 */     super(Integer.valueOf(1002));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  62 */     this.qe.addQuestLvlUp(1002);
/*  63 */     this.qe.addOnEnterWorld(1002);
/*  64 */     this.qe.setNpcQuestData(203076).addOnTalkEvent(1002);
/*  65 */     this.qe.setNpcQuestData(730007).addOnTalkEvent(1002);
/*  66 */     this.qe.setNpcQuestData(730010).addOnTalkEvent(1002);
/*  67 */     this.qe.setNpcQuestData(730008).addOnTalkEvent(1002);
/*  68 */     this.qe.setNpcQuestData(205000).addOnTalkEvent(1002);
/*  69 */     this.qe.setNpcQuestData(203067).addOnTalkEvent(1002);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  75 */     Player player = env.getPlayer();
/*  76 */     QuestState qs = player.getQuestStateList().getQuestState(1002);
/*  77 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED)
/*  78 */       return false; 
/*  79 */     qs.setStatus(QuestStatus.START);
/*  80 */     updateQuestStatus(player, qs);
/*  81 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  87 */     final Player player = env.getPlayer();
/*  88 */     final QuestState qs = player.getQuestStateList().getQuestState(1002);
/*  89 */     if (qs == null) {
/*  90 */       return false;
/*     */     }
/*  92 */     int var = qs.getQuestVarById(0);
/*  93 */     int targetId = 0;
/*  94 */     if (env.getVisibleObject() instanceof Npc) {
/*  95 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  97 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  99 */       if (targetId == 203067) {
/*     */         
/* 101 */         if (env.getDialogId().intValue() == -1)
/* 102 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
/* 103 */         if (env.getDialogId().intValue() == 1009) {
/* 104 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */         }
/* 106 */         return defaultQuestEndDialog(env);
/*     */       } 
/* 108 */       return false;
/*     */     } 
/* 110 */     if (qs.getStatus() != QuestStatus.START)
/*     */     {
/* 112 */       return false;
/*     */     }
/* 114 */     if (targetId == 203076) {
/*     */       
/* 116 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 119 */           if (var == 0)
/* 120 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/* 121 */           return false;
/*     */         case 10000:
/* 123 */           if (var == 0) {
/*     */             
/* 125 */             qs.setQuestVarById(0, var + 1);
/* 126 */             updateQuestStatus(player, qs);
/* 127 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 128 */             return true;
/*     */           } 
/* 130 */           return false;
/*     */       } 
/* 132 */       return false;
/*     */     } 
/*     */     
/* 135 */     if (targetId == 730007) {
/*     */       
/* 137 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 140 */           if (var == 1)
/* 141 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 142 */           if (var == 5) {
/*     */             
/* 144 */             ItemService.decreaseItemCountByItemId(player, 182200002, 1L);
/* 145 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
/*     */           } 
/* 147 */           if (var == 6)
/* 148 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/* 149 */           return false;
/*     */         case 33:
/* 151 */           if (var == 6) {
/*     */             
/* 153 */             if (QuestService.collectItemCheck(env, true)) {
/*     */               
/* 155 */               qs.setQuestVarById(0, 12);
/* 156 */               updateQuestStatus(player, qs);
/* 157 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2120);
/*     */             } 
/*     */             
/* 160 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2205);
/*     */           } 
/* 162 */           if (var == 12)
/* 163 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2120); 
/* 164 */           return false;
/*     */         case 1353:
/* 166 */           if (var == 1)
/* 167 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 20)); 
/* 168 */           return false;
/*     */         case 10001:
/* 170 */           if (var == 1) {
/*     */             
/* 172 */             if (player.getInventory().getItemCountByItemId(182200002) == 0L && 
/* 173 */               !ItemService.addItems(player, Collections.singletonList(new QuestItems(182200002, 1))))
/* 174 */               return true; 
/* 175 */             qs.setQuestVarById(0, var + 1);
/* 176 */             updateQuestStatus(player, qs);
/* 177 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 178 */             return true;
/*     */           } 
/* 180 */           return false;
/*     */         case 10002:
/* 182 */           if (var == 5) {
/*     */             
/* 184 */             qs.setQuestVarById(0, var + 1);
/* 185 */             updateQuestStatus(player, qs);
/* 186 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 187 */             return true;
/*     */           } 
/* 189 */           return false;
/*     */         case 10003:
/* 191 */           if (var == 12) {
/*     */             
/* 193 */             qs.setQuestVarById(0, var + 1);
/* 194 */             updateQuestStatus(player, qs);
/* 195 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 196 */             return true;
/*     */           } 
/* 198 */           return false;
/*     */       } 
/* 200 */       return false;
/*     */     } 
/*     */     
/* 203 */     if (targetId == 730010 && var >= 2 && var < 5) {
/*     */       List<Item> items; final int targetObjectId;
/* 205 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case -1:
/* 208 */           items = player.getInventory().getItemsByItemId(182200002);
/* 209 */           if (items.isEmpty())
/* 210 */             return false; 
/* 211 */           targetObjectId = env.getVisibleObject().getObjectId();
/* 212 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/* 213 */           PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_QUESTLOOT, 0, targetObjectId), true);
/* 214 */           ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 218 */                   if (!player.isTargeting(targetObjectId))
/*     */                     return; 
/* 220 */                   PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/* 221 */                   PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.END_QUESTLOOT, 0, targetObjectId), true);
/* 222 */                   PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(targetObjectId, 10));
/*     */                 }
/*     */               }3000L);
/* 225 */           return true;
/*     */         case 25:
/* 227 */           if (var == 2)
/* 228 */             var++; 
/* 229 */           qs.setQuestVarById(0, var + 1);
/* 230 */           updateQuestStatus(player, qs);
/* 231 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
/* 232 */           ((Npc)env.getVisibleObject()).getController().onDie(null);
/* 233 */           return true;
/*     */       } 
/* 235 */       return false;
/*     */     } 
/*     */     
/* 238 */     if (targetId == 730008) {
/*     */       
/* 240 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 243 */           if (var == 13) {
/*     */             
/* 245 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 2375, 1002));
/* 246 */             return true;
/*     */           } 
/* 248 */           if (var == 14) {
/*     */             
/* 250 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 2461, 1002));
/* 251 */             return true;
/*     */           } 
/* 253 */           return false;
/*     */         case 10004:
/* 255 */           if (var == 13) {
/*     */             
/* 257 */             qs.setQuestVarById(0, 20);
/* 258 */             updateQuestStatus(player, qs);
/* 259 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
/* 260 */             WorldMapInstance newInstance = InstanceService.getNextAvailableInstance(310010000);
/* 261 */             TeleportService.teleportTo(player, 310010000, newInstance.getInstanceId(), 52.0F, 174.0F, 229.0F, 0);
/* 262 */             return true;
/*     */           } 
/* 264 */           return false;
/*     */         case 10005:
/* 266 */           if (var == 14) {
/*     */             
/* 268 */             qs.setStatus(QuestStatus.REWARD);
/* 269 */             updateQuestStatus(player, qs);
/* 270 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 271 */             return true;
/*     */           } 
/* 273 */           return false;
/*     */       } 
/* 275 */       return false;
/*     */     } 
/*     */     
/* 278 */     if (targetId == 205000) {
/*     */       
/* 280 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 283 */           if (var == 20) {
/*     */             
/* 285 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_FLYTELEPORT, 1001, 0));
/* 286 */             ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */                 {
/*     */                   public void run()
/*     */                   {
/* 290 */                     qs.setQuestVarById(0, 14);
/* 291 */                     _1002RequestoftheElim.this.updateQuestStatus(player, qs);
/* 292 */                     TeleportService.teleportTo(player, 210010000, 1, 603.0F, 1537.0F, 116.0F, (byte)20, 0);
/*     */                   }
/*     */                 }43000L);
/* 295 */             return true;
/*     */           } 
/* 297 */           return false;
/*     */       } 
/* 299 */       return false;
/*     */     } 
/*     */     
/* 302 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onEnterWorldEvent(QuestEnv env) {
/* 308 */     Player player = env.getPlayer();
/* 309 */     QuestState qs = player.getQuestStateList().getQuestState(1002);
/* 310 */     if (qs != null && qs.getStatus() == QuestStatus.START)
/*     */     {
/* 312 */       if (player.getWorldId() == 310010000) {
/*     */         
/* 314 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_ASCENSION_MORPH(1));
/* 315 */         return true;
/*     */       } 
/*     */     }
/* 318 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\poeta\_1002RequestoftheElim.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */