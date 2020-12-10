package com.aionemu.gameserver.taskmanager.tasks;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.configs.main.OptionsConfig;
import com.aionemu.gameserver.dao.InventoryDAO;
import com.aionemu.gameserver.dao.ItemStoneListDAO;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.taskmanager.AbstractFIFOPeriodicTaskManager;





















public class ItemUpdater
  extends AbstractFIFOPeriodicTaskManager<Item>
{
  private static final class SingletonHolder
  {
    private static final ItemUpdater INSTANCE = new ItemUpdater();
  }

  
  public static ItemUpdater getInstance() {
    return SingletonHolder.INSTANCE;
  }




  
  public ItemUpdater() {
    super(OptionsConfig.ITEMS * 1000);
  }





  
  protected void callTask(Item item) {
    ((InventoryDAO)DAOManager.getDAO(InventoryDAO.class)).store(item);
    ((ItemStoneListDAO)DAOManager.getDAO(ItemStoneListDAO.class)).store(item.getGodStone());
    ((ItemStoneListDAO)DAOManager.getDAO(ItemStoneListDAO.class)).store(item.getItemStones());
  }





  
  protected String getCalledMethodName() {
    return "ItemUpdater()";
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\taskmanager\tasks\ItemUpdater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
