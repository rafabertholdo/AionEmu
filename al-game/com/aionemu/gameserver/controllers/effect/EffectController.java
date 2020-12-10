package com.aionemu.gameserver.controllers.effect;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ABNORMAL_EFFECT;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ABNORMAL_STATE;
import com.aionemu.gameserver.skillengine.effect.EffectId;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.SkillTargetSlot;
import com.aionemu.gameserver.skillengine.model.SkillType;
import com.aionemu.gameserver.taskmanager.tasks.PacketBroadcaster;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javolution.util.FastMap;






















public class EffectController
{
  private Creature owner;
  protected Map<String, Effect> passiveEffectMap = (Map<String, Effect>)(new FastMap()).shared();
  protected Map<String, Effect> noshowEffects = (Map<String, Effect>)(new FastMap()).shared();
  protected Map<String, Effect> abnormalEffectMap = (Map<String, Effect>)(new FastMap()).shared();
  
  protected int abnormals;

  
  public EffectController(Creature owner) {
    this.owner = owner;
  }




  
  public Creature getOwner() {
    return this.owner;
  }





  
  public void addEffect(Effect effect) {
    Map<String, Effect> mapToUpdate = getMapForEffect(effect);
    
    Effect existingEffect = mapToUpdate.get(effect.getStack());
    if (existingEffect != null) {

      
      if (existingEffect.getSkillStackLvl() > effect.getSkillStackLvl()) {
        return;
      }
      if (existingEffect.getSkillStackLvl() == effect.getSkillStackLvl() && existingEffect.getSkillLevel() > effect.getSkillLevel()) {
        return;
      }
      
      existingEffect.endEffect();
    } 
    
    if (effect.isToggle() && mapToUpdate.size() >= 3) {
      
      Iterator<Effect> iter = mapToUpdate.values().iterator();
      Effect nextEffect = iter.next();
      nextEffect.endEffect();
      iter.remove();
    } 
    
    mapToUpdate.put(effect.getStack(), effect);
    effect.startEffect(false);
    
    if (!effect.isPassive())
    {
      broadCastEffects();
    }
  }






  
  private Map<String, Effect> getMapForEffect(Effect effect) {
    if (effect.isPassive()) {
      return this.passiveEffectMap;
    }
    if (effect.isToggle()) {
      return this.noshowEffects;
    }
    return this.abnormalEffectMap;
  }






  
  public Effect getAnormalEffect(String stack) {
    return this.abnormalEffectMap.get(stack);
  }

  
  public void broadCastEffects() {
    this.owner.addPacketBroadcastMask(PacketBroadcaster.BroadcastMode.BROAD_CAST_EFFECTS);
  }




  
  public void broadCastEffectsImp() {
    List<Effect> effects = getAbnormalEffects();
    PacketSendUtility.broadcastPacket((VisibleObject)getOwner(), (AionServerPacket)new SM_ABNORMAL_EFFECT(getOwner().getObjectId(), this.abnormals, effects));
  }







  
  public void sendEffectIconsTo(Player player) {
    List<Effect> effects = getAbnormalEffects();
    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_ABNORMAL_EFFECT(getOwner().getObjectId(), this.abnormals, effects));
  }






  
  public void clearEffect(Effect effect) {
    this.abnormalEffectMap.remove(effect.getStack());
    broadCastEffects();
  }






  
  public void removeEffect(int skillid) {
    for (Effect effect : this.abnormalEffectMap.values()) {
      if (effect.getSkillId() == skillid) {
        effect.endEffect();
        this.abnormalEffectMap.remove(effect.getStack());
      } 
    } 
  }






  
  public void removeEffectBySetNumber(int setNumber) {
    for (Effect effect : this.abnormalEffectMap.values()) {
      
      if (effect.getSkillSetException() == setNumber) {
        
        effect.endEffect();
        this.abnormalEffectMap.remove(effect.getStack());
      } 
    } 
    for (Effect effect : this.passiveEffectMap.values()) {
      
      if (effect.getSkillSetException() == setNumber) {
        
        effect.endEffect();
        this.passiveEffectMap.remove(effect.getStack());
      } 
    } 
    for (Effect effect : this.noshowEffects.values()) {
      
      if (effect.getSkillSetException() == setNumber) {
        
        effect.endEffect();
        this.noshowEffects.remove(effect.getStack());
      } 
    } 
  }





  
  public void removeEffectWithSetNumberReserved() {
    removeEffectBySetNumber(1);
  }





  
  public void removeEffectByEffectId(int effectId) {
    for (Effect effect : this.abnormalEffectMap.values()) {
      if (effect.containsEffectId(effectId)) {
        effect.endEffect();
        this.abnormalEffectMap.remove(effect.getStack());
      } 
    } 
  }






  
  public void removeEffectByTargetSlot(SkillTargetSlot targetSlot, int count) {
    for (Effect effect : this.abnormalEffectMap.values()) {
      
      if (count == 0) {
        break;
      }
      if (effect.getTargetSlot() == targetSlot.ordinal()) {
        
        effect.endEffect();
        this.abnormalEffectMap.remove(effect.getStack());
        count--;
      } 
    } 
  }





  
  public void removeEffectBySkillType(SkillType skillType, int value) {
    for (Effect effect : this.abnormalEffectMap.values()) {
      
      if (value == 0) {
        break;
      }
      if (effect.getSkillType() == skillType) {
        
        effect.endEffect();
        this.abnormalEffectMap.remove(effect.getStack());
        value--;
      } 
    } 
  }







  
  public void removeEffectBySkillTypeAndTargetSlot(SkillType skillType, SkillTargetSlot targetSlot, int value) {
    for (Effect effect : this.abnormalEffectMap.values()) {
      
      if (value == 0) {
        break;
      }
      if (effect.getSkillType() == skillType && effect.getTargetSlot() == targetSlot.ordinal()) {
        
        effect.endEffect();
        this.abnormalEffectMap.remove(effect.getStack());
        value--;
      } 
    } 
  }






  
  public void removePassiveEffect(int skillid) {
    for (Effect effect : this.passiveEffectMap.values()) {
      if (effect.getSkillId() == skillid) {
        effect.endEffect();
        this.passiveEffectMap.remove(effect.getStack());
      } 
    } 
  }





  
  public void removeNoshowEffect(int skillid) {
    for (Effect effect : this.noshowEffects.values()) {
      if (effect.getSkillId() == skillid) {
        effect.endEffect();
        this.noshowEffects.remove(effect.getStack());
      } 
    } 
  }





  
  public void removeAbnormalEffectsByTargetSlot(SkillTargetSlot targetSlot) {
    for (Effect effect : this.abnormalEffectMap.values()) {
      if (effect.getTargetSlot() == targetSlot.ordinal()) {
        effect.endEffect();
        this.abnormalEffectMap.remove(effect.getStack());
      } 
    } 
  }




  
  public void removeAllEffects() {
    for (Effect effect : this.abnormalEffectMap.values())
    {
      effect.endEffect();
    }
    this.abnormalEffectMap.clear();
    for (Effect effect : this.noshowEffects.values())
    {
      effect.endEffect();
    }
    this.noshowEffects.clear();
  }

  
  public void updatePlayerEffectIcons() {
    getOwner().addPacketBroadcastMask(PacketBroadcaster.BroadcastMode.UPDATE_PLAYER_EFFECT_ICONS);
  }

  
  public void updatePlayerEffectIconsImpl() {
    List<Effect> effects = getAbnormalEffects();
    
    PacketSendUtility.sendPacket((Player)this.owner, (AionServerPacket)new SM_ABNORMAL_STATE(effects, this.abnormals));
  }





  
  public List<Effect> getAbnormalEffects() {
    List<Effect> effects = new ArrayList<Effect>();
    Iterator<Effect> iterator = iterator();
    while (iterator.hasNext()) {
      
      Effect effect = iterator.next();
      if (effect != null)
        effects.add(effect); 
    } 
    return effects;
  }





  
  public void setAbnormal(int mask) {
    this.abnormals |= mask;
  }

  
  public void unsetAbnormal(int mask) {
    this.abnormals &= mask ^ 0xFFFFFFFF;
  }







  
  public boolean isAbnoramlSet(EffectId effectId) {
    return ((this.abnormals & effectId.getEffectId()) == effectId.getEffectId());
  }







  
  public boolean isAbnormalState(EffectId effectId) {
    int state = this.abnormals & effectId.getEffectId();
    return (state > 0 && state <= effectId.getEffectId());
  }

  
  public int getAbnormals() {
    return this.abnormals;
  }





  
  public Iterator<Effect> iterator() {
    return this.abnormalEffectMap.values().iterator();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\effect\EffectController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
