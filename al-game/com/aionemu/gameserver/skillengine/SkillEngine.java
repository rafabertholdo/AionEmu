/*    */ package com.aionemu.gameserver.skillengine;
/*    */ 
/*    */ import com.aionemu.gameserver.dataholders.DataManager;
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*    */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.skillengine.model.ActivationAttribute;
/*    */ import com.aionemu.gameserver.skillengine.model.Skill;
/*    */ import com.aionemu.gameserver.skillengine.model.SkillTemplate;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SkillEngine
/*    */ {
/* 33 */   public static final SkillEngine skillEngine = new SkillEngine();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Skill getSkillFor(Player player, int skillId, VisibleObject firstTarget) {
/* 52 */     SkillTemplate template = DataManager.SKILL_DATA.getSkillTemplate(skillId);
/*    */     
/* 54 */     if (template == null) {
/* 55 */       return null;
/*    */     }
/*    */     
/* 58 */     if (template.getActivationAttribute() != ActivationAttribute.PROVOKED)
/*    */     {
/* 60 */       if (!player.getSkillList().isSkillPresent(skillId)) {
/* 61 */         return null;
/*    */       }
/*    */     }
/*    */     
/* 65 */     Creature target = null;
/* 66 */     if (firstTarget instanceof Creature) {
/* 67 */       target = (Creature)firstTarget;
/*    */     }
/* 69 */     return new Skill(template, player, target);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Skill getSkill(Creature creature, int skillId, int skillLevel, VisibleObject firstTarget) {
/* 82 */     SkillTemplate template = DataManager.SKILL_DATA.getSkillTemplate(skillId);
/*    */     
/* 84 */     if (template == null) {
/* 85 */       return null;
/*    */     }
/* 87 */     Creature target = null;
/* 88 */     if (firstTarget instanceof Creature)
/* 89 */       target = (Creature)firstTarget; 
/* 90 */     return new Skill(template, creature, skillLevel, target);
/*    */   }
/*    */ 
/*    */   
/*    */   public static SkillEngine getInstance() {
/* 95 */     return skillEngine;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\SkillEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */