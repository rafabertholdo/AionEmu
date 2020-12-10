/*    */ package com.aionemu.gameserver.dataholders;
/*    */ 
/*    */ import com.aionemu.gameserver.model.templates.GatherableTemplate;
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
/*    */ @XmlRootElement(name = "gatherable_templates")
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ public class GatherableData
/*    */ {
/*    */   @XmlElement(name = "gatherable_template")
/*    */   private List<GatherableTemplate> gatherables;
/* 42 */   private TIntObjectHashMap<GatherableTemplate> gatherableData = new TIntObjectHashMap();
/*    */ 
/*    */   
/*    */   void afterUnmarshal(Unmarshaller u, Object parent) {
/* 46 */     for (GatherableTemplate gatherable : this.gatherables)
/*    */     {
/* 48 */       this.gatherableData.put(gatherable.getTemplateId(), gatherable);
/*    */     }
/* 50 */     this.gatherables = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 55 */     return this.gatherableData.size();
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
/*    */   public GatherableTemplate getGatherableTemplate(int id) {
/* 68 */     return (GatherableTemplate)this.gatherableData.get(id);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\GatherableData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */