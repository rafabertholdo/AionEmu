package com.aionemu.gameserver.utils.chathandlers;

import com.aionemu.gameserver.model.ChatType;
import com.aionemu.gameserver.model.gameobjects.player.Player;

public interface ChatHandler {
  ChatHandlerResponse handleChatMessage(ChatType paramChatType, String paramString, Player paramPlayer);
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\chathandlers\ChatHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */