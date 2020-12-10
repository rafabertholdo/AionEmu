package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.AionObject;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;

public class ReloadSpawns extends AdminCommand {
  public ReloadSpawns() {
    super("reload_spawn");
  }

  public void executeCommand(Player admin, String[] params) {
    if (admin.getAccessLevel() < AdminConfig.COMMAND_RELOADSPAWNS) {

      PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");

      return;
    }

    for (AionObject obj : World.getInstance().getAllObjects()) {

      if (obj instanceof com.aionemu.gameserver.model.gameobjects.Npc
          || obj instanceof com.aionemu.gameserver.model.gameobjects.Gatherable
          || obj instanceof com.aionemu.gameserver.model.gameobjects.StaticObject) {
        ((VisibleObject) obj).getController().delete();
      }
    }

    SpawnEngine.getInstance().spawnAll();
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\admincommands\ReloadSpawns.class Java compiler version: 6 (50.0) JD-Core
 * Version: 1.1.3
 */
