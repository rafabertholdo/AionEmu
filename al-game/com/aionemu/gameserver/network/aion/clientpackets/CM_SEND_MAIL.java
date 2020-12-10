/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.services.MailService;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CM_SEND_MAIL
/*    */   extends AionClientPacket
/*    */ {
/*    */   private String recipientName;
/*    */   private String title;
/*    */   private String message;
/*    */   private int itemObjId;
/*    */   private int itemCount;
/*    */   private int kinahCount;
/*    */   private int express;
/*    */   
/*    */   public CM_SEND_MAIL(int opcode) {
/* 39 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 45 */     this.recipientName = readS();
/* 46 */     this.title = readS();
/* 47 */     this.message = readS();
/* 48 */     this.itemObjId = readD();
/* 49 */     this.itemCount = readD();
/* 50 */     readD();
/* 51 */     this.kinahCount = readD();
/* 52 */     readD();
/* 53 */     this.express = readC();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 59 */     Player player = ((AionConnection)getConnection()).getActivePlayer();
/* 60 */     if (!player.isTrading()) {
/*    */ 
/*    */       
/* 63 */       if (this.express == 0)
/* 64 */         MailService.getInstance().sendMail(player, this.recipientName, this.title, this.message, this.itemObjId, this.itemCount, this.kinahCount, false); 
/* 65 */       if (this.express == 1)
/* 66 */         MailService.getInstance().sendMail(player, this.recipientName, this.title, this.message, this.itemObjId, this.itemCount, this.kinahCount, true); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_SEND_MAIL.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */