/*    */ package com.aionemu.gameserver.model.gameobjects.player;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockedPlayer
/*    */ {
/*    */   PlayerCommonData pcd;
/*    */   String reason;
/*    */   
/*    */   public BlockedPlayer(PlayerCommonData pcd) {
/* 32 */     this(pcd, "");
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockedPlayer(PlayerCommonData pcd, String reason) {
/* 37 */     this.pcd = pcd;
/* 38 */     this.reason = reason;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getObjId() {
/* 43 */     return this.pcd.getPlayerObjId();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 48 */     return this.pcd.getName();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getReason() {
/* 53 */     return this.reason;
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized void setReason(String reason) {
/* 58 */     this.reason = reason;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\BlockedPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */