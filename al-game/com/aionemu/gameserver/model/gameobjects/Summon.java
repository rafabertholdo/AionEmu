/*     */ package com.aionemu.gameserver.model.gameobjects;
/*     */ 
/*     */ import com.aionemu.gameserver.controllers.CreatureController;
/*     */ import com.aionemu.gameserver.controllers.SummonController;
/*     */ import com.aionemu.gameserver.controllers.VisibleObjectController;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.CreatureGameStats;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.CreatureLifeStats;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.SummonGameStats;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.SummonLifeStats;
/*     */ import com.aionemu.gameserver.model.templates.NpcTemplate;
/*     */ import com.aionemu.gameserver.model.templates.VisibleObjectTemplate;
/*     */ import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
/*     */ import com.aionemu.gameserver.model.templates.stats.SummonStatsTemplate;
/*     */ import com.aionemu.gameserver.world.WorldPosition;
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
/*     */ public class Summon
/*     */   extends Creature
/*     */ {
/*     */   private Player master;
/*     */   private SummonMode mode;
/*     */   private byte level;
/*     */   
/*     */   public enum SummonMode
/*     */   {
/*  43 */     ATTACK(0),
/*  44 */     GUARD(1),
/*  45 */     REST(2),
/*  46 */     RELEASE(3);
/*     */     
/*     */     private int id;
/*     */ 
/*     */     
/*     */     SummonMode(int id) {
/*  52 */       this.id = id;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getId() {
/*  60 */       return this.id;
/*     */     }
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
/*     */   public Summon(int objId, CreatureController<? extends Creature> controller, SpawnTemplate spawnTemplate, VisibleObjectTemplate objectTemplate, SummonStatsTemplate statsTemplate, byte level) {
/*  75 */     super(objId, controller, spawnTemplate, objectTemplate, new WorldPosition());
/*     */     
/*  77 */     controller.setOwner(this);
/*  78 */     this.level = level;
/*  79 */     setGameStats((CreatureGameStats<? extends Creature>)new SummonGameStats(this, statsTemplate));
/*  80 */     setLifeStats((CreatureLifeStats<? extends Creature>)new SummonLifeStats(this));
/*     */     
/*  82 */     this.mode = SummonMode.GUARD;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Player getMaster() {
/*  91 */     return this.master;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaster(Player master) {
/* 100 */     this.master = master;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 106 */     return this.objectTemplate.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getLevel() {
/* 115 */     return this.level;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initializeAi() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NpcTemplate getObjectTemplate() {
/* 127 */     return (NpcTemplate)super.getObjectTemplate();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNpcId() {
/* 132 */     return getObjectTemplate().getTemplateId();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNameId() {
/* 137 */     return getObjectTemplate().getNameId();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NpcObjectType getNpcObjectType() {
/* 146 */     return NpcObjectType.SUMMON;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SummonController getController() {
/* 152 */     return (SummonController)super.getController();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SummonMode getMode() {
/* 160 */     return this.mode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMode(SummonMode mode) {
/* 168 */     this.mode = mode;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isEnemyNpc(Npc visibleObject) {
/* 174 */     return this.master.isEnemyNpc(visibleObject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isEnemyPlayer(Player visibleObject) {
/* 180 */     return this.master.isEnemyPlayer(visibleObject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isEnemySummon(Summon summon) {
/* 186 */     return this.master.isEnemySummon(summon);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTribe() {
/* 192 */     return this.master.getTribe();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAggressiveTo(Creature creature) {
/* 198 */     return creature.isAggroFrom(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAggroFrom(Npc npc) {
/* 204 */     if (getMaster() == null) {
/* 205 */       return false;
/*     */     }
/* 207 */     return getMaster().isAggroFrom(npc);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\Summon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */