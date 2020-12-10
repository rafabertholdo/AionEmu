package mysql5;

import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.gameserver.dao.InventoryDAO;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.player.Equipment;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.Storage;
import com.aionemu.gameserver.model.gameobjects.player.StorageType;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.world.World;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class MySQL5InventoryDAO extends InventoryDAO {
    private static final Logger log = Logger.getLogger(MySQL5InventoryDAO.class);

    private static final String SELECT_QUERY = "SELECT `itemUniqueId`, `itemId`, `itemCount`, `itemColor`, `isEquiped`, `isSoulBound`, `slot`, `enchant`, `itemSkin`, `fusionedItem` FROM `inventory` WHERE `itemOwner`=? AND `itemLocation`=? AND `isEquiped`=?";

    private static final String SELECT_QUERY2 = "SELECT itemUniqueId FROM inventory";

    private static final String INSERT_QUERY = "INSERT INTO `inventory` (`itemUniqueId`, `itemId`, `itemCount`, `itemColor`, `itemOwner`, `isEquiped`, isSoulBound, `slot`, `itemLocation`, `enchant`, `itemSkin`, `fusionedItem`) VALUES(?,?,?,?,?,?,?,?,?,?, ?,?)";
    private static final String UPDATE_QUERY = "UPDATE inventory SET  itemCount=?, itemColor=?, itemOwner=?, isEquiped=?, isSoulBound=?, slot=?, itemLocation=?, enchant=?, itemSkin=?, fusionedItem=? WHERE itemUniqueId=?";
    private static final String DELETE_QUERY = "DELETE FROM inventory WHERE itemUniqueId=?";
    private static final String DELETE_CLEAN_QUERY = "DELETE FROM inventory WHERE itemOwner=? AND (itemLocation=0 OR itemLocation=1)";
    private static final String SELECT_ACCOUNT_QUERY = "SELECT `account_id` FROM `players` WHERE `id`=?";

    public Storage loadStorage(Player player, int objectId, StorageType storageType) {
        Storage inventory = new Storage(storageType);
        int storage = storageType.getId();
        int equipped = 0;

        inventory.setOwnerId(objectId);

        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT `itemUniqueId`, `itemId`, `itemCount`, `itemColor`, `isEquiped`, `isSoulBound`, `slot`, `enchant`, `itemSkin`, `fusionedItem` FROM `inventory` WHERE `itemOwner`=? AND `itemLocation`=? AND `isEquiped`=?");
            stmt.setInt(1, objectId);
            stmt.setInt(2, storage);
            stmt.setInt(3, 0);
            ResultSet rset = stmt.executeQuery();
            while (rset.next()) {

                int itemUniqueId = rset.getInt("itemUniqueId");
                int itemId = rset.getInt("itemId");
                long itemCount = rset.getLong("itemCount");
                int itemColor = rset.getInt("itemColor");
                int isEquiped = rset.getInt("isEquiped");
                int isSoulBound = rset.getInt("isSoulBound");
                int slot = rset.getInt("slot");
                int enchant = rset.getInt("enchant");
                int itemSkin = rset.getInt("itemSkin");
                int fusionedItem = rset.getInt("fusionedItem");
                Item item = new Item(objectId, itemUniqueId, itemId, itemCount, itemColor, (isEquiped == 1),
                        (isSoulBound == 1), slot, storage, enchant, itemSkin, fusionedItem);
                item.setPersistentState(PersistentState.UPDATED);
                ItemService.onLoadHandler(player, inventory, item);
            }
            rset.close();
            stmt.close();
        } catch (Exception e) {

            log.fatal("Could not restore storage data for player: " + objectId + " from DB: " + e.getMessage(), e);
        } finally {

            DatabaseFactory.close(con);
        }
        return inventory;
    }

    public Equipment loadEquipment(Player player) {
        Equipment equipment = new Equipment(player);

        int playerId = player.getObjectId();
        int storage = 0;
        int equipped = 1;

        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT `itemUniqueId`, `itemId`, `itemCount`, `itemColor`, `isEquiped`, `isSoulBound`, `slot`, `enchant`, `itemSkin`, `fusionedItem` FROM `inventory` WHERE `itemOwner`=? AND `itemLocation`=? AND `isEquiped`=?");
            stmt.setInt(1, playerId);
            stmt.setInt(2, 0);
            stmt.setInt(3, 1);
            ResultSet rset = stmt.executeQuery();
            while (rset.next()) {

                int itemUniqueId = rset.getInt("itemUniqueId");
                int itemId = rset.getInt("itemId");
                long itemCount = rset.getLong("itemCount");
                int itemColor = rset.getInt("itemColor");
                int isSoulBound = rset.getInt("isSoulBound");
                int slot = rset.getInt("slot");
                int enchant = rset.getInt("enchant");
                int itemSkin = rset.getInt("itemSkin");
                int fusionedItem = rset.getInt("fusionedItem");
                Item item = new Item(playerId, itemUniqueId, itemId, itemCount, itemColor, true, (isSoulBound == 1),
                        slot, 0, enchant, itemSkin, fusionedItem);
                item.setPersistentState(PersistentState.UPDATED);
                equipment.onLoadHandler(item);
            }
            rset.close();
            stmt.close();
        } catch (Exception e) {

            log.fatal("Could not restore Equipment data for player: " + playerId + " from DB: " + e.getMessage(), e);
        } finally {

            DatabaseFactory.close(con);
        }
        return equipment;
    }

    public List<Item> loadEquipment(int playerId) {
        List<Item> items = new ArrayList<Item>();
        int storage = 0;
        int equipped = 1;

        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT `itemUniqueId`, `itemId`, `itemCount`, `itemColor`, `isEquiped`, `isSoulBound`, `slot`, `enchant`, `itemSkin`, `fusionedItem` FROM `inventory` WHERE `itemOwner`=? AND `itemLocation`=? AND `isEquiped`=?");
            stmt.setInt(1, playerId);
            stmt.setInt(2, 0);
            stmt.setInt(3, 1);
            ResultSet rset = stmt.executeQuery();
            while (rset.next()) {

                int itemUniqueId = rset.getInt("itemUniqueId");
                int itemId = rset.getInt("itemId");
                long itemCount = rset.getLong("itemCount");
                int itemColor = rset.getInt("itemColor");
                int isSoulBound = rset.getInt("isSoulBound");
                int slot = rset.getInt("slot");
                int enchant = rset.getInt("enchant");
                int itemSkin = rset.getInt("itemSkin");
                int fusionedItem = rset.getInt("fusionedItem");
                Item item = new Item(playerId, itemUniqueId, itemId, itemCount, itemColor, true, (isSoulBound == 1),
                        slot, 0, enchant, itemSkin, fusionedItem);
                items.add(item);
            }
            rset.close();
            stmt.close();
        } catch (Exception e) {

            log.fatal("Could not restore Equipment data for player: " + playerId + " from DB: " + e.getMessage(), e);
        } finally {

            DatabaseFactory.close(con);
        }
        return items;
    }

    public int getPlayerAccountId(int playerId) {
        Connection con = null;
        int accountId = 0;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT `account_id` FROM `players` WHERE `id`=?");
            stmt.setInt(1, playerId);
            ResultSet rset = stmt.executeQuery();
            if (rset.next()) {
                accountId = rset.getInt("account_id");
            }
            rset.close();
            stmt.close();
        } catch (Exception e) {

            log.fatal("Could not restore accountId data for player: " + playerId + " from DB: " + e.getMessage(), e);
        } finally {

            DatabaseFactory.close(con);
        }
        return accountId;
    }

    public boolean store(List<Item> items) {
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement insertStmt = con.prepareStatement(
                    "INSERT INTO `inventory` (`itemUniqueId`, `itemId`, `itemCount`, `itemColor`, `itemOwner`, `isEquiped`, isSoulBound, `slot`, `itemLocation`, `enchant`, `itemSkin`, `fusionedItem`) VALUES(?,?,?,?,?,?,?,?,?,?, ?,?)");
            int insertCount = 0;
            PreparedStatement updateStmt = con.prepareStatement(
                    "UPDATE inventory SET  itemCount=?, itemColor=?, itemOwner=?, isEquiped=?, isSoulBound=?, slot=?, itemLocation=?, enchant=?, itemSkin=?, fusionedItem=? WHERE itemUniqueId=?");
            int updateCount = 0;
            PreparedStatement deleteStmt = con.prepareStatement("DELETE FROM inventory WHERE itemUniqueId=?");
            int deleteCount = 0;
            for (Item item : items) {
                Player player;
                switch (item.getPersistentState()) {

                    case NEW:
                        insertStmt.setInt(1, item.getObjectId());
                        insertStmt.setInt(2, item.getItemTemplate().getTemplateId());
                        insertStmt.setLong(3, item.getItemCount());
                        insertStmt.setInt(4, item.getItemColor());
                        insertStmt.setInt(5, item.getOwnerId());
                        insertStmt.setBoolean(6, item.isEquipped());
                        insertStmt.setInt(7, item.isSoulBound() ? 1 : 0);
                        insertStmt.setInt(8, item.getEquipmentSlot());
                        insertStmt.setInt(9, item.getItemLocation());
                        insertStmt.setInt(10, item.getEnchantLevel());
                        insertStmt.setInt(11, item.getItemSkinTemplate().getTemplateId());
                        insertStmt.setInt(12, item.getFusionedItem());
                        if (insertCount >= 500) {

                            insertCount = 0;
                            insertStmt.addBatch();
                            insertStmt.executeBatch();

                            break;
                        }
                        insertCount++;
                        insertStmt.addBatch();
                        break;

                    case UPDATE_REQUIRED:
                        updateStmt.setLong(1, item.getItemCount());
                        updateStmt.setInt(2, item.getItemColor());
                        updateStmt.setInt(3, item.getOwnerId());
                        updateStmt.setBoolean(4, item.isEquipped());
                        updateStmt.setInt(5, item.isSoulBound() ? 1 : 0);
                        updateStmt.setInt(6, item.getEquipmentSlot());
                        updateStmt.setInt(7, item.getItemLocation());
                        updateStmt.setInt(8, item.getEnchantLevel());
                        updateStmt.setInt(9, item.getItemSkinTemplate().getTemplateId());
                        updateStmt.setInt(10, item.getFusionedItem());
                        updateStmt.setInt(11, item.getObjectId());
                        if (updateCount >= 500) {

                            updateCount = 0;
                            updateStmt.addBatch();
                            updateStmt.executeBatch();

                            break;
                        }
                        updateCount++;
                        updateStmt.addBatch();
                        break;

                    case DELETED:
                        player = World.getInstance().findPlayer(item.getOwnerId());
                        if (player != null) {

                            Storage storage = player.getStorage(item.getItemLocation());
                            if (storage != null) {
                                storage.getDeletedItems().remove(item);
                            }
                        }
                        deleteStmt.setInt(1, item.getObjectId());
                        if (deleteCount >= 500) {

                            deleteCount = 0;
                            deleteStmt.addBatch();
                            deleteStmt.executeBatch();

                            break;
                        }
                        deleteCount++;
                        deleteStmt.addBatch();
                        break;
                }

                item.setPersistentState(PersistentState.UPDATED);
            }
            if (insertCount > 0)
                insertStmt.executeBatch();
            if (updateCount > 0)
                updateStmt.executeBatch();
            if (deleteCount > 0)
                deleteStmt.executeBatch();
            insertStmt.close();
            updateStmt.close();
            deleteStmt.close();
        } catch (Exception e) {

            log.error("Error store items", e);
            return false;
        } finally {

            DatabaseFactory.close(con);
        }
        return true;
    }

    public boolean store(Item item) {
        boolean result = false;

        switch (item.getPersistentState()) {

            case NEW:
                result = insertItem(item);
                break;
            case UPDATE_REQUIRED:
                result = updateItem(item);
                break;
            case DELETED:
                result = deleteItem(item);
                break;
        }
        item.setPersistentState(PersistentState.UPDATED);
        return result;
    }

    private boolean insertItem(Item item) {
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "INSERT INTO `inventory` (`itemUniqueId`, `itemId`, `itemCount`, `itemColor`, `itemOwner`, `isEquiped`, isSoulBound, `slot`, `itemLocation`, `enchant`, `itemSkin`, `fusionedItem`) VALUES(?,?,?,?,?,?,?,?,?,?, ?,?)");
            stmt.setInt(1, item.getObjectId());
            stmt.setInt(2, item.getItemTemplate().getTemplateId());
            stmt.setLong(3, item.getItemCount());
            stmt.setInt(4, item.getItemColor());
            stmt.setInt(5, item.getOwnerId());
            stmt.setBoolean(6, item.isEquipped());
            stmt.setInt(7, item.isSoulBound() ? 1 : 0);
            stmt.setInt(8, item.getEquipmentSlot());
            stmt.setInt(9, item.getItemLocation());
            stmt.setInt(10, item.getEnchantLevel());
            stmt.setInt(11, item.getItemSkinTemplate().getTemplateId());
            stmt.setInt(12, item.getFusionedItem());
            stmt.execute();
            stmt.close();
        } catch (Exception e) {

            log.error("Error insert item ItemObjId: " + item.getObjectId(), e);
            return false;
        } finally {

            DatabaseFactory.close(con);
        }
        return true;
    }

    private boolean updateItem(Item item) {
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "UPDATE inventory SET  itemCount=?, itemColor=?, itemOwner=?, isEquiped=?, isSoulBound=?, slot=?, itemLocation=?, enchant=?, itemSkin=?, fusionedItem=? WHERE itemUniqueId=?");
            stmt.setLong(1, item.getItemCount());
            stmt.setInt(2, item.getItemColor());
            stmt.setInt(3, item.getOwnerId());
            stmt.setBoolean(4, item.isEquipped());
            stmt.setInt(5, item.isSoulBound() ? 1 : 0);
            stmt.setInt(6, item.getEquipmentSlot());
            stmt.setInt(7, item.getItemLocation());
            stmt.setInt(8, item.getEnchantLevel());
            stmt.setInt(9, item.getItemSkinTemplate().getTemplateId());
            stmt.setInt(10, item.getFusionedItem());
            stmt.setInt(11, item.getObjectId());
            stmt.execute();
            stmt.close();
        } catch (Exception e) {

            log.error("Error update item ItemObjId: " + item.getObjectId(), e);
            return false;
        } finally {

            DatabaseFactory.close(con);
        }
        return true;
    }

    private boolean deleteItem(Item item) {
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement("DELETE FROM inventory WHERE itemUniqueId=?");
            stmt.setInt(1, item.getObjectId());
            stmt.execute();
            stmt.close();
        } catch (Exception e) {

            log.error("Error delete item. ItemObjId: " + item.getObjectId(), e);
            return false;
        } finally {

            DatabaseFactory.close(con);
        }
        return true;
    }

    public boolean deletePlayerItems(int playerId) {
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con
                    .prepareStatement("DELETE FROM inventory WHERE itemOwner=? AND (itemLocation=0 OR itemLocation=1)");
            stmt.setInt(1, playerId);
            stmt.execute();
            stmt.close();
        } catch (Exception e) {

            log.error("Error Player all items. PlayerObjId: " + playerId, e);
            return false;
        } finally {

            DatabaseFactory.close(con);
        }
        return true;
    }

    public int[] getUsedIDs() {
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT itemUniqueId FROM inventory", 1004, 1007);
            ResultSet rset = stmt.executeQuery();
            rset.last();
            int count = rset.getRow();
            rset.beforeFirst();
            int[] ids = new int[count];
            for (int i = 0; i < count; i++) {

                rset.next();
                ids[i] = rset.getInt("itemUniqueId");
            }

            rset.close();
            stmt.close();

            return ids;
        } catch (SQLException e) {

            log.error("Can't get list of id's from inventory table", e);
        } finally {

            DatabaseFactory.close(con);
        }

        return new int[0];
    }

    public boolean supports(String s, int i, int i1) {
        return MySQL5DAOUtils.supports(s, i, i1);
    }
}
