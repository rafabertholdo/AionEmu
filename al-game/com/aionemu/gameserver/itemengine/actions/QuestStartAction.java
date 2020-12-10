/*    */ package com.aionemu.gameserver.itemengine.actions;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Item;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "QuestStartAction")
/*    */ public class QuestStartAction
/*    */   extends AbstractItemAction
/*    */ {
/*    */   @XmlAttribute
/*    */   protected int questid;
/*    */   
/*    */   public boolean canAct(Player player, Item parentItem, Item targetItem) {
/* 26 */     return true;
/*    */   }
/*    */   
/*    */   public void act(Player player, Item parentItem, Item targetItem) {}
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\itemengine\actions\QuestStartAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */