/*    */ package com.aionemu.gameserver.model.templates.itemset;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.xml.bind.Unmarshaller;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
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
/*    */ @XmlRootElement(name = "itemset")
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ public class ItemSetTemplate
/*    */ {
/*    */   @XmlElement(required = true)
/*    */   protected List<ItemPart> itempart;
/*    */   @XmlElement(required = true)
/*    */   protected List<PartBonus> partbonus;
/*    */   protected FullBonus fullbonus;
/*    */   @XmlAttribute
/*    */   protected String name;
/*    */   @XmlAttribute
/*    */   protected int id;
/*    */   
/*    */   void afterUnmarshal(Unmarshaller u, Object parent) {
/* 51 */     if (this.fullbonus != null)
/*    */     {
/*    */       
/* 54 */       this.fullbonus.setNumberOfItems(this.itempart.size());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<ItemPart> getItempart() {
/* 63 */     return this.itempart;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<PartBonus> getPartbonus() {
/* 70 */     return this.partbonus;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FullBonus getFullbonus() {
/* 77 */     return this.fullbonus;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 84 */     return this.name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 91 */     return this.id;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\itemset\ItemSetTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */