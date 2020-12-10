package com.aionemu.gameserver.utils.chathandlers;

import com.aionemu.commons.scripting.scriptmanager.ScriptManager;
import com.aionemu.gameserver.GameServerError;
import java.io.File;
import javolution.util.FastList;

public class ChatHandlers {
  private FastList<ChatHandler> handlers;
  public static final File CHAT_DESCRIPTOR_FILE = new File("./data/scripts/system/handlers.xml");

  private ScriptManager sm;

  public static final ChatHandlers getInstance() {
    return SingletonHolder.instance;
  }

  private ChatHandlers() {
    this.handlers = new FastList();
    this.sm = new ScriptManager();
    createChatHandlers();
  }

  void addChatHandler(ChatHandler ch) {
    this.handlers.add(ch);
  }

  public FastList<ChatHandler> getHandlers() {
    return this.handlers;
  }

  private void createChatHandlers() {
    AdminCommandChatHandler adminCCH = new AdminCommandChatHandler();
    addChatHandler(adminCCH);

    this.sm.setGlobalClassListener(new ChatHandlersLoader(adminCCH));

    try {
      this.sm.load(CHAT_DESCRIPTOR_FILE);
    } catch (Exception e) {

      throw new GameServerError("Can't initialize chat handlers.", e);
    }
  }

  private static class SingletonHolder {
    protected static final ChatHandlers instance = new ChatHandlers();
  }
}
