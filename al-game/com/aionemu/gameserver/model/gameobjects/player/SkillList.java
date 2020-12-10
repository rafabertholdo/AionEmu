package com.aionemu.gameserver.model.gameobjects.player;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.templates.item.ArmorType;
import com.aionemu.gameserver.model.templates.item.WeaponType;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SKILL_LIST;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SKILL_REMOVE;
import com.aionemu.gameserver.skillengine.effect.ArmorMasteryEffect;
import com.aionemu.gameserver.skillengine.effect.EffectTemplate;
import com.aionemu.gameserver.skillengine.effect.WeaponMasteryEffect;
import com.aionemu.gameserver.skillengine.model.SkillTemplate;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;



























public class SkillList
{
  private static final Logger logger = Logger.getLogger(SkillList.class);
  
  public static final String[] split = null;


  
  private final Map<Integer, SkillListEntry> skills;


  
  private final List<SkillListEntry> deletedSkills;


  
  private final Map<WeaponType, Integer> weaponMasterySkills = new HashMap<WeaponType, Integer>();


  
  private final Map<ArmorType, Integer> armorMasterySkills = new HashMap<ArmorType, Integer>();




  
  public SkillList() {
    this.skills = new HashMap<Integer, SkillListEntry>();
    this.deletedSkills = new ArrayList<SkillListEntry>();
  }





  
  public SkillList(Map<Integer, SkillListEntry> arg) {
    this.skills = arg;
    this.deletedSkills = new ArrayList<SkillListEntry>();
    calculateUsedWeaponMasterySkills();
    calculateUsedArmorMasterySkills();
  }





  
  public SkillListEntry[] getAllSkills() {
    return (SkillListEntry[])this.skills.values().toArray((Object[])new SkillListEntry[this.skills.size()]);
  }





  
  public SkillListEntry[] getDeletedSkills() {
    return this.deletedSkills.<SkillListEntry>toArray(new SkillListEntry[this.deletedSkills.size()]);
  }





  
  public SkillListEntry getSkillEntry(int skillId) {
    return this.skills.get(Integer.valueOf(skillId));
  }






  
  public synchronized boolean addSkill(Player player, int skillId, int skillLevel, boolean msg) {
    SkillListEntry existingSkill = this.skills.get(Integer.valueOf(skillId));
    if (existingSkill != null) {
      
      if (existingSkill.getSkillLevel() >= skillLevel)
      {
        return false;
      }
      existingSkill.setSkillLvl(skillLevel);
    }
    else {
      
      this.skills.put(Integer.valueOf(skillId), new SkillListEntry(skillId, false, skillLevel, PersistentState.NEW));
    } 
    if (msg) {
      sendMessage(player, skillId);
    }
    SkillTemplate skillTemplate = DataManager.SKILL_DATA.getSkillTemplate(skillId);

    
    if (skillTemplate.isPassive()) {
      
      calculateUsedWeaponMasterySkills();
      calculateUsedArmorMasterySkills();
    } 
    return true;
  }




  
  public void addSkill(SkillListEntry skill) {
    this.skills.put(Integer.valueOf(skill.getSkillId()), skill);
  }








  
  public boolean addSkillXp(Player player, int skillId, int xpReward) {
    SkillListEntry skillEntry = getSkillEntry(skillId);
    switch (skillEntry.getSkillId()) {
      
      case 30001:
        if (skillEntry.getSkillLevel() == 49)
          return false; 
      case 30002:
      case 30003:
      case 40001:
      case 40002:
      case 40003:
      case 40004:
      case 40007:
      case 40008:
        switch (skillEntry.getSkillLevel()) {
          
          case 99:
          case 199:
          case 299:
          case 399:
          case 450:
          case 499:
            return false;
        } 
        player.getRecipeList().autoLearnRecipe(player, skillId, skillEntry.getSkillLevel()); break;
    } 
    boolean updateSkill = skillEntry.addSkillXp(xpReward);
    if (updateSkill)
      sendMessage(player, skillId); 
    return true;
  }






  
  public boolean isSkillPresent(int skillId) {
    return this.skills.containsKey(Integer.valueOf(skillId));
  }






  
  public int getSkillLevel(int skillId) {
    return ((SkillListEntry)this.skills.get(Integer.valueOf(skillId))).getSkillLevel();
  }






  
  public synchronized boolean removeSkill(Player player, int skillId) {
    SkillListEntry entry = this.skills.get(Integer.valueOf(skillId));
    if (entry != null) {
      
      entry.setPersistentState(PersistentState.DELETED);
      this.deletedSkills.add(entry);
      this.skills.remove(Integer.valueOf(skillId));
      PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SKILL_REMOVE(skillId));
    } 
    return (entry != null);
  }




  
  public int getSize() {
    return this.skills.size();
  }






  
  private void sendMessage(Player player, int skillId) {
    switch (skillId) {
      
      case 30001:
      case 30002:
        PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SKILL_LIST(player.getSkillList().getSkillEntry(skillId), 1330005));
        return;
      case 30003:
        PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SKILL_LIST(player.getSkillList().getSkillEntry(skillId), 1330005));
        return;
      case 40001:
      case 40002:
      case 40003:
      case 40004:
      case 40005:
      case 40006:
      case 40007:
      case 40008:
      case 40009:
        PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SKILL_LIST(player.getSkillList().getSkillEntry(skillId), 1330061));
        return;
    } 
    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SKILL_LIST(player.getSkillList().getSkillEntry(skillId), 1300050));
  }





  
  private void calculateUsedWeaponMasterySkills() {
    Map<WeaponType, Integer> skillLevels = new HashMap<WeaponType, Integer>();
    for (SkillListEntry skillListEntry : getAllSkills()) {
      
      SkillTemplate skillTemplate = DataManager.SKILL_DATA.getSkillTemplate(skillListEntry.getSkillId());
      if (skillTemplate == null) {
        
        logger.warn("CHECKPOINT: no skill template found for " + skillListEntry.getSkillId());

      
      }
      else if (skillTemplate.isPassive()) {
        
        if (skillTemplate.getEffects() != null) {

          
          EffectTemplate template = null;
          if (template = skillTemplate.getEffectTemplate(1) instanceof WeaponMasteryEffect) {
            
            WeaponMasteryEffect wme = (WeaponMasteryEffect)template;
            if (skillLevels.get(wme.getWeaponType()) == null || ((Integer)skillLevels.get(wme.getWeaponType())).intValue() < wme.getBasicLvl()) {

              
              skillLevels.put(wme.getWeaponType(), Integer.valueOf(wme.getBasicLvl()));
              this.weaponMasterySkills.put(wme.getWeaponType(), Integer.valueOf(skillTemplate.getSkillId()));
            } 
          } 
        } 
      } 
    } 
  }



  
  private void calculateUsedArmorMasterySkills() {
    Map<ArmorType, Integer> skillLevels = new HashMap<ArmorType, Integer>();
    for (SkillListEntry skillListEntry : getAllSkills()) {
      
      SkillTemplate skillTemplate = DataManager.SKILL_DATA.getSkillTemplate(skillListEntry.getSkillId());
      if (skillTemplate == null) {
        
        logger.warn("CHECKPOINT: no skill template found for " + skillListEntry.getSkillId());

      
      }
      else if (skillTemplate.isPassive()) {
        
        if (skillTemplate.getEffects() != null) {

          
          EffectTemplate template = null;
          if (template = skillTemplate.getEffectTemplate(1) instanceof ArmorMasteryEffect) {
            
            ArmorMasteryEffect ame = (ArmorMasteryEffect)template;
            if (skillLevels.get(ame.getArmorType()) == null || ((Integer)skillLevels.get(ame.getArmorType())).intValue() < ame.getBasicLvl()) {

              
              skillLevels.put(ame.getArmorType(), Integer.valueOf(ame.getBasicLvl()));
              this.armorMasterySkills.put(ame.getArmorType(), Integer.valueOf(skillTemplate.getSkillId()));
            } 
          } 
        } 
      } 
    } 
  }





  
  public Integer getWeaponMasterySkill(WeaponType weaponType) {
    return this.weaponMasterySkills.get(weaponType);
  }






  
  public Integer getArmorMasterySkill(ArmorType armorType) {
    return this.armorMasterySkills.get(armorType);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\SkillList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
