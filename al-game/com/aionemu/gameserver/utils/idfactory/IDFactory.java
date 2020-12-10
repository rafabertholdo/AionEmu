package com.aionemu.gameserver.utils.idfactory;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.dao.InventoryDAO;
import com.aionemu.gameserver.dao.LegionDAO;
import com.aionemu.gameserver.dao.MailDAO;
import com.aionemu.gameserver.dao.PlayerDAO;
import java.util.BitSet;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.log4j.Logger;


























public class IDFactory
{
  private static final Logger log = Logger.getLogger(IDFactory.class);



  
  private final BitSet idList;



  
  private final ReentrantLock lock;



  
  private volatile int nextMinId = 0;









  
  private IDFactory() {
    this.idList = new BitSet();
    this.lock = new ReentrantLock();
    lockIds(new int[] { 0 });

    
    lockIds(((PlayerDAO)DAOManager.getDAO(PlayerDAO.class)).getUsedIDs());
    lockIds(((InventoryDAO)DAOManager.getDAO(InventoryDAO.class)).getUsedIDs());
    lockIds(((LegionDAO)DAOManager.getDAO(LegionDAO.class)).getUsedIDs());
    lockIds(((MailDAO)DAOManager.getDAO(MailDAO.class)).getUsedIDs());
    log.info("IDFactory: " + getUsedCount() + " id's used.");
  }

  
  public static final IDFactory getInstance() {
    return SingletonHolder.instance;
  }

  
  public int nextId() {
    try {
      int id;
      this.lock.lock();

      
      if (this.nextMinId == Integer.MIN_VALUE) {


        
        id = Integer.MIN_VALUE;
      }
      else {
        
        id = this.idList.nextClearBit(this.nextMinId);
      } 


      
      if (id == Integer.MIN_VALUE)
      {
        throw new IDFactoryError("All id's are used, please clear your database");
      }
      this.idList.set(id);

      
      this.nextMinId = id + 1;
      return id;
    }
    finally {
      
      this.lock.unlock();
    } 
  }










  
  private void lockIds(int... ids) {
    try {
      this.lock.lock();
      for (int id : ids)
      {
        boolean status = this.idList.get(id);
        if (status)
        {
          throw new IDFactoryError("ID " + id + " is already taken, fatal error!!!");
        }
        this.idList.set(id);
      }
    
    } finally {
      
      this.lock.unlock();
    } 
  }










  
  public void lockIds(Iterable<Integer> ids) {
    try {
      this.lock.lock();
      for (Iterator<Integer> i$ = ids.iterator(); i$.hasNext(); ) { int id = ((Integer)i$.next()).intValue();
        
        boolean status = this.idList.get(id);
        if (status)
        {
          throw new IDFactoryError("ID " + id + " is already taken, fatal error!!!");
        }
        this.idList.set(id); }

    
    } finally {
      
      this.lock.unlock();
    } 
  }










  
  public void releaseId(int id) {
    try {
      this.lock.lock();
      boolean status = this.idList.get(id);
      if (!status)
      {
        throw new IDFactoryError("ID " + id + " is not taken, can't release it.");
      }
      this.idList.clear(id);
      if (id < this.nextMinId || this.nextMinId == Integer.MIN_VALUE)
      {
        this.nextMinId = id;
      }
    }
    finally {
      
      this.lock.unlock();
    } 
  }







  
  public int getUsedCount() {
    try {
      this.lock.lock();
      return this.idList.cardinality();
    }
    finally {
      
      this.lock.unlock();
    } 
  }

  
  private static class SingletonHolder
  {
    protected static final IDFactory instance = new IDFactory();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\idfactory\IDFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
