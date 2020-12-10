package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.state.CreatureSeeState;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_STATE;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.utils.PacketSendUtility;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;






















@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchEffect")
public class SearchEffect
  extends EffectTemplate
{
  @XmlAttribute
  protected int value;
  
  public void applyEffect(Effect effect) {
    effect.addToEffectedController();
  }


  
  public void calculate(Effect effect) {
    effect.addSucessEffect(this);
  }

  
  public void endEffect(Effect effect) {
    CreatureSeeState seeState;
    Creature effected = effect.getEffected();


    
    switch (this.value) {
      
      case 1:
        seeState = CreatureSeeState.SEARCH1;
        break;
      case 2:
        seeState = CreatureSeeState.SEARCH2;
        break;
      default:
        seeState = CreatureSeeState.NORMAL;
        break;
    } 
    effected.unsetSeeState(seeState);
    
    if (effected instanceof Player)
    {
      PacketSendUtility.broadcastPacket((Player)effected, (AionServerPacket)new SM_PLAYER_STATE((Player)effected), true);
    }
  }

  
  public void startEffect(Effect effect) {
    CreatureSeeState seeState;
    Creature effected = effect.getEffected();


    
    switch (this.value) {
      
      case 1:
        seeState = CreatureSeeState.SEARCH1;
        break;
      case 2:
        seeState = CreatureSeeState.SEARCH2;
        break;
      default:
        seeState = CreatureSeeState.NORMAL;
        break;
    } 
    effected.setSeeState(seeState);
    
    if (effected instanceof Player)
    {
      PacketSendUtility.broadcastPacket((Player)effected, (AionServerPacket)new SM_PLAYER_STATE((Player)effected), true);
    }
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\SearchEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
