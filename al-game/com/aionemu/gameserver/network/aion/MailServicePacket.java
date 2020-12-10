/*     */ package com.aionemu.gameserver.network.aion;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.Letter;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.item.ItemTemplate;
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
/*     */ public abstract class MailServicePacket
/*     */   extends InventoryPacket
/*     */ {
/*     */   protected void writeLettersList(ByteBuffer buf, Collection<Letter> letters, Player player) {
/*  35 */     writeC(buf, 2);
/*  36 */     writeD(buf, player.getObjectId());
/*  37 */     writeC(buf, 0);
/*  38 */     writeH(buf, player.getMailbox().getFreeSlots());
/*     */     
/*  40 */     for (Letter letter : letters) {
/*     */       
/*  42 */       writeD(buf, letter.getObjectId());
/*  43 */       writeS(buf, letter.getSenderName());
/*  44 */       writeS(buf, letter.getTitle());
/*  45 */       if (letter.isUnread()) {
/*  46 */         writeC(buf, 0);
/*     */       } else {
/*  48 */         writeC(buf, 1);
/*  49 */       }  if (letter.getAttachedItem() != null) {
/*     */         
/*  51 */         writeD(buf, letter.getAttachedItem().getObjectId());
/*  52 */         writeD(buf, letter.getAttachedItem().getItemTemplate().getTemplateId());
/*     */       }
/*     */       else {
/*     */         
/*  56 */         writeD(buf, 0);
/*  57 */         writeD(buf, 0);
/*     */       } 
/*  59 */       writeD(buf, (int)letter.getAttachedKinah());
/*  60 */       writeD(buf, 0);
/*  61 */       writeC(buf, 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void writeEmptyLettersList(ByteBuffer buf, Player player) {
/*  67 */     writeC(buf, 2);
/*  68 */     writeD(buf, player.getObjectId());
/*  69 */     writeH(buf, 0);
/*  70 */     writeC(buf, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void writeMailMessage(ByteBuffer buf, int messageId) {
/*  75 */     writeC(buf, 1);
/*  76 */     writeC(buf, messageId);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void writeMailboxState(ByteBuffer buf, int haveNewMail, int haveUnread) {
/*  81 */     writeC(buf, 0);
/*  82 */     writeC(buf, haveNewMail);
/*  83 */     writeC(buf, 0);
/*  84 */     writeC(buf, haveUnread);
/*  85 */     writeD(buf, 0);
/*  86 */     writeC(buf, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void writeLetterRead(ByteBuffer buf, Letter letter, long time) {
/*  91 */     writeC(buf, 3);
/*  92 */     writeD(buf, letter.getRecipientId());
/*  93 */     writeD(buf, 1);
/*  94 */     writeD(buf, 0);
/*  95 */     writeD(buf, letter.getObjectId());
/*  96 */     writeD(buf, letter.getRecipientId());
/*  97 */     writeS(buf, letter.getSenderName());
/*  98 */     writeS(buf, letter.getTitle());
/*  99 */     writeS(buf, letter.getMessage());
/*     */     
/* 101 */     Item item = letter.getAttachedItem();
/* 102 */     if (item != null) {
/*     */       
/* 104 */       ItemTemplate itemTemplate = item.getItemTemplate();
/*     */       
/* 106 */       writeMailGeneralInfo(buf, item);
/*     */       
/* 108 */       if (itemTemplate.isArmor()) {
/* 109 */         writeArmorInfo(buf, item, false, false, true);
/* 110 */       } else if (itemTemplate.isWeapon()) {
/* 111 */         writeWeaponInfo(buf, item, false, false, false, true);
/*     */       } else {
/* 113 */         writeGeneralItemInfo(buf, item, false, true);
/*     */       } 
/*     */     } else {
/*     */       
/* 117 */       writeD(buf, 0);
/* 118 */       writeD(buf, 0);
/* 119 */       writeD(buf, 0);
/* 120 */       writeD(buf, 0);
/* 121 */       writeD(buf, 0);
/*     */     } 
/*     */     
/* 124 */     writeD(buf, (int)letter.getAttachedKinah());
/* 125 */     writeD(buf, 0);
/* 126 */     writeC(buf, 0);
/* 127 */     writeQ(buf, time / 1000L);
/* 128 */     writeC(buf, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void writeLetterState(ByteBuffer buf, int letterId, int attachmentType) {
/* 133 */     writeC(buf, 5);
/* 134 */     writeD(buf, letterId);
/* 135 */     writeC(buf, attachmentType);
/* 136 */     writeC(buf, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void writeLetterDelete(ByteBuffer buf, int letterId) {
/* 141 */     writeC(buf, 6);
/* 142 */     writeD(buf, 0);
/* 143 */     writeD(buf, 0);
/* 144 */     writeD(buf, letterId);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\MailServicePacket.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */