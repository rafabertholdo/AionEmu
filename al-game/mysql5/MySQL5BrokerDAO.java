/*     */ package mysql5;
/*     */ 
/*     */ import com.aionemu.commons.database.DatabaseFactory;
/*     */ import com.aionemu.gameserver.dao.BrokerDAO;
/*     */ import com.aionemu.gameserver.model.broker.BrokerRace;
/*     */ import com.aionemu.gameserver.model.gameobjects.BrokerItem;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.PersistentState;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MySQL5BrokerDAO
/*     */   extends BrokerDAO
/*     */ {
/*  38 */   private static final Logger log = Logger.getLogger(MySQL5BrokerDAO.class);
/*     */   
/*     */   private static final String SELECT_QUERY = "SELECT * FROM broker";
/*     */   
/*     */   private static final String SELECT_QUERY2 = "SELECT * FROM inventory WHERE `itemLocation` = 126";
/*     */   
/*     */   private static final String SELECT_QUERY3 = "SELECT id FROM players";
/*     */   
/*     */   private static final String INSERT_QUERY = "INSERT INTO `broker` (`itemPointer`, `itemId`, `itemCount`,`seller`, `price`, `brokerRace`, `expireTime`, `sellerId`, `isSold`, `isSettled`) VALUES (?,?,?,?,?,?,?,?,?,?)";
/*     */   private static final String DELETE_QUERY = "DELETE FROM `broker` WHERE `itemPointer` = ? AND `sellerId` = ? AND `expireTime` = ?";
/*     */   private static final String UPDATE_QUERY = "UPDATE broker SET `isSold` = ?, `isSettled` = 1, `settleTime` = ? WHERE `itemPointer` = ? AND `expireTime` = ? AND `sellerId` = ? AND `isSettled` = 0";
/*     */   
/*     */   public List<BrokerItem> loadBroker() {
/*  51 */     List<BrokerItem> brokerItems = new ArrayList<BrokerItem>();
/*     */     
/*  53 */     List<Item> items = getBrokerItems();
/*     */     
/*  55 */     Connection con = null;
/*     */ 
/*     */     
/*     */     try {
/*  59 */       con = DatabaseFactory.getConnection();
/*  60 */       PreparedStatement stmt = con.prepareStatement("SELECT * FROM broker");
/*  61 */       ResultSet rset = stmt.executeQuery();
/*     */       
/*  63 */       while (rset.next()) {
/*     */         
/*  65 */         int itemPointer = rset.getInt("itemPointer");
/*  66 */         int itemId = rset.getInt("itemId");
/*  67 */         long itemCount = rset.getLong("itemCount");
/*  68 */         String seller = rset.getString("seller");
/*  69 */         int sellerId = rset.getInt("sellerId");
/*  70 */         long price = rset.getLong("price");
/*  71 */         BrokerRace itemBrokerRace = BrokerRace.valueOf(rset.getString("brokerRace"));
/*  72 */         Timestamp expireTime = rset.getTimestamp("expireTime");
/*  73 */         Timestamp settleTime = rset.getTimestamp("settleTime");
/*  74 */         int sold = rset.getInt("isSold");
/*  75 */         int settled = rset.getInt("isSettled");
/*     */         
/*  77 */         boolean isSold = (sold == 1);
/*  78 */         boolean isSettled = (settled == 1);
/*     */         
/*  80 */         Item item = null;
/*  81 */         if (!isSold)
/*     */         {
/*  83 */           for (Item brItem : items) {
/*     */             
/*  85 */             if (itemPointer == brItem.getObjectId()) {
/*     */               
/*  87 */               item = brItem;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         }
/*  93 */         brokerItems.add(new BrokerItem(item, itemId, itemPointer, itemCount, price, seller, sellerId, itemBrokerRace, isSold, isSettled, expireTime, settleTime));
/*     */       } 
/*     */       
/*  96 */       rset.close();
/*  97 */       stmt.close();
/*     */     }
/*  99 */     catch (SQLException e) {
/*     */       
/* 101 */       log.error(e);
/*     */     }
/*     */     finally {
/*     */       
/* 105 */       DatabaseFactory.close(con);
/*     */     } 
/*     */     
/* 108 */     return brokerItems;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<Item> getBrokerItems() {
/* 113 */     List<Item> brokerItems = new ArrayList<Item>();
/*     */     
/* 115 */     Connection con = null;
/*     */ 
/*     */     
/*     */     try {
/* 119 */       con = DatabaseFactory.getConnection();
/* 120 */       PreparedStatement stmt = con.prepareStatement("SELECT * FROM inventory WHERE `itemLocation` = 126");
/* 121 */       ResultSet rset = stmt.executeQuery();
/*     */       
/* 123 */       while (rset.next()) {
/*     */         
/* 125 */         int itemUniqueId = rset.getInt("itemUniqueId");
/* 126 */         int itemOwner = rset.getInt("itemOwner");
/* 127 */         int itemId = rset.getInt("itemId");
/* 128 */         long itemCount = rset.getLong("itemCount");
/* 129 */         int itemColor = rset.getInt("itemColor");
/* 130 */         int slot = rset.getInt("slot");
/* 131 */         int location = rset.getInt("itemLocation");
/* 132 */         int enchant = rset.getInt("enchant");
/* 133 */         int itemSkin = rset.getInt("itemSkin");
/* 134 */         int fusionedItem = rset.getInt("fusionedItem");
/* 135 */         brokerItems.add(new Item(itemOwner, itemUniqueId, itemId, itemCount, itemColor, false, false, slot, location, enchant, itemSkin, fusionedItem));
/*     */       } 
/* 137 */       rset.close();
/* 138 */       stmt.close();
/*     */     }
/* 140 */     catch (SQLException e) {
/*     */       
/* 142 */       log.error(e);
/*     */     }
/*     */     finally {
/*     */       
/* 146 */       DatabaseFactory.close(con);
/*     */     } 
/*     */     
/* 149 */     return brokerItems;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean store(BrokerItem item) {
/* 155 */     boolean result = false;
/*     */     
/* 157 */     if (item == null) {
/*     */       
/* 159 */       log.warn("Null broker item on save");
/* 160 */       return result;
/*     */     } 
/*     */     
/* 163 */     switch (item.getPersistentState()) {
/*     */       
/*     */       case NEW:
/* 166 */         result = insertBrokerItem(item);
/*     */         break;
/*     */       
/*     */       case DELETED:
/* 170 */         result = deleteBrokerItem(item);
/*     */         break;
/*     */       
/*     */       case UPDATE_REQUIRED:
/* 174 */         result = updateBrokerItem(item);
/*     */         break;
/*     */     } 
/*     */     
/* 178 */     if (result) {
/* 179 */       item.setPersistentState(PersistentState.UPDATED);
/*     */     }
/* 181 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean insertBrokerItem(BrokerItem item) {
/* 186 */     Connection con = null;
/*     */ 
/*     */     
/*     */     try {
/* 190 */       con = DatabaseFactory.getConnection();
/* 191 */       PreparedStatement stmt = con.prepareStatement("INSERT INTO `broker` (`itemPointer`, `itemId`, `itemCount`,`seller`, `price`, `brokerRace`, `expireTime`, `sellerId`, `isSold`, `isSettled`) VALUES (?,?,?,?,?,?,?,?,?,?)");
/*     */       
/* 193 */       stmt.setInt(1, item.getItemUniqueId());
/* 194 */       stmt.setInt(2, item.getItemId());
/* 195 */       stmt.setLong(3, item.getItemCount());
/* 196 */       stmt.setString(4, item.getSeller());
/* 197 */       stmt.setLong(5, item.getPrice());
/* 198 */       stmt.setString(6, String.valueOf(item.getItemBrokerRace()));
/* 199 */       stmt.setTimestamp(7, item.getExpireTime());
/* 200 */       stmt.setInt(8, item.getSellerId());
/* 201 */       stmt.setBoolean(9, item.isSold());
/* 202 */       stmt.setBoolean(10, item.isSettled());
/*     */       
/* 204 */       stmt.execute();
/*     */     }
/* 206 */     catch (SQLException e) {
/*     */       
/* 208 */       log.error(e);
/*     */     }
/*     */     finally {
/*     */       
/* 212 */       DatabaseFactory.close(con);
/*     */     } 
/*     */     
/* 215 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean deleteBrokerItem(BrokerItem item) {
/* 220 */     Connection con = null;
/*     */ 
/*     */     
/*     */     try {
/* 224 */       con = DatabaseFactory.getConnection();
/* 225 */       PreparedStatement stmt = con.prepareStatement("DELETE FROM `broker` WHERE `itemPointer` = ? AND `sellerId` = ? AND `expireTime` = ?");
/*     */       
/* 227 */       stmt.setInt(1, item.getItemUniqueId());
/* 228 */       stmt.setInt(2, item.getSellerId());
/* 229 */       stmt.setTimestamp(3, item.getExpireTime());
/*     */       
/* 231 */       stmt.execute();
/*     */     }
/* 233 */     catch (SQLException e) {
/*     */       
/* 235 */       log.error(e);
/*     */     }
/*     */     finally {
/*     */       
/* 239 */       DatabaseFactory.close(con);
/*     */     } 
/*     */     
/* 242 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean updateBrokerItem(BrokerItem item) {
/* 247 */     Connection con = null;
/*     */ 
/*     */     
/*     */     try {
/* 251 */       con = DatabaseFactory.getConnection();
/* 252 */       PreparedStatement stmt = con.prepareStatement("UPDATE broker SET `isSold` = ?, `isSettled` = 1, `settleTime` = ? WHERE `itemPointer` = ? AND `expireTime` = ? AND `sellerId` = ? AND `isSettled` = 0");
/*     */       
/* 254 */       stmt.setBoolean(1, item.isSold());
/* 255 */       stmt.setTimestamp(2, item.getSettleTime());
/* 256 */       stmt.setInt(3, item.getItemUniqueId());
/* 257 */       stmt.setTimestamp(4, item.getExpireTime());
/* 258 */       stmt.setInt(5, item.getSellerId());
/*     */       
/* 260 */       stmt.execute();
/*     */     }
/* 262 */     catch (SQLException e) {
/*     */       
/* 264 */       log.error(e);
/*     */     }
/*     */     finally {
/*     */       
/* 268 */       DatabaseFactory.close(con);
/*     */     } 
/*     */     
/* 271 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getUsedIDs() {
/* 277 */     Connection con = null;
/*     */ 
/*     */     
/*     */     try {
/* 281 */       con = DatabaseFactory.getConnection();
/* 282 */       PreparedStatement stmt = con.prepareStatement("SELECT id FROM players", 1004, 1007);
/*     */       
/* 284 */       ResultSet rset = stmt.executeQuery();
/* 285 */       rset.last();
/* 286 */       int count = rset.getRow();
/* 287 */       rset.beforeFirst();
/* 288 */       int[] ids = new int[count];
/* 289 */       for (int i = 0; i < count; i++) {
/*     */         
/* 291 */         rset.next();
/* 292 */         ids[i] = rset.getInt("id");
/*     */       } 
/*     */       
/* 295 */       rset.close();
/* 296 */       stmt.close();
/*     */       
/* 298 */       return ids;
/*     */     }
/* 300 */     catch (SQLException e) {
/*     */       
/* 302 */       log.error("Can't get list of id's from players table", e);
/*     */     }
/*     */     finally {
/*     */       
/* 306 */       DatabaseFactory.close(con);
/*     */     } 
/*     */     
/* 309 */     return new int[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean supports(String s, int i, int i1) {
/* 314 */     return MySQL5DAOUtils.supports(s, i, i1);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\mysql5\MySQL5BrokerDAO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */