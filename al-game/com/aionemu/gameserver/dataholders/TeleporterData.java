/*    */ package com.aionemu.gameserver.dataholders;
/*    */ 
/*    */ import com.aionemu.gameserver.model.templates.teleport.TeleporterTemplate;
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
/*    */ @XmlRootElement(name = "npc_teleporter")
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ public class TeleporterData
/*    */ {
/*    */   @XmlElement(name = "teleporter_template")
/*    */   private List<TeleporterTemplate> tlist;
/* 33 */   private TIntObjectHashMap<TeleporterTemplate> npctlistData = new TIntObjectHashMap();
/*    */ 
/*    */   
/*    */   void afterUnmarshal(Unmarshaller u, Object parent) {
/* 37 */     for (TeleporterTemplate npc : this.tlist)
/*    */     {
/* 39 */       this.npctlistData.put(npc.getNpcId(), npc);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 45 */     return this.npctlistData.size();
/*    */   }
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
/*    */   public TeleporterTemplate getTeleporterTemplate(int id) {
/* 58 */     return (TeleporterTemplate)this.npctlistData.get(id);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\TeleporterData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */