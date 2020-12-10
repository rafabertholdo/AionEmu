/*    */ package com.aionemu.gameserver.dataholders;
/*    */ 
/*    */ import com.aionemu.gameserver.model.templates.item.ItemTemplate;
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
/*    */ 
/*    */ @XmlRootElement(name = "item_templates")
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ public class ItemData
/*    */ {
/*    */   @XmlElement(name = "item_template")
/*    */   private List<ItemTemplate> its;
/*    */   private TIntObjectHashMap<ItemTemplate> items;
/*    */   
/*    */   void afterUnmarshal(Unmarshaller u, Object parent) {
/* 46 */     this.items = new TIntObjectHashMap();
/* 47 */     for (ItemTemplate it : this.its)
/*    */     {
/* 49 */       this.items.put(it.getTemplateId(), it);
/*    */     }
/* 51 */     this.its = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemTemplate getItemTemplate(int itemId) {
/* 56 */     return (ItemTemplate)this.items.get(itemId);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int size() {
/* 64 */     return this.items.size();
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\ItemData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */