package com.aionemu.gameserver.utils.chathandlers;

import com.aionemu.gameserver.model.gameobjects.player.Player;


























public abstract class AdminCommand
{
  private final String commandName;
  
  protected AdminCommand(String commandName) {
    this.commandName = commandName;
  }


























  
  public int getSplitSize() {
    return -1;
  }






  
  public String getCommandName() {
    return this.commandName;
  }
  
  public abstract void executeCommand(Player paramPlayer, String[] paramArrayOfString);
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\chathandlers\AdminCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
