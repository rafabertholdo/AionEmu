package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.skillengine.model.Effect;
import java.nio.ByteBuffer;
import java.util.List;

























public class SM_CASTSPELL_END
  extends AionServerPacket
{
  private Creature attacker;
  private Creature target;
  private int spellid;
  private int level;
  private int cooldown;
  private List<Effect> effects;
  private int spellStatus;
  private float x;
  private float y;
  private float z;
  private int targetType;
  private boolean chainSuccess;
  
  public SM_CASTSPELL_END(Creature attacker, Creature target, List<Effect> effects, int spellid, int level, int cooldown, boolean chainSuccess, int spellStatus) {
    this.attacker = attacker;
    this.target = target;
    this.spellid = spellid;
    this.level = level;
    this.effects = effects;
    this.cooldown = cooldown;
    this.spellStatus = spellStatus;
    this.chainSuccess = chainSuccess;
    this.targetType = 0;
  }


  
  public SM_CASTSPELL_END(Creature attacker, Creature target, List<Effect> effects, int spellid, int level, int cooldown, boolean chainSuccess, int spellStatus, float x, float y, float z) {
    this(attacker, target, effects, spellid, level, cooldown, chainSuccess, spellStatus);
    this.x = x;
    this.y = y;
    this.z = z;
    this.targetType = 1;
  }






  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.attacker.getObjectId());
    writeC(buf, this.targetType);
    switch (this.targetType) {
      
      case 0:
        writeD(buf, this.target.getObjectId());
        break;
      case 1:
        writeF(buf, this.x);
        writeF(buf, this.y);
        writeF(buf, this.z + 0.4F);
        break;
    } 
    writeH(buf, this.spellid);
    writeC(buf, this.level);
    writeD(buf, this.cooldown);
    writeH(buf, 560);
    writeC(buf, 0);





    
    if (this.chainSuccess) {
      writeH(buf, 32);
    } else {
      writeH(buf, 0);
    } 






    
    writeC(buf, 0);















    
    writeH(buf, this.effects.size());
    for (Effect effect : this.effects) {
      
      writeD(buf, effect.getEffected().getObjectId());
      writeC(buf, 0);
      
      int attackerMaxHp = this.attacker.getLifeStats().getMaxHp();
      int attackerCurrHp = this.attacker.getLifeStats().getCurrentHp();
      int targetMaxHp = this.target.getLifeStats().getMaxHp();
      int targetCurrHp = this.target.getLifeStats().getCurrentHp();
      
      writeC(buf, 100 * targetCurrHp / targetMaxHp);
      writeC(buf, 100 * attackerCurrHp / attackerMaxHp);














      
      writeC(buf, this.spellStatus);
      
      switch (this.spellStatus) {
        
        case 1:
        case 2:
        case 4:
        case 8:
          writeF(buf, this.target.getX());
          writeF(buf, this.target.getY());
          writeF(buf, this.target.getZ() + 0.4F);
          break;
        case 16:
          writeC(buf, this.target.getHeading());
          break;
      } 


      
      writeC(buf, 16);
      writeC(buf, 0);
      
      writeC(buf, 1);
      writeC(buf, 0);
      writeD(buf, effect.getReserved1());
      writeC(buf, effect.getAttackStatus().getId());
      writeC(buf, effect.getShieldDefense());
      
      switch (effect.getShieldDefense()) {
        
        case 1:
          writeD(buf, 0);
          writeD(buf, 0);
          writeD(buf, 0);
          writeD(buf, 0);
          writeD(buf, 0);
      } 
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_CASTSPELL_END.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
