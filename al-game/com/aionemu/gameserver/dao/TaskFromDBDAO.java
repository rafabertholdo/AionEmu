package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.tasks.TaskFromDB;
import java.util.ArrayList;

public abstract class TaskFromDBDAO implements DAO {
  public abstract ArrayList<TaskFromDB> getAllTasks();

  public abstract void setLastActivation(int paramInt);

  public final String getClassName() {
    return TaskFromDBDAO.class.getName();
  }
}
