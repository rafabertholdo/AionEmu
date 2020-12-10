/*    */ package com.aionemu.gameserver.model.templates.itemset;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.stats.modifiers.StatModifier;
/*    */ import com.aionemu.gameserver.model.templates.stats.ModifiersTemplate;
/*    */ import java.util.TreeSet;
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
/*    */ @XmlRootElement(name = "FullBonus")
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ public class FullBonus
/*    */ {
/*    */   @XmlElement(name = "modifiers", required = false)
/*    */   protected ModifiersTemplate modifiers;
/*    */   private int totalnumberofitems;
/*    */   
/*    */   public TreeSet<StatModifier> getModifiers() {
/* 44 */     return (this.modifiers != null) ? this.modifiers.getModifiers() : null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getCount() {
/* 52 */     return this.totalnumberofitems;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setNumberOfItems(int number) {
/* 61 */     this.totalnumberofitems = number;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\itemset\FullBonus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */