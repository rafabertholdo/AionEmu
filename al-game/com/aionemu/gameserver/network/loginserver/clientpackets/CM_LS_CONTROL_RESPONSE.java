/*     */ package com.aionemu.gameserver.network.loginserver.clientpackets;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.loginserver.LoginServer;
/*     */ import com.aionemu.gameserver.network.loginserver.LsClientPacket;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.Util;
/*     */ import com.aionemu.gameserver.utils.rates.Rates;
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
/*     */ public class CM_LS_CONTROL_RESPONSE
/*     */   extends LsClientPacket
/*     */ {
/*     */   private int type;
/*     */   private boolean result;
/*     */   private String playerName;
/*     */   private byte param;
/*     */   private String adminName;
/*     */   private int accountId;
/*     */   
/*     */   public CM_LS_CONTROL_RESPONSE(int opcode) {
/*  44 */     super(opcode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readImpl() {
/*  53 */     this.type = readC();
/*  54 */     this.result = (readC() == 1);
/*  55 */     this.adminName = readS();
/*  56 */     this.playerName = readS();
/*  57 */     this.param = (byte)readC();
/*  58 */     this.accountId = readD();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void runImpl() {
/*  67 */     World world = World.getInstance();
/*  68 */     Player admin = world.findPlayer(Util.convertName(this.adminName));
/*  69 */     Player player = world.findPlayer(Util.convertName(this.playerName));
/*  70 */     LoginServer.getInstance().accountUpdate(this.accountId, this.param, this.type);
/*  71 */     switch (this.type) {
/*     */       
/*     */       case 1:
/*  74 */         if (!this.result) {
/*     */           
/*  76 */           if (admin != null)
/*  77 */             PacketSendUtility.sendMessage(admin, this.playerName + " has been promoted Administrator with role " + this.param); 
/*  78 */           if (player != null)
/*     */           {
/*  80 */             PacketSendUtility.sendMessage(player, "You have been promoted Administrator with role " + this.param + " by " + this.adminName);
/*     */           }
/*     */           
/*     */           break;
/*     */         } 
/*  85 */         if (admin != null) {
/*  86 */           PacketSendUtility.sendMessage(admin, " Abnormal, the operation failed! ");
/*     */         }
/*     */         break;
/*     */       case 2:
/*  90 */         if (!this.result) {
/*     */           
/*  92 */           if (admin != null)
/*  93 */             PacketSendUtility.sendMessage(admin, this.playerName + " has been promoted membership with level " + this.param); 
/*  94 */           if (player != null) {
/*     */             
/*  96 */             player.setRates(Rates.getRatesFor(this.param));
/*  97 */             PacketSendUtility.sendMessage(player, "You have been promoted membership with level " + this.param + " by " + this.adminName);
/*     */           } 
/*     */           
/*     */           break;
/*     */         } 
/* 102 */         if (admin != null)
/* 103 */           PacketSendUtility.sendMessage(admin, " Abnormal, the operation failed! "); 
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\loginserver\clientpackets\CM_LS_CONTROL_RESPONSE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */