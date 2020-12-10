package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

public class SpawnNpc extends AdminCommand {
  public SpawnNpc() {
    super("spawn");
  }

  public void executeCommand(Player admin, String[] params) {
    if (admin.getAccessLevel() < AdminConfig.COMMAND_SPAWNNPC) {

      PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");

      return;
    }
    if (params.length < 1) {

      PacketSendUtility.sendMessage(admin, "syntax //spawn <template_id> {norespawn}");

      return;
    }
    boolean noRespawn = false;

    if (params.length == 2 && "norespawn".equalsIgnoreCase(params[1])) {
      noRespawn = true;
    }
    int templateId = Integer.parseInt(params[0]);
    float x = admin.getX();
    float y = admin.getY();
    float z = admin.getZ();
    byte heading = admin.getHeading();
    int worldId = admin.getWorldId();

    SpawnTemplate spawn = SpawnEngine.getInstance().addNewSpawn(worldId, 1, templateId, x, y, z, heading, 0, 0,
        noRespawn, true);

    if (spawn == null) {

      PacketSendUtility.sendMessage(admin, "There is no template with id " + templateId);

      return;
    }
    VisibleObject visibleObject = SpawnEngine.getInstance().spawnObject(spawn, 1);
    String objectName = visibleObject.getObjectTemplate().getName();

    PacketSendUtility.sendMessage(admin, objectName + " spawned");
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\admincommands\SpawnNpc.class Java compiler version: 6 (50.0) JD-Core
 * Version: 1.1.3
 */
