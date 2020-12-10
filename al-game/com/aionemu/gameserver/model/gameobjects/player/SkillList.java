/*     */ package com.aionemu.gameserver.model.gameobjects.player;
/*     */ 
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.gameobjects.PersistentState;
/*     */ import com.aionemu.gameserver.model.templates.item.ArmorType;
/*     */ import com.aionemu.gameserver.model.templates.item.WeaponType;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SKILL_LIST;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SKILL_REMOVE;
/*     */ import com.aionemu.gameserver.skillengine.effect.ArmorMasteryEffect;
/*     */ import com.aionemu.gameserver.skillengine.effect.EffectTemplate;
/*     */ import com.aionemu.gameserver.skillengine.effect.WeaponMasteryEffect;
/*     */ import com.aionemu.gameserver.skillengine.model.SkillTemplate;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SkillList
/*     */ {
/*  49 */   private static final Logger logger = Logger.getLogger(SkillList.class);
/*     */   
/*  51 */   public static final String[] split = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private final Map<Integer, SkillListEntry> skills;
/*     */ 
/*     */ 
/*     */   
/*     */   private final List<SkillListEntry> deletedSkills;
/*     */ 
/*     */ 
/*     */   
/*  63 */   private final Map<WeaponType, Integer> weaponMasterySkills = new HashMap<WeaponType, Integer>();
/*     */ 
/*     */ 
/*     */   
/*  67 */   private final Map<ArmorType, Integer> armorMasterySkills = new HashMap<ArmorType, Integer>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SkillList() {
/*  74 */     this.skills = new HashMap<Integer, SkillListEntry>();
/*  75 */     this.deletedSkills = new ArrayList<SkillListEntry>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SkillList(Map<Integer, SkillListEntry> arg) {
/*  84 */     this.skills = arg;
/*  85 */     this.deletedSkills = new ArrayList<SkillListEntry>();
/*  86 */     calculateUsedWeaponMasterySkills();
/*  87 */     calculateUsedArmorMasterySkills();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SkillListEntry[] getAllSkills() {
/*  96 */     return (SkillListEntry[])this.skills.values().toArray((Object[])new SkillListEntry[this.skills.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SkillListEntry[] getDeletedSkills() {
/* 105 */     return this.deletedSkills.<SkillListEntry>toArray(new SkillListEntry[this.deletedSkills.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SkillListEntry getSkillEntry(int skillId) {
/* 114 */     return this.skills.get(Integer.valueOf(skillId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean addSkill(Player player, int skillId, int skillLevel, boolean msg) {
/* 124 */     SkillListEntry existingSkill = this.skills.get(Integer.valueOf(skillId));
/* 125 */     if (existingSkill != null) {
/*     */       
/* 127 */       if (existingSkill.getSkillLevel() >= skillLevel)
/*     */       {
/* 129 */         return false;
/*     */       }
/* 131 */       existingSkill.setSkillLvl(skillLevel);
/*     */     }
/*     */     else {
/*     */       
/* 135 */       this.skills.put(Integer.valueOf(skillId), new SkillListEntry(skillId, false, skillLevel, PersistentState.NEW));
/*     */     } 
/* 137 */     if (msg) {
/* 138 */       sendMessage(player, skillId);
/*     */     }
/* 140 */     SkillTemplate skillTemplate = DataManager.SKILL_DATA.getSkillTemplate(skillId);
/*     */ 
/*     */     
/* 143 */     if (skillTemplate.isPassive()) {
/*     */       
/* 145 */       calculateUsedWeaponMasterySkills();
/* 146 */       calculateUsedArmorMasterySkills();
/*     */     } 
/* 148 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSkill(SkillListEntry skill) {
/* 156 */     this.skills.put(Integer.valueOf(skill.getSkillId()), skill);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addSkillXp(Player player, int skillId, int xpReward) {
/* 168 */     SkillListEntry skillEntry = getSkillEntry(skillId);
/* 169 */     switch (skillEntry.getSkillId()) {
/*     */       
/*     */       case 30001:
/* 172 */         if (skillEntry.getSkillLevel() == 49)
/* 173 */           return false; 
/*     */       case 30002:
/*     */       case 30003:
/*     */       case 40001:
/*     */       case 40002:
/*     */       case 40003:
/*     */       case 40004:
/*     */       case 40007:
/*     */       case 40008:
/* 182 */         switch (skillEntry.getSkillLevel()) {
/*     */           
/*     */           case 99:
/*     */           case 199:
/*     */           case 299:
/*     */           case 399:
/*     */           case 450:
/*     */           case 499:
/* 190 */             return false;
/*     */         } 
/* 192 */         player.getRecipeList().autoLearnRecipe(player, skillId, skillEntry.getSkillLevel()); break;
/*     */     } 
/* 194 */     boolean updateSkill = skillEntry.addSkillXp(xpReward);
/* 195 */     if (updateSkill)
/* 196 */       sendMessage(player, skillId); 
/* 197 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSkillPresent(int skillId) {
/* 207 */     return this.skills.containsKey(Integer.valueOf(skillId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSkillLevel(int skillId) {
/* 217 */     return ((SkillListEntry)this.skills.get(Integer.valueOf(skillId))).getSkillLevel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean removeSkill(Player player, int skillId) {
/* 227 */     SkillListEntry entry = this.skills.get(Integer.valueOf(skillId));
/* 228 */     if (entry != null) {
/*     */       
/* 230 */       entry.setPersistentState(PersistentState.DELETED);
/* 231 */       this.deletedSkills.add(entry);
/* 232 */       this.skills.remove(Integer.valueOf(skillId));
/* 233 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SKILL_REMOVE(skillId));
/*     */     } 
/* 235 */     return (entry != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 243 */     return this.skills.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sendMessage(Player player, int skillId) {
/* 253 */     switch (skillId) {
/*     */       
/*     */       case 30001:
/*     */       case 30002:
/* 257 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SKILL_LIST(player.getSkillList().getSkillEntry(skillId), 1330005));
/*     */         return;
/*     */       case 30003:
/* 260 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SKILL_LIST(player.getSkillList().getSkillEntry(skillId), 1330005));
/*     */         return;
/*     */       case 40001:
/*     */       case 40002:
/*     */       case 40003:
/*     */       case 40004:
/*     */       case 40005:
/*     */       case 40006:
/*     */       case 40007:
/*     */       case 40008:
/*     */       case 40009:
/* 271 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SKILL_LIST(player.getSkillList().getSkillEntry(skillId), 1330061));
/*     */         return;
/*     */     } 
/* 274 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SKILL_LIST(player.getSkillList().getSkillEntry(skillId), 1300050));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void calculateUsedWeaponMasterySkills() {
/* 283 */     Map<WeaponType, Integer> skillLevels = new HashMap<WeaponType, Integer>();
/* 284 */     for (SkillListEntry skillListEntry : getAllSkills()) {
/*     */       
/* 286 */       SkillTemplate skillTemplate = DataManager.SKILL_DATA.getSkillTemplate(skillListEntry.getSkillId());
/* 287 */       if (skillTemplate == null) {
/*     */         
/* 289 */         logger.warn("CHECKPOINT: no skill template found for " + skillListEntry.getSkillId());
/*     */ 
/*     */       
/*     */       }
/* 293 */       else if (skillTemplate.isPassive()) {
/*     */         
/* 295 */         if (skillTemplate.getEffects() != null) {
/*     */ 
/*     */           
/* 298 */           EffectTemplate template = null;
/* 299 */           if (template = skillTemplate.getEffectTemplate(1) instanceof WeaponMasteryEffect) {
/*     */             
/* 301 */             WeaponMasteryEffect wme = (WeaponMasteryEffect)template;
/* 302 */             if (skillLevels.get(wme.getWeaponType()) == null || ((Integer)skillLevels.get(wme.getWeaponType())).intValue() < wme.getBasicLvl()) {
/*     */ 
/*     */               
/* 305 */               skillLevels.put(wme.getWeaponType(), Integer.valueOf(wme.getBasicLvl()));
/* 306 */               this.weaponMasterySkills.put(wme.getWeaponType(), Integer.valueOf(skillTemplate.getSkillId()));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void calculateUsedArmorMasterySkills() {
/* 318 */     Map<ArmorType, Integer> skillLevels = new HashMap<ArmorType, Integer>();
/* 319 */     for (SkillListEntry skillListEntry : getAllSkills()) {
/*     */       
/* 321 */       SkillTemplate skillTemplate = DataManager.SKILL_DATA.getSkillTemplate(skillListEntry.getSkillId());
/* 322 */       if (skillTemplate == null) {
/*     */         
/* 324 */         logger.warn("CHECKPOINT: no skill template found for " + skillListEntry.getSkillId());
/*     */ 
/*     */       
/*     */       }
/* 328 */       else if (skillTemplate.isPassive()) {
/*     */         
/* 330 */         if (skillTemplate.getEffects() != null) {
/*     */ 
/*     */           
/* 333 */           EffectTemplate template = null;
/* 334 */           if (template = skillTemplate.getEffectTemplate(1) instanceof ArmorMasteryEffect) {
/*     */             
/* 336 */             ArmorMasteryEffect ame = (ArmorMasteryEffect)template;
/* 337 */             if (skillLevels.get(ame.getArmorType()) == null || ((Integer)skillLevels.get(ame.getArmorType())).intValue() < ame.getBasicLvl()) {
/*     */ 
/*     */               
/* 340 */               skillLevels.put(ame.getArmorType(), Integer.valueOf(ame.getBasicLvl()));
/* 341 */               this.armorMasterySkills.put(ame.getArmorType(), Integer.valueOf(skillTemplate.getSkillId()));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getWeaponMasterySkill(WeaponType weaponType) {
/* 355 */     return this.weaponMasterySkills.get(weaponType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getArmorMasterySkill(ArmorType armorType) {
/* 365 */     return this.armorMasterySkills.get(armorType);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\SkillList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */