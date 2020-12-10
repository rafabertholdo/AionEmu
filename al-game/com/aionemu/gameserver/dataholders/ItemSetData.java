/*    */ package com.aionemu.gameserver.dataholders;
/*    */ 
/*    */ import com.aionemu.gameserver.model.templates.itemset.ItemPart;
/*    */ import com.aionemu.gameserver.model.templates.itemset.ItemSetTemplate;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlRootElement(name = "item_sets")
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ public class ItemSetData
/*    */ {
/*    */   @XmlElement(name = "itemset")
/*    */   protected List<ItemSetTemplate> itemsetList;
/*    */   private TIntObjectHashMap<ItemSetTemplate> sets;
/*    */   private TIntObjectHashMap<ItemSetTemplate> setItems;
/*    */   
/*    */   void afterUnmarshal(Unmarshaller u, Object parent) {
/* 51 */     this.sets = new TIntObjectHashMap();
/* 52 */     this.setItems = new TIntObjectHashMap();
/*    */     
/* 54 */     for (ItemSetTemplate set : this.itemsetList) {
/*    */       
/* 56 */       this.sets.put(set.getId(), set);
/*    */ 
/*    */       
/* 59 */       for (ItemPart part : set.getItempart())
/*    */       {
/* 61 */         this.setItems.put(part.getItemid(), set);
/*    */       }
/*    */     } 
/* 64 */     this.itemsetList = null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemSetTemplate getItemSetTemplate(int itemSetId) {
/* 74 */     return (ItemSetTemplate)this.sets.get(itemSetId);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemSetTemplate getItemSetTemplateByItemId(int itemId) {
/* 84 */     return (ItemSetTemplate)this.setItems.get(itemId);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int size() {
/* 92 */     return this.sets.size();
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\ItemSetData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */