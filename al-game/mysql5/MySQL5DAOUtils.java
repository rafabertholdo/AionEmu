package mysql5;

public class MySQL5DAOUtils {
  public static final String MYSQL_DB_NAME = "MySQL";

  public static boolean supports(String db, int majorVersion, int minorVersion) {
    return ("MySQL".equals(db) && majorVersion == 5);
  }
}
