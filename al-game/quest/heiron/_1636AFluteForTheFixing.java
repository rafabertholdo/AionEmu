/*     */ package quest.heiron;
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
/*     */ import com.aionemu.gameserver.utils.MathUtil;
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
/*     */ public class _1636AFluteForTheFixing
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1636;
/*     */   
/*     */   public _1636AFluteForTheFixing() {
/*  46 */     super(Integer.valueOf(1636));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  52 */     this.qe.setNpcQuestData(204535).addOnQuestStart(1636);
/*  53 */     this.qe.setNpcQuestData(204535).addOnTalkEvent(1636);
/*  54 */     this.qe.setNpcQuestData(203792).addOnTalkEvent(1636);
/*  55 */     this.qe.setQuestItemIds(182201785).add(1636);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  61 */     Player player = env.getPlayer();
/*  62 */     QuestState qs = player.getQuestStateList().getQuestState(1636);
/*     */     
/*  64 */     int targetId = 0;
/*  65 */     if (env.getVisibleObject() instanceof Npc) {
/*  66 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  68 */     if (qs == null || qs.getStatus() == QuestStatus.NONE)
/*     */     {
/*  70 */       if (targetId == 204535) {
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
/*     */       long itemCount1; long itemCount2; long itemCount3; long itemCount4;
/*  86 */       switch (targetId) {
/*     */ 
/*     */         
/*     */         case 203792:
/*  90 */           switch (env.getDialogId().intValue()) {
/*     */ 
/*     */             
/*     */             case 25:
/*  94 */               itemCount1 = player.getInventory().getItemCountByItemId(182201786);
/*  95 */               itemCount2 = player.getInventory().getItemCountByItemId(152020034);
/*  96 */               itemCount3 = player.getInventory().getItemCountByItemId(152020091);
/*  97 */               itemCount4 = player.getInventory().getItemCountByItemId(169400060);
/*     */               
/*  99 */               if (qs.getQuestVarById(0) == 0)
/*     */               {
/* 101 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */               }
/* 103 */               if (qs.getQuestVarById(0) == 1 && itemCount1 >= 1L && itemCount2 >= 1L && itemCount3 >= 1L && itemCount4 >= 1L)
/*     */               {
/*     */                 
/* 106 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
/*     */               }
/*     */               
/* 109 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
/*     */ 
/*     */             
/*     */             case 10000:
/* 113 */               qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 114 */               updateQuestStatus(player, qs);
/* 115 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
/*     */               
/* 117 */               return true;
/*     */ 
/*     */             
/*     */             case 33:
/* 121 */               qs.setQuestVarById(0, qs.getQuestVarById(0) + 2);
/* 122 */               updateQuestStatus(player, qs);
/*     */               
/* 124 */               if (player.getInventory().getItemCountByItemId(182201785) == 0L)
/*     */               {
/* 126 */                 if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182201785, 1))))
/*     */                 {
/*     */                   
/* 129 */                   return true;
/*     */                 }
/*     */               }
/* 132 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
/*     */               
/* 134 */               return true;
/*     */           } 
/*     */           
/*     */           break;
/*     */       } 
/*     */     
/* 140 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 142 */       if (targetId == 204535) {
/*     */         
/* 144 */         if (env.getDialogId().intValue() == 1009) {
/* 145 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */         }
/* 147 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 150 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseEvent(QuestEnv env, final Item item) {
/* 156 */     final Player player = env.getPlayer();
/* 157 */     final int id = item.getItemTemplate().getTemplateId();
/* 158 */     final int itemObjId = item.getObjectId();
/*     */     
/* 160 */     if (id != 182201785)
/*     */     {
/* 162 */       return false;
/*     */     }
/*     */     
/* 165 */     final QuestState qs = player.getQuestStateList().getQuestState(1636);
/* 166 */     if (qs == null || qs.getStatus() != QuestStatus.START)
/*     */     {
/* 168 */       return false;
/*     */     }
/*     */     
/* 171 */     int var = qs.getQuestVars().getQuestVars();
/* 172 */     if (var != 3)
/*     */     {
/* 174 */       return false;
/*     */     }
/*     */     
/* 177 */     if (MathUtil.getDistance(182.0F, 2703.0F, 143.0F, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ()) > 10.0D)
/*     */     {
/*     */       
/* 180 */       return false;
/*     */     }
/*     */     
/* 183 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
/*     */     
/* 185 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 189 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
/*     */             
/* 191 */             ItemService.removeItemFromInventory(player, item);
/* 192 */             qs.setStatus(QuestStatus.REWARD);
/* 193 */             _1636AFluteForTheFixing.this.updateQuestStatus(player, qs);
/*     */           }
/*     */         }3000L);
/* 196 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\heiron\_1636AFluteForTheFixing.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */