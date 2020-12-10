package com.aionemu.commons.database;

import com.aionemu.commons.configuration.Property;
import java.io.File;

public class DatabaseConfig {
  @Property(key = "database.url", defaultValue = "jdbc:mysql://localhost:3306/Aion_DB")
  public static String DATABASE_URL;
  
  @Property(key = "database.driver", defaultValue = "com.mysql.jdbc.Driver")
  public static Class<?> DATABASE_DRIVER;
  
  @Property(key = "database.user", defaultValue = "root")
  public static String DATABASE_USER;
  
  @Property(key = "database.password", defaultValue = "root")
  public static String DATABASE_PASSWORD;
  
  @Property(key = "database.connections.min", defaultValue = "2")
  public static int DATABASE_CONNECTIONS_MIN;
  
  @Property(key = "database.connections.max", defaultValue = "10")
  public static int DATABASE_CONNECTIONS_MAX;
  
  @Property(key = "database.scriptcontext.descriptor", defaultValue = "./data/scripts/system/database/database.xml")
  public static File DATABASE_SCRIPTCONTEXT_DESCRIPTOR;
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\database\DatabaseConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
