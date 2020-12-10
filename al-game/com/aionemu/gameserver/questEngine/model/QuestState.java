package com.aionemu.gameserver.questEngine.model;

import com.aionemu.gameserver.model.gameobjects.PersistentState;






















public class QuestState
{
  private final int questId;
  private QuestVars questVars;
  private QuestStatus status;
  private int completeCount;
  private PersistentState persistentState;
  
  public QuestState(int questId) {
    this.questId = questId;
    this.status = QuestStatus.START;
    this.questVars = new QuestVars();
    this.completeCount = 0;
    this.persistentState = PersistentState.NEW;
  }

  
  public QuestState(int questId, QuestStatus status, int questVars, int completeCount) {
    this.questId = questId;
    this.status = status;
    this.questVars = new QuestVars(questVars);
    this.completeCount = completeCount;
    this.persistentState = PersistentState.NEW;
  }

  
  public QuestVars getQuestVars() {
    return this.questVars;
  }





  
  public void setQuestVarById(int id, int var) {
    this.questVars.setVarById(id, var);
    setPersistentState(PersistentState.UPDATE_REQUIRED);
  }





  
  public int getQuestVarById(int id) {
    return this.questVars.getVarById(id);
  }

  
  public void setQuestVar(int var) {
    this.questVars.setVar(var);
    setPersistentState(PersistentState.UPDATE_REQUIRED);
  }

  
  public QuestStatus getStatus() {
    return this.status;
  }

  
  public void setStatus(QuestStatus status) {
    this.status = status;
    setPersistentState(PersistentState.UPDATE_REQUIRED);
  }

  
  public int getQuestId() {
    return this.questId;
  }

  
  public void setCompliteCount(int completeCount) {
    this.completeCount = completeCount;
    setPersistentState(PersistentState.UPDATE_REQUIRED);
  }

  
  public int getCompliteCount() {
    return this.completeCount;
  }




  
  public PersistentState getPersistentState() {
    return this.persistentState;
  }




  
  public void setPersistentState(PersistentState persistentState) {
    switch (persistentState) {
      
      case DELETED:
        if (this.persistentState == PersistentState.NEW)
          throw new IllegalArgumentException("Cannot change state to DELETED from NEW"); 
      case UPDATE_REQUIRED:
        if (this.persistentState == PersistentState.NEW)
          return;  break;
    } 
    this.persistentState = persistentState;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\model\QuestState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
