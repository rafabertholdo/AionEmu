package mysql5;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.commons.database.IUStH;
import com.aionemu.gameserver.dao.PlayerMacrossesDAO;
import com.aionemu.gameserver.model.gameobjects.player.MacroList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class MySQL5PlayerMacrossesDAO extends PlayerMacrossesDAO {
    private static Logger log = Logger.getLogger(MySQL5PlayerMacrossesDAO.class);

    public static final String INSERT_QUERY = "INSERT INTO `player_macrosses` (`player_id`, `order`, `macro`) VALUES (?,?,?)";

    public static final String DELETE_QUERY = "DELETE FROM `player_macrosses` WHERE `player_id`=? AND `order`=?";

    public static final String SELECT_QUERY = "SELECT `order`, `macro` FROM `player_macrosses` WHERE `player_id`=?";

    public void addMacro(final int playerId, final int macroPosition, final String macro) {
        DB.insertUpdate("INSERT INTO `player_macrosses` (`player_id`, `order`, `macro`) VALUES (?,?,?)", new IUStH() {
            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                MySQL5PlayerMacrossesDAO.log
                        .debug("[DAO: MySQL5PlayerMacrossesDAO] storing macro " + playerId + " " + macroPosition);
                stmt.setInt(1, playerId);
                stmt.setInt(2, macroPosition);
                stmt.setString(3, macro);
                stmt.execute();
            }
        });
    }

    public void deleteMacro(final int playerId, final int macroPosition) {
        DB.insertUpdate("DELETE FROM `player_macrosses` WHERE `player_id`=? AND `order`=?", new IUStH() {

            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                MySQL5PlayerMacrossesDAO.log
                        .debug("[DAO: MySQL5PlayerMacrossesDAO] removing macro " + playerId + " " + macroPosition);
                stmt.setInt(1, playerId);
                stmt.setInt(2, macroPosition);
                stmt.execute();
            }
        });
    }

    public MacroList restoreMacrosses(int playerId) {
        Map<Integer, String> macrosses = new HashMap<Integer, String>();
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con
                    .prepareStatement("SELECT `order`, `macro` FROM `player_macrosses` WHERE `player_id`=?");
            stmt.setInt(1, playerId);
            ResultSet rset = stmt.executeQuery();
            log.debug("[DAO: MySQL5PlayerMacrossesDAO] loading macroses for playerId: " + playerId);
            while (rset.next()) {

                int order = rset.getInt("order");
                String text = rset.getString("macro");
                macrosses.put(Integer.valueOf(order), text);
            }
            rset.close();
            stmt.close();
        } catch (Exception e) {

            log.fatal("Could not restore MacroList data for player " + playerId + " from DB: " + e.getMessage(), e);
        } finally {

            DatabaseFactory.close(con);
        }
        return new MacroList(macrosses);
    }

    public boolean supports(String databaseName, int majorVersion, int minorVersion) {
        return MySQL5DAOUtils.supports(databaseName, majorVersion, minorVersion);
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\mysql5\MySQL5PlayerMacrossesDAO.class Java compiler version: 6 (50.0)
 * JD-Core Version: 1.1.3
 */