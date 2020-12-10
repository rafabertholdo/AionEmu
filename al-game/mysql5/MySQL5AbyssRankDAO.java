/*     */ package mysql5;
/*     */ 
/*     */ import com.aionemu.commons.database.DatabaseFactory;
/*     */ import com.aionemu.gameserver.dao.AbyssRankDAO;
/*     */ import com.aionemu.gameserver.model.gameobjects.PersistentState;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.AbyssRank;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MySQL5AbyssRankDAO
/*     */   extends AbyssRankDAO
/*     */ {
/*  41 */   private static final Logger log = Logger.getLogger(MySQL5AbyssRankDAO.class);
/*     */   
/*     */   public static final String SELECT_QUERY = "SELECT daily_ap, weekly_ap, ap, rank, top_ranking, daily_kill, weekly_kill, all_kill, max_rank, last_kill, last_ap, last_update FROM abyss_rank WHERE player_id = ?";
/*     */   
/*     */   public static final String INSERT_QUERY = "INSERT INTO abyss_rank (player_id, daily_ap, weekly_ap, ap, rank, top_ranking, daily_kill, weekly_kill, all_kill, max_rank, last_kill, last_ap, last_update) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
/*     */   
/*     */   public static final String UPDATE_QUERY = "UPDATE abyss_rank SET  daily_ap = ?, weekly_ap = ?, ap = ?, rank = ?, top_ranking = ?, daily_kill = ?, weekly_kill = ?, all_kill = ?, max_rank = ?, last_kill = ?, last_ap = ?, last_update = ? WHERE player_id = ?";
/*     */   
/*     */   public void loadAbyssRank(Player player) {
/*  50 */     Connection con = null;
/*     */ 
/*     */     
/*     */     try {
/*  54 */       con = DatabaseFactory.getConnection();
/*  55 */       PreparedStatement stmt = con.prepareStatement("SELECT daily_ap, weekly_ap, ap, rank, top_ranking, daily_kill, weekly_kill, all_kill, max_rank, last_kill, last_ap, last_update FROM abyss_rank WHERE player_id = ?");
/*     */       
/*  57 */       stmt.setInt(1, player.getObjectId());
/*     */       
/*  59 */       ResultSet resultSet = stmt.executeQuery();
/*     */       
/*  61 */       if (resultSet.next()) {
/*     */         
/*  63 */         int daily_ap = resultSet.getInt("daily_ap");
/*  64 */         int weekly_ap = resultSet.getInt("weekly_ap");
/*  65 */         int ap = resultSet.getInt("ap");
/*  66 */         int rank = resultSet.getInt("rank");
/*  67 */         int top_ranking = resultSet.getInt("top_ranking");
/*  68 */         int daily_kill = resultSet.getInt("daily_kill");
/*  69 */         int weekly_kill = resultSet.getInt("weekly_kill");
/*  70 */         int all_kill = resultSet.getInt("all_kill");
/*  71 */         int max_rank = resultSet.getInt("max_rank");
/*  72 */         int last_kill = resultSet.getInt("last_kill");
/*  73 */         int last_ap = resultSet.getInt("last_ap");
/*  74 */         long last_update = resultSet.getLong("last_update");
/*     */         
/*  76 */         AbyssRank abyssRank = new AbyssRank(daily_ap, weekly_ap, ap, rank, top_ranking, daily_kill, weekly_kill, all_kill, max_rank, last_kill, last_ap, last_update);
/*  77 */         abyssRank.setPersistentState(PersistentState.UPDATED);
/*  78 */         player.setAbyssRank(abyssRank);
/*     */       }
/*     */       else {
/*     */         
/*  82 */         AbyssRank abyssRank = new AbyssRank(0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, System.currentTimeMillis());
/*  83 */         abyssRank.setPersistentState(PersistentState.NEW);
/*  84 */         player.setAbyssRank(abyssRank);
/*     */       } 
/*     */       
/*  87 */       resultSet.close();
/*  88 */       stmt.close();
/*     */     }
/*  90 */     catch (SQLException e) {
/*     */       
/*  92 */       log.error(e);
/*     */     }
/*     */     finally {
/*     */       
/*  96 */       DatabaseFactory.close(con);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean storeAbyssRank(Player player) {
/* 103 */     AbyssRank rank = player.getAbyssRank();
/* 104 */     boolean result = false;
/* 105 */     switch (rank.getPersistentState()) {
/*     */       
/*     */       case NEW:
/* 108 */         result = addRank(player.getObjectId(), rank);
/*     */         break;
/*     */       case UPDATE_REQUIRED:
/* 111 */         result = updateRank(player.getObjectId(), rank);
/*     */         break;
/*     */     } 
/* 114 */     rank.setPersistentState(PersistentState.UPDATED);
/* 115 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean addRank(int objectId, AbyssRank rank) {
/* 125 */     Connection con = null;
/*     */ 
/*     */     
/*     */     try {
/* 129 */       con = DatabaseFactory.getConnection();
/* 130 */       PreparedStatement stmt = con.prepareStatement("INSERT INTO abyss_rank (player_id, daily_ap, weekly_ap, ap, rank, top_ranking, daily_kill, weekly_kill, all_kill, max_rank, last_kill, last_ap, last_update) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
/*     */       
/* 132 */       stmt.setInt(1, objectId);
/* 133 */       stmt.setInt(2, rank.getDailyAP());
/* 134 */       stmt.setInt(3, rank.getWeeklyAP());
/* 135 */       stmt.setInt(4, rank.getAp());
/* 136 */       stmt.setInt(5, rank.getRank().getId());
/* 137 */       stmt.setInt(6, rank.getTopRanking());
/* 138 */       stmt.setInt(7, rank.getDailyKill());
/* 139 */       stmt.setInt(8, rank.getWeeklyKill());
/* 140 */       stmt.setInt(9, rank.getAllKill());
/* 141 */       stmt.setInt(10, rank.getMaxRank());
/* 142 */       stmt.setInt(11, rank.getLastKill());
/* 143 */       stmt.setInt(12, rank.getLastAP());
/* 144 */       stmt.setLong(13, rank.getLastUpdate());
/* 145 */       stmt.execute();
/*     */       
/* 147 */       return true;
/*     */     }
/* 149 */     catch (SQLException e) {
/*     */       
/* 151 */       log.error(e);
/*     */       
/* 153 */       return false;
/*     */     }
/*     */     finally {
/*     */       
/* 157 */       DatabaseFactory.close(con);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean updateRank(int objectId, AbyssRank rank) {
/* 168 */     Connection con = null;
/*     */ 
/*     */     
/*     */     try {
/* 172 */       con = DatabaseFactory.getConnection();
/* 173 */       PreparedStatement stmt = con.prepareStatement("UPDATE abyss_rank SET  daily_ap = ?, weekly_ap = ?, ap = ?, rank = ?, top_ranking = ?, daily_kill = ?, weekly_kill = ?, all_kill = ?, max_rank = ?, last_kill = ?, last_ap = ?, last_update = ? WHERE player_id = ?");
/*     */       
/* 175 */       stmt.setInt(1, rank.getDailyAP());
/* 176 */       stmt.setInt(2, rank.getWeeklyAP());
/* 177 */       stmt.setInt(3, rank.getAp());
/* 178 */       stmt.setInt(4, rank.getRank().getId());
/* 179 */       stmt.setInt(5, rank.getTopRanking());
/* 180 */       stmt.setInt(6, rank.getDailyKill());
/* 181 */       stmt.setInt(7, rank.getWeeklyKill());
/* 182 */       stmt.setInt(8, rank.getAllKill());
/* 183 */       stmt.setInt(9, rank.getMaxRank());
/* 184 */       stmt.setInt(10, rank.getLastKill());
/* 185 */       stmt.setInt(11, rank.getLastAP());
/* 186 */       stmt.setLong(12, rank.getLastUpdate());
/* 187 */       stmt.setInt(13, objectId);
/* 188 */       stmt.execute();
/*     */       
/* 190 */       return true;
/*     */     }
/* 192 */     catch (SQLException e) {
/*     */       
/* 194 */       log.error(e);
/*     */       
/* 196 */       return false;
/*     */     }
/*     */     finally {
/*     */       
/* 200 */       DatabaseFactory.close(con);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean supports(String databaseName, int majorVersion, int minorVersion) {
/* 207 */     return MySQL5DAOUtils.supports(databaseName, majorVersion, minorVersion);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\mysql5\MySQL5AbyssRankDAO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */