package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.itemset.ItemPart;
import com.aionemu.gameserver.model.templates.itemset.ItemSetTemplate;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;

public class AddSet extends AdminCommand {
  public AddSet() {
    super("addset");
  }

  public void executeCommand(Player admin, String[] params) {
    if (admin.getAccessLevel() < AdminConfig.COMMAND_ADDSET) {

      PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");

      return;
    }
    if (params.length == 0 || params.length > 2) {

      PacketSendUtility.sendMessage(admin, "syntax //addset <player> <itemset ID>");
      PacketSendUtility.sendMessage(admin, "syntax //addset <itemset ID>");

      return;
    }
    int itemSetId = 0;
    Player receiver = null;

    try {
      itemSetId = Integer.parseInt(params[0]);

      receiver = admin;
    } catch (NumberFormatException e) {

      receiver = World.getInstance().findPlayer(Util.convertName(params[0]));

      if (receiver == null) {

        PacketSendUtility.sendMessage(admin, "Could not find a player by that name.");

        return;
      }

      try {
        itemSetId = Integer.parseInt(params[1]);
      } catch (NumberFormatException ex) {

        PacketSendUtility.sendMessage(admin, "You must give number to itemset ID.");

        return;
      } catch (Exception ex2) {

        PacketSendUtility.sendMessage(admin, "Occurs an error.");

        return;
      }
    }
    ItemSetTemplate itemSet = DataManager.ITEM_SET_DATA.getItemSetTemplate(itemSetId);
    if (itemSet == null) {

      PacketSendUtility.sendMessage(admin, "ItemSet does not exist with id " + itemSetId);

      return;
    }
    if (receiver.getInventory().getNumberOfFreeSlots() < itemSet.getItempart().size()) {

      PacketSendUtility.sendMessage(admin, "Inventory needs at least " + itemSet.getItempart().size() + " free slots.");

      return;
    }
    for (ItemPart setPart : itemSet.getItempart()) {

      if (!ItemService.addItem(receiver, setPart.getItemid(), 1L)) {

        PacketSendUtility.sendMessage(admin, "Item " + setPart.getItemid() + " couldn't be added");

        return;
      }
    }
    PacketSendUtility.sendMessage(admin, "Item Set added successfully");
    PacketSendUtility.sendMessage(receiver, "Admin gives you an item set");
  }
}
