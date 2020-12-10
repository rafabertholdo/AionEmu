package mysql5;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.commons.database.IUStH;
import com.aionemu.commons.database.ParamReadStH;
import com.aionemu.commons.database.ReadStH;
import com.aionemu.gameserver.dao.PlayerDAO;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.dataholders.PlayerInitialData;
import com.aionemu.gameserver.model.Gender;
import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.account.PlayerAccountData;
import com.aionemu.gameserver.model.gameobjects.player.Mailbox;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.WorldPosition;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javolution.util.FastMap;
import org.apache.log4j.Logger;

public class MySQL5PlayerDAO extends PlayerDAO {
    private static final Logger log = Logger.getLogger(MySQL5PlayerDAO.class);

    private FastMap<Integer, PlayerCommonData> playerCommonData = (new FastMap()).shared();

    public boolean isNameUsed(String name) {
        PreparedStatement s = DB.prepareStatement("SELECT count(id) as cnt FROM players WHERE ? = players.name");

        try {
            s.setString(1, name);
            ResultSet rs = s.executeQuery();
            rs.next();
            return (rs.getInt("cnt") > 0);
        } catch (SQLException e) {

            log.error("Can't check if name " + name + ", is used, returning possitive result", e);
            return true;
        } finally {

            DB.close(s);
        }
    }

    public void storePlayer(Player player) {
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "UPDATE players SET name=?, exp=?, recoverexp=?, x=?, y=?, z=?, heading=?, world_id=?, player_class=?, last_online=?, cube_size=?, advenced_stigma_slot_size=?, warehouse_size=?, note=?, bind_point=?, title_id=?, mailboxLetters=? WHERE id=?");

            log.debug("[DAO: MySQL5PlayerDAO] storing player " + player.getObjectId() + " " + player.getName());

            stmt.setString(1, player.getName());
            stmt.setLong(2, player.getCommonData().getExp());
            stmt.setLong(3, player.getCommonData().getExpRecoverable());
            stmt.setFloat(4, player.getX());
            stmt.setFloat(5, player.getY());
            stmt.setFloat(6, player.getZ());
            stmt.setInt(7, player.getHeading());
            stmt.setInt(8, player.getWorldId());
            stmt.setString(9, player.getCommonData().getPlayerClass().toString());
            stmt.setTimestamp(10, player.getCommonData().getLastOnline());
            stmt.setInt(11, player.getCubeSize());
            stmt.setInt(12, player.getCommonData().getAdvencedStigmaSlotSize());
            stmt.setInt(13, player.getWarehouseSize());
            stmt.setString(14, player.getCommonData().getNote());
            stmt.setInt(15, player.getCommonData().getBindPoint());
            stmt.setInt(16, player.getCommonData().getTitleId());

            Mailbox mailBox = player.getMailbox();
            int mails = (mailBox != null) ? mailBox.size() : player.getCommonData().getMailboxLetters();
            stmt.setInt(17, mails);

            stmt.setInt(18, player.getObjectId());
            stmt.execute();
            stmt.close();
        } catch (Exception e) {

            log.error("Error saving player: " + player.getObjectId() + " " + player.getName(), e);
        } finally {

            DatabaseFactory.close(con);
        }
    }

    public boolean saveNewPlayer(PlayerCommonData pcd, int accountId, String accountName) {
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(
                    "INSERT INTO players(id, `name`, account_id, account_name, x, y, z, heading, world_id, gender, race, player_class , cube_size, warehouse_size, online) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0)");

            log.debug("[DAO: MySQL5PlayerDAO] saving new player: " + pcd.getPlayerObjId() + " " + pcd.getName());

            preparedStatement.setInt(1, pcd.getPlayerObjId());
            preparedStatement.setString(2, pcd.getName());
            preparedStatement.setInt(3, accountId);
            preparedStatement.setString(4, accountName);
            preparedStatement.setFloat(5, pcd.getPosition().getX());
            preparedStatement.setFloat(6, pcd.getPosition().getY());
            preparedStatement.setFloat(7, pcd.getPosition().getZ());
            preparedStatement.setInt(8, pcd.getPosition().getHeading());
            preparedStatement.setInt(9, pcd.getPosition().getMapId());
            preparedStatement.setString(10, pcd.getGender().toString());
            preparedStatement.setString(11, pcd.getRace().toString());
            preparedStatement.setString(12, pcd.getPlayerClass().toString());
            preparedStatement.setInt(13, pcd.getCubeSize());
            preparedStatement.setInt(14, pcd.getWarehouseSize());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {

            log.error("Error saving new player: " + pcd.getPlayerObjId() + " " + pcd.getName(), e);
            return false;
        } finally {

            DatabaseFactory.close(con);
        }
        this.playerCommonData.put(Integer.valueOf(pcd.getPlayerObjId()), pcd);
        return true;
    }

    public PlayerCommonData loadPlayerCommonDataByName(String name) {
        Player player = World.getInstance().findPlayer(name);
        if (player != null)
            return player.getCommonData();
        int playerObjId = 0;

        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT id FROM players WHERE name = ?");
            stmt.setString(1, name);
            ResultSet rset = stmt.executeQuery();
            if (rset.next())
                playerObjId = rset.getInt("id");
            rset.close();
            stmt.close();
        } catch (Exception e) {

            log.fatal("Could not restore playerId data for player name: " + name + " from DB: " + e.getMessage(), e);
        } finally {

            DatabaseFactory.close(con);
        }

        if (playerObjId == 0) {
            return null;
        }
        return loadPlayerCommonData(playerObjId);
    }

    public PlayerCommonData loadPlayerCommonData(int playerObjId) {
        PlayerCommonData cached = (PlayerCommonData) this.playerCommonData.get(Integer.valueOf(playerObjId));
        if (cached != null) {

            log.debug("[DAO: MySQL5PlayerDAO] PlayerCommonData for id: " + playerObjId + " obtained from cache");
            return cached;
        }
        PlayerCommonData cd = new PlayerCommonData(playerObjId);
        boolean success = false;
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM players WHERE id = ?");
            stmt.setInt(1, playerObjId);
            ResultSet resultSet = stmt.executeQuery();
            log.debug("[DAO: MySQL5PlayerDAO] loading from db " + playerObjId);

            if (resultSet.next()) {

                success = true;
                cd.setName(resultSet.getString("name"));

                cd.setPlayerClass(PlayerClass.valueOf(resultSet.getString("player_class")));
                cd.setExp(resultSet.getLong("exp"));
                cd.setRecoverableExp(resultSet.getLong("recoverexp"));
                cd.setRace(Race.valueOf(resultSet.getString("race")));
                cd.setGender(Gender.valueOf(resultSet.getString("gender")));
                cd.setLastOnline(resultSet.getTimestamp("last_online"));
                cd.setNote(resultSet.getString("note"));
                cd.setCubesize(resultSet.getInt("cube_size"));
                cd.setAdvencedStigmaSlotSize(resultSet.getInt("advenced_stigma_slot_size"));
                cd.setBindPoint(resultSet.getInt("bind_point"));
                cd.setTitleId(resultSet.getInt("title_id"));
                cd.setWarehouseSize(resultSet.getInt("warehouse_size"));
                cd.setOnline(resultSet.getBoolean("online"));
                cd.setMailboxLetters(resultSet.getInt("mailboxLetters"));

                float x = resultSet.getFloat("x");
                float y = resultSet.getFloat("y");
                float z = resultSet.getFloat("z");
                byte heading = resultSet.getByte("heading");
                int worldId = resultSet.getInt("world_id");
                PlayerInitialData playerInitialData = DataManager.PLAYER_INITIAL_DATA;
                if (z < -1000.0F && playerInitialData != null) {

                    PlayerInitialData.LocationData ld = playerInitialData.getSpawnLocation(cd.getRace());
                    x = ld.getX();
                    y = ld.getY();
                    z = ld.getZ();
                    heading = ld.getHeading();
                    worldId = ld.getMapId();
                }

                WorldPosition position = World.getInstance().createPosition(worldId, x, y, z, heading);
                cd.setPosition(position);
            } else {

                log.info("Missing PlayerCommonData from db " + playerObjId);
            }
            resultSet.close();
            stmt.close();
        } catch (Exception e) {

            log.fatal("Could not restore PlayerCommonData data for player: " + playerObjId + " from DB: "
                    + e.getMessage(), e);
            success = false;
        } finally {

            DatabaseFactory.close(con);
        }

        if (success) {

            this.playerCommonData.put(Integer.valueOf(playerObjId), cd);
            return cd;
        }

        return null;
    }

    public void deletePlayer(int playerId) {
        PreparedStatement statement = DB.prepareStatement("DELETE FROM players WHERE id = ?");

        try {
            statement.setInt(1, playerId);
        } catch (SQLException e) {

            log.error("Some crap, can't set int parameter to PreparedStatement", e);
        }
        DB.executeUpdateAndClose(statement);
    }

    public List<Integer> getPlayerOidsOnAccount(final int accountId) {
        final List<Integer> result = new ArrayList<Integer>();
        boolean success = DB.select("SELECT id FROM players WHERE account_id = ?", (ReadStH) new ParamReadStH() {
            public void handleRead(ResultSet resultSet) throws SQLException {
                while (resultSet.next()) {
                    result.add(Integer.valueOf(resultSet.getInt("id")));
                }
            }

            public void setParams(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1, accountId);
            }
        });

        return success ? result : null;
    }

    public void setCreationDeletionTime(final PlayerAccountData acData) {
        DB.select("SELECT creation_date, deletion_date FROM players WHERE id = ?", (ReadStH) new ParamReadStH() {
            public void setParams(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, acData.getPlayerCommonData().getPlayerObjId());
            }

            public void handleRead(ResultSet rset) throws SQLException {
                rset.next();

                acData.setDeletionDate(rset.getTimestamp("deletion_date"));
                acData.setCreationDate(rset.getTimestamp("creation_date"));
            }
        });
    }

    public void updateDeletionTime(final int objectId, final Timestamp deletionDate) {
        DB.insertUpdate("UPDATE players set deletion_date = ? where id = ?", new IUStH() {
            public void handleInsertUpdate(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setTimestamp(1, deletionDate);
                preparedStatement.setInt(2, objectId);
                preparedStatement.execute();
            }
        });
    }

    public void storeCreationTime(final int objectId, final Timestamp creationDate) {
        DB.insertUpdate("UPDATE players set creation_date = ? where id = ?", new IUStH() {
            public void handleInsertUpdate(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setTimestamp(1, creationDate);
                preparedStatement.setInt(2, objectId);
                preparedStatement.execute();
            }
        });
    }

    public void storeLastOnlineTime(final int objectId, final Timestamp lastOnline) {
        DB.insertUpdate("UPDATE players set last_online = ? where id = ?", new IUStH() {
            public void handleInsertUpdate(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setTimestamp(1, lastOnline);
                preparedStatement.setInt(2, objectId);
                preparedStatement.execute();
            }
        });
    }

    public int[] getUsedIDs() {
        PreparedStatement statement = DB.prepareStatement("SELECT id FROM players", 1004, 1007);

        try {
            ResultSet rs = statement.executeQuery();
            rs.last();
            int count = rs.getRow();
            rs.beforeFirst();
            int[] ids = new int[count];
            for (int i = 0; i < count; i++) {

                rs.next();
                ids[i] = rs.getInt("id");
            }
            return ids;
        } catch (SQLException e) {

            log.error("Can't get list of id's from players table", e);
        } finally {

            DB.close(statement);
        }

        return new int[0];
    }

    public void onlinePlayer(final Player player, final boolean online) {
        DB.insertUpdate("UPDATE players SET online=? WHERE id=?", new IUStH() {
            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                MySQL5PlayerDAO.log
                        .debug("[DAO: MySQL5PlayerDAO] online status " + player.getObjectId() + " " + player.getName());

                stmt.setBoolean(1, online);
                stmt.setInt(2, player.getObjectId());
                stmt.execute();
            }
        });
    }

    public void setPlayersOffline(final boolean online) {
        DB.insertUpdate("UPDATE players SET online=?", new IUStH() {
            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                stmt.setBoolean(1, online);
                stmt.execute();
            }
        });
    }

    public String getPlayerNameByObjId(final int playerObjId) {
        final String[] result = new String[1];
        DB.select("SELECT name FROM players WHERE id = ?", (ReadStH) new ParamReadStH() {

            public void handleRead(ResultSet arg0) throws SQLException {
                arg0.next();
                result[0] = arg0.getString("name");
            }

            public void setParams(PreparedStatement arg0) throws SQLException {
                arg0.setInt(1, playerObjId);
            }
        });
        return result[0];
    }

    public int getAccountIdByName(String name) {
        Connection con = null;
        int accountId = 0;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement s = con.prepareStatement("SELECT `account_id` FROM `players` WHERE `name` = ?");
            s.setString(1, name);
            ResultSet rs = s.executeQuery();
            rs.next();
            accountId = rs.getInt("account_id");
            rs.close();
            s.close();
        } catch (Exception e) {

            return 0;
        } finally {

            DatabaseFactory.close(con);
        }
        return accountId;
    }

    public boolean supports(String s, int i, int i1) {
        return MySQL5DAOUtils.supports(s, i, i1);
    }
}
