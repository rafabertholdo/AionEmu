package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.alliance.PlayerAllianceMember;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MANTRA_EFFECT;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.SkillTemplate;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import java.util.concurrent.Future;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;





















@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuraEffect")
public class AuraEffect
  extends EffectTemplate
{
  @XmlAttribute
  protected int distance;
  @XmlAttribute(name = "skill_id")
  protected int skillId;
  
  public void applyEffect(Effect effect) {
    effect.addToEffectedController();
  }


  
  public void calculate(Effect effect) {
    effect.addSucessEffect(this);
  }


  
  public void onPeriodicAction(Effect effect) {
    Player effector = (Player)effect.getEffector();
    if (effector.isInAlliance()) {
      
      for (PlayerAllianceMember allianceMember : effector.getPlayerAlliance().getMembersForGroup(effector.getObjectId()))
      {
        if (allianceMember.isOnline() && MathUtil.isIn3dRange((VisibleObject)effector, (VisibleObject)allianceMember.getPlayer(), (this.distance + 4)))
        {
          applyAuraTo(allianceMember.getPlayer());
        }
      }
    
    } else if (effector.isInGroup()) {
      
      for (Player member : effector.getPlayerGroup().getMembers())
      {
        if (MathUtil.isIn3dRange((VisibleObject)effector, (VisibleObject)member, (this.distance + 4)))
        {
          applyAuraTo(member);
        }
      }
    
    } else {
      
      applyAuraTo(effector);
    } 
  }





  
  private void applyAuraTo(Player effector) {
    SkillTemplate template = DataManager.SKILL_DATA.getSkillTemplate(this.skillId);
    Effect e = new Effect((Creature)effector, (Creature)effector, template, template.getLvl(), template.getEffectsDuration());
    e.initialize();
    e.applyEffect();
    PacketSendUtility.broadcastPacket((VisibleObject)effector, (AionServerPacket)new SM_MANTRA_EFFECT(effector, this.skillId));
  }


  
  public void startEffect(final Effect effect) {
    Future<?> task = ThreadPoolManager.getInstance().scheduleEffectAtFixedRate(new Runnable()
        {
          
          public void run()
          {
            AuraEffect.this.onPeriodicAction(effect);
          }
        },  0L, 6500L);
    effect.setPeriodicTask(task, this.position);
  }
  
  public void endEffect(Effect effect) {}
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\AuraEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
