package com.aionemu.gameserver.model.gameobjects.player;

import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.utils.stats.AbyssRankEnum;
import java.util.Calendar;








































public class AbyssRank
{
  private int dailyAP;
  private int weeklyAP;
  private int ap;
  private AbyssRankEnum rank;
  private int topRanking;
  private PersistentState persistentState;
  private int dailyKill;
  private int weeklyKill;
  private int allKill;
  private int maxRank;
  private int lastKill;
  private int lastAP;
  private long lastUpdate;
  
  public AbyssRank(int dailyAP, int weeklyAP, int ap, int rank, int topRanking, int dailyKill, int weeklyKill, int allKill, int maxRank, int lastKill, int lastAP, long lastUpdate) {
    this.dailyAP = dailyAP;
    this.weeklyAP = weeklyAP;
    this.ap = ap;
    this.rank = AbyssRankEnum.getRankById(rank);
    this.topRanking = topRanking;
    this.dailyKill = dailyKill;
    this.weeklyKill = weeklyKill;
    this.allKill = allKill;
    this.maxRank = maxRank;
    this.lastKill = lastKill;
    this.lastAP = lastAP;
    this.lastUpdate = lastUpdate;
    
    doUpdate();
  }






  
  public void addAp(int ap) {
    this.dailyAP += ap;
    if (this.dailyAP < 0) {
      this.dailyAP = 0;
    }
    this.weeklyAP += ap;
    if (this.weeklyAP < 0) {
      this.weeklyAP = 0;
    }
    setAp(this.ap + ap);
  }




  
  public int getDailyAP() {
    return this.dailyAP;
  }




  
  public int getWeeklyAP() {
    return this.weeklyAP;
  }




  
  public int getAp() {
    return this.ap;
  }






  
  public void setAp(int ap) {
    if (ap < 0)
      ap = 0; 
    this.ap = ap;
    
    AbyssRankEnum newRank = AbyssRankEnum.getRankForAp(this.ap);
    if (newRank != this.rank) {
      setRank(newRank);
    }
    setPersistentState(PersistentState.UPDATE_REQUIRED);
  }




  
  public AbyssRankEnum getRank() {
    return this.rank;
  }




  
  public int getTopRanking() {
    return this.topRanking;
  }




  
  public void setTopRanking(int topRanking) {
    this.topRanking = topRanking;
  }




  
  public int getDailyKill() {
    return this.dailyKill;
  }




  
  public int getWeeklyKill() {
    return this.weeklyKill;
  }




  
  public int getAllKill() {
    return this.allKill;
  }




  
  public void setAllKill() {
    this.dailyKill++;
    this.weeklyKill++;
    this.allKill++;
  }




  
  public int getMaxRank() {
    return this.maxRank;
  }




  
  public int getLastKill() {
    return this.lastKill;
  }




  
  public int getLastAP() {
    return this.lastAP;
  }




  
  public void setRank(AbyssRankEnum rank) {
    if (rank.getId() > this.maxRank) {
      this.maxRank = rank.getId();
    }
    this.rank = rank;

    
    setPersistentState(PersistentState.UPDATE_REQUIRED);
  }




  
  public PersistentState getPersistentState() {
    return this.persistentState;
  }




  
  public void setPersistentState(PersistentState persistentState) {
    switch (persistentState) {
      
      case UPDATE_REQUIRED:
        if (this.persistentState == PersistentState.NEW)
          return;  break;
    } 
    this.persistentState = persistentState;
  }





  
  public long getLastUpdate() {
    return this.lastUpdate;
  }




  
  public void doUpdate() {
    boolean needUpdate = false;
    Calendar lastCal = Calendar.getInstance();
    lastCal.setTimeInMillis(this.lastUpdate);
    
    Calendar curCal = Calendar.getInstance();
    curCal.setTimeInMillis(System.currentTimeMillis());

    
    if (lastCal.get(5) != curCal.get(5) || lastCal.get(2) != curCal.get(2) || lastCal.get(1) != curCal.get(1)) {


      
      this.dailyAP = 0;
      this.dailyKill = 0;
      needUpdate = true;
    } 

    
    if (lastCal.get(3) != curCal.get(3) || lastCal.get(1) != curCal.get(1)) {

      
      this.lastKill = this.weeklyKill;
      this.lastAP = this.weeklyAP;
      this.weeklyKill = 0;
      this.weeklyAP = 0;
      needUpdate = true;
    } 

    
    this.lastUpdate = System.currentTimeMillis();
    
    if (needUpdate)
      setPersistentState(PersistentState.UPDATE_REQUIRED); 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\AbyssRank.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
