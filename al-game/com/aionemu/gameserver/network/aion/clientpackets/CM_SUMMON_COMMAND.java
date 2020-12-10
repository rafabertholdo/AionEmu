package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.controllers.SummonController;
import com.aionemu.gameserver.model.gameobjects.AionObject;
import com.aionemu.gameserver.model.gameobjects.Summon;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.world.World;

public class CM_SUMMON_COMMAND extends AionClientPacket {
  private int mode;
  private int targetObjId;

  public CM_SUMMON_COMMAND(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.mode = readC();
    readD();
    readD();
    this.targetObjId = readD();
  }

  protected void runImpl() {
    Player activePlayer = ((AionConnection) getConnection()).getActivePlayer();
    Summon summon = activePlayer.getSummon();
    if (summon != null) {
      AionObject target;
      switch (this.mode) {

        case 0:
          target = World.getInstance().findAionObject(this.targetObjId);
          if (target != null && target instanceof com.aionemu.gameserver.model.gameobjects.Creature) {
            summon.getController().attackMode();
          }
          break;
        case 1:
          summon.getController().guardMode();
          break;
        case 2:
          summon.getController().restMode();
          break;
        case 3:
          summon.getController().release(SummonController.UnsummonType.COMMAND);
          break;
      }
    }
  }
}
