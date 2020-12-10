package mysql5;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.IUStH;
import com.aionemu.commons.database.ParamReadStH;
import com.aionemu.commons.database.ReadStH;
import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.dao.BlockListDAO;
import com.aionemu.gameserver.dao.PlayerDAO;
import com.aionemu.gameserver.model.gameobjects.player.BlockList;
import com.aionemu.gameserver.model.gameobjects.player.BlockedPlayer;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class MySQL5BlockListDAO extends BlockListDAO {
    public static final String LOAD_QUERY = "SELECT blocked_player, reason FROM blocks WHERE player=?";
    public static final String ADD_QUERY = "INSERT INTO blocks (player, blocked_player, reason) VALUES (?, ?, ?)";
    public static final String DEL_QUERY = "DELETE FROM blocks WHERE player=? AND blocked_player=?";
    public static final String SET_REASON_QUERY = "UPDATE blocks SET reason=? WHERE player=? AND blocked_player=?";
    private static Logger log = Logger.getLogger(MySQL5BlockListDAO.class);

    public boolean addBlockedUser(final int playerObjId, final int objIdToBlock, final String reason) {
        return DB.insertUpdate("INSERT INTO blocks (player, blocked_player, reason) VALUES (?, ?, ?)", new IUStH() {

            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, playerObjId);
                stmt.setInt(2, objIdToBlock);
                stmt.setString(3, reason);
                stmt.execute();
            }
        });
    }

    public boolean delBlockedUser(final int playerObjId, final int objIdToDelete) {
        return DB.insertUpdate("DELETE FROM blocks WHERE player=? AND blocked_player=?", new IUStH() {

            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, playerObjId);
                stmt.setInt(2, objIdToDelete);
                stmt.execute();
            }
        });
    }

    public BlockList load(final Player player) {
        final Map<Integer, BlockedPlayer> list = new HashMap<Integer, BlockedPlayer>();

        DB.select("SELECT blocked_player, reason FROM blocks WHERE player=?", (ReadStH) new ParamReadStH() {

            public void handleRead(ResultSet rset) throws SQLException {
                PlayerDAO playerDao = (PlayerDAO) DAOManager.getDAO(PlayerDAO.class);
                while (rset.next()) {

                    int blockedOid = rset.getInt("blocked_player");
                    PlayerCommonData pcd = playerDao.loadPlayerCommonData(blockedOid);
                    if (pcd == null) {

                        MySQL5BlockListDAO.log.error("Attempt to load block list for " + player.getName()
                                + " tried to load a player which does not exist: " + blockedOid);

                        continue;
                    }
                    list.put(Integer.valueOf(blockedOid), new BlockedPlayer(pcd, rset.getString("reason")));
                }
            }

            public void setParams(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, player.getObjectId());
            }
        });
        return new BlockList(list);
    }

    public boolean setReason(final int playerObjId, final int blockedPlayerObjId, final String reason) {
        return DB.insertUpdate("UPDATE blocks SET reason=? WHERE player=? AND blocked_player=?", new IUStH() {

            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                stmt.setString(1, reason);
                stmt.setInt(2, playerObjId);
                stmt.setInt(3, blockedPlayerObjId);
                stmt.execute();
            }
        });
    }

    public boolean supports(String databaseName, int majorVersion, int minorVersion) {
        return MySQL5DAOUtils.supports(databaseName, majorVersion, minorVersion);
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\mysql5\MySQL5BlockListDAO.class Java compiler version: 6 (50.0) JD-Core
 * Version: 1.1.3
 */