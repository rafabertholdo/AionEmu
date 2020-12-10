package com.aionemu.gameserver.model.alliance;

import java.util.Collection;
import javolution.util.FastMap;























public class PlayerAllianceGroup
{
  private FastMap<Integer, PlayerAllianceMember> groupMembers;
  private int allianceId;
  private PlayerAlliance owner;
  
  public PlayerAllianceGroup(PlayerAlliance alliance) {
    this.groupMembers = (new FastMap()).shared();
    this.owner = alliance;
  }

  
  public PlayerAlliance getParent() {
    return this.owner;
  }

  
  public void setAllianceId(int allianceId) {
    this.allianceId = allianceId;
  }

  
  public int getAllianceId() {
    return this.allianceId;
  }

  
  public void addMember(PlayerAllianceMember member) {
    this.groupMembers.put(Integer.valueOf(member.getObjectId()), member);
    member.setAllianceId(this.allianceId);
  }






  
  public PlayerAllianceMember removeMember(int memberObjectId) {
    return (PlayerAllianceMember)this.groupMembers.remove(Integer.valueOf(memberObjectId));
  }

  
  public PlayerAllianceMember getMemberById(int memberObjectId) {
    return (PlayerAllianceMember)this.groupMembers.get(Integer.valueOf(memberObjectId));
  }

  
  public Collection<PlayerAllianceMember> getMembers() {
    return this.groupMembers.values();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\alliance\PlayerAllianceGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
