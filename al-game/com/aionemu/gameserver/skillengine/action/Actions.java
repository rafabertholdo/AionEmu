/*    */ package com.aionemu.gameserver.skillengine.action;
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
/*    */ @XmlType(name = "Actions", propOrder = {"actions"})
/*    */ public class Actions
/*    */ {
/*    */   @XmlElements({@XmlElement(name = "itemuse", type = ItemUseAction.class), @XmlElement(name = "mpuse", type = MpUseAction.class), @XmlElement(name = "hpuse", type = HpUseAction.class), @XmlElement(name = "dpuse", type = DpUseAction.class)})
/*    */   protected List<Action> actions;
/*    */   
/*    */   public List<Action> getActions() {
/* 73 */     if (this.actions == null) {
/* 74 */       this.actions = new ArrayList<Action>();
/*    */     }
/* 76 */     return this.actions;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\action\Actions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */