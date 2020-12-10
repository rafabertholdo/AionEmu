package mysql5;





































public class MySQL5DAOUtils
{
  public static final String MYSQL_DB_NAME = "MySQL";
  
  public static boolean supports(String db, int majorVersion, int minorVersion) {
    return ("MySQL".equals(db) && majorVersion == 5);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\mysql5\MySQL5DAOUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
