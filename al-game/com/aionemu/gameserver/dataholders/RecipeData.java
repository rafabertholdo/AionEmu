/*     */ package com.aionemu.gameserver.dataholders;
/*     */ 
/*     */ import com.aionemu.gameserver.model.Race;
/*     */ import com.aionemu.gameserver.model.templates.recipe.RecipeTemplate;
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
/*     */ 
/*     */ 
/*     */ @XmlRootElement(name = "recipe_templates")
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ public class RecipeData
/*     */ {
/*     */   @XmlElement(name = "recipe_template")
/*     */   protected List<RecipeTemplate> list;
/*     */   private TIntObjectHashMap<RecipeTemplate> recipeData;
/*  48 */   private final TIntObjectHashMap<ArrayList<RecipeTemplate>> learnTemplates = new TIntObjectHashMap();
/*     */ 
/*     */   
/*     */   void afterUnmarshal(Unmarshaller u, Object parent) {
/*  52 */     this.recipeData = new TIntObjectHashMap();
/*  53 */     for (RecipeTemplate it : this.list) {
/*     */       
/*  55 */       this.recipeData.put(it.getId().intValue(), it);
/*  56 */       if (it.getAutolearn().intValue() == 0)
/*     */         continue; 
/*  58 */       addTemplate(it);
/*     */     } 
/*  60 */     this.list = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public RecipeTemplate getRecipeTemplateById(int id) {
/*  65 */     return (RecipeTemplate)this.recipeData.get(id);
/*     */   }
/*     */ 
/*     */   
/*     */   private void addTemplate(RecipeTemplate template) {
/*  70 */     SkillRace race = template.getRace();
/*  71 */     if (race == null) {
/*  72 */       race = SkillRace.ALL;
/*     */     }
/*  74 */     int hash = makeHash(race.ordinal(), template.getSkillid().intValue(), template.getSkillpoint().intValue());
/*  75 */     ArrayList<RecipeTemplate> value = (ArrayList<RecipeTemplate>)this.learnTemplates.get(hash);
/*  76 */     if (value == null) {
/*     */       
/*  78 */       value = new ArrayList<RecipeTemplate>();
/*  79 */       this.learnTemplates.put(hash, value);
/*     */     } 
/*     */     
/*  82 */     value.add(template);
/*     */   }
/*     */ 
/*     */   
/*     */   public RecipeTemplate[] getRecipeIdFor(Race race, int skillId, int skillPoint) {
/*  87 */     List<RecipeTemplate> newRecipes = new ArrayList<RecipeTemplate>();
/*  88 */     List<RecipeTemplate> raceSpecificTemplates = (List<RecipeTemplate>)this.learnTemplates.get(makeHash(race.ordinal(), skillId, skillPoint));
/*     */     
/*  90 */     List<RecipeTemplate> allRaceSpecificTemplates = (List<RecipeTemplate>)this.learnTemplates.get(makeHash(SkillRace.ALL.ordinal(), skillId, skillPoint));
/*     */ 
/*     */     
/*  93 */     if (raceSpecificTemplates != null)
/*  94 */       newRecipes.addAll(raceSpecificTemplates); 
/*  95 */     if (allRaceSpecificTemplates != null) {
/*  96 */       newRecipes.addAll(allRaceSpecificTemplates);
/*     */     }
/*  98 */     return newRecipes.<RecipeTemplate>toArray(new RecipeTemplate[newRecipes.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 106 */     return this.recipeData.size();
/*     */   }
/*     */ 
/*     */   
/*     */   private static int makeHash(int race, int skillId, int skillLevel) {
/* 111 */     int result = race << 8;
/* 112 */     result = (result | skillId) << 8;
/* 113 */     return result | skillLevel;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\RecipeData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */