package mysql5;

import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.gameserver.dao.BrokerDAO;
import com.aionemu.gameserver.model.broker.BrokerRace;
import com.aionemu.gameserver.model.gameobjects.BrokerItem;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class MySQL5BrokerDAO extends BrokerDAO {
  private static final Logger log = Logger.getLogger(MySQL5BrokerDAO.class);

  private static final String SELECT_QUERY = "SELECT * FROM broker";

  private static final String SELECT_QUERY2 = "SELECT * FROM inventory WHERE `itemLocation` = 126";

  private static final String SELECT_QUERY3 = "SELECT id FROM players";

  private static final String INSERT_QUERY = "INSERT INTO `broker` (`itemPointer`, `itemId`, `itemCount`,`seller`, `price`, `brokerRace`, `expireTime`, `sellerId`, `isSold`, `isSettled`) VALUES (?,?,?,?,?,?,?,?,?,?)";
  private static final String DELETE_QUERY = "DELETE FROM `broker` WHERE `itemPointer` = ? AND `sellerId` = ? AND `expireTime` = ?";
  private static final String UPDATE_QUERY = "UPDATE broker SET `isSold` = ?, `isSettled` = 1, `settleTime` = ? WHERE `itemPointer` = ? AND `expireTime` = ? AND `sellerId` = ? AND `isSettled` = 0";

  public List<BrokerItem> loadBroker() {
    List<BrokerItem> brokerItems = new ArrayList<BrokerItem>();

    List<Item> items = getBrokerItems();

    Connection con = null;

    try {
      con = DatabaseFactory.getConnection();
      PreparedStatement stmt = con.prepareStatement("SELECT * FROM broker");
      ResultSet rset = stmt.executeQuery();

      while (rset.next()) {

        int itemPointer = rset.getInt("itemPointer");
        int itemId = rset.getInt("itemId");
        long itemCount = rset.getLong("itemCount");
        String seller = rset.getString("seller");
        int sellerId = rset.getInt("sellerId");
        long price = rset.getLong("price");
        BrokerRace itemBrokerRace = BrokerRace.valueOf(rset.getString("brokerRace"));
        Timestamp expireTime = rset.getTimestamp("expireTime");
        Timestamp settleTime = rset.getTimestamp("settleTime");
        int sold = rset.getInt("isSold");
        int settled = rset.getInt("isSettled");

        boolean isSold = (sold == 1);
        boolean isSettled = (settled == 1);

        Item item = null;
        if (!isSold) {
          for (Item brItem : items) {

            if (itemPointer == brItem.getObjectId()) {

              item = brItem;

              break;
            }
          }
        }
        brokerItems.add(new BrokerItem(item, itemId, itemPointer, itemCount, price, seller, sellerId, itemBrokerRace,
            isSold, isSettled, expireTime, settleTime));
      }

      rset.close();
      stmt.close();
    } catch (SQLException e) {

      log.error(e);
    } finally {

      DatabaseFactory.close(con);
    }

    return brokerItems;
  }

  private List<Item> getBrokerItems() {
    List<Item> brokerItems = new ArrayList<Item>();

    Connection con = null;

    try {
      con = DatabaseFactory.getConnection();
      PreparedStatement stmt = con.prepareStatement("SELECT * FROM inventory WHERE `itemLocation` = 126");
      ResultSet rset = stmt.executeQuery();

      while (rset.next()) {

        int itemUniqueId = rset.getInt("itemUniqueId");
        int itemOwner = rset.getInt("itemOwner");
        int itemId = rset.getInt("itemId");
        long itemCount = rset.getLong("itemCount");
        int itemColor = rset.getInt("itemColor");
        int slot = rset.getInt("slot");
        int location = rset.getInt("itemLocation");
        int enchant = rset.getInt("enchant");
        int itemSkin = rset.getInt("itemSkin");
        int fusionedItem = rset.getInt("fusionedItem");
        brokerItems.add(new Item(itemOwner, itemUniqueId, itemId, itemCount, itemColor, false, false, slot, location,
            enchant, itemSkin, fusionedItem));
      }
      rset.close();
      stmt.close();
    } catch (SQLException e) {

      log.error(e);
    } finally {

      DatabaseFactory.close(con);
    }

    return brokerItems;
  }

  public boolean store(BrokerItem item) {
    boolean result = false;

    if (item == null) {

      log.warn("Null broker item on save");
      return result;
    }

    switch (item.getPersistentState()) {

      case NEW:
        result = insertBrokerItem(item);
        break;

      case DELETED:
        result = deleteBrokerItem(item);
        break;

      case UPDATE_REQUIRED:
        result = updateBrokerItem(item);
        break;
    }

    if (result) {
      item.setPersistentState(PersistentState.UPDATED);
    }
    return result;
  }

  private boolean insertBrokerItem(BrokerItem item) {
    Connection con = null;

    try {
      con = DatabaseFactory.getConnection();
      PreparedStatement stmt = con.prepareStatement(
          "INSERT INTO `broker` (`itemPointer`, `itemId`, `itemCount`,`seller`, `price`, `brokerRace`, `expireTime`, `sellerId`, `isSold`, `isSettled`) VALUES (?,?,?,?,?,?,?,?,?,?)");

      stmt.setInt(1, item.getItemUniqueId());
      stmt.setInt(2, item.getItemId());
      stmt.setLong(3, item.getItemCount());
      stmt.setString(4, item.getSeller());
      stmt.setLong(5, item.getPrice());
      stmt.setString(6, String.valueOf(item.getItemBrokerRace()));
      stmt.setTimestamp(7, item.getExpireTime());
      stmt.setInt(8, item.getSellerId());
      stmt.setBoolean(9, item.isSold());
      stmt.setBoolean(10, item.isSettled());

      stmt.execute();
    } catch (SQLException e) {

      log.error(e);
    } finally {

      DatabaseFactory.close(con);
    }

    return true;
  }

  private boolean deleteBrokerItem(BrokerItem item) {
    Connection con = null;

    try {
      con = DatabaseFactory.getConnection();
      PreparedStatement stmt = con
          .prepareStatement("DELETE FROM `broker` WHERE `itemPointer` = ? AND `sellerId` = ? AND `expireTime` = ?");

      stmt.setInt(1, item.getItemUniqueId());
      stmt.setInt(2, item.getSellerId());
      stmt.setTimestamp(3, item.getExpireTime());

      stmt.execute();
    } catch (SQLException e) {

      log.error(e);
    } finally {

      DatabaseFactory.close(con);
    }

    return true;
  }

  private boolean updateBrokerItem(BrokerItem item) {
    Connection con = null;

    try {
      con = DatabaseFactory.getConnection();
      PreparedStatement stmt = con.prepareStatement(
          "UPDATE broker SET `isSold` = ?, `isSettled` = 1, `settleTime` = ? WHERE `itemPointer` = ? AND `expireTime` = ? AND `sellerId` = ? AND `isSettled` = 0");

      stmt.setBoolean(1, item.isSold());
      stmt.setTimestamp(2, item.getSettleTime());
      stmt.setInt(3, item.getItemUniqueId());
      stmt.setTimestamp(4, item.getExpireTime());
      stmt.setInt(5, item.getSellerId());

      stmt.execute();
    } catch (SQLException e) {

      log.error(e);
    } finally {

      DatabaseFactory.close(con);
    }

    return true;
  }

  public int[] getUsedIDs() {
    Connection con = null;

    try {
      con = DatabaseFactory.getConnection();
      PreparedStatement stmt = con.prepareStatement("SELECT id FROM players", 1004, 1007);

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

      log.error("Can't get list of id's from players table", e);
    } finally {

      DatabaseFactory.close(con);
    }

    return new int[0];
  }

  public boolean supports(String s, int i, int i1) {
    return MySQL5DAOUtils.supports(s, i, i1);
  }
}
