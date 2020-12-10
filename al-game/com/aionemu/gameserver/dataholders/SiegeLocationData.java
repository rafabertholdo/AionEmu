/*    */ package com.aionemu.gameserver.dataholders;
/*    */ 
/*    */ import com.aionemu.gameserver.model.siege.Artifact;
/*    */ import com.aionemu.gameserver.model.siege.Commander;
/*    */ import com.aionemu.gameserver.model.siege.Fortress;
/*    */ import com.aionemu.gameserver.model.siege.SiegeLocation;
/*    */ import com.aionemu.gameserver.model.siege.SiegeType;
/*    */ import com.aionemu.gameserver.model.templates.siegelocation.SiegeLocationTemplate;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.Unmarshaller;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlRootElement;
/*    */ import javolution.util.FastMap;
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
/*    */ @XmlRootElement(name = "siege_locations")
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ public class SiegeLocationData
/*    */ {
/*    */   @XmlElement(name = "siege_location")
/*    */   private List<SiegeLocationTemplate> siegeLocationTemplates;
/* 48 */   private FastMap<Integer, SiegeLocation> siegeLocations = new FastMap();
/*    */ 
/*    */   
/*    */   void afterUnmarshal(Unmarshaller u, Object parent) {
/* 52 */     this.siegeLocations.clear();
/* 53 */     for (SiegeLocationTemplate template : this.siegeLocationTemplates) {
/*    */       
/* 55 */       switch (template.getType()) {
/*    */         
/*    */         case FORTRESS:
/* 58 */           this.siegeLocations.put(Integer.valueOf(template.getId()), new Fortress(template));
/*    */         
/*    */         case ARTIFACT:
/* 61 */           this.siegeLocations.put(Integer.valueOf(template.getId()), new Artifact(template));
/*    */         
/*    */         case BOSSRAID_LIGHT:
/*    */         case BOSSRAID_DARK:
/* 65 */           this.siegeLocations.put(Integer.valueOf(template.getId()), new Commander(template));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int size() {
/* 75 */     return this.siegeLocations.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public FastMap<Integer, SiegeLocation> getSiegeLocations() {
/* 80 */     return this.siegeLocations;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\SiegeLocationData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */