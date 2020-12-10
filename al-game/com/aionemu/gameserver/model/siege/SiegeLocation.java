/*     */ package com.aionemu.gameserver.model.siege;
/*     */ 
/*     */ import com.aionemu.gameserver.model.templates.siegelocation.SiegeLocationTemplate;
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
/*     */ public class SiegeLocation
/*     */ {
/*     */   public static final int INVULNERABLE = 0;
/*     */   public static final int VULNERABLE = 1;
/*     */   private int locationId;
/*     */   private int worldId;
/*  37 */   private SiegeRace siegeRace = SiegeRace.BALAUR;
/*  38 */   private int legionId = 0;
/*     */   
/*     */   private boolean isVulnerable = false;
/*  41 */   private int nextState = 0;
/*     */   
/*     */   public SiegeLocation() {}
/*     */   
/*     */   public SiegeLocation(SiegeLocationTemplate template) {
/*  46 */     this.locationId = template.getId();
/*  47 */     this.worldId = template.getWorldId();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLocationId() {
/*  57 */     return this.locationId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWorldId() {
/*  62 */     return this.worldId;
/*     */   }
/*     */ 
/*     */   
/*     */   public SiegeRace getRace() {
/*  67 */     return this.siegeRace;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRace(SiegeRace siegeRace) {
/*  72 */     this.siegeRace = siegeRace;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLegionId() {
/*  77 */     return this.legionId;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLegionId(int legionId) {
/*  82 */     this.legionId = legionId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNextState() {
/*  93 */     return this.nextState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNextState(Integer nextState) {
/* 101 */     this.nextState = nextState.intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVulnerable() {
/* 109 */     return this.isVulnerable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVulnerable(boolean value) {
/* 117 */     this.isVulnerable = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInfluenceValue() {
/* 124 */     return 0;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\siege\SiegeLocation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */