/*    */ package com.aionemu.gameserver.model.gameobjects.player;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
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
/*    */ 
/*    */ public abstract class RequestResponseHandler
/*    */ {
/*    */   private Creature requester;
/*    */   
/*    */   public RequestResponseHandler(Creature requester) {
/* 34 */     this.requester = requester;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void handle(Player responder, int response) {
/* 45 */     if (response == 0) {
/* 46 */       denyRequest(this.requester, responder);
/*    */     } else {
/* 48 */       acceptRequest(this.requester, responder);
/*    */     } 
/*    */   }
/*    */   
/*    */   public abstract void acceptRequest(Creature paramCreature, Player paramPlayer);
/*    */   
/*    */   public abstract void denyRequest(Creature paramCreature, Player paramPlayer);
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\RequestResponseHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */