package com.aionemu.gameserver.taskmanager.tasks;

import com.aionemu.gameserver.model.alliance.PlayerAllianceEvent;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.group.GroupEvent;
import com.aionemu.gameserver.services.AllianceService;
import com.aionemu.gameserver.taskmanager.AbstractFIFOPeriodicTaskManager;





















public final class GroupAllianceUpdater
  extends AbstractFIFOPeriodicTaskManager<Player>
{
  private static final class SingletonHolder
  {
    private static final GroupAllianceUpdater INSTANCE = new GroupAllianceUpdater();
  }

  
  public static GroupAllianceUpdater getInstance() {
    return SingletonHolder.INSTANCE;
  }

  
  public GroupAllianceUpdater() {
    super(2000);
  }


  
  protected void callTask(Player player) {
    if (player.isInGroup()) {
      player.getPlayerGroup().updateGroupUIToEvent(player, GroupEvent.MOVEMENT);
    }
    if (player.isInAlliance()) {
      AllianceService.getInstance().updateAllianceUIToEvent(player, PlayerAllianceEvent.MOVEMENT);
    }
  }

  
  protected String getCalledMethodName() {
    return "groupAllianceUpdate()";
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\taskmanager\tasks\GroupAllianceUpdater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
