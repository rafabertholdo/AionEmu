/*     */ package quest.reshanta;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
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
/*     */ public class _1076FragmentofMemory2
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1076;
/*  45 */   private static final int[] npc_ids = new int[] { 278500, 203834, 203786, 203754, 203704 };
/*     */ 
/*     */   
/*     */   public _1076FragmentofMemory2() {
/*  49 */     super(Integer.valueOf(1076));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  55 */     this.qe.addQuestLvlUp(1076);
/*  56 */     this.qe.setQuestItemIds(182202006).add(1076);
/*  57 */     for (int npc_id : npc_ids) {
/*  58 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1076);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  64 */     Player player = env.getPlayer();
/*  65 */     QuestState qs = player.getQuestStateList().getQuestState(1076);
/*  66 */     boolean lvlCheck = QuestService.checkLevelRequirement(1076, player.getCommonData().getLevel());
/*  67 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
/*  68 */       return false;
/*     */     }
/*  70 */     QuestState qs2 = player.getQuestStateList().getQuestState(1701);
/*  71 */     if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
/*  72 */       return false; 
/*  73 */     qs.setStatus(QuestStatus.START);
/*  74 */     updateQuestStatus(player, qs);
/*  75 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  81 */     Player player = env.getPlayer();
/*  82 */     QuestState qs = player.getQuestStateList().getQuestState(1076);
/*  83 */     if (qs == null) {
/*  84 */       return false;
/*     */     }
/*  86 */     int var = qs.getQuestVarById(0);
/*  87 */     int targetId = 0;
/*  88 */     if (env.getVisibleObject() instanceof Npc) {
/*  89 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  91 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  93 */       if (targetId == 203704) {
/*     */         
/*  95 */         if (env.getDialogId().intValue() == -1)
/*  96 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002); 
/*  97 */         if (env.getDialogId().intValue() == 1009)
/*  98 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5); 
/*  99 */         return defaultQuestEndDialog(env);
/*     */       } 
/* 101 */       return false;
/*     */     } 
/* 103 */     if (qs.getStatus() != QuestStatus.START)
/*     */     {
/* 105 */       return false;
/*     */     }
/* 107 */     if (targetId == 278500) {
/*     */       
/* 109 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 112 */           if (var == 0)
/* 113 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*     */         case 10000:
/* 115 */           if (var == 0) {
/*     */             
/* 117 */             qs.setQuestVarById(0, var + 1);
/* 118 */             updateQuestStatus(player, qs);
/* 119 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 120 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 124 */     } else if (targetId == 203834) {
/*     */       
/* 126 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 129 */           if (var == 1)
/* 130 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 131 */           if (var == 3)
/* 132 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/* 133 */           if (var == 5)
/* 134 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
/*     */         case 1353:
/* 136 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 102));
/*     */           break;
/*     */         case 10001:
/* 139 */           if (var == 1) {
/*     */             
/* 141 */             qs.setQuestVarById(0, var + 1);
/* 142 */             updateQuestStatus(player, qs);
/* 143 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 144 */             return true;
/*     */           } 
/*     */         case 10003:
/* 147 */           if (var == 3) {
/*     */             
/* 149 */             qs.setQuestVarById(0, var + 1);
/* 150 */             updateQuestStatus(player, qs);
/* 151 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 152 */             return true;
/*     */           } 
/*     */         case 10005:
/* 155 */           if (var == 5) {
/*     */             
/* 157 */             qs.setQuestVarById(0, 6);
/* 158 */             updateQuestStatus(player, qs);
/* 159 */             ItemService.removeItemFromInventoryByItemId(player, 182202006);
/* 160 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 161 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 165 */     } else if (targetId == 203786) {
/*     */       
/* 167 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 170 */           if (var == 2)
/* 171 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*     */         case 33:
/* 173 */           if (QuestService.collectItemCheck(env, true)) {
/*     */             
/* 175 */             qs.setQuestVarById(0, var + 1);
/* 176 */             updateQuestStatus(player, qs);
/* 177 */             ItemService.addItems(player, Collections.singletonList(new QuestItems(182202006, 1)));
/* 178 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10000);
/*     */           } 
/*     */           
/* 181 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
/*     */       } 
/*     */     
/* 184 */     } else if (targetId == 203754) {
/*     */       
/* 186 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 189 */           if (var == 6)
/* 190 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
/*     */         case 10255:
/* 192 */           if (var == 6) {
/*     */             
/* 194 */             qs.setStatus(QuestStatus.REWARD);
/* 195 */             updateQuestStatus(player, qs);
/* 196 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 197 */             return true;
/*     */           }  break;
/*     */       } 
/*     */     } 
/* 201 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseEvent(QuestEnv env, Item item) {
/* 207 */     final Player player = env.getPlayer();
/* 208 */     final int id = item.getItemTemplate().getTemplateId();
/* 209 */     final int itemObjId = item.getObjectId();
/*     */     
/* 211 */     if (id != 182202006) {
/* 212 */       return false;
/*     */     }
/* 214 */     final QuestState qs = player.getQuestStateList().getQuestState(1076);
/* 215 */     if (qs == null || qs.getStatus() != QuestStatus.START || qs.getQuestVarById(0) != 4) {
/* 216 */       return false;
/*     */     }
/* 218 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 1000, 0, 0), true);
/* 219 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 223 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
/* 224 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 170));
/* 225 */             qs.setQuestVar(5);
/* 226 */             _1076FragmentofMemory2.this.updateQuestStatus(player, qs);
/*     */           }
/*     */         }1000L);
/* 229 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\reshanta\_1076FragmentofMemory2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */