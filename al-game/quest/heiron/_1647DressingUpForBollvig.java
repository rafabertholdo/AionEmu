/*     */ package quest.heiron;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
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
/*     */ public class _1647DressingUpForBollvig
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1647;
/*     */   
/*     */   public _1647DressingUpForBollvig() {
/*  46 */     super(Integer.valueOf(1647));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  52 */     this.qe.setNpcQuestData(790019).addOnQuestStart(1647);
/*  53 */     this.qe.setNpcQuestData(790019).addOnTalkEvent(1647);
/*  54 */     this.qe.setQuestItemIds(182201783).add(1647);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  60 */     Player player = env.getPlayer();
/*  61 */     QuestState qs = player.getQuestStateList().getQuestState(1647);
/*     */     
/*  63 */     int targetId = 0;
/*  64 */     if (env.getVisibleObject() instanceof Npc) {
/*  65 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  67 */     if (qs == null || qs.getStatus() == QuestStatus.NONE)
/*     */     {
/*  69 */       if (targetId == 790019) {
/*     */         
/*  71 */         switch (env.getDialogId().intValue()) {
/*     */ 
/*     */           
/*     */           case 25:
/*  75 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4762);
/*     */ 
/*     */           
/*     */           case 1002:
/*  79 */             if (player.getInventory().getItemCountByItemId(182201783) == 0L)
/*     */             {
/*  81 */               if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182201783, 1))))
/*     */               {
/*  83 */                 return true;
/*     */               }
/*     */             }
/*     */             break;
/*     */         } 
/*  88 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*  93 */     if (qs == null) {
/*  94 */       return false;
/*     */     }
/*  96 */     if (qs.getStatus() == QuestStatus.REWARD)
/*     */     {
/*  98 */       switch (targetId) {
/*     */ 
/*     */         
/*     */         case 790019:
/* 102 */           switch (env.getDialogId().intValue()) {
/*     */ 
/*     */             
/*     */             case 25:
/* 106 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002);
/*     */ 
/*     */             
/*     */             case 1009:
/* 110 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */           } 
/*     */           
/* 113 */           return defaultQuestEndDialog(env);
/*     */       } 
/*     */ 
/*     */     
/*     */     }
/* 118 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseEvent(QuestEnv env, final Item item) {
/* 124 */     final Player player = env.getPlayer();
/* 125 */     final int id = item.getItemTemplate().getTemplateId();
/* 126 */     final int itemObjId = item.getObjectId();
/*     */     
/* 128 */     if (id != 182201783)
/*     */     {
/* 130 */       return false;
/*     */     }
/*     */     
/* 133 */     final QuestState qs = player.getQuestStateList().getQuestState(1647);
/* 134 */     if (qs == null)
/*     */     {
/* 136 */       return false;
/*     */     }
/*     */     
/* 139 */     int var = qs.getQuestVars().getQuestVars();
/* 140 */     if (var != 0)
/*     */     {
/* 142 */       return false;
/*     */     }
/*     */     
/* 145 */     if (qs.getStatus() != QuestStatus.START)
/*     */     {
/* 147 */       return false;
/*     */     }
/*     */     
/* 150 */     if (MathUtil.getDistance(1677.0F, 2520.0F, 100.0F, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ()) > 5.0D)
/*     */     {
/*     */       
/* 153 */       return false;
/*     */     }
/*     */     
/* 156 */     int itemId1 = 110100150;
/* 157 */     int itemId2 = 113100144;
/* 158 */     boolean CheckitemId1 = false;
/* 159 */     boolean CheckitemId2 = false;
/*     */     
/* 161 */     List<Item> items1 = player.getEquipment().getEquippedItemsByItemId(itemId1);
/*     */     
/* 163 */     for (Item ListeCheckitemId1 : items1)
/*     */     {
/* 165 */       CheckitemId1 = true;
/*     */     }
/*     */     
/* 168 */     List<Item> items2 = player.getEquipment().getEquippedItemsByItemId(itemId2);
/*     */     
/* 170 */     for (Item ListeCheckitemId2 : items2)
/*     */     {
/* 172 */       CheckitemId2 = true;
/*     */     }
/*     */     
/* 175 */     if ((!CheckitemId1 && CheckitemId2) || (CheckitemId1 && !CheckitemId2) || (!CheckitemId1 && !CheckitemId2))
/*     */     {
/* 177 */       return false;
/*     */     }
/*     */     
/* 180 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
/*     */     
/* 182 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 186 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
/*     */             
/* 188 */             ItemService.removeItemFromInventory(player, item);
/* 189 */             qs.setStatus(QuestStatus.REWARD);
/* 190 */             _1647DressingUpForBollvig.this.updateQuestStatus(player, qs);
/*     */           }
/*     */         }3000L);
/* 193 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\heiron\_1647DressingUpForBollvig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */