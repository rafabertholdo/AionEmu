/*    */ package com.aionemu.gameserver.model.broker.filter;
/*    */ 
/*    */ import com.aionemu.gameserver.model.PlayerClass;
/*    */ import com.aionemu.gameserver.model.templates.item.ItemTemplate;
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
/*    */ public class BrokerPlayerClassExtraFilter
/*    */   extends BrokerPlayerClassFilter
/*    */ {
/*    */   private int mask;
/*    */   
/*    */   public BrokerPlayerClassExtraFilter(int mask, PlayerClass playerClass) {
/* 35 */     super(playerClass);
/* 36 */     this.mask = mask;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean accept(ItemTemplate template) {
/* 42 */     return (super.accept(template) && this.mask == template.getTemplateId() / 100000);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\broker\filter\BrokerPlayerClassExtraFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */