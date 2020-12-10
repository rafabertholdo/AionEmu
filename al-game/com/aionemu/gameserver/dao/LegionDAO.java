package com.aionemu.gameserver.dao;

import com.aionemu.gameserver.model.legion.Legion;
import com.aionemu.gameserver.model.legion.LegionEmblem;
import com.aionemu.gameserver.model.legion.LegionHistory;
import com.aionemu.gameserver.model.legion.LegionWarehouse;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.TreeMap;

















































































public abstract class LegionDAO
  implements IDFactoryAwareDAO
{
  public abstract boolean isNameUsed(String paramString);
  
  public abstract boolean saveNewLegion(Legion paramLegion);
  
  public abstract void storeLegion(Legion paramLegion);
  
  public abstract Legion loadLegion(String paramString);
  
  public abstract Legion loadLegion(int paramInt);
  
  public abstract void deleteLegion(int paramInt);
  
  public abstract TreeMap<Timestamp, String> loadAnnouncementList(int paramInt);
  
  public abstract boolean saveNewAnnouncement(int paramInt, Timestamp paramTimestamp, String paramString);
  
  public final String getClassName() {
    return LegionDAO.class.getName();
  }
  
  public abstract void storeLegionEmblem(int paramInt, LegionEmblem paramLegionEmblem);
  
  public abstract void removeAnnouncement(int paramInt, Timestamp paramTimestamp);
  
  public abstract LegionEmblem loadLegionEmblem(int paramInt);
  
  public abstract LegionWarehouse loadLegionStorage(Legion paramLegion);
  
  public abstract HashMap<Integer, Integer> loadLegionRanking();
  
  public abstract void loadLegionHistory(Legion paramLegion);
  
  public abstract boolean saveNewLegionHistory(int paramInt, LegionHistory paramLegionHistory);
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dao\LegionDAO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
