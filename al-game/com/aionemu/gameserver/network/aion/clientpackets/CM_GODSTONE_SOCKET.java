package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.world.World;

public class CM_GODSTONE_SOCKET extends AionClientPacket {
  private int npcId;
  private int weaponId;
  private int stoneId;

  public CM_GODSTONE_SOCKET(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.npcId = readD();
    this.weaponId = readD();
    this.stoneId = readD();
  }

  protected void runImpl() {
    Player activePlayer = ((AionConnection) getConnection()).getActivePlayer();

    Npc npc = (Npc) World.getInstance().findAionObject(this.npcId);
    if (npc == null) {
      return;
    }
    if (!MathUtil.isInRange((VisibleObject) activePlayer, (VisibleObject) npc, 15.0F)) {
      return;
    }
    ItemService.socketGodstone(activePlayer, this.weaponId, this.stoneId);
  }
}
