/*    */ package com.aionemu.gameserver.dataholders;
/*    */ 
/*    */ import com.aionemu.gameserver.model.templates.WorldMapTemplate;
/*    */ import gnu.trove.TIntObjectHashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.Unmarshaller;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlRootElement;
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
/*    */ 
/*    */ 
/*    */ @XmlRootElement(name = "world_maps")
/*    */ @XmlAccessorType(XmlAccessType.NONE)
/*    */ public class WorldMapsData
/*    */   implements Iterable<WorldMapTemplate>
/*    */ {
/*    */   @XmlElement(name = "map")
/*    */   private List<WorldMapTemplate> worldMaps;
/* 47 */   private TIntObjectHashMap<WorldMapTemplate> worldIdMap = new TIntObjectHashMap();
/*    */ 
/*    */   
/*    */   void afterUnmarshal(Unmarshaller u, Object parent) {
/* 51 */     for (WorldMapTemplate map : this.worldMaps)
/*    */     {
/* 53 */       this.worldIdMap.put(map.getMapId().intValue(), map);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Iterator<WorldMapTemplate> iterator() {
/* 63 */     return this.worldMaps.iterator();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int size() {
/* 73 */     return (this.worldMaps == null) ? 0 : this.worldMaps.size();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldMapTemplate getTemplate(int worldId) {
/* 83 */     return (WorldMapTemplate)this.worldIdMap.get(worldId);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\WorldMapsData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */