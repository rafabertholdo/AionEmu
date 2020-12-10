package com.aionemu.gameserver.controllers;

import com.aionemu.gameserver.model.alliance.PlayerAlliance;
import com.aionemu.gameserver.model.gameobjects.AionObject;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Monster;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.group.PlayerGroup;
import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.services.AllianceService;
import com.aionemu.gameserver.services.DropService;
import com.aionemu.gameserver.services.GroupService;
import com.aionemu.gameserver.utils.stats.StatFunctions;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.WorldType;


















public class MonsterController
  extends NpcController
{
  public void doReward() {
    AionObject winner = getOwner().getAggroList().getMostDamage();
    
    if (winner == null) {
      return;
    }

    
    if (winner instanceof PlayerAlliance) {
      
      AllianceService.getInstance().doReward((PlayerAlliance)winner, getOwner());
    }
    else if (winner instanceof PlayerGroup) {
      
      GroupService.getInstance().doReward((PlayerGroup)winner, getOwner());
    }
    else if (((Player)winner).isInGroup()) {
      
      GroupService.getInstance().doReward(((Player)winner).getPlayerGroup(), getOwner());
    }
    else {
      
      super.doReward();
      
      Player player = (Player)winner;

      
      long expReward = StatFunctions.calculateSoloExperienceReward(player, (Creature)getOwner());
      player.getCommonData().addExp(expReward);

      
      int currentDp = player.getCommonData().getDp();
      int dpReward = StatFunctions.calculateSoloDPReward(player, (Creature)getOwner());
      player.getCommonData().setDp(dpReward + currentDp);

      
      WorldType worldType = World.getInstance().getWorldMap(player.getWorldId()).getWorldType();
      if (worldType == WorldType.ABYSS) {
        
        int apReward = StatFunctions.calculateSoloAPReward(player, (Creature)getOwner());
        player.getCommonData().addAp(apReward);
      } 
      
      QuestEngine.getInstance().onKill(new QuestEnv((VisibleObject)getOwner(), player, Integer.valueOf(0), Integer.valueOf(0)));

      
      DropService.getInstance().registerDrop((Npc)getOwner(), player, player.getLevel());
    } 
  }


  
  public void onRespawn() {
    super.onRespawn();
    DropService.getInstance().unregisterDrop((Npc)getOwner());
  }


  
  public Monster getOwner() {
    return (Monster)super.getOwner();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\MonsterController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
