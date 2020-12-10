package mysql5;

import com.aionemu.commons.database.DB;
import com.aionemu.gameserver.dao.GameTimeDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class MySQL5GameTimeDAO extends GameTimeDAO {
    private static Logger log = Logger.getLogger(MySQL5GameTimeDAO.class);

    public int load() {
        PreparedStatement ps = DB.prepareStatement("SELECT `value` FROM `server_variables` WHERE `key`='time'");

        try {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Integer.parseInt(rs.getString("value"));
            }
        } catch (SQLException e) {

            log.error("Error loading last saved server time", e);
        } finally {

            DB.close(ps);
        }

        return 0;
    }

    public boolean store(int time) {
        boolean success = false;
        PreparedStatement ps = DB.prepareStatement("REPLACE INTO `server_variables` (`key`,`value`) VALUES (?,?)");

        try {
            ps.setString(1, "time");
            ps.setString(2, String.valueOf(time));
            success = (ps.executeUpdate() > 0);
        } catch (SQLException e) {

            log.error("Error storing server time", e);
        } finally {

            DB.close(ps);
        }

        return success;
    }

    public boolean supports(String databaseName, int majorVersion, int minorVersion) {
        return MySQL5DAOUtils.supports(databaseName, majorVersion, minorVersion);
    }
}
