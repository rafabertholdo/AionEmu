/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_INFO;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*    */ import com.aionemu.gameserver.questEngine.QuestEngine;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*    */ import com.aionemu.gameserver.services.WeatherService;
/*    */ import com.aionemu.gameserver.spawnengine.RiftSpawnManager;
/*    */ import com.aionemu.gameserver.world.World;
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
/*    */ public class CM_LEVEL_READY
/*    */   extends AionClientPacket
/*    */ {
/*    */   public CM_LEVEL_READY(int opcode) {
/* 45 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 63 */     Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
/*    */     
/* 65 */     sendPacket((AionServerPacket)new SM_PLAYER_INFO(activePlayer, false));
/* 66 */     activePlayer.getController().startProtectionActiveTask();
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 71 */     World.getInstance().spawn((VisibleObject)activePlayer);
/*    */     
/* 73 */     activePlayer.getController().refreshZoneImpl();
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 78 */     WeatherService.getInstance().loadWeather(activePlayer);
/*    */     
/* 80 */     QuestEngine.getInstance().onEnterWorld(new QuestEnv(null, activePlayer, Integer.valueOf(0), Integer.valueOf(0)));
/*    */ 
/*    */     
/* 83 */     sendPacket((AionServerPacket)new SM_SYSTEM_MESSAGE(1390122, new Object[] { Integer.valueOf(activePlayer.getPosition().getInstanceId()) }));
/*    */     
/* 85 */     RiftSpawnManager.sendRiftStatus(activePlayer);
/*    */     
/* 87 */     activePlayer.getEffectController().updatePlayerEffectIcons();
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_LEVEL_READY.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */