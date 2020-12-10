package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.controllers.attack.AttackResult;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;
import java.util.List;

public class SM_ATTACK extends AionServerPacket {
  private int attackno;
  private int time;
  private int type;
  private List<AttackResult> attackList;
  private Creature attacker;
  private Creature target;

  public SM_ATTACK(Creature attacker, Creature target, int attackno, int time, int type,
      List<AttackResult> attackList) {
    this.attacker = attacker;
    this.target = target;
    this.attackno = attackno;
    this.time = time;
    this.type = type;
    this.attackList = attackList;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.attacker.getObjectId());
    writeC(buf, this.attackno);
    writeH(buf, this.time);
    writeC(buf, this.type);
    writeD(buf, this.target.getObjectId());

    int attackerMaxHp = this.attacker.getLifeStats().getMaxHp();
    int attackerCurrHp = this.attacker.getLifeStats().getCurrentHp();
    int targetMaxHp = this.target.getLifeStats().getMaxHp();
    int targetCurrHp = this.target.getLifeStats().getCurrentHp();

    writeC(buf, 100 * targetCurrHp / targetMaxHp);
    writeC(buf, 100 * attackerCurrHp / attackerMaxHp);

    switch (((AttackResult) this.attackList.get(0)).getAttackStatus().getId()) {

      case -60:
      case 4:
        writeH(buf, 32);
        break;
      case -62:
      case 2:
        writeH(buf, 64);
        break;
      case -64:
      case 0:
        writeH(buf, 128);
        break;
      case -58:
      case 6:
        writeH(buf, 256);
        break;
      default:
        writeH(buf, 0);
        break;
    }

    writeC(buf, this.attackList.size());
    for (AttackResult attack : this.attackList) {

      writeD(buf, attack.getDamage());
      writeC(buf, attack.getAttackStatus().getId());
      writeC(buf, attack.getShieldType());

      switch (attack.getShieldType()) {

        case 1:
          writeD(buf, 0);
          writeD(buf, 0);
          writeD(buf, 0);
          writeD(buf, 0);
          writeD(buf, 0);
      }

    }
    writeC(buf, 0);
  }
}
