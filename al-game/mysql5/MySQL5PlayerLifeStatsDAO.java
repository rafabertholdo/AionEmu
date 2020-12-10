package mysql5;

import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.gameserver.dao.PlayerLifeStatsDAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.stats.PlayerLifeStats;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;

public class MySQL5PlayerLifeStatsDAO extends PlayerLifeStatsDAO {
    private static final Logger log = Logger.getLogger(MySQL5PlayerLifeStatsDAO.class);

    public static final String INSERT_QUERY = "INSERT INTO `player_life_stats` (`player_id`, `hp`, `mp`, `fp`) VALUES (?,?,?,?)";

    public static final String DELETE_QUERY = "DELETE FROM `player_life_stats` WHERE `player_id`=?";

    public static final String SELECT_QUERY = "SELECT `hp`, `mp`, `fp` FROM `player_life_stats` WHERE `player_id`=?";

    public static final String UPDATE_QUERY = "UPDATE player_life_stats set `hp`=?, `mp`=?, `fp`=? WHERE `player_id`=?";

    public void loadPlayerLifeStat(Player player) {
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con
                    .prepareStatement("SELECT `hp`, `mp`, `fp` FROM `player_life_stats` WHERE `player_id`=?");
            stmt.setInt(1, player.getObjectId());
            ResultSet rset = stmt.executeQuery();
            if (rset.next()) {

                PlayerLifeStats lifeStats = player.getLifeStats();
                lifeStats.setCurrentHp(rset.getInt("hp"));
                lifeStats.setCurrentMp(rset.getInt("mp"));
                lifeStats.setCurrentFp(rset.getInt("fp"));
            } else {

                insertPlayerLifeStat(player);
            }
            rset.close();
            stmt.close();
        } catch (Exception e) {

            log.fatal("Could not restore PlayerLifeStat data for playerObjId: " + player.getObjectId() + " from DB: "
                    + e.getMessage(), e);
        } finally {

            DatabaseFactory.close(con);
        }
    }

    public void insertPlayerLifeStat(Player player) {
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "INSERT INTO `player_life_stats` (`player_id`, `hp`, `mp`, `fp`) VALUES (?,?,?,?)");
            stmt.setInt(1, player.getObjectId());
            stmt.setInt(2, player.getLifeStats().getCurrentHp());
            stmt.setInt(3, player.getLifeStats().getCurrentMp());
            stmt.setInt(4, player.getLifeStats().getCurrentFp());
            stmt.execute();
            stmt.close();
        } catch (Exception e) {

            log.fatal("Could not store PlayerLifeStat data for player " + player.getObjectId() + " from DB: "
                    + e.getMessage(), e);
        } finally {

            DatabaseFactory.close(con);
        }
    }

    public void deletePlayerLifeStat(int playerId) {
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement("DELETE FROM `player_life_stats` WHERE `player_id`=?");
            stmt.setInt(1, playerId);
            stmt.execute();
            stmt.close();
        } catch (Exception e) {

            log.fatal("Could not delete PlayerLifeStat data for player " + playerId + " from DB: " + e.getMessage(), e);
        } finally {

            DatabaseFactory.close(con);
        }
    }

    public void updatePlayerLifeStat(Player player) {
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con
                    .prepareStatement("UPDATE player_life_stats set `hp`=?, `mp`=?, `fp`=? WHERE `player_id`=?");
            stmt.setInt(1, player.getLifeStats().getCurrentHp());
            stmt.setInt(2, player.getLifeStats().getCurrentMp());
            stmt.setInt(3, player.getLifeStats().getCurrentFp());
            stmt.setInt(4, player.getObjectId());
            stmt.execute();
            stmt.close();
        } catch (Exception e) {

            log.fatal("Could not update PlayerLifeStat data for player " + player.getObjectId() + " from DB: "
                    + e.getMessage(), e);
        } finally {

            DatabaseFactory.close(con);
        }
    }

    public boolean supports(String databaseName, int majorVersion, int minorVersion) {
        return MySQL5DAOUtils.supports(databaseName, majorVersion, minorVersion);
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\mysql5\MySQL5PlayerLifeStatsDAO.class Java compiler version: 6 (50.0)
 * JD-Core Version: 1.1.3
 */