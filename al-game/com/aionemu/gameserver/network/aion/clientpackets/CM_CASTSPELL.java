package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.skillengine.model.SkillTemplate;










public class CM_CASTSPELL
  extends AionClientPacket
{
  private int spellid;
  private int targetType;
  private float x;
  private float y;
  private float z;
  private int targetObjectId;
  private int time;
  private int level;
  
  public CM_CASTSPELL(int opcode) {
    super(opcode);
  }





  
  protected void readImpl() {
    this.spellid = readH();
    this.level = readC();
    
    this.targetType = readC();
    
    switch (this.targetType) {
      
      case 0:
        this.targetObjectId = readD();
        break;
      case 1:
        this.x = readF();
        this.y = readF();
        this.z = readF();
        break;
    } 


    
    this.time = readH();
  }





  
  protected void runImpl() {
    Player player = ((AionConnection)getConnection()).getActivePlayer();
    
    if (player.isProtectionActive())
    {
      player.getController().stopProtectionActiveTask();
    }
    
    if (!player.getLifeStats().isAlreadyDead()) {
      
      SkillTemplate template = DataManager.SKILL_DATA.getSkillTemplate(this.spellid);
      if (template == null || template.isPassive())
        return; 
      if (!player.getSkillList().isSkillPresent(this.spellid))
        return; 
      player.getController().useSkill(this.spellid, this.targetType, this.x, this.y, this.z);
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_CASTSPELL.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
