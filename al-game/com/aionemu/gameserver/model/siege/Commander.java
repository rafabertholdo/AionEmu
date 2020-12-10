/*    */ package com.aionemu.gameserver.model.siege;
/*    */ 
/*    */ import com.aionemu.gameserver.model.templates.siegelocation.SiegeLocationTemplate;
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
/*    */ public class Commander
/*    */   extends SiegeLocation
/*    */ {
/*    */   public Commander(SiegeLocationTemplate template) {
/* 29 */     super(template);
/*    */ 
/*    */     
/* 32 */     setVulnerable(false);
/* 33 */     setNextState(Integer.valueOf(0));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInfluenceValue() {
/* 40 */     return 0;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\siege\Commander.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */