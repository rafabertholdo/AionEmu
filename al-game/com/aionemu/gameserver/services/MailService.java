package com.aionemu.gameserver.services;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.dao.InventoryDAO;
import com.aionemu.gameserver.dao.MailDAO;
import com.aionemu.gameserver.dao.PlayerDAO;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Letter;
import com.aionemu.gameserver.model.gameobjects.player.Mailbox;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import com.aionemu.gameserver.model.gameobjects.player.Storage;
import com.aionemu.gameserver.model.gameobjects.player.StorageType;
import com.aionemu.gameserver.model.templates.item.ItemQuality;
import com.aionemu.gameserver.model.templates.mail.MailMessage;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ADD_ITEMS;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MAIL_SERVICE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_UPDATE_ITEM;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.utils.idfactory.IDFactory;
import com.aionemu.gameserver.world.World;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collections;
import org.apache.log4j.Logger;





















public class MailService
{
  private static final Logger log = Logger.getLogger(MailService.class);

  
  public static final MailService getInstance() {
    return SingletonHolder.instance;
  }

  
  private MailService() {
    log.info("MailService: Initialized.");
  }












  
  public void sendMail(Player sender, String recipientName, String title, String message, int attachedItemObjId, int attachedItemCount, int attachedKinahCount, boolean express) {
    Player onlineRecipient;
    if (express) {
      return;
    }
    if (recipientName.length() > 16) {
      return;
    }
    if (title.length() > 20) {
      title = title.substring(0, 20);
    }
    if (message.length() > 1000) {
      message = message.substring(0, 1000);
    }
    if (!((PlayerDAO)DAOManager.getDAO(PlayerDAO.class)).isNameUsed(recipientName)) {
      
      PacketSendUtility.sendPacket(sender, (AionServerPacket)new SM_MAIL_SERVICE(MailMessage.NO_SUCH_CHARACTER_NAME));
      
      return;
    } 
    PlayerCommonData recipientCommonData = ((PlayerDAO)DAOManager.getDAO(PlayerDAO.class)).loadPlayerCommonDataByName(recipientName);


    
    if (recipientCommonData == null) {
      
      PacketSendUtility.sendPacket(sender, (AionServerPacket)new SM_MAIL_SERVICE(MailMessage.NO_SUCH_CHARACTER_NAME));
      
      return;
    } 
    if (recipientCommonData.getRace().getRaceId() != sender.getCommonData().getRace().getRaceId()) {
      
      PacketSendUtility.sendPacket(sender, (AionServerPacket)new SM_MAIL_SERVICE(MailMessage.MAIL_IS_ONE_RACE_ONLY));
      
      return;
    } 
    if (recipientCommonData.isOnline()) {
      
      onlineRecipient = World.getInstance().findPlayer(recipientCommonData.getPlayerObjId());
      if (!onlineRecipient.getMailbox().haveFreeSlots()) {
        
        PacketSendUtility.sendPacket(sender, (AionServerPacket)new SM_MAIL_SERVICE(MailMessage.RECIPIENT_MAILBOX_FULL));

        
        return;
      } 
    } else {
      if (recipientCommonData.getMailboxLetters() >= 100) {
        
        PacketSendUtility.sendPacket(sender, (AionServerPacket)new SM_MAIL_SERVICE(MailMessage.RECIPIENT_MAILBOX_FULL));
        return;
      } 
      onlineRecipient = null;
    } 
    
    if (!validateMailSendPrice(sender, attachedKinahCount, attachedItemObjId, attachedItemCount)) {
      return;
    }
    Item attachedItem = null;
    int finalAttachedKinahCount = 0;
    
    int kinahMailCommission = 0;
    int itemMailCommission = 0;
    
    Storage senderInventory = sender.getInventory();
    
    if (attachedItemObjId != 0) {
      
      Item senderItem = senderInventory.getItemByObjId(attachedItemObjId);

      
      if (!senderItem.isTradeable()) {
        return;
      }
      if (senderItem != null) {
        float qualityPriceRate;
        
        switch (senderItem.getItemTemplate().getItemQuality()) {
          
          case JUNK:
          case COMMON:
            qualityPriceRate = 0.02F;
            break;
          
          case RARE:
            qualityPriceRate = 0.03F;
            break;
          
          case LEGEND:
          case UNIQUE:
            qualityPriceRate = 0.04F;
            break;
          
          case MYTHIC:
          case EPIC:
            qualityPriceRate = 0.05F;
            break;
          
          default:
            qualityPriceRate = 0.02F;
            break;
        } 
        
        if (senderItem.getItemCount() == attachedItemCount) {
          
          ItemService.removeItemFromInventory(sender, senderItem, false);
          
          senderItem.setEquipped(false);
          senderItem.setEquipmentSlot(0);
          senderItem.setItemLocation(StorageType.MAILBOX.getId());
          
          attachedItem = senderItem;
          attachedItem.setOwnerId(recipientCommonData.getPlayerObjId());
          itemMailCommission = Math.round((float)(senderItem.getItemTemplate().getPrice() * attachedItem.getItemCount()) * qualityPriceRate);

        
        }
        else if (senderItem.getItemCount() > attachedItemCount) {
          
          attachedItem = ItemService.newItem(senderItem.getItemTemplate().getTemplateId(), attachedItemCount);
          
          senderItem.decreaseItemCount(attachedItemCount);
          PacketSendUtility.sendPacket(sender, (AionServerPacket)new SM_UPDATE_ITEM(senderItem));
          
          attachedItem.setEquipped(false);
          attachedItem.setEquipmentSlot(0);
          attachedItem.setItemLocation(StorageType.MAILBOX.getId());
          attachedItem.setOwnerId(recipientCommonData.getPlayerObjId());
          
          itemMailCommission = Math.round((float)(attachedItem.getItemTemplate().getPrice() * attachedItem.getItemCount()) * qualityPriceRate);
        } 
      } 
    } 


    
    if (attachedKinahCount > 0)
    {
      if (senderInventory.getKinahItem().getItemCount() - attachedKinahCount >= 0L) {
        
        finalAttachedKinahCount = attachedKinahCount;
        kinahMailCommission = Math.round(attachedKinahCount * 0.01F);
      } 
    }
    
    Timestamp time = new Timestamp(Calendar.getInstance().getTimeInMillis());
    
    Letter newLetter = new Letter(IDFactory.getInstance().nextId(), recipientCommonData.getPlayerObjId(), attachedItem, finalAttachedKinahCount, title, message, sender.getName(), time, true, express);

    
    if (!((MailDAO)DAOManager.getDAO(MailDAO.class)).storeLetter(time, newLetter)) {
      return;
    }


    
    if (!ItemService.decreaseKinah(sender, finalAttachedKinahCount)) {
      
      log.warn("[AUDIT]Mail kinah exploit: " + sender.getObjectId());
      
      return;
    } 
    if (attachedItem != null && 
      !((InventoryDAO)DAOManager.getDAO(InventoryDAO.class)).store(attachedItem)) {
      return;
    }
    int finalMailCommission = 10 + kinahMailCommission + itemMailCommission;
    
    if (senderInventory.getKinahItem().getItemCount() > finalMailCommission) {
      ItemService.decreaseKinah(sender, finalMailCommission);
    } else {
      
      log.warn("[AUDIT]Mail kinah exploit: " + sender.getObjectId());

      
      return;
    } 

    
    if (onlineRecipient != null) {
      
      Mailbox recipientMailbox = onlineRecipient.getMailbox();
      recipientMailbox.putLetterToMailbox(newLetter);
      
      PacketSendUtility.sendPacket(onlineRecipient, (AionServerPacket)new SM_MAIL_SERVICE(onlineRecipient, onlineRecipient.getMailbox().getLetters()));
      
      PacketSendUtility.sendPacket(onlineRecipient, (AionServerPacket)new SM_MAIL_SERVICE(false, false));
      PacketSendUtility.sendPacket(onlineRecipient, (AionServerPacket)new SM_MAIL_SERVICE(true, true));
    } 
    
    PacketSendUtility.sendPacket(sender, (AionServerPacket)new SM_MAIL_SERVICE(MailMessage.MAIL_SEND_SECCESS));



    
    if (!recipientCommonData.isOnline()) {
      
      recipientCommonData.setMailboxLetters(recipientCommonData.getMailboxLetters() + 1);
      ((MailDAO)DAOManager.getDAO(MailDAO.class)).updateOfflineMailCounter(recipientCommonData);
    } 
  }








  
  public void readMail(Player player, int letterId) {
    Letter letter = player.getMailbox().getLetterFromMailbox(letterId);
    if (letter == null) {
      
      log.warn("Cannot read mail " + player.getObjectId() + " " + letterId);
      
      return;
    } 
    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_MAIL_SERVICE(player, letter, letter.getTimeStamp().getTime()));
    letter.setReadLetter();
  }






  
  public void getAttachments(Player player, int letterId, int attachmentType) {
    Item attachedItem, inventoryItem;
    Letter letter = player.getMailbox().getLetterFromMailbox(letterId);
    
    if (letter == null) {
      return;
    }
    switch (attachmentType) {

      
      case 0:
        attachedItem = letter.getAttachedItem();
        if (attachedItem == null)
          return; 
        if (player.getInventory().isFull()) {
          
          PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.MSG_FULL_INVENTORY);
          return;
        } 
        inventoryItem = player.getInventory().putToBag(attachedItem);
        PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_ADD_ITEMS(Collections.singletonList(inventoryItem)));
        PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_MAIL_SERVICE(letterId, attachmentType));
        letter.removeAttachedItem();
        break;

      
      case 1:
        ItemService.increaseKinah(player, letter.getAttachedKinah());
        PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_MAIL_SERVICE(letterId, attachmentType));
        letter.removeAttachedKinah();
        break;
    } 
  }







  
  public void deleteMail(Player player, int letterId) {
    Mailbox mailbox = player.getMailbox();
    
    mailbox.removeLetter(letterId);
    ((MailDAO)DAOManager.getDAO(MailDAO.class)).deleteLetter(letterId);
    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_MAIL_SERVICE(letterId));
  }










  
  private boolean validateMailSendPrice(Player sender, int attachedKinahCount, int attachedItemObjId, int attachedItemCount) {
    int itemMailCommission = 0;
    int kinahMailCommission = Math.round(attachedKinahCount * 0.01F);
    if (attachedItemObjId != 0) {
      float qualityPriceRate;
      Item senderItem = sender.getInventory().getItemByObjId(attachedItemObjId);
      
      switch (senderItem.getItemTemplate().getItemQuality()) {
        
        case JUNK:
        case COMMON:
          qualityPriceRate = 0.02F;
          break;
        
        case RARE:
          qualityPriceRate = 0.03F;
          break;
        
        case LEGEND:
        case UNIQUE:
          qualityPriceRate = 0.04F;
          break;
        
        case MYTHIC:
        case EPIC:
          qualityPriceRate = 0.05F;
          break;
        
        default:
          qualityPriceRate = 0.02F;
          break;
      } 
      
      itemMailCommission = Math.round((senderItem.getItemTemplate().getPrice() * attachedItemCount) * qualityPriceRate);
    } 

    
    int finalMailPrice = 10 + itemMailCommission + kinahMailCommission;
    
    if (sender.getInventory().getKinahItem().getItemCount() >= finalMailPrice) {
      return true;
    }
    return false;
  }





  
  public void onPlayerLogin(Player player) {
    ThreadPoolManager.getInstance().schedule(new MailLoadTask(player), 5000L);
  }


  
  private class MailLoadTask
    implements Runnable
  {
    private Player player;


    
    private MailLoadTask(Player player) {
      this.player = player;
    }


    
    public void run() {
      this.player.setMailbox(((MailDAO)DAOManager.getDAO(MailDAO.class)).loadPlayerMailbox(this.player));
      PacketSendUtility.sendPacket(this.player, (AionServerPacket)new SM_MAIL_SERVICE(this.player, this.player.getMailbox().getLetters()));
      if (this.player.getMailbox().haveUnread()) {
        PacketSendUtility.sendPacket(this.player, (AionServerPacket)new SM_MAIL_SERVICE(true, true));
      }
    }
  }
  
  private static class SingletonHolder
  {
    protected static final MailService instance = new MailService();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\MailService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
