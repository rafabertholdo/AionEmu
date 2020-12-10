package com.aionemu.gameserver.utils.chathandlers;

import com.aionemu.gameserver.model.gameobjects.player.Player;

public abstract class AdminCommand {
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
