package mysql5;

import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.gameserver.dao.LegionDAO;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.player.Storage;
import com.aionemu.gameserver.model.gameobjects.player.StorageType;
import com.aionemu.gameserver.model.legion.Legion;
import com.aionemu.gameserver.model.legion.LegionEmblem;
import com.aionemu.gameserver.model.legion.LegionHistory;
import com.aionemu.gameserver.model.legion.LegionHistoryType;
import com.aionemu.gameserver.model.legion.LegionWarehouse;
import com.aionemu.gameserver.services.ItemService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.TreeMap;
import org.apache.log4j.Logger;

public class MySQL5LegionDAO extends LegionDAO {
    private static final Logger log = Logger.getLogger(MySQL5LegionDAO.class);

    private static final String INSERT_LEGION_QUERY = "INSERT INTO legions(id, `name`) VALUES (?, ?)";

    private static final String SELECT_LEGION_QUERY1 = "SELECT * FROM legions WHERE id=?";

    private static final String SELECT_LEGION_QUERY2 = "SELECT * FROM legions WHERE name=?";

    private static final String DELETE_LEGION_QUERY = "DELETE FROM legions WHERE id = ?";

    private static final String UPDATE_LEGION_QUERY = "UPDATE legions SET name=?, level=?, contribution_points=?, legionar_permission2=?, centurion_permission1=?, centurion_permission2=?, disband_time=? WHERE id=?";

    private static final String SELECT_LEGIONRANKING_QUERY = "SELECT id, contribution_points FROM legions ORDER BY contribution_points DESC;";

    private static final String INSERT_ANNOUNCEMENT_QUERY = "INSERT INTO legion_announcement_list(`legion_id`, `announcement`, `date`) VALUES (?, ?, ?)";

    private static final String SELECT_ANNOUNCEMENTLIST_QUERY = "SELECT * FROM legion_announcement_list WHERE legion_id=? ORDER BY date ASC LIMIT 0,7;";

    private static final String DELETE_ANNOUNCEMENT_QUERY = "DELETE FROM legion_announcement_list WHERE legion_id = ? AND date = ?";

    private static final String INSERT_EMBLEM_QUERY = "INSERT INTO legion_emblems(legion_id, emblem_id, color_r, color_g, color_b) VALUES (?, ?, ?, ?, ?)";

    private static final String UPDATE_EMBLEM_QUERY = "UPDATE legion_emblems SET emblem_id=?, color_r=?, color_g=?, color_b=? WHERE legion_id=?";

    private static final String SELECT_EMBLEM_QUERY = "SELECT * FROM legion_emblems WHERE legion_id=?";

    private static final String SELECT_STORAGE_QUERY = "SELECT `itemUniqueId`, `itemId`, `itemCount`, `itemColor`, `isEquiped`, `slot`, `enchant`, `itemSkin`, `fusionedItem` FROM `inventory` WHERE `itemOwner`=? AND `itemLocation`=? AND `isEquiped`=?";

    private static final String INSERT_HISTORY_QUERY = "INSERT INTO legion_history(`legion_id`, `date`, `history_type`, `name`) VALUES (?, ?, ?, ?)";

    private static final String SELECT_HISTORY_QUERY = "SELECT * FROM `legion_history` WHERE legion_id=? ORDER BY date ASC;";
    private static final String SELECT_QUERY = "SELECT id FROM legions";

    public boolean isNameUsed(String name) {
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con
                    .prepareStatement("SELECT count(id) as cnt FROM legions WHERE ? = legions.name");
            stmt.setString(1, name);
            ResultSet rset = stmt.executeQuery();
            rset.next();
            return (rset.getInt("cnt") > 0);
        } catch (SQLException e) {

            log.error("Can't check if name " + name + ", is used, returning possitive result", e);
            return true;
        } finally {

            DatabaseFactory.close(con);
        }
    }

    public boolean saveNewLegion(Legion legion) {
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement("INSERT INTO legions(id, `name`) VALUES (?, ?)");

            log.debug(
                    "[DAO: MySQL5LegionDAO] saving new legion: " + legion.getLegionId() + " " + legion.getLegionName());

            stmt.setInt(1, legion.getLegionId());
            stmt.setString(2, legion.getLegionName());
            stmt.execute();
        } catch (SQLException e) {

            log.error(e);
        } finally {

            DatabaseFactory.close(con);
        }

        return true;
    }

    public void storeLegion(Legion legion) {
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "UPDATE legions SET name=?, level=?, contribution_points=?, legionar_permission2=?, centurion_permission1=?, centurion_permission2=?, disband_time=? WHERE id=?");

            log.debug("[DAO: MySQL5LegionDAO] storing player " + legion.getLegionId() + " " + legion.getLegionName());

            stmt.setString(1, legion.getLegionName());
            stmt.setInt(2, legion.getLegionLevel());
            stmt.setInt(3, legion.getContributionPoints());
            stmt.setInt(4, legion.getLegionarPermission2());
            stmt.setInt(5, legion.getCenturionPermission1());
            stmt.setInt(6, legion.getCenturionPermission2());
            stmt.setInt(7, legion.getDisbandTime());
            stmt.setInt(8, legion.getLegionId());
            stmt.execute();
        } catch (SQLException e) {

            log.error(e);
        } finally {

            DatabaseFactory.close(con);
        }
    }

    public Legion loadLegion(String legionName) {
        Legion legion = new Legion();

        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM legions WHERE name=?");

            stmt.setString(1, legionName);

            ResultSet rset = stmt.executeQuery();

            while (rset.next()) {

                legion.setLegionName(legionName);
                legion.setLegionId(rset.getInt("id"));
                legion.setLegionLevel(rset.getInt("level"));
                legion.addContributionPoints(rset.getInt("contribution_points"));

                legion.setLegionPermissions(rset.getInt("legionar_permission2"), rset.getInt("centurion_permission1"),
                        rset.getInt("centurion_permission2"));

                legion.setDisbandTime(rset.getInt("disband_time"));
            }

            rset.close();
            stmt.close();
        } catch (SQLException e) {

            log.error(e);
        } finally {

            DatabaseFactory.close(con);
        }

        log.debug("[MySQL5LegionDAO] Loaded " + legion.getLegionId() + " legion.");

        return (legion.getLegionId() != 0) ? legion : null;
    }

    public Legion loadLegion(int legionId) {
        Legion legion = new Legion();

        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM legions WHERE id=?");

            stmt.setInt(1, legionId);

            ResultSet rset = stmt.executeQuery();

            while (rset.next()) {

                legion.setLegionId(legionId);
                legion.setLegionName(rset.getString("name"));
                legion.setLegionLevel(rset.getInt("level"));
                legion.addContributionPoints(rset.getInt("contribution_points"));

                legion.setLegionPermissions(rset.getInt("legionar_permission2"), rset.getInt("centurion_permission1"),
                        rset.getInt("centurion_permission2"));

                legion.setDisbandTime(rset.getInt("disband_time"));
            }

            rset.close();
            stmt.close();
        } catch (SQLException e) {

            log.error(e);
        } finally {

            DatabaseFactory.close(con);
        }

        log.debug("[MySQL5LegionDAO] Loaded " + legion.getLegionId() + " legion.");

        return (legion.getLegionName() != "") ? legion : null;
    }

    public void deleteLegion(int legionId) {
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement("DELETE FROM legions WHERE id = ?");

            stmt.setInt(1, legionId);
            stmt.execute();
        } catch (SQLException e) {

            log.error("Some crap, can't set int parameter to PreparedStatement", e);
        } finally {

            DatabaseFactory.close(con);
        }
    }

    public int[] getUsedIDs() {
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT id FROM legions", 1004, 1007);
            ResultSet rset = stmt.executeQuery();

            rset.last();
            int count = rset.getRow();
            rset.beforeFirst();
            int[] ids = new int[count];
            for (int i = 0; i < count; i++) {

                rset.next();
                ids[i] = rset.getInt("id");
            }

            rset.close();
            stmt.close();

            return ids;
        } catch (SQLException e) {

            log.error("Can't get list of id's from legions table", e);
        } finally {

            DatabaseFactory.close(con);
        }

        return new int[0];
    }

    public boolean supports(String s, int i, int i1) {
        return MySQL5DAOUtils.supports(s, i, i1);
    }

    public TreeMap<Timestamp, String> loadAnnouncementList(int legionId) {
        TreeMap<Timestamp, String> announcementList = new TreeMap<Timestamp, String>();

        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT * FROM legion_announcement_list WHERE legion_id=? ORDER BY date ASC LIMIT 0,7;");

            stmt.setInt(1, legionId);

            ResultSet rset = stmt.executeQuery();

            while (rset.next()) {

                String message = rset.getString("announcement");
                Timestamp date = rset.getTimestamp("date");

                announcementList.put(date, message);
            }

            rset.close();
            stmt.close();
        } catch (SQLException e) {

            log.error(e);
        } finally {

            DatabaseFactory.close(con);
        }

        log.debug("[MySQL5LegionDAO] Loaded announcementList " + legionId + " legion.");

        return announcementList;
    }

    public boolean saveNewAnnouncement(int legionId, Timestamp currentTime, String message) {
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "INSERT INTO legion_announcement_list(`legion_id`, `announcement`, `date`) VALUES (?, ?, ?)");

            log.debug("[DAO: MySQL5LegionDAO] saving new announcement.");

            stmt.setInt(1, legionId);
            stmt.setString(2, message);
            stmt.setTimestamp(3, currentTime);
            stmt.execute();
        } catch (SQLException e) {

            log.error(e);
        } finally {

            DatabaseFactory.close(con);
        }

        return true;
    }

    public void removeAnnouncement(int legionId, Timestamp unixTime) {
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con
                    .prepareStatement("DELETE FROM legion_announcement_list WHERE legion_id = ? AND date = ?");

            stmt.setInt(1, legionId);
            stmt.setTimestamp(2, unixTime);
            stmt.execute();
        } catch (SQLException e) {

            log.error("Some crap, can't set int parameter to PreparedStatement", e);
        } finally {

            DatabaseFactory.close(con);
        }
    }

    public void storeLegionEmblem(int legionId, LegionEmblem legionEmblem) {
        switch (legionEmblem.getPersistentState()) {

            case UPDATE_REQUIRED:
                updateLegionEmblem(legionId, legionEmblem);
                break;
            case NEW:
                createLegionEmblem(legionId, legionEmblem);
                break;
        }
        legionEmblem.setPersistentState(PersistentState.UPDATED);
    }

    private void createLegionEmblem(int legionId, LegionEmblem legionEmblem) {
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "INSERT INTO legion_emblems(legion_id, emblem_id, color_r, color_g, color_b) VALUES (?, ?, ?, ?, ?)");

            stmt.setInt(1, legionId);
            stmt.setInt(2, legionEmblem.getEmblemId());
            stmt.setInt(3, legionEmblem.getColor_r());
            stmt.setInt(4, legionEmblem.getColor_g());
            stmt.setInt(5, legionEmblem.getColor_b());
            stmt.execute();
        } catch (SQLException e) {

            log.error(e);
        } finally {

            DatabaseFactory.close(con);
        }
    }

    private void updateLegionEmblem(int legionId, LegionEmblem legionEmblem) {
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "UPDATE legion_emblems SET emblem_id=?, color_r=?, color_g=?, color_b=? WHERE legion_id=?");

            stmt.setInt(1, legionEmblem.getEmblemId());
            stmt.setInt(2, legionEmblem.getColor_r());
            stmt.setInt(3, legionEmblem.getColor_g());
            stmt.setInt(4, legionEmblem.getColor_b());
            stmt.setInt(5, legionId);
            stmt.execute();
        } catch (SQLException e) {

            log.error(e);
        } finally {

            DatabaseFactory.close(con);
        }
    }

    public LegionEmblem loadLegionEmblem(int legionId) {
        LegionEmblem legionEmblem = new LegionEmblem();

        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM legion_emblems WHERE legion_id=?");

            stmt.setInt(1, legionId);

            ResultSet rset = stmt.executeQuery();

            while (rset.next()) {
                legionEmblem.setEmblem(rset.getInt("emblem_id"), rset.getInt("color_r"), rset.getInt("color_g"),
                        rset.getInt("color_b"));
            }

            rset.close();
            stmt.close();
        } catch (SQLException e) {

            log.error(e);
        } finally {

            DatabaseFactory.close(con);
        }

        legionEmblem.setPersistentState(PersistentState.UPDATED);

        return legionEmblem;
    }

    public LegionWarehouse loadLegionStorage(Legion legion) {
        LegionWarehouse inventory = new LegionWarehouse(legion);
        int legionId = legion.getLegionId();
        int storage = StorageType.LEGION_WAREHOUSE.getId();
        int equipped = 0;

        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT `itemUniqueId`, `itemId`, `itemCount`, `itemColor`, `isEquiped`, `slot`, `enchant`, `itemSkin`, `fusionedItem` FROM `inventory` WHERE `itemOwner`=? AND `itemLocation`=? AND `isEquiped`=?");

            stmt.setInt(1, legionId);
            stmt.setInt(2, storage);
            stmt.setInt(3, 0);

            ResultSet rset = stmt.executeQuery();

            while (rset.next()) {

                int itemUniqueId = rset.getInt("itemUniqueId");
                int itemId = rset.getInt("itemId");
                int itemCount = rset.getInt("itemCount");
                int itemColor = rset.getInt("itemColor");
                int isEquiped = rset.getInt("isEquiped");
                int slot = rset.getInt("slot");
                int enchant = rset.getInt("enchant");
                int itemSkin = rset.getInt("itemSkin");
                int fusionedItem = rset.getInt("fusionedItem");
                Item item = new Item(legionId, itemUniqueId, itemId, itemCount, itemColor, (isEquiped == 1), false,
                        slot, storage, enchant, itemSkin, fusionedItem);
                item.setPersistentState(PersistentState.UPDATED);
                ItemService.onLoadHandler(null, (Storage) inventory, item);
            }

            rset.close();
            stmt.close();
        } catch (SQLException e) {

            log.error(e);
        } finally {

            DatabaseFactory.close(con);
        }

        return inventory;
    }

    public HashMap<Integer, Integer> loadLegionRanking() {
        HashMap<Integer, Integer> legionRanking = new HashMap<Integer, Integer>();

        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con
                    .prepareStatement("SELECT id, contribution_points FROM legions ORDER BY contribution_points DESC;");
            ResultSet rset = stmt.executeQuery();

            int i = 1;
            while (rset.next()) {

                if (rset.getInt("contribution_points") > 0) {

                    legionRanking.put(Integer.valueOf(rset.getInt("id")), Integer.valueOf(i));
                    i++;
                    continue;
                }
                legionRanking.put(Integer.valueOf(rset.getInt("id")), Integer.valueOf(0));
            }

            rset.close();
            stmt.close();
        } catch (SQLException e) {

            log.error(e);
        } finally {

            DatabaseFactory.close(con);
        }

        return legionRanking;
    }

    public void loadLegionHistory(Legion legion) {
        Collection<LegionHistory> history = legion.getLegionHistory();

        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con
                    .prepareStatement("SELECT * FROM `legion_history` WHERE legion_id=? ORDER BY date ASC;");

            stmt.setInt(1, legion.getLegionId());

            ResultSet rset = stmt.executeQuery();

            while (rset.next()) {
                history.add(new LegionHistory(LegionHistoryType.valueOf(rset.getString("history_type")),
                        rset.getString("name"), rset.getTimestamp("date")));
            }

            rset.close();
            stmt.close();
        } catch (SQLException e) {

            log.error(e);
        } finally {

            DatabaseFactory.close(con);
        }
    }

    public boolean saveNewLegionHistory(int legionId, LegionHistory legionHistory) {
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "INSERT INTO legion_history(`legion_id`, `date`, `history_type`, `name`) VALUES (?, ?, ?, ?)");

            stmt.setInt(1, legionId);
            stmt.setTimestamp(2, legionHistory.getTime());
            stmt.setString(3, legionHistory.getLegionHistoryType().toString());
            stmt.setString(4, legionHistory.getName());
            stmt.execute();
        } catch (SQLException e) {

            log.error(e);
        } finally {

            DatabaseFactory.close(con);
        }

        return true;
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\mysql5\MySQL5LegionDAO.class Java compiler version: 6 (50.0) JD-Core
 * Version: 1.1.3
 */