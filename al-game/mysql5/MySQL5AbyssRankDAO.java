package mysql5;

import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.gameserver.dao.AbyssRankDAO;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.player.AbyssRank;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

























public class MySQL5AbyssRankDAO
  extends AbyssRankDAO
{
  private static final Logger log = Logger.getLogger(MySQL5AbyssRankDAO.class);
  
  public static final String SELECT_QUERY = "SELECT daily_ap, weekly_ap, ap, rank, top_ranking, daily_kill, weekly_kill, all_kill, max_rank, last_kill, last_ap, last_update FROM abyss_rank WHERE player_id = ?";
  
  public static final String INSERT_QUERY = "INSERT INTO abyss_rank (player_id, daily_ap, weekly_ap, ap, rank, top_ranking, daily_kill, weekly_kill, all_kill, max_rank, last_kill, last_ap, last_update) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
  
  public static final String UPDATE_QUERY = "UPDATE abyss_rank SET  daily_ap = ?, weekly_ap = ?, ap = ?, rank = ?, top_ranking = ?, daily_kill = ?, weekly_kill = ?, all_kill = ?, max_rank = ?, last_kill = ?, last_ap = ?, last_update = ? WHERE player_id = ?";
  
  public void loadAbyssRank(Player player) {
    Connection con = null;

    
    try {
      con = DatabaseFactory.getConnection();
      PreparedStatement stmt = con.prepareStatement("SELECT daily_ap, weekly_ap, ap, rank, top_ranking, daily_kill, weekly_kill, all_kill, max_rank, last_kill, last_ap, last_update FROM abyss_rank WHERE player_id = ?");
      
      stmt.setInt(1, player.getObjectId());
      
      ResultSet resultSet = stmt.executeQuery();
      
      if (resultSet.next()) {
        
        int daily_ap = resultSet.getInt("daily_ap");
        int weekly_ap = resultSet.getInt("weekly_ap");
        int ap = resultSet.getInt("ap");
        int rank = resultSet.getInt("rank");
        int top_ranking = resultSet.getInt("top_ranking");
        int daily_kill = resultSet.getInt("daily_kill");
        int weekly_kill = resultSet.getInt("weekly_kill");
        int all_kill = resultSet.getInt("all_kill");
        int max_rank = resultSet.getInt("max_rank");
        int last_kill = resultSet.getInt("last_kill");
        int last_ap = resultSet.getInt("last_ap");
        long last_update = resultSet.getLong("last_update");
        
        AbyssRank abyssRank = new AbyssRank(daily_ap, weekly_ap, ap, rank, top_ranking, daily_kill, weekly_kill, all_kill, max_rank, last_kill, last_ap, last_update);
        abyssRank.setPersistentState(PersistentState.UPDATED);
        player.setAbyssRank(abyssRank);
      }
      else {
        
        AbyssRank abyssRank = new AbyssRank(0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, System.currentTimeMillis());
        abyssRank.setPersistentState(PersistentState.NEW);
        player.setAbyssRank(abyssRank);
      } 
      
      resultSet.close();
      stmt.close();
    }
    catch (SQLException e) {
      
      log.error(e);
    }
    finally {
      
      DatabaseFactory.close(con);
    } 
  }


  
  public boolean storeAbyssRank(Player player) {
    AbyssRank rank = player.getAbyssRank();
    boolean result = false;
    switch (rank.getPersistentState()) {
      
      case NEW:
        result = addRank(player.getObjectId(), rank);
        break;
      case UPDATE_REQUIRED:
        result = updateRank(player.getObjectId(), rank);
        break;
    } 
    rank.setPersistentState(PersistentState.UPDATED);
    return result;
  }






  
  private boolean addRank(int objectId, AbyssRank rank) {
    Connection con = null;

    
    try {
      con = DatabaseFactory.getConnection();
      PreparedStatement stmt = con.prepareStatement("INSERT INTO abyss_rank (player_id, daily_ap, weekly_ap, ap, rank, top_ranking, daily_kill, weekly_kill, all_kill, max_rank, last_kill, last_ap, last_update) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
      
      stmt.setInt(1, objectId);
      stmt.setInt(2, rank.getDailyAP());
      stmt.setInt(3, rank.getWeeklyAP());
      stmt.setInt(4, rank.getAp());
      stmt.setInt(5, rank.getRank().getId());
      stmt.setInt(6, rank.getTopRanking());
      stmt.setInt(7, rank.getDailyKill());
      stmt.setInt(8, rank.getWeeklyKill());
      stmt.setInt(9, rank.getAllKill());
      stmt.setInt(10, rank.getMaxRank());
      stmt.setInt(11, rank.getLastKill());
      stmt.setInt(12, rank.getLastAP());
      stmt.setLong(13, rank.getLastUpdate());
      stmt.execute();
      
      return true;
    }
    catch (SQLException e) {
      
      log.error(e);
      
      return false;
    }
    finally {
      
      DatabaseFactory.close(con);
    } 
  }






  
  private boolean updateRank(int objectId, AbyssRank rank) {
    Connection con = null;

    
    try {
      con = DatabaseFactory.getConnection();
      PreparedStatement stmt = con.prepareStatement("UPDATE abyss_rank SET  daily_ap = ?, weekly_ap = ?, ap = ?, rank = ?, top_ranking = ?, daily_kill = ?, weekly_kill = ?, all_kill = ?, max_rank = ?, last_kill = ?, last_ap = ?, last_update = ? WHERE player_id = ?");
      
      stmt.setInt(1, rank.getDailyAP());
      stmt.setInt(2, rank.getWeeklyAP());
      stmt.setInt(3, rank.getAp());
      stmt.setInt(4, rank.getRank().getId());
      stmt.setInt(5, rank.getTopRanking());
      stmt.setInt(6, rank.getDailyKill());
      stmt.setInt(7, rank.getWeeklyKill());
      stmt.setInt(8, rank.getAllKill());
      stmt.setInt(9, rank.getMaxRank());
      stmt.setInt(10, rank.getLastKill());
      stmt.setInt(11, rank.getLastAP());
      stmt.setLong(12, rank.getLastUpdate());
      stmt.setInt(13, objectId);
      stmt.execute();
      
      return true;
    }
    catch (SQLException e) {
      
      log.error(e);
      
      return false;
    }
    finally {
      
      DatabaseFactory.close(con);
    } 
  }


  
  public boolean supports(String databaseName, int majorVersion, int minorVersion) {
    return MySQL5DAOUtils.supports(databaseName, majorVersion, minorVersion);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\mysql5\MySQL5AbyssRankDAO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
