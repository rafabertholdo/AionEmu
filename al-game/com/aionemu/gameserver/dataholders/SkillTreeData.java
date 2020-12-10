/*     */ package com.aionemu.gameserver.dataholders;
/*     */ 
/*     */ import com.aionemu.gameserver.model.PlayerClass;
/*     */ import com.aionemu.gameserver.model.Race;
/*     */ import com.aionemu.gameserver.skillengine.model.learn.SkillClass;
/*     */ import com.aionemu.gameserver.skillengine.model.learn.SkillLearnTemplate;
/*     */ import com.aionemu.gameserver.skillengine.model.learn.SkillRace;
/*     */ import gnu.trove.TIntObjectHashMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
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
/*     */ @XmlRootElement(name = "skill_tree")
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ public class SkillTreeData
/*     */ {
/*     */   @XmlElement(name = "skill")
/*     */   private List<SkillLearnTemplate> skillTemplates;
/*  47 */   private final TIntObjectHashMap<ArrayList<SkillLearnTemplate>> templates = new TIntObjectHashMap();
/*     */ 
/*     */   
/*     */   void afterUnmarshal(Unmarshaller u, Object parent) {
/*  51 */     for (SkillLearnTemplate template : this.skillTemplates)
/*     */     {
/*  53 */       addTemplate(template);
/*     */     }
/*  55 */     this.skillTemplates = null;
/*     */   }
/*     */ 
/*     */   
/*     */   private void addTemplate(SkillLearnTemplate template) {
/*  60 */     SkillRace race = template.getRace();
/*  61 */     if (race == null) {
/*  62 */       race = SkillRace.ALL;
/*     */     }
/*  64 */     int hash = makeHash(template.getClassId().ordinal(), race.ordinal(), template.getMinLevel());
/*  65 */     ArrayList<SkillLearnTemplate> value = (ArrayList<SkillLearnTemplate>)this.templates.get(hash);
/*  66 */     if (value == null) {
/*     */       
/*  68 */       value = new ArrayList<SkillLearnTemplate>();
/*  69 */       this.templates.put(hash, value);
/*     */     } 
/*     */     
/*  72 */     value.add(template);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TIntObjectHashMap<ArrayList<SkillLearnTemplate>> getTemplates() {
/*  80 */     return this.templates;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SkillLearnTemplate[] getTemplatesFor(PlayerClass playerClass, int level, Race race) {
/*  96 */     List<SkillLearnTemplate> newSkills = new ArrayList<SkillLearnTemplate>();
/*     */     
/*  98 */     List<SkillLearnTemplate> classRaceSpecificTemplates = (List<SkillLearnTemplate>)this.templates.get(makeHash(playerClass.ordinal(), race.ordinal(), level));
/*     */     
/* 100 */     List<SkillLearnTemplate> classSpecificTemplates = (List<SkillLearnTemplate>)this.templates.get(makeHash(playerClass.ordinal(), SkillRace.ALL.ordinal(), level));
/*     */     
/* 102 */     List<SkillLearnTemplate> generalTemplates = (List<SkillLearnTemplate>)this.templates.get(makeHash(SkillClass.ALL.ordinal(), SkillRace.ALL.ordinal(), level));
/*     */ 
/*     */     
/* 105 */     if (classRaceSpecificTemplates != null)
/* 106 */       newSkills.addAll(classRaceSpecificTemplates); 
/* 107 */     if (classSpecificTemplates != null)
/* 108 */       newSkills.addAll(classSpecificTemplates); 
/* 109 */     if (generalTemplates != null) {
/* 110 */       newSkills.addAll(generalTemplates);
/*     */     }
/* 112 */     return newSkills.<SkillLearnTemplate>toArray(new SkillLearnTemplate[newSkills.size()]);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 117 */     int size = 0;
/* 118 */     for (int arr$[] = this.templates.keys(), len$ = arr$.length, i$ = 0; i$ < len$; ) { Integer key = Integer.valueOf(arr$[i$]);
/* 119 */       size += ((ArrayList)this.templates.get(key.intValue())).size(); i$++; }
/* 120 */      return size;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int makeHash(int classId, int race, int level) {
/* 125 */     int result = classId << 8;
/* 126 */     result = (result | race) << 8;
/* 127 */     return result | level;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\SkillTreeData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */