package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.tasks.TaskFromDB;
import java.util.ArrayList;




































public abstract class TaskFromDBDAO
  implements DAO
{
  public abstract ArrayList<TaskFromDB> getAllTasks();
  
  public abstract void setLastActivation(int paramInt);
  
  public final String getClassName() {
    return TaskFromDBDAO.class.getName();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dao\TaskFromDBDAO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
