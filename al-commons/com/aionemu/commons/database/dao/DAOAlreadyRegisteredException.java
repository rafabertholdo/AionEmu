/*    */ package com.aionemu.commons.database.dao;
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
/*    */ 
/*    */ public class DAOAlreadyRegisteredException
/*    */   extends DAOException
/*    */ {
/*    */   private static final long serialVersionUID = -4966845154050833016L;
/*    */   
/*    */   public DAOAlreadyRegisteredException() {}
/*    */   
/*    */   public DAOAlreadyRegisteredException(String message) {
/* 41 */     super(message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DAOAlreadyRegisteredException(String message, Throwable cause) {
/* 51 */     super(message, cause);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DAOAlreadyRegisteredException(Throwable cause) {
/* 60 */     super(cause);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\database\dao\DAOAlreadyRegisteredException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */