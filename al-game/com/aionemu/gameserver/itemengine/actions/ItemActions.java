/*    */ package com.aionemu.gameserver.itemengine.actions;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlElements;
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
/*    */ @XmlType(name = "ItemActions")
/*    */ public class ItemActions
/*    */ {
/*    */   @XmlElements({@XmlElement(name = "skilllearn", type = SkillLearnAction.class), @XmlElement(name = "extract", type = ExtractAction.class), @XmlElement(name = "skilluse", type = SkillUseAction.class), @XmlElement(name = "enchant", type = EnchantItemAction.class), @XmlElement(name = "queststart", type = QuestStartAction.class), @XmlElement(name = "dye", type = DyeAction.class), @XmlElement(name = "craftlearn", type = CraftLearnAction.class), @XmlElement(name = "toypetspawn", type = ToyPetSpawnAction.class)})
/*    */   protected List<AbstractItemAction> itemActions;
/*    */   
/*    */   public List<AbstractItemAction> getItemActions() {
/* 57 */     if (this.itemActions == null) {
/* 58 */       this.itemActions = new ArrayList<AbstractItemAction>();
/*    */     }
/* 60 */     return this.itemActions;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\itemengine\actions\ItemActions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */