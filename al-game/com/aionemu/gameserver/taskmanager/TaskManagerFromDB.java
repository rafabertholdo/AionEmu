package com.aionemu.gameserver.taskmanager;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.dao.TaskFromDBDAO;
import com.aionemu.gameserver.model.tasks.TaskFromDB;
import com.aionemu.gameserver.model.templates.tasks.TaskFromDBHandler;
import com.aionemu.gameserver.taskmanager.tasks.RestartTask;
import com.aionemu.gameserver.taskmanager.tasks.ShutdownTask;
import com.aionemu.gameserver.taskmanager.tasks.UpdateAbyssRank;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import org.apache.log4j.Logger;






























public class TaskManagerFromDB
{
  private static final Logger log = Logger.getLogger(TaskManagerFromDB.class);
  
  private ArrayList<TaskFromDB> tasksList;
  
  private HashMap<String, TaskFromDBHandler> handlers;
  
  public TaskManagerFromDB() {
    this.handlers = new HashMap<String, TaskFromDBHandler>();
    
    this.tasksList = getDAO().getAllTasks();
    log.info("Loaded " + this.tasksList.size() + " task" + ((this.tasksList.size() > 1) ? "s" : "") + " from the database");
    addTasks();
    
    registerHandlers();
    registerTasks();
  }




  
  private void addTasks() {
    int maxId = (this.tasksList.size() > 0) ? ((TaskFromDB)this.tasksList.get(this.tasksList.size() - 1)).getId() : 0;
    
    this.tasksList.add(new TaskFromDB(maxId + 1, "update_abyss_rank", "FIXED_IN_TIME", new Timestamp(System.currentTimeMillis()), "00:00:00", 0, null));
  }




  
  private void registerHandlers() {
    registerNewTask((TaskFromDBHandler)new ShutdownTask());
    registerNewTask((TaskFromDBHandler)new RestartTask());
    registerNewTask((TaskFromDBHandler)new UpdateAbyssRank());
  }






  
  private void registerNewTask(TaskFromDBHandler task) {
    if (this.handlers.get(task.getTaskName()) != null) {
      log.error("Can't override a task with name : " + task.getTaskName());
    }
    this.handlers.put(task.getTaskName(), task);
  }





  
  private void registerTasks() {
    for (TaskFromDB task : this.tasksList) {

      
      if (this.handlers.get(task.getName()) != null) {
        
        Class<? extends TaskFromDBHandler> tmpClass = (Class)((TaskFromDBHandler)this.handlers.get(task.getName())).getClass();
        TaskFromDBHandler currentTask = null;


        
        try {
          currentTask = tmpClass.newInstance();
        }
        catch (InstantiationException e) {
          
          log.error(e.getMessage(), e);
        }
        catch (IllegalAccessException e) {
          
          log.error(e.getMessage(), e);
        } 

        
        currentTask.setId(task.getId());
        currentTask.setParam(task.getParams());
        
        if (!currentTask.isValid()) {
          
          log.error("Invalid parameter for task ID: " + task.getId());
          
          continue;
        } 
        if (task.getType().equals("FIXED_IN_TIME")) {
          
          runFixedInTimeTask(currentTask, task);
          continue;
        } 
        log.error("Unknow task's type for " + task.getType());
        continue;
      } 
      log.error("Unknow task's name with ID : " + task.getName());
    } 
  }






  
  private void runFixedInTimeTask(TaskFromDBHandler handler, TaskFromDB dbTask) {
    String[] time = dbTask.getStartTime().split(":");
    int hour = Integer.parseInt(time[0]);
    int minute = Integer.parseInt(time[1]);
    int second = Integer.parseInt(time[2]);
    
    Calendar calendar = Calendar.getInstance();
    calendar.set(11, hour);
    calendar.set(12, minute);
    calendar.set(13, second);
    
    long delay = calendar.getTimeInMillis() - System.currentTimeMillis();
    
    if (delay < 0L) {
      delay += 86400000L;
    }
    ThreadPoolManager.getInstance().scheduleAtFixedRate((Runnable)handler, delay, 86400000L);
  }






  
  private static TaskFromDBDAO getDAO() {
    return (TaskFromDBDAO)DAOManager.getDAO(TaskFromDBDAO.class);
  }






  
  public static final TaskManagerFromDB getInstance() {
    return SingletonHolder.instance;
  }



  
  private static class SingletonHolder
  {
    protected static final TaskManagerFromDB instance = new TaskManagerFromDB();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\taskmanager\TaskManagerFromDB.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
