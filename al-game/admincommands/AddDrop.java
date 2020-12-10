package admincommands;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.IUStH;
import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.drop.DropList;
import com.aionemu.gameserver.model.drop.DropTemplate;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.DropService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddDrop extends AdminCommand {
    public AddDrop() {
        super("adddrop");
    }

    public void executeCommand(Player admin, String[] params) {
        if (admin.getAccessLevel() < AdminConfig.COMMAND_ADDDROP) {

            PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");

            return;
        }
        if (params.length != 5) {

            PacketSendUtility.sendMessage(admin, "syntax //adddrop <mobid> <itemid> <min> <max> <chance>");

            return;
        }

        try {
            final int mobId = Integer.parseInt(params[0]);
            final int itemId = Integer.parseInt(params[1]);
            final int min = Integer.parseInt(params[2]);
            final int max = Integer.parseInt(params[3]);
            final int chance = Integer.parseInt(params[4]);

            DropList dropList = DropService.getInstance().getDropList();

            DropTemplate dropTemplate = new DropTemplate(mobId, itemId, min, max, chance);
            dropList.addDropTemplate(mobId, dropTemplate);

            DB.insertUpdate("INSERT INTO droplist (`mobId`, `itemId`, `min`, `max`, `chance`) VALUES (?, ?, ?, ?, ?)",
                    new IUStH() {

                        public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
                            ps.setInt(1, mobId);
                            ps.setInt(2, itemId);
                            ps.setInt(3, min);
                            ps.setInt(4, max);
                            ps.setInt(5, chance);
                            ps.execute();
                        }
                    });
        } catch (Exception ex) {

            PacketSendUtility.sendMessage(admin, "Only numbers are allowed");
            return;
        }
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\admincommands\AddDrop.class Java compiler version: 6 (50.0) JD-Core
 * Version: 1.1.3
 */