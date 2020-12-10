package mysql5;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.commons.database.IUStH;
import com.aionemu.gameserver.dao.PlayerSettingsDAO;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerSettings;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class MySQL5PlayerSettingsDAO extends PlayerSettingsDAO {
    private static final Logger log = Logger.getLogger(MySQL5PlayerSettingsDAO.class);

    public void loadSettings(Player player) {
        int playerId = player.getObjectId();
        PlayerSettings playerSettings = new PlayerSettings();
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT * FROM player_settings WHERE player_id = ?");
            statement.setInt(1, playerId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                int type = resultSet.getInt("settings_type");
                switch (type) {

                    case 0:
                        playerSettings.setUiSettings(resultSet.getBytes("settings"));

                    case 1:
                        playerSettings.setShortcuts(resultSet.getBytes("settings"));

                    case 2:
                        playerSettings.setDisplay(resultSet.getInt("settings"));

                    case 3:
                        playerSettings.setDeny(resultSet.getInt("settings"));
                }

            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {

            log.fatal("Could not restore PlayerSettings data for player " + playerId + " from DB: " + e.getMessage(),
                    e);
        } finally {

            DatabaseFactory.close(con);
        }
        playerSettings.setPersistentState(PersistentState.UPDATED);
        player.setPlayerSettings(playerSettings);
    }

    public void saveSettings(Player player) {
        final int playerId = player.getObjectId();

        PlayerSettings playerSettings = player.getPlayerSettings();
        if (playerSettings.getPersistentState() == PersistentState.UPDATED) {
            return;
        }
        final byte[] uiSettings = playerSettings.getUiSettings();
        final byte[] shortcuts = playerSettings.getShortcuts();
        final int display = playerSettings.getDisplay();
        final int deny = playerSettings.getDeny();

        if (uiSettings != null) {
            DB.insertUpdate("REPLACE INTO player_settings values (?, ?, ?)", new IUStH() {
                public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                    stmt.setInt(1, playerId);
                    stmt.setInt(2, 0);
                    stmt.setBytes(3, uiSettings);
                    stmt.execute();
                }
            });
        }

        if (shortcuts != null) {
            DB.insertUpdate("REPLACE INTO player_settings values (?, ?, ?)", new IUStH() {
                public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                    stmt.setInt(1, playerId);
                    stmt.setInt(2, 1);
                    stmt.setBytes(3, shortcuts);
                    stmt.execute();
                }
            });
        }

        DB.insertUpdate("REPLACE INTO player_settings values (?, ?, ?)", new IUStH() {
            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, playerId);
                stmt.setInt(2, 2);
                stmt.setInt(3, display);
                stmt.execute();
            }
        });

        DB.insertUpdate("REPLACE INTO player_settings values (?, ?, ?)", new IUStH() {
            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, playerId);
                stmt.setInt(2, 3);
                stmt.setInt(3, deny);
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
 * !\mysql5\MySQL5PlayerSettingsDAO.class Java compiler version: 6 (50.0)
 * JD-Core Version: 1.1.3
 */