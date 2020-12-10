/*     */ package com.aionemu.commons.network;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ public class AcceptReadWriteDispatcherImpl
/*     */   extends Dispatcher
/*     */ {
/*  37 */   private final List<AConnection> pendingClose = new ArrayList<AConnection>();
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
/*     */   public AcceptReadWriteDispatcherImpl(String name, DisconnectionThreadPool dcPool) throws IOException {
/*  49 */     super(name, dcPool);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void dispatch() throws IOException {
/*  60 */     int selected = this.selector.select();
/*     */     
/*  62 */     processPendingClose();
/*     */     
/*  64 */     if (selected != 0) {
/*     */       
/*  66 */       Iterator<SelectionKey> selectedKeys = this.selector.selectedKeys().iterator();
/*  67 */       while (selectedKeys.hasNext()) {
/*     */         
/*  69 */         SelectionKey key = selectedKeys.next();
/*  70 */         selectedKeys.remove();
/*     */         
/*  72 */         if (!key.isValid()) {
/*     */           continue;
/*     */         }
/*     */         
/*  76 */         switch (key.readyOps()) {
/*     */           
/*     */           case 16:
/*  79 */             accept(key);
/*     */           
/*     */           case 1:
/*  82 */             read(key);
/*     */           
/*     */           case 4:
/*  85 */             write(key);
/*     */           
/*     */           case 5:
/*  88 */             read(key);
/*  89 */             if (key.isValid()) {
/*  90 */               write(key);
/*     */             }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void closeConnection(AConnection con) {
/* 106 */     synchronized (this.pendingClose) {
/*     */       
/* 108 */       this.pendingClose.add(con);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processPendingClose() {
/* 117 */     synchronized (this.pendingClose) {
/*     */       
/* 119 */       for (AConnection connection : this.pendingClose)
/* 120 */         closeConnectionImpl(connection); 
/* 121 */       this.pendingClose.clear();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\network\AcceptReadWriteDispatcherImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */