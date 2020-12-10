package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.AionObject;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Summon;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.world.World;























public class CM_SUMMON_CASTSPELL
  extends AionClientPacket
{
  private int summonObjId;
  private int targetObjId;
  private int skillId;
  private int skillLvl;
  private float unk;
  
  public CM_SUMMON_CASTSPELL(int opcode) {
    super(opcode);
  }


  
  protected void readImpl() {
    this.summonObjId = readD();
    this.skillId = readH();
    this.skillLvl = readC();
    this.targetObjId = readD();
    this.unk = readF();
  }


  
  protected void runImpl() {
    Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
    Summon summon = activePlayer.getSummon();
    
    if (summon == null) {
      return;
    }
    AionObject targetObject = World.getInstance().findAionObject(this.targetObjId);
    if (targetObject instanceof Creature)
    {
      summon.getController().useSkill(this.skillId, (Creature)targetObject);
    }
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_SUMMON_CASTSPELL.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
