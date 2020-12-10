package com.aionemu.gameserver.model.gameobjects.stats;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.stats.modifiers.StatModifier;
import com.aionemu.gameserver.model.items.ItemSlot;
import com.aionemu.gameserver.model.items.NpcEquippedGear;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.model.templates.stats.NpcStatsTemplate;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.TreeSet;


















public class NpcGameStats
  extends CreatureGameStats<Npc>
{
  int currentRunSpeed = 0;

  
  public NpcGameStats(Npc owner) {
    super(owner);
    
    NpcStatsTemplate nst = owner.getObjectTemplate().getStatsTemplate();
    
    initStat(StatEnum.MAXHP, nst.getMaxHp() + Math.round(owner.getObjectTemplate().getHpGauge() * 1.5F * owner.getLevel()));
    
    initStat(StatEnum.MAXMP, nst.getMaxMp());

    
    initStat(StatEnum.ATTACK_SPEED, 2000);
    initStat(StatEnum.PHYSICAL_DEFENSE, Math.round((nst.getPdef() / owner.getLevel() - 1.0F) * nst.getPdef() + (10 * owner.getLevel())));
    
    initStat(StatEnum.EVASION, Math.round(nst.getEvasion() * 2.3F + (owner.getLevel() * 10)));
    initStat(StatEnum.MAGICAL_RESIST, Math.round(nst.getMdef()));
    initStat(StatEnum.MAIN_HAND_POWER, nst.getPower());
    initStat(StatEnum.MAIN_HAND_ACCURACY, Math.round(nst.getAccuracy() * 2.3F + (owner.getLevel() * 10)));
    initStat(StatEnum.MAIN_HAND_CRITICAL, Math.round(nst.getCrit()));
    initStat(StatEnum.SPEED, Math.round(nst.getRunSpeedFight() * 1000.0F));
    
    initStat(StatEnum.MAGICAL_ACCURACY, 1500);
    initStat(StatEnum.BOOST_MAGICAL_SKILL, 1000);
    
    initStatsFromEquipment(owner);
  }






  
  private void initStatsFromEquipment(Npc owner) {
    NpcEquippedGear equipment = owner.getObjectTemplate().getEquipment();
    if (equipment != null) {
      
      equipment.init();
      
      ItemTemplate itemTemplate = equipment.getItem(ItemSlot.MAIN_HAND);
      if (itemTemplate != null) {
        
        TreeSet<StatModifier> modifiers = itemTemplate.getModifiers();
        if (modifiers != null)
        {
          for (StatModifier modifier : modifiers) {
            
            if (modifier.getStat() == StatEnum.ATTACK_RANGE) {
              initStat(StatEnum.ATTACK_RANGE, modifier.apply(0, 0));
            }
          } 
        }
      } 
    } 


    
    if (getCurrentStat(StatEnum.ATTACK_RANGE) == 0) {
      initStat(StatEnum.ATTACK_RANGE, 2000);
    }
  }

  
  public void recomputeStats() {
    super.recomputeStats();
    
    int newRunSpeed = getCurrentStat(StatEnum.SPEED);
    
    if (newRunSpeed != this.currentRunSpeed) {
      
      this.owner.getMoveController().setSpeed(newRunSpeed / 1000.0F);
      PacketSendUtility.broadcastPacket((VisibleObject)this.owner, (AionServerPacket)new SM_EMOTION((Creature)this.owner, EmotionType.START_EMOTE2, 0, 0));
    } 
    this.currentRunSpeed = newRunSpeed;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\stats\NpcGameStats.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
