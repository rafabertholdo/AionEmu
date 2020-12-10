/*     */ package com.aionemu.gameserver.model.templates;
/*     */ 
/*     */ import com.aionemu.gameserver.world.WorldType;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
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
/*     */ @XmlRootElement(name = "map")
/*     */ @XmlAccessorType(XmlAccessType.NONE)
/*     */ public class WorldMapTemplate
/*     */ {
/*     */   @XmlAttribute(name = "name")
/*  36 */   private String name = "";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlAttribute(name = "id", required = true)
/*     */   private Integer mapId;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlAttribute(name = "twin_count")
/*     */   private int twinCount;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlAttribute(name = "max_user")
/*     */   private int maxUser;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlAttribute(name = "prison")
/*     */   private boolean prison = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlAttribute(name = "instance")
/*     */   private boolean instance = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlAttribute(name = "death_level", required = true)
/*  72 */   private int deathlevel = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlAttribute(name = "water_level", required = true)
/*  78 */   private int waterlevel = 16;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlAttribute(name = "world_type")
/*  84 */   private WorldType worldType = WorldType.NONE;
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  89 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getMapId() {
/*  94 */     return this.mapId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTwinCount() {
/*  99 */     return this.twinCount;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxUser() {
/* 104 */     return this.maxUser;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPrison() {
/* 109 */     return this.prison;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInstance() {
/* 117 */     return this.instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWaterLevel() {
/* 124 */     return this.waterlevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDeathLevel() {
/* 131 */     return this.deathlevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldType getWorldType() {
/* 139 */     return this.worldType;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\WorldMapTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */