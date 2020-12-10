package com.aionemu.gameserver.model.gameobjects.player;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.templates.TitleTemplate;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TITLE_LIST;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.Collection;
import javolution.util.FastMap;



























public class TitleList
{
  private FastMap<Integer, Title> titles = new FastMap();
  private Player owner = null;


  
  public void setOwner(Player owner) {
    this.owner = owner;
  }

  
  public Player getOwner() {
    return this.owner;
  }

  
  public boolean addTitle(int titleId) {
    TitleTemplate tt = DataManager.TITLE_DATA.getTitleTemplate(titleId);
    if (tt == null)
    {
      throw new IllegalArgumentException("Invalid title id " + titleId);
    }
    if (this.owner != null)
    {
      if (this.owner.getCommonData().getRace().getRaceId() != tt.getRace())
      {
        return false;
      }
    }
    if (!this.titles.containsKey(Integer.valueOf(titleId))) {
      
      this.titles.put(Integer.valueOf(titleId), new Title(tt));
    }
    else {
      
      return false;
    } 
    
    if (this.owner != null)
    {
      PacketSendUtility.sendPacket(this.owner, (AionServerPacket)new SM_TITLE_LIST(this.owner));
    }
    return true;
  }

  
  public int size() {
    return this.titles.size();
  }

  
  public Collection<Title> getTitles() {
    return this.titles.values();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\TitleList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
