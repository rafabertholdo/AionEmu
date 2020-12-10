/*     */ package com.aionemu.gameserver.network.aion.clientpackets;
/*     */ 
/*     */ import com.aionemu.gameserver.itemengine.actions.AbstractItemAction;
/*     */ import com.aionemu.gameserver.itemengine.actions.ItemActions;
/*     */ import com.aionemu.gameserver.model.Race;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.item.ItemRace;
/*     */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*     */ import com.aionemu.gameserver.network.aion.AionConnection;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.questEngine.QuestEngine;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.restrictions.RestrictionsManager;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import java.util.ArrayList;
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
/*     */ public class CM_USE_ITEM
/*     */   extends AionClientPacket
/*     */ {
/*     */   public int uniqueItemId;
/*     */   public int type;
/*     */   public int targetItemId;
/*  43 */   private static final Logger log = Logger.getLogger(CM_USE_ITEM.class);
/*     */   
/*     */   public CM_USE_ITEM(int opcode) {
/*  46 */     super(opcode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readImpl() {
/*  54 */     this.uniqueItemId = readD();
/*  55 */     this.type = readC();
/*  56 */     if (this.type == 2)
/*     */     {
/*  58 */       this.targetItemId = readD();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void runImpl() {
/*  68 */     Player player = ((AionConnection)getConnection()).getActivePlayer();
/*  69 */     Item item = player.getInventory().getItemByObjId(this.uniqueItemId);
/*     */     
/*  71 */     if (item == null) {
/*     */       
/*  73 */       log.warn(String.format("CHECKPOINT: null item use action: %d %d", new Object[] { Integer.valueOf(player.getObjectId()), Integer.valueOf(this.uniqueItemId) }));
/*     */       
/*     */       return;
/*     */     } 
/*  77 */     if (!RestrictionsManager.canUseItem(player)) {
/*     */       return;
/*     */     }
/*     */     
/*  81 */     switch (item.getItemTemplate().getRace()) {
/*     */       
/*     */       case ASMODIANS:
/*  84 */         if (player.getCommonData().getRace() != Race.ASMODIANS)
/*     */           return; 
/*     */         break;
/*     */       case ELYOS:
/*  88 */         if (player.getCommonData().getRace() != Race.ELYOS) {
/*     */           return;
/*     */         }
/*     */         break;
/*     */     } 
/*     */     
/*  94 */     if (!item.getItemTemplate().isAllowedFor(player.getCommonData().getPlayerClass(), player.getLevel())) {
/*     */       return;
/*     */     }
/*  97 */     if (QuestEngine.getInstance().onItemUseEvent(new QuestEnv(null, player, Integer.valueOf(0), Integer.valueOf(0)), item)) {
/*     */       return;
/*     */     }
/*     */     
/* 101 */     if (player.isCasting()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 107 */     Item targetItem = player.getInventory().getItemByObjId(this.targetItemId);
/* 108 */     if (targetItem == null) {
/* 109 */       targetItem = player.getEquipment().getEquippedItemByObjId(this.targetItemId);
/*     */     }
/* 111 */     ItemActions itemActions = item.getItemTemplate().getActions();
/* 112 */     ArrayList<AbstractItemAction> actions = new ArrayList<AbstractItemAction>();
/*     */     
/* 114 */     if (itemActions == null) {
/*     */       return;
/*     */     }
/* 117 */     for (AbstractItemAction itemAction : itemActions.getItemActions()) {
/*     */ 
/*     */       
/* 120 */       if (itemAction.canAct(player, item, targetItem)) {
/* 121 */         actions.add(itemAction);
/*     */       }
/*     */     } 
/* 124 */     if (actions.size() == 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 129 */     if (player.isItemUseDisabled(item.getItemTemplate().getDelayId())) {
/*     */       
/* 131 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_ITEM_CANT_USE_UNTIL_DELAY_TIME);
/*     */       
/*     */       return;
/*     */     } 
/* 135 */     int useDelay = item.getItemTemplate().getDelayTime();
/* 136 */     player.addItemCoolDown(item.getItemTemplate().getDelayId(), System.currentTimeMillis() + useDelay, useDelay / 1000);
/*     */     
/* 138 */     for (AbstractItemAction itemAction : actions)
/*     */     {
/* 140 */       itemAction.act(player, item, targetItem);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_USE_ITEM.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */