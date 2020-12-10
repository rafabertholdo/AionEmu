/*    */ package com.aionemu.gameserver.model.gameobjects.player;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import org.apache.log4j.Logger;
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
/*    */ public class ResponseRequester
/*    */ {
/* 31 */   private static final Logger log = Logger.getLogger(ResponseRequester.class);
/*    */   
/*    */   private Player player;
/* 34 */   private HashMap<Integer, RequestResponseHandler> map = new HashMap<Integer, RequestResponseHandler>();
/*    */ 
/*    */   
/*    */   public ResponseRequester(Player player) {
/* 38 */     this.player = player;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized boolean putRequest(int messageId, RequestResponseHandler handler) {
/* 48 */     if (this.map.containsKey(Integer.valueOf(messageId))) {
/* 49 */       return false;
/*    */     }
/* 51 */     this.map.put(Integer.valueOf(messageId), handler);
/* 52 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized boolean respond(int messageId, int response) {
/* 64 */     RequestResponseHandler handler = this.map.get(Integer.valueOf(messageId));
/* 65 */     if (handler != null) {
/*    */       
/* 67 */       this.map.remove(Integer.valueOf(messageId));
/* 68 */       log.debug("RequestResponseHandler triggered for response code " + messageId + " from " + this.player.getName());
/* 69 */       handler.handle(this.player, response);
/* 70 */       return true;
/*    */     } 
/* 72 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized void denyAll() {
/* 80 */     for (RequestResponseHandler handler : this.map.values())
/*    */     {
/* 82 */       handler.handle(this.player, 0);
/*    */     }
/*    */     
/* 85 */     this.map.clear();
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\ResponseRequester.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */