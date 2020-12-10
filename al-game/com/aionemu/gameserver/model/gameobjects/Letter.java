package com.aionemu.gameserver.model.gameobjects;

import java.sql.Timestamp;

































public class Letter
  extends AionObject
{
  private int recipientId;
  private Item attachedItem;
  private long attachedKinahCount;
  private String senderName;
  private String title;
  private String message;
  private boolean unread;
  private boolean express;
  private Timestamp timeStamp;
  private PersistentState persistentState;
  
  public Letter(int objId, int recipientId, Item attachedItem, long attachedKinahCount, String title, String message, String senderName, Timestamp timeStamp, boolean unread, boolean express) {
    super(Integer.valueOf(objId));
    
    this.recipientId = recipientId;
    this.attachedItem = attachedItem;
    this.attachedKinahCount = attachedKinahCount;
    this.title = title;
    this.message = message;
    this.senderName = senderName;
    this.timeStamp = timeStamp;
    this.unread = unread;
    this.express = express;
    this.persistentState = PersistentState.NEW;
  }



  
  public String getName() {
    return String.valueOf(this.attachedItem.getItemTemplate().getNameId());
  }

  
  public int getRecipientId() {
    return this.recipientId;
  }

  
  public Item getAttachedItem() {
    return this.attachedItem;
  }

  
  public long getAttachedKinah() {
    return this.attachedKinahCount;
  }

  
  public String getTitle() {
    return this.title;
  }

  
  public String getMessage() {
    return this.message;
  }

  
  public String getSenderName() {
    return this.senderName;
  }

  
  public boolean isUnread() {
    return this.unread;
  }

  
  public void setReadLetter() {
    this.unread = false;
    this.persistentState = PersistentState.UPDATE_REQUIRED;
  }

  
  public boolean isExpress() {
    return this.express;
  }

  
  public PersistentState getLetterPersistentState() {
    return this.persistentState;
  }

  
  public void removeAttachedItem() {
    this.attachedItem = null;
    this.persistentState = PersistentState.UPDATE_REQUIRED;
  }

  
  public void removeAttachedKinah() {
    this.attachedKinahCount = 0L;
    this.persistentState = PersistentState.UPDATE_REQUIRED;
  }

  
  public void delete() {
    this.persistentState = PersistentState.DELETED;
  }

  
  public void setPersistState(PersistentState state) {
    this.persistentState = state;
  }




  
  public Timestamp getTimeStamp() {
    return this.timeStamp;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\Letter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
