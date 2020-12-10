/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.commons.database.dao.DAOManager;
/*     */ import com.aionemu.gameserver.dao.InventoryDAO;
/*     */ import com.aionemu.gameserver.dao.MailDAO;
/*     */ import com.aionemu.gameserver.dao.PlayerDAO;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.Letter;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Mailbox;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Storage;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.StorageType;
/*     */ import com.aionemu.gameserver.model.templates.item.ItemQuality;
/*     */ import com.aionemu.gameserver.model.templates.mail.MailMessage;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ADD_ITEMS;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_MAIL_SERVICE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_UPDATE_ITEM;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import com.aionemu.gameserver.utils.idfactory.IDFactory;
/*     */ import com.aionemu.gameserver.world.World;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collections;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class MailService
/*     */ {
/*  52 */   private static final Logger log = Logger.getLogger(MailService.class);
/*     */ 
/*     */   
/*     */   public static final MailService getInstance() {
/*  56 */     return SingletonHolder.instance;
/*     */   }
/*     */ 
/*     */   
/*     */   private MailService() {
/*  61 */     log.info("MailService: Initialized.");
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendMail(Player sender, String recipientName, String title, String message, int attachedItemObjId, int attachedItemCount, int attachedKinahCount, boolean express) {
/*     */     Player onlineRecipient;
/*  78 */     if (express) {
/*     */       return;
/*     */     }
/*  81 */     if (recipientName.length() > 16) {
/*     */       return;
/*     */     }
/*  84 */     if (title.length() > 20) {
/*  85 */       title = title.substring(0, 20);
/*     */     }
/*  87 */     if (message.length() > 1000) {
/*  88 */       message = message.substring(0, 1000);
/*     */     }
/*  90 */     if (!((PlayerDAO)DAOManager.getDAO(PlayerDAO.class)).isNameUsed(recipientName)) {
/*     */       
/*  92 */       PacketSendUtility.sendPacket(sender, (AionServerPacket)new SM_MAIL_SERVICE(MailMessage.NO_SUCH_CHARACTER_NAME));
/*     */       
/*     */       return;
/*     */     } 
/*  96 */     PlayerCommonData recipientCommonData = ((PlayerDAO)DAOManager.getDAO(PlayerDAO.class)).loadPlayerCommonDataByName(recipientName);
/*     */ 
/*     */ 
/*     */     
/* 100 */     if (recipientCommonData == null) {
/*     */       
/* 102 */       PacketSendUtility.sendPacket(sender, (AionServerPacket)new SM_MAIL_SERVICE(MailMessage.NO_SUCH_CHARACTER_NAME));
/*     */       
/*     */       return;
/*     */     } 
/* 106 */     if (recipientCommonData.getRace().getRaceId() != sender.getCommonData().getRace().getRaceId()) {
/*     */       
/* 108 */       PacketSendUtility.sendPacket(sender, (AionServerPacket)new SM_MAIL_SERVICE(MailMessage.MAIL_IS_ONE_RACE_ONLY));
/*     */       
/*     */       return;
/*     */     } 
/* 112 */     if (recipientCommonData.isOnline()) {
/*     */       
/* 114 */       onlineRecipient = World.getInstance().findPlayer(recipientCommonData.getPlayerObjId());
/* 115 */       if (!onlineRecipient.getMailbox().haveFreeSlots()) {
/*     */         
/* 117 */         PacketSendUtility.sendPacket(sender, (AionServerPacket)new SM_MAIL_SERVICE(MailMessage.RECIPIENT_MAILBOX_FULL));
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */     } else {
/* 123 */       if (recipientCommonData.getMailboxLetters() >= 100) {
/*     */         
/* 125 */         PacketSendUtility.sendPacket(sender, (AionServerPacket)new SM_MAIL_SERVICE(MailMessage.RECIPIENT_MAILBOX_FULL));
/*     */         return;
/*     */       } 
/* 128 */       onlineRecipient = null;
/*     */     } 
/*     */     
/* 131 */     if (!validateMailSendPrice(sender, attachedKinahCount, attachedItemObjId, attachedItemCount)) {
/*     */       return;
/*     */     }
/* 134 */     Item attachedItem = null;
/* 135 */     int finalAttachedKinahCount = 0;
/*     */     
/* 137 */     int kinahMailCommission = 0;
/* 138 */     int itemMailCommission = 0;
/*     */     
/* 140 */     Storage senderInventory = sender.getInventory();
/*     */     
/* 142 */     if (attachedItemObjId != 0) {
/*     */       
/* 144 */       Item senderItem = senderInventory.getItemByObjId(attachedItemObjId);
/*     */ 
/*     */       
/* 147 */       if (!senderItem.isTradeable()) {
/*     */         return;
/*     */       }
/* 150 */       if (senderItem != null) {
/*     */         float qualityPriceRate;
/*     */         
/* 153 */         switch (senderItem.getItemTemplate().getItemQuality()) {
/*     */           
/*     */           case JUNK:
/*     */           case COMMON:
/* 157 */             qualityPriceRate = 0.02F;
/*     */             break;
/*     */           
/*     */           case RARE:
/* 161 */             qualityPriceRate = 0.03F;
/*     */             break;
/*     */           
/*     */           case LEGEND:
/*     */           case UNIQUE:
/* 166 */             qualityPriceRate = 0.04F;
/*     */             break;
/*     */           
/*     */           case MYTHIC:
/*     */           case EPIC:
/* 171 */             qualityPriceRate = 0.05F;
/*     */             break;
/*     */           
/*     */           default:
/* 175 */             qualityPriceRate = 0.02F;
/*     */             break;
/*     */         } 
/*     */         
/* 179 */         if (senderItem.getItemCount() == attachedItemCount) {
/*     */           
/* 181 */           ItemService.removeItemFromInventory(sender, senderItem, false);
/*     */           
/* 183 */           senderItem.setEquipped(false);
/* 184 */           senderItem.setEquipmentSlot(0);
/* 185 */           senderItem.setItemLocation(StorageType.MAILBOX.getId());
/*     */           
/* 187 */           attachedItem = senderItem;
/* 188 */           attachedItem.setOwnerId(recipientCommonData.getPlayerObjId());
/* 189 */           itemMailCommission = Math.round((float)(senderItem.getItemTemplate().getPrice() * attachedItem.getItemCount()) * qualityPriceRate);
/*     */ 
/*     */         
/*     */         }
/* 193 */         else if (senderItem.getItemCount() > attachedItemCount) {
/*     */           
/* 195 */           attachedItem = ItemService.newItem(senderItem.getItemTemplate().getTemplateId(), attachedItemCount);
/*     */           
/* 197 */           senderItem.decreaseItemCount(attachedItemCount);
/* 198 */           PacketSendUtility.sendPacket(sender, (AionServerPacket)new SM_UPDATE_ITEM(senderItem));
/*     */           
/* 200 */           attachedItem.setEquipped(false);
/* 201 */           attachedItem.setEquipmentSlot(0);
/* 202 */           attachedItem.setItemLocation(StorageType.MAILBOX.getId());
/* 203 */           attachedItem.setOwnerId(recipientCommonData.getPlayerObjId());
/*     */           
/* 205 */           itemMailCommission = Math.round((float)(attachedItem.getItemTemplate().getPrice() * attachedItem.getItemCount()) * qualityPriceRate);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 212 */     if (attachedKinahCount > 0)
/*     */     {
/* 214 */       if (senderInventory.getKinahItem().getItemCount() - attachedKinahCount >= 0L) {
/*     */         
/* 216 */         finalAttachedKinahCount = attachedKinahCount;
/* 217 */         kinahMailCommission = Math.round(attachedKinahCount * 0.01F);
/*     */       } 
/*     */     }
/*     */     
/* 221 */     Timestamp time = new Timestamp(Calendar.getInstance().getTimeInMillis());
/*     */     
/* 223 */     Letter newLetter = new Letter(IDFactory.getInstance().nextId(), recipientCommonData.getPlayerObjId(), attachedItem, finalAttachedKinahCount, title, message, sender.getName(), time, true, express);
/*     */ 
/*     */     
/* 226 */     if (!((MailDAO)DAOManager.getDAO(MailDAO.class)).storeLetter(time, newLetter)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 232 */     if (!ItemService.decreaseKinah(sender, finalAttachedKinahCount)) {
/*     */       
/* 234 */       log.warn("[AUDIT]Mail kinah exploit: " + sender.getObjectId());
/*     */       
/*     */       return;
/*     */     } 
/* 238 */     if (attachedItem != null && 
/* 239 */       !((InventoryDAO)DAOManager.getDAO(InventoryDAO.class)).store(attachedItem)) {
/*     */       return;
/*     */     }
/* 242 */     int finalMailCommission = 10 + kinahMailCommission + itemMailCommission;
/*     */     
/* 244 */     if (senderInventory.getKinahItem().getItemCount() > finalMailCommission) {
/* 245 */       ItemService.decreaseKinah(sender, finalMailCommission);
/*     */     } else {
/*     */       
/* 248 */       log.warn("[AUDIT]Mail kinah exploit: " + sender.getObjectId());
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 255 */     if (onlineRecipient != null) {
/*     */       
/* 257 */       Mailbox recipientMailbox = onlineRecipient.getMailbox();
/* 258 */       recipientMailbox.putLetterToMailbox(newLetter);
/*     */       
/* 260 */       PacketSendUtility.sendPacket(onlineRecipient, (AionServerPacket)new SM_MAIL_SERVICE(onlineRecipient, onlineRecipient.getMailbox().getLetters()));
/*     */       
/* 262 */       PacketSendUtility.sendPacket(onlineRecipient, (AionServerPacket)new SM_MAIL_SERVICE(false, false));
/* 263 */       PacketSendUtility.sendPacket(onlineRecipient, (AionServerPacket)new SM_MAIL_SERVICE(true, true));
/*     */     } 
/*     */     
/* 266 */     PacketSendUtility.sendPacket(sender, (AionServerPacket)new SM_MAIL_SERVICE(MailMessage.MAIL_SEND_SECCESS));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 271 */     if (!recipientCommonData.isOnline()) {
/*     */       
/* 273 */       recipientCommonData.setMailboxLetters(recipientCommonData.getMailboxLetters() + 1);
/* 274 */       ((MailDAO)DAOManager.getDAO(MailDAO.class)).updateOfflineMailCounter(recipientCommonData);
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
/*     */   public void readMail(Player player, int letterId) {
/* 287 */     Letter letter = player.getMailbox().getLetterFromMailbox(letterId);
/* 288 */     if (letter == null) {
/*     */       
/* 290 */       log.warn("Cannot read mail " + player.getObjectId() + " " + letterId);
/*     */       
/*     */       return;
/*     */     } 
/* 294 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_MAIL_SERVICE(player, letter, letter.getTimeStamp().getTime()));
/* 295 */     letter.setReadLetter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getAttachments(Player player, int letterId, int attachmentType) {
/*     */     Item attachedItem, inventoryItem;
/* 306 */     Letter letter = player.getMailbox().getLetterFromMailbox(letterId);
/*     */     
/* 308 */     if (letter == null) {
/*     */       return;
/*     */     }
/* 311 */     switch (attachmentType) {
/*     */ 
/*     */       
/*     */       case 0:
/* 315 */         attachedItem = letter.getAttachedItem();
/* 316 */         if (attachedItem == null)
/*     */           return; 
/* 318 */         if (player.getInventory().isFull()) {
/*     */           
/* 320 */           PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.MSG_FULL_INVENTORY);
/*     */           return;
/*     */         } 
/* 323 */         inventoryItem = player.getInventory().putToBag(attachedItem);
/* 324 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_ADD_ITEMS(Collections.singletonList(inventoryItem)));
/* 325 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_MAIL_SERVICE(letterId, attachmentType));
/* 326 */         letter.removeAttachedItem();
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1:
/* 331 */         ItemService.increaseKinah(player, letter.getAttachedKinah());
/* 332 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_MAIL_SERVICE(letterId, attachmentType));
/* 333 */         letter.removeAttachedKinah();
/*     */         break;
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
/*     */   public void deleteMail(Player player, int letterId) {
/* 346 */     Mailbox mailbox = player.getMailbox();
/*     */     
/* 348 */     mailbox.removeLetter(letterId);
/* 349 */     ((MailDAO)DAOManager.getDAO(MailDAO.class)).deleteLetter(letterId);
/* 350 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_MAIL_SERVICE(letterId));
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
/*     */ 
/*     */   
/*     */   private boolean validateMailSendPrice(Player sender, int attachedKinahCount, int attachedItemObjId, int attachedItemCount) {
/* 364 */     int itemMailCommission = 0;
/* 365 */     int kinahMailCommission = Math.round(attachedKinahCount * 0.01F);
/* 366 */     if (attachedItemObjId != 0) {
/*     */       float qualityPriceRate;
/* 368 */       Item senderItem = sender.getInventory().getItemByObjId(attachedItemObjId);
/*     */       
/* 370 */       switch (senderItem.getItemTemplate().getItemQuality()) {
/*     */         
/*     */         case JUNK:
/*     */         case COMMON:
/* 374 */           qualityPriceRate = 0.02F;
/*     */           break;
/*     */         
/*     */         case RARE:
/* 378 */           qualityPriceRate = 0.03F;
/*     */           break;
/*     */         
/*     */         case LEGEND:
/*     */         case UNIQUE:
/* 383 */           qualityPriceRate = 0.04F;
/*     */           break;
/*     */         
/*     */         case MYTHIC:
/*     */         case EPIC:
/* 388 */           qualityPriceRate = 0.05F;
/*     */           break;
/*     */         
/*     */         default:
/* 392 */           qualityPriceRate = 0.02F;
/*     */           break;
/*     */       } 
/*     */       
/* 396 */       itemMailCommission = Math.round((senderItem.getItemTemplate().getPrice() * attachedItemCount) * qualityPriceRate);
/*     */     } 
/*     */ 
/*     */     
/* 400 */     int finalMailPrice = 10 + itemMailCommission + kinahMailCommission;
/*     */     
/* 402 */     if (sender.getInventory().getKinahItem().getItemCount() >= finalMailPrice) {
/* 403 */       return true;
/*     */     }
/* 405 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPlayerLogin(Player player) {
/* 414 */     ThreadPoolManager.getInstance().schedule(new MailLoadTask(player), 5000L);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class MailLoadTask
/*     */     implements Runnable
/*     */   {
/*     */     private Player player;
/*     */ 
/*     */ 
/*     */     
/*     */     private MailLoadTask(Player player) {
/* 427 */       this.player = player;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/* 433 */       this.player.setMailbox(((MailDAO)DAOManager.getDAO(MailDAO.class)).loadPlayerMailbox(this.player));
/* 434 */       PacketSendUtility.sendPacket(this.player, (AionServerPacket)new SM_MAIL_SERVICE(this.player, this.player.getMailbox().getLetters()));
/* 435 */       if (this.player.getMailbox().haveUnread()) {
/* 436 */         PacketSendUtility.sendPacket(this.player, (AionServerPacket)new SM_MAIL_SERVICE(true, true));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static class SingletonHolder
/*     */   {
/* 443 */     protected static final MailService instance = new MailService();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\MailService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */