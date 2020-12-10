/*     */ package quest.sanctum;
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
/*     */ public class _1901KrallicPotion
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1901;
/*     */   
/*     */   public _1901KrallicPotion() {
/*  40 */     super(Integer.valueOf(1901));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  46 */     this.qe.setNpcQuestData(203830).addOnQuestStart(1901);
/*  47 */     this.qe.setNpcQuestData(203830).addOnTalkEvent(1901);
/*  48 */     this.qe.setNpcQuestData(798026).addOnTalkEvent(1901);
/*  49 */     this.qe.setNpcQuestData(798025).addOnTalkEvent(1901);
/*  50 */     this.qe.setNpcQuestData(203131).addOnTalkEvent(1901);
/*  51 */     this.qe.setNpcQuestData(798003).addOnTalkEvent(1901);
/*  52 */     this.qe.setNpcQuestData(203864).addOnTalkEvent(1901);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  59 */     Player player = env.getPlayer();
/*  60 */     int targetId = 0;
/*  61 */     if (env.getVisibleObject() instanceof Npc)
/*  62 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  63 */     QuestState qs = player.getQuestStateList().getQuestState(1901);
/*  64 */     if (targetId == 203830) {
/*     */       
/*  66 */       if (env.getDialogId().intValue() == 25) {
/*  67 */         return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */       }
/*  69 */       return defaultQuestStartDialog(env);
/*     */     } 
/*     */ 
/*     */     
/*  73 */     if (targetId == 203864) {
/*     */       
/*  75 */       if (env.getDialogId().intValue() == 25)
/*  76 */         return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*  77 */       if (env.getDialogId().intValue() == 1009) {
/*     */         
/*  79 */         qs.setQuestVar(7);
/*  80 */         updateQuestStatus(player, qs);
/*  81 */         qs.setStatus(QuestStatus.REWARD);
/*  82 */         return defaultQuestEndDialog(env);
/*     */       } 
/*  84 */       if (qs != null && qs.getStatus() == QuestStatus.REWARD)
/*     */       {
/*  86 */         if (env.getDialogId().intValue() == -1)
/*  87 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3398); 
/*  88 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     
/*  91 */     } else if (qs != null) {
/*     */       
/*  93 */       if (qs.getStatus() == QuestStatus.START) {
/*     */         
/*  95 */         int var = qs.getQuestVarById(0);
/*  96 */         switch (targetId) {
/*     */           
/*     */           case 798026:
/*  99 */             switch (env.getDialogId().intValue()) {
/*     */               
/*     */               case 25:
/* 102 */                 if (var == 0)
/* 103 */                   return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 104 */                 if (var == 5)
/* 105 */                   return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
/*     */               case 1438:
/* 107 */                 if (ItemService.decreaseKinah(player, 10000L)) {
/*     */                   
/* 109 */                   qs.setQuestVarById(0, var + 1);
/* 110 */                   updateQuestStatus(player, qs);
/* 111 */                   PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */                   
/* 113 */                   return true;
/*     */                 } 
/*     */                 
/* 116 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1523);
/*     */               case 10000:
/* 118 */                 qs.setQuestVarById(0, var + 1);
/* 119 */                 updateQuestStatus(player, qs);
/* 120 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */                 
/* 122 */                 return true;
/*     */               case 10001:
/* 124 */                 qs.setQuestVarById(0, var + 1);
/* 125 */                 updateQuestStatus(player, qs);
/* 126 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */                 
/* 128 */                 return true;
/*     */               case 10006:
/* 130 */                 qs.setQuestVarById(0, var + 1);
/* 131 */                 qs.setStatus(QuestStatus.REWARD);
/* 132 */                 updateQuestStatus(player, qs);
/* 133 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */                 
/* 135 */                 return true;
/*     */             } 
/* 137 */             return defaultQuestStartDialog(env);
/*     */           
/*     */           case 798025:
/* 140 */             switch (env.getDialogId().intValue()) {
/*     */               
/*     */               case 25:
/* 143 */                 if (var == 1)
/* 144 */                   return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 145 */                 if (var == 4)
/* 146 */                   return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
/*     */               case 10002:
/* 148 */                 qs.setQuestVarById(0, var + 1);
/* 149 */                 updateQuestStatus(player, qs);
/* 150 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */                 
/* 152 */                 return true;
/*     */               case 10005:
/* 154 */                 ItemService.removeItemFromInventoryByItemId(player, 182206000);
/* 155 */                 qs.setQuestVarById(0, var + 1);
/* 156 */                 updateQuestStatus(player, qs);
/* 157 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */                 
/* 159 */                 return true;
/*     */             } 
/*     */           case 203131:
/* 162 */             switch (env.getDialogId().intValue()) {
/*     */               
/*     */               case 25:
/* 165 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
/*     */               case 10003:
/* 167 */                 qs.setQuestVarById(0, var + 1);
/* 168 */                 updateQuestStatus(player, qs);
/* 169 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */                 
/* 171 */                 return true;
/*     */             } 
/*     */           case 798003:
/* 174 */             switch (env.getDialogId().intValue()) {
/*     */               
/*     */               case 25:
/* 177 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
/*     */               case 10004:
/* 179 */                 if (player.getInventory().getItemCountByItemId(182206000) == 0L && 
/* 180 */                   !ItemService.addItems(player, Collections.singletonList(new QuestItems(182206000, 1))))
/*     */                 {
/* 182 */                   return true; } 
/* 183 */                 qs.setQuestVarById(0, var + 1);
/* 184 */                 updateQuestStatus(player, qs);
/* 185 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */                 
/* 187 */                 return true;
/*     */             }  break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 192 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\sanctum\_1901KrallicPotion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */