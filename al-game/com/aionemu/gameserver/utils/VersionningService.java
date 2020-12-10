package com.aionemu.gameserver.utils;

import com.aionemu.commons.utils.AEInfos;
import com.aionemu.commons.versionning.Version;
import com.aionemu.gameserver.GameServer;
import java.util.Date;
import org.apache.log4j.Logger;

public class VersionningService {
  private static final Logger log = Logger.getLogger(VersionningService.class);

  private static final VersionInfo commons = new VersionInfo(AEInfos.class);
  private static final VersionInfo game = new VersionInfo(GameServer.class);

  public static String getCommonsVersion() {
    return commons.getBuildVersion();
  }

  public static String getGameVersion() {
    return game.getBuildVersion();
  }

  public static String getCommonsRevision() {
    return commons.getBuildRevision();
  }

  public static String getGameRevision() {
    return game.getBuildRevision();
  }

  public static Date getCommonsDate() {
    return commons.getBuildDate();
  }

  public static Date getGameDate() {
    return game.getBuildDate();
  }

  private static final class VersionInfo extends Version {
    private final String version;
    private final String revision;
    private final Date buildDate;

    public VersionInfo(Class<?> c) {
      super(c);

      this.version = String.format("%-6s", new Object[] { getVersion() });
      this.revision = String.format("%-6s", new Object[] { getRevision() });
      this.buildDate = new Date(getDate());
    }

    public String getBuildVersion() {
      return this.version;
    }

    public String getBuildRevision() {
      return this.revision;
    }

    public Date getBuildDate() {
      return this.buildDate;
    }
  }

  public static String[] getFullVersionInfo() {
    return new String[] { "Commons Version: " + getCommonsVersion(), "Commons Revision: " + getCommonsRevision(),
        "Commons Build Date: " + getCommonsDate(), "GS Version: " + getGameVersion(),
        "GS Revision: " + getGameRevision(), "GS Build Date: " + getGameDate() };
  }

  public static void printFullVersionInfo() {
    for (String line : getFullVersionInfo())
      log.info(line);
  }
}
