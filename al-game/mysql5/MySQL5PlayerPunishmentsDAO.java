package mysql5;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.IUStH;
import com.aionemu.commons.database.ParamReadStH;
import com.aionemu.commons.database.ReadStH;
import com.aionemu.gameserver.dao.PlayerPunishmentsDAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL5PlayerPunishmentsDAO extends PlayerPunishmentsDAO {
    public static final String SELECT_QUERY = "SELECT `player_id`, `punishment_status`, `punishment_timer` FROM `player_punishments` WHERE `player_id`=?";
    public static final String UPDATE_QUERY = "UPDATE `player_punishments` SET `punishment_status`=?, `punishment_timer`=? WHERE `player_id`=?";
    public static final String REPLACE_QUERY = "REPLACE INTO `player_punishments` VALUES (?,?,?)";
    public static final String DELETE_QUERY = "DELETE FROM `player_punishments` WHERE `player_id`=?";

    public void loadPlayerPunishments(final Player player) {
        DB.select(
                "SELECT `player_id`, `punishment_status`, `punishment_timer` FROM `player_punishments` WHERE `player_id`=?",
                (ReadStH) new ParamReadStH() {
                    public void setParams(PreparedStatement ps) throws SQLException {
                        ps.setInt(1, player.getObjectId());
                    }

                    public void handleRead(ResultSet rs) throws SQLException {
                        while (rs.next()) {

                            player.setPrisonTimer(rs.getLong("punishment_timer"));

                            if (player.isInPrison()) {
                                player.setPrisonTimer(rs.getLong("punishment_timer"));
                                continue;
                            }
                            player.setPrisonTimer(0L);
                        }
                    }
                });
    }

    public void storePlayerPunishments(final Player player) {
        DB.insertUpdate(
                "UPDATE `player_punishments` SET `punishment_status`=?, `punishment_timer`=? WHERE `player_id`=?",
                new IUStH() {
                    public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
                        ps.setInt(1, player.isInPrison() ? 1 : 0);
                        ps.setLong(2, player.getPrisonTimer());
                        ps.setInt(3, player.getObjectId());
                        ps.execute();
                    }
                });
    }

    public void punishPlayer(final Player player, final int mode) {
        DB.insertUpdate("REPLACE INTO `player_punishments` VALUES (?,?,?)", new IUStH() {
            public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
                ps.setInt(1, player.getObjectId());
                ps.setInt(2, mode);
                ps.setLong(3, player.getPrisonTimer());
                ps.execute();
            }
        });
    }

    public void unpunishPlayer(final Player player) {
        DB.insertUpdate("DELETE FROM `player_punishments` WHERE `player_id`=?", new IUStH() {
            public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
                ps.setInt(1, player.getObjectId());
                ps.execute();
            }
        });
    }

    public boolean supports(String s, int i, int i1) {
        return MySQL5DAOUtils.supports(s, i, i1);
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\mysql5\MySQL5PlayerPunishmentsDAO.class Java compiler version: 6 (50.0)
 * JD-Core Version: 1.1.3
 */