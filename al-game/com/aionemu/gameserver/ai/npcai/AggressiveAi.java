/*    */ package com.aionemu.gameserver.ai.npcai;
/*    */ 
/*    */ import com.aionemu.gameserver.ai.events.EventHandlers;
/*    */ import com.aionemu.gameserver.ai.state.StateHandlers;
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
/*    */ public class AggressiveAi
/*    */   extends MonsterAi
/*    */ {
/*    */   public AggressiveAi() {
/* 34 */     addEventHandler(EventHandlers.SEEPLAYER_EH.getHandler());
/* 35 */     addEventHandler(EventHandlers.NOTSEEPLAYER_EH.getHandler());
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 40 */     addStateHandler(StateHandlers.ACTIVE_AGGRO_SH.getHandler());
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\npcai\AggressiveAi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */