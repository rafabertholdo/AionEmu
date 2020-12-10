/*    */ package com.aionemu.gameserver.model.templates.mail;
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
/*    */ public enum MailMessage
/*    */ {
/* 25 */   MAIL_SEND_SECCESS(0),
/* 26 */   NO_SUCH_CHARACTER_NAME(1),
/* 27 */   RECIPIENT_MAILBOX_FULL(2),
/* 28 */   MAIL_IS_ONE_RACE_ONLY(3),
/* 29 */   YOU_ARE_IN_RECIPIENT_IGNORE_LIST(4),
/* 30 */   RECIPIENT_IGNORING_MAIL_FROM_PLAYERS_LOWER_206_LVL(5),
/* 31 */   MAILSPAM_WAIT_FOR_SOME_TIME(6);
/*    */   
/*    */   private int id;
/*    */ 
/*    */   
/*    */   MailMessage(int id) {
/* 37 */     this.id = id;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 42 */     return this.id;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\mail\MailMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */