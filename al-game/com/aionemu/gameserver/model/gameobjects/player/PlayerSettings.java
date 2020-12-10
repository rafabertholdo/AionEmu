package com.aionemu.gameserver.model.gameobjects.player;

import com.aionemu.gameserver.model.gameobjects.PersistentState;






















public class PlayerSettings
{
  private PersistentState persistentState;
  private byte[] uiSettings;
  private byte[] shortcuts;
  private int deny = 0;
  private int display = 0;


  
  public PlayerSettings() {}


  
  public PlayerSettings(byte[] uiSettings, byte[] shortcuts, int deny, int display) {
    this.uiSettings = uiSettings;
    this.shortcuts = shortcuts;
    this.deny = deny;
    this.display = display;
  }




  
  public PersistentState getPersistentState() {
    return this.persistentState;
  }




  
  public void setPersistentState(PersistentState persistentState) {
    this.persistentState = persistentState;
  }




  
  public byte[] getUiSettings() {
    return this.uiSettings;
  }




  
  public void setUiSettings(byte[] uiSettings) {
    this.uiSettings = uiSettings;
    this.persistentState = PersistentState.UPDATE_REQUIRED;
  }




  
  public byte[] getShortcuts() {
    return this.shortcuts;
  }




  
  public void setShortcuts(byte[] shortcuts) {
    this.shortcuts = shortcuts;
    this.persistentState = PersistentState.UPDATE_REQUIRED;
  }




  
  public int getDisplay() {
    return this.display;
  }




  
  public void setDisplay(int display) {
    this.display = display;
    this.persistentState = PersistentState.UPDATE_REQUIRED;
  }




  
  public int getDeny() {
    return this.deny;
  }




  
  public void setDeny(int deny) {
    this.deny = deny;
    this.persistentState = PersistentState.UPDATE_REQUIRED;
  }

  
  public boolean isInDeniedStatus(DeniedStatus deny) {
    int isDeniedStatus = this.deny & deny.getId();
    
    if (isDeniedStatus == deny.getId()) {
      return true;
    }
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\PlayerSettings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
