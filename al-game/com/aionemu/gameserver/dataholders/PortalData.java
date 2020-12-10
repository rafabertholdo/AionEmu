/*     */ package com.aionemu.gameserver.dataholders;
/*     */ 
/*     */ import com.aionemu.gameserver.model.Race;
/*     */ import com.aionemu.gameserver.model.templates.portal.PortalTemplate;
/*     */ import gnu.trove.THashMap;
/*     */ import gnu.trove.TIntObjectHashMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlRootElement(name = "portal_templates")
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ public class PortalData
/*     */ {
/*     */   @XmlElement(name = "portal")
/*     */   private List<PortalTemplate> portals;
/*  46 */   private TIntObjectHashMap<PortalTemplate> portalData = new TIntObjectHashMap();
/*  47 */   private TIntObjectHashMap<ArrayList<PortalTemplate>> instancesMap = new TIntObjectHashMap();
/*  48 */   private THashMap<String, PortalTemplate> namedPortals = new THashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void afterUnmarshal(Unmarshaller u, Object parent) {
/*  58 */     this.portalData.clear();
/*  59 */     this.instancesMap.clear();
/*  60 */     this.namedPortals.clear();
/*     */     
/*  62 */     for (PortalTemplate portal : this.portals) {
/*     */       
/*  64 */       this.portalData.put(portal.getNpcId(), portal);
/*  65 */       if (portal.isInstance()) {
/*     */         
/*  67 */         int mapId = portal.getExitPoint().getMapId();
/*  68 */         ArrayList<PortalTemplate> templates = (ArrayList<PortalTemplate>)this.instancesMap.get(mapId);
/*  69 */         if (templates == null) {
/*     */           
/*  71 */           templates = new ArrayList<PortalTemplate>();
/*  72 */           this.instancesMap.put(mapId, templates);
/*     */         } 
/*  74 */         templates.add(portal);
/*     */       } 
/*  76 */       if (portal.getName() != null && !portal.getName().isEmpty()) {
/*  77 */         this.namedPortals.put(portal.getName(), portal);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public int size() {
/*  83 */     return this.portalData.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PortalTemplate getPortalTemplate(int npcId) {
/*  93 */     return (PortalTemplate)this.portalData.get(npcId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PortalTemplate getInstancePortalTemplate(int worldId, Race race) {
/* 104 */     List<PortalTemplate> portals = (List<PortalTemplate>)this.instancesMap.get(worldId);
/*     */     
/* 106 */     if (portals == null)
/*     */     {
/*     */       
/* 109 */       return null;
/*     */     }
/*     */     
/* 112 */     for (PortalTemplate portal : portals) {
/*     */       
/* 114 */       if (portal.getRace() == null || portal.getRace().equals(race)) {
/* 115 */         return portal;
/*     */       }
/*     */     } 
/* 118 */     throw new IllegalArgumentException("There is no portal template for: " + worldId + " " + race);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PortalTemplate getTemplateByNameAndWorld(int worldId, String name) {
/* 129 */     PortalTemplate portal = (PortalTemplate)this.namedPortals.get(name);
/*     */     
/* 131 */     if (portal != null && portal.getExitPoint().getMapId() != worldId) {
/* 132 */       throw new IllegalArgumentException("Invalid combination of world and name: " + worldId + " " + name);
/*     */     }
/* 134 */     return portal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<PortalTemplate> getPortals() {
/* 142 */     return this.portals;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPortals(List<PortalTemplate> portals) {
/* 150 */     this.portals = portals;
/* 151 */     afterUnmarshal(null, null);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\PortalData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */