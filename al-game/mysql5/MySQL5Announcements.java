/*    */ package mysql5;
/*    */ 
/*    */ import com.aionemu.commons.database.DB;
/*    */ import com.aionemu.commons.database.IUStH;
/*    */ import com.aionemu.commons.database.ReadStH;
/*    */ import com.aionemu.gameserver.dao.AnnouncementsDAO;
/*    */ import com.aionemu.gameserver.model.Announcement;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MySQL5Announcements
/*    */   extends AnnouncementsDAO
/*    */ {
/*    */   public Set<Announcement> getAnnouncements() {
/* 45 */     final Set<Announcement> result = new HashSet<Announcement>();
/* 46 */     DB.select("SELECT * FROM announcements ORDER BY id", new ReadStH()
/*    */         {
/*    */           public void handleRead(ResultSet resultSet) throws SQLException
/*    */           {
/* 50 */             while (resultSet.next())
/* 51 */               result.add(new Announcement(resultSet.getInt("id"), resultSet.getString("announce"), resultSet.getString("faction"), resultSet.getString("type"), resultSet.getInt("delay"))); 
/*    */           }
/*    */         });
/* 54 */     return result;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addAnnouncement(final Announcement announce) {
/* 63 */     DB.insertUpdate("INSERT INTO announcements (announce, faction, type, delay) VALUES (?, ?, ?, ?)", new IUStH()
/*    */         {
/*    */           
/*    */           public void handleInsertUpdate(PreparedStatement preparedStatement) throws SQLException
/*    */           {
/* 68 */             preparedStatement.setString(1, announce.getAnnounce());
/* 69 */             preparedStatement.setString(2, announce.getFaction());
/* 70 */             preparedStatement.setString(3, announce.getType());
/* 71 */             preparedStatement.setInt(4, announce.getDelay());
/* 72 */             preparedStatement.execute();
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean delAnnouncement(final int idAnnounce) {
/* 83 */     return DB.insertUpdate("DELETE FROM announcements WHERE id = ?", new IUStH()
/*    */         {
/*    */           public void handleInsertUpdate(PreparedStatement preparedStatement) throws SQLException
/*    */           {
/* 87 */             preparedStatement.setInt(1, idAnnounce);
/* 88 */             preparedStatement.execute();
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean supports(String s, int i, int i1) {
/* 99 */     return MySQL5DAOUtils.supports(s, i, i1);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\mysql5\MySQL5Announcements.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */