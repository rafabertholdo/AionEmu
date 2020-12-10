package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.model.gameobjects.player.FriendList;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_SEARCH;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.world.World;
import java.util.ArrayList;
import java.util.List;

public class CM_PLAYER_SEARCH extends AionClientPacket {
  public static final int MAX_RESULTS = 124;
  private String name;
  private int region;
  private int classMask;
  private int minLevel;
  private int maxLevel;
  private int lfgOnly;

  public CM_PLAYER_SEARCH(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    if (!(this.name = readS()).isEmpty()) {

      this.name = Util.convertName(this.name);
      readB(44 - this.name.length() * 2 + 2);
    } else {

      readB(42);
    }
    this.region = readD();
    this.classMask = readD();
    this.minLevel = readC();
    this.maxLevel = readC();
    this.lfgOnly = readC();
    readC();
  }

  protected void runImpl() {
    Player activePlayer = ((AionConnection) getConnection()).getActivePlayer();

    List<Player> matches = new ArrayList<Player>(124);

    if (activePlayer != null && activePlayer.getLevel() < 10) {

      sendPacket((AionServerPacket) SM_SYSTEM_MESSAGE.LEVEL_NOT_ENOUGH_FOR_SEARCH("10"));
      return;
    }
    for (Player player : World.getInstance().getAllPlayers()) {

      if (matches.size() > 124)
        return;
      if (!player.isSpawned())
        continue;
      if (player.getFriendList().getStatus() == FriendList.Status.OFFLINE)
        continue;
      if (this.lfgOnly == 1 && !player.isLookingForGroup())
        continue;
      if (!this.name.isEmpty() && !player.getName().toLowerCase().contains(this.name.toLowerCase()))
        continue;
      if (this.minLevel != 255 && player.getLevel() < this.minLevel)
        continue;
      if (this.maxLevel != 255 && player.getLevel() > this.maxLevel)
        continue;
      if (this.classMask > 0 && (player.getPlayerClass().getMask() & this.classMask) == 0)
        continue;
      if (this.region > 0 && player.getActiveRegion().getMapId().intValue() != this.region)
        continue;
      if (player.getCommonData().getRace() != activePlayer.getCommonData().getRace()
          && !CustomConfig.FACTIONS_SEARCH_MODE) {
        continue;
      }

      matches.add(player);
    }

    sendPacket((AionServerPacket) new SM_PLAYER_SEARCH(matches, this.region));
  }
}
