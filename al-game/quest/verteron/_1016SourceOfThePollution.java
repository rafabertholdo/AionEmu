/*     */ package quest.verteron;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
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
/*     */ 
/*     */ public class _1016SourceOfThePollution
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1016;
/*  43 */   private static final int[] npc_ids = new int[] { 203149, 203148, 203832, 203705, 203822, 203761, 203098, 203195 };
/*     */ 
/*     */   
/*     */   public _1016SourceOfThePollution() {
/*  47 */     super(Integer.valueOf(1016));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  53 */     this.qe.addQuestLvlUp(1016);
/*  54 */     this.qe.setNpcQuestData(210318).addOnKillEvent(1016);
/*  55 */     for (int npc_id : npc_ids) {
/*  56 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1016);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  62 */     Player player = env.getPlayer();
/*  63 */     QuestState qs = player.getQuestStateList().getQuestState(1016);
/*  64 */     boolean lvlCheck = QuestService.checkLevelRequirement(1016, player.getCommonData().getLevel());
/*  65 */     if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
/*  66 */       return false; 
/*  67 */     qs.setStatus(QuestStatus.START);
/*  68 */     updateQuestStatus(player, qs);
/*  69 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  75 */     Player player = env.getPlayer();
/*  76 */     QuestState qs = player.getQuestStateList().getQuestState(1016);
/*  77 */     if (qs == null) {
/*  78 */       return false;
/*     */     }
/*  80 */     int var = qs.getQuestVarById(0);
/*  81 */     int targetId = 0;
/*  82 */     if (env.getVisibleObject() instanceof Npc) {
/*  83 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  85 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  87 */       if (targetId == 203098) {
/*     */         
/*  89 */         if (env.getDialogId().intValue() == -1)
/*  90 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4080); 
/*  91 */         if (env.getDialogId().intValue() == 1009)
/*  92 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5); 
/*  93 */         return defaultQuestEndDialog(env);
/*     */       } 
/*  95 */       return false;
/*     */     } 
/*  97 */     if (qs.getStatus() == QuestStatus.START)
/*     */     {
/*  99 */       switch (targetId) {
/*     */         
/*     */         case 203149:
/* 102 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 105 */               if (var == 0)
/* 106 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/* 107 */               if (var == 2)
/* 108 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 109 */               if (var == 7)
/* 110 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3398); 
/* 111 */               if (var == 8) {
/*     */                 
/* 113 */                 if (player.getInventory().getItemCountByItemId(182200015) == 0L) {
/* 114 */                   return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3484);
/*     */                 }
/* 116 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3569);
/*     */               } 
/*     */             case 3400:
/* 119 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 28));
/*     */               break;
/*     */             case 10000:
/*     */             case 10002:
/* 123 */               if (var == 0 || var == 2) {
/*     */                 
/* 125 */                 qs.setQuestVarById(0, var + 1);
/* 126 */                 updateQuestStatus(player, qs);
/* 127 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 128 */                 return true;
/*     */               } 
/*     */             case 10007:
/* 131 */               if (var == 7) {
/*     */                 
/* 133 */                 qs.setQuestVarById(0, var + 1);
/* 134 */                 updateQuestStatus(player, qs);
/* 135 */                 ItemService.decreaseItemCountByItemId(player, 182200013, 1L);
/* 136 */                 ItemService.decreaseItemCountByItemId(player, 182200014, 1L);
/* 137 */                 if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182200015, 2))));
/* 138 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 139 */                 return true;
/*     */               } 
/*     */             case 10008:
/* 142 */               if (var == 8) {
/*     */                 
/* 144 */                 if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182200015, 2))));
/* 145 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 146 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */         case 203148:
/* 150 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 153 */               if (var == 1)
/* 154 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */             case 10001:
/* 156 */               if (var == 1) {
/*     */                 
/* 158 */                 qs.setQuestVarById(0, var + 1);
/* 159 */                 updateQuestStatus(player, qs);
/* 160 */                 if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182200017, 1))));
/* 161 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 162 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */         case 203832:
/* 166 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 169 */               if (var == 3)
/* 170 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/*     */             case 10003:
/* 172 */               if (var == 3) {
/*     */                 
/* 174 */                 qs.setQuestVarById(0, var + 1);
/* 175 */                 updateQuestStatus(player, qs);
/* 176 */                 if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182200013, 1))));
/* 177 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 178 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */         case 203705:
/* 182 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 185 */               if (var == 4)
/* 186 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*     */             case 10004:
/* 188 */               if (var == 4) {
/*     */                 
/* 190 */                 qs.setQuestVarById(0, var + 1);
/* 191 */                 updateQuestStatus(player, qs);
/* 192 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 193 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */         case 203822:
/* 197 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 200 */               if (var == 5)
/* 201 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
/*     */             case 10005:
/* 203 */               if (var == 5) {
/*     */                 
/* 205 */                 qs.setQuestVarById(0, var + 1);
/* 206 */                 updateQuestStatus(player, qs);
/* 207 */                 ItemService.decreaseItemCountByItemId(player, 182200017, 1L);
/* 208 */                 if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182200018, 1))));
/* 209 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 210 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */         case 203761:
/* 214 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 217 */               if (var == 6)
/* 218 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
/*     */             case 10006:
/* 220 */               if (var == 6) {
/*     */                 
/* 222 */                 qs.setQuestVarById(0, var + 1);
/* 223 */                 updateQuestStatus(player, qs);
/* 224 */                 ItemService.decreaseItemCountByItemId(player, 182200018, 1L);
/* 225 */                 if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182200014, 1))));
/* 226 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 227 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */         case 203195:
/* 231 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 234 */               if (var == 9)
/* 235 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3739); 
/*     */             case 10008:
/* 237 */               if (var == 9) {
/*     */                 
/* 239 */                 qs.setStatus(QuestStatus.REWARD);
/* 240 */                 updateQuestStatus(player, qs);
/* 241 */                 ItemService.decreaseItemCountByItemId(player, 182200015, 1L);
/* 242 */                 if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182200016, 1))));
/* 243 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 244 */                 final Npc npc = (Npc)env.getVisibleObject();
/* 245 */                 ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */                     {
/*     */                       public void run()
/*     */                       {
/* 249 */                         npc.getController().onDelete();
/*     */                       }
/*     */                     },  40000L);
/* 252 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */       }  } 
/* 257 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 263 */     Player player = env.getPlayer();
/* 264 */     QuestState qs = player.getQuestStateList().getQuestState(1016);
/* 265 */     if (qs.getStatus() != QuestStatus.START) {
/* 266 */       return false;
/*     */     }
/* 268 */     final int instanceId = player.getInstanceId();
/* 269 */     int var = qs.getQuestVarById(0);
/* 270 */     int targetId = 0;
/* 271 */     if (env.getVisibleObject() instanceof Npc)
/* 272 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/* 273 */     final Npc npc = (Npc)env.getVisibleObject();
/*     */     
/* 275 */     switch (targetId) {
/*     */       
/*     */       case 210318:
/* 278 */         if (var == 8) {
/*     */           
/* 280 */           qs.setQuestVar(9);
/* 281 */           updateQuestStatus(player, qs);
/* 282 */           ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 286 */                   QuestService.addNewSpawn(210030000, instanceId, 203195, npc.getX(), npc.getY(), npc.getZ(), (byte)0, true);
/*     */                 }
/*     */               }5000L);
/*     */ 
/*     */           
/* 291 */           return true;
/*     */         }  break;
/*     */     } 
/* 294 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\verteron\_1016SourceOfThePollution.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */