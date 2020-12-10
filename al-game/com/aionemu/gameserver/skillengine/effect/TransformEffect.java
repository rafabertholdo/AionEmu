package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TRANSFORM;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.utils.PacketSendUtility;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;























@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransformEffect")
public class TransformEffect
  extends EffectTemplate
{
  @XmlAttribute
  protected int model;
  
  public void applyEffect(Effect effect) {
    Creature effected = effect.getEffected();
    boolean transformed = false;
    if (effected instanceof com.aionemu.gameserver.model.gameobjects.Npc) {
      
      transformed = (effected.getTransformedModelId() == effected.getObjectTemplate().getTemplateId());
    }
    else if (effected instanceof com.aionemu.gameserver.model.gameobjects.player.Player) {
      
      transformed = (effected.getTransformedModelId() != 0);
    } 
    if (transformed)
    {
      for (Effect tmp : effected.getEffectController().getAbnormalEffects()) {
        
        if (effect.getSkillId() == tmp.getSkillId())
          continue; 
        boolean abort = false;
        for (EffectTemplate template : tmp.getEffectTemplates()) {
          
          if (template instanceof TransformEffect) {
            
            abort = true;
            break;
          } 
        } 
        if (abort)
          tmp.endEffect(); 
      } 
    }
    effect.addToEffectedController();
  }



  
  public void calculate(Effect effect) {
    effect.addSucessEffect(this);
  }


  
  public void endEffect(Effect effect) {
    Creature effected = effect.getEffected();
    effected.getEffectController().unsetAbnormal(EffectId.SHAPECHANGE.getEffectId());
    
    if (effected instanceof com.aionemu.gameserver.model.gameobjects.Npc) {
      
      effected.setTransformedModelId(effected.getObjectTemplate().getTemplateId());
    }
    else if (effected instanceof com.aionemu.gameserver.model.gameobjects.player.Player) {
      
      effected.setTransformedModelId(0);
    } 
    PacketSendUtility.broadcastPacketAndReceive((VisibleObject)effected, (AionServerPacket)new SM_TRANSFORM(effected));
  }


  
  public void startEffect(Effect effect) {
    Creature effected = effect.getEffected();
    switch (effect.getSkillId()) {
      case 689:
      case 690:
      case 780:
      case 781:
      case 782:
      case 789:
      case 790:
      case 791:
      case 9737:
      case 9738:
      case 9739:
      case 9740:
      case 9741:
      case 9742:
      case 9743:
      case 9744:
      case 9745:
      case 9746:
        break;
      
      default:
        effected.getEffectController().setAbnormal(EffectId.SHAPECHANGE.getEffectId()); break;
    } 
    effected.setTransformedModelId(this.model);
    PacketSendUtility.broadcastPacketAndReceive((VisibleObject)effected, (AionServerPacket)new SM_TRANSFORM(effected));
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\TransformEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
