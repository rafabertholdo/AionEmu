package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.cache.HTMLCache;
import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.configs.main.GSConfig;
import com.aionemu.gameserver.model.account.Account;
import com.aionemu.gameserver.model.account.PlayerAccountData;
import com.aionemu.gameserver.model.gameobjects.AionObject;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.Storage;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ABYSS_RANK;
import com.aionemu.gameserver.network.aion.serverpackets.SM_CHANNEL_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_CUBE_UPDATE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION_LIST;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ENTER_WORLD_CHECK;
import com.aionemu.gameserver.network.aion.serverpackets.SM_GAME_TIME;
import com.aionemu.gameserver.network.aion.serverpackets.SM_INFLUENCE_RATIO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_INVENTORY_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_COOLDOWN;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MACRO_LIST;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_ID;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_SPAWN;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PRICES;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_LIST;
import com.aionemu.gameserver.network.aion.serverpackets.SM_RECIPE_LIST;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SIEGE_LOCATION_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SKILL_COOLDOWN;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SKILL_LIST;
import com.aionemu.gameserver.network.aion.serverpackets.SM_STATS_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TITLE_LIST;
import com.aionemu.gameserver.network.aion.serverpackets.SM_UI_SETTINGS;
import com.aionemu.gameserver.services.AllianceService;
import com.aionemu.gameserver.services.BrokerService;
import com.aionemu.gameserver.services.ChatService;
import com.aionemu.gameserver.services.ClassChangeService;
import com.aionemu.gameserver.services.GroupService;
import com.aionemu.gameserver.services.HTMLService;
import com.aionemu.gameserver.services.KiskService;
import com.aionemu.gameserver.services.LegionService;
import com.aionemu.gameserver.services.MailService;
import com.aionemu.gameserver.services.PetitionService;
import com.aionemu.gameserver.services.PlayerService;
import com.aionemu.gameserver.services.PunishmentService;
import com.aionemu.gameserver.services.StigmaService;
import com.aionemu.gameserver.services.TeleportService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.VersionningService;
import com.aionemu.gameserver.utils.rates.Rates;
import com.aionemu.gameserver.world.World;
import java.util.List;






























public class CM_ENTER_WORLD
  extends AionClientPacket
{
  private int objectId;
  
  public CM_ENTER_WORLD(int opcode) {
    super(opcode);
  }





  
  protected void readImpl() {
    this.objectId = readD();
  }





  
  protected void runImpl() {
    AionConnection client = (AionConnection)getConnection();
    Account account = client.getAccount();
    PlayerAccountData playerAccData = client.getAccount().getPlayerAccountData(this.objectId);
    
    if (playerAccData == null) {
      return;
    }


    
    Player player = PlayerService.getPlayer(this.objectId, account);
    
    if (player != null && client.setActivePlayer(player)) {
      
      player.setClientConnection(client);


      
      Player player2 = World.getInstance().findPlayer(player.getObjectId());
      if (player2 != null) {
        
        player2.onLoggedOut();
        World.getInstance().removeObject((AionObject)player2);
      } 
      World.getInstance().storeObject((AionObject)player);
      
      StigmaService.onPlayerLogin(player);
      sendPacket((AionServerPacket)new SM_SKILL_LIST(player));
      
      if (player.getSkillCoolDowns() != null) {
        sendPacket((AionServerPacket)new SM_SKILL_COOLDOWN(player.getSkillCoolDowns()));
      }
      if (player.getItemCoolDowns() != null) {
        sendPacket((AionServerPacket)new SM_ITEM_COOLDOWN(player.getItemCoolDowns()));
      }
      sendPacket((AionServerPacket)new SM_QUEST_LIST(player));
      sendPacket((AionServerPacket)new SM_RECIPE_LIST(player.getRecipeList().getRecipeList()));



      
      sendPacket((AionServerPacket)new SM_ENTER_WORLD_CHECK());
      
      byte[] uiSettings = player.getPlayerSettings().getUiSettings();
      byte[] shortcuts = player.getPlayerSettings().getShortcuts();
      
      if (uiSettings != null) {
        sendPacket((AionServerPacket)new SM_UI_SETTINGS(uiSettings, 0));
      }
      if (shortcuts != null) {
        sendPacket((AionServerPacket)new SM_UI_SETTINGS(shortcuts, 1));
      }
      
      int cubeSize = player.getCubeSize();
      player.getInventory().setLimit(27 + cubeSize * 9);

      
      Storage inventory = player.getInventory();
      List<Item> equipedItems = player.getEquipment().getEquippedItems();
      if (equipedItems.size() != 0)
      {
        sendPacket((AionServerPacket)new SM_INVENTORY_INFO(player.getEquipment().getEquippedItems(), cubeSize));
      }
      
      List<Item> unequipedItems = inventory.getAllItems();
      int itemsSize = unequipedItems.size();
      
      if (itemsSize != 0) {
        
        int index = 0;
        while (index + 10 < itemsSize) {
          
          sendPacket((AionServerPacket)new SM_INVENTORY_INFO(unequipedItems.subList(index, index + 10), cubeSize));
          index += 10;
        } 
        sendPacket((AionServerPacket)new SM_INVENTORY_INFO(unequipedItems.subList(index, itemsSize), cubeSize));
      } 
      
      sendPacket((AionServerPacket)new SM_INVENTORY_INFO());
      
      PlayerService.playerLoggedIn(player);
      
      sendPacket((AionServerPacket)new SM_STATS_INFO(player));
      
      sendPacket((AionServerPacket)new SM_CUBE_UPDATE(player, 6, player.getCommonData().getAdvencedStigmaSlotSize()));
      
      KiskService.onLogin(player);
      TeleportService.sendSetBindPoint(player);

      
      if (player.isInAlliance()) {
        AllianceService.getInstance().onLogin(player);
      }
      sendPacket((AionServerPacket)new SM_PLAYER_ID((AionObject)player));
      
      sendPacket((AionServerPacket)new SM_MACRO_LIST(player));
      sendPacket((AionServerPacket)new SM_GAME_TIME());
      player.getController().updateNearbyQuests();
      
      sendPacket((AionServerPacket)new SM_TITLE_LIST(player));
      sendPacket((AionServerPacket)new SM_CHANNEL_INFO(player.getPosition()));
      sendPacket((AionServerPacket)new SM_PLAYER_SPAWN(player));
      sendPacket((AionServerPacket)new SM_EMOTION_LIST());
      sendPacket((AionServerPacket)new SM_INFLUENCE_RATIO());
      sendPacket((AionServerPacket)new SM_SIEGE_LOCATION_INFO());
      
      sendPacket((AionServerPacket)new SM_PRICES(player.getPrices()));
      sendPacket((AionServerPacket)new SM_ABYSS_RANK(player.getAbyssRank()));
      
      for (String message : getWelcomeMessage()) {
        PacketSendUtility.sendMessage(player, message);
      }
      if (player.isInPrison()) {
        PunishmentService.updatePrisonStatus(player);
      }
      if (player.isLegionMember()) {
        LegionService.getInstance().onLogin(player);
      }
      if (player.isInGroup()) {
        GroupService.getInstance().onLogin(player);
      }
      player.setRates(Rates.getRatesFor(client.getAccount().getMembership()));
      
      ClassChangeService.showClassChangeDialog(player);



      
      MailService.getInstance().onPlayerLogin(player);



      
      BrokerService.getInstance().onPlayerLogin(player);


      
      if (!GSConfig.DISABLE_CHAT_SERVER) {
        ChatService.onPlayerLogin(player);
      }


      
      PetitionService.getInstance().onPlayerLogin(player);



      
      player.getLifeStats().updateCurrentStats();
      
      if (CustomConfig.ENABLE_HTML_WELCOME) {
        HTMLService.showHTML(player, HTMLCache.getInstance().getHTML("welcome.xhtml"));
      }
    } 
  }




  
  private String[] getWelcomeMessage() {
    return new String[] { "Welcome to " + GSConfig.SERVER_NAME + ", powered by Aion Lightning revision " + VersionningService.getGameRevision(), "This software is under GPL. See our website for more info: http://www.aion-lightning.com", "And remember, our source is based on Aion Unique." };
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_ENTER_WORLD.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
