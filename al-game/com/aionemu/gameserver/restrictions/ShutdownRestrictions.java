package com.aionemu.gameserver.restrictions;

import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.model.Skill;
import com.aionemu.gameserver.utils.PacketSendUtility;






















public class ShutdownRestrictions
  extends AbstractRestrictions
{
  public boolean isRestricted(Player player, Class<? extends Restrictions> callingRestriction) {
    if (isInShutdownProgress(player)) {
      
      PacketSendUtility.sendMessage(player, "You are in shutdown progress!");
      return true;
    } 
    
    return false;
  }


  
  public boolean canAttack(Player player, VisibleObject target) {
    if (isInShutdownProgress(player)) {
      
      PacketSendUtility.sendMessage(player, "You cannot attack in Shutdown progress!");
      return false;
    } 
    
    return true;
  }


  
  public boolean canAffectBySkill(Player player, VisibleObject target) {
    return true;
  }


  
  public boolean canUseSkill(Player player, Skill skill) {
    if (isInShutdownProgress(player)) {
      
      PacketSendUtility.sendMessage(player, "You cannot use skills in Shutdown progress!");
      return false;
    } 
    
    return true;
  }


  
  public boolean canChat(Player player) {
    if (isInShutdownProgress(player)) {
      
      PacketSendUtility.sendMessage(player, "You cannot chat in Shutdown progress!");
      return false;
    } 
    
    return true;
  }


  
  public boolean canInviteToGroup(Player player, Player target) {
    if (isInShutdownProgress(player)) {
      
      PacketSendUtility.sendMessage(player, "You cannot invite members to group in Shutdown progress!");
      return false;
    } 
    
    return true;
  }


  
  public boolean canInviteToAlliance(Player player, Player target) {
    if (isInShutdownProgress(player)) {
      
      PacketSendUtility.sendMessage(player, "You cannot invite members to alliance in Shutdown progress!");
      return false;
    } 
    
    return true;
  }


  
  public boolean canChangeEquip(Player player) {
    if (isInShutdownProgress(player)) {
      
      PacketSendUtility.sendMessage(player, "You cannot equip / unequip item in Shutdown progress!");
      return false;
    } 
    
    return true;
  }

  
  private boolean isInShutdownProgress(Player player) {
    return player.getController().isInShutdownProgress();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\restrictions\ShutdownRestrictions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
