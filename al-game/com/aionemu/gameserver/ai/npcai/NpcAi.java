/*    */ package com.aionemu.gameserver.ai.npcai;
/*    */ 
/*    */ import com.aionemu.gameserver.ai.AI;
/*    */ import com.aionemu.gameserver.ai.events.Event;
/*    */ import com.aionemu.gameserver.ai.events.EventHandlers;
/*    */ import com.aionemu.gameserver.ai.events.handler.EventHandler;
/*    */ import com.aionemu.gameserver.ai.state.StateHandlers;
/*    */ import com.aionemu.gameserver.model.gameobjects.Npc;
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
/*    */ public class NpcAi
/*    */   extends AI<Npc>
/*    */ {
/*    */   public NpcAi() {
/* 37 */     addEventHandler(EventHandlers.NOTHINGTODO_EH.getHandler());
/* 38 */     addEventHandler(EventHandlers.TALK_EH.getHandler());
/* 39 */     addEventHandler(EventHandlers.SEEPLAYER_EH.getHandler());
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 44 */     addStateHandler(StateHandlers.ACTIVE_NPC_SH.getHandler());
/* 45 */     addStateHandler(StateHandlers.TALKING_SH.getHandler());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void handleEvent(Event event) {
/* 51 */     super.handleEvent(event);
/*    */ 
/*    */ 
/*    */     
/* 55 */     if (this.owner.getLifeStats().isAlreadyDead()) {
/*    */       return;
/*    */     }
/* 58 */     EventHandler eventHandler = (EventHandler)this.eventHandlers.get(event);
/* 59 */     if (eventHandler != null)
/* 60 */       eventHandler.handleEvent(event, this); 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\npcai\NpcAi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */