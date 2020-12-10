/*     */ package com.aionemu.gameserver.network.aion.clientpackets;
/*     */ 
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.TradeListTemplate;
/*     */ import com.aionemu.gameserver.model.trade.TradeList;
/*     */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*     */ import com.aionemu.gameserver.network.aion.AionConnection;
/*     */ import com.aionemu.gameserver.services.PrivateStoreService;
/*     */ import com.aionemu.gameserver.services.TradeService;
/*     */ import com.aionemu.gameserver.world.World;
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
/*     */ 
/*     */ 
/*     */ public class CM_BUY_ITEM
/*     */   extends AionClientPacket
/*     */ {
/*     */   private int sellerObjId;
/*     */   private int unk1;
/*     */   private int amount;
/*     */   private int itemId;
/*     */   private int count;
/*     */   public int unk2;
/*     */   
/*     */   public CM_BUY_ITEM(int opcode) {
/*  49 */     super(opcode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   private static final Logger log = Logger.getLogger(CM_BUY_ITEM.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private TradeList tradeList;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readImpl() {
/*  65 */     this.sellerObjId = readD();
/*  66 */     this.unk1 = readH();
/*  67 */     this.amount = readH();
/*     */     
/*  69 */     this.tradeList = new TradeList();
/*  70 */     this.tradeList.setSellerObjId(this.sellerObjId);
/*     */     
/*  72 */     for (int i = 0; i < this.amount; i++) {
/*     */       
/*  74 */       this.itemId = readD();
/*  75 */       this.count = readD();
/*  76 */       this.unk2 = readD();
/*     */ 
/*     */       
/*  79 */       if (this.count >= 1)
/*     */       {
/*     */         
/*  82 */         if (this.unk1 == 13 || this.unk1 == 14) {
/*     */           
/*  84 */           this.tradeList.addBuyItem(this.itemId, this.count);
/*     */         }
/*  86 */         else if (this.unk1 == 0 || this.unk1 == 1) {
/*     */           
/*  88 */           this.tradeList.addSellItem(this.itemId, this.count);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void runImpl() {
/*     */     Player targetPlayer;
/*     */     Npc npc;
/*     */     TradeListTemplate tlist;
/*  99 */     Player player = ((AionConnection)getConnection()).getActivePlayer();
/*     */     
/* 101 */     switch (this.unk1) {
/*     */       
/*     */       case 0:
/* 104 */         targetPlayer = (Player)World.getInstance().findAionObject(this.sellerObjId);
/* 105 */         PrivateStoreService.sellStoreItem(targetPlayer, player, this.tradeList);
/*     */         return;
/*     */       
/*     */       case 1:
/* 109 */         TradeService.performSellToShop(player, this.tradeList);
/*     */         return;
/*     */       
/*     */       case 13:
/* 113 */         TradeService.performBuyFromShop(player, this.tradeList);
/*     */         return;
/*     */       
/*     */       case 14:
/* 117 */         npc = (Npc)World.getInstance().findAionObject(this.sellerObjId);
/* 118 */         tlist = DataManager.TRADE_LIST_DATA.getTradeListTemplate(npc.getNpcId());
/* 119 */         if (tlist.isAbyss()) {
/* 120 */           TradeService.performBuyFromAbyssShop(player, this.tradeList);
/*     */         }
/*     */         return;
/*     */     } 
/* 124 */     log.info(String.format("Unhandle shop action unk1: %d", new Object[] { Integer.valueOf(this.unk1) }));
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_BUY_ITEM.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */