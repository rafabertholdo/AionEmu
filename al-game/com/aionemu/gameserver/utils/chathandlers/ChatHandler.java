package com.aionemu.gameserver.utils.chathandlers;

import com.aionemu.gameserver.model.ChatType;
import com.aionemu.gameserver.model.gameobjects.player.Player;

public interface ChatHandler {
  ChatHandlerResponse handleChatMessage(ChatType paramChatType, String paramString, Player paramPlayer);
}
