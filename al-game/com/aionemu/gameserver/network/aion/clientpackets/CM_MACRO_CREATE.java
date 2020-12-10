/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_MACRO_RESULT;
/*    */ import com.aionemu.gameserver.services.PlayerService;
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
/*    */ 
/*    */ 
/*    */ public class CM_MACRO_CREATE
/*    */   extends AionClientPacket
/*    */ {
/* 37 */   private static final Logger log = Logger.getLogger(CM_MACRO_CREATE.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private int macroPosition;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private String macroXML;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CM_MACRO_CREATE(int opcode) {
/* 55 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 64 */     this.macroPosition = readC();
/* 65 */     this.macroXML = readS();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 74 */     log.debug(String.format("Created Macro #%d: %s", new Object[] { Integer.valueOf(this.macroPosition), this.macroXML }));
/*    */     
/* 76 */     PlayerService.addMacro(((AionConnection)getConnection()).getActivePlayer(), this.macroPosition, this.macroXML);
/*    */     
/* 78 */     sendPacket((AionServerPacket)SM_MACRO_RESULT.SM_MACRO_CREATED);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_MACRO_CREATE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */