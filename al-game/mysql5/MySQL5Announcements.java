package mysql5;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.IUStH;
import com.aionemu.commons.database.ReadStH;
import com.aionemu.gameserver.dao.AnnouncementsDAO;
import com.aionemu.gameserver.model.Announcement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;




























public class MySQL5Announcements
  extends AnnouncementsDAO
{
  public Set<Announcement> getAnnouncements() {
    final Set<Announcement> result = new HashSet<Announcement>();
    DB.select("SELECT * FROM announcements ORDER BY id", new ReadStH()
        {
          public void handleRead(ResultSet resultSet) throws SQLException
          {
            while (resultSet.next())
              result.add(new Announcement(resultSet.getInt("id"), resultSet.getString("announce"), resultSet.getString("faction"), resultSet.getString("type"), resultSet.getInt("delay"))); 
          }
        });
    return result;
  }





  
  public void addAnnouncement(final Announcement announce) {
    DB.insertUpdate("INSERT INTO announcements (announce, faction, type, delay) VALUES (?, ?, ?, ?)", new IUStH()
        {
          
          public void handleInsertUpdate(PreparedStatement preparedStatement) throws SQLException
          {
            preparedStatement.setString(1, announce.getAnnounce());
            preparedStatement.setString(2, announce.getFaction());
            preparedStatement.setString(3, announce.getType());
            preparedStatement.setInt(4, announce.getDelay());
            preparedStatement.execute();
          }
        });
  }





  
  public boolean delAnnouncement(final int idAnnounce) {
    return DB.insertUpdate("DELETE FROM announcements WHERE id = ?", new IUStH()
        {
          public void handleInsertUpdate(PreparedStatement preparedStatement) throws SQLException
          {
            preparedStatement.setInt(1, idAnnounce);
            preparedStatement.execute();
          }
        });
  }





  
  public boolean supports(String s, int i, int i1) {
    return MySQL5DAOUtils.supports(s, i, i1);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\mysql5\MySQL5Announcements.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
