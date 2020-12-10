package com.aionemu.gameserver.model;

import javax.xml.bind.annotation.XmlEnum;


























@XmlEnum
public enum Gender
{
  MALE(0),



  
  FEMALE(1);





  
  private int genderId;





  
  Gender(int genderId) {
    this.genderId = genderId;
  }






  
  public int getGenderId() {
    return this.genderId;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\Gender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
