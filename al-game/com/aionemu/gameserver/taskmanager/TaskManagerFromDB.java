/*     */ package com.aionemu.gameserver.taskmanager;
/*     */ 
/*     */ import com.aionemu.commons.database.dao.DAOManager;
/*     */ import com.aionemu.gameserver.dao.TaskFromDBDAO;
/*     */ import com.aionemu.gameserver.model.tasks.TaskFromDB;
/*     */ import com.aionemu.gameserver.model.templates.tasks.TaskFromDBHandler;
/*     */ import com.aionemu.gameserver.taskmanager.tasks.RestartTask;
/*     */ import com.aionemu.gameserver.taskmanager.tasks.ShutdownTask;
/*     */ import com.aionemu.gameserver.taskmanager.tasks.UpdateAbyssRank;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.HashMap;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TaskManagerFromDB
/*     */ {
/*  48 */   private static final Logger log = Logger.getLogger(TaskManagerFromDB.class);
/*     */   
/*     */   private ArrayList<TaskFromDB> tasksList;
/*     */   
/*     */   private HashMap<String, TaskFromDBHandler> handlers;
/*     */   
/*     */   public TaskManagerFromDB() {
/*  55 */     this.handlers = new HashMap<String, TaskFromDBHandler>();
/*     */     
/*  57 */     this.tasksList = getDAO().getAllTasks();
/*  58 */     log.info("Loaded " + this.tasksList.size() + " task" + ((this.tasksList.size() > 1) ? "s" : "") + " from the database");
/*  59 */     addTasks();
/*     */     
/*  61 */     registerHandlers();
/*  62 */     registerTasks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addTasks() {
/*  70 */     int maxId = (this.tasksList.size() > 0) ? ((TaskFromDB)this.tasksList.get(this.tasksList.size() - 1)).getId() : 0;
/*     */     
/*  72 */     this.tasksList.add(new TaskFromDB(maxId + 1, "update_abyss_rank", "FIXED_IN_TIME", new Timestamp(System.currentTimeMillis()), "00:00:00", 0, null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void registerHandlers() {
/*  80 */     registerNewTask((TaskFromDBHandler)new ShutdownTask());
/*  81 */     registerNewTask((TaskFromDBHandler)new RestartTask());
/*  82 */     registerNewTask((TaskFromDBHandler)new UpdateAbyssRank());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void registerNewTask(TaskFromDBHandler task) {
/*  92 */     if (this.handlers.get(task.getTaskName()) != null) {
/*  93 */       log.error("Can't override a task with name : " + task.getTaskName());
/*     */     }
/*  95 */     this.handlers.put(task.getTaskName(), task);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void registerTasks() {
/* 104 */     for (TaskFromDB task : this.tasksList) {
/*     */ 
/*     */       
/* 107 */       if (this.handlers.get(task.getName()) != null) {
/*     */         
/* 109 */         Class<? extends TaskFromDBHandler> tmpClass = (Class)((TaskFromDBHandler)this.handlers.get(task.getName())).getClass();
/* 110 */         TaskFromDBHandler currentTask = null;
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 115 */           currentTask = tmpClass.newInstance();
/*     */         }
/* 117 */         catch (InstantiationException e) {
/*     */           
/* 119 */           log.error(e.getMessage(), e);
/*     */         }
/* 121 */         catch (IllegalAccessException e) {
/*     */           
/* 123 */           log.error(e.getMessage(), e);
/*     */         } 
/*     */ 
/*     */         
/* 127 */         currentTask.setId(task.getId());
/* 128 */         currentTask.setParam(task.getParams());
/*     */         
/* 130 */         if (!currentTask.isValid()) {
/*     */           
/* 132 */           log.error("Invalid parameter for task ID: " + task.getId());
/*     */           
/*     */           continue;
/*     */         } 
/* 136 */         if (task.getType().equals("FIXED_IN_TIME")) {
/*     */           
/* 138 */           runFixedInTimeTask(currentTask, task);
/*     */           continue;
/*     */         } 
/* 141 */         log.error("Unknow task's type for " + task.getType());
/*     */         continue;
/*     */       } 
/* 144 */       log.error("Unknow task's name with ID : " + task.getName());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void runFixedInTimeTask(TaskFromDBHandler handler, TaskFromDB dbTask) {
/* 155 */     String[] time = dbTask.getStartTime().split(":");
/* 156 */     int hour = Integer.parseInt(time[0]);
/* 157 */     int minute = Integer.parseInt(time[1]);
/* 158 */     int second = Integer.parseInt(time[2]);
/*     */     
/* 160 */     Calendar calendar = Calendar.getInstance();
/* 161 */     calendar.set(11, hour);
/* 162 */     calendar.set(12, minute);
/* 163 */     calendar.set(13, second);
/*     */     
/* 165 */     long delay = calendar.getTimeInMillis() - System.currentTimeMillis();
/*     */     
/* 167 */     if (delay < 0L) {
/* 168 */       delay += 86400000L;
/*     */     }
/* 170 */     ThreadPoolManager.getInstance().scheduleAtFixedRate((Runnable)handler, delay, 86400000L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static TaskFromDBDAO getDAO() {
/* 180 */     return (TaskFromDBDAO)DAOManager.getDAO(TaskFromDBDAO.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final TaskManagerFromDB getInstance() {
/* 190 */     return SingletonHolder.instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class SingletonHolder
/*     */   {
/* 198 */     protected static final TaskManagerFromDB instance = new TaskManagerFromDB();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\taskmanager\TaskManagerFromDB.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */