package com.aionemu.gameserver.dao;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Equipment;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.Storage;
import com.aionemu.gameserver.model.gameobjects.player.StorageType;
import java.util.List;












































public abstract class InventoryDAO
  implements IDFactoryAwareDAO
{
  public abstract Storage loadStorage(Player paramPlayer, int paramInt, StorageType paramStorageType);
  
  public abstract Equipment loadEquipment(Player paramPlayer);
  
  public abstract List<Item> loadEquipment(int paramInt);
  
  public abstract boolean store(List<Item> paramList);
  
  public abstract boolean store(Item paramItem);
  
  public abstract boolean deletePlayerItems(int paramInt);
  
  public String getClassName() {
    return InventoryDAO.class.getName();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dao\InventoryDAO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
