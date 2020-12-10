/*     */ package quest.heiron;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_ACCEPTED;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import java.util.Collections;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class _1687TheTigrakiAgreement
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1687;
/*     */   private int Choix;
/*  44 */   private static final Logger log = Logger.getLogger(_1687TheTigrakiAgreement.class);
/*     */ 
/*     */   
/*     */   public _1687TheTigrakiAgreement() {
/*  48 */     super(Integer.valueOf(1687));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  54 */     this.qe.setNpcQuestData(204601).addOnQuestStart(1687);
/*  55 */     this.qe.setNpcQuestData(204601).addOnTalkEvent(1687);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  61 */     Player player = env.getPlayer();
/*  62 */     QuestState qs = player.getQuestStateList().getQuestState(1687);
/*     */     
/*  64 */     int targetId = 0;
/*  65 */     if (env.getVisibleObject() instanceof Npc) {
/*  66 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  68 */     if (qs == null || qs.getStatus() == QuestStatus.NONE || qs.getStatus() == QuestStatus.COMPLETE)
/*     */     {
/*  70 */       if (targetId == 204601) {
/*     */         
/*  72 */         if (env.getDialogId().intValue() == 25)
/*     */         {
/*  74 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4762);
/*     */         }
/*     */         
/*  77 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */     }
/*     */     
/*  81 */     if (qs == null) {
/*  82 */       return false;
/*     */     }
/*  84 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       long itemCount1; long itemCount2;
/*  86 */       switch (targetId) {
/*     */ 
/*     */         
/*     */         case 204601:
/*  90 */           switch (env.getDialogId().intValue()) {
/*     */ 
/*     */             
/*     */             case 25:
/*  94 */               itemCount1 = player.getInventory().getItemCountByItemId(186000035);
/*  95 */               itemCount2 = player.getInventory().getItemCountByItemId(186000036);
/*  96 */               if (itemCount1 >= 2L && itemCount2 >= 5L)
/*     */               {
/*  98 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
/*     */               }
/*     */ 
/*     */             
/*     */             case 10009:
/* 103 */               SetChoix(1);
/* 104 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */ 
/*     */             
/*     */             case 10019:
/* 108 */               SetChoix(2);
/* 109 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 6);
/*     */ 
/*     */             
/*     */             case 10029:
/* 113 */               SetChoix(3);
/* 114 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 7);
/*     */ 
/*     */             
/*     */             case 8:
/* 118 */               log.info("Received Choix id :" + getChoix());
/* 119 */               switch (getChoix()) {
/*     */ 
/*     */                 
/*     */                 case 1:
/* 123 */                   if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(111100788, 1))))
/*     */                   {
/*     */                     
/* 126 */                     return true;
/*     */                   }
/* 128 */                   QuestFinish(env);
/* 129 */                   return true;
/*     */ 
/*     */                 
/*     */                 case 2:
/* 133 */                   if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(112100747, 1))))
/*     */                   {
/*     */                     
/* 136 */                     return true;
/*     */                   }
/* 138 */                   QuestFinish(env);
/* 139 */                   return true;
/*     */ 
/*     */                 
/*     */                 case 3:
/* 143 */                   if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(114100825, 1))))
/*     */                   {
/*     */                     
/* 146 */                     return true;
/*     */                   }
/* 148 */                   QuestFinish(env);
/* 149 */                   return true;
/*     */               } 
/*     */             
/*     */             
/*     */             
/*     */             case 9:
/* 155 */               switch (getChoix()) {
/*     */ 
/*     */                 
/*     */                 case 1:
/* 159 */                   if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(111300792, 1))))
/*     */                   {
/*     */                     
/* 162 */                     return true;
/*     */                   }
/* 164 */                   QuestFinish(env);
/* 165 */                   return true;
/*     */ 
/*     */                 
/*     */                 case 2:
/* 169 */                   if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(112300744, 1))))
/*     */                   {
/*     */                     
/* 172 */                     return true;
/*     */                   }
/* 174 */                   QuestFinish(env);
/* 175 */                   return true;
/*     */ 
/*     */                 
/*     */                 case 3:
/* 179 */                   if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(114300851, 1))))
/*     */                   {
/*     */                     
/* 182 */                     return true;
/*     */                   }
/* 184 */                   QuestFinish(env);
/* 185 */                   return true;
/*     */               } 
/*     */             
/*     */             
/*     */             
/*     */             case 10:
/* 191 */               switch (getChoix()) {
/*     */ 
/*     */                 
/*     */                 case 1:
/* 195 */                   if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(111500775, 1))))
/*     */                   {
/*     */                     
/* 198 */                     return true;
/*     */                   }
/* 200 */                   QuestFinish(env);
/* 201 */                   return true;
/*     */ 
/*     */                 
/*     */                 case 2:
/* 205 */                   if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(112500732, 1))))
/*     */                   {
/*     */                     
/* 208 */                     return true;
/*     */                   }
/* 210 */                   QuestFinish(env);
/* 211 */                   return true;
/*     */ 
/*     */                 
/*     */                 case 3:
/* 215 */                   if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(114500797, 1))))
/*     */                   {
/*     */                     
/* 218 */                     return true;
/*     */                   }
/* 220 */                   QuestFinish(env);
/* 221 */                   return true;
/*     */               } 
/*     */             
/*     */             
/*     */             
/*     */             case 11:
/* 227 */               switch (getChoix()) {
/*     */ 
/*     */                 
/*     */                 case 1:
/* 231 */                   if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(111600767, 1))))
/*     */                   {
/*     */                     
/* 234 */                     return true;
/*     */                   }
/* 236 */                   QuestFinish(env);
/* 237 */                   return true;
/*     */ 
/*     */                 
/*     */                 case 2:
/* 241 */                   if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(112600743, 1))))
/*     */                   {
/*     */                     
/* 244 */                     return true;
/*     */                   }
/* 246 */                   QuestFinish(env);
/* 247 */                   return true;
/*     */ 
/*     */                 
/*     */                 case 3:
/* 251 */                   if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(114600754, 1))))
/*     */                   {
/*     */                     
/* 254 */                     return true;
/*     */                   }
/* 256 */                   QuestFinish(env);
/* 257 */                   return true;
/*     */               } 
/*     */               
/*     */               break;
/*     */           } 
/*     */           break;
/*     */       } 
/*     */     } 
/* 265 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getChoix() {
/* 270 */     return this.Choix;
/*     */   }
/*     */ 
/*     */   
/*     */   public void SetChoix(int Choix) {
/* 275 */     this.Choix = Choix;
/*     */   }
/*     */ 
/*     */   
/*     */   private void QuestFinish(QuestEnv env) {
/* 280 */     Player player = env.getPlayer();
/* 281 */     QuestState qs = player.getQuestStateList().getQuestState(1687);
/*     */     
/* 283 */     ItemService.removeItemFromInventoryByItemId(player, 186000035);
/* 284 */     ItemService.removeItemFromInventoryByItemId(player, 186000036);
/* 285 */     qs.setStatus(QuestStatus.COMPLETE);
/* 286 */     qs.setCompliteCount(qs.getCompliteCount() + 1);
/* 287 */     int rewardExp = player.getRates().getQuestXpRate() * 1535800;
/* 288 */     int rewardAbyssPoint = player.getRates().getQuestXpRate() * 200;
/* 289 */     player.getCommonData().addExp(rewardExp);
/* 290 */     player.getCommonData().addAp(rewardAbyssPoint);
/* 291 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_QUEST_ACCEPTED(1687, QuestStatus.COMPLETE, 2));
/* 292 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
/* 293 */     updateQuestStatus(player, qs);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\heiron\_1687TheTigrakiAgreement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */