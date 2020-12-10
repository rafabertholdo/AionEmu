package mysql5;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.commons.database.IUStH;
import com.aionemu.gameserver.dao.ItemStoneListDAO;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.items.GodStone;
import com.aionemu.gameserver.model.items.ItemStone;
import com.aionemu.gameserver.model.items.ManaStone;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;

public class MySQL5ItemStoneListDAO extends ItemStoneListDAO {
    private static final Logger log = Logger.getLogger(MySQL5ItemStoneListDAO.class);

    public static final String INSERT_QUERY = "INSERT INTO `item_stones` (`itemUniqueId`, `itemId`, `slot`, `category`) VALUES (?,?,?, ?)";

    public static final String UPDATE_QUERY = "UPDATE `item_stones` SET `itemId`=? where `itemUniqueId`=? AND `category`=?";

    public static final String DELETE_QUERY = "DELETE FROM `item_stones` WHERE `itemUniqueId`=? AND slot=? AND category=?";

    public static final String SELECT_QUERY = "SELECT `itemId`, `slot`, `category` FROM `item_stones` WHERE `itemUniqueId`=?";

    public void load(List<Item> items) {
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con
                    .prepareStatement("SELECT `itemId`, `slot`, `category` FROM `item_stones` WHERE `itemUniqueId`=?");
            for (Item item : items) {

                if (item.getItemTemplate().isArmor() || item.getItemTemplate().isWeapon()) {

                    stmt.setInt(1, item.getObjectId());
                    ResultSet rset = stmt.executeQuery();
                    while (rset.next()) {

                        int itemId = rset.getInt("itemId");
                        int slot = rset.getInt("slot");
                        int stoneType = rset.getInt("category");
                        if (stoneType == 0) {
                            item.getItemStones()
                                    .add(new ManaStone(item.getObjectId(), itemId, slot, PersistentState.UPDATED));
                            continue;
                        }
                        item.setGoodStone(new GodStone(item.getObjectId(), itemId, PersistentState.UPDATED));
                    }
                    rset.close();
                }
            }
            stmt.close();
        } catch (Exception e) {

            log.fatal("Could not restore ItemStoneList data from DB: " + e.getMessage(), e);
        } finally {

            DatabaseFactory.close(con);
        }
    }

    public void save(Player player) {
        List<Item> allPlayerItems = player.getAllItems();

        for (Item item : allPlayerItems) {

            if (item.hasManaStones()) {
                store(item.getItemStones());
            }
            GodStone godStone = item.getGodStone();
            store(godStone);
        }
    }

    public void store(Set<ManaStone> manaStones) {
        if (manaStones == null) {
            return;
        }
        Iterator<ManaStone> iterator = manaStones.iterator();
        while (iterator.hasNext()) {

            ManaStone manaStone = iterator.next();
            switch (manaStone.getPersistentState()) {

                case NEW:
                    addItemStone(manaStone.getItemObjId(), manaStone.getItemId(), manaStone.getSlot());
                    break;

                case DELETED:
                    deleteItemStone(manaStone.getItemObjId(), manaStone.getSlot());
                    break;
            }

            manaStone.setPersistentState(PersistentState.UPDATED);
        }
    }

    public void store(GodStone godstone) {
        if (godstone == null) {
            return;
        }
        switch (godstone.getPersistentState()) {

            case NEW:
                addGodStone(godstone.getItemObjId(), godstone.getItemId(), godstone.getSlot());
                break;

            case UPDATE_REQUIRED:
                updateGodStone(godstone.getItemObjId(), godstone.getItemId());
                break;
            case DELETED:
                deleteGodStone(godstone.getItemObjId(), godstone.getSlot());
                break;
        }
        godstone.setPersistentState(PersistentState.UPDATED);
    }

    private void addItemStone(final int itemObjId, final int itemId, final int slot) {
        DB.insertUpdate("INSERT INTO `item_stones` (`itemUniqueId`, `itemId`, `slot`, `category`) VALUES (?,?,?, ?)",
                new IUStH() {
                    public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                        stmt.setInt(1, itemObjId);
                        stmt.setInt(2, itemId);
                        stmt.setInt(3, slot);
                        stmt.setInt(4, ItemStone.ItemStoneType.MANASTONE.ordinal());
                        stmt.execute();
                    }
                });
    }

    private void addGodStone(final int itemObjId, final int itemId, final int slot) {
        DB.insertUpdate("INSERT INTO `item_stones` (`itemUniqueId`, `itemId`, `slot`, `category`) VALUES (?,?,?, ?)",
                new IUStH() {
                    public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                        stmt.setInt(1, itemObjId);
                        stmt.setInt(2, itemId);
                        stmt.setInt(3, slot);
                        stmt.setInt(4, ItemStone.ItemStoneType.GODSTONE.ordinal());
                        stmt.execute();
                    }
                });
    }

    private void updateGodStone(final int itemObjId, final int itemId) {
        DB.insertUpdate("UPDATE `item_stones` SET `itemId`=? where `itemUniqueId`=? AND `category`=?", new IUStH() {
            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, itemId);
                stmt.setInt(2, itemObjId);
                stmt.setInt(3, ItemStone.ItemStoneType.GODSTONE.ordinal());
                stmt.execute();
            }
        });
    }

    private void deleteItemStone(final int itemObjId, final int slot) {
        DB.insertUpdate("DELETE FROM `item_stones` WHERE `itemUniqueId`=? AND slot=? AND category=?", new IUStH() {

            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, itemObjId);
                stmt.setInt(2, slot);
                stmt.setInt(3, ItemStone.ItemStoneType.MANASTONE.ordinal());
                stmt.execute();
            }
        });
    }

    private void deleteGodStone(final int itemObjId, final int slot) {
        DB.insertUpdate("DELETE FROM `item_stones` WHERE `itemUniqueId`=? AND slot=? AND category=?", new IUStH() {

            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, itemObjId);
                stmt.setInt(2, slot);
                stmt.setInt(3, ItemStone.ItemStoneType.GODSTONE.ordinal());
                stmt.execute();
            }
        });
    }

    public boolean supports(String s, int i, int i1) {
        return MySQL5DAOUtils.supports(s, i, i1);
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\mysql5\MySQL5ItemStoneListDAO.class Java compiler version: 6 (50.0) JD-Core
 * Version: 1.1.3
 */
