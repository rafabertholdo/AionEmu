package com.aionemu.gameserver.model.legion;

import com.aionemu.gameserver.model.gameobjects.PersistentState;





















public class LegionEmblem
{
  private int emblemId = 0;
  private int color_r = 0;
  private int color_g = 0;
  private int color_b = 0;
  
  private boolean defaultEmblem = true;
  private PersistentState persistentState;
  private boolean isUploading = false;
  private int uploadSize = 0;
  private int uploadedSize = 0;
  
  private byte[] uploadData;
  
  public LegionEmblem() {
    setPersistentState(PersistentState.NEW);
  }











  
  public void setEmblem(int emblemId, int color_r, int color_g, int color_b) {
    this.emblemId = emblemId;
    this.color_r = color_r;
    this.color_g = color_g;
    this.color_b = color_b;
    setPersistentState(PersistentState.UPDATE_REQUIRED);
    this.defaultEmblem = false;
  }




  
  public int getEmblemId() {
    return this.emblemId;
  }




  
  public int getColor_r() {
    return this.color_r;
  }




  
  public int getColor_g() {
    return this.color_g;
  }




  
  public int getColor_b() {
    return this.color_b;
  }




  
  public boolean isDefaultEmblem() {
    return this.defaultEmblem;
  }





  
  public void setUploading(boolean isUploading) {
    this.isUploading = isUploading;
  }




  
  public boolean isUploading() {
    return this.isUploading;
  }





  
  public void setUploadSize(int emblemSize) {
    this.uploadSize = emblemSize;
  }




  
  public int getUploadSize() {
    return this.uploadSize;
  }





  
  public void addUploadData(byte[] data) {
    byte[] newData = new byte[this.uploadedSize];
    int i = 0;
    if (this.uploadData.length > 0)
    {
      for (byte dataByte : this.uploadData) {
        
        newData[i] = dataByte;
        i++;
      } 
    }
    for (byte dataByte : data) {
      
      newData[i] = dataByte;
      i++;
    } 
    this.uploadData = newData;
  }




  
  public byte[] getUploadData() {
    return this.uploadData;
  }





  
  public void addUploadedSize(int uploadedSize) {
    this.uploadedSize += uploadedSize;
  }




  
  public int getUploadedSize() {
    return this.uploadedSize;
  }




  
  public void resetUploadSettings() {
    this.isUploading = false;
    this.uploadedSize = 0;
  }





  
  public void setPersistentState(PersistentState persistentState) {
    switch (persistentState) {
      
      case UPDATE_REQUIRED:
        if (this.persistentState == PersistentState.NEW)
          return;  break;
    } 
    this.persistentState = persistentState;
  }





  
  public PersistentState getPersistentState() {
    return this.persistentState;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\legion\LegionEmblem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
