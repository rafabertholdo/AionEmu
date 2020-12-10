package com.aionemu.gameserver.world.container;

import com.aionemu.gameserver.model.legion.LegionMember;
import com.aionemu.gameserver.model.legion.LegionMemberEx;
import com.aionemu.gameserver.world.exceptions.DuplicateAionObjectException;
import javolution.util.FastMap;























public class LegionMemberContainer
{
  private final FastMap<Integer, LegionMember> legionMemberById = new FastMap();
  
  private final FastMap<Integer, LegionMemberEx> legionMemberExById = new FastMap();
  private final FastMap<String, LegionMemberEx> legionMemberExByName = new FastMap();






  
  public void addMember(LegionMember legionMember) {
    if (this.legionMemberById.containsKey(Integer.valueOf(legionMember.getObjectId())))
      throw new DuplicateAionObjectException(); 
    this.legionMemberById.put(Integer.valueOf(legionMember.getObjectId()), legionMember);
  }






  
  public LegionMember getMember(int memberObjId) {
    return (LegionMember)this.legionMemberById.get(Integer.valueOf(memberObjId));
  }






  
  public void addMemberEx(LegionMemberEx legionMember) {
    if (this.legionMemberExById.containsKey(Integer.valueOf(legionMember.getObjectId())) || this.legionMemberExByName.containsKey(legionMember.getName()))
    {
      throw new DuplicateAionObjectException(); } 
    this.legionMemberExById.put(Integer.valueOf(legionMember.getObjectId()), legionMember);
    this.legionMemberExByName.put(legionMember.getName(), legionMember);
  }






  
  public LegionMemberEx getMemberEx(int memberObjId) {
    return (LegionMemberEx)this.legionMemberExById.get(Integer.valueOf(memberObjId));
  }






  
  public LegionMemberEx getMemberEx(String memberName) {
    return (LegionMemberEx)this.legionMemberExByName.get(memberName);
  }






  
  public void remove(LegionMemberEx legionMember) {
    this.legionMemberById.remove(Integer.valueOf(legionMember.getObjectId()));
    this.legionMemberExById.remove(Integer.valueOf(legionMember.getObjectId()));
    this.legionMemberExByName.remove(legionMember.getName());
  }







  
  public boolean contains(int memberObjId) {
    return this.legionMemberById.containsKey(Integer.valueOf(memberObjId));
  }







  
  public boolean containsEx(int memberObjId) {
    return this.legionMemberExById.containsKey(Integer.valueOf(memberObjId));
  }







  
  public boolean containsEx(String memberName) {
    return this.legionMemberExByName.containsKey(memberName);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\world\container\LegionMemberContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
