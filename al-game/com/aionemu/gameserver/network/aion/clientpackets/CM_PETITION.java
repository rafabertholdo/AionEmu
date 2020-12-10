/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.Petition;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PETITION;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*    */ import com.aionemu.gameserver.services.PetitionService;
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
/*    */ public class CM_PETITION
/*    */   extends AionClientPacket
/*    */ {
/*    */   private int action;
/* 31 */   private String title = "";
/* 32 */   private String text = "";
/* 33 */   private String additionalData = "";
/*    */ 
/*    */   
/*    */   public CM_PETITION(int opcode) {
/* 37 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 43 */     this.action = readH();
/* 44 */     if (this.action == 2) {
/*    */       
/* 46 */       readD();
/*    */     }
/*    */     else {
/*    */       
/* 50 */       String data = readS();
/* 51 */       String[] dataArr = data.split("/", 3);
/* 52 */       this.title = dataArr[0];
/* 53 */       this.text = dataArr[1];
/* 54 */       this.additionalData = dataArr[2];
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 61 */     int playerObjId = ((AionConnection)getConnection()).getActivePlayer().getObjectId();
/* 62 */     if (this.action == 2)
/*    */     {
/* 64 */       if (PetitionService.getInstance().hasRegisteredPetition(playerObjId)) {
/*    */         
/* 66 */         int petitionId = PetitionService.getInstance().getPetition(playerObjId).getPetitionId();
/* 67 */         PetitionService.getInstance().deletePetition(playerObjId);
/* 68 */         sendPacket((AionServerPacket)new SM_SYSTEM_MESSAGE(1300552, new Object[] { Integer.valueOf(petitionId) }));
/* 69 */         sendPacket((AionServerPacket)new SM_SYSTEM_MESSAGE(1300553, new Object[] { Integer.valueOf(49) }));
/*    */         
/*    */         return;
/*    */       } 
/*    */     }
/*    */     
/* 75 */     if (!PetitionService.getInstance().hasRegisteredPetition(((AionConnection)getConnection()).getActivePlayer().getObjectId())) {
/*    */       
/* 77 */       Petition petition = PetitionService.getInstance().registerPetition(((AionConnection)getConnection()).getActivePlayer(), this.action, this.title, this.text, this.additionalData);
/* 78 */       sendPacket((AionServerPacket)new SM_PETITION(petition));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_PETITION.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */