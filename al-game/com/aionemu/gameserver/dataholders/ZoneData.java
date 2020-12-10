/*    */ package com.aionemu.gameserver.dataholders;
/*    */ 
/*    */ import com.aionemu.gameserver.model.templates.zone.ZoneTemplate;
/*    */ import com.aionemu.gameserver.world.zone.ZoneName;
/*    */ import gnu.trove.THashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.Unmarshaller;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlRootElement;
/*    */ import javax.xml.bind.annotation.XmlType;
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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "")
/*    */ @XmlRootElement(name = "zones")
/*    */ public class ZoneData
/*    */   implements Iterable<ZoneTemplate>
/*    */ {
/*    */   @XmlElement(name = "zone")
/*    */   protected List<ZoneTemplate> zoneList;
/* 46 */   private THashMap<ZoneName, ZoneTemplate> zoneNameMap = new THashMap();
/*    */ 
/*    */   
/*    */   void afterUnmarshal(Unmarshaller u, Object parent) {
/* 50 */     for (ZoneTemplate zone : this.zoneList)
/*    */     {
/* 52 */       this.zoneNameMap.put(zone.getName(), zone);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Iterator<ZoneTemplate> iterator() {
/* 59 */     return this.zoneList.iterator();
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 64 */     return this.zoneList.size();
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\ZoneData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */