/*     */ package com.aionemu.gameserver.model.gameobjects;
/*     */ 
/*     */ import com.aionemu.gameserver.ai.AI;
/*     */ import com.aionemu.gameserver.ai.npcai.ServantAi;
/*     */ import com.aionemu.gameserver.controllers.NpcController;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.VisibleObjectTemplate;
/*     */ import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
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
/*     */ public class Servant
/*     */   extends Npc
/*     */ {
/*     */   private int skillId;
/*     */   private Creature creator;
/*     */   private Creature target;
/*     */   private int hpRatio;
/*     */   
/*     */   public Servant(int objId, NpcController controller, SpawnTemplate spawnTemplate, VisibleObjectTemplate objectTemplate) {
/*  57 */     super(objId, controller, spawnTemplate, objectTemplate);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSkillId() {
/*  65 */     return this.skillId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSkillId(int skillId) {
/*  73 */     this.skillId = skillId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Creature getCreator() {
/*  81 */     return this.creator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCreator(Creature creator) {
/*  89 */     this.creator = creator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Creature getTarget() {
/*  98 */     return this.target;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTarget(Creature target) {
/* 106 */     this.target = target;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHpRatio() {
/* 114 */     return this.hpRatio;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHpRatio(int hpRatio) {
/* 122 */     this.hpRatio = hpRatio;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initializeAi() {
/* 128 */     this.ai = (AI<? extends Creature>)new ServantAi();
/* 129 */     this.ai.setOwner(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isEnemyNpc(Npc visibleObject) {
/* 135 */     return this.creator.isEnemyNpc(visibleObject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isEnemyPlayer(Player visibleObject) {
/* 141 */     return this.creator.isEnemyPlayer(visibleObject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isEnemySummon(Summon summon) {
/* 147 */     return this.creator.isEnemySummon(summon);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NpcObjectType getNpcObjectType() {
/* 156 */     return NpcObjectType.SERVANT;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Creature getActingCreature() {
/* 162 */     return this.creator;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Creature getMaster() {
/* 168 */     return this.creator;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\Servant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */