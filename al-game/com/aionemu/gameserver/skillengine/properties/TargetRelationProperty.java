package com.aionemu.gameserver.skillengine.properties;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.skillengine.model.Skill;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;




























@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TargetRelationProperty")
public class TargetRelationProperty
  extends Property
{
  @XmlAttribute(required = true)
  protected TargetRelationAttribute value;
  
  public TargetRelationAttribute getValue() {
    return this.value;
  }

  
  public boolean set(Skill skill) {
    Iterator<Creature> iter;
    List<Creature> effectedList = skill.getEffectedList();
    Creature effector = skill.getEffector();
    
    switch (this.value) {


      
      case ENEMY:
        for (iter = effectedList.iterator(); iter.hasNext(); ) {
          
          Creature nextEffected = iter.next();
          
          if (effector.isEnemy((VisibleObject)nextEffected)) {
            continue;
          }
          iter.remove();
        } 
        break;
      case FRIEND:
        for (iter = effectedList.iterator(); iter.hasNext(); ) {
          
          Creature nextEffected = iter.next();
          
          if (!effector.isEnemy((VisibleObject)nextEffected)) {
            continue;
          }
          iter.remove();
        } 
        
        if (effectedList.size() == 0) {
          
          skill.setFirstTarget(skill.getEffector());
          effectedList.add(skill.getEffector());
          
          break;
        } 
        skill.setFirstTarget(effectedList.get(0));
        break;
    } 

    
    return true;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\properties\TargetRelationProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
