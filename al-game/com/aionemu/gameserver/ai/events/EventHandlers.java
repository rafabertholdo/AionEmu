/*    */ package com.aionemu.gameserver.ai.events;
/*    */ 
/*    */ import com.aionemu.gameserver.ai.events.handler.AttackedEventHandler;
/*    */ import com.aionemu.gameserver.ai.events.handler.BackHomeEventHandler;
/*    */ import com.aionemu.gameserver.ai.events.handler.EventHandler;
/*    */ import com.aionemu.gameserver.ai.events.handler.NotSeePlayerEventHandler;
/*    */ import com.aionemu.gameserver.ai.events.handler.NothingTodoEventHandler;
/*    */ import com.aionemu.gameserver.ai.events.handler.RestoredHealthEventHandler;
/*    */ import com.aionemu.gameserver.ai.events.handler.SeePlayerEventHandler;
/*    */ import com.aionemu.gameserver.ai.events.handler.TalkEventHandler;
/*    */ import com.aionemu.gameserver.ai.events.handler.TiredAttackingEventHandler;
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
/*    */ public enum EventHandlers
/*    */ {
/* 35 */   ATTACKED_EH((EventHandler)new AttackedEventHandler()),
/* 36 */   TIREDATTACKING_EH((EventHandler)new TiredAttackingEventHandler()),
/* 37 */   MOST_HATED_CHANGED_EH((EventHandler)new TiredAttackingEventHandler()),
/* 38 */   SEEPLAYER_EH((EventHandler)new SeePlayerEventHandler()),
/* 39 */   NOTSEEPLAYER_EH((EventHandler)new NotSeePlayerEventHandler()),
/* 40 */   BACKHOME_EH((EventHandler)new BackHomeEventHandler()),
/* 41 */   TALK_EH((EventHandler)new TalkEventHandler()),
/* 42 */   RESTOREDHEALTH_EH((EventHandler)new RestoredHealthEventHandler()),
/* 43 */   NOTHINGTODO_EH((EventHandler)new NothingTodoEventHandler());
/*    */   
/*    */   private EventHandler eventHandler;
/*    */ 
/*    */   
/*    */   EventHandlers(EventHandler eventHandler) {
/* 49 */     this.eventHandler = eventHandler;
/*    */   }
/*    */ 
/*    */   
/*    */   public EventHandler getHandler() {
/* 54 */     return this.eventHandler;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\events\EventHandlers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */