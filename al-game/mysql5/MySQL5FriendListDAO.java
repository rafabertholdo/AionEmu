package mysql5;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.commons.database.IUStH;
import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.dao.FriendListDAO;
import com.aionemu.gameserver.dao.PlayerDAO;
import com.aionemu.gameserver.model.gameobjects.player.Friend;
import com.aionemu.gameserver.model.gameobjects.player.FriendList;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class MySQL5FriendListDAO extends FriendListDAO {
    private static final Logger log = Logger.getLogger(MySQL5FriendListDAO.class);

    public static final String LOAD_QUERY = "SELECT * FROM `friends` WHERE `player`=?";

    public static final String ADD_QUERY = "INSERT INTO `friends` (`player`,`friend`) VALUES (?, ?)";

    public static final String DEL_QUERY = "DELETE FROM friends WHERE player = ? AND friend = ?";

    public FriendList load(Player player) {
        List<Friend> friends = new ArrayList<Friend>();
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM `friends` WHERE `player`=?");
            stmt.setInt(1, player.getObjectId());
            ResultSet rset = stmt.executeQuery();
            PlayerDAO dao = (PlayerDAO) DAOManager.getDAO(PlayerDAO.class);
            while (rset.next()) {

                int objId = rset.getInt("friend");

                PlayerCommonData pcd = dao.loadPlayerCommonData(objId);
                if (pcd != null) {
                    Friend friend = new Friend(pcd);
                    friends.add(friend);
                }

            }
        } catch (Exception e) {

            log.fatal("Could not restore QuestStateList data for player: " + player.getObjectId() + " from DB: "
                    + e.getMessage(), e);
        } finally {

            DatabaseFactory.close(con);
        }

        return new FriendList(player, friends);
    }

    public boolean addFriends(final Player player, final Player friend) {
        return DB.insertUpdate("INSERT INTO `friends` (`player`,`friend`) VALUES (?, ?)", new IUStH() {

            public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
                ps.setInt(1, player.getObjectId());
                ps.setInt(2, friend.getObjectId());
                ps.addBatch();

                ps.setInt(1, friend.getObjectId());
                ps.setInt(2, player.getObjectId());
                ps.addBatch();

                ps.executeBatch();
                }
            });
        }

    /*     */
    /*     */
    /*     */
    /*     */
    public boolean delFriends(final int playerOid, final int friendOid) {
        return DB.insertUpdate("DELETE FROM friends WHERE player = ? AND friend = ?", new IUStH()
        {
            /*     */
            /*     */
            public void handleInsertUpdate(PreparedStatement ps) throws SQLException
            {
                ps.setInt(1, playerOid);
                ps.setInt(2, friendOid);
                ps.addBatch();
                /*     */
                ps.setInt(1, friendOid);
                ps.setInt(2, playerOid);
                ps.addBatch();
                /*     */
                ps.executeBatch();
                }
            });
        }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public boolean supports(String s, int i, int i1) {
        return MySQL5DAOUtils.supports(s, i, i1);
        }
    }

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\mysql5\MySQL5FriendListDAO.class Java compiler version: 6 (50.0) JD-Core
 * Version: 1.1.3
 */
