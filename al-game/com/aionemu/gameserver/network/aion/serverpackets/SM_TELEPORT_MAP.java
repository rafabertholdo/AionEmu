/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.model.templates.teleport.TeleporterTemplate;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*    */ import com.aionemu.gameserver.world.World;
/*    */ import java.nio.ByteBuffer;
/*    */ import org.apache.log4j.Logger;
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
/*    */ public class SM_TELEPORT_MAP
/*    */   extends AionServerPacket
/*    */ {
/*    */   private int targetObjectId;
/*    */   private Player player;
/*    */   private TeleporterTemplate teleport;
/*    */   public Npc npc;
/* 42 */   private static final Logger log = Logger.getLogger(SM_TELEPORT_MAP.class);
/*    */ 
/*    */ 
/*    */   
/*    */   public SM_TELEPORT_MAP(Player player, int targetObjectId, TeleporterTemplate teleport) {
/* 47 */     this.player = player;
/* 48 */     this.targetObjectId = targetObjectId;
/* 49 */     this.npc = (Npc)World.getInstance().findAionObject(targetObjectId);
/* 50 */     this.teleport = teleport;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 56 */     if (this.teleport != null && this.teleport.getNpcId() != 0 && this.teleport.getTeleportId() != 0) {
/*    */       
/* 58 */       writeD(buf, this.targetObjectId);
/* 59 */       writeH(buf, this.teleport.getTeleportId());
/*    */     }
/*    */     else {
/*    */       
/* 63 */       PacketSendUtility.sendMessage(this.player, "Missing info at npc_teleporter.xml with npcid: " + this.npc.getNpcId());
/* 64 */       log.info(String.format("Missing teleport info with npcid: %d", new Object[] { Integer.valueOf(this.npc.getNpcId()) }));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_TELEPORT_MAP.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */