package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.configs.main.SiegeConfig;
import com.aionemu.gameserver.model.siege.SiegeLocation;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.services.SiegeService;
import java.nio.ByteBuffer;
import javolution.util.FastMap;

public class SM_SIEGE_LOCATION_INFO extends AionServerPacket {
  private int infoType;
  private FastMap<Integer, SiegeLocation> locations;

  public SM_SIEGE_LOCATION_INFO() {
    this.infoType = 0;
    this.locations = SiegeService.getInstance().getSiegeLocations();
  }

  public SM_SIEGE_LOCATION_INFO(SiegeLocation loc) {
    this.infoType = 1;
    this.locations = new FastMap();
    this.locations.put(Integer.valueOf(loc.getLocationId()), loc);
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    if (!SiegeConfig.SIEGE_ENABLED) {

      writeC(buf, 0);
      writeH(buf, 0);

      return;
    }
    writeC(buf, this.infoType);
    writeH(buf, this.locations.size());
    FastMap.Entry<Integer, SiegeLocation> e = this.locations.head(), end = this.locations.tail();
    while ((e = e.getNext()) != end) {

      SiegeLocation sLoc = (SiegeLocation) e.getValue();

      writeD(buf, sLoc.getLocationId());
      writeD(buf, sLoc.getLegionId());

      writeD(buf, 0);
      writeD(buf, 0);

      writeC(buf, sLoc.getRace().getRaceId());

      writeC(buf, sLoc.isVulnerable() ? 2 : 0);

      writeC(buf, 1);

      writeC(buf, sLoc.getNextState());

      writeD(buf, 0);
      writeD(buf, 0);
    }
  }
}
