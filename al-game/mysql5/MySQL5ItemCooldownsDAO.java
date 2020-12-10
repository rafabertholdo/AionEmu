package mysql5;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.IUStH;
import com.aionemu.commons.database.ParamReadStH;
import com.aionemu.commons.database.ReadStH;
import com.aionemu.gameserver.dao.ItemCooldownsDAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.items.ItemCooldown;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class MySQL5ItemCooldownsDAO extends ItemCooldownsDAO {
    public static final String INSERT_QUERY = "INSERT INTO `item_cooldowns` (`player_id`, `delay_id`, `use_delay`, `reuse_time`) VALUES (?,?,?,?)";
    public static final String DELETE_QUERY = "DELETE FROM `item_cooldowns` WHERE `player_id`=?";
    public static final String SELECT_QUERY = "SELECT `delay_id`, `use_delay`, `reuse_time` FROM `item_cooldowns` WHERE `player_id`=?";

    public void loadItemCooldowns(final Player player) {
        DB.select("SELECT `delay_id`, `use_delay`, `reuse_time` FROM `item_cooldowns` WHERE `player_id`=?",
                (ReadStH) new ParamReadStH() {

                    public void setParams(PreparedStatement stmt) throws SQLException {
                        stmt.setInt(1, player.getObjectId());
                    }

                    public void handleRead(ResultSet rset) throws SQLException {
                        while (rset.next()) {

                            int delayId = rset.getInt("delay_id");
                            int useDelay = rset.getInt("use_delay");
                            long reuseTime = rset.getLong("reuse_time");

                            if (reuseTime > System.currentTimeMillis()) {
                                player.addItemCoolDown(delayId, reuseTime, useDelay);
                            }
                        }
                    }
                });
        player.getEffectController().broadCastEffects();
    }

    public void storeItemCooldowns(final Player player) {
        deleteItemCooldowns(player);
        Map<Integer, ItemCooldown> itemCoolDowns = player.getItemCoolDowns();

        if (itemCoolDowns == null) {
            return;
        }
        for (Map.Entry<Integer, ItemCooldown> entry : itemCoolDowns.entrySet()) {

            final int delayId = ((Integer) entry.getKey()).intValue();
            final long reuseTime = ((ItemCooldown) entry.getValue()).getReuseTime();
            final int useDelay = ((ItemCooldown) entry.getValue()).getUseDelay();

            if (reuseTime - System.currentTimeMillis() < 30000L) {
                continue;
            }
            DB.insertUpdate(
                    "INSERT INTO `item_cooldowns` (`player_id`, `delay_id`, `use_delay`, `reuse_time`) VALUES (?,?,?,?)",
                    new IUStH() {
                        public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                            stmt.setInt(1, player.getObjectId());
                            stmt.setInt(2, delayId);
                            stmt.setInt(3, useDelay);
                            stmt.setLong(4, reuseTime);
                            stmt.execute();
                        }
                    });
        }
    }

    private void deleteItemCooldowns(final Player player) {
        DB.insertUpdate("DELETE FROM `item_cooldowns` WHERE `player_id`=?", new IUStH() {

            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, player.getObjectId());
                stmt.execute();
            }
        });
    }

    public boolean supports(String arg0, int arg1, int arg2) {
        return MySQL5DAOUtils.supports(arg0, arg1, arg2);
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\mysql5\MySQL5ItemCooldownsDAO.class Java compiler version: 6 (50.0) JD-Core
 * Version: 1.1.3
 */