/*     */ package com.aionemu.gameserver.model.gameobjects;
/*     */ 
/*     */ import com.aionemu.gameserver.ai.AI;
/*     */ import com.aionemu.gameserver.ai.npcai.TrapAi;
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
/*     */ public class Trap
/*     */   extends Npc
/*     */ {
/*     */   private int skillId;
/*     */   private Creature creator;
/*     */   
/*     */   public Trap(int objId, NpcController controller, SpawnTemplate spawnTemplate, VisibleObjectTemplate objectTemplate) {
/*  50 */     super(objId, controller, spawnTemplate, objectTemplate);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSkillId() {
/*  58 */     return this.skillId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSkillId(int skillId) {
/*  66 */     this.skillId = skillId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Creature getCreator() {
/*  74 */     return this.creator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCreator(Creature creator) {
/*  82 */     this.creator = creator;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getLevel() {
/*  88 */     return (this.creator == null) ? 1 : this.creator.getLevel();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initializeAi() {
/*  94 */     this.ai = (AI<? extends Creature>)new TrapAi();
/*  95 */     this.ai.setOwner(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isEnemyNpc(Npc visibleObject) {
/* 101 */     return this.creator.isEnemyNpc(visibleObject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isEnemyPlayer(Player visibleObject) {
/* 107 */     return this.creator.isEnemyPlayer(visibleObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NpcObjectType getNpcObjectType() {
/* 116 */     return NpcObjectType.TRAP;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Creature getActingCreature() {
/* 122 */     return this.creator;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Creature getMaster() {
/* 128 */     return this.creator;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\Trap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */