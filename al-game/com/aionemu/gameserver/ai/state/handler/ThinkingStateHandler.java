package com.aionemu.gameserver.ai.state.handler;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.state.AIState;
import com.aionemu.gameserver.model.gameobjects.Npc;






















public class ThinkingStateHandler
  extends StateHandler
{
  public AIState getState() {
    return AIState.THINKING;
  }







  
  public void handleState(AIState state, AI<?> ai) {
    ai.clearDesires();
    
    Npc owner = (Npc)ai.getOwner();
    if (owner.getAggroList().getMostHated() != null) {
      
      ai.setAiState(AIState.ATTACKING);
      return;
    } 
    if (!owner.isAtSpawnLocation()) {
      
      ai.setAiState(AIState.MOVINGTOHOME);
      return;
    } 
    if (!owner.getLifeStats().isFullyRestoredHp()) {
      
      ai.setAiState(AIState.RESTING);
      return;
    } 
    ai.setAiState(AIState.ACTIVE);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\state\handler\ThinkingStateHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
