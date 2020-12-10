/*     */ package com.aionemu.gameserver.network.loginserver.clientpackets;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.loginserver.LsClientPacket;
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
/*     */ public class CM_BAN_RESPONSE
/*     */   extends LsClientPacket
/*     */ {
/*     */   private byte type;
/*     */   private int accountId;
/*     */   private String ip;
/*     */   private int time;
/*     */   private int adminObjId;
/*     */   private boolean result;
/*     */   
/*     */   public CM_BAN_RESPONSE(int opcode) {
/*  41 */     super(opcode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readImpl() {
/*  50 */     this.type = (byte)readC();
/*  51 */     this.accountId = readD();
/*  52 */     this.ip = readS();
/*  53 */     this.time = readD();
/*  54 */     this.adminObjId = readD();
/*  55 */     this.result = (readC() == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void runImpl() {
/*  64 */     Player admin = World.getInstance().findPlayer(this.adminObjId);
/*     */     
/*  66 */     if (admin == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  73 */     if (this.type == 1 || this.type == 3) {
/*     */       String message;
/*  75 */       if (this.result) {
/*     */         
/*  77 */         if (this.time < 0) {
/*  78 */           message = "Account ID " + this.accountId + " was successfully unbanned";
/*  79 */         } else if (this.time == 0) {
/*  80 */           message = "Account ID " + this.accountId + " was successfully banned";
/*     */         } else {
/*  82 */           message = "Account ID " + this.accountId + " was successfully banned for " + this.time + " minutes";
/*     */         } 
/*     */       } else {
/*  85 */         message = "Error occurred while banning player's account";
/*  86 */       }  PacketSendUtility.sendMessage(admin, message);
/*     */     } 
/*  88 */     if (this.type == 2 || this.type == 3) {
/*     */       String message;
/*  90 */       if (this.result) {
/*     */         
/*  92 */         if (this.time < 0) {
/*  93 */           message = "IP mask " + this.ip + " was successfully removed from block list";
/*  94 */         } else if (this.time == 0) {
/*  95 */           message = "IP mask " + this.ip + " was successfully added to block list";
/*     */         } else {
/*  97 */           message = "IP mask " + this.ip + " was successfully added to block list for " + this.time + " minutes";
/*     */         } 
/*     */       } else {
/* 100 */         message = "Error occurred while adding IP mask " + this.ip;
/* 101 */       }  PacketSendUtility.sendMessage(admin, message);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\loginserver\clientpackets\CM_BAN_RESPONSE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */