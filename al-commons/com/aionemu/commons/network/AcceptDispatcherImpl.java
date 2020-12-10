/*    */ package com.aionemu.commons.network;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.nio.channels.SelectionKey;
/*    */ import java.util.Iterator;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AcceptDispatcherImpl
/*    */   extends Dispatcher
/*    */ {
/*    */   public AcceptDispatcherImpl(String name) throws IOException {
/* 40 */     super(name, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void dispatch() throws IOException {
/* 51 */     if (this.selector.select() != 0) {
/*    */       
/* 53 */       Iterator<SelectionKey> selectedKeys = this.selector.selectedKeys().iterator();
/* 54 */       while (selectedKeys.hasNext()) {
/*    */         
/* 56 */         SelectionKey key = selectedKeys.next();
/* 57 */         selectedKeys.remove();
/*    */         
/* 59 */         if (key.isValid()) {
/* 60 */           accept(key);
/*    */         }
/*    */       } 
/*    */     } 
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
/*    */   void closeConnection(AConnection con) {
/* 75 */     throw new UnsupportedOperationException("This method should never be called!");
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\network\AcceptDispatcherImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */