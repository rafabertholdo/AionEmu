/*    */ package com.aionemu.gameserver.ai.desires.impl;
/*    */ 
/*    */ import com.aionemu.commons.utils.Rnd;
/*    */ import com.aionemu.gameserver.ai.AI;
/*    */ import com.aionemu.gameserver.ai.desires.AbstractDesire;
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*    */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*    */ import com.aionemu.gameserver.model.templates.npcskill.NpcSkillList;
/*    */ import com.aionemu.gameserver.model.templates.npcskill.NpcSkillTemplate;
/*    */ import com.aionemu.gameserver.skillengine.SkillEngine;
/*    */ import com.aionemu.gameserver.skillengine.model.Skill;
/*    */ import java.util.List;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SkillUseDesire
/*    */   extends AbstractDesire
/*    */ {
/*    */   protected Creature owner;
/*    */   private NpcSkillList skillList;
/*    */   
/*    */   public SkillUseDesire(Creature owner, int desirePower) {
/* 47 */     super(desirePower);
/* 48 */     this.owner = owner;
/* 49 */     this.skillList = ((Npc)owner).getNpcSkillList();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean handleDesire(AI<?> ai) {
/* 55 */     if (this.owner.isCasting()) {
/* 56 */       return true;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 61 */     List<NpcSkillTemplate> skills = this.skillList.getNpcSkills();
/* 62 */     NpcSkillTemplate npcSkill = skills.get(Rnd.get(0, this.skillList.getCount() - 1));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 68 */     int skillProbability = npcSkill.getProbability();
/* 69 */     if (Rnd.get(0, 100) < skillProbability) {
/*    */       
/* 71 */       Skill skill = SkillEngine.getInstance().getSkill(this.owner, npcSkill.getSkillid(), npcSkill.getSkillLevel(), this.owner.getTarget());
/*    */ 
/*    */       
/* 74 */       if (skill != null) {
/* 75 */         skill.useSkill();
/*    */       }
/*    */     } 
/*    */     
/* 79 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onClear() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getExecutionInterval() {
/* 91 */     return 1;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\desires\impl\SkillUseDesire.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */