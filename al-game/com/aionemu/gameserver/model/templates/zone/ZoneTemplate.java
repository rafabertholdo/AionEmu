/*     */ package com.aionemu.gameserver.model.templates.zone;
/*     */ 
/*     */ import com.aionemu.gameserver.world.zone.ZoneName;
/*     */ import java.util.ArrayList;
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
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "Zone")
/*     */ public class ZoneTemplate
/*     */ {
/*     */   @XmlElement(required = true)
/*     */   protected Points points;
/*     */   protected List<ZoneName> link;
/*     */   @XmlAttribute
/*     */   protected int priority;
/*     */   @XmlAttribute(name = "fly")
/*     */   protected boolean flightAllowed;
/*     */   @XmlAttribute(name = "breath")
/*     */   protected boolean breath;
/*     */   @XmlAttribute
/*     */   protected ZoneName name;
/*     */   @XmlAttribute
/*     */   protected int mapid;
/*     */   
/*     */   public Points getPoints() {
/*  56 */     return this.points;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ZoneName> getLink() {
/*  63 */     if (this.link == null) {
/*  64 */       this.link = new ArrayList<ZoneName>();
/*     */     }
/*  66 */     return this.link;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPriority() {
/*  74 */     return this.priority;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFlightAllowed() {
/*  82 */     return this.flightAllowed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZoneName getName() {
/*  89 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMapid() {
/*  96 */     return this.mapid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBreath() {
/* 104 */     return this.breath;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\zone\ZoneTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */