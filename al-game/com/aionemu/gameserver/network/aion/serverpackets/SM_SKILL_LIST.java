package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.SkillListEntry;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

































public class SM_SKILL_LIST
  extends AionServerPacket
{
  private SkillListEntry[] skillList;
  private int messageId;
  private int skillNameId;
  private String skillLvl;
  public static final int YOU_LEARNED_SKILL = 1300050;
  
  public SM_SKILL_LIST(Player player) {
    this.skillList = player.getSkillList().getAllSkills();
    this.messageId = 0;
  }

  
  public SM_SKILL_LIST(SkillListEntry skillListEntry, int messageId) {
    this.skillList = new SkillListEntry[] { skillListEntry };
    this.messageId = messageId;
    this.skillNameId = DataManager.SKILL_DATA.getSkillTemplate(skillListEntry.getSkillId()).getNameId();
    this.skillLvl = String.valueOf(skillListEntry.getSkillLevel());
  }






  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    int size = this.skillList.length;
    writeH(buf, size);
    
    if (size > 0)
    {
      for (SkillListEntry entry : this.skillList) {
        
        writeH(buf, entry.getSkillId());
        writeH(buf, entry.getSkillLevel());
        writeC(buf, 0);
        writeC(buf, entry.getExtraLvl());
        writeD(buf, 0);
        writeC(buf, entry.isStigma() ? 1 : 0);
      } 
    }
    writeD(buf, this.messageId);
    if (this.messageId != 0);
    
    writeH(buf, 36);
    writeD(buf, this.skillNameId);
    writeH(buf, 0);
    writeS(buf, this.skillLvl);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_SKILL_LIST.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
