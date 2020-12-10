package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.Storage;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;

public class Remove extends AdminCommand {
  public Remove() {
    super("remove");
  }

  public void executeCommand(Player admin, String[] params) {
    if (admin.getAccessLevel() < AdminConfig.COMMAND_REMOVE) {

      PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command.");

      return;
    }
    if (params.length < 2) {

      PacketSendUtility.sendMessage(admin, "syntax //remove <player> <item ID> <quantity>");

      return;
    }
    int itemId = 0;
    long itemCount = 1L;
    Player target = World.getInstance().findPlayer(Util.convertName(params[0]));
    if (target == null) {

      PacketSendUtility.sendMessage(admin, "Player isn't online.");

      return;
    }

    try {
      itemId = Integer.parseInt(params[1]);
      if (params.length == 3) {
        itemCount = Long.parseLong(params[2]);
      }
    } catch (NumberFormatException e) {

      PacketSendUtility.sendMessage(admin, "Parameters need to be an integer.");

      return;
    }
    Storage bag = target.getInventory();

    long itemsInBag = bag.getItemCountByItemId(itemId);
    if (itemsInBag == 0L) {

      PacketSendUtility.sendMessage(admin, "Items with that id are not found in the player's bag.");

      return;
    }
    Item item = bag.getFirstItemByItemId(itemId);

    ItemService.decreaseItemCount(target, item, itemCount);

    PacketSendUtility.sendMessage(admin, "Item(s) removed succesfully");
    PacketSendUtility.sendMessage(target, "Admin removed an item from your bag");
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\admincommands\Remove.class Java compiler version: 6 (50.0) JD-Core Version:
 * 1.1.3
 */
