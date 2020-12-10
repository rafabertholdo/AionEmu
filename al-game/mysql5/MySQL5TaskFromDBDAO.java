package mysql5;

import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.gameserver.dao.TaskFromDBDAO;
import com.aionemu.gameserver.model.tasks.TaskFromDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class MySQL5TaskFromDBDAO extends TaskFromDBDAO {
    private static final Logger log = Logger.getLogger(MySQL5TaskFromDBDAO.class);

    private static final String SELECT_ALL_QUERY = "SELECT * FROM tasks ORDER BY id";

    private static final String UPDATE_QUERY = "UPDATE tasks SET last_activation = ? WHERE id = ?";

    public ArrayList<TaskFromDB> getAllTasks() {
        ArrayList<TaskFromDB> result = new ArrayList<TaskFromDB>();

        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM tasks ORDER BY id");

            ResultSet rset = stmt.executeQuery();

            while (rset.next()) {
                result.add(new TaskFromDB(rset.getInt("id"), rset.getString("task"), rset.getString("type"),
                        rset.getTimestamp("last_activation"), rset.getString("startTime"), rset.getInt("delay"),
                        rset.getString("param")));
            }

            rset.close();
            stmt.close();
        } catch (SQLException e) {

            log.error(e);
        } finally {

            DatabaseFactory.close(con);
        }

        return result;
    }

    public void setLastActivation(int id) {
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement("UPDATE tasks SET last_activation = ? WHERE id = ?");

            stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            stmt.setInt(2, id);
            stmt.execute();
        } catch (SQLException e) {

            log.error(e);
        } finally {

            DatabaseFactory.close(con);
        }
    }

    public boolean supports(String s, int i, int i1) {
        return MySQL5DAOUtils.supports(s, i, i1);
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\mysql5\MySQL5TaskFromDBDAO.class Java compiler version: 6 (50.0) JD-Core
 * Version: 1.1.3
 */
