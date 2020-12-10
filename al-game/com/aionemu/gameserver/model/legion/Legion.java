package com.aionemu.gameserver.model.legion;

import com.aionemu.gameserver.configs.main.LegionConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.world.World;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
























public class Legion
{
  private static final int PERMISSION1_MIN = 96;
  private static final int PERMISSION2_MIN = 0;
  private static final int LEGIONAR_PERMISSION2_MAX = 8;
  private static final int CENTURION_PERMISSION1_MAX = 124;
  private static final int CENTURION_PERMISSION2_MAX = 14;
  private int legionId = 0;
  private String legionName = "";
  private int legionLevel = 1;
  private int legionRank = 0;
  private int contributionPoints = 0;
  private List<Integer> legionMembers = new ArrayList<Integer>();
  private static final int legionarPermission1 = 64;
  private int legionarPermission2 = 0;
  private int centurionPermission1 = 96;
  private int centurionPermission2 = 0;
  private int disbandTime;
  private TreeMap<Timestamp, String> announcementList = new TreeMap<Timestamp, String>();
  private LegionEmblem legionEmblem = new LegionEmblem();


  
  private LegionWarehouse legionWarehouse;

  
  private SortedSet<LegionHistory> legionHistory;


  
  public Legion(int legionId, String legionName) {
    this();
    this.legionId = legionId;
    this.legionName = legionName;
  }







  
  public Legion() {
    this.legionWarehouse = new LegionWarehouse(this);
    this.legionHistory = new TreeSet<LegionHistory>(new Comparator<LegionHistory>()
        {
          
          public int compare(LegionHistory o1, LegionHistory o2)
          {
            return (o1.getTime().getTime() < o2.getTime().getTime()) ? 1 : -1;
          }
        });
  }






  
  public void setLegionId(int legionId) {
    this.legionId = legionId;
  }




  
  public int getLegionId() {
    return this.legionId;
  }





  
  public void setLegionName(String legionName) {
    this.legionName = legionName;
  }




  
  public String getLegionName() {
    return this.legionName;
  }





  
  public void setLegionMembers(ArrayList<Integer> legionMembers) {
    this.legionMembers = legionMembers;
  }




  
  public List<Integer> getLegionMembers() {
    return this.legionMembers;
  }




  
  public ArrayList<Player> getOnlineLegionMembers() {
    ArrayList<Player> onlineLegionMembers = new ArrayList<Player>();
    for (Iterator<Integer> i$ = this.legionMembers.iterator(); i$.hasNext(); ) { int legionMemberObjId = ((Integer)i$.next()).intValue();
      
      Player onlineLegionMember = World.getInstance().findPlayer(legionMemberObjId);
      if (onlineLegionMember != null)
        onlineLegionMembers.add(onlineLegionMember);  }
    
    return onlineLegionMembers;
  }






  
  public boolean addLegionMember(int playerObjId) {
    if (canAddMember()) {
      
      this.legionMembers.add(Integer.valueOf(playerObjId));
      return true;
    } 
    return false;
  }






  
  public void deleteLegionMember(int playerObjId) {
    this.legionMembers.remove(new Integer(playerObjId));
  }









  
  public boolean setLegionPermissions(int legionarPermission2, int centurionPermission1, int centurionPermission2) {
    if (checkPermissions(legionarPermission2, centurionPermission1, centurionPermission2)) {
      
      this.legionarPermission2 = legionarPermission2;
      this.centurionPermission1 = centurionPermission1;
      this.centurionPermission2 = centurionPermission2;
      return true;
    } 
    return false;
  }






  
  private boolean checkPermissions(int legionarPermission2, int centurionPermission1, int centurionPermission2) {
    if (legionarPermission2 < 0 || legionarPermission2 > 8)
      return false; 
    if (centurionPermission1 < 96 || centurionPermission1 > 124)
      return false; 
    if (centurionPermission2 < 0 || centurionPermission2 > 14)
      return false; 
    return true;
  }




  
  public int getLegionarPermission1() {
    return 64;
  }




  
  public int getLegionarPermission2() {
    return this.legionarPermission2;
  }




  
  public int getCenturionPermission1() {
    return this.centurionPermission1;
  }




  
  public int getCenturionPermission2() {
    return this.centurionPermission2;
  }




  
  public int getLegionLevel() {
    return this.legionLevel;
  }




  
  public void setLegionLevel(int legionLevel) {
    this.legionLevel = legionLevel;
  }





  
  public void setLegionRank(int legionRank) {
    this.legionRank = legionRank;
  }




  
  public int getLegionRank() {
    return this.legionRank;
  }





  
  public void addContributionPoints(int contributionPoints) {
    this.contributionPoints += contributionPoints;
  }




  
  public void setContributionPoints(int contributionPoints) {
    this.contributionPoints = contributionPoints;
  }




  
  public int getContributionPoints() {
    return this.contributionPoints;
  }






  
  public boolean hasRequiredMembers() {
    switch (getLegionLevel()) {
      
      case 1:
        if (getLegionMembers().size() >= LegionConfig.LEGION_LEVEL2_REQUIRED_MEMBERS)
          return true; 
        break;
      case 2:
        if (getLegionMembers().size() >= LegionConfig.LEGION_LEVEL3_REQUIRED_MEMBERS)
          return true; 
        break;
      case 3:
        if (getLegionMembers().size() >= LegionConfig.LEGION_LEVEL4_REQUIRED_MEMBERS)
          return true; 
        break;
      case 4:
        if (getLegionMembers().size() >= LegionConfig.LEGION_LEVEL5_REQUIRED_MEMBERS)
          return true; 
        break;
    } 
    return false;
  }






  
  public int getKinahPrice() {
    switch (getLegionLevel()) {
      
      case 1:
        return LegionConfig.LEGION_LEVEL2_REQUIRED_KINAH;
      case 2:
        return LegionConfig.LEGION_LEVEL3_REQUIRED_KINAH;
      case 3:
        return LegionConfig.LEGION_LEVEL4_REQUIRED_KINAH;
      case 4:
        return LegionConfig.LEGION_LEVEL5_REQUIRED_KINAH;
    } 
    return 0;
  }






  
  public int getContributionPrice() {
    switch (getLegionLevel()) {
      
      case 1:
        return LegionConfig.LEGION_LEVEL2_REQUIRED_CONTRIBUTION;
      case 2:
        return LegionConfig.LEGION_LEVEL3_REQUIRED_CONTRIBUTION;
      case 3:
        return LegionConfig.LEGION_LEVEL4_REQUIRED_CONTRIBUTION;
      case 4:
        return LegionConfig.LEGION_LEVEL5_REQUIRED_CONTRIBUTION;
    } 
    return 0;
  }






  
  private boolean canAddMember() {
    switch (getLegionLevel()) {
      
      case 1:
        if (getLegionMembers().size() < LegionConfig.LEGION_LEVEL1_MAX_MEMBERS)
          return true; 
        break;
      case 2:
        if (getLegionMembers().size() < LegionConfig.LEGION_LEVEL2_MAX_MEMBERS)
          return true; 
        break;
      case 3:
        if (getLegionMembers().size() < LegionConfig.LEGION_LEVEL3_MAX_MEMBERS)
          return true; 
        break;
      case 4:
        if (getLegionMembers().size() < LegionConfig.LEGION_LEVEL4_MAX_MEMBERS)
          return true; 
        break;
      case 5:
        if (getLegionMembers().size() < LegionConfig.LEGION_LEVEL5_MAX_MEMBERS)
          return true; 
        break;
    } 
    return false;
  }





  
  public void setAnnouncementList(TreeMap<Timestamp, String> announcementList) {
    this.announcementList = announcementList;
  }




  
  public void addAnnouncementToList(Timestamp unixTime, String announcement) {
    this.announcementList.put(unixTime, announcement);
  }




  
  public void removeFirstEntry() {
    this.announcementList.remove(this.announcementList.firstEntry().getKey());
  }




  
  public TreeMap<Timestamp, String> getAnnouncementList() {
    return this.announcementList;
  }




  
  public Map.Entry<Timestamp, String> getCurrentAnnouncement() {
    if (this.announcementList.size() > 0)
      return this.announcementList.lastEntry(); 
    return null;
  }





  
  public void setDisbandTime(int disbandTime) {
    this.disbandTime = disbandTime;
  }




  
  public int getDisbandTime() {
    return this.disbandTime;
  }




  
  public boolean isDisbanding() {
    if (this.disbandTime > 0)
    {
      return true;
    }
    return false;
  }







  
  public boolean isMember(int playerObjId) {
    return this.legionMembers.contains(Integer.valueOf(playerObjId));
  }





  
  public void setLegionEmblem(LegionEmblem legionEmblem) {
    this.legionEmblem = legionEmblem;
  }




  
  public LegionEmblem getLegionEmblem() {
    return this.legionEmblem;
  }





  
  public void setLegionWarehouse(LegionWarehouse legionWarehouse) {
    this.legionWarehouse = legionWarehouse;
  }




  
  public LegionWarehouse getLegionWarehouse() {
    return this.legionWarehouse;
  }






  
  public int getWarehouseSlots() {
    switch (getLegionLevel()) {
      
      case 1:
        return 24;
      case 2:
        return 32;
      case 3:
        return 40;
      case 4:
        return 48;
      case 5:
        return 56;
    } 
    return 24;
  }




  
  public Collection<LegionHistory> getLegionHistory() {
    return this.legionHistory;
  }




  
  public void addHistory(LegionHistory history) {
    this.legionHistory.add(history);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\legion\Legion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
