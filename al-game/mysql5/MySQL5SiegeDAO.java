package mysql5;

import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.gameserver.dao.SiegeDAO;
import com.aionemu.gameserver.model.siege.SiegeLocation;
import com.aionemu.gameserver.model.siege.SiegeRace;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javolution.util.FastMap;
import org.apache.log4j.Logger;

public class MySQL5SiegeDAO extends SiegeDAO {
    public static final String SELECT_QUERY = "SELECT `id`, `race`, `legion_id` FROM `siege_locations`";
    public static final String INSERT_QUERY = "INSERT INTO `siege_locations` (`id`, `race`, `legion_id`) VALUES(?, ?, ?)";
    public static final String UPDATE_QUERY = "UPDATE `siege_locations` SET  `race` = ?, `legion_id` = ? WHERE `id` = ?";
    private static final Logger log = Logger.getLogger(MySQL5PlayerDAO.class);

    public boolean loadSiegeLocations(FastMap<Integer, SiegeLocation> locations) {
        boolean success = true;
        Connection con = null;
        List<Integer> loaded = new ArrayList<Integer>();

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT `id`, `race`, `legion_id` FROM `siege_locations`");
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {

                SiegeLocation loc = (SiegeLocation) locations.get(Integer.valueOf(resultSet.getInt("id")));
                loc.setRace(SiegeRace.valueOf(resultSet.getString("race")));
                loc.setLegionId(resultSet.getInt("legion_id"));
                loaded.add(Integer.valueOf(loc.getLocationId()));
            }
            resultSet.close();
            stmt.close();
        } catch (Exception exception) {

            log.warn("Error loading Siege informaiton from database: " + exception.getMessage(), exception);
            success = false;
        } finally {

            DatabaseFactory.close(con);
        }

        FastMap.Entry<Integer, SiegeLocation> e = locations.head(), end = locations.tail();
        while ((e = e.getNext()) != end) {

            SiegeLocation sLoc = (SiegeLocation) e.getValue();
            if (!loaded.contains(Integer.valueOf(sLoc.getLocationId()))) {
                insertSiegeLocation(sLoc);
            }
        }

        return success;
    }

    public boolean updateSiegeLocation(SiegeLocation siegeLocation) {
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con
                    .prepareStatement("UPDATE `siege_locations` SET  `race` = ?, `legion_id` = ? WHERE `id` = ?");
            stmt.setString(1, siegeLocation.getRace().toString());
            stmt.setInt(2, siegeLocation.getLegionId());
            stmt.setInt(3, siegeLocation.getLocationId());
            stmt.execute();
            stmt.close();
        } catch (Exception e) {

            log.error("Error update Siege Location: " + siegeLocation.getLocationId() + " to race: "
                    + siegeLocation.getRace().toString(), e);
            return false;
        } finally {

            DatabaseFactory.close(con);
        }
        return true;
    }

    private boolean insertSiegeLocation(SiegeLocation siegeLocation) {
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con
                    .prepareStatement("INSERT INTO `siege_locations` (`id`, `race`, `legion_id`) VALUES(?, ?, ?)");
            stmt.setInt(1, siegeLocation.getLocationId());
            stmt.setString(2, siegeLocation.getRace().toString());
            stmt.setInt(3, siegeLocation.getLegionId());
            stmt.execute();
            stmt.close();
        } catch (Exception e) {

            log.error("Error insert Siege Location: " + siegeLocation.getLocationId(), e);
            return false;
        } finally {

            DatabaseFactory.close(con);
        }

        return true;
    }

    public boolean supports(String databaseName, int majorVersion, int minorVersion) {
        return MySQL5DAOUtils.supports(databaseName, majorVersion, minorVersion);
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\mysql5\MySQL5SiegeDAO.class Java compiler version: 6 (50.0) JD-Core
 * Version: 1.1.3
 */
