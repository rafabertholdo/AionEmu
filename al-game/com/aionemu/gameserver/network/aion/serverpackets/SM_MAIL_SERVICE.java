/*     */ package com.aionemu.gameserver.network.aion.serverpackets;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Letter;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.mail.MailMessage;
/*     */ import com.aionemu.gameserver.network.aion.AionConnection;
/*     */ import com.aionemu.gameserver.network.aion.MailServicePacket;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Collection;
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
/*     */ public class SM_MAIL_SERVICE
/*     */   extends MailServicePacket
/*     */ {
/*     */   private int serviceId;
/*     */   private Player player;
/*     */   private Collection<Letter> letters;
/*     */   private int haveNewMail;
/*     */   private int haveUnread;
/*     */   private int mailMessage;
/*     */   private Letter letter;
/*     */   private long time;
/*     */   private int letterId;
/*     */   private int attachmentType;
/*     */   
/*     */   public SM_MAIL_SERVICE(boolean isNewMail, boolean haveUnread) {
/*  51 */     this.serviceId = 0;
/*     */     
/*  53 */     if (isNewMail) {
/*  54 */       this.haveNewMail = 1;
/*     */     } else {
/*  56 */       this.haveNewMail = 0;
/*     */     } 
/*  58 */     if (haveUnread) {
/*  59 */       this.haveUnread = 1;
/*     */     } else {
/*  61 */       this.haveUnread = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SM_MAIL_SERVICE(MailMessage mailMessage) {
/*  70 */     this.serviceId = 1;
/*  71 */     this.mailMessage = mailMessage.getId();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SM_MAIL_SERVICE(Player player, Collection<Letter> letters) {
/*  81 */     this.serviceId = 2;
/*  82 */     this.player = player;
/*  83 */     this.letters = letters;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SM_MAIL_SERVICE(Player player, Letter letter, long time) {
/*  94 */     this.serviceId = 3;
/*  95 */     this.player = player;
/*  96 */     this.letter = letter;
/*  97 */     this.time = time;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SM_MAIL_SERVICE(int letterId, int attachmentType) {
/* 107 */     this.serviceId = 5;
/* 108 */     this.letterId = letterId;
/* 109 */     this.attachmentType = attachmentType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SM_MAIL_SERVICE(int letterId) {
/* 118 */     this.serviceId = 6;
/* 119 */     this.letterId = letterId;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeImpl(AionConnection con, ByteBuffer buf) {
/* 125 */     switch (this.serviceId) {
/*     */       
/*     */       case 0:
/* 128 */         writeMailboxState(buf, this.haveNewMail, this.haveUnread);
/*     */         break;
/*     */       
/*     */       case 1:
/* 132 */         writeMailMessage(buf, this.mailMessage);
/*     */         break;
/*     */       
/*     */       case 2:
/* 136 */         if (this.letters.size() > 0) {
/* 137 */           writeLettersList(buf, this.letters, this.player); break;
/*     */         } 
/* 139 */         writeEmptyLettersList(buf, this.player);
/*     */         break;
/*     */       
/*     */       case 3:
/* 143 */         writeLetterRead(buf, this.letter, this.time);
/*     */         break;
/*     */       
/*     */       case 5:
/* 147 */         writeLetterState(buf, this.letterId, this.attachmentType);
/*     */         break;
/*     */       
/*     */       case 6:
/* 151 */         writeLetterDelete(buf, this.letterId);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_MAIL_SERVICE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */