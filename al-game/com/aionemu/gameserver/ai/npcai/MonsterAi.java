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
/*    */ public class MonsterAi
/*    */   extends NpcAi
/*    */ {
/*    */   public MonsterAi() {
/* 31 */     addEventHandler(EventHandlers.ATTACKED_EH.getHandler());
/* 32 */     addEventHandler(EventHandlers.TIREDATTACKING_EH.getHandler());
/* 33 */     addEventHandler(EventHandlers.MOST_HATED_CHANGED_EH.getHandler());
/* 34 */     addEventHandler(EventHandlers.BACKHOME_EH.getHandler());
/* 35 */     addEventHandler(EventHandlers.RESTOREDHEALTH_EH.getHandler());
/*    */     
/* 37 */     addStateHandler(StateHandlers.MOVINGTOHOME_SH.getHandler());
/* 38 */     addStateHandler(StateHandlers.NONE_MONSTER_SH.getHandler());
/* 39 */     addStateHandler(StateHandlers.ATTACKING_SH.getHandler());
/* 40 */     addStateHandler(StateHandlers.THINKING_SH.getHandler());
/* 41 */     addStateHandler(StateHandlers.RESTING_SH.getHandler());
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\npcai\MonsterAi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */