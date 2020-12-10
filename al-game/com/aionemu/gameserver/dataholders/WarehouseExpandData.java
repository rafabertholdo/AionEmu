/*    */ package com.aionemu.gameserver.dataholders;
/*    */ 
/*    */ import com.aionemu.gameserver.model.templates.WarehouseExpandTemplate;
/*    */ import gnu.trove.TIntObjectHashMap;
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
/*    */ @XmlRootElement(name = "warehouse_expander")
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ public class WarehouseExpandData
/*    */ {
/*    */   @XmlElement(name = "warehouse_npc")
/*    */   private List<WarehouseExpandTemplate> clist;
/* 42 */   private TIntObjectHashMap<WarehouseExpandTemplate> npctlistData = new TIntObjectHashMap();
/*    */ 
/*    */   
/*    */   void afterUnmarshal(Unmarshaller u, Object parent) {
/* 46 */     for (WarehouseExpandTemplate npc : this.clist)
/*    */     {
/* 48 */       this.npctlistData.put(npc.getNpcId(), npc);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 54 */     return this.npctlistData.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public WarehouseExpandTemplate getWarehouseExpandListTemplate(int id) {
/* 59 */     return (WarehouseExpandTemplate)this.npctlistData.get(id);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\WarehouseExpandData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */