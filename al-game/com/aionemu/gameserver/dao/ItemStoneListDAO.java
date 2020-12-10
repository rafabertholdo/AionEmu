package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.items.GodStone;
import com.aionemu.gameserver.model.items.ManaStone;
import java.util.List;
import java.util.Set;







































public abstract class ItemStoneListDAO
  implements DAO
{
  public abstract void load(List<Item> paramList);
  
  public abstract void store(Set<ManaStone> paramSet);
  
  public abstract void save(Player paramPlayer);
  
  public abstract void store(GodStone paramGodStone);
  
  public String getClassName() {
    return ItemStoneListDAO.class.getName();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dao\ItemStoneListDAO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
