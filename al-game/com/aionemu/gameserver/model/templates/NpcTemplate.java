/*     */ package com.aionemu.gameserver.model.templates;
/*     */ 
/*     */ import com.aionemu.gameserver.model.NpcType;
/*     */ import com.aionemu.gameserver.model.Race;
/*     */ import com.aionemu.gameserver.model.items.NpcEquippedGear;
/*     */ import com.aionemu.gameserver.model.templates.stats.KiskStatsTemplate;
/*     */ import com.aionemu.gameserver.model.templates.stats.NpcRank;
/*     */ import com.aionemu.gameserver.model.templates.stats.NpcStatsTemplate;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlID;
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
/*     */ @XmlAccessorType(XmlAccessType.NONE)
/*     */ @XmlRootElement(name = "npc_template")
/*     */ public class NpcTemplate
/*     */   extends VisibleObjectTemplate
/*     */ {
/*     */   private int npcId;
/*     */   @XmlAttribute(name = "level", required = true)
/*     */   private byte level;
/*     */   @XmlAttribute(name = "name_id", required = true)
/*     */   private int nameId;
/*     */   @XmlAttribute(name = "title_id")
/*     */   private int titleId;
/*     */   @XmlAttribute(name = "name")
/*     */   private String name;
/*     */   @XmlAttribute(name = "height")
/*  50 */   private float height = 1.0F;
/*     */   @XmlAttribute(name = "talking_distance")
/*  52 */   private int talkingDistance = 2;
/*     */   
/*     */   @XmlAttribute(name = "npc_type", required = true)
/*     */   private NpcType npcType;
/*     */   @XmlElement(name = "stats")
/*     */   private NpcStatsTemplate statsTemplate;
/*     */   @XmlElement(name = "equipment")
/*     */   private NpcEquippedGear equipment;
/*     */   @XmlElement(name = "kisk_stats")
/*     */   private KiskStatsTemplate kiskStatsTemplate;
/*     */   @XmlElement(name = "ammo_speed")
/*  63 */   private int ammoSpeed = 0;
/*     */   
/*     */   @XmlAttribute(name = "rank")
/*     */   private NpcRank rank;
/*     */   
/*     */   @XmlAttribute(name = "srange")
/*     */   private int aggrorange;
/*     */   
/*     */   @XmlAttribute(name = "arange")
/*     */   private int attackRange;
/*     */   
/*     */   @XmlAttribute(name = "srange")
/*     */   private int attackRate;
/*     */   @XmlAttribute(name = "hpgauge")
/*     */   private int hpGauge;
/*     */   @XmlAttribute(name = "tribe")
/*     */   private String tribe;
/*     */   @XmlAttribute
/*     */   private Race race;
/*     */   @XmlAttribute
/*     */   private int state;
/*     */   
/*     */   public int getTemplateId() {
/*  86 */     return this.npcId;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNameId() {
/*  92 */     return this.nameId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTitleId() {
/*  97 */     return this.titleId;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 103 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getHeight() {
/* 111 */     return this.height;
/*     */   }
/*     */ 
/*     */   
/*     */   public NpcType getNpcType() {
/* 116 */     return this.npcType;
/*     */   }
/*     */ 
/*     */   
/*     */   public NpcEquippedGear getEquipment() {
/* 121 */     return this.equipment;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getLevel() {
/* 126 */     return this.level;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NpcStatsTemplate getStatsTemplate() {
/* 134 */     return this.statsTemplate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStatsTemplate(NpcStatsTemplate statsTemplate) {
/* 142 */     this.statsTemplate = statsTemplate;
/*     */   }
/*     */ 
/*     */   
/*     */   public KiskStatsTemplate getKiskStatsTemplate() {
/* 147 */     return this.kiskStatsTemplate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTribe() {
/* 155 */     return this.tribe;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 164 */     return "Npc Template id: " + this.npcId + " name: " + this.name;
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
/*     */   @XmlID
/*     */   @XmlAttribute(name = "npc_id", required = true)
/*     */   private void setXmlUid(String uid) {
/* 178 */     this.npcId = Integer.parseInt(uid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NpcRank getRank() {
/* 186 */     return this.rank;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAggroRange() {
/* 191 */     return this.aggrorange;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAttackRange() {
/* 199 */     return this.attackRange;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAttackRate() {
/* 207 */     return this.attackRate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHpGauge() {
/* 215 */     return this.hpGauge;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Race getRace() {
/* 223 */     return this.race;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getState() {
/* 232 */     return this.state;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\NpcTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */