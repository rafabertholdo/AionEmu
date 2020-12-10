package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.templates.GatherableTemplate;
import com.aionemu.gameserver.model.templates.gather.Material;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;






















public class SM_GATHER_UPDATE
  extends AionServerPacket
{
  private GatherableTemplate template;
  private int action;
  private int itemId;
  private int success;
  private int failure;
  private int nameId;
  
  public SM_GATHER_UPDATE(GatherableTemplate template, Material material, int success, int failure, int action) {
    this.action = action;
    this.template = template;
    this.itemId = material.getItemid().intValue();
    this.success = success;
    this.failure = failure;
    this.nameId = material.getNameid().intValue();
  }


  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeH(buf, this.template.getSkillLevel());
    writeC(buf, this.action);
    writeD(buf, this.itemId);
    
    switch (this.action) {

      
      case 0:
        writeD(buf, this.template.getSuccessAdj());
        writeD(buf, this.template.getFailureAdj());
        writeD(buf, 0);
        writeD(buf, 1200);
        writeD(buf, 1330011);
        writeH(buf, 36);
        writeD(buf, this.nameId);
        writeH(buf, 0);
        break;

      
      case 1:
        writeD(buf, this.success);
        writeD(buf, this.failure);
        writeD(buf, 700);
        writeD(buf, 1200);
        writeD(buf, 0);
        writeH(buf, 0);
        break;

      
      case 2:
        writeD(buf, this.template.getSuccessAdj());
        writeD(buf, this.failure);
        writeD(buf, 700);
        writeD(buf, 1200);
        writeD(buf, 0);
        writeH(buf, 0);
        break;

      
      case 5:
        writeD(buf, 0);
        writeD(buf, 0);
        writeD(buf, 700);
        writeD(buf, 1200);
        writeD(buf, 1330080);
        writeH(buf, 0);
        break;

      
      case 6:
        writeD(buf, this.template.getSuccessAdj());
        writeD(buf, this.failure);
        writeD(buf, 700);
        writeD(buf, 1200);
        writeD(buf, 0);
        writeH(buf, 0);
        break;

      
      case 7:
        writeD(buf, this.success);
        writeD(buf, this.template.getFailureAdj());
        writeD(buf, 0);
        writeD(buf, 1200);
        writeD(buf, 1330079);
        writeH(buf, 36);
        writeD(buf, this.nameId);
        writeH(buf, 0);
        break;
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_GATHER_UPDATE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
