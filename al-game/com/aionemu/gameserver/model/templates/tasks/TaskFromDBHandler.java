/*    */ package com.aionemu.gameserver.model.templates.tasks;
/*    */ 
/*    */ import com.aionemu.commons.database.dao.DAOManager;
/*    */ import com.aionemu.gameserver.dao.TaskFromDBDAO;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class TaskFromDBHandler
/*    */   implements Runnable
/*    */ {
/*    */   protected int id;
/*    */   protected String[] params;
/*    */   
/*    */   public void setId(int id) {
/* 38 */     this.id = id;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setParam(String[] params) {
/* 48 */     this.params = params;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract String getTaskName();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract boolean isValid();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void setLastActivation() {
/* 71 */     ((TaskFromDBDAO)DAOManager.getDAO(TaskFromDBDAO.class)).setLastActivation(this.id);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\tasks\TaskFromDBHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */