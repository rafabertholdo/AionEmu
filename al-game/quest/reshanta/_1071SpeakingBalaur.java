/*     */ package quest.reshanta;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
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
/*     */ public class _1071SpeakingBalaur
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1071;
/*     */   
/*     */   public _1071SpeakingBalaur() {
/*  44 */     super(Integer.valueOf(1071));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  50 */     this.qe.addQuestLvlUp(1071);
/*  51 */     this.qe.setQuestItemIds(182202001).add(1071);
/*  52 */     this.qe.setNpcQuestData(278532).addOnTalkEvent(1071);
/*  53 */     this.qe.setNpcQuestData(798026).addOnTalkEvent(1071);
/*  54 */     this.qe.setNpcQuestData(798025).addOnTalkEvent(1071);
/*  55 */     this.qe.setNpcQuestData(279019).addOnTalkEvent(1071);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  61 */     Player player = env.getPlayer();
/*  62 */     QuestState qs = player.getQuestStateList().getQuestState(1071);
/*  63 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED) {
/*  64 */       return false;
/*     */     }
/*  66 */     QuestState qs2 = player.getQuestStateList().getQuestState(1701);
/*  67 */     if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
/*  68 */       return false; 
/*  69 */     qs.setStatus(QuestStatus.START);
/*  70 */     updateQuestStatus(player, qs);
/*  71 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  77 */     Player player = env.getPlayer();
/*  78 */     QuestState qs = player.getQuestStateList().getQuestState(1071);
/*  79 */     if (qs == null) {
/*  80 */       return false;
/*     */     }
/*  82 */     int var = qs.getQuestVarById(0);
/*  83 */     int targetId = 0;
/*  84 */     if (env.getVisibleObject() instanceof Npc) {
/*  85 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  87 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  89 */       if (targetId == 278532) {
/*     */         
/*  91 */         if (env.getDialogId().intValue() == -1)
/*  92 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002); 
/*  93 */         if (env.getDialogId().intValue() == 1009)
/*  94 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5); 
/*  95 */         return defaultQuestEndDialog(env);
/*     */       } 
/*  97 */       return false;
/*     */     } 
/*  99 */     if (qs.getStatus() != QuestStatus.START)
/*     */     {
/* 101 */       return false;
/*     */     }
/* 103 */     if (targetId == 278532) {
/*     */       
/* 105 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 108 */           if (var == 0)
/* 109 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*     */         case 10000:
/* 111 */           if (var == 0) {
/*     */             
/* 113 */             qs.setQuestVarById(0, var + 1);
/* 114 */             updateQuestStatus(player, qs);
/* 115 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 116 */             return true;
/*     */           } 
/* 118 */           return false;
/*     */       } 
/*     */     
/* 121 */     } else if (targetId == 798026) {
/*     */       
/* 123 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 126 */           if (var == 1)
/* 127 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 128 */           if (var == 4)
/* 129 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 130 */           if (var == 6 || var == 8)
/* 131 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
/*     */         case 10004:
/* 133 */           if (var == 4) {
/*     */             
/* 135 */             qs.setQuestVarById(0, var + 1);
/* 136 */             updateQuestStatus(player, qs);
/* 137 */             ItemService.decreaseItemCountByItemId(player, 182202002, 1L);
/* 138 */             ItemService.addItems(player, Collections.singletonList(new QuestItems(182202001, 1)));
/* 139 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 140 */             return true;
/*     */           } 
/* 142 */           return false;
/*     */         case 10006:
/* 144 */           if (var == 6 || var == 8) {
/*     */             
/* 146 */             qs.setStatus(QuestStatus.REWARD);
/* 147 */             updateQuestStatus(player, qs);
/* 148 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 149 */             return true;
/*     */           } 
/* 151 */           return false;
/*     */         
/*     */         case 10010:
/* 154 */           if (var == 1 && ItemService.decreaseKinah(player, 20000L)) {
/*     */             
/* 156 */             ItemService.addItems(player, Collections.singletonList(new QuestItems(182202001, 1)));
/* 157 */             qs.setQuestVar(7);
/* 158 */             updateQuestStatus(player, qs);
/* 159 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 160 */             return true;
/*     */           } 
/*     */           
/* 163 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1355);
/*     */         case 10011:
/* 165 */           if (var == 1) {
/*     */             
/* 167 */             qs.setQuestVarById(0, var + 1);
/* 168 */             updateQuestStatus(player, qs);
/* 169 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 170 */             return true;
/*     */           } 
/* 172 */           return false;
/*     */       } 
/*     */     
/* 175 */     } else if (targetId == 798025) {
/*     */       
/* 177 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 180 */           if (var == 2)
/* 181 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*     */         case 10002:
/* 183 */           if (var == 2) {
/*     */             
/* 185 */             qs.setQuestVarById(0, var + 1);
/* 186 */             updateQuestStatus(player, qs);
/* 187 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 188 */             return true;
/*     */           } 
/* 190 */           return false;
/*     */       } 
/*     */     
/* 193 */     } else if (targetId == 279019) {
/*     */       
/* 195 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 198 */           if (var == 3)
/* 199 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/* 200 */           return false;
/*     */         case 10003:
/* 202 */           if (var == 3) {
/*     */             
/* 204 */             qs.setQuestVarById(0, var + 1);
/* 205 */             updateQuestStatus(player, qs);
/* 206 */             ItemService.addItems(player, Collections.singletonList(new QuestItems(182202002, 1)));
/* 207 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 208 */             return true;
/*     */           } 
/* 210 */           return false;
/*     */       } 
/*     */     
/*     */     } 
/* 214 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseEvent(QuestEnv env, Item item) {
/* 220 */     Player player = env.getPlayer();
/* 221 */     int id = item.getItemTemplate().getTemplateId();
/* 222 */     int itemObjId = item.getObjectId();
/*     */     
/* 224 */     if (id != 182202001)
/* 225 */       return false; 
/* 226 */     QuestState qs = player.getQuestStateList().getQuestState(1071);
/* 227 */     if (qs == null) {
/* 228 */       return false;
/*     */     }
/* 230 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 1, 1, 0), true);
/* 231 */     ItemService.decreaseItemCountByItemId(player, 182202001, 1L);
/* 232 */     qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 233 */     updateQuestStatus(player, qs);
/* 234 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\reshanta\_1071SpeakingBalaur.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */