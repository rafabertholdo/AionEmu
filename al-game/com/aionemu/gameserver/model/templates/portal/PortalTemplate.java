/*     */ package com.aionemu.gameserver.model.templates.portal;
/*     */ 
/*     */ import com.aionemu.gameserver.model.Race;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlType;
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
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "Portal")
/*     */ public class PortalTemplate
/*     */ {
/*     */   @XmlAttribute(name = "npcid")
/*     */   protected int npcId;
/*     */   @XmlAttribute(name = "name")
/*     */   protected String name;
/*     */   @XmlAttribute(name = "instance")
/*     */   protected boolean instance;
/*     */   @XmlAttribute(name = "minlevel")
/*     */   protected int minLevel;
/*     */   @XmlAttribute(name = "maxlevel")
/*     */   protected int maxLevel;
/*     */   @XmlAttribute(name = "group")
/*     */   protected boolean group;
/*     */   @XmlAttribute(name = "race")
/*     */   protected Race race;
/*     */   @XmlElement(name = "entrypoint")
/*     */   protected List<EntryPoint> entryPoint;
/*     */   @XmlElement(name = "exitpoint")
/*     */   protected ExitPoint exitPoint;
/*     */   @XmlElement(name = "portalitem")
/*     */   protected List<PortalItem> portalItem;
/*     */   @XmlAttribute(name = "titleid")
/*     */   protected int IdTitle;
/*     */   
/*     */   public int getNpcId() {
/*  65 */     return this.npcId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  73 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInstance() {
/*  81 */     return this.instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinLevel() {
/*  89 */     return this.minLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxLevel() {
/*  97 */     return this.maxLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isGroup() {
/* 105 */     return this.group;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Race getRace() {
/* 113 */     return this.race;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<EntryPoint> getEntryPoint() {
/* 121 */     return this.entryPoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExitPoint getExitPoint() {
/* 129 */     return this.exitPoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<PortalItem> getPortalItem() {
/* 137 */     return this.portalItem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIdTitle() {
/* 145 */     return this.IdTitle;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\portal\PortalTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */