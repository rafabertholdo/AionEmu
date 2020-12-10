package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.services.WeatherService;
import com.aionemu.gameserver.spawnengine.RiftSpawnManager;
import com.aionemu.gameserver.world.World;


























public class CM_LEVEL_READY
  extends AionClientPacket
{
  public CM_LEVEL_READY(int opcode) {
    super(opcode);
  }






  
  protected void readImpl() {}






  
  protected void runImpl() {
    Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
    
    sendPacket((AionServerPacket)new SM_PLAYER_INFO(activePlayer, false));
    activePlayer.getController().startProtectionActiveTask();



    
    World.getInstance().spawn((VisibleObject)activePlayer);
    
    activePlayer.getController().refreshZoneImpl();



    
    WeatherService.getInstance().loadWeather(activePlayer);
    
    QuestEngine.getInstance().onEnterWorld(new QuestEnv(null, activePlayer, Integer.valueOf(0), Integer.valueOf(0)));

    
    sendPacket((AionServerPacket)new SM_SYSTEM_MESSAGE(1390122, new Object[] { Integer.valueOf(activePlayer.getPosition().getInstanceId()) }));
    
    RiftSpawnManager.sendRiftStatus(activePlayer);
    
    activePlayer.getEffectController().updatePlayerEffectIcons();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_LEVEL_READY.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
