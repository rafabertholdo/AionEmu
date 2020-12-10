package com.aionemu.gameserver.model.gameobjects.stats;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
import com.aionemu.gameserver.services.LifeStatsRestoreService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.log4j.Logger;






















public abstract class CreatureLifeStats<T extends Creature>
{
  private static final Logger log = Logger.getLogger(CreatureLifeStats.class);
  
  protected int currentHp;
  
  protected int currentMp;
  
  protected boolean alreadyDead = false;
  
  protected Creature owner;
  private final ReentrantLock hpLock = new ReentrantLock();
  private final ReentrantLock mpLock = new ReentrantLock();

  
  protected Future<?> lifeRestoreTask;

  
  public CreatureLifeStats(Creature owner, int currentHp, int currentMp) {
    this.owner = owner;
    this.currentHp = currentHp;
    this.currentMp = currentMp;
  }




  
  public Creature getOwner() {
    return this.owner;
  }




  
  public int getCurrentHp() {
    return this.currentHp;
  }




  
  public int getCurrentMp() {
    return this.currentMp;
  }




  
  public int getMaxHp() {
    int maxHp = getOwner().getGameStats().getCurrentStat(StatEnum.MAXHP);
    if (maxHp == 0) {
      
      maxHp = 1;
      log.warn("CHECKPOINT: maxhp is 0 :" + getOwner().getGameStats());
    } 
    return maxHp;
  }




  
  public int getMaxMp() {
    return getOwner().getGameStats().getCurrentStat(StatEnum.MAXMP);
  }





  
  public boolean isAlreadyDead() {
    return this.alreadyDead;
  }







  
  public int reduceHp(int value, Creature attacker) {
    this.hpLock.lock();
    
    try {
      int newHp = this.currentHp - value;
      
      if (newHp < 0) {
        
        newHp = 0;
        if (!this.alreadyDead)
        {
          this.alreadyDead = true;
        }
      } 
      this.currentHp = newHp;
      
      onReduceHp();
      
      if (this.alreadyDead)
      {
        getOwner().getController().onDie(attacker);
      }
    }
    finally {
      
      this.hpLock.unlock();
    } 
    
    return this.currentHp;
  }






  
  public int reduceMp(int value) {
    this.mpLock.lock();
    
    try {
      int newMp = this.currentMp - value;
      
      if (newMp < 0) {
        newMp = 0;
      }
      this.currentMp = newMp;
    }
    finally {
      
      this.mpLock.unlock();
    } 
    
    onReduceMp();
    
    return this.currentMp;
  }


  
  protected void sendAttackStatusPacketUpdate(SM_ATTACK_STATUS.TYPE type, int value) {
    if (this.owner == null) {
      return;
    }

    
    PacketSendUtility.broadcastPacketAndReceive((VisibleObject)this.owner, (AionServerPacket)new SM_ATTACK_STATUS(this.owner, 0));
  }






  
  public int increaseHp(SM_ATTACK_STATUS.TYPE type, int value) {
    this.hpLock.lock();
    
    try {
      if (isAlreadyDead())
      {
        return 0;
      }
      int newHp = this.currentHp + value;
      if (newHp > getMaxHp())
      {
        newHp = getMaxHp();
      }
      if (this.currentHp != newHp)
      {
        this.currentHp = newHp;
        onIncreaseHp(type, value);
      }
    
    } finally {
      
      this.hpLock.unlock();
    } 
    return this.currentHp;
  }






  
  public int increaseMp(SM_ATTACK_STATUS.TYPE type, int value) {
    this.mpLock.lock();

    
    try {
      if (isAlreadyDead())
      {
        return 0;
      }
      int newMp = this.currentMp + value;
      if (newMp > getMaxMp())
      {
        newMp = getMaxMp();
      }
      if (this.currentMp != newMp)
      {
        this.currentMp = newMp;
        onIncreaseMp(type, value);
      }
    
    } finally {
      
      this.mpLock.unlock();
    } 
    
    return this.currentMp;
  }




  
  public void restoreHp() {
    increaseHp(SM_ATTACK_STATUS.TYPE.NATURAL_HP, getOwner().getGameStats().getCurrentStat(StatEnum.REGEN_HP));
  }




  
  public void restoreMp() {
    increaseMp(SM_ATTACK_STATUS.TYPE.NATURAL_MP, getOwner().getGameStats().getCurrentStat(StatEnum.REGEN_MP));
  }




  
  protected void triggerRestoreTask() {
    if (this.lifeRestoreTask == null && !this.alreadyDead)
    {
      this.lifeRestoreTask = LifeStatsRestoreService.getInstance().scheduleRestoreTask(this);
    }
  }




  
  public void cancelRestoreTask() {
    if (this.lifeRestoreTask != null && !this.lifeRestoreTask.isCancelled()) {
      
      this.lifeRestoreTask.cancel(false);
      this.lifeRestoreTask = null;
    } 
  }





  
  public boolean isFullyRestoredHpMp() {
    return (getMaxHp() == this.currentHp && getMaxMp() == this.currentMp);
  }





  
  public boolean isFullyRestoredHp() {
    return (getMaxHp() == this.currentHp);
  }





  
  public void synchronizeWithMaxStats() {
    int maxHp = getMaxHp();
    if (this.currentHp != maxHp)
      this.currentHp = maxHp; 
    int maxMp = getMaxMp();
    if (this.currentMp != maxMp) {
      this.currentMp = maxMp;
    }
  }






  
  public void updateCurrentStats() {
    int maxHp = getMaxHp();
    if (maxHp < this.currentHp) {
      this.currentHp = maxHp;
    }
    int maxMp = getMaxMp();
    if (maxMp < this.currentMp) {
      this.currentMp = maxMp;
    }
    if (!isFullyRestoredHpMp()) {
      triggerRestoreTask();
    }
  }




  
  public int getHpPercentage() {
    return (int)(100L * this.currentHp / getMaxHp());
  }





  
  public int getMpPercentage() {
    return 100 * this.currentMp / getMaxMp();
  }


  
  protected abstract void onIncreaseMp(SM_ATTACK_STATUS.TYPE paramTYPE, int paramInt);

  
  protected abstract void onReduceMp();

  
  protected abstract void onIncreaseHp(SM_ATTACK_STATUS.TYPE paramTYPE, int paramInt);

  
  protected abstract void onReduceHp();

  
  public int increaseFp(int value) {
    return 0;
  }




  
  public int getCurrentFp() {
    return 0;
  }




  
  public void cancelAllTasks() {
    cancelRestoreTask();
  }







  
  public void setCurrentHpPercent(int hpPercent) {
    this.hpLock.lock();
    
    try {
      int maxHp = getMaxHp();
      this.currentHp = (int)(maxHp * hpPercent / 100L);
      
      if (this.currentHp > 0) {
        this.alreadyDead = false;
      }
    } finally {
      
      this.hpLock.unlock();
    } 
  }




  
  public void setCurrentHp(int hp) {
    this.hpLock.lock();
    
    try {
      this.currentHp = hp;
      
      if (this.currentHp > 0) {
        this.alreadyDead = false;
      }
      if (this.currentHp < getMaxHp()) {
        onReduceHp();
      }
    } finally {
      
      this.hpLock.unlock();
    } 
  }

  
  public int setCurrentMp(int value) {
    this.mpLock.lock();
    
    try {
      int newMp = value;
      
      if (newMp < 0) {
        newMp = 0;
      }
      this.currentMp = newMp;
    }
    finally {
      
      this.mpLock.unlock();
    } 
    
    onReduceMp();
    
    return this.currentMp;
  }






  
  public void setCurrentMpPercent(int mpPercent) {
    this.mpLock.lock();
    
    try {
      int maxMp = getMaxMp();
      this.currentMp = maxMp * mpPercent / 100;
    }
    finally {
      
      this.mpLock.unlock();
    } 
  }






  
  public void triggerRestoreOnRevive() {
    triggerRestoreTask();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\stats\CreatureLifeStats.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
