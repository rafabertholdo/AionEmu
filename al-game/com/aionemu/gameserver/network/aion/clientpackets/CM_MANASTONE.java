/*     */ package com.aionemu.gameserver.network.aion.clientpackets;
/*     */ 
/*     */ import com.aionemu.gameserver.itemengine.actions.EnchantItemAction;
/*     */ import com.aionemu.gameserver.model.gameobjects.AionObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*     */ import com.aionemu.gameserver.network.aion.AionConnection;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.world.World;
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
/*     */ 
/*     */ public class CM_MANASTONE
/*     */   extends AionClientPacket
/*     */ {
/*     */   private int npcObjId;
/*     */   private int slotNum;
/*     */   private int actionType;
/*     */   private int stoneUniqueId;
/*     */   private int targetItemUniqueId;
/*     */   private int supplementUniqueId;
/*     */   
/*     */   public CM_MANASTONE(int opcode) {
/*  48 */     super(opcode);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readImpl() {
/*  54 */     this.actionType = readC();
/*  55 */     readC();
/*  56 */     this.targetItemUniqueId = readD();
/*  57 */     switch (this.actionType) {
/*     */       
/*     */       case 1:
/*     */       case 2:
/*  61 */         this.stoneUniqueId = readD();
/*  62 */         this.supplementUniqueId = readD();
/*     */         break;
/*     */       case 3:
/*  65 */         this.slotNum = readC();
/*  66 */         readC();
/*  67 */         readH();
/*  68 */         this.npcObjId = readD();
/*     */         break;
/*     */     } 
/*     */   }
/*     */   protected void runImpl() {
/*     */     EnchantItemAction action;
/*     */     Item manastone, targetItem;
/*     */     long price;
/*  76 */     AionObject npc = World.getInstance().findAionObject(this.npcObjId);
/*  77 */     Player player = ((AionConnection)getConnection()).getActivePlayer();
/*     */     
/*  79 */     switch (this.actionType) {
/*     */       
/*     */       case 1:
/*     */       case 2:
/*  83 */         action = new EnchantItemAction();
/*  84 */         manastone = player.getInventory().getItemByObjId(this.stoneUniqueId);
/*  85 */         targetItem = player.getInventory().getItemByObjId(this.targetItemUniqueId);
/*  86 */         if (targetItem == null)
/*     */         {
/*  88 */           targetItem = player.getEquipment().getEquippedItemByObjId(this.targetItemUniqueId);
/*     */         }
/*  90 */         if (manastone != null && targetItem != null && action.canAct(player, manastone, targetItem)) {
/*     */           
/*  92 */           Item supplement = player.getInventory().getItemByObjId(this.supplementUniqueId);
/*  93 */           action.act(player, manastone, targetItem, supplement);
/*     */         } 
/*     */         break;
/*     */       case 3:
/*  97 */         price = player.getPrices().getPriceForService(500L);
/*  98 */         if (!ItemService.decreaseKinah(player, price)) {
/*     */           
/* 100 */           PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.NOT_ENOUGH_KINAH(price));
/*     */           return;
/*     */         } 
/* 103 */         if (npc != null) {
/*     */           
/* 105 */           ItemService.decreaseKinah(player, price);
/* 106 */           ItemService.removeManastone(player, this.targetItemUniqueId, this.slotNum);
/*     */         } 
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_MANASTONE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */